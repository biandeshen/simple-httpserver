package com.example.fjp.httpserver.v1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @FileName: GlobalConfig
 * @Author: fjp
 * @Date: 2020/7/24 11:14
 * @Description: 全局配置
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/24           版本号
 */
public class GlobalConfig {
	private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
	public static final Properties GLOBAL_PROPERTIES;
	public static final String GLOBAL_ENCODING;
	
	private GlobalConfig() {
	
	}
	
	static {
		try {
			GLOBAL_PROPERTIES = new Properties();
			GLOBAL_PROPERTIES.load(GlobalConfig.class.getClassLoader().getResourceAsStream("server.properties"));
			String globalPropertiesProperty = GLOBAL_PROPERTIES.getProperty("global-encoding");
			GLOBAL_ENCODING = globalPropertiesProperty == null ? "UTF-8" : globalPropertiesProperty;
			logger.debug("loading global properties success");
		} catch (IOException e) {
			//	todo
			logger.warn("loading global properties Failed: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
}