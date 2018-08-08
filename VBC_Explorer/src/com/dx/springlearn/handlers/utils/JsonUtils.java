package com.dx.springlearn.handlers.utils;

import java.io.StringWriter;
import java.util.Collection;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * json工具类
 * 
 * @author 
 *
 */
@SuppressWarnings("rawtypes")
public abstract class JsonUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 获取数组对应的实体类
	 * 
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public static JavaType getCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClasses) {
		return MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClasses);
	}

	/**
	 * json字符串转化为对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToObject(String json, Class<T> clazz) throws Exception {
		if (BooleanUtils.isBlank(json) || clazz == null) {
			return null;
		}
		return MAPPER.readValue(json, clazz);
	}

	/**
	 * json字符串转化为数组对象
	 * 
	 * @param json
	 * @param javaType
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToObject(String json, JavaType javaType) throws Exception {
		if (BooleanUtils.isBlank(json) || javaType == null) {
			return null;
		}
		return MAPPER.readValue(json, javaType);
	}

	/**
	 * json字符串转化为泛型对象
	 * 
	 * @param json
	 * @param typeReference
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToObject(String json, TypeReference typeReference) throws Exception {
		if (BooleanUtils.isBlank(json) || typeReference == null) {
			return null;
		}
		return MAPPER.readValue(json, typeReference);
	}

	/**
	 * 对象转化为json字符串
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String objectToJson(Object object) throws Exception {
		if (object == null) {
			return null;
		}
		if (BooleanUtils.isSimpleType(object)) {
			return "{\"value\":\"" + object.toString() + "\"}";
		}

		StringWriter sw = new StringWriter();
		MAPPER.writeValue(sw, object);
		return sw.toString();
	}

}
