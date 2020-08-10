package xyz.biandeshen.net.simpleserver.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: HttpVersion
 * @Author: fjp
 * @Date: 2020/8/3 17:08
 * @Description: HTTP协议版本
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/3           版本号
 */
public enum HttpVersion {// 默认支持 1.1
	HTTP1_0("HTTP/1.0"), HTTP1_1("HTTP/1.1"), HTTP2_0("HTTP/2.0");
	
	private String name;
	
	HttpVersion(String name) {
		this.name = name;
	}
	
	/**
	 * 获取HTTP协议版本
	 *
	 * @return 默认大写格式返回
	 */
	public String getName() {
		return name;
	}
	
	private static final Map<String, HttpVersion> HTTPVERSIONHASHMAP = new HashMap<>(3);
	
	static {
		for (HttpVersion httpVersion : values()) {
			HTTPVERSIONHASHMAP.put(httpVersion.getName(), httpVersion);
		}
	}
	
	/**
	 * 将给定的方法值解析为{@code HttpVersion}。
	 *
	 * @param version
	 * 		方法名称
	 *
	 * @return 对应的 {@code HttpVersion} ，或 {@code null} (如果没有找到)
	 */
	public static HttpVersion resolve(String version) {
		return (version != null ? HTTPVERSIONHASHMAP.get(version) : null);
	}
	
	/**
	 * 确定这个{@code HttpMethod}是否与给定的*方法值匹配。
	 *
	 * @param version
	 * 		方法名称
	 *
	 * @return 如果匹配，{@code true}，否则，{@code false}
	 */
	public boolean matches(String version) {
		return (this == resolve(version));
	}}