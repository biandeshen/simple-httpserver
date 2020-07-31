package xyz.biandeshen.net.simpleserver.common;

/**
 * @FileName: HttpMessage
 * @Author: fjp
 * @Date: 2020/7/31 16:57
 * @Description: 表示HTTP请求和响应消息的基本接口。*由{@link HttpHeaders}组成，可通过{@link #getHeaders()}检索。
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/31           版本号
 */
public interface HttpMessage {
	/** 返回此消息的头。@return相应的HttpHeaders对象(never {@code null}) */
	
	HttpHeaders getHeaders();
}