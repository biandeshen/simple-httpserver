package xyz.biandeshen.net.simpleserver.common.response;

import lombok.Data;
import xyz.biandeshen.net.simpleserver.common.HttpHeaders;
import xyz.biandeshen.net.simpleserver.common.HttpStatus;
import xyz.biandeshen.net.simpleserver.common.HttpVersion;
import xyz.biandeshen.net.simpleserver.common.request.SimpleHttpRequest;
import xyz.biandeshen.net.simpleserver.config.GlobalConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @FileName: HttpResponseBuilder
 * @Author: fjp
 * @Date: 2020/8/5 11:17
 * @Description: 构建响应结果
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/5           版本号
 */
@Data
@Deprecated
public class HttpResponseBuilder {
	//https://www.cnblogs.com/myseries/p/11239662.html
	//＜status-line＞   //状态行  格式：HTTP-Version Status-Code Reason-Phrase CRLF 比如：HTTP/1.1 200 OK
	//
	//＜headers＞   //消息报头
	//
	//＜blank line＞   //空行
	//
	//＜response-body＞    //响应体
	
	//public HttpResponse build2HttpResponse(SocketChannel socketChannel) throws IOException {
	//	HttpResponse httpResponse = new SimpleHttpResponse(socketChannel.socket().getOutputStream());
	//
	//	HttpResponseLine httpResponseLine = new HttpResponseLine();
	//	HttpResponseHeader httpResponseHeader = new HttpResponseHeader();
	//}
	//public static void main(String[] args) {
	//	try {
	//
	//		HttpResponse httpResponse = new SimpleHttpResponse(200, false);
	//		//HttpResponse httpResponse = build2HttpResponse(200, false);
	//		HttpResponse httpResponse1 = new HttpResponse(200, false) {
	//			@Override
	//			public void judgeHttpResponseFormat() throws IOException {
	//				super.judgeHttpResponseFormat();
	//			}
	//		};
	//
	//
	//		SimpleHttpRequest simpleHttpRequest = new SimpleHttpRequest();
	//		//SimpleHttpResponse simpleHttpResponse = new SimpleHttpResponse();
	//
	//		//simpleHttpResponse.judgeHttpResponseFormat();
	//	} catch (Throwable e) {
	//		e.printStackTrace();
	//	}
	//
	//}
	
	//
	//public static HttpResponse build2HttpResponse(int httpStatusCode, boolean isConnClosed) throws IOException {
	//	//＜status-line＞   //状态行
	//	HttpResponseLine httpResponseLine = new HttpResponseLine(HttpVersion.HTTP1_1, httpStatusCode,
	//	                                                         HttpStatus.msg(httpStatusCode));
	//	//＜headers＞   //消息报头
	//	HttpHeaders httpHeaders = new HttpHeaders();
	//	httpHeaders.setDate(ZonedDateTime.now());
	//	httpHeaders.setServer("SimpleHttpServer");
	//	httpHeaders.setConnection(isConnClosed);
	//	HttpResponseHeader httpResponseHeader = new HttpResponseHeader(httpHeaders);
	//
	//	//＜response-body＞ //响应体
	//	HttpResponseBody httpResponseBody = new HttpResponseBody();
	//
	//	HttpResponse httpResponse = new SimpleHttpResponse();
	//	httpResponse.setResponseLine(httpResponseLine);
	//	httpResponse.setResponseHeader(httpResponseHeader);
	//	httpResponse.setResponseBody(httpResponseBody);
	//	return httpResponse;
	//}
	
	//
	///**
	// * 调整响应结果的 body，并转换为适当的 bytebuffer 结果，用以返回正确格式的内容
	// * <p>
	// *
	// * @param httpResponse
	// * 		响应结果
	// */
	//public static void JudgeHttpResponseFormat(HttpResponse httpResponse) throws IOException {
	//	ByteBuffer byteBuffer = httpResponse.getResponseBody().getByteBuffer();
	//	// 给响应的ByteBuffer赋值
	//	if (byteBuffer == null) {
	//		// 响应内容
	//		byte[] body = httpResponse.getResponseBody().getBody();
	//		// 响应内容长度
	//		int bodyLength = body.length;
	//		// 设置 Content-Length 可能为 0
	//		httpResponse.getHeaders().setContentLength(bodyLength);
	//
	//		// 拼接响应状态行
	//		StringBuilder stringBuilder = new StringBuilder(1024);
	//		String httpVersion = httpResponse.getResponseLine().getProtocol().getName();
	//		String httpCode = String.valueOf(httpResponse.getResponseLine().getCode());
	//		String httpReasonPhrase = httpResponse.getResponseLine().getStatus();
	//		stringBuilder.append(httpVersion).append(" ");
	//		stringBuilder.append(httpCode).append(" ");
	//		stringBuilder.append(httpReasonPhrase).append("\r\n");
	//
	//		// 拼接响应头
	//		Map<String, String> headers = httpResponse.getHeaders().getHeaders();
	//		for (Entry<String, String> entry : headers.entrySet()) {
	//			stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\r\n");
	//		}
	//		stringBuilder.append("\r\n");
	//
	//		byte[] statusLineAndHeaders = stringBuilder.toString().getBytes(GlobalConfig.GLOBAL_CHARSET);
	//
	//		byteBuffer = ByteBuffer.allocate(statusLineAndHeaders.length + bodyLength);
	//		byteBuffer.put(statusLineAndHeaders);
	//		byteBuffer.put(body);
	//		byteBuffer.flip();
	//	}
	//}
}