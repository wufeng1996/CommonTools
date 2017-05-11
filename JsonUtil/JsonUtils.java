package com.taotao.common.utils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON格式轉換的工具類
 * 內部依賴Jackson包，核心對象是ObjectMapper
 * @author mbc1996
 *
 */
public class JsonUtils {

	// 定義jackson對象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 將對象轉化爲json字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data) {
		try {
			String json = MAPPER.writeValueAsString(data);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 將json結果集轉換爲對象
	 * 
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把Json數據轉換爲pojo 的list
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = MAPPER.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
