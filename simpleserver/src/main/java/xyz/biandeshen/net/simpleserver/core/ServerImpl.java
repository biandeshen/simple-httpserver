package xyz.biandeshen.net.simpleserver.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.biandeshen.net.simpleserver.common.HttpHandler;
import xyz.biandeshen.net.simpleserver.common.HttpStatus;
import xyz.biandeshen.net.simpleserver.common.request.HttpRequest;
import xyz.biandeshen.net.simpleserver.common.request.HttpRequestParser;
import xyz.biandeshen.net.simpleserver.common.request.SimpleHttpRequest;
import xyz.biandeshen.net.simpleserver.common.response.HttpResponse;
import xyz.biandeshen.net.simpleserver.common.response.SimpleHttpResponse;
import xyz.biandeshen.net.simpleserver.config.GlobalConfig;
import xyz.biandeshen.net.simpleserver.util.GlobalPropertiesUtil;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @FileName: ServerImpl
 * @Author: fjp
 * @Date: 2020/7/17 9:34
 * @Description: 自定义ServerImplement
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/17           版本号
 */
@SuppressWarnings("ALL")
public class ServerImpl {
	private static final Logger logger = LoggerFactory.getLogger(ServerImpl.class);
	private static final String DEFAULT_CHARSET_NAME = GlobalPropertiesUtil.getProperty("global-encoding");
	
	private String protocol;
	private Executor executor;
	private boolean https;
	private ContextList contexts;
	private InetSocketAddress address;
	private ServerSocketChannel schan;
	private Selector selector;
	private SelectionKey listenerKey;
	private volatile boolean finished = false;
	private volatile boolean terminating = false;
	private boolean started = false;
	
	private HttpServer wrapper;
	
	/**
	 * bootstrapExecutor
	 */
	private static final ExecutorService BOOTSTRAPEXECUTOR = new ThreadPoolExecutor(1, 1, Integer.MAX_VALUE,
	                                                                                TimeUnit.SECONDS,
	                                                                                new LinkedBlockingQueue<>(1024),
	                                                                                (ThreadFactory) Thread::new);
	
	
	/**
	 * 是否已经绑定端口号
	 */
	private boolean isBound = false;
	
	/**
	 * 是否已经创建serverSocket
	 */
	private boolean isCreated = false;
	
	/**
	 * 是否已经关闭连接
	 */
	private boolean isClosed = false;
	
	ServerImpl.Dispatcher dispatcher;
	
	public ServerImpl(HttpServer httpServer, String protocol, InetSocketAddress inetSocketAddress, int backLog) throws IOException {
		this.protocol = protocol;
		this.wrapper = httpServer;
		this.https = protocol.equalsIgnoreCase("https");
		this.address = inetSocketAddress;
		// Contexts
		this.contexts = new ContextList();
		// ServerSocketChannel
		this.schan = ServerSocketChannel.open();
		if (address != null) {
			ServerSocket serverSocket = this.schan.socket();
			serverSocket.bind(address, backLog);
			this.isBound = true;
		}
		this.selector = Selector.open();
		this.schan.configureBlocking(false);
		this.listenerKey = this.schan.register(this.selector, 16);
		// 监听程序
		this.dispatcher = new ServerImpl.Dispatcher();
		logger.info("HttpServer created in protocol:{}  ip:{}  port:{}", protocol, InetAddress.getLocalHost(),
		            inetSocketAddress.getPort());
	}
	
	private static Thread newThread(Runnable threadFactory) {return new Thread();}
	
	
	public HttpServer getWrapper() {
		return this.wrapper;
	}
	
	public void bind(InetSocketAddress inetSocketAddress, int backLog) throws IOException {
		if (this.isBound) {
			throw new BindException("HttpServer already bound");
		} else if (inetSocketAddress == null) {
			throw new NullPointerException("null address");
		} else {
			this.schan.bind(inetSocketAddress, backLog);
			this.isBound = true;
		}
	}
	
	public void start() {
		if (this.isBound && !this.started && !this.finished) {
			// 监听转发时需要使用线程池执行每次监听的任务
			// 故线程池不存在时，使用默认的线程池执行任务
			if (this.executor == null) {
				this.executor = new ServerImpl.DefaultExecutor();
			}
			// 启用监听及转发线程
			BOOTSTRAPEXECUTOR.execute(this.dispatcher);
			this.started = true;
		} else {
			throw new IllegalStateException("server in wrong state");
		}
	}
	
	public void setExecutor(Executor executor) {
		if (this.started) {
			throw new IllegalStateException("server already started");
		} else {
			this.executor = executor;
		}
	}
	
	public Executor getExecutor() {
		return this.executor;
	}
	
	public void stop(int seconds) {
		if (seconds < 0) {
			throw new IllegalArgumentException("negative delay parameter");
		} else {
			this.terminating = true;
			
			try {
				this.schan.close();
				//isBound = false;
				//isCreated = false;
				//isClosed = true;
			} catch (IOException var8) {
				//isBound = false;
				//isCreated = false;
				//isClosed = true;
			}
			this.selector.wakeup();
			long delayTime = System.currentTimeMillis() + (long) (seconds * 1000);
			while (System.currentTimeMillis() < delayTime) {
				this.delay();
				if (this.finished) {
					break;
				}
			}
			this.finished = true;
		}
	}
	
	private void delay() {
		Thread.yield();
		try {
			Thread.sleep(200L);
		} catch (InterruptedException var2) {
		}
	}
	
	public synchronized HttpContextImpl creatContext(String path, HttpHandler httpHandler) {
		if (httpHandler != null && path != null) {
			HttpContextImpl httpContext = new HttpContextImpl(this.protocol, path, httpHandler, this);
			this.contexts.add(httpContext);
			logger.info("context created in: {}:{}{}", this.getAddress().getAddress().getHostAddress(),
			            this.getAddress().getPort(), path);
			return httpContext;
		} else {
			throw new NullPointerException("null handler, or path parameter");
		}
	}
	
	public HttpContextImpl createContext(String path) {
		if (path == null) {
			throw new NullPointerException("null path parameter");
		} else {
			HttpContextImpl httpContext = new HttpContextImpl(this.protocol, path, null, this);
			logger.info("context created in: {}:{}{}", this.getAddress().getAddress().getHostAddress(),
			            this.getAddress().getPort(), path);
			return httpContext;
		}
	}
	
	public void removeContext(String path) {
		if (path == null) {
			throw new NullPointerException("null path parameter");
		} else {
			this.contexts.remove(this.protocol, path);
			logger.info("context removed: {}", path);
		}
	}
	
	public void removeContext(HttpContext httpContext) {
		if (!(httpContext instanceof HttpContextImpl)) {
			throw new IllegalArgumentException("wrong HttpContext type");
		} else {
			this.contexts.remove((HttpContextImpl) httpContext);
			logger.info("context removed: {}", httpContext.getPath());
		}
	}
	
	public InetSocketAddress getAddress() {
		return AccessController.doPrivileged((PrivilegedAction<InetSocketAddress>) () -> (InetSocketAddress) ServerImpl.this.schan.socket().getLocalSocketAddress());
	}
	
	private static class DefaultExecutor implements Executor {
		private DefaultExecutor() {
		}
		
		@Override
		public void execute(Runnable runnable) {
			runnable.run();
		}
	}
	
	class Dispatcher implements Runnable {
		Dispatcher() {
		}
		
		@Override
		public void run() {
			while (!ServerImpl.this.finished) {
				try {
					ServerImpl.this.selector.select(1000L);
					Set<SelectionKey> readyKeys = ServerImpl.this.selector.selectedKeys();
					Iterator<SelectionKey> readyIterator = readyKeys.iterator();
					
					while (readyIterator.hasNext()) {
						SelectionKey readyKey = readyIterator.next();
						if (readyKey.equals(ServerImpl.this.listenerKey)) {
							if (!ServerImpl.this.terminating) {
								SocketChannel clientChannel = ServerImpl.this.schan.accept();
								if (clientChannel != null) {
									clientChannel.configureBlocking(false);
									clientChannel.register(ServerImpl.this.selector, 1);
								}
							}
						} else {
							try {
								if (readyKey.isReadable()) {
									SocketChannel clientChannel = (SocketChannel) readyKey.channel();
									this.handle(clientChannel);
								} else {
									assert false;
								}
							} catch (IOException e) {
								this.handleException(readyKey, e);
							}
						}
						readyIterator.remove();
					}
					ServerImpl.this.selector.selectNow();
				} catch (IOException e) {
					e.printStackTrace();
					// 后续处理 todo 日志
					
				}
			}
			
			try {
				ServerImpl.this.selector.close();
			} catch (Exception e) {
			}
		}
		
		private void handleException(SelectionKey readyKey, IOException e) {
			// 后续处理 todo 日志
		}
		
		public void handle(SocketChannel clientSocket) throws IOException {
			ServerImpl.Exchange exchange = ServerImpl.this.new Exchange(clientSocket, ServerImpl.this.protocol);
			ServerImpl.this.executor.execute(exchange);
		}
	}
	
	private class Exchange implements Runnable {
		SocketChannel channel;
		HttpContextImpl ctx;
		//boolean rejected = false;
		String protocol;
		// 请求
		HttpRequest httpRequest;
		// 响应
		HttpResponse httpResponse;
		
		Exchange(SocketChannel clientSocket, String protocol) {
			this.channel = clientSocket;
			this.protocol = protocol;
			httpResponse = new SimpleHttpResponse();
		}
		
		@Override
		public void run() {
			try {
				//((SimpleHttpResponse) httpResponse).setOutputStream(channel.socket().getOutputStream());
				httpRequest = HttpRequestParser.parse2HttpRequest(channel);
				if (((SimpleHttpRequest) httpRequest).getRequestURI() == null) {
					this.reject(HttpStatus.HTTP_NOT_FOUND, "未找到的请求路径！");
					return;
				}
				
				if (((SimpleHttpRequest) httpRequest).getProtocol() == null) {
					this.reject(HttpStatus.HTTP_VERSION, "请求协议为空或不支持的HTTP版本，当前仅支持HTTP/1.1");
					return;
				}
				
				String method = ((SimpleHttpRequest) httpRequest).getMethod();
				if (Stream.of("GET", "POST", "PUT", "DELETE", "HEAD").noneMatch(method::equals)) {
					//	响应一个不支持的方法的提示
					this.reject(HttpStatus.HTTP_BAD_METHOD, "不支持的请求方法！");
					logger.warn("error response: Method Not Allowed");
					return;
				}
				
				URI requestUrl = ((SimpleHttpRequest) httpRequest).getURI();
				this.ctx = ServerImpl.this.contexts.findContext(this.protocol, requestUrl.getPath());
				if (this.ctx == null) {
					this.reject(HttpStatus.HTTP_UNAVAILABLE, "当前协议版本及路径不存在！");
					return;
				}
				
				if (this.ctx.getHandler() == null) {
					this.reject(HttpStatus.HTTP_NOT_IMPLEMENTED, "当前服务未实现！");
					return;
				}
				// ===== 执行context =====
				wrapper.getHttpAdapter().handle(this.ctx.getHandler(), httpRequest, httpResponse);
				
				// 响应流内容的输出
				this.sendReply();
			} catch (Exception e) {
				try {
					this.reject(HttpStatus.HTTP_INTERNAL_ERROR, "服务器内部错误！");
				} catch (IOException e1) {
					//	todo
					e1.printStackTrace();
					logger.error("error:  ", e1);
				}
				logger.error("error response:  ", e);
			} finally {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		void reject(int httpCode, String respStr) throws IOException {
			//this.rejected = true;
			httpResponse.setHttpStatusCode(httpCode);
			httpResponse.setHasConectClosed(false);
			byte[] body;
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<h1>");
			stringBuilder.append(httpCode);
			stringBuilder.append(" ");
			stringBuilder.append(HttpStatus.msg(httpCode));
			stringBuilder.append("</h1><p/><hr/>");
			stringBuilder.append(respStr);
			body = stringBuilder.toString().getBytes(GlobalConfig.GLOBAL_CHARSET);
			httpResponse.getResponseBody().setBody(body);
			this.sendReply();
		}
		
		void sendReply() throws IOException {
			if (httpResponse.getResponseLine() == null || httpResponse.getResponseHeader().getHttpHeaders().getHeaders().size() == 0) {
				logger.error("响应结果状态行或响应头为空！");
			}
			httpResponse.judgeHttpResponseFormat();
			ByteBuffer byteBuffer = httpResponse.getResponseBody().getByteBuffer();
			while (byteBuffer.hasRemaining()) {
				channel.write(byteBuffer);
			}
		}
	}
}