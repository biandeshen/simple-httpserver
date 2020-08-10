package xyz.biandeshen.net.simpleserver.common.request;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import xyz.biandeshen.net.simpleserver.common.HttpHeaders;
import xyz.biandeshen.net.simpleserver.common.HttpMethod;
import xyz.biandeshen.net.simpleserver.common.HttpVersion;
import xyz.biandeshen.net.simpleserver.config.GlobalConfig;
import xyz.biandeshen.net.simpleserver.exception.IllegalRequestException;
import xyz.biandeshen.net.simpleserver.util.BytesUtil;
import xyz.biandeshen.net.simpleserver.util.GlobalPropertiesUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: HttpRequestParse
 * @Author: fjp
 * @Date: 2020/8/4 9:23
 * @Description: 自定义Http请求解析
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/4           版本号
 */
@Slf4j
@SuppressWarnings("ALL")
public class HttpRequestParser {
	private HttpRequestParser() {
	}
	
	/*即大致结构如下*/
	//＜request-line＞ //请求行
	//
	//＜headers＞ //首部行
	//
	//＜blank line＞ //空行
	//
	//＜request-body＞ //请求体
	
	/**
	 * 解析http请求报文的方法
	 *
	 * @throws IOException
	 */
	public static HttpRequest parse2HttpRequest(SocketChannel socketChannel) throws IOException {
		HttpRequest httpRequest = new SimpleHttpRequest();
		/* ----0----- */
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2048);
		socketChannel.read(byteBuffer);
		byteBuffer.flip();
		int remaining = byteBuffer.remaining();
		if (remaining == 0) {
			return httpRequest;
		}
		//String string = Charset.forName(StandardCharsets.UTF_8.getHeaderName()).newDecoder().decode(byteBuffer)
		// .toString();
		//String string2 = Charset.forName("UTF-8").newDecoder().decode(byteBuffer).toString();
		/* ----1----- */
		// 获取当前内容
		byte[] bytes = new byte[remaining];
		byteBuffer.get(bytes);
		// 获取请求行所在位置
		int requestLinePositon = BytesUtil.indexOf(bytes, "\r\n");
		// 获取请求体所在位置
		int requestBodyPositon = BytesUtil.indexOf(bytes, "\r\n\r\n");
		if (requestLinePositon == -1) {
			throw new IllegalRequestException("Illegal request format");
		}
		
		/* ----2----- */
		// 请求行数据
		byte[] requestLine = Arrays.copyOfRange(bytes, 0, requestLinePositon);
		// 请求头数据
		byte[] requestHeader = Arrays.copyOfRange(bytes, requestLinePositon + 2, requestBodyPositon - 4);
		// 请求空行数据
		//byte[] blankLine = Arrays.copyOfRange(bytes, requestBodyPositon - 4, requestBodyPositon);
		// 请求体数据 为了以防万一，应再次获取并判断是否读取完毕
		byte[] requestBody;
		
		/* ----3----- */
		// 解析请求行并赋值 request-line
		parseRequestLine(requestLine, httpRequest);
		
		// 解析首部行 headers
		parseHeaders(requestHeader, httpRequest);
		
		// 解析空行   blank line
		
		// 解析请求体 request-body
		byteBuffer.position(requestBodyPositon + 4);
		int contentLength = httpRequest.getRequestHeader().getContentLength();
		ByteBuffer bodyBuffer = ByteBuffer.allocate(contentLength);
		bodyBuffer.put(byteBuffer);
		//为了以防万一，应再次获取并判断是否读取完毕
		while (bodyBuffer.hasRemaining()) {
			socketChannel.read(bodyBuffer);
		}
		requestBody = bodyBuffer.array();
		parseRequestBody(requestBody, httpRequest);
		
		return httpRequest;
	}
	
	private static void parseRequestLine(byte[] src, HttpRequest httpRequest) throws IOException {
		HttpRequestLine httpRequestLine = new HttpRequestLine();
		BufferedReader bufferedReader = new BufferedReader(new StringReader(new String(src,
		                                                                               GlobalPropertiesUtil.getProperty("global-encoding"))));
		String[] requestLines = StringUtils.split(bufferedReader.readLine(), " ");
		
		// 解析请求方法
		HttpMethod httpMethod = HttpMethod.valueOf(requestLines[0]);
		
		// 解析查询字符串
		String queryString = parseQueryString(requestLines[1]);
		MultiValuedMap<String, String> queryMap = new ArrayListValuedHashMap<>();
		parseQueryParameters(queryString, queryMap);
		
		// 解析请求地址
		String requestURI = StringUtils.split(requestLines[1], "?")[0];
		
		// 解析请求协议
		HttpVersion httpVersion = HttpVersion.resolve(requestLines[2]);
		
		// 赋值
		httpRequestLine.setMethod(httpMethod);
		httpRequestLine.setQueryString(queryString);
		httpRequestLine.setParameters(queryMap);
		httpRequestLine.setRequestURI(requestURI);
		httpRequestLine.setProtocol(httpVersion);
		
		httpRequest.setRequestLine(httpRequestLine);
	}
	
	/**
	 * 解析查询字符串
	 *
	 * @param requestURI
	 */
	private static String parseQueryString(String requestURI) throws UnsupportedEncodingException {
		if (StringUtils.isNotEmpty(requestURI)) {
			return URLDecoder.decode(StringUtils.substring(requestURI, requestURI.indexOf("?")),
			                         GlobalPropertiesUtil.getProperty("global-encoding"));
		}
		return "";
	}
	
	/**
	 * 解析查询字符串
	 *
	 * @param queryString
	 * @param parameters
	 */
	private static void parseQueryParameters(String queryString, MultiValuedMap<String, String> parameters) {
		if (StringUtils.isNotEmpty(queryString)) {
			String[] queryStr = StringUtils.split(queryString, "&");
			for (String qstr : queryStr) {
				String[] split = StringUtils.split(qstr, "=");
				if (split.length != 2) {
					continue;
				}
				parameters.put(split[0].trim(), split[1].trim());
			}
		}
	}
	
	private static void parseHeaders(byte[] src, HttpRequest httpRequest) throws IOException {
		HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
		HttpHeaders httpHeader = new HttpHeaders();
		Map<String, String> headers = new HashMap<String, String>(16);
		BufferedReader bufferedReader = new BufferedReader(new StringReader(new String(src,
		                                                                               GlobalPropertiesUtil.getProperty("global-encoding"))));
		String readLine;
		while ((readLine = bufferedReader.readLine()) != null) {
			String[] readLineHeaders = StringUtils.split(readLine, ":");
			if (readLineHeaders.length != 2) {
				continue;
			}
			headers.put(readLineHeaders[0].trim(), readLineHeaders[1].trim());
		}
		httpHeader.setHeaders(headers);
		httpRequestHeader.setHttpHeaders(httpHeader);
		httpRequest.setRequestHeader(httpRequestHeader);
	}
	
	
	private static void parseRequestBody(byte[] src, HttpRequest httpRequest) throws IOException {
		HttpRequestBody httpRequestBody = new HttpRequestBody();
		if (src.length == 0) {
			httpRequest.setRequestBody(httpRequestBody);
			return;
		}
		// application/x-www-form-urlencoded 的 k-v 类型参数
		MultiValuedMap<String, String> formMap = new ArrayListValuedHashMap<>();
		// multipart/form-data 的 报文参数
		Map<String, MimeData> mimeDataMap = new HashMap<>(2 << 3);
		
		//  通过请求头获取 content-type
		String contentType = httpRequest.getHeaders().getContentType();
		//  解析 application/x-www-form-urlencoded
		if (StringUtils.containsIgnoreCase(contentType, "application/x-www-form-urlencoded")) {
			//	 k-v 类型
			String body = new String(src, GlobalConfig.GLOBAL_CHARSET);
			parseQueryParameters(body, formMap);
		} else if (StringUtils.containsIgnoreCase(contentType, "multipart/form-data")) {
			//  解析 multipart/form-data
			//------WebKitFormBoundaryY7KHZHkSlrrJk3Q2\r\n
			//Content-Disposition: form-data; name="photo"; filename="c:/aa.txt"\r\n
			//Content-Type: text/plain\r\n\r\n
			//111\r\n
			//------WebKitFormBoundaryY7KHZHkSlrrJk3Q2\r\n
			//Content-Disposition: form-data; name="name"\r\n\r\n
			//
			//222\r\n
			//------WebKitFormBoundaryY7KHZHkSlrrJk3Q2\r\n
			//Content-Disposition: form-data; name="age"\r\n\r\n
			//
			//333\r\n
			//------WebKitFormBoundaryY7KHZHkSlrrJk3Q2--\r\n
			int boundaryValueIndex = contentType.indexOf("boundary=");
			String boundary = StringUtils.substring(contentType, boundaryValueIndex + "boundary=".length());
			parseMimeMap(src, mimeDataMap, boundary);
		}
		httpRequestBody.setFormMap(formMap);
		httpRequestBody.setMimeMap(mimeDataMap);
		httpRequest.setRequestBody(httpRequestBody);
	}
	
	private static void parseMimeMap(byte[] src, Map<String, MimeData> mimeDataMap, String boundary) throws IOException {
		boundary = StringUtils.join("--", boundary);
		List<Integer> boundaryIndexList = BytesUtil.findAll(src, boundary);
		if (boundaryIndexList.isEmpty()) {
			return;
		}
		int startIndex, endIndex;
		// 循环遍历boundary索引获取值，由于最后一个为空，故 -1
		for (int i = 0; i < boundaryIndexList.size() - 1; i++) {
			//	按boundary将报文切分为块
			//	块的起始索引
			startIndex = boundaryIndexList.get(i);
			//  块的结尾索引
			endIndex = boundaryIndexList.get(i + 1);
			//	块的内容 起始长度和去掉boundary和制表符后的长度
			byte[] segment = Arrays.copyOfRange(src, startIndex + boundary.length() + 2, endIndex);
			//	块的\r\n\r\n块的内容(不包括\r\n\r\n制表符长度)，即若有 Content-Type，也将包含在此处
			int firstSegmentIndex = BytesUtil.indexOf(segment, "\r\n\r\n");
			byte[] firstSegmentLine = Arrays.copyOfRange(segment, 0, firstSegmentIndex);
			//  数据区内容应为\r\n\r\n块处开始到块的末尾（不包括制表符长度）
			byte[] dataSegmentLine = Arrays.copyOfRange(segment, firstSegmentIndex + 4, segment.length - 2);
			/* 解析块的\r\n\r\n块的内容
			 * 将 name 的 值 作为 map 的 k
			 * 若 存在 filename ，此时应有 Content-Type
			 * 若 不存在 filename ，此时 filename 设置为空
			 * 若 存在 filename 不存在 Content-Type， 默认设置 Content-Type 为 text-plain
			 */
			String control;
			String contentType = null;
			String filename = null;
			// 查找 双引号 判断变量的数量
			List<Integer> allQuotationIndexes = BytesUtil.findAll(firstSegmentLine, "\"");
			control = new String(Arrays.copyOfRange(firstSegmentLine, allQuotationIndexes.get(0) + 1,
			                                        allQuotationIndexes.get(1)), GlobalConfig.GLOBAL_CHARSET);
			// 即只存在 name
			//if (allQuotationIndexes.size() == 2) {
			//	// 什么也不需要做
			//}
			// 即同时存在 name 和 filename
			if (allQuotationIndexes.size() == 4) {
				filename = new String(Arrays.copyOfRange(firstSegmentLine, allQuotationIndexes.get(2) + 1,
				                                         allQuotationIndexes.get(3)), GlobalConfig.GLOBAL_CHARSET);
				// 即若存在content-type，此时在字段前仍应存在\r\n
				// Content-Disposition: form-data; name="photo"; filename="c:/aa.txt"\r\n
				// Content-Type: text/plain\r\n\r\n
				int firstIndex = BytesUtil.indexOf(firstSegmentLine, "\r\n");
				contentType = new String(Arrays.copyOfRange(firstSegmentLine, firstIndex + 16,
				                                            firstSegmentLine.length), GlobalConfig.GLOBAL_CHARSET);
				if (StringUtils.isEmpty(contentType)) {
					contentType = "text/plain;charset=" + GlobalConfig.GLOBAL_CHARSET;
				}
			}
			mimeDataMap.put(control, new MimeData(contentType, filename, dataSegmentLine));
		}
	}
	
	public static String bytesToHexString(byte[] src) {
		
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
		
	}
	
	
	/**
	 * https://www.w3.org/TR/html401/interact/forms.html#h-17.13.4.2
	 * standard form format
	 */
	//    <FORM action="http://server.com/cgi/handle"
	//          enctype="multipart/form-data"
	//          method="post">
	//    <P>
	//    What is your name? <INPUT type="text" name="submit-name"><BR>
	//    What files are you sending? <INPUT type="file" name="files"><BR>
	//    <INPUT type="submit" value="Send"> <INPUT type="reset">
	//    </FORM>
	
	//    Content-Type: multipart/form-data; boundary=AaB03x
	//
	//    --AaB03x
	//    Content-Disposition: form-data; name="submit-name"
	//
	//    Larry
	//    --AaB03x
	//    Content-Disposition: form-data; name="files"; filename="file1.txt"
	//    Content-Type: text/plain
	//
	//    ... contents of file1.txt ...
	//    --AaB03x--
	
	/**
	 * empty form format
	 */
	//    POST http://localhost:9000/testpost HTTP/1.1
	//    Host: localhost:9000
	//    Connection: keep-alive
	//    Content-Length: 44
	//    Cache-Control: max-age=0
	//    Origin: http://localhost:9000
	//    Upgrade-Insecure-Requests: 1
	//    Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryUHzb1hRumHCWpU0G
	//    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0
	//    .3325.181 Safari/537.36
	//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
	//    Referer: http://localhost:9000/
	//    Accept-Encoding: gzip, deflate, br
	//    Accept-Language: zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,es;q=0.6
	//
	//    ------WebKitFormBoundaryUHzb1hRumHCWpU0G--
	
	/**
	 * form with one input
	 *
	 */
	//    <form action="/testpost"
	//    enctype="multipart/form-data"
	//    method="post">
	//    <input type="text" name="submit-name">
	//    <input type="submit" value="Send">
	//    </form>
	//
	//    POST http://localhost:9000/testpost HTTP/1.1
	//    Host: localhost:9000
	//    Connection: keep-alive
	//    Content-Length: 145
	//    Cache-Control: max-age=0
	//    Origin: http://localhost:9000
	//    Upgrade-Insecure-Requests: 1
	//    Content-Type: multipart/form-data; boundary=----WebKitFormBoundarywAVMkIySLJR8L8ko
	//    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0
	//    .3325.181 Safari/537.36
	//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
	//    Referer: http://localhost:9000/
	//    Accept-Encoding: gzip, deflate, br
	//    Accept-Language: zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,es;q=0.6
	//
	//    ------WebKitFormBoundarywAVMkIySLJR8L8ko
	//    Content-Disposition: form-data; name="submit-name"
	//
	//    abc
	//    ------WebKitFormBoundarywAVMkIySLJR8L8ko--
	
}