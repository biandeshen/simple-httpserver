package xyz.biandeshen.net.simpleserver.response;

import xyz.biandeshen.net.simpleserver.common.HttpHeaders;
import xyz.biandeshen.net.simpleserver.common.StatusCode;
import xyz.biandeshen.net.simpleserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: HttpResponseBuilder
 * @Author: fjp
 * @Date: 2020/7/16 15:17
 * @Description: 构建自定义HttpResponse
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public class HttpResponseBuilder {
	//https://www.cnblogs.com/myseries/p/11239662.html
	//＜status-line＞   //状态行  格式：HTTP-Version Status-Code Reason-Phrase CRLF 比如：HTTP/1.1 200 OK
	//
	//＜headers＞   //消息报头
	//
	//＜blank line＞   //空行
	//
	//＜response-body＞    //响应体
	
	/**
	 * 构建响应结果
	 *
	 * @param httpRequest
	 * @param response
	 *
	 * @return 响应字符串
	 */
	public static HttpResponse build2Response(HttpRequest httpRequest, String response) {
		HttpResponse httpResponse = new HttpResponse();
		buildStatusLine(httpRequest, httpResponse);
		buildHeaders(httpResponse, response);
		buildBlankLine(httpResponse);
		buildResponseBody(httpResponse, response);
		return httpResponse;
	}
	
	/**
	 * 构建响应结果
	 *
	 * @param httpRequest
	 * @param response
	 *
	 * @return 响应字符串
	 */
	public static String build2ResponseString(HttpRequest httpRequest, String response) {
		HttpResponse httpResponse = new HttpResponse();
		buildStatusLine(httpRequest, httpResponse);
		buildHeaders(httpResponse, response);
		buildBlankLine(httpResponse);
		buildResponseBody(httpResponse, response);
		return httpResponse.toString();
	}
	
	private static void buildResponseBody(HttpResponse httpResponse, String response) {
		httpResponse.setResponseBody(response);
	}
	
	private static void buildBlankLine(HttpResponse httpResponse) {
		// no thing to do
	}
	
	private static void buildHeaders(HttpResponse httpResponse, String response) {
		Map<String, String> headers = new HashMap<>(8);
		headers.put("Content-Type", "application/json");
		headers.put("Content-Length", String.valueOf(response.getBytes().length));
		httpResponse.setHeaders(new HttpHeaders(headers));
	}
	
	private static void buildStatusLine(HttpRequest httpRequest, HttpResponse httpResponse) {
		httpResponse.setCode(StatusCode.HTTP_OK);
		httpResponse.setStatus(StatusCode.msg(StatusCode.HTTP_OK));
		httpResponse.setProtocol(httpRequest.getProtocol());
	}
	
}