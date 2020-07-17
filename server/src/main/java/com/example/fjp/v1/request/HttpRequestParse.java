package com.example.fjp.v1.request;

import com.example.fjp.v1.common.HttpHeaders;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: HttpRequestParse
 * @Author: fjp
 * @Date: 2020/7/16 11:07
 * @Description: 自定义Http请求封装解析器，用以解析请求字符串为一个自定义Http请求
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
@SuppressWarnings("ALL")
public class HttpRequestParse {
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
	 * @param bufferedReader
	 * @param httpRequest
	 *
	 * @throws IOException
	 */
	public static HttpRequest parse2HttpRequest(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		HttpRequest httpRequest = new HttpRequest();
		// 解析请求行 request-line
		parseRequestLine(bufferedReader, httpRequest);
		// 解析首部行 headers
		parseHeaders(bufferedReader, httpRequest);
		// 解析空行   blank line
		//parseBlankLine(bufferedReader);
		// 解析请求体 request-body
		parseRequestBody(bufferedReader, httpRequest);
		return httpRequest;
	}
	
	private static void parseRequestBody(BufferedReader bufferedReader, HttpRequest httpRequest) throws IOException {
		// 根据请求方法或请求头中的Content-Length长度来判断是否有请求头
		int contentLength = httpRequest.getHttpHeaders().getContentLength();
		if (contentLength == 0) {
			// 即没有长度，没有message，直接返回
			return;
		}
		char[] messages = new char[contentLength];
		bufferedReader.read(messages);
		httpRequest.setRequestBody(new String(messages));
	}
	
	private static void parseBlankLine(BufferedReader bufferedReader) throws IOException {
		// 空行
		String readBlankLine = bufferedReader.readLine();
		assert readBlankLine == null;
	}
	
	private static void parseHeaders(BufferedReader bufferedReader, HttpRequest httpRequest) throws IOException {
		Map<String, String> headers = new HashMap<String, String>(16);
		String readLine;
		while ((readLine = bufferedReader.readLine()) != null && !"".equals(readLine)) {
			String[] readLineHeaders = StringUtils.split(readLine, ":");
			assert readLineHeaders.length == 2;
			headers.put(readLineHeaders[0].trim(), readLineHeaders[1].trim());
		}
		httpRequest.setHttpHeaders(new HttpHeaders(headers));
		
	}
	
	/**
	 * 解析请求行
	 *
	 * @param bufferedReader
	 * @param httpRequest
	 */
	private static void parseRequestLine(BufferedReader bufferedReader, HttpRequest httpRequest) throws IOException {
		String[] requestLines = StringUtils.split(bufferedReader.readLine(), " ");
		assert requestLines.length == 3;
		httpRequest.setMethod(requestLines[0]);
		httpRequest.setRequestURI(requestLines[1]);
		httpRequest.setProtocol(requestLines[2]);
	}
}