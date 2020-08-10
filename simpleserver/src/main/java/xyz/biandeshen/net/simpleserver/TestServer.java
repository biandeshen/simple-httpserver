package xyz.biandeshen.net.simpleserver;

import xyz.biandeshen.net.simpleserver.common.DefaultHttpAdapter;
import xyz.biandeshen.net.simpleserver.common.HttpAdapter;
import xyz.biandeshen.net.simpleserver.common.HttpHandler;
import xyz.biandeshen.net.simpleserver.common.request.HttpRequest;
import xyz.biandeshen.net.simpleserver.common.request.SimpleHttpRequest;
import xyz.biandeshen.net.simpleserver.common.response.HttpResponse;
import xyz.biandeshen.net.simpleserver.core.HttpServer;

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
		HttpServer httpServer = null;
		try {
			httpServer = HttpServer.create(new InetSocketAddress(9527), 128);
			httpServer.createContext("/test", new TestHandler());
			//HttpAdapter httpAdapter = new DefaultHttpAdapter();
			httpServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class TestHandler implements HttpHandler {
		@Override
		public void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
			httpResponse.getResponseBody().setBody("测试用的处理器！".getBytes());
			// 测试 multipart/form-data
			//Map<String, MimeData> mimeMap = httpRequest.getRequestBody().getMimeMap();
			//for (Entry<String, MimeData> stringMimeDataEntry : mimeMap.entrySet()) {
			//	System.out.println("stringMimeDataEntry = " + stringMimeDataEntry.getKey()+"\t" + new String
			//	(stringMimeDataEntry.getValue().getData()));
			//}
			// 测试 application/x-www-form-urlencoded
			String photo = ((SimpleHttpRequest) httpRequest).getParameter("photo");
			System.out.println("photo = " + photo);
		}
	}
}