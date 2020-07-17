//package com.example.fjp.v1.request;
//
//import com.example.fjp.v1.core.HttpExchange;
//import com.example.fjp.v1.response.HttpResponse;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.util.concurrent.Executor;
//
///**
// * @FileName: SimpleHttpHandler
// * @Author: fjp
// * @Date: 2020/7/16 14:26
// * @Description: 简单的HttpHandler实现程序
// * History:
// * <author>          <time>          <version>
// * fjp           2020/7/16           版本号
// */
//public class SimpleHttpHandler implements AbstractHttpHandler {
//
//	private final HttpAdapter httpAdapter;
//
//	private final Executor executor;
//
//
//	public SimpleHttpHandler(HttpAdapter httpAdapter, Executor executor) {
//		this.httpAdapter = httpAdapter;
//		this.executor = executor;
//	}
//
//	@Override
//	public void handle(HttpExchange httpExchange) throws IOException {
//
//	}
//
//	@Override
//	public void handle(Socket socket) {
//		try {
//			if (executor != null) {
//				this.executor.execute(new SimpleHttpHandler.HttpHandlerRunnable(socket));
//			} else {
//				handleSocket(socket);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
//
//	}
//
//	public void handleSocket(Socket socket) throws IOException {
//		if (socket == null) {
//			throw new IllegalArgumentException("Socket can't be null!");
//		}
//		httpAdapter.handle(socket);
//	}
//
//	class HttpHandlerRunnable implements Runnable {
//		final Socket socket;
//
//		HttpHandlerRunnable(Socket socket) {
//			this.socket = socket;
//		}
//
//		@Override
//		public void run() {
//			try {
//				SimpleHttpHandler.this.handleSocket(socket);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}