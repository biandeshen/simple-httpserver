package com.example.fjp.v1.response;

import com.example.fjp.v1.common.HttpHeaders;

import java.util.Map;

/**
 * @FileName: HttpResponse
 * @Author: fjp
 * @Date: 2020/7/16 14:58
 * @Description: 自定义封装的HttpResponse
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public class HttpResponse {
	//https://www.cnblogs.com/myseries/p/11239662.html
	//＜status-line＞   //状态行  格式：HTTP-Version Status-Code Reason-Phrase CRLF 比如：HTTP/1.1 200 OK
	//
	//＜headers＞   //消息报头
	//
	//＜blank line＞   //空行
	//
	//＜response-body＞    //响应体
	
	
	/**
	 * 协议版本 HTTP/1.1
	 */
	private String protocol;
	
	/**
	 * 响应码 httpstatus.value
	 */
	private int code;
	
	/**
	 * 状态行响应原因 httpstatus.reasonPhrase
	 */
	private String status;
	
	/**
	 * 消息报头
	 */
	private HttpHeaders headers;
	
	/**
	 * 响应体
	 */
	private String responseBody;
	
	
	/**
	 * 获取 协议版本 HTTP1.1
	 *
	 * @return protocol 协议版本 HTTP1.1
	 */
	public String getProtocol() {
		return this.protocol;
	}
	
	/**
	 * 设置 协议版本 HTTP1.1
	 *
	 * @param protocol
	 * 		协议版本 HTTP1.1
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * 获取 响应码 httpstatus.value
	 *
	 * @return code 响应码 httpstatus.value
	 */
	public int getCode() {
		return this.code;
	}
	
	/**
	 * 设置 响应码 httpstatus.value
	 *
	 * @param code
	 * 		响应码 httpstatus.value
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
	/**
	 * 获取 状态行响应原因 httpstatus.reasonPhrase
	 *
	 * @return status 状态行响应原因 httpstatus.reasonPhrase
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * 设置 状态行响应原因 httpstatus.reasonPhrase
	 *
	 * @param status
	 * 		状态行响应原因 httpstatus.reasonPhrase
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 获取 消息报头
	 *
	 * @return headers 消息报头
	 */
	public HttpHeaders getHeaders() {
		return this.headers;
	}
	
	/**
	 * 设置 消息报头
	 *
	 * @param headers
	 * 		消息报头
	 */
	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}
	
	/**
	 * 获取 响应体
	 *
	 * @return responseBody 响应体
	 */
	public String getResponseBody() {
		return this.responseBody;
	}
	
	/**
	 * 设置 响应体
	 *
	 * @param responseBody
	 * 		响应体
	 */
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb;
		sb = new StringBuilder();
		// 状态行
		sb.append(protocol);
		sb.append(" ");
		sb.append(code);
		sb.append(" ");
		sb.append(status);
		sb.append("\n");
		// 消息头
		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.getHeaders().entrySet()) {
				sb.append(entry.getKey());
				sb.append(":");
				sb.append(entry.getValue());
				sb.append("\n");
			}
		}
		// 空行
		sb.append("\n");
		// 响应体
		sb.append(responseBody);
		return sb.toString();
	}
}