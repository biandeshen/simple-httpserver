package com.example.fjp.v1.core;


import com.example.fjp.v1.AbstractHttpServer;
import com.example.fjp.v1.request.AbstractHttpHandler;
import com.example.fjp.v1.request.HttpRequest;
import com.example.fjp.v1.request.HttpRequestParse;
import com.example.fjp.v1.response.HttpResponse;
import com.example.fjp.v1.response.HttpResponseBuilder;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	private static ExecutorService bootstrapExecutor = Executors.newSingleThreadExecutor();
	
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
			this.bind(inetSocketAddress, backLog);
			this.isCreated = true;
			this.isBound = true;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		//
		this.dispatcher = new ServerImpl.Dispatcher();
		//	Timer todo
		//	event todo
		//	日志 todo
		System.out.println("HttpServer created  " + protocol + " " + inetSocketAddress);
		
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
			// todo
			if (this.executor == null) {
				this.executor = new ServerImpl.DefaultExecutor();
			}
			
			//Thread var1 = new Thread(this.dispatcher);
			//this.started = true;
			//var1.start();
			
			bootstrapExecutor.submit(this.dispatcher);
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
			
			//this.selector.wakeup();
			long var2 = System.currentTimeMillis() + (long) (seconds * 1000);
			
			while (System.currentTimeMillis() < var2) {
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
	
	public synchronized HttpContextImpl creatContext(String path, AbstractHttpHandler httpHandler) {
		if (httpHandler != null && path != null) {
			HttpContextImpl httpContext = new HttpContextImpl(this.protocol, path, httpHandler, this);
			this.contexts.add(httpContext);
			// todo 日志
			System.out.println("context created: " + path);
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
			// todo 日志
			System.out.println("context created: " + path);
			return httpContext;
		}
	}
	
	public void removeContext(String path) {
		if (path == null) {
			throw new NullPointerException("null path parameter");
		} else {
			this.contexts.remove(this.protocol, path);
			//this.logger.config("context removed: " + path);
			// todo 日志
			System.out.println("context removed: " + path);
		}
	}
	
	public void removeContext(HttpContext httpContext) {
		if (!(httpContext instanceof HttpContextImpl)) {
			throw new IllegalArgumentException("wrong HttpContext type");
		} else {
			this.contexts.remove((HttpContextImpl) httpContext);
			//this.logger.config("context removed: " + var1.getPath());
			// todo 日志
			System.out.println("context removed: " + httpContext.getPath());
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
							Socket clientSocket = ServerImpl.this.serverSocket.accept();
							this.handle(clientSocket);
						}
						
						
					}
				} catch (IOException e) {
					e.printStackTrace();
					// 后续处理 todo
				}
			}
		}
		
		public void handle(Socket clientSocket) throws IOException {
			ServerImpl.Exchange exchange = ServerImpl.this.new Exchange(clientSocket, ServerImpl.this.protocol);
			ServerImpl.this.executor.execute(exchange);
		}
	}
	
	private class Exchange implements Runnable {
		Socket clientSocket;
		String protocol;
		//HttpContextImpl context;
		HttpContextImpl ctx;
		boolean rejected = false;
		PrintWriter printWriter;
		// 请求
		HttpRequest httpRequest = new HttpRequest();
		// 响应
		HttpResponse httpResponse;
		
		Exchange(Socket clientSocket, String protocol) throws IOException {
			this.clientSocket = clientSocket;
			this.protocol = protocol;
		}
		
		@Override
		public void run() {
			try {
				printWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				httpRequest = HttpRequestParse.parse2HttpRequest(clientSocket.getInputStream());
				httpResponse = new HttpResponse();
				
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
					response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
					printWriter.println(response);
					System.err.println("error response: " + response);
				}
				
				URI requestUrl = new URI(httpRequest.getRequestURI());
				this.ctx = ServerImpl.this.contexts.findContext(this.protocol, requestUrl.getPath());
				if (this.ctx == null) {
					// todo
					this.reject(404, "404", "No context found for request");
					return;
				}
				if (this.ctx.getHandler() == null) {
					this.reject(500, "500", "No handler for context");
					return;
				}
				//// 执行context =====
				//this.ctx.getHandler().handle(clientSocket);
				this.ctx.getHandler().handle(httpRequest, httpResponse);
				//// 执行具体逻辑
				//handle(httpRequest, httpResponse);
				// 判断响应结果
				judgeHttpResponse(httpResponse);
				// 返回响应结果
				//printWriter.println(httpResponse);
				//printWriter.flush();
				sendReply(200, true, httpResponse.toString());
			} catch (Exception e) {
				HttpResponse response = HttpResponseBuilder.build2Response(httpRequest, e.toString());
				response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
				System.err.println("error response: " + response);
				e.printStackTrace();
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
				httpResponse.setCode(200);
				httpResponse.setStatus(HttpStatus.OK.getReasonPhrase());
			}
		}
		
		void reject(int httpCode, String var2, String var3) throws IOException {
			this.rejected = true;
			this.sendReply(httpCode, false, "<h1>" + httpCode + var2 + "</h1>" + var3);
			this.clientSocket.close();
		}
		
		// 重写 TODO
		void sendReply(int httpCode, boolean var2, String var3) {
			try {
				StringBuilder var4 = new StringBuilder(512);
				//var4.append("HTTP/1.1 ").append(httpCode).append(httpCode).append("\r\n");
				var4.append("HTTP/1.1 ").append(httpCode).append(" ").append(HttpStatus.valueOf(httpCode).getReasonPhrase()).append("\r\n");
				if (var3 != null && var3.length() != 0) {
					var4.append("Content-Length: ").append(var3.length()).append("\r\n").append("Content-Type: " +
							                                                                            "text/html\r" + "\n");
				} else {
					var4.append("Content-Length: 0\r\n");
					var3 = "";
				}
				
				if (var2) {
					var4.append("Connection: close\r\n");
				}
				
				var4.append("\r\n").append(var3);
				String var5 = var4.toString();
				//byte[] var6 = var5.getBytes("ISO8859_1");
				//byte[] var6 = var5.getBytes("UTF-8");
				this.printWriter.print(var5);
				this.printWriter.flush();
				if (var2) {
					this.clientSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}