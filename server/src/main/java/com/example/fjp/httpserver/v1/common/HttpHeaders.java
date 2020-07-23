package com.example.fjp.httpserver.v1.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: HttpHeaders
 * @Author: fjp
 * @Date: 2020/7/16 11:16
 * @Description: 自定义Http请求头封装
 * https://www.cnblogs.com/wanghuaqiang/p/12093563.html
 * 通过此博客链接可以看到更多的请求头信息
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public class HttpHeaders {
	private Map<String, String> headers = new HashMap<>(8);
	
	public HttpHeaders() {
	}
	
	public HttpHeaders(Map<String, String> headers) {
		this.headers = headers;
		//map执行赋值操作
		setHost(headers.get("Host"));
		setConnection(headers.get("Connection"));
		setAgent(headers.get("User-Agent"));
		setAccept(headers.get("Accept"));
		setCharset(headers.get("Accept-Charset"));
		setEncoding(headers.get("Accept-Encoding"));
		setLanguage(headers.get("Accept-Language"));
		setContentLength(Integer.parseInt(headers.getOrDefault("Content-Length", "0")));
	}
	
	/**
	 * 请求的主机信息
	 */
	private String host;
	/**
	 * Http请求连接状态信息 对应HTTP请求中的Connection
	 */
	private String connection;
	/**
	 * 代理，用来标识代理的浏览器信息 ,对应HTTP请求中的User-Agent:
	 */
	private String agent;
	/**
	 * 对应Accept-Language
	 */
	private String language;
	/**
	 * 对应HTTP请求中的Accept-Encoding
	 */
	private String encoding;
	/**
	 * 请求的字符编码 对应HTTP请求中的Accept-Charset
	 */
	private String charset;
	/**
	 * 对应HTTP请求中的Accept
	 */
	private String accept;
	/**
	 * 对应HTTP请求中的contentLength
	 */
	private int contentLength;
	
	
	/**
	 * 对应HTTP响应中的contentType
	 */
	private String contentType;
	
	
	public Map<String, String> getHeaders() {
		return Collections.synchronizedMap(headers);
	}
	
	public void setHeaders(Map<String, String> headers) {
		//throw new UnsupportedOperationException("headers is immutable!");
		//map执行赋值操作
		setHost(headers.get("Host"));
		setConnection(headers.get("Connection"));
		setAgent(headers.get("User-Agent"));
		setAccept(headers.get("Accept"));
		setCharset(headers.get("Accept-Charset"));
		setEncoding(headers.get("Accept-Encoding"));
		setLanguage(headers.get("Accept-Language"));
		setContentLength(Integer.parseInt(headers.getOrDefault("Content-Length", "0")));
		
		setContentType(headers.get("Content-Type"));
	}
	
	
	/**
	 * 获取 请求的主机信息
	 *
	 * @return host 请求的主机信息
	 */
	public String getHost() {
		return this.host;
	}
	
	/**
	 * 设置 请求的主机信息
	 *
	 * @param host
	 * 		请求的主机信息
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * 获取 代理，用来标识代理的浏览器信息 对应HTTP请求中的User-Agent:
	 *
	 * @return agent 代理，用来标识代理的浏览器信息 对应HTTP请求中的User-Agent:
	 */
	public String getAgent() {
		return this.agent;
	}
	
	/**
	 * 设置 代理，用来标识代理的浏览器信息 对应HTTP请求中的User-Agent:
	 *
	 * @param agent
	 * 		代理，用来标识代理的浏览器信息 对应HTTP请求中的User-Agent:
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	/**
	 * 获取 对应Accept-Language
	 *
	 * @return language 对应Accept-Language
	 */
	public String getLanguage() {
		return this.language;
	}
	
	/**
	 * 设置 对应Accept-Language
	 *
	 * @param language
	 * 		对应Accept-Language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * 获取 对应HTTP请求中的Accept-Encoding
	 *
	 * @return encoding 对应HTTP请求中的Accept-Encoding
	 */
	public String getEncoding() {
		return this.encoding;
	}
	
	/**
	 * 设置 对应HTTP请求中的Accept-Encoding
	 *
	 * @param encoding
	 * 		对应HTTP请求中的Accept-Encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * 获取 请求的字符编码 对应HTTP请求中的Accept-Charset
	 *
	 * @return charset 请求的字符编码 对应HTTP请求中的Accept-Charset
	 */
	public String getCharset() {
		return this.charset;
	}
	
	/**
	 * 设置 请求的字符编码 对应HTTP请求中的Accept-Charset
	 *
	 * @param charset
	 * 		请求的字符编码 对应HTTP请求中的Accept-Charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	/**
	 * 获取 对应HTTP请求中的Accept;
	 *
	 * @return accept 对应HTTP请求中的Accept;
	 */
	public String getAccept() {
		return this.accept;
	}
	
	/**
	 * 设置 对应HTTP请求中的Accept;
	 *
	 * @param accept
	 * 		对应HTTP请求中的Accept;
	 */
	public void setAccept(String accept) {
		this.accept = accept;
	}
	
	/**
	 * 获取 对应HTTP请求中的contentLength
	 *
	 * @return contentLength 对应HTTP请求中的contentLength
	 */
	public int getContentLength() {
		return this.contentLength;
	}
	
	/**
	 * 设置 对应HTTP请求中的contentLength
	 *
	 * @param contentLength
	 * 		对应HTTP请求中的contentLength
	 */
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	
	/**
	 * 获取 Http请求连接状态信息 对应HTTP请求中的Connection
	 *
	 * @return connection Http请求连接状态信息 对应HTTP请求中的Connection
	 */
	public String getConnection() {
		return this.connection;
	}
	
	/**
	 * 设置 Http请求连接状态信息 对应HTTP请求中的Connection
	 *
	 * @param connection
	 * 		Http请求连接状态信息 对应HTTP请求中的Connection
	 */
	public void setConnection(String connection) {
		this.connection = connection;
	}
	
	/**
	 * 获取 对应HTTP响应中的contentType
	 *
	 * @return contentType 对应HTTP响应中的contentType
	 */
	public String getContentType() {
		return this.contentType;
	}
	
	/**
	 * 设置 对应HTTP响应中的contentType
	 *
	 * @param contentType
	 * 		对应HTTP响应中的contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	public String getFirst(String first) {
		List<String> list = Collections.singletonList(this.headers.get(this.normalize(first)));
		return list.get(0);
	}
	
	private String normalize(String character) {
		if (character == null) {
			return null;
		} else {
			int charLength = character.length();
			if (charLength == 0) {
				return character;
			} else {
				char[] charArray = character.toCharArray();
				if (charArray[0] >= 'a' && charArray[0] <= 'z') {
					charArray[0] = (char) (charArray[0] - 32);
				}
				
				for (int var4 = 1; var4 < charLength; ++var4) {
					if (charArray[var4] >= 'A' && charArray[var4] <= 'Z') {
						charArray[var4] = (char) (charArray[var4] + 32);
					}
				}
				
				return new String(charArray);
			}
		}
	}
}