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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * 发送请求
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2011-11-22 下午3:20:10
 */
public class HttpHandler {

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param;
			// System.out.println(urlName);
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Trident/5.0)");
			// 建立实际的连接
			conn.connect();
			// // 获取所有响应头字段
			// Map<String, List<String>> map = conn.getHeaderFields();
			// for (String key : map.keySet()) { // 遍历所有的响应头字段
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (IOException e) {
			// System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally { // 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 *
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Trident/5.0)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (IOException e) {
			// System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			// 使用finally块来关闭输出流、输入流
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		// System.out.println(sendGet("http://service2.winic.org/Service.asmx/EditUserPwd", "uid=string&pwd=string&newpwd=string").trim());
		// System.out.println(sendPost("http://service2.winic.org/Service.asmx/EditUserPwd", "uid=string&pwd=string&newpwd=string").trim());

		// System.out.println(sendPost("http://service2.winic.org/Service.asmx/SendMessages", "uid=mango&pwd=123456&tos=13590333471&msg=hello!&otime=2011-08-29").trim());

		// 发送短信
		// System.out.println(sendPost("http://10.4.5.151/YWSMSService/SMSAccess.asmx/SmsSender", "mobileNum=13590333471&message=hello!").trim());
		System.out.println(sendPost("http://www.nbedi.com/NBCPCAM/ClassifyQuery.aspx", "__EVENTTARGET=pagePagination&__EVENTARGUMENT=10&__EVENTVALIDATION==/wEWBALx/PcNAtOTgCcC5/mJoQkC6t3E2AsbhH2km52sfRgTCOTqvncl2QIX2Q=="));
	}
}
