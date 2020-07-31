package xyz.biandeshen.net.simpleserver.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @FileName: PropertiesUtil
 * @Author: fjp
 * @Date: 2020/7/31 15:35
 * @Description: 配置文件读取工具类
 * History:
 * <author>          <time>          <version>
 * fjp           2020/7/31           版本号
 */
@Slf4j
public class GlobalPropertiesUtil {
	private static final Properties properties;
	
	static {
		properties = new Properties();
		try {
			properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("server.properties"));
		} catch (RuntimeException | IOException e) {
			log.error("读取配置文件 server.properties 失败", e);
			System.exit(1);
		}
	}
	
	public static String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	public static String getProperty(String name, String defaultValue) {
		return getProperty(name) == null ? defaultValue : getProperty(name);
	}
}