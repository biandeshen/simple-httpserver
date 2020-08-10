package xyz.biandeshen.net.simpleserver.util;

import xyz.biandeshen.net.simpleserver.common.request.MimeData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Created on 2018/4/26.
 */
public class ReflectUtil {
	
	private static final Map<Class<?>, UnaryOperator<Object>> TYPE_FUNCTION_MAP;
	private static final UnaryOperator<Object> DEFAULT_FUNC = s -> null;
	
	static {
		TYPE_FUNCTION_MAP = new HashMap<>();
		UnaryOperator<Object> boolFunc = s -> Boolean.valueOf(String.valueOf(s));
		UnaryOperator<Object> charFunc = s -> (String.valueOf(s)).charAt(0);
		UnaryOperator<Object> byteFunc = s -> Byte.valueOf((String) s);
		UnaryOperator<Object> shortFunc = s -> Short.valueOf(String.valueOf(s));
		//TODO 使用valueOf和parseInt的性能比较
		UnaryOperator<Object> integerFunc = s -> Integer.valueOf(String.valueOf(s));
		UnaryOperator<Object> longFunc = s -> Long.valueOf(String.valueOf(s));
		UnaryOperator<Object> floatFunc = s -> Float.valueOf(String.valueOf(s));
		UnaryOperator<Object> doubleFunc = s -> Double.valueOf(String.valueOf(s));
		//UnaryOperator<Object> classFunc = ReflectUtil::apply;
		
		TYPE_FUNCTION_MAP.put(Boolean.class, boolFunc);
		TYPE_FUNCTION_MAP.put(Character.class, charFunc);
		TYPE_FUNCTION_MAP.put(Byte.class, byteFunc);
		TYPE_FUNCTION_MAP.put(Short.class, shortFunc);
		TYPE_FUNCTION_MAP.put(Integer.class, integerFunc);
		TYPE_FUNCTION_MAP.put(Long.class, longFunc);
		TYPE_FUNCTION_MAP.put(Float.class, floatFunc);
		TYPE_FUNCTION_MAP.put(Double.class, doubleFunc);
		TYPE_FUNCTION_MAP.put(String.class, String::valueOf);
		TYPE_FUNCTION_MAP.put(MimeData.class, o -> o);
		//TODO Date类型
		//TYPE_FUNCTION_MAP.put(Object.class, classFunc);
		//TYPE_FUNCTION_MAP.put(Class.class, o -> o);
	}
	
	public static Object parseObj(Object val, Class<?> type) {
		return val == null ? null : TYPE_FUNCTION_MAP.getOrDefault(type, DEFAULT_FUNC).apply(val);
	}
	
	//public static void main(String[] args) {
	//
	//	Object o2 = parseObj(new ByteArrayInputStream("ttt".getBytes()), SimpleHttpRequest.class);
	//	System.out.println("o2 = " + o2);
	//	System.out.println("o2.getClass() = " + o2.getClass());
	//}
	//
	//private static Object apply(Object s) {
	//	try {
	//		Stream<Class> classStream = Stream.of(TYPE_FUNCTION_MAP.keySet().toArray(new Class[0]));
	//		List<Class> collect = classStream.collect(Collectors.toList());
	//		collect.remove(Object.class);
	//		boolean contains = collect.contains(s.getClass());
	//		if (contains) {
	//			return parseObj(s, s.getClass());
	//		}
	//		Object newInstance = null;
	//		try {
	//			newInstance = s.getClass().getConstructor(s.getClass()).newInstance(s);
	//		} catch (InvocationTargetException | NoSuchMethodException e) {
	//			e.getMessage();
	//		}
	//		if (newInstance == null) {
	//			return parseObj(s, Class.class);
	//		}
	//		return newInstance;
	//	} catch (InstantiationException | IllegalAccessException e) {
	//		return null;
	//	}
	//}
}
