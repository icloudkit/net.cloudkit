/*
 * Copyright (C) 2015 The CloudKit Open Source Project
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
package net.cloudkit.enterprises.infrastructure.utilities;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 随机测试数据生成工具类.
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月21日 上午9:52:53
 */
public class RandomDataHelper {

	private static Random random = new Random();

	/** 返回随机ID. **/
	public static long randomId() {
		return random.nextLong();
	}

	/** 返回随机名称, prefix字符串+5位随机数字. **/
	public static String randomName(String prefix) {
		return prefix + random.nextInt(10000);
	}

	/** 从输入list中随机返回一个对象. **/
	public static <T> T randomOne(List<T> list) {
		Collections.shuffle(list);
		return list.get(0);
	}

	 /** 从输入list中随机返回n个对象. **/
	public static <T> List<T> randomSome(List<T> list, int n) {
		Collections.shuffle(list);
		return list.subList(0, n);
	}

	/** 从输入list中随机返回随机个对象. **/
	public static <T> List<T> randomSome(List<T> list) {
		int size = random.nextInt(list.size());
		if (size == 0) {
			size = 1;
		}
		return randomSome(list, size);
	}
}
