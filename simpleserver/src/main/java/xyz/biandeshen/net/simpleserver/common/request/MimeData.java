package xyz.biandeshen.net.simpleserver.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fjp
 * @Title: MimeData
 * @Description: multipart/form-data 说明
 * <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC1867</a>
 * 用以接收  multipart/form-data
 * RFC1867协议
 * RFC1867对HTTP头作了适当地变更，但变更很小。
 * <h3>（1）对HTTP头的变更</h3>
 * 首先content-type头由以前的：
 * <p>
 * content-type: application/x-www-form-urlencoded
 * <p>
 * 变为
 * <p>
 * content-type: multipart/form-data; +空格+ boundary=---------------------------7d52b133509e2
 * 即增加了boundary，所谓的boundary其实就是分割线，下文将看到，RFC1867利用boundary分割HTTP实体数据。boundary中数字字符区是随机生成的。
 *
 * <h3>（2）对HTTP实体的变更</h3>
 * <p>
 * 因为RFC1867增加了文件上传得功能，而上传文件内容自然也会被加入到HTTP的实体中。现在因为既有HTTP一般的参数实体，
 * 又有上传文件的实体，所以用boundary把每种实体进行了分割，HTTP的实体看起来将是下面的样子：
 * @date 2020/8/5 9:35
 */
@Data
@AllArgsConstructor
public class MimeData {
	
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
	
	/**
	 * 即与文件名称共存
	 */
	private String contentType;
	/**
	 * 即与contentType共存
	 */
	private String fileName;
	/**
	 * \r\n\r\n 后即为数据 数据结尾为\r\n
	 */
	private byte[] data;
}