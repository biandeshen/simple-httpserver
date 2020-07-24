package com.example.fjp.httpserver.v1.common;

/**
 * @FileName: HttpMethod
 * @Author: fjp
 * @Date: 2020/7/16 12:11
 * @Description: 常用的http请求方法
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/16           版本号
 */
public enum HttpMethod {
	//请求方法
	/**
	 * 向服务器请求一个文件
	 */
	GET("GET"),
	/**
	 * 向服务器发送数据让服务器处理
	 */
	POST("POST"),
	/**
	 * 向服务器方式数据并存储在服务器
	 */
	PUT("PUT"),
	/**
	 * 检查一个对象是否存在
	 */
	HEAD("HEAD"),
	/**
	 * 从服务器上删除一个文件
	 */
	DELETE("DELETE"),
	/**
	 * 对通道提供支持
	 */
	CONNECT("CONNECT"),
	/**
	 * 跟踪到服务器的路径
	 */
	TRACE("TRACE"),
	/**
	 * 查询服务器的性能
	 */
	OPTIONS("OPTIONS");
	
	/**
	 * 方法名称
	 */
	private String name;
	
	HttpMethod(String name) {
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