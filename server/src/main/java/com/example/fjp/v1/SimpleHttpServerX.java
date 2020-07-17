//package com.example.fjp.v1;
//
//import com.example.fjp.v1.request.AbstractHttpHandler;
//import com.example.fjp.v1.request.DefaultHttpAdapter;
//
//import javax.annotation.PreDestroy;
//import java.io.IOException;
//import java.net.*;
//import java.util.concurrent.*;
//
///**
// * @FileName: SimpleHttpServerX
// * @Author: fjp
// * @Date: 2020/7/16 15:53
// * @Description: 简单实现的http服务端
// * History:
// * <author>          <time>          <version>
// * fjp           2020/7/16           版本号
// */
//public class SimpleHttpServerX extends AbstractHttpServer {
//	/**
//	 * bootstrapExecutor
//	 */
//	private static ExecutorService bootstrapExecutor = Executors.newSingleThreadExecutor();
//	/**
//	 * taskExecutor
//	 */
//	private static ExecutorService taskExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
//	                                                                     Runtime.getRuntime().availableProcessors(),
//	                                                                     0L, TimeUnit.MILLISECONDS,
//	                                                                     new LinkedBlockingQueue<>(100),
//	                                                                     new ThreadPoolExecutor.DiscardPolicy());
//	/**
//	 * 服务端socket套接字
//	 */
//	private ServerSocket serverSocket;
//	/**
//	 * 默认绑定本地 端口号 8080
//	 */
//	private static final int PORT = 9527;
//	/**
//	 * 默认的BACKLOG大小
//	 */
//	private static final int BACKLOG = 128;
//
//	/**
//	 * 是否已经绑定端口号
//	 */
//	private boolean isBound = false;
//
//	/**
//	 * 是否已经创建serverSocket
//	 */
//	private boolean isCreated = false;
//
//	/**
//	 * 是否已经关闭连接
//	 */
//	private boolean isClosed = false;
//
//
//	SimpleHttpServerX() throws IOException {
//		this.serverSocket = new ServerSocket();
//	}
//
//	SimpleHttpServerX(ServerSocket serverSocket) {
//		this.serverSocket = serverSocket;
//	}
//
//
//	public static SimpleHttpServerX create() throws IOException {
//		SimpleHttpServerX simpleHttpServer = new SimpleHttpServerX();
//		if (!simpleHttpServer.serverSocket.isBound()) {
//			simpleHttpServer.serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), PORT), BACKLOG);
//		}
//		simpleHttpServer.isBound = true;
//		simpleHttpServer.isCreated = true;
//		System.out.println("simpleHttpServer is created in IP = " + simpleHttpServer.serverSocket.getLocalSocketAddress() + " and Port = " + simpleHttpServer.serverSocket.getLocalPort());
//		return simpleHttpServer;
//	}
//
//	public static SimpleHttpServerX create(InetSocketAddress inetSocketAddress, int backlog) throws IOException {
//		ServerSocket serverSocket = new ServerSocket(inetSocketAddress.getPort(), backlog,
//		                                             inetSocketAddress.getAddress());
//		if (!serverSocket.isBound()) {
//			serverSocket.bind(inetSocketAddress, backlog);
//		}
//		SimpleHttpServerX simpleHttpServer = new SimpleHttpServerX(serverSocket);
//		simpleHttpServer.isBound = true;
//		simpleHttpServer.isCreated = true;
//		System.out.println("simpleHttpServer is created in IP = " + simpleHttpServer.serverSocket.getLocalSocketAddress() + " and Port = " + simpleHttpServer.serverSocket.getLocalPort());
//		return simpleHttpServer;
//	}
//
//	@Override
//	public void bind(InetSocketAddress inetSocketAddress, int backlog) throws IOException {
//		if (this.isBound) {
//			throw new BindException("HttpServer already bound");
//		} else if (inetSocketAddress == null) {
//			throw new NullPointerException("null address");
//		} else {
//			ServerSocket serverSocket = this.serverSocket;
//			serverSocket.bind(inetSocketAddress, backlog);
//			this.isBound = true;
//		}
//	}
//
//	@Override
//	public void start() {
//		System.out.println("SimpleHttpServer was started!");
//		handle();
//	}
//
//
//	protected void handle() {
//		// 若关闭，则停止
//		while (isCreated || !isClosed || isBound) {
//			try {
//				bootstrapExecutor.submit(new ServerThread(serverSocket));
//				break;
//			} catch (Exception e) {
//				try {
//					//重试
//					TimeUnit.SECONDS.sleep(10);
//				} catch (InterruptedException ie) {
//					Thread.currentThread().interrupt();
//				}
//			}
//
//			bootstrapExecutor.shutdown();
//		}
//	}
//
//	class ServerThread implements Runnable {
//
//		private ServerSocket serverSocket;
//
//		public ServerThread(ServerSocket s) throws IOException {
//			this.serverSocket = s;
//		}
//
//		@Override
//		public void run() {
//			while (!isClosed || isCreated || !isBound) {
//				try {
//					Socket socket = this.serverSocket.accept();
//					AbstractHttpHandler httpHandler = new SimpleHttpHandler(new DefaultHttpAdapter(), taskExecutor);
//					httpHandler.handle(socket);
//				} catch (Exception e) {
//					e.printStackTrace();
//					try {
//						TimeUnit.SECONDS.sleep(1);
//					} catch (InterruptedException ie) {
//						Thread.currentThread().interrupt();
//					}
//				}
//			}
//		}
//	}
//
//	@Override
//	public void setExecutor(Executor executor) {
//		super.setExecutor(executor);
//	}
//
//	@Override
//	public Executor getExecutor() {
//		return super.getExecutor();
//	}
//
//	@Override
//	@PreDestroy
//	public void stop(int i) {
//		System.out.println("SimpleHttpServer is stopping...");
//		try {
//			close();
//		} catch (IOException e) {
//			serverSocket = null;
//			isBound = false;
//			isCreated = false;
//			isClosed = true;
//		}
//		System.out.println("SimpleHttpServer was stopped!");
//	}
//
//	@Override
//	public InetSocketAddress getAddress() {
//		return super.getAddress();
//	}
//
//	public void close() throws IOException {
//		System.out.println("SimpleHttpServer is closing...");
//		if (!isClosed) {
//			serverSocket = null;
//			isBound = false;
//			isCreated = false;
//			isClosed = true;
//			System.out.println("SimpleHttpServer was closed！");
//		}
//
//	}
//}