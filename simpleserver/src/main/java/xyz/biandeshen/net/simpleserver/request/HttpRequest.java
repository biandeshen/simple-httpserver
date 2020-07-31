package xyz.biandeshen.net.simpleserver.request;

import xyz.biandeshen.net.simpleserver.common.HttpHeaders;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: HttpRequest
 * @Author: fjp
 * @Date: 2020/7/16 10:41
 * @Description: 自定义请求报文对应实体类
 * https://blog.csdn.net/kongmin_123/article/details/82154780
 * https://www.cnblogs.com/myseries/p/11239662.html
 * 根据相关协议，完成对http请求报文的实体类映射
 * <p>
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
@SuppressWarnings("ALL")
public class HttpRequest {
	private Map<String, String> parameters = new HashMap<>(8);
	
	void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public Map<String, String> getParameters() {
		return parameters;
	}
	
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
	 * 请求方法
	 */
	private String method;
	/**
	 * 协议版本  HTTP/1.1
	 */
	private String protocol;
	
	/**
	 * 请求URL
	 */
	private String requestURL;
	
	/**
	 * 请求的URI地址 在HTTP请求的第一行的请求方法后面
	 */
	private String requestURI;
	
	/**
	 * 请求报文的请求头
	 */
	private HttpHeaders httpHeaders = new HttpHeaders();
	
	/**
	 * 请求字符串
	 */
	private String queryString;
	
	/**
	 * 请求体
	 */
	private String requestBody;
	
	/**
	 * 请求流
	 */
	private InputStream inputStream;
	
	
	/**
	 * 获取 请求方法
	 *
	 * @return method 请求方法
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * 获取 请求方法
	 *
	 * @return method 请求方法
	 */
	public String getMethod() {
		return this.method;
	}
	
	/**
	 * 设置 请求方法
	 *
	 * @param method
	 * 		请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * 获取 协议版本
	 *
	 * @return protocol 协议版本
	 */
	public String getProtocol() {
		return this.protocol;
	}
	
	/**
	 * 设置 协议版本
	 *
	 * @param protocol
	 * 		协议版本
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * 获取 请求URL
	 *
	 * @return requestURL 请求URL
	 */
	public String getRequestURL() {
		return this.requestURL;
	}
	
	/**
	 * 设置 请求URL
	 *
	 * @param requestURL
	 * 		请求URL
	 */
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	
	/**
	 * 获取 请求的URI地址 在HTTP请求的第一行的请求方法后面
	 *
	 * @return requestURI 请求的URI地址 在HTTP请求的第一行的请求方法后面
	 */
	public String getRequestURI() {
		return this.requestURI;
	}
	
	/**
	 * 设置 请求的URI地址 在HTTP请求的第一行的请求方法后面
	 *
	 * @param requestURI
	 * 		请求的URI地址 在HTTP请求的第一行的请求方法后面
	 */
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	/**
	 * 获取 请求报文的请求头
	 *
	 * @return httpHeaders 请求报文的请求头
	 */
	public HttpHeaders getHttpHeaders() {
		return this.httpHeaders;
	}
	
	/**
	 * 设置 请求报文的请求头
	 *
	 * @param httpHeaders
	 * 		请求报文的请求头
	 */
	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders.getHeaders().putAll(httpHeaders.getHeaders());
	}
	
	/**
	 * 获取 请求体
	 *
	 * @return requestBody 请求体
	 */
	public String getRequestBody() {
		return this.requestBody;
	}
	
	/**
	 * 设置 请求体
	 *
	 * @param requestBody
	 * 		请求体
	 */
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	
	/**
	 * 获取 请求流
	 *
	 * @return inputStream 请求流
	 */
	public InputStream getInputStream() {
		return this.inputStream;
	}
	
	/**
	 * 设置 请求流
	 *
	 * @param inputStream
	 * 		请求流
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * 获取 请求字符串
	 *
	 * @return queryString 请求字符串
	 */
	public String getQueryString() {
		return this.queryString;
	}
	
	/**
	 * 设置 请求字符串
	 *
	 * @param queryString
	 * 		请求字符串
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
}