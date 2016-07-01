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
 * 随机串生成器
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2011-12-2 上午9:06:37
 */
public class SequenceGenerator {

	// 纯数字
	public static final int DIGITAL = 1;
	// 小写字母
	public static final int LOWERCASE_LETTERS = 2;
	// 大写字母
	public static final int UPPERCASE_LETTERS = 3;
	// 数字加大写字母
	public static final int DIGITAL_UPPERCASE_LETTERS = 4;
	// 数字加小写字母default
	public static final int DIGITAL_LOWERCASE_LETTERS = 5;

	/**
	 * 随机数生成
	 *
	 * @param length 长度
	 * @return
	 */
	public synchronized static String getSequence(int length, int type) {
		String str = "";
		switch (type) {
		case 1:
			str = "1234567890";
			break;
		case 2:
			str = "abcdefghijklmnopqrstuvwxyz";
			break;
		case 3:
			str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		case 4:
			str = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		default:
			str = "1234567890abcdefghijklmnopqrstuvwxyz";
			break;
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < length; i++) {
			double num = Math.random() * str.length();
			int intnum = (int) Math.floor(num);
			result.append(str.charAt(intnum));
		}
		return result.toString();
	}

}
