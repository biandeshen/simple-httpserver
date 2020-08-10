package xyz.biandeshen.net.simpleserver.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;

/**
 * @FileName: HttpResponseBody
 * @Author: fjp
 * @Date: 2020/8/4 8:44
 * @Description: HTTP响应体
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/4           版本号
 */
@Data
public class HttpResponseBody {
	/**
	 * 响应数据 把方法改成私有的方法，且需处理 TODO
	 */
	private byte[] body;
	/**
	 * 待响应的以HTTP/1.1规范返回的响应数据 （！！！谨慎写入，否则将无法正常返回）
	 */
	private ByteBuffer byteBuffer;
	
	public HttpResponseBody() {
	}
	
	public HttpResponseBody(byte[] body) {
		this.body = body;
	}
	
	public HttpResponseBody(byte[] body, ByteBuffer byteBuffer) {
		this.body = body;
		this.byteBuffer = byteBuffer;
	}
}