package xyz.biandeshen.net.simpleserver.request;


import xyz.biandeshen.net.simpleserver.response.HttpResponse;

import java.io.IOException;

/**
 * @FileName: HttpHandler
 * @Author: fjp
 * @Date: 2020/7/16 14:10
 * @Description: http请求处理器
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
@SuppressWarnings("ALL")
public interface HttpHandler {
	
	void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
	
	//void handle(HttpExchange httpExchange) throws IOException;
	//
	///**
	// * 默认实现的逻辑 实现 socket 到 httpRequest 与 httpResponse
	// *
	// * @param socket
	// *
	// * @throws IOException
	// */
	//default void handle(Socket socket) throws IOException {
	//	//PrintWriter printWriter;
	//	//// 请求
	//	//HttpRequest httpRequest = new HttpRequest();
	//	//// 响应
	//	//HttpResponse httpResponse;
	//	//
	//	//try {
	//	//	printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
	//	//	httpRequest = HttpRequestParse.parse2HttpRequest(socket.getInputStream());
	//	//	httpResponse = new HttpResponse();
	//	//
	//	//	String method = httpRequest.getMethod();
	//	//	if (Stream.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD)
	//	.noneMatch(method::equals)) {
	//	//		//	响应一个不支持的方法的提示 todo
	//	//		HttpResponse response = HttpResponseBuilder.build2Response(null, "不支持的请求方法!");
	//	//		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	//	//		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	//	//		printWriter.println(response);
	//	//		System.err.println("error response: " + response);
	//	//	}
	//	//	// 执行具体逻辑
	//	//	handle(httpRequest, httpResponse);
	//	//	// 判断响应结果
	//	//	judgeHttpResponse(httpResponse);
	//	//	// 返回响应结果
	//	//	printWriter.println(httpResponse);
	//	//	printWriter.flush();
	//	//} catch (Exception e) {
	//	//	HttpResponse response = HttpResponseBuilder.build2Response(httpRequest, e.toString());
	//	//	response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	//	//	response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	//	//	System.err.println("error response: " + response);
	//	//	e.printStackTrace();
	//	//} finally {
	//	//	try {
	//	//		socket.close();
	//	//	} catch (IOException e) {
	//	//		e.printStackTrace();
	//	//	}
	//	//}
	//}
	//
	//default void judgeHttpResponse(HttpResponse httpResponse) {
	//	// 进行判空操作 即未设置状态码时，默认成功
	//	if (httpResponse.getStatus().isEmpty()) {
	//		httpResponse.setCode(HttpStatus.OK.value());
	//		httpResponse.setStatus(HttpStatus.OK.getReasonPhrase());
	//	}
	//}
	//
	///**
	// * 具体的执行逻辑，用以处理每个处理器
	// *
	// * @param httpRequest
	// * @param httpResponse
	// *
	// * @throws IOException
	// */
	//void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}