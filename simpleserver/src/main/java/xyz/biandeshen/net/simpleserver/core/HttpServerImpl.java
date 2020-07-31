package xyz.biandeshen.net.simpleserver.core;


import xyz.biandeshen.net.simpleserver.request.HttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

/**
 * @FileName: HttpServerImpl
 * @Author: fjp
 * @Date: 2020/7/17 11:30
 * @Description: History:
 * <author>          <time>          <version>
 * fjp           2020/7/17           版本号
 */
public class HttpServerImpl implements HttpServer {
	ServerImpl server;
	
	public HttpServerImpl() {
		this(new InetSocketAddress(80), 0);
	}
	
	public HttpServerImpl(InetSocketAddress inetSocketAddress, int backLog) {
		this.server = new ServerImpl(this, "http", inetSocketAddress, backLog);
	}
	
	@Override
	public void bind(InetSocketAddress inetSocketAddress, int backLog) throws IOException {
		this.server.bind(inetSocketAddress, backLog);
	}
	
	@Override
	public void start() {
		this.server.start();
	}
	
	@Override
	public void setExecutor(Executor executor) {
		this.server.setExecutor(executor);
	}
	
	@Override
	public Executor getExecutor() {
		return this.server.getExecutor();
	}
	
	@Override
	public void stop(int seconds) {
		this.server.stop(seconds);
	}
	
	@Override
	public HttpContext createContext(String path, HttpHandler httpHandler) {
		return this.server.creatContext(path, httpHandler);
	}
	
	@Override
	public HttpContext createContext(String path) {
		return this.server.createContext(path);
	}
	
	@Override
	public void removeContext(String path) {
		this.server.removeContext(path);
	}
	
	@Override
	public void removeContext(HttpContext httpContext) {
		this.server.removeContext(httpContext);
	}
	
	@Override
	public InetSocketAddress getAddress() {
		return this.server.getAddress();
	}
}