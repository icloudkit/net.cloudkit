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

import net.cloudkit.enterprises.xml.message.annotation.Element;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class XMLWriter {

	// /** Namespaces **/
	// private static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";

	public Map<String, List<String>> buildDocument(StringBuffer stringBuffer, Map<Class<?>, List<Object>> recordsMap) {

		// LinkedHashMap<类注解, List<实体document>>
		Map<String, List<String>> documentsMap = new LinkedHashMap<String, List<String>>();

		for (Map.Entry<Class<?>, List<Object>> entry : recordsMap.entrySet()) {

			try {

				String rootElement = null;
				Class<?> entityClazz = entry.getKey();
				if (entityClazz.isAnnotationPresent(Element.class)) {

					// 获取类注解
					Element element = entityClazz.getAnnotation(Element.class);
					rootElement = element.name();
				} else {
					throw new MessageException(ErrorEnum.CODE_0X00001);
				}

				// 获得所有的字段
				Field[] declaredFields = entityClazz.getDeclaredFields();

				List<String> documentsList = new LinkedList<String>();

				for (Object entity : entry.getValue()) {

					StringWriter writerStr = new StringWriter();
					// PrintWriter writerXml = new PrintWriter(new OutputStreamWriter(new FileOutputStream("city-StAX.xml"), "utf-8"));

					// Create an output factory
					XMLOutputFactory xmlof = XMLOutputFactory.newInstance();

					// Set namespace prefix defaulting for all created writers
					// xmlof.setProperty("javax.xml.stream.isPrefixDefaulting", Boolean.TRUE);

					// Create an XML stream writer
					XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(writerStr);

					// Now start with root element
					xmlw.writeStartElement(rootElement);

					for (Field field : declaredFields) {

						// 查看是否具有指定类型的注释
						if (field.isAnnotationPresent(Element.class)) {

							Element element = field.getAnnotation(Element.class);
							String fieldType = field.getType().getName();
							// 获得message节点
							String fieldElement = element.name();

							// 获得get方法
							PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entityClazz);
							Method getMethod = pd.getReadMethod();

							// 调用get方法获取值
							Object fieldValue = getMethod.invoke(entity);

							// 格式化日期
							if (fieldType.equals("java.util.Calendar")) {
								if (fieldValue != null) {
									Date date = ((Calendar) fieldValue).getTime();
									SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
									fieldValue = df.format(date);
								}
							}

							// Different namespace for description element
							xmlw.writeStartElement(fieldElement);
							xmlw.writeCharacters(fieldValue.toString());
							xmlw.writeEndElement();

						}

					}

					// Write document end. This closes all open structures
					xmlw.writeEndDocument();

					// Close the writer to flush the output
					xmlw.close();

					documentsList.add(writerStr.toString());
					// System.out.println(writerStr.toString());

				}

				documentsMap.put(rootElement, documentsList);

			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return documentsMap;

	}

	public static void main(String[] args) {

		XMLWriter xmlWrite = new XMLWriter();

		List<Object> userList = new ArrayList<Object>();

		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setName("Coffee");
			user.setPassword("123456");
			user.setAge(15);
			userList.add(user);
		}

		Map<Class<?>, List<Object>> recordsMap = new HashMap<Class<?>, List<Object>>();
		recordsMap.put(User.class, userList);

		Map<String, List<String>> documentsMap = xmlWrite.buildDocument(null, recordsMap);
		for(Map.Entry<String, List<String>> entry : documentsMap.entrySet()){

			System.out.println(entry.getKey());
			for(String document : entry.getValue()){
				System.out.println(document);
			}

		}
	}

}

// StAX写XML
//		try {
//			StringWriter writerStr = new StringWriter();
//			// PrintWriter writerXml = new PrintWriter(new OutputStreamWriter(new FileOutputStream("city-StAX.xml"), "utf-8"));
//
//			// Create an output factory
//			XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
//
//			// Set namespace prefix defaulting for all created writers
//			// xmlof.setProperty("javax.xml.stream.isPrefixDefaulting", Boolean.TRUE);
//
//			// Create an XML stream writer
//			XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(writerStr);
//			// XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(writerXml);
//
//			// Write XML prologue
//			//写入XML文档声明
//			xmlw.writeStartDocument("UTF-8", "1.0");
//			// xmlw.writeStartDocument();
//
//			// Write a processing instruction
//			// xmlw.writeProcessingInstruction("xml-stylesheet href='catalog.xsl' type='text/xsl'");
//
//			// 写入注释到xml文档
//			xmlw.writeComment("注释");
//
//			// Now start with root element
//			xmlw.writeStartElement("product");
//
//			// Set the namespace definitions to the root element
//			// Declare the default namespace in the root element
//			// xmlw.writeDefaultNamespace(GARDENING);
//
//			// Writing a few attributes
//			xmlw.writeAttribute("name", "Nightshadow");
//
//			// Declare XHTML prefix
//			xmlw.setPrefix("xhtml", XHTML_NAMESPACE);
//
//			// Different namespace for description element
//			// xmlw.writeStartElement(XHTML, "description");
//			xmlw.writeStartElement("description");
//			// Declare XHTML namespace in the scope of the description element
//			xmlw.writeNamespace("xhtml", XHTML_NAMESPACE);
//			xmlw.writeCharacters("A tulip of almost black color. \nBlossoms in April & May");
//			xmlw.writeEndElement();
//
//			// Shorthand for empty elements
//			xmlw.writeEmptyElement("supplier");
//			xmlw.writeAttribute("name", "Floral22");
//			//	xmlw.writeEndElement();
//
//			// Write document end. This closes all open structures
//			xmlw.writeEndDocument();
//
//			// Close the writer to flush the output
//			xmlw.close();
//
//			System.out.println(writerStr.toString());
//		} catch (XMLStreamException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
