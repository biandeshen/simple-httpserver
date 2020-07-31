package xyz.biandeshen.net.simpleserver.exception;

/**
 * @FileName: ServerInternalEeception
 * @Author: fjp
 * @Date: 2020/7/31 10:41
 * @Description: 服务器内部异常
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/31           版本号
 */
public class ServerInternalException extends RuntimeException {
	public ServerInternalException() {
		super();
	}
	
	public ServerInternalException(String message) {
		super(message);
	}
}