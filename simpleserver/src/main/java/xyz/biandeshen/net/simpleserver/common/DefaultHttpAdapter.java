package xyz.biandeshen.net.simpleserver.common;


import xyz.biandeshen.net.simpleserver.common.request.HttpRequest;
import xyz.biandeshen.net.simpleserver.common.response.HttpResponse;

import java.io.IOException;

/**
 * @FileName: AbstractHttpAdapter
 * @Author: fjp
 * @Date: 2020/8/3 16:52
 * @Description: 抽象的适配器
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/3           版本号
 */
public class DefaultHttpAdapter implements HttpAdapter {
	
	@Override
	public HttpAdapter create() {
		return new DefaultHttpAdapter();
	}
	
	@Override
	public void handle(HttpHandler handler, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		handler.handle(httpRequest, httpResponse);
	}
}