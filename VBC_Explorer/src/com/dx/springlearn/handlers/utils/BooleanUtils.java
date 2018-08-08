package com.dx.springlearn.handlers.utils;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * boolean工具类
 *
 * @author
 *
 */
public abstract class BooleanUtils {

	/**
	 * 校验参数是否为null或""
	 * 
	 * @param params
	 * @return
	 */
	public static boolean isEmpty(String params) {
		if (params == null || params.equals("")) {
			return true;
		}
		return false;
	}

	
	/**
	 * 校验数组是否为null或长度为0
	 * 
	 * @param params
	 * @return
	 */
	public static boolean isEmpty(int[] params) {
		if (params == null || params.length == 0) {
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * 校验数组是否为null或长度为0
	 * 
	 * @param params
	 * @return
	 */
	public static boolean isEmpty(Object[] params) {
		if (params == null || params.length == 0) {
			return true;
		}
		return false;
	}
 
	/**
	 * 校验参数是否为null或去掉空格后为""
	 * 
	 * @param params
	 * @return
	 */
	public static boolean isBlank(String params) {
		boolean result = isEmpty(params);
		if (!result) {
			result = params.trim().equals("");
		}
		return result;
	}

	/**
	 * 校验集合是否为null或长度为0
	 * 
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.size() == 0;
	}

	/**
	 * 校验map是否为null或长度为0
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 校验对象是否为基本类型
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isSimpleType(Object object) {
		if (object == null) {
			return false;
		}
		Class<?> clazz = object.getClass();
		if (String.class.isAssignableFrom(clazz) || Enum.class.isAssignableFrom(clazz)
				|| Character.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)
				|| Date.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}

}
