package xyz.biandeshen.net.simpleserver.exception;

/**
 * @FileName: IllegalRequestException
 * @Author: fjp
 * @Date: 2020/7/31 10:36
 * @Description: 非法的请求访问异常
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/31           版本号
 */
public class IllegalRequestException extends RuntimeException {
	public IllegalRequestException() {
		super();
	}
	
	public IllegalRequestException(String message) {
		super(message);
	}
}