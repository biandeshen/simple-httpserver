package com.example.fjp.httpserver.v1.core;


import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @FileName: DefaultHttpServerProvider
 * @Author: fjp
 * @Date: 2020/7/17 11:24
 * @Description: 默认的HttpServer的提供程序
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/17           版本号
 */
public class DefaultHttpServerProvider {
	private static final Object lock = new Object();
	private static DefaultHttpServerProvider provider = null;
	
	public DefaultHttpServerProvider() {
		//HttpServerProvider();
	}
	
	public AbstractHttpServer createHttpServer(InetSocketAddress inetSocketAddress, int backLog) throws IOException {
		return new HttpServerImpl(inetSocketAddress, backLog);
	}
	
	//public HttpsServer createHttpsServer(InetSocketAddress var1, int var2) throws IOException {
	//	return new HttpsServerImpl(var1, var2);
	//}SimpleHttpServerX
	
	
	///**
	// * 实际应为父类构造方法，子类构造时先调用父类构造方法
	// * 故改为方法后构造方法应先调用此方法
	// */
	//protected void HttpServerProvider() {
	//	SecurityManager var1 = System.getSecurityManager();
	//	if (var1 != null) {
	//		var1.checkPermission(new RuntimePermission("httpServerProvider"));
	//	}
	//
	//}
	
	//private static boolean loadProviderFromProperty() {
	//	String var0 = System.getProperty("com.example.fjp.httpserver.v1.core.DefaultHttpServerProvider");
	//	if (var0 == null) {
	//		return false;
	//	} else {
	//		try {
	//			Class var1 = Class.forName(var0, true, ClassLoader.getSystemClassLoader());
	//			provider = (HttpServerProvider) var1.newInstance();
	//			return true;
	//		} catch (IllegalAccessException | InstantiationException | SecurityException | ClassNotFoundException var2) {
	//			throw new ServiceConfigurationError((String) null, var2);
	//		}
	//	}
	//}
	//
	//private static boolean loadProviderAsService() {
	//	Iterator var0 = ServiceLoader.load(HttpServerProvider.class, ClassLoader.getSystemClassLoader()).iterator();
	//
	//	while (true) {
	//		try {
	//			if (!var0.hasNext()) {
	//				return false;
	//			}
	//
	//			provider = (HttpServerProvider) var0.next();
	//			return true;
	//		} catch (ServiceConfigurationError var2) {
	//			if (!(var2.getCause() instanceof SecurityException)) {
	//				throw var2;
	//			}
	//		}
	//	}
	//}
	
	//public static DefaultHttpServerProvider provider() {
	//	synchronized (lock) {
	//		return provider != null ? provider :
	//				       (HttpServerProvider) AccessController.doPrivileged(new PrivilegedAction<Object>() {
	//			public Object run() {
	//				if (HttpServerProvider.loadProviderFromProperty()) {
	//					return HttpServerProvider.provider;
	//				} else if (HttpServerProvider.loadProviderAsService()) {
	//					return HttpServerProvider.provider;
	//				} else {
	//					HttpServerProvider.provider = new sun.net.httpserver.DefaultHttpServerProvider();
	//					return HttpServerProvider.provider;
	//				}
	//			}
	//		});
	//	}
	//}
}