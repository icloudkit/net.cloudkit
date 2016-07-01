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

package net.cloudkit.enterprises.xml.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DefaultSaxHandler extends DefaultHandler {

	private List<String> elementName;

	private List<String> elementValue;

	private int step;

	public static void main(String[] args) {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = spf.newSAXParser();
			saxParser.parse(DefaultSaxHandler.class.getResourceAsStream("test.xml"), new DefaultSaxHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 开始解析XML文件
	@Override
	public void startDocument() throws SAXException {
		elementName = new ArrayList<String>();
		elementValue = new ArrayList<String>();
		step = 0;
	}

	/**
	 * 结束解析XML文件
	 */
	@Override
	public void endDocument() throws SAXException {
		for (int i = 0; i < elementName.size(); i++) {
			if (!elementName.get(i).equals("") || elementName.get(i) != null) {
				System.out.println("节点名称：" + elementName.get(i));
				System.out.println("节点值：" + elementValue.get(i));
			}
		}
	}

	/**
	 * 在解释到一个开始元素时会调用此方法.但是当元素有重复时可以自己写算法来区分
	 * 这些重复的元素.qName是什么? <name:page ll=""></name:page>这样写就会抛出SAXException错误
	 * 通常情况下qName等于localName
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// 节点名称
		elementName.add(qName);
		// 循环输出属性
		for (int i = 0; i < attributes.getLength(); i++) {
			// 获取属性名称
			System.out.println("属性名称：" + attributes.getQName(i));
			// 获取属性值
			System.out.println("属性值：" + attributes.getValue(attributes.getQName(i)));
		}

	}

	/**
	 * 在遇到结束标签时调用此方法
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		step = step + 1;
	}

	/**
	 * 读取标签里的值,ch用来存放某行的xml的字符数据,包括标签,初始大小是2048,
	 * 每解释到新的字符会把它添加到char[]里。 * 注意,这个char字符会自己管理存储的字符,
	 * 并不是每一行就会刷新一次char,start,length是由xml的元素数据确定的,
	 * 暂时找不到规律,以后看源代码.
	 *
	 * 这里一个正标签，反标签都会被执行一次characters，所以在反标签时不用获得其中的值
	 */
	public void characters(char ch[], int start, int length) throws SAXException {

		// 只要当前的标签组的长度一至，值就不赋，则反标签不被计划在内
		if (elementName.size() - 1 == elementValue.size()) {
			elementValue.add(new String(ch, start, length));
		}
	}

	public List<String> getTagName() {
		return elementName;
	}

	public void setTagName(Vector<String> tagName) {
		this.elementName = tagName;
	}

	public List<String> getTagValue() {
		return elementValue;
	}

	public void setTagValue(Vector<String> tagValue) {
		this.elementValue = tagValue;
	}

}
