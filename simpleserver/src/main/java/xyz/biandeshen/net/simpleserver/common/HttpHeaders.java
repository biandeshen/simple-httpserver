package xyz.biandeshen.net.simpleserver.common;

import xyz.biandeshen.net.simpleserver.util.TimeUtil;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: HttpHeaders
 * @Author: fjp
 * @Date: 2020/7/16 11:16
 * @Description: 自定义Http请求头封装 todo 重写此类
 * https://www.cnblogs.com/wanghuaqiang/p/12093563.html
 * 通过此博客链接可以看到更多的请求头信息
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
@SuppressWarnings("ALL")
public class HttpHeaders {
	private Map<String, String> headers = new HashMap<>(64);
	
	public HttpHeaders() {
	}
	
	public HttpHeaders(Map<String, String> headers) {
		this.headers = headers;
		//map执行赋值操作
		setHost(headers.get("Host"));
		setUserAgent(headers.get("User-Agent"));
		setConnection(Boolean.parseBoolean(headers.get(HttpHeader.Connection.getHeaderName())));
		setAccept(headers.get("Accept"));
		setAcceptCharset(headers.get("Accept-Charset"));
		setAcceptEncoding(headers.get("Accept-Encoding"));
		setAcceptLanguage(headers.get("Accept-Language"));
		setContentLength(Integer.parseInt(headers.getOrDefault("Content-Length", "0")));
		setContentType(headers.get("Content-Type"));
	}
	
	public Map<String, String> getHeaders() {
		return Collections.synchronizedMap(headers);
	}
	
	public void setHeaders(Map<String, String> headers) {
		//map执行赋值操作
		setHost(headers.get("Host"));
		setUserAgent(headers.get("User-Agent"));
		setConnection(Boolean.parseBoolean(headers.get(HttpHeader.Connection.getHeaderName())));
		setAccept(headers.get("Accept"));
		setAcceptCharset(headers.get("Accept-Charset"));
		setAcceptEncoding(headers.get("Accept-Encoding"));
		setAcceptLanguage(headers.get("Accept-Language"));
		setContentLength(Integer.parseInt(headers.getOrDefault("Content-Length", "0")));
		setContentType(headers.get("Content-Type"));
	}
	
	
	/**
	 * 获取 请求的主机信息
	 *
	 * @return host 请求的主机信息
	 */
	public String getHost() {
		return get(HttpHeader.Host.getHeaderName());
	}
	
	/**
	 * 设置 请求的主机信息
	 *
	 * @param host
	 * 		请求的主机信息
	 */
	public void setHost(String host) {
		set(HttpHeader.Host.getHeaderName(), host);
	}
	
	/**
	 * 获取 代理，用来标识代理的浏览器信息 对应HTTP请求中的User-Agent:
	 *
	 * @return agent 代理，用来标识代理的浏览器信息 对应HTTP请求中的User-Agent:
	 */
	public String getUserAgent() {
		return get(HttpHeader.User_Agent.getHeaderName());
	}
	
	/**
	 * 设置 代理，用来标识代理的浏览器信息 对应HTTP请求中的User-Agent:
	 *
	 * @param agent
	 * 		代理，用来标识代理的浏览器信息 对应HTTP请求中的User-Agent:
	 */
	public void setUserAgent(String agent) {
		set(HttpHeader.User_Agent.getHeaderName(), agent);
	}
	
	/**
	 * 获取 对应Accept-Language
	 *
	 * @return language 对应Accept-Language
	 */
	public String getAcceptLanguage() {
		return get(HttpHeader.Accept_Language.getHeaderName());
	}
	
	/**
	 * 设置 对应Accept-Language
	 *
	 * @param language
	 * 		对应Accept-Language
	 */
	public void setAcceptLanguage(String language) {
		set(HttpHeader.Accept_Language.getHeaderName(), language);
	}
	
	/**
	 * 获取 对应HTTP请求中的Accept-Encoding
	 *
	 * @return encoding 对应HTTP请求中的Accept-Encoding
	 */
	public String getAcceptEncoding() {
		return get(HttpHeader.Accept_Encoding.getHeaderName());
	}
	
	/**
	 * 设置 对应HTTP请求中的Accept-Encoding
	 *
	 * @param encoding
	 * 		对应HTTP请求中的Accept-Encoding
	 */
	public void setAcceptEncoding(String encoding) {
		set(HttpHeader.Accept_Encoding.getHeaderName(), encoding);
	}
	
	/**
	 * 获取 请求的字符编码 对应HTTP请求中的Accept-Charset
	 *
	 * @return charset 请求的字符编码 对应HTTP请求中的Accept-Charset
	 */
	public String getAcceptCharset() {
		return get(HttpHeader.Accept_Charset.getHeaderName());
	}
	
	/**
	 * 设置 请求的字符编码 对应HTTP请求中的Accept-Charset
	 *
	 * @param charset
	 * 		请求的字符编码 对应HTTP请求中的Accept-Charset
	 */
	public void setAcceptCharset(String charset) {
		set(HttpHeader.Accept_Charset.getHeaderName(), charset);
	}
	
	/**
	 * 获取 对应HTTP请求中的Accept;
	 *
	 * @return accept 对应HTTP请求中的Accept;
	 */
	public String getAccept() {
		return get(HttpHeader.Accept.getHeaderName());
	}
	
	/**
	 * 设置 对应HTTP请求中的Accept;
	 *
	 * @param accept
	 * 		对应HTTP请求中的Accept;
	 */
	public void setAccept(String accept) {
		set(HttpHeader.Accept.getHeaderName(), accept);
	}
	
	/**
	 * 获取 对应HTTP请求中的contentLength
	 *
	 * @return contentLength 对应HTTP请求中的contentLength
	 */
	public int getContentLength() {
		return Integer.parseInt(get(HttpHeader.Content_Length.getHeaderName()));
	}
	
	/**
	 * 设置 对应HTTP请求中的contentLength
	 *
	 * @param contentLength
	 * 		对应HTTP请求中的contentLength
	 */
	public void setContentLength(int contentLength) {
		set(HttpHeader.Content_Length.getHeaderName(), String.valueOf(contentLength));
	}
	
	/**
	 * 获取 对应HTTP响应中的contentType
	 *
	 * @return contentType 对应HTTP响应中的contentType
	 */
	public String getContentType() {
		return get(HttpHeader.Content_Type.getHeaderName());
	}
	
	/**
	 * 设置 对应HTTP响应中的contentType
	 *
	 * @param contentType
	 * 		对应HTTP响应中的contentType
	 */
	public void setContentType(String contentType) {
		set(HttpHeader.Content_Type.getHeaderName(), contentType);
	}
	
	public String getFirst(String first) {
		List<String> list = Collections.singletonList(this.headers.get(this.normalize(first)));
		return list.get(0);
	}
	
	public void setDate(ZonedDateTime torfc822) {
		set(HttpHeader.Date.getHeaderName(), TimeUtil.toRFC822(torfc822));
	}
	
	public void setDate(String torfc822) {
		set(HttpHeader.Date.getHeaderName(), TimeUtil.toRFC822(TimeUtil.parseRFC822(torfc822)));
	}
	
	public String getDate() {
		return get(HttpHeader.Date.getHeaderName());
	}
	
	
	public void setServer(String server) {
		set(HttpHeader.Server.getHeaderName(), server);
	}
	
	public String getServer() {
		return get(HttpHeader.Server.getHeaderName());
	}
	
	public void setConnection(boolean isConnClosed) {
		set(HttpHeader.Connection.getHeaderName(), String.valueOf(isConnClosed));
	}
	
	public String getConnection() {
		return get(HttpHeader.Connection.getHeaderName());
	}
	
	public void set(String headerName, String headerValue) {
		headers.put(headerName, headerValue);
	}
	
	public String get(String headerName) {
		return headers.get(headerName);
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
				for (int i = 1; i < charLength; ++i) {
					if (charArray[i] >= 'A' && charArray[i] <= 'Z') {
						charArray[i] = (char) (charArray[i] + 32);
					}
				}
				return new String(charArray);
			}
		}
	}
}