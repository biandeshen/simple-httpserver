//package com.example.fjp.httpserver.v1;
//
//import javax.annotation.PreDestroy;
//import javax.xml.ws.Response;
//import java.io.*;
//import java.net.*;
//import java.util.concurrent.Executor;
//
///**
// * @FileName: SimpleServerSocket
// * @Author: fjp
// * @Date: 2020/7/15 11:15
// * @Description: 简单的Http服务器
// * History:
// * <author>          <time>          <version>
// * fjp           2020/7/15           版本号
// */
//public class SimpleHttpServer extends AbstractHttpServer implements Closeable {
//	/**
//	 * 服务端socket套接字
//	 */
//	private ServerSocket serverSocket;
//	/**
//	 * 客户端将返回的套接字
//	 */
//	private Socket clientSocket = null;
//	/**
//	 * 缓冲读取
//	 */
//	private BufferedReader bufferedReader = null;
//	/**
//	 * 缓冲写入
//	 */
//	private PrintWriter printWriter = null;
//
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
//	SimpleHttpServer() throws IOException {
//		this.serverSocket = new ServerSocket();
//	}
//
//	SimpleHttpServer(ServerSocket serverSocket) {
//		this.serverSocket = serverSocket;
//	}
//
//
//	public static SimpleHttpServer create() throws IOException {
//		SimpleHttpServer simpleHttpServer = new SimpleHttpServer();
//		if (!simpleHttpServer.serverSocket.isBound()) {
//			simpleHttpServer.serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), PORT), BACKLOG);
//		}
//		simpleHttpServer.isBound = true;
//		simpleHttpServer.isCreated = true;
//		System.out.println("simpleHttpServer is created in IP = " + simpleHttpServer.serverSocket.getLocalSocketAddress() + " and Port = " + simpleHttpServer.serverSocket.getLocalPort());
//		return simpleHttpServer;
//	}
//
//	public static SimpleHttpServer create(InetSocketAddress inetSocketAddress, int backlog) throws IOException {
//		ServerSocket serverSocket = new ServerSocket(inetSocketAddress.getPort(), backlog,
//		                                             inetSocketAddress.getAddress());
//		if (!serverSocket.isBound()) {
//			serverSocket.bind(inetSocketAddress, backlog);
//		}
//		SimpleHttpServer simpleHttpServer = new SimpleHttpServer(serverSocket);
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
//		//System.out.println("SimpleHttpServer is starting...");
//		System.out.println("SimpleHttpServer was started!");
//		handle();
//	}
//
//
//	protected void handle() {
//		try {
//			if (isBound && isCreated && !isClosed) {
//				// 若关闭，则停止
//				while (!isClosed) {
//					accept();
//					receive();
//					response(null);
//					clientSocket.close();
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	protected void accept() throws IOException {
//		clientSocket = serverSocket.accept();
//		bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//		// true 即自动刷新缓存
//		printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
//	}
//
//	protected String receive() throws IOException {
//		StringBuilder stringBuilder = new StringBuilder();
//		String readLine;
//		while ((readLine = bufferedReader.readLine()) != null && !readLine.equals("")) {
//			stringBuilder.append(readLine);
//			stringBuilder.append(" ");
//		}
//		System.out.println("readLine = " + stringBuilder.toString());
//		bufferedReader.close();
//		return stringBuilder.toString();
//	}
//
//	protected void response(String message) throws IOException {
//		if (message == null || message.length() <= 0) {
//			message = "HTTP/1.0 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<html><body><b>Hello " +
//					          "World!</b></body" + "></html>";
//		}
//		printWriter.write(message);
//		printWriter.close();
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
//			bufferedReader.close();
//			printWriter.close();
//		} catch (IOException e) {
//			serverSocket = null;
//			clientSocket = null;
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
//	@Override
//	public void close() throws IOException {
//		System.out.println("SimpleHttpServer is closing...");
//		if (!isClosed) {
//			bufferedReader.close();
//			printWriter.close();
//			serverSocket = null;
//			clientSocket = null;
//			isBound = false;
//			isCreated = false;
//			isClosed = true;
//			System.out.println("SimpleHttpServer was closed！");
//		}
//
//	}
//}