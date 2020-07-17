package com.example.fjp.v1.request;

import com.example.fjp.v1.response.HttpResponse;

import java.io.IOException;

/**
 * @FileName: DispatherHttpHandler
 * @Author: fjp
 * @Date: 2020/7/17 14:47
 * @Description: 所有待执行的任务的处理器，每个要执行的任务都需要继承这个类 类似于 Servlet
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/17           版本号
 */
@SuppressWarnings("ALL")
public abstract class AbstractHttpHandler implements HttpHandler {
	
	public abstract void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}