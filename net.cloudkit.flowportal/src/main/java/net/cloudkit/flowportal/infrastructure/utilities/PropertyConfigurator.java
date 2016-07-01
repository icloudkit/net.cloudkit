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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 配置信息加载 Configuration
 *
 * @author hongquanli<hongquanli@qq.com>
 * @version 1.0 2011-8-26 上午10:43:45
 */
public class PropertyConfigurator {

	/** 配置文件 **/
	public static final String SYS_PARAM = "environment.properties";

	/** HashMap<文件名, 配置对像> **/
	private static Map<String, Properties> propertiesMap = new LinkedHashMap<>();

	public PropertyConfigurator() {

	}

	static {
		FileInputStream fis = null;
		try {
			propertiesMap.put(SYS_PARAM, new Properties());

			for (Entry<String, Properties> entry : propertiesMap.entrySet()) {
				String fileName = entry.getKey();
				Properties prop = entry.getValue();
				// System.getProperty("user.dir") + "/config/" + fileName
				// fis = new FileInputStream(System.getProperty("user.dir") + "/config/" + fileName);
				// prop.load(fis);
				// fis.close();
				InputStream is = PropertyConfigurator.class.getClassLoader().getResourceAsStream(fileName);
				prop.load(is);
				is.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据键配置文件中相应的值
	 *
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return getProperty(SYS_PARAM, key);
	}

	/**
	 * 根据键取指定配置文件中的值
	 *
	 * @param filename 配置文件名常量
	 * @param key
	 * @return
	 */
	public static String getProperty(String filename, String key) {
		Properties prop = propertiesMap.get(filename);
		if (prop.containsKey(key)) {
			return prop.getProperty(key).trim();
		}
		return null;
	}

	public static void main(String[] args) {
		PropertyConfigurator.getProperty(SYS_PARAM, "user");
	}
}
