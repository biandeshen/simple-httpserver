package com.example.fjp.httpserver.v1.core;


import com.example.fjp.httpserver.v1.common.CharsetLengthUtils;
import com.example.fjp.httpserver.v1.common.Code;
import com.example.fjp.httpserver.v1.config.GlobalConfig;
import com.example.fjp.httpserver.v1.request.HttpHandler;
import com.example.fjp.httpserver.v1.request.HttpRequest;
import com.example.fjp.httpserver.v1.request.HttpRequestParse;
import com.example.fjp.httpserver.v1.response.HttpResponse;
import com.example.fjp.httpserver.v1.response.HttpResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
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
	
	/**
	 * http
	 */
	private String protocol;
	private Executor executor;
	private boolean https;
	private ContextList contexts;
	private InetSocketAddress address;
	private ServerSocket serverSocket;
	
	private volatile boolean finished = false;
	private volatile boolean terminating = false;
	private boolean started = false;
	
	
	private AbstractHttpServer wrapper;
	
	/**
	 * bootstrapExecutor
	 */
	private static ExecutorService bootstrapExecutor = new ThreadPoolExecutor(1, 1, Integer.MAX_VALUE,
	                                                                          TimeUnit.SECONDS,
	                                                                          new LinkedBlockingQueue<>(1024),
	                                                                          new AbortPolicy());
	
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
	
	public ServerImpl(AbstractHttpServer httpServer, String protocol, InetSocketAddress inetSocketAddress,
	                  int backLog) {
		this.protocol = protocol;
		this.wrapper = httpServer;
		this.https = protocol.equalsIgnoreCase("https");
		this.address = inetSocketAddress;
		// Contexts
		this.contexts = new ContextList();
		// ServerSocket
		try {
			this.serverSocket = new ServerSocket();
			if (inetSocketAddress != null) {
				this.bind(inetSocketAddress, backLog);
				this.isBound = true;
			}
			this.isCreated = true;
		} catch (IOException e) {
			logger.error("serverImpl created failed: {}", e.toString());
			System.exit(1);
		}
		//
		this.dispatcher = new ServerImpl.Dispatcher();
		//	Timer todo
		//	event todo
		logger.info("HttpServer created in protocol:{}  ip:{}  port:{}", protocol, inetSocketAddress.getAddress(),
		            inetSocketAddress.getPort());
		
	}
	
	
	public AbstractHttpServer getWrapper() {
		return this.wrapper;
	}
	
	public void bind(InetSocketAddress inetSocketAddress, int backLog) throws IOException {
		if (this.isBound) {
			throw new BindException("HttpServer already bound");
		} else if (inetSocketAddress == null) {
			throw new NullPointerException("null address");
		} else {
			ServerSocket serverSocket = this.serverSocket;
			serverSocket.bind(inetSocketAddress, backLog);
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
			bootstrapExecutor.execute(this.dispatcher);
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
				this.serverSocket.close();
				isBound = false;
				isCreated = false;
				isClosed = true;
			} catch (IOException var8) {
				isBound = false;
				isCreated = false;
				isClosed = true;
			}
			
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
		return AccessController.doPrivileged(new PrivilegedAction<InetSocketAddress>() {
			@Override
			public InetSocketAddress run() {
				return (InetSocketAddress) ServerImpl.this.serverSocket.getLocalSocketAddress();
			}
		});
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
					while (!isClosed || isCreated || !isBound) {
						if (!ServerImpl.this.terminating) {
							// 监听要建立到这个套接字的连接并接受它。该方法将阻塞，直到建立连接为止。
							Socket clientSocket = ServerImpl.this.serverSocket.accept();
							// 以此套接字连接构造为一个定义好处理逻辑的Runnable，并交由线程池执行
							this.handle(clientSocket);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					// 后续处理 todo
				}
			}
		}
		
		public void handle(Socket clientSocket) {
			ServerImpl.Exchange exchange = ServerImpl.this.new Exchange(clientSocket, ServerImpl.this.protocol);
			ServerImpl.this.executor.execute(exchange);
		}
	}
	
	private class Exchange implements Runnable {
		Socket clientSocket;
		String protocol;
		HttpContextImpl ctx;
		boolean rejected = false;
		PrintWriter printWriter;
		// 请求
		HttpRequest httpRequest = new HttpRequest();
		// 响应
		HttpResponse httpResponse = new HttpResponse();
		
		Exchange(Socket clientSocket, String protocol) {
			this.clientSocket = clientSocket;
			this.protocol = protocol;
		}
		
		@Override
		public void run() {
			try {
				httpResponse.setOutputStream(clientSocket.getOutputStream());
				httpRequest.setInputStream(clientSocket.getInputStream());
				printWriter = new PrintWriter(new OutputStreamWriter(httpResponse.getOutputStream()));
				httpRequest = HttpRequestParse.parse2HttpRequest(httpRequest.getInputStream());
				
				if (httpRequest.getRequestURI() == null) {
					this.clientSocket.close();
					return;
				}
				
				if (httpRequest.getProtocol() == null) {
					this.reject(400, "400", "Bad request line");
					return;
				}
				
				String method = httpRequest.getMethod();
				if (Stream.of("GET", "POST", "PUT", "DELETE", "HEAD").noneMatch(method::equals)) {
					//	响应一个不支持的方法的提示
					HttpResponse response = HttpResponseBuilder.build2Response(httpRequest, "不支持的请求方法!");
					response.setCode(Code.HTTP_INTERNAL_ERROR);
					response.setStatus(Code.msg(Code.HTTP_INTERNAL_ERROR));
					printWriter.println(response);
					logger.warn("error response: {}", response);
				}
				
				URI requestUrl = new URI(httpRequest.getRequestURI());
				this.ctx = ServerImpl.this.contexts.findContext(this.protocol, requestUrl.getPath());
				if (this.ctx == null) {
					this.reject(Code.HTTP_NOT_FOUND, Code.msg(Code.HTTP_NOT_FOUND), "No context found for request");
					return;
				}
				if (this.ctx.getHandler() == null) {
					this.reject(Code.HTTP_INTERNAL_ERROR, Code.msg(Code.HTTP_INTERNAL_ERROR), "No handler for " +
							                                                                          "context");
					return;
				}
				// ===== 执行context =====
				this.ctx.getHandler().handle(httpRequest, httpResponse);
				// 判断响应结果
				judgeHttpResponse(httpResponse);
				// 返回响应结果 todo
				// 响应流内容的输出
				sendReply(httpResponse.getCode(), false, httpResponse.getResponseBody());
			} catch (Exception e) {
				HttpResponse response = HttpResponseBuilder.build2Response(httpRequest, e.getMessage());
				response.setCode(Code.HTTP_INTERNAL_ERROR);
				response.setStatus(Code.msg(Code.HTTP_INTERNAL_ERROR));
				try {
					reject(response.getCode(), response.getStatus(), response.getResponseBody());
				} catch (IOException e1) {
					//	todo
				}
				logger.error("error response:  {}", e);
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		void judgeHttpResponse(HttpResponse httpResponse) {
			// 进行判空操作 即未设置状态码时，默认成功
			if (httpResponse.getStatus() != null || httpResponse.getCode() == 0) {
				httpResponse.setCode(Code.HTTP_OK);
				httpResponse.setStatus(Code.msg(Code.HTTP_OK));
			}
		}
		
		void reject(int httpCode, String httpStatus, String respStr) throws IOException {
			this.rejected = true;
			this.sendReply(httpCode, false, "<h1>" + httpCode + " " + httpStatus + "</h1><p/><hr/>" + respStr);
			this.clientSocket.close();
		}
		
		
		/**
		 * 响应结果
		 * <p>
		 * 重写 TODO
		 *
		 * @param httpCode
		 * 		状态码
		 * @param isConnClosed
		 * 		是否关闭客户端服务器之间的长连接
		 * @param responseBody
		 * 		响应结果
		 */
		void sendReply(int httpCode, boolean isConnClosed, String responseBody) {
			try {
				StringBuilder stringBuilder = new StringBuilder(512);
				stringBuilder.append("HTTP/1.1 ");
				stringBuilder.append(httpCode);
				stringBuilder.append(" ");
				stringBuilder.append(Code.msg(httpCode)).append("\r\n");
				if (responseBody != null && responseBody.length() != 0) {
					stringBuilder.append("Content-Length: ");
					stringBuilder.append(CharsetLengthUtils.getWordCountCharset(responseBody,
					                                                            GlobalConfig.GLOBAL_ENCODING));
					stringBuilder.append("\r\n");
					stringBuilder.append("Content-Type: ");
					stringBuilder.append("text/html;charset=");
					stringBuilder.append(GlobalConfig.GLOBAL_ENCODING);
					stringBuilder.append("\r\n");
				} else {
					stringBuilder.append("Content-Length: 0\r\n");
					responseBody = "";
				}
				
				if (isConnClosed) {
					stringBuilder.append("Connection: close\r\n");
				}
				
				stringBuilder.append("\r\n");
				stringBuilder.append(responseBody);
				stringBuilder.append("\r\n");
				this.printWriter.print(stringBuilder.toString());
				this.printWriter.flush();
				if (isConnClosed) {
					this.clientSocket.close();
				}
			} catch (IOException e) {
				logger.error("error response: {}", e.toString());
			}
			
		}
	}
}