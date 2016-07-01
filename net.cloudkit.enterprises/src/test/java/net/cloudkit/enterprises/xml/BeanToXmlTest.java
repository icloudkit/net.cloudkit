/*
 * Copyright (C) 2016. The CloudKit Open Source Project
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

package net.cloudkit.enterprises.xml;

import net.cloudkit.enterprises.xml.message.User;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BeanToXmlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<User> userList = new ArrayList<User>();
		User user = new User();
		user.setId(new Long(1));
		user.setName("JLee");
		user.setPassword("111");
		user.setAge(18);
		userList.add(user);

		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("User.xml")));
			// 使用writeObject方法把Bean输出为XML文件
			encoder.writeObject(userList);
			encoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// try {
		// XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("User.xml")));
		// // 使用readObject方法把Bean从XML文件中读取出来
		// User user = (User) decoder.readObject();
		// System.out.println(user);
		// decoder.close();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
	}

}
