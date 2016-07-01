package net.cloudkit.enterprises.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLReader {

	private static final String MESSAGE_CONFIG = "message_config.xml";

	public void test() {

		//		XMLInputFactory factory = XMLInputFactory.newInstance();
		//		XMLStreamReader r = factory.createXMLStreamReader(input);
		//		try {
		//			int event = r.getEventType();
		//			while (true) {
		//				switch (event) {
		//				case XMLStreamConstants.START_DOCUMENT:
		//					System.out.println("Start Document.");
		//					break;
		//				case XMLStreamConstants.START_ELEMENT:
		//					System.out.println("Start Element: " + r.getName());
		//					for (int i = 0, n = r.getAttributeCount(); i < n; ++i)
		//						System.out.println("Attribute: " + r.getAttributeName(i) + "=" + r.getAttributeValue(i));
		//					break;
		//				case XMLStreamConstants.CHARACTERS:
		//					if (r.isWhiteSpace())
		//						break;
		//					System.out.println("Text: " + r.getText());
		//					break;
		//				case XMLStreamConstants.END_ELEMENT:
		//					System.out.println("End Element:" + r.getName());
		//					break;
		//				case XMLStreamConstants.END_DOCUMENT:
		//					System.out.println("End Document.");
		//					break;
		//				}
		//				if (!r.hasNext())
		//					break;
		//				event = r.next();
		//			}
		//		} finally {
		//			r.close();
		//		}

		//		URL url = new URL(uri);
		//		InputStream input = url.openStream();
		//		XMLInputFactory f = XMLInputFactory.newInstance();
		//		XMLStreamReader r = f.createXMLStreamReader(uri, input);
		//		XMLStreamReader fr = new StreamReaderDelegate(r) {
		//			public int next() throws XMLStreamException {
		//				while (true) {
		//					int event = super.next();
		//					switch (event) {
		//					case XMLStreamConstants.COMMENT:
		//					case XMLStreamConstants.PROCESSING_INSTRUCTION:
		//						continue;
		//					default:
		//						return event;
		//					}
		//				}
		//			}
		//		};
		//		try {
		//			int event = fr.getEventType();
		//			while (true) {
		//				switch (event) {
		//				case XMLStreamConstants.COMMENT:
		//				case XMLStreamConstants.PROCESSING_INSTRUCTION:
		//					// this should never happen
		//					throw new IllegalStateException("Filter failed!");
		//				default:
		//					// process XML normally
		//				}
		//				if (!fr.hasNext())
		//					break;
		//				event = fr.next();
		//			}
		//		} finally {
		//			fr.close();
		//		}
		//		input.close();

		// ------------------------------------------------------------
		//		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		//		XMLEventReader reader = inputFactory.createXMLEventReader(input);
		//		try {
		//			while (reader.hasNext()) {
		//				XMLEvent e = reader.nextEvent();
		//				if (e.isCharacters() && ((Characters) e).isWhiteSpace())
		//					continue;
		//				System.out.println(e);
		//			}
		//		} finally {
		//			reader.close();
		//		}

		//		final QName ICON = new QName("http://www.w3.org/2005/Atom", "icon");
		//		URL url = new URL(uri);
		//		InputStream input = url.openStream();
		//
		//		XMLInputFactory factory = XMLInputFactory.newInstance();
		//		XMLEventReader reader = factory.createXMLEventReader(uri, input);
		//		try {
		//		      while (reader.hasNext()) {
		//		            XMLEvent event = reader.peek();
		//		            if (event.isStartElement()) {
		//		                  StartElement start = event.asStartElement();
		//		                  if (ICON.equals(start.getName())) {
		//		                        System.out.println(reader.getElementText());
		//		                        break;
		//		                  }
		//		            }
		//
		//		            reader.nextEvent();
		//		      }
		//		} finally {
		//		      reader.close();
		//		}
		//		input.close();

		//		final String xml = "<?xml version=\"1.0\" standalone=\"no\" ?>" + "<!DOCTYPE catalog [" + "<!ELEMENT catalog (publication+) >" + "<!ELEMENT publication (#PCDATA) >" + "<!ATTLIST publication title CDATA #REQUIRED >" + "<!NOTATION pdf SYSTEM \"application/pdf\" >" + "<!NOTATION html SYSTEM \"text/html\" >" + "<!ENTITY overview SYSTEM \"resources/overview.pdf\" NDATA pdf >" + "<!ENTITY chapter1 SYSTEM \"resources/chapter_1.html\" NDATA html >" + "]>" + "<catalog>" + "<ext title=\"Overview\">&overview;</ext>" + "<ext title=\"Chapter 1\">&chapter1;</ext>" + "</catalog>";
		//		Map notations = new HashMap();
		//		StringReader input = new StringReader(xml);
		//		XMLInputFactory f = XMLInputFactory.newInstance();
		//		XMLEventReader r = null;
		//		PrintWriter out = new PrintWriter(System.out);
		//		try {
		//			r = f.createXMLEventReader("http://example.com/catalog.xml", input);
		//			while (r.hasNext()) {
		//				XMLEvent event = r.nextEvent();
		//				switch (event.getEventType()) {
		//				case XMLStreamConstants.ENTITY_REFERENCE:
		//					EntityReference ref = (EntityReference) event;
		//					EntityDeclaration decl = ref.getDeclaration();
		//					NotationDeclaration n = (NotationDeclaration) notations.get(decl.getNotationName());
		//
		//					out.print("Object of type ");
		//					out.print(n.getSystemId());
		//					out.print(" located at ");
		//					out.print(decl.getSystemId());
		//					out.print(" would be placed here.");
		//					break;
		//				case XMLStreamConstants.DTD:
		//					DTD dtd = (DTD) event;
		//					for (Iterator i = dtd.getNotations().iterator(); i.hasNext();) {
		//						n = (NotationDeclaration) i.next();
		//						notations.put(n.getName(), n);
		//					}
		//				default:
		//					event.writeAsEncodedUnicode(out);
		//					out.println();
		//				}
		//			}
		//		} catch (XMLStreamException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} finally {
		//			try {
		//				r.close();
		//			} catch (XMLStreamException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//		}
		//
		//		input.close();
		//		out.flush();

		//		final String ATOM_NS = "http://www.w3.org/2005/Atom";
		//		URL url = new URL(uri);
		//		InputStream input = url.openStream();
		//		XMLInputFactory f = XMLInputFactory.newInstance();
		//		XMLEventReader r = f.createXMLEventReader(uri, input);
		//		try {
		//			while (r.hasNext()) {
		//				XMLEvent event = r.nextEvent();
		//				if (event.isStartElement()) {
		//					StartElement start = event.asStartElement();
		//					boolean isExtension = false;
		//					boolean elementPrinted = false;
		//					if (!ATOM_NS.equals(start.getName().getNamespaceURI())) {
		//						System.out.println(start.getName());
		//						isExtension = true;
		//						elementPrinted = true;
		//					}
		//					for (Iterator i = start.getAttributes(); i.hasNext();) {
		//						Attribute attr = (Attribute) i.next();
		//						String ns = attr.getName().getNamespaceURI();
		//						if (ATOM_NS.equals(ns))
		//							continue;
		//						if ("".equals(ns) && !isExtension)
		//							continue;
		//						if ("xml".equals(attr.getName().getPrefix()))
		//							continue;
		//						if (!elementPrinted) {
		//							elementPrinted = true;
		//							System.out.println(start.getName());
		//						}
		//						System.out.print("\t");
		//						System.out.println(attr);
		//					}
		//				}
		//			}
		//		} finally {
		//			r.close();
		//		}
		//		input.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String, List<String>> dataMap = new HashMap<String, List<String>>();
		List<String> messageList = null;

		String key = null;

		// MESSAGE_CONFIG
		// 创建InputStream
		InputStream in = null;
		try {

			in = XMLReader.class.getClassLoader().getResourceAsStream(MESSAGE_CONFIG);
			// 创建StAX分析工厂
			XMLInputFactory xif = XMLInputFactory.newInstance();
			// 创建分析器
			XMLStreamReader reader = xif.createXMLStreamReader(in);

			boolean flag = false;

			// 迭代
			while (reader.hasNext()) {
				// 读取下一个事件
				int event = reader.next();

				// 如果这个事件是元素开始
				// if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
				if (event == XMLStreamReader.START_ELEMENT) {
					// System.out.println(reader.getLocalName());
					// System.out.println(reader.getElementText());

					if ("messages".equals(reader.getLocalName())) {
						key = reader.getAttributeValue(null, "name");
						messageList = new ArrayList<String>();
						flag = true;
						continue;
					}

					if (flag) {
						// System.out.println(reader.getLocalName());
						//// 判断元素是不是message
						//// 如果是message则输出元素的文本内容
						// if ("message".equals(reader.getLocalName())) {
						messageList.add(reader.getAttributeValue(null, "type"));
						// System.out.println(reader.getAttributeValue(null, "type"));
						// System.out.println(reader.getAttributeValue(null, "builder"));
						// }
					}
					//if ("element".equals(reader.getLocalName())) {
					//	System.out.println(reader.getAttributeValue(null, "name") + ":" + reader.getElementText());
					//}
				}

				if (event == XMLStreamReader.END_ELEMENT) {

					if ("messages".equals(reader.getLocalName())) {
						// System.out.println(reader.getLocalName());
						dataMap.put(key, messageList);
						messageList = null;
						// flag = false;
						continue;
					}
				}

				//if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
				//	System.out.println("Start Element: " + reader.getName());
				//	for (int i = 0, n = reader.getAttributeCount(); i < n; ++i) {
				//
				//		QName name = reader.getAttributeName(i);
				//		String value = reader.getAttributeValue(i);
				//		System.out.println("Attribute: " + name + " = " + value);
				//	}
				//}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		for (Map.Entry<String, List<String>> entry : dataMap.entrySet()) {

			System.out.println(entry.getKey() + "--------------------------");
			for (String string : entry.getValue()) {
				System.out.println(string);
			}
		}
	}

}
