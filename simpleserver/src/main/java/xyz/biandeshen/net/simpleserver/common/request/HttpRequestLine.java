package xyz.biandeshen.net.simpleserver.common.request;

import lombok.Data;
import org.apache.commons.collections4.MultiValuedMap;
import xyz.biandeshen.net.simpleserver.common.HttpMethod;
import xyz.biandeshen.net.simpleserver.common.HttpVersion;

import java.util.Map;

/**
 * @FileName: HttpRequestLine
 * @Author: fjp
 * @Date: 2020/8/3 9:01
 * @Description: 自定义请求行
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/3           版本号
 */
@Data
public class HttpRequestLine {
	
	/**
	 * 请求方法
	 */
	private HttpMethod method;
	
	/**
	 * 协议版本  HTTP/1.1
	 */
	private HttpVersion protocol;
	
	/**
	 * 请求URL
	 */
	private String requestURL;
	
	/**
	 * 请求的URI地址 在HTTP请求的第一行的请求方法后面
	 */
	private String requestURI;
	
	/**
	 * 请求字符串
	 */
	private String queryString;
	
	/**
	 * 请求字符串k-v格式
	 */
	private MultiValuedMap<String, String> parameters;
	
	/**
	 * 获取 HttpMethod
	 *
	 * @return HttpMethod
	 */
	public HttpMethod getMethod() {
		return this.method;
	}
	
	/**
	 * 获取 HttpMethodName
	 *
	 * @return HttpMethodName
	 */
	public String getMethodName() {
		return this.method.getName();
	}
	
	/**
	 * 通过 HttpMethod 设置 HttpMethod
	 *
	 * @param httpMethod
	 * 		HttpMethod
	 */
	public void setMethod(HttpMethod httpMethod) {
		this.method = httpMethod;
	}
	
	/**
	 * 通过 HttpMethodName 设置 HttpMethod
	 *
	 * @param httpMethodName
	 * 		HttpMethodName
	 */
	public void setMethod(String httpMethodName) {
		this.method = HttpMethod.resolve(httpMethodName);
	}
}