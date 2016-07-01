package net.cloudkit.enterprises.infrastructure.utilities;///*
// * Copyright (C) 2015 The CloudKit Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package net.cloudkit.commons.utilities;
//
//import java.io.IOException;
//import java.utils.Collection;
//import java.utils.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.utils.JSONPObject;
//import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
//
///**
// * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
// *
// * 封装不同的输出风格, 使用不同的builder函数创建实例.
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2013年11月20日 下午11:36:52
// */
//public class JsonMapperHelper {
//
//	private static Logger logger = LoggerFactory.getLogger(JsonMapperHelper.class);
//
//	private ObjectMapper mapper;
//
//	public JsonMapperHelper() {
//		this(null);
//	}
//
//	public JsonMapperHelper(Include include) {
//		mapper = new ObjectMapper();
//		// 设置输出时包含属性的风格
//		if (include != null) {
//			mapper.setSerializationInclusion(include);
//		}
//		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
//		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//	}
//
//	/**
//	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
//	 */
//	public static JsonMapperHelper nonEmptyMapper() {
//		return new JsonMapperHelper(Include.NON_EMPTY);
//	}
//
//	/**
//	 * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
//	 */
//	public static JsonMapperHelper nonDefaultMapper() {
//		return new JsonMapperHelper(Include.NON_DEFAULT);
//	}
//
//	/**
//	 * Object可以是POJO，也可以是Collection或数组。
//	 * 如果对象为Null, 返回"null".
//	 * 如果集合为空集合, 返回"[]".
//	 */
//	public String toJson(Object object) {
//
//		try {
//			return mapper.writeValueAsString(object);
//		} catch (IOException e) {
//			logger.warn("write to json string error:" + object, e);
//			return null;
//		}
//	}
//
//	/**
//	 * 反序列化POJO或简单Collection如List<String>.
//	 *
//	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
//	 * 如果JSON字符串为"[]", 返回空集合.
//	 *
//	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
//	 *
//	 * @see #fromJson(String, com.fasterxml.jackson.databind.JavaType)
//	 */
//	public <T> T fromJson(String jsonString, Class<T> clazz) {
//		if (StringUtils.isEmpty(jsonString)) {
//			return null;
//		}
//
//		try {
//			return mapper.readValue(jsonString, clazz);
//		} catch (IOException e) {
//			logger.warn("parse json string error:" + jsonString, e);
//			return null;
//		}
//	}
//
//	/**
//	 * 反序列化复杂Collection如List<Bean>, 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.
//	 *
//	 * @see #createCollectionType(Class, Class...)
//	 */
//	@SuppressWarnings("unchecked")
//    public <T> T fromJson(String jsonString, JavaType javaType) {
//		if (StringUtils.isEmpty(jsonString)) {
//			return null;
//		}
//
//		try {
//			return (T) mapper.readValue(jsonString, javaType);
//		} catch (IOException e) {
//			logger.warn("parse json string error:" + jsonString, e);
//			return null;
//		}
//	}
//
//	/**
//	 * 构造Collection类型.
//	 */
//	public JavaType contructCollectionType(Class<? extends Collection<?>> collectionClass, Class<?> elementClass) {
//		return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
//	}
//
//	/**
//	 * 构造Map类型.
//	 */
//	public JavaType contructMapType(Class<? extends Map<?, ?>> mapClass, Class<?> keyClass, Class<?> valueClass) {
//		return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
//	}
//
//	/**
//	 * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
//	 */
//	public void update(String jsonString, Object object) {
//		try {
//			mapper.readerForUpdating(object).readValue(jsonString);
//		} catch (JsonProcessingException e) {
//			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
//		} catch (IOException e) {
//			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
//		}
//	}
//
//	/**
//	 * 輸出JSONP格式數據.
//	 */
//	public String toJsonP(String functionName, Object object) {
//		return toJson(new JSONPObject(functionName, object));
//	}
//
//	/**
//	 * 設定是否使用Enum的toString函數來讀寫Enum,
//	 * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
//	 * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
//	 */
//	public void enableEnumUseToString() {
//		mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
//		mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
//	}
//
//	/**
//	 * 支持使用Jaxb的Annotation，使得POJO上的annotation不用与Jackson耦合。
//	 * 默认会先查找jaxb的annotation，如果找不到再找jackson的。
//	 */
//	public void enableJaxbAnnotation() {
//		JaxbAnnotationModule module = new JaxbAnnotationModule();
//		mapper.registerModule(module);
//	}
//
//	/**
//	 * 取出Mapper做进一步的设置或使用其他序列化API.
//	 */
//	public ObjectMapper getMapper() {
//		return mapper;
//	}
//}
