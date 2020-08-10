package xyz.biandeshen.net.simpleserver.common.request;

import lombok.Data;
import xyz.biandeshen.net.simpleserver.common.HttpMessage;
import xyz.biandeshen.net.simpleserver.common.HttpMethod;

import java.net.URI;

/**
 * @author fjp
 * @Title: HttpRequest
 * @Description: 表示一个HTTP请求消息，由* {@linkplain #getHttpMethod method}和{@linkplain #getURI() uri}组成。
 * @date 2020/8/3 8:49
 */
@Data
public abstract class HttpRequest implements HttpInputMessage {
	
	// GET /sss HTTP/1.1   -----> 1. 请求方法  2. 请求 URL  3. HTTP协议及版本
	// Host: 10.10.12.109:9527
	// Connection: keep-alive
	// Cache-Control: max-age=0
	// Upgrade-Insecure-Requests: 1
	// User-Agent: Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97
	// Safari/537.36
	// Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,
	// application/signed-exchange;v=b3;q=0.9
	// Accept-Encoding: gzip, deflate
	// Accept-Language: zh,zh-CN;q=0.9
	// -----> 以上部分为 4. 请求头
	// 其余请求头以下的数据则为 -----> 5. 报文体
	
	/*即大致结构如下*/
	//＜request-line＞ //请求行
	//
	//＜headers＞ //首部行
	//
	//＜blank line＞ //空行
	//
	//＜request-body＞ //请求体
	
	/**
	 * 返回请求的HTTP方法。
	 *
	 * @return HTTP方法作为 HttpMethod enum 值，或{@code null}
	 * 如果不能解析(例如，在非标准HTTP方法的情况下)
	 */
	abstract HttpMethod getHttpMethod();
	
	/**
	 * 返回请求的URI。
	 *
	 * @return the URI of the request (never {@code null})
	 */
	abstract URI getURI();
	
	
	private HttpRequestLine requestLine;
	private HttpRequestHeader requestHeader;
	private HttpRequestBody requestBody;
	
	public HttpRequest() {
		this.requestLine = new HttpRequestLine();
		this.requestHeader = new HttpRequestHeader();
		this.requestBody = new HttpRequestBody();
	}
}