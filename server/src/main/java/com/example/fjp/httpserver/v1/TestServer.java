package com.example.fjp.httpserver.v1;

import com.example.fjp.httpserver.v1.core.AbstractHttpServer;
import com.example.fjp.httpserver.v1.request.AbstractHttpHandler;
import com.example.fjp.httpserver.v1.request.HttpRequest;
import com.example.fjp.httpserver.v1.response.HttpResponse;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @FileName: TestServer
 * @Author: fjp
 * @Date: 2020/7/16 9:02
 * @Description: 服务端的测试类
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public class TestServer {
	public static void main(String[] args) {
		try {
			AbstractHttpServer httpServer = AbstractHttpServer.create(new InetSocketAddress(9527), 128);
			httpServer.createContext("/sss", new TestHandler());
			httpServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class TestHandler extends AbstractHttpHandler {
		
		@Override
		public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
			httpResponse.setResponseBody("<hr>test handler</hr>");
		}
	}
}