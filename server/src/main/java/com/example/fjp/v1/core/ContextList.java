package com.example.fjp.v1.core;


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
	//TODO 需要看看
	static final int MAX_CONTEXTS = 50;
	LinkedList<HttpContextImpl> list = new LinkedList<>();
	
	ContextList() {
	}
	
	public synchronized void add(HttpContextImpl var1) {
		assert var1.getPath() != null;
		
		this.list.add(var1);
	}
	
	public synchronized int size() {
		return this.list.size();
	}
	
	synchronized HttpContextImpl findContext(String var1, String var2) {
		return this.findContext(var1, var2, false);
	}
	
	synchronized HttpContextImpl findContext(String var1, String var2, boolean var3) {
		var1 = var1.toLowerCase();
		String var4 = "";
		HttpContextImpl var5 = null;
		Iterator var6 = this.list.iterator();
		
		while (true) {
			HttpContextImpl var7;
			String var8;
			do {
				do {
					do {
						if (!var6.hasNext()) {
							return var5;
						}
						
						var7 = (HttpContextImpl) var6.next();
					} while (!var7.getProtocol().equals(var1));
					
					var8 = var7.getPath();
				} while (var3 && !var8.equals(var2));
			} while (!var3 && !var2.startsWith(var8));
			
			if (var8.length() > var4.length()) {
				var4 = var8;
				var5 = var7;
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