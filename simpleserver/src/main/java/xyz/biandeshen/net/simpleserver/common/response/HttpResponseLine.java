package xyz.biandeshen.net.simpleserver.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.biandeshen.net.simpleserver.common.HttpVersion;

/**
 * @FileName: HttpResponseLine
 * @Author: fjp
 * @Date: 2020/8/3 17:07
 * @Description: History:
 * <author>          <time>          <version>
 * fjp           2020/8/3           版本号
 */
@Data
public class HttpResponseLine {
	/**
	 * 协议版本 HTTP/1.1
	 */
	private HttpVersion protocol;
	
	/**
	 * 响应码 httpstatus.value
	 */
	private int code;
	
	/**
	 * 状态行响应原因 httpstatus.reasonPhrase
	 */
	private String status;
	
	private HttpResponseLine() {
	}
	
	public HttpResponseLine(HttpVersion protocol, int code, String status) {
		this.protocol = protocol;
		this.code = code;
		this.status = status;
	}
}