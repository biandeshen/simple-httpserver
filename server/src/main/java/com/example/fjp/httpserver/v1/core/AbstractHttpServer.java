package com.example.fjp.httpserver.v1.core;


import com.example.fjp.httpserver.v1.request.HttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

/**
 * @FileName: AbstractHttpServer
 * @Author: fjp
 * @Date: 2020/7/15 11:29
 * @Description: 抽象的httpServer服务
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/15           版本号
 */
public abstract class AbstractHttpServer {
	
	protected AbstractHttpServer() {
	}
	
	public static AbstractHttpServer create() throws IOException {
		return create(null, 0);
	}
	
	public static AbstractHttpServer create(InetSocketAddress inetSocketAddress, int backLog) throws IOException {
		//DefaultHttpServerProvider var2 = DefaultHttpServerProvider.provider();
		DefaultHttpServerProvider defaultHttpServerProvider = new DefaultHttpServerProvider();
		return defaultHttpServerProvider.createHttpServer(inetSocketAddress, backLog);
	}
	
	
	/**
	 * 用于绑定套接字地址
	 *
	 * @param inetSocketAddress
	 * 		套接字地址
	 * @param i
	 *
	 * @throws IOException
	 */
	public void bind(InetSocketAddress inetSocketAddress, int i) throws IOException {
	
	}
	
	public void start() {
	
	}
	
	public void setExecutor(Executor executor) {
	
	}
	
	public Executor getExecutor() {
		return null;
	}
	
	public void stop(int i) {
	
	}
	
	public HttpContext createContext(String s, HttpHandler httpHandler) {
		return null;
	}
	
	public HttpContext createContext(String s) {
		return null;
	}
	
	public void removeContext(String s) throws IllegalArgumentException {
	
	}
	
	public void removeContext(HttpContext httpContext) {
	
	}
	
	public InetSocketAddress getAddress() {
		return null;
	}
}