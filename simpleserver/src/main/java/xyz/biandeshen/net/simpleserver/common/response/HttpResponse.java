package xyz.biandeshen.net.simpleserver.common.response;

import lombok.Data;
import xyz.biandeshen.net.simpleserver.common.HttpHeaders;
import xyz.biandeshen.net.simpleserver.common.HttpStatus;
import xyz.biandeshen.net.simpleserver.common.HttpVersion;
import xyz.biandeshen.net.simpleserver.config.GlobalConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author fjp
 * @Title: HttpRequest
 * @Description: 表示一个HTTP响应消息
 * @date 2020/8/3 8:49
 */
@Data
public abstract class HttpResponse implements HttpOutputMessage {
	private HttpResponseLine responseLine = null;
	private HttpResponseHeader responseHeader = null;
	private HttpResponseBody responseBody = null;
	
	@Override
	public HttpHeaders getHeaders() {
		return responseHeader.getHttpHeaders();
	}
	
	/**
	 * 调整响应结果的 body，并转换为适当的 bytebuffer 结果，用以返回正确格式的内容
	 * <p>
	 * <p>
	 * //* @param httpResponse
	 * //* 		响应结果
	 */
	public void judgeHttpResponseFormat() throws IOException {
		ByteBuffer byteBuffer = this.responseBody.getByteBuffer();
		// 即若是byteBuffer已经存在值，则不再进行操作
		if (byteBuffer == null) {
			// 响应内容
			byte[] body = this.responseBody.getBody();
			// 响应内容长度
			int bodyLength = body.length;
			// 设置 Content-Length 可能为 0
			this.responseHeader.getHttpHeaders().setContentLength(bodyLength);
			
			// 拼接响应状态行
			StringBuilder stringBuilder = new StringBuilder(1024);
			String httpVersion = this.responseLine.getProtocol().getName();
			String httpCode = String.valueOf(this.responseLine.getCode());
			String httpReasonPhrase = this.responseLine.getStatus();
			stringBuilder.append(httpVersion).append(" ");
			stringBuilder.append(httpCode).append(" ");
			stringBuilder.append(httpReasonPhrase).append("\r\n");
			
			// 拼接响应头
			this.responseHeader.getHttpHeaders().setDate(ZonedDateTime.now());
			if (responseHeader.getContentType() == null) {
				// 为空时，默认响应头为text/html;charset= GlobalConfig.GLOBAL_CHARSET
				this.responseHeader.getHttpHeaders().setContentType("text/html;charset=" + GlobalConfig.GLOBAL_CHARSET);
			}
			Map<String, String> headers = this.responseHeader.getHttpHeaders().getHeaders();
			for (Entry<String, String> entry : headers.entrySet()) {
				stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\r\n");
			}
			stringBuilder.append("\r\n");
			
			// 给响应的ByteBuffer赋值
			byte[] statusLineAndHeaders = stringBuilder.toString().getBytes(GlobalConfig.GLOBAL_CHARSET);
			// 初始化
			byteBuffer = ByteBuffer.allocate(statusLineAndHeaders.length + bodyLength);
			byteBuffer.put(statusLineAndHeaders);
			byteBuffer.put(body);
			byteBuffer.flip();
		}
		this.responseBody.setByteBuffer(byteBuffer);
	}
	
	public HttpResponse() {
	
	}
	
	public HttpResponse(int httpStatusCode, boolean isConnClosed) {
		//＜status-line＞   //状态行
		//＜headers＞   //消息报头
		setHttpCodeAndClosed(httpStatusCode, isConnClosed);
		//＜response-body＞ //响应体
		responseBody = new HttpResponseBody(new byte[0]);
	}
	
	
	public void setHttpStatusCode(int httpStatusCode) {
		//＜status-line＞   //状态行
		this.setResponseLine(new HttpResponseLine(HttpVersion.HTTP1_1, httpStatusCode,
		                                          HttpStatus.msg(httpStatusCode)));
	}
	
	public void setHasConectClosed(boolean isConnClosed) {
		//＜headers＞   //消息报头
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setServer("SimpleHttpServer");
		httpHeaders.setConnection(isConnClosed);
		this.setResponseHeader(new HttpResponseHeader(httpHeaders));
	}
	
	public void setHttpCodeAndClosed(int httpStatusCode, boolean isConnClosed) {
		setHttpStatusCode(httpStatusCode);
		setHasConectClosed(isConnClosed);
	}
}