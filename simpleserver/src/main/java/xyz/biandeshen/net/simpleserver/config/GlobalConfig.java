package xyz.biandeshen.net.simpleserver.config;


import static xyz.biandeshen.net.simpleserver.util.GlobalPropertiesUtil.getProperty;

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
	public static final String GLOBAL_CHARSET;
	
	static {
		// 初始化默认编码
		GLOBAL_CHARSET = getProperty("global-encoding");
	}
}