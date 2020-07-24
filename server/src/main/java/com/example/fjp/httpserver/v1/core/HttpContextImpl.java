package com.example.fjp.httpserver.v1.core;

import com.example.fjp.httpserver.v1.request.HttpHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: HttpContextImpl
 * @Author: fjp
 * @Date: 2020/7/17 9:29
 * @Description: 自定义HttpContext的实现类
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/17           版本号
 */
@SuppressWarnings("ALL")
class HttpContextImpl extends HttpContext {
	/**
	 * path 路径
	 */
	private String path;
	/**
	 * 网络协议 http/https
	 */
	private String protocol;
	/**
	 * 对应的处理器
	 */
	private HttpHandler handler;
	/**
	 * 属性
	 */
	private Map<String, Object> attributes = new HashMap<>();
	/**
	 * ServerImpl
	 */
	private ServerImpl server;
	
	/**
	 * 过滤器
	 */
	
	
	HttpContextImpl(String protocol, String path, HttpHandler handler, ServerImpl server) {
		if (path != null && protocol != null && path.length() >= 1 && path.charAt(0) == '/') {
			this.protocol = protocol.toLowerCase();
			this.path = path;
			if (!this.protocol.equals("http") && !this.protocol.equals("https")) {
				throw new IllegalArgumentException("Illegal value for protocol");
			} else {
				this.handler = handler;
				this.server = server;
			}
		} else {
			throw new IllegalArgumentException("Illegal value for path or protocol");
		}
	}
	
	@Override
	public HttpHandler getHandler() {
		return this.handler;
	}
	
	@Override
	public void setHandler(HttpHandler handler) {
		if (handler == null) {
			throw new NullPointerException("Null handler parameter");
		} else if (this.handler != null) {
			throw new IllegalArgumentException("handler already set");
		} else {
			this.handler = handler;
		}
	}
	
	@Override
	public String getPath() { return this.path;}
	
	@Override
	public AbstractHttpServer getServer() { return this.server.getWrapper(); }
	
	ServerImpl getServerImpl() { return this.server; }
	
	public String getProtocol() { return this.protocol; }
	
	@Override
	public Map<String, Object> getAttributes() { return this.attributes; }
}