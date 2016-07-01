/*
 * Copyright (C) 2015. The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cloudkit.flowportal.infrastructure.utilities;

/**
 * ObjectFactory
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2011-10-8 下午12:31:44
 */
public class ObjectFactory {

	private static Class<?> clazz;

	public static Object getObject(Class<?> clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		// 获得当前类模板
		clazz = Class.forName(clazz.getName());
		// 产生了一个新的当前类对象
		return clazz.newInstance();
	}

	public static Object getObject(String classPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		// 获得当前类模板
		clazz = Class.forName(classPath);
		// 产生了一个新的当前类对象
		return clazz.newInstance();
	}
}
