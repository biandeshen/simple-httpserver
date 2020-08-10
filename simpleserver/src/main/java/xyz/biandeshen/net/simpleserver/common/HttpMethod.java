package xyz.biandeshen.net.simpleserver.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: HttpMethod
 * @Author: fjp
 * @Date: 2020/7/16 12:11
 * @Description: 常用的http请求方法
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public enum HttpMethod {
	//请求方法
	/**
	 * 向服务器请求一个文件
	 */
	GET("GET"),
	/**
	 * 向服务器发送数据让服务器处理
	 */
	POST("POST"),
	/**
	 * 向服务器方式数据并存储在服务器
	 */
	PUT("PUT"),
	/**
	 * 检查一个对象是否存在
	 */
	HEAD("HEAD"),
	/**
	 * 从服务器上删除一个文件
	 */
	DELETE("DELETE"),
	/**
	 * 对通道提供支持
	 */
	CONNECT("CONNECT"),
	/**
	 * 跟踪到服务器的路径
	 */
	TRACE("TRACE"),
	/**
	 * 查询服务器的性能
	 */
	OPTIONS("OPTIONS");
	
	/**
	 * 方法名称
	 */
	private String name;
	
	private static final Map<String, HttpMethod> mappings = new HashMap<String, HttpMethod>(8);
	
	static {
		for (HttpMethod httpMethod : values()) {
			mappings.put(httpMethod.getName(), httpMethod);
		}
	}
	
	/**
	 * 将给定的方法值解析为{@code HttpMethod}。
	 *
	 * @param method
	 * 		方法名称
	 *
	 * @return 对应的 {@code HttpMethod} ，或 {@code null} (如果没有找到)
	 */
	public static HttpMethod resolve(String method) {
		return (method != null ? mappings.get(method.toUpperCase()) : null);
	}
	
	/**
	 * 确定这个{@code HttpMethod}是否与给定的*方法值匹配。
	 *
	 * @param method
	 * 		方法名称
	 *
	 * @return 如果匹配，{@code true}，否则，{@code false}
	 */
	public boolean matches(String method) {
		return (this == resolve(method));
	}
	
	
	HttpMethod(String name) {
		this.name = name;
	}
	
	/**
	 * 获取 方法名称
	 *
	 * @return name 方法名称
	 */
	public String getName() {
		return this.name;
	}
	
}