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
	GET,
	/**
	 * 向服务器发送数据让服务器处理
	 */
	POST,
	/**
	 * 向服务器方式数据并存储在服务器
	 */
	PUT,
	/**
	 * 检查一个对象是否存在
	 */
	HEAD,
	/**
	 * 从服务器上删除一个文件
	 */
	DELETE,
	/**
	 * 对通道提供支持
	 */
	CONNECT,
	/**
	 * 跟踪到服务器的路径
	 */
	TRACE,
	/**
	 * 查询服务器的性能
	 */
	OPTIONS;
	
	/**
	 * 方法名称
	 */
	private String method;
	
	/**
	 * 获取 方法名称
	 *
	 * @return method 方法名称
	 */
	public String getMethod() {
		return this.method;
	}
	
	/**
	 * 设置 方法名称
	 *
	 * @param method 方法名称
	 */
	public void setMethod(String method) {
		this.method = method;
	}}