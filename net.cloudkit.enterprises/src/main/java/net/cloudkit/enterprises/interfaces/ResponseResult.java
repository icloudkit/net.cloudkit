///*
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
//package net.cloudkit.ecological.enterprises.interfaces.reports;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
///**
// * Controller 响应结果
// * Util class, returns a Map in the format json. ResponeResult.java
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2014年1月2日 上午9:31:07
// */
//@Component
//public class ResponseResult {
//
//	/**
//	 * Generates modelMap to return in the modelAndView
//	 *
//	 * @param data
//	 * @return
//	 */
//	public static Map<String, Object> mapSuccess(List<?> data) {
//
//		Map<String, Object> modelMap = new HashMap<String, Object>(3);
//		modelMap.put("total", data.size());
//		modelMap.put("data", data);
//		modelMap.put("status", true);
//
//		return modelMap;
//	}
//
//	/**
//	 * Generates modelMap to return in the modelAndView
//	 *
//	 * @param data
//     * @param total
//	 * @return
//	 */
//	public static Map<String, Object> mapSuccess(List<?> data, int total) {
//
//		Map<String, Object> modelMap = new HashMap<String, Object>(3);
//		modelMap.put("total", total);
//		modelMap.put("data", data);
//		modelMap.put("status", true);
//
//		return modelMap;
//	}
//
//	/**
//	 * Generates modelMap to return in the modelAndView in case of exception
//	 *
//	 * @param msg message
//	 * @return
//	 */
//	public static Map<String, Object> mapError(String msg) {
//
//		Map<String, Object> modelMap = new HashMap<String, Object>(2);
//		modelMap.put("error", msg);
//		modelMap.put("status", false);
//
//		return modelMap;
//	}
//}
