package com.jc.system.remind.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.remind.domain.Remind;

/**
 * @title GOA系统管理
 * @description 通知消息表 实体类
 * @version  2014-07-24
 */
public class FileXmlUtil {
private static final String filePathR = "config-metadata.xml";
/**
 * 获得文档
 * @return 消息提醒数组
 * @author wangjinchen
*/
public static Element getDocument(HttpServletRequest request){
	String filePath= GlobalContext.basePath+filePathR;
	Document doc =null;
		File file = new File(filePath);
		if(file.exists()){
			try{ 
				DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
				DocumentBuilder builder=factory.newDocumentBuilder(); 
				    doc = builder.parse(file); 
				    }catch(Exception e){ 
				e.printStackTrace(); 
			} 
		}
		Element root=doc.getDocumentElement();
	return root;
}
/**
 * 获得全部按钮
 * @return 消息提醒
* @throws CustomException 
*/
	public static List<Remind> getFieldList(HttpServletRequest request) {
		Element doc = getDocument(request);
		List<Remind> list = new ArrayList();
		if (doc != null) {
			NodeList nodeList = doc.getElementsByTagName("remind");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element nodeRoot = (Element) nodeList.item(i);
				NodeList formControlList = nodeRoot
						.getElementsByTagName("control");
				for (int j = 0; j < formControlList.getLength(); j++) {
					Element node = (Element) formControlList.item(j);
					Remind fc = new Remind();
					fc.setId(node.getAttribute("id"));
					fc.setDivid(node.getAttribute("id"));
					fc.setIclass(node.getAttribute("iclass"));
					fc.setDataoriginaltitle(node.getAttribute("dataoriginaltitle"));
					fc.setOnclickurl(node.getAttribute("onclickurl"));
					fc.setTegartClass(node.getAttribute("tegartClass"));
					list.add(fc);
				}
			}
		}
		return list;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
