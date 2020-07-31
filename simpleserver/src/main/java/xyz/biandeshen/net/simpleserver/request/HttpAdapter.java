package xyz.biandeshen.net.simpleserver.request;

import java.net.Socket;

/**
 * @FileName: HttpAdapter
 * @Author: fjp
 * @Date: 2020/7/16 14:44
 * @Description: 适配器
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public abstract class HttpAdapter {
	/**
	 * 处理方法
	 */
	public abstract void handle(Socket socket);
}