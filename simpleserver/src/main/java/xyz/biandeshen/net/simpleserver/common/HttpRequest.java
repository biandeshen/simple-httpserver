package xyz.biandeshen.net.simpleserver.common;

import java.net.URI;

/**
 * 表示一个HTTP请求消息，由* {@linkplain #getMethod method}和{@linkplain #getURI() uri}组成。
 */
public interface HttpRequest extends HttpMessage {
	
	/**
	 * 返回请求的HTTP方法。
	 *
	 * @return HTTP方法作为 HttpMethod enum 值，或{@code null}
	 * 如果不能解析(例如，在非标准HTTP方法的情况下)
	 */
	HttpMethod getMethod();
	
	/**
	 * 返回请求的URI。
	 *
	 * @return the URI of the request (never {@code null})
	 */
	URI getURI();
	
}