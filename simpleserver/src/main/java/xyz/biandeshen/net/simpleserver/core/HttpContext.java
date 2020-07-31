package xyz.biandeshen.net.simpleserver.core;


import xyz.biandeshen.net.simpleserver.request.HttpHandler;

import java.util.Map;

/**
 * @FileName: HttpContext
 * @Author: fjp
 * @Date: 2020/7/16 17:05
 * @Description: HttpContext
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public abstract class HttpContext {
	protected HttpContext() {
	}
	
	public abstract HttpHandler getHandler();
	
	public abstract void setHandler(HttpHandler handler);
	
	public abstract String getPath();
	
	public abstract HttpServer getServer();
	
	public abstract Map<String, Object> getAttributes();
	
	//public abstract List<Filter> getFilters();
	
	//public abstract Authenticator setAuthenticator(Authenticator var1);
	//
	//public abstract Authenticator getAuthenticator();
	
}