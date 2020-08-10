package xyz.biandeshen.net.simpleserver.common.response;

import lombok.Data;
import xyz.biandeshen.net.simpleserver.common.HttpHeader;
import xyz.biandeshen.net.simpleserver.common.HttpHeaders;

/**
 * @FileName: HttpRequestHeader
 * @Author: fjp
 * @Date: 2020/8/3 8:55
 * @Description: 自定义请求的请求头
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/3           版本号
 */
@Data
public class HttpResponseHeader {
	private HttpHeaders httpHeaders;
	
	public String getContentType() {
		return httpHeaders.getHeaders().get(HttpHeader.Content_Type.getHeaderName());
	}
	
	public int getContentLength() {
		return Integer.parseInt(httpHeaders.getHeaders().getOrDefault(HttpHeader.Content_Length.getHeaderName(), "0"));
	}
	
	private HttpResponseHeader() {
	}
	
	public HttpResponseHeader(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}
}