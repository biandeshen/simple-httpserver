package xyz.biandeshen.net.simpleserver.common;
/**
 * @FileName: HttpHeader
 * @Author: fjp
 * @Date: 2020/7/31 10:48
 * @Description: Hypertext Transfer Protocol -- HTTP/1.1   HTTP Message  Message Headers
 * 参考超文本传输协议 1.1
 * @see <a href="https://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01#Message">Hypertext Transfer Protocol
 * -- HTTP/1.1   HTTP Message  Message Headers</a>
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/31           版本号
 */
public enum HttpHeader {
	/**
	 * 1. General Header Fields
	 * 2. Request Header Fields
	 * 3. Response Header Fields
	 * 4. Entity Header Fields
	 *
	 * it is "good practice" to send General-Header fields first, followed by Request-Header or Response-Header fields
	 * prior to the Entity-Header fields.
	 * 推荐使用方式: 先通用头，然后是请求头或响应头字段，最后是实体头。
	 */
	
	// 1. General Header Fields
	Cache_Control("Cache-Control"),
	Connection("Connection"),
	Date("Date"),
	Forwarded("Forwarded"),
	Keep_Alive("Keep-Alive"),
	MIME_Version("MIME-Version"),
	Pragma("Pragma"),
	Upgrade("Upgrade"),
	
	// 2. Request Header Fields
	Accept("Accept"),
	Accept_Charset("Accept-Charset"),
	Accept_Encoding("Accept-Encoding"),
	Accept_Language("Accept-Language"),
	Authorization("Authorization"),
	From("From"),
	Host("Host"),
	If_Modified_Since("If-Modified-Since"),
	Proxy_Authorization("Proxy-Authorization"),
	Range("Range"),
	Referer("Referer"),
	Unless("Unless"),
	User_Agent("User-Agent"),
	
	// 3. Response Header Fields
	Location("Location"),
	Proxy_Authenticate("Proxy-Authenticate"),
	Public("Public"),
	Retry_After("Retry-After"),
	Server("Server"),
	WWW_Authenticate("WWW-Authenticate"),
	
	// 4. Entity Header Fields
	Allow("Allow"),
	Content_Encoding("Content-Encoding"),
	Content_Language("Content-Language"),
	Content_Length("Content-Length"),
	Content_MD5("Content-MD5"),
	Content_Range("Content-Range"),
	Content_Type("Content-Type"),
	Content_Version("Content-Version"),
	Derived_From("Derived-From"),
	Expires("Expires"),
	Last_Modified("Last-Modified"),
	Link("Link"),
	Title("Title"),
	Transfer_Encoding("Transfer-Encoding"),
	URI_header("URI-header");
	
	/**
	 * Message Header Name
	 */
	private String header;
	
	HttpHeader(String header) {
		this.header = header;
	}
	
	public String getHeaderName() {
		return header;
	}
	
	
}