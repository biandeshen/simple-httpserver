package com.example.fjp.httpserver.v1.common;

import java.io.UnsupportedEncodingException;

/**
 * @FileName: CodeLengthUtils
 * @Author: fjp
 * @Date: 2020/7/24 11:02
 * @Description: 编码长度计算
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/24           版本号
 */
@SuppressWarnings("ALL")
public class CharsetLengthUtils {
	
	private CharsetLengthUtils() {
	}
	
	/**
	 * 由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。
	 * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。
	 * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度。如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。
	 * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。
	 *
	 * @param content
	 * 		文本内容
	 *
	 * @return content.length
	 * 对应编码的文本长度
	 */
	public static int getWordCount(String content) {
		int length = 0;
		for (int i = 0; i < content.length(); i++) {
			int ascii = Character.codePointAt(content, i);
			if (ascii >= 0 && ascii <= 255) {
				length++;
			} else {
				length += 2;
			}
			
		}
		return length;
	}
	
	/**
	 * 基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。
	 * 这样就可以直接例用length方法获得字符串的字节长度了
	 *
	 * @param content
	 * 		文本内容
	 *
	 * @return 文本长度
	 */
	public static int getWordCountRegex(String content) {
		content = content.replaceAll("[^\\x00-\\xff]", "**");
		return content.length();
	}
	
	
	/**
	 * 按特定的编码格式获取长度
	 *
	 * @param content
	 * 		文本内容
	 * @param charsetName
	 * 		编码格式名称
	 *
	 * @return 文本对应编码格式的长度
	 *
	 * @throws UnsupportedEncodingException
	 */
	public static int getWordCountCharset(String content, String charsetName) throws UnsupportedEncodingException {
		return content.getBytes(charsetName).length;
	}
}