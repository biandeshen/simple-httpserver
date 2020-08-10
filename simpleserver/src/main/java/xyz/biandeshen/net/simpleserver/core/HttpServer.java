package xyz.biandeshen.net.simpleserver.core;


import xyz.biandeshen.net.simpleserver.common.HttpAdapter;
import xyz.biandeshen.net.simpleserver.common.HttpHandler;

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
public interface HttpServer {
	
	public static HttpServer create() throws IOException {
		return create(null, 0);
	}
	
	public static HttpServer create(InetSocketAddress inetSocketAddress, int backLog) throws IOException {
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
	public void bind(InetSocketAddress inetSocketAddress, int i) throws IOException;
	
	public void start();
	
	public void setExecutor(Executor executor);
	
	public Executor getExecutor();
	
	public void stop(int i);
	
	public HttpContext createContext(String s, HttpHandler httpHandler);
	
	public HttpContext createContext(String s);
	
	public void removeContext(String s) throws IllegalArgumentException;
	
	public void removeContext(HttpContext httpContext);
	
	public InetSocketAddress getAddress();
	
	public void setHttpAdapter(HttpAdapter httpAdapter);
	
	public HttpAdapter getHttpAdapter();
	
}