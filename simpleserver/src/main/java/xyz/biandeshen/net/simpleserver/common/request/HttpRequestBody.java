package xyz.biandeshen.net.simpleserver.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MultiValuedMap;

import java.util.Map;

/**
 * @FileName: HttpRequestBody
 * @Author: fjp
 * @Date: 2020/8/3 9:17
 * @Description: 自定义Http请求体
 * History:
 * <author>          <time>          <version>
 * fjp           2020/8/3           版本号
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestBody {
	private MultiValuedMap<String, String> formMap;
	
	private Map<String, MimeData> mimeMap;
	
}

