package xyz.biandeshen.net.simpleserver.common;

/**
 * @FileName: HttpMethod
 * @Author: fjp
 * @Date: 2020/7/16 12:11
 * @Description: 常用的http请求方法
 * @see
 * <a href="https://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01#Request">https://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01#Request</a>
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public enum StandardHttpMethod {
	/**
	 * 该Method标记指示要由标识的资源执行的方法 Request-URI。该方法区分大小写。
	 * Method            = "OPTIONS"
	 *                   | "GET"
	 *                   | "HEAD"
	 *                   | "POST"
	 *                   | "PUT"
	 *                   | "PATCH"
	 *                   | "COPY"
	 *                   | "MOVE"
	 *                   | "DELETE"
	 *                   | "LINK"
	 *                   | "UNLINK"
	 *                   | "TRACE"
	 *                   | "WRAPPED"
	 *                   | extension-method
	 *    extension-method = token
	 */
	
	/**
	 * 此方法允许客户端确定与资源相关联的选项和/或要求或服务器的功能，而无需暗示资源操作或启动资源检索。
	 */
	OPTIONS("OPTIONS"),
	
	/**
	 * 向服务器请求一个文件
	 */
	GET("GET"),
	
	/**
	 * 此方法通常用于测试超文本链接的有效性，可访问性和最新修改。
	 */
	HEAD("HEAD"),
	
	/**
	 * 向服务器发送数据让服务器处理
	 */
	POST("POST"),
	
	/**
	 * 向服务器方式数据并存储在服务器
	 */
	PUT("PUT"),
	
	/**
	 *
	 */
	PATCH("PATCH"),
	
	/**
	 * 该COPY方法请求将由标识的资源Request-URI复制到请求的URI标头字段中给定的位置。对这种方法的响应是不可实现的。
	 */
	COPY("COPY"),
	
	/**
	 * 该MOVE方法请求将由标识的资源Request-URI移动到请求的URI标头字段中给定的位置。
	 * 此方法等效于COPY 紧随其后的DELETE，但是使两者都可以在单个事务中发生。
	 * 如果请求通过缓存并Request-URI标识了当前缓存的实体，则必须从缓存中删除该实体。对这种方法的响应是不可实现的。
	 */
	MOVE("MOVE"),
	
	/**
	 * DELETE方法请求原始服务器删除由Request-URI标识的资源。
	 * <p>
	 * 如果响应包含描述状态的实体，则成功响应应为200（确定）；
	 * 如果尚未执行该操作，则为202（接受）；
	 * 如果响应为OK但不包含实体，则成功响应为204（无内容）。
	 * <p>
	 * 如果请求通过缓存并Request-URI标识了当前缓存的实体，则必须从缓存中删除该实体。对这种方法的响应是不可实现的。
	 */
	DELETE("DELETE"),
	
	/**
	 * LINK方法在由Request-URI标识的现有资源与其他现有资源之间建立一个或多个链接关系。
	 * LINK与允许在资源之间建立链接的其他方法之间的区别在于，LINK方法不允许在请求中发送任何实体，并且不会直接导致创建新资源。
	 * 如果请求通过缓存并Request-URI标识了当前缓存的实体，则必须从缓存中删除该实体。对这种方法的响应是不可实现的。
	 */
	LINK("LINK"),
	
	/**
	 * UNLINK方法从Request-URI标识的现有资源中删除一个或多个链接关系。
	 * 这些关系可能已经使用LINK方法或任何其他支持Link标头的方法建立了。删除指向资源的链接并不意味着该资源不再存在或将来无法访问。
	 * 如果请求通过缓存并Request-URI标识了当前缓存的实体，则必须从缓存中删除该实体。对这种方法的响应是不可实现的。
	 */
	UNLINK("UNLINK"),
	
	/**
	 * 对通道提供支持 TODO 协议中未说明
	 */
	CONNECT("CONNECT"),
	
	/**
	 * TRACE方法请求由Request-URI反射标识的服务器将接收到的所有内容反射回客户端，作为响应的实体。这样，客户端可以看到在请求链的另一端收到了什么，并且可以将这些数据用于测试或诊断信息。
	 * 如果成功，则响应应在实体主体中包含完整的未经编辑的请求消息，其Content-Type为“ message / http”，“ application / http”或“ text /
	 * plain”。对这种方法的响应是不可实现的。
	 */
	TRACE("TRACE"),
	
	/**
	 * WRAPPED方法允许客户端将一个或多个封装的请求发送到由Request-URI标识的服务器。该方法旨在允许将请求包装在一起，可能进行加密以提高请求的安全性和/或私密性，并由目标服务器交付以进行包装。收到WRAPPED
	 * 请求后，目标服务器必须解包该消息，并将其提供给适当的协议处理程序，就好像它是传入请求流一样。
	 * 对这种方法的响应是不可实现的。应用程序不应使用此方法来发出通常是公开且可缓存的请求。
	 */
	WRAPPED("WRAPPED");
	
	/**
	 * 方法名称
	 */
	private String name;
	
	StandardHttpMethod(String name) {
		this.name = name;
	}
	
	/**
	 * 获取 方法名称
	 *
	 * @return name 方法名称
	 */
	public String getName() {
		return this.name;
	}
	
}