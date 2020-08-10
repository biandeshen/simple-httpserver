package xyz.biandeshen.net.simpleserver.common;


import xyz.biandeshen.net.simpleserver.common.request.HttpRequest;
import xyz.biandeshen.net.simpleserver.common.response.HttpResponse;

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
}