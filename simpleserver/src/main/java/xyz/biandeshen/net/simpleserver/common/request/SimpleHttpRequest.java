package xyz.biandeshen.net.simpleserver.common.request;

import org.apache.commons.collections4.CollectionUtils;
import xyz.biandeshen.net.simpleserver.common.HttpHeaders;
import xyz.biandeshen.net.simpleserver.common.HttpMethod;
import xyz.biandeshen.net.simpleserver.util.GlobalPropertiesUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.URI;
import java.security.Principal;
import java.util.*;

/**
 * @FileName: ServletHttpRequest
 * @Author: fjp
 * @Date: 2020/8/3 14:42
 * @Description: 自定义 SimpleHttpRequest
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/3           版本号
 */
public class SimpleHttpRequest extends HttpRequest implements HttpInputMessage, HttpServletRequest {
	// GET /sss HTTP/1.1   -----> 1. 请求方法  2. 请求 URL  3. HTTP协议及版本
	// Host: 10.10.12.109:9527
	// Connection: keep-alive
	// Cache-Control: max-age=0
	// Upgrade-Insecure-Requests: 1
	// User-Agent: Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97
	// Safari/537.36
	// Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,
	// application/signed-exchange;v=b3;q=0.9
	// Accept-Encoding: gzip, deflate
	// Accept-Language: zh,zh-CN;q=0.9
	// -----> 以上部分为 4. 请求头
	// 其余请求头以下的数据则为 -----> 5. 报文体
	
	/*即大致结构如下*/
	//＜request-line＞ //请求行
	//
	//＜headers＞ //首部行
	//
	//＜blank line＞ //空行
	//
	//＜request-body＞ //请求体
	
	
	public SimpleHttpRequest() {
		super();
	}
	
	public SimpleHttpRequest(InputStream inputStream) {
		super();
		this.inputStream = inputStream;
	}
	
	/**
	 * 输入流
	 */
	private InputStream inputStream;
	
	@Override
	public String getAuthType() {
		return null;
	}
	
	@Override
	public Cookie[] getCookies() {
		return new Cookie[0];
	}
	
	@Override
	public long getDateHeader(String s) {
		return 0;
	}
	
	@Override
	public String getHeader(String s) {
		return super.getRequestHeader().getHttpHeaders().getHeaders().get(s);
	}
	
	@Override
	public Enumeration<String> getHeaders(String s) {
		return null;
	}
	
	@Override
	public Enumeration<String> getHeaderNames() {
		return null;
	}
	
	@Override
	public int getIntHeader(String s) {
		return 0;
	}
	
	@Override
	public String getPathInfo() {
		return super.getRequestLine().getRequestURI();
	}
	
	@Override
	public String getPathTranslated() {
		return null;
	}
	
	@Override
	public String getContextPath() {
		return null;
	}
	
	@Override
	public String getQueryString() {
		return super.getRequestLine().getQueryString();
	}
	
	@Override
	public String getRemoteUser() {
		return null;
	}
	
	@Override
	public boolean isUserInRole(String s) {
		return false;
	}
	
	@Override
	public Principal getUserPrincipal() {
		return null;
	}
	
	@Override
	public String getRequestedSessionId() {
		return null;
	}
	
	@Override
	public String getRequestURI() {
		return super.getRequestLine().getRequestURI();
	}
	
	@Override
	public StringBuffer getRequestURL() {
		return null;
	}
	
	@Override
	public String getServletPath() {
		return null;
	}
	
	@Override
	public HttpSession getSession(boolean b) {
		return null;
	}
	
	@Override
	public HttpSession getSession() {
		return null;
	}
	
	@Override
	public String changeSessionId() {
		return null;
	}
	
	@Override
	public boolean isRequestedSessionIdValid() {
		return false;
	}
	
	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}
	
	@Override
	public boolean isRequestedSessionIdFromURL() {
		return false;
	}
	
	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}
	
	@Override
	public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
		return false;
	}
	
	@Override
	public void login(String s, String s1) throws ServletException {
	
	}
	
	@Override
	public void logout() throws ServletException {
	
	}
	
	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		return null;
	}
	
	@Override
	public Part getPart(String s) throws IOException, ServletException {
		return null;
	}
	
	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
		return null;
	}
	
	@Override
	public Object getAttribute(String s) {
		return super.getRequestLine().getParameters().get(s);
	}
	
	@Override
	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration(super.getRequestLine().getParameters().keySet());
	}
	
	@Override
	public String getCharacterEncoding() {
		return super.getRequestHeader().getHttpHeaders().getAcceptEncoding();
	}
	
	@Override
	public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
		super.getRequestHeader().getHttpHeaders().setAcceptEncoding(s);
	}
	
	@Override
	public int getContentLength() {
		return super.getRequestHeader().getContentLength();
	}
	
	@Override
	public long getContentLengthLong() {
		return super.getRequestHeader().getContentLength();
	}
	
	@Override
	public String getContentType() {
		return super.getRequestHeader().getContentType();
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return (ServletInputStream) getBody();
	}
	
	@Override
	public String getParameter(String s) {
		String method = super.getRequestLine().getMethod().getName();
		Collection<String> collection = method.equals(HttpMethod.GET.getName()) ?
				                                super.getRequestLine().getParameters().get(s) :
				                                super.getRequestBody().getFormMap().get(s);
		return CollectionUtils.isEmpty(collection) ? null : collection.iterator().next();
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(super.getRequestLine().getParameters().keySet());
	}
	
	@Override
	public String[] getParameterValues(String s) {
		return super.getRequestLine().getParameters().get(s).toArray(new String[0]);
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		return null;
	}
	
	@Override
	public String getProtocol() {
		return super.getRequestLine().getProtocol().getName();
	}
	
	@Override
	public String getScheme() {
		// TODO
		return null;
	}
	
	@Override
	public String getServerName() {
		// TODO
		return null;
	}
	
	@Override
	public int getServerPort() {
		return 0;
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), GlobalPropertiesUtil.getProperty(
				"global-encoding")));
	}
	
	@Override
	public String getRemoteAddr() {
		return null;
	}
	
	@Override
	public String getRemoteHost() {
		return null;
	}
	
	@Override
	public void setAttribute(String s, Object o) {
		super.getRequestLine().getParameters().put(s, String.valueOf(o));
	}
	
	@Override
	public void removeAttribute(String s) {
		super.getRequestLine().getParameters().remove(s);
	}
	
	@Override
	public Locale getLocale() {
		return null;
	}
	
	@Override
	public Enumeration<Locale> getLocales() {
		return null;
	}
	
	@Override
	public boolean isSecure() {
		return false;
	}
	
	@Override
	public RequestDispatcher getRequestDispatcher(String s) {
		return null;
	}
	
	@Override
	public String getRealPath(String s) {
		return null;
	}
	
	@Override
	public int getRemotePort() {
		return 0;
	}
	
	@Override
	public String getLocalName() {
		return null;
	}
	
	@Override
	public String getLocalAddr() {
		return null;
	}
	
	@Override
	public int getLocalPort() {
		return 0;
	}
	
	@Override
	public ServletContext getServletContext() {
		return null;
	}
	
	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		return null;
	}
	
	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
		return null;
	}
	
	@Override
	public boolean isAsyncStarted() {
		return false;
	}
	
	@Override
	public boolean isAsyncSupported() {
		return false;
	}
	
	@Override
	public AsyncContext getAsyncContext() {
		return null;
	}
	
	@Override
	public DispatcherType getDispatcherType() {
		return null;
	}
	
	@Override
	public InputStream getBody() throws IOException {
		return inputStream;
	}
	
	@Override
	public String getMethod() {
		return super.getRequestLine().getMethod().getName();
	}
	
	@Override
	public HttpMethod getHttpMethod() {
		return super.getRequestLine().getMethod();
	}
	
	@Override
	public URI getURI() {
		return URI.create(super.getRequestLine().getRequestURI());
	}
	
	@Override
	public HttpHeaders getHeaders() {
		return super.getRequestHeader().getHttpHeaders();
	}
	
	private void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}