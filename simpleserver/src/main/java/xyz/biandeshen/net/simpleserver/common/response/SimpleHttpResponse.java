package xyz.biandeshen.net.simpleserver.common.response;

import xyz.biandeshen.net.simpleserver.common.HttpHeaders;
import xyz.biandeshen.net.simpleserver.common.HttpStatus;
import xyz.biandeshen.net.simpleserver.util.GlobalPropertiesUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * @FileName: SimpleHttpResponse
 * @Author: fjp
 * @Date: 2020/8/3 16:13
 * @Description: 自定义 SimpleHttpRequest
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/3           版本号
 */
public class SimpleHttpResponse extends HttpResponse implements HttpOutputMessage, HttpServletResponse {
	
	//https://www.cnblogs.com/myseries/p/11239662.html
	//＜status-line＞   //状态行  格式：HTTP-Version Status-Code Reason-Phrase CRLF 比如：HTTP/1.1 200 OK
	//
	//＜headers＞   //消息报头
	//
	//＜blank line＞   //空行
	//
	//＜response-body＞    //响应体
	
	
	public SimpleHttpResponse() {
		this(HttpStatus.HTTP_OK, false);
	}
	
	//public SimpleHttpResponse(OutputStream outputStream) {
	//	this.outputStream = outputStream;
	//}
	
	public SimpleHttpResponse(int httpStatusCode, boolean isConnClosed) {
		super(httpStatusCode, isConnClosed);
	}
	
	/**
	 * 输出流
	 */
	private OutputStream outputStream;
	
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	@Override
	public void addCookie(Cookie cookie) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean containsHeader(String name) {
		return false;
	}
	
	@Override
	public String encodeURL(String url) {
		return null;
	}
	
	@Override
	public String encodeRedirectURL(String url) {
		return null;
	}
	
	@Override
	public String encodeUrl(String url) {
		return null;
	}
	
	@Override
	public String encodeRedirectUrl(String url) {
		return null;
	}
	
	@Override
	public void sendError(int sc, String msg) throws IOException {
	
	}
	
	@Override
	public void sendError(int sc) throws IOException {
	
	}
	
	@Override
	public void sendRedirect(String location) throws IOException {
	
	}
	
	@Override
	public void setDateHeader(String name, long date) {
	
	}
	
	@Override
	public void addDateHeader(String name, long date) {
	
	}
	
	@Override
	public void setHeader(String name, String value) {
	
	}
	
	@Override
	public void addHeader(String name, String value) {
	
	}
	
	@Override
	public void setIntHeader(String name, int value) {
	
	}
	
	@Override
	public void addIntHeader(String name, int value) {
	
	}
	
	@Override
	public void setStatus(int sc) {
	
	}
	
	@Override
	public void setStatus(int sc, String sm) {
	
	}
	
	@Override
	public int getStatus() {
		return 0;
	}
	
	@Override
	public String getHeader(String name) {
		return super.getResponseHeader().getHttpHeaders().getHeaders().get(name);
	}
	
	@Override
	public Collection<String> getHeaders(String name) {
		return Collections.singleton(super.getResponseHeader().getHttpHeaders().getHeaders().get(name));
	}
	
	@Override
	public Collection<String> getHeaderNames() {
		return super.getResponseHeader().getHttpHeaders().getHeaders().keySet();
	}
	
	@Override
	public String getCharacterEncoding() {
		return super.getResponseHeader().getHttpHeaders().getAcceptEncoding();
	}
	
	@Override
	public String getContentType() {
		return super.getResponseHeader().getContentType();
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return (ServletOutputStream) getBody();
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(new OutputStreamWriter(this.getOutputStream(), GlobalPropertiesUtil.getProperty("global"
				                                                                                                       + "-encoding")));
	}
	
	@Override
	public void setCharacterEncoding(String charset) {
		super.getResponseHeader().getHttpHeaders().setAcceptEncoding(charset);
	}
	
	@Override
	public void setContentLength(int len) {
		super.getResponseHeader().getHttpHeaders().setContentLength(len);
	}
	
	@Override
	public void setContentLengthLong(long len) {
		super.getResponseHeader().getHttpHeaders().setContentLength(Math.toIntExact(len));
	}
	
	@Override
	public void setContentType(String type) {
		super.getResponseHeader().getHttpHeaders().setContentType(type);
	}
	
	@Override
	public void setBufferSize(int size) {
	
	}
	
	@Override
	public int getBufferSize() {
		return 0;
	}
	
	@Override
	public void flushBuffer() throws IOException {
	
	}
	
	@Override
	public void resetBuffer() {
	
	}
	
	@Override
	public boolean isCommitted() {
		return false;
	}
	
	@Override
	public void reset() {
	
	}
	
	@Override
	public void setLocale(Locale loc) {
	
	}
	
	@Override
	public Locale getLocale() {
		return null;
	}
	
	@Override
	public OutputStream getBody() throws IOException {
		return outputStream;
	}
	
	@Override
	public HttpHeaders getHeaders() {
		return super.getHeaders();
	}
	
	@Override
	public HttpResponseLine getResponseLine() {
		return super.getResponseLine();
	}
	
	@Override
	public void setResponseLine(HttpResponseLine responseLine) {
		super.setResponseLine(responseLine);
	}
	
	@Override
	public HttpResponseHeader getResponseHeader() {
		return super.getResponseHeader();
	}
	
	@Override
	public void setResponseHeader(HttpResponseHeader responseHeader) {
		super.setResponseHeader(responseHeader);
	}
	
	@Override
	public HttpResponseBody getResponseBody() {
		return super.getResponseBody();
	}
	
	@Override
	public void setResponseBody(HttpResponseBody responseBody) {
		super.setResponseBody(responseBody);
	}
}