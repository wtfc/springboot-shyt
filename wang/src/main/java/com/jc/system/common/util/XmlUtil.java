package com.jc.system.common.util;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlUtil {
	/**
	 * 将对象序列化成xml格式数据
	 * 
	 * @param object
	 * @return
	 */
	public static String object2Xml(Object object) {
		String xml = "";
		StringWriter sw = new StringWriter();
		sw.write("<?xml version='1.0'?>\n");
		BeanWriter bw = new BeanWriter(sw);
		bw.getXMLIntrospector().getConfiguration()
				.setAttributesForPrimitives(false);
		bw.setWriteEmptyElements(false);
		bw.getBindingConfiguration().setMapIDs(false);
		bw.enablePrettyPrint();
		try {
			bw.write(object);
			xml = sw.toString();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	/***
	 * 将xml文件反序列化为对象
	 * 
	 * @param xml
	 * @param classType
	 * @return
	 */
	public static Object xml2Object(String xml, Class classType) {
		StringReader xmlReader = new StringReader(xml);
		BeanReader beanReader = new BeanReader();
		Object object = null;
		// 配置reader
		beanReader.getXMLIntrospector().getConfiguration()
				.setAttributesForPrimitives(false);
		beanReader.getBindingConfiguration().setMapIDs(false);
		try {
			// 注册beans，以便betwixt知道XML将要被转化为一个什么Bean
			beanReader.registerBeanClass(classType.getSimpleName(), classType);
			object = beanReader.parse(xmlReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public static boolean doc2XmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			/** 将document中的内容写入文件中 */
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			/** 编码 */
			// transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public static Document load(String filename) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filename));
			document.normalize();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
}
