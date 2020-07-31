package xyz.biandeshen.net.simpleserver.core;


import java.util.Iterator;
import java.util.LinkedList;

/**
 * @FileName: ContextList
 * @Author: fjp
 * @Date: 2020/7/17 12:08
 * @Description: 自定义ContextList的实现类，用以存放 HttpContext 的实现类 HttpContextImpl
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/17           版本号
 */
public class ContextList {
	private static final int MAX_CONTEXTS = 50;
	private LinkedList<HttpContextImpl> list = new LinkedList<>();
	
	ContextList() {
	}
	
	public synchronized void add(HttpContextImpl var1) {
		if (var1.getPath() == null) {
			throw new AssertionError();
		}
		this.list.add(var1);
	}
	
	public synchronized int size() {
		return this.list.size();
	}
	
	synchronized HttpContextImpl findContext(String var1, String var2) {
		return this.findContext(var1, var2, false);
	}
	
	synchronized HttpContextImpl findContext(String protocol, String path, boolean var3) {
		protocol = protocol.toLowerCase();
		String nullPath = "";
		HttpContextImpl httpContextImpl = null;
		Iterator<HttpContextImpl> httpContextIterator = this.list.iterator();
		while (true) {
			HttpContextImpl httpContextImplTmp;
			String httpContextImplTmpPath;
			do {
				do {
					do {
						if (!httpContextIterator.hasNext()) {
							return httpContextImpl;
						}
						
						httpContextImplTmp = httpContextIterator.next();
					} while (!httpContextImplTmp.getProtocol().equals(protocol));
					
					httpContextImplTmpPath = httpContextImplTmp.getPath();
				} while (var3 && !httpContextImplTmpPath.equals(path));
			} while (!var3 && !path.startsWith(httpContextImplTmpPath));
			
			if (httpContextImplTmpPath.length() > nullPath.length()) {
				nullPath = httpContextImplTmpPath;
				httpContextImpl = httpContextImplTmp;
			}
		}
	}
	
	public synchronized void remove(String var1, String var2) throws IllegalArgumentException {
		HttpContextImpl var3 = this.findContext(var1, var2, true);
		if (var3 == null) {
			throw new IllegalArgumentException("cannot remove element from list");
		} else {
			this.list.remove(var3);
		}
	}
	
	public synchronized void remove(HttpContextImpl var1) throws IllegalArgumentException {
		Iterator var2 = this.list.iterator();
		
		HttpContextImpl var3;
		do {
			if (!var2.hasNext()) {
				throw new IllegalArgumentException("no such context in list");
			}
			
			var3 = (HttpContextImpl) var2.next();
		} while (!var3.equals(var1));
		
		this.list.remove(var3);
	}
}