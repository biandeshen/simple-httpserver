package xyz.biandeshen.net.simpleserver.common.response;

/**
 * @FileName: HttpInputMessage
 * @Author: fjp
 * @Date: 2020/7/31 17:00
 * @Description: History:
 * <author>          <time>          <version>
 * fjp           2020/7/31           版本号
 */

import xyz.biandeshen.net.simpleserver.common.HttpMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 表示一个HTTP输出消息，由{@linkplain #getHeaders() header}
 * 和一个可读的{@linkplain #getBody() body}组成。
 * 通常由服务器端一个HTTP响应句柄实现，或客户端一个HTTP请求句柄实现
 */
public interface HttpOutputMessage extends HttpMessage {
	
	/**
	 * 以输出流的形式返回消息体。
	 *
	 * @return THE INPUT STREAM BODY (NEVER {@CODE NULL})
	 *
	 * @throws IOException
	 * 		以防I/O错误
	 */
	OutputStream getBody() throws IOException;
}