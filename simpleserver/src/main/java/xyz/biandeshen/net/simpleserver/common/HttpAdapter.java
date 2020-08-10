package xyz.biandeshen.net.simpleserver.common;

import xyz.biandeshen.net.simpleserver.common.request.HttpRequest;
import xyz.biandeshen.net.simpleserver.common.response.HttpResponse;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @FileName: HttpAdapter
 * @Author: fjp
 * @Date: 2020/7/16 14:44
 * @Description: 适配器
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public interface HttpAdapter {
	
	HttpAdapter create();
	
	void handle(HttpHandler handler, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}