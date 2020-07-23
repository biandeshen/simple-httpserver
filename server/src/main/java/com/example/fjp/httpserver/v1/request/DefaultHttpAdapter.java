package com.example.fjp.httpserver.v1.request;

import com.example.fjp.httpserver.v1.common.Code;
import com.example.fjp.httpserver.v1.common.HttpMethod;
import com.example.fjp.httpserver.v1.response.HttpResponse;
import com.example.fjp.httpserver.v1.response.HttpResponseBuilder;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @FileName: DefaultHttpAdapter
 * @Author: fjp
 * @Date: 2020/7/16 14:48
 * @Description: 默认的httpadapter
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public class DefaultHttpAdapter extends HttpAdapter {
	
	@Override
	public void handle(Socket socket) {
		try {
			PrintWriter printWriter;
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			
			HttpRequest httpRequest = HttpRequestParse.parse2HttpRequest(socket.getInputStream());
			
			String method = httpRequest.getMethod();
			if (!method.equals(HttpMethod.GET) && !method.equals(HttpMethod.POST) && !method.equals(HttpMethod.PUT) && !method.equals(HttpMethod.DELETE) && !method.equals(HttpMethod.HEAD)) {
				//	响应一个不支持的方法的提示 todo
				//HttpResponseBuilder.build2Response()
			}
			try {
				//	处理请求结果 并进行响应
				String result = "响应请求结果: requestBody = " + httpRequest.getRequestBody();
				String responseString = HttpResponseBuilder.build2ResponseString(httpRequest, result);
				printWriter.println(responseString);
			} catch (Exception e) {
				HttpResponse response = HttpResponseBuilder.build2Response(httpRequest, e.toString());
				response.setCode(Code.HTTP_INTERNAL_ERROR);
				response.setStatus(Code.msg(Code.HTTP_INTERNAL_ERROR));
				printWriter.println(response.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}