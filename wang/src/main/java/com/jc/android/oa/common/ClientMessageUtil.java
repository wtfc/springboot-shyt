package com.jc.android.oa.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.android.oa.common.domain.Usermap;
import com.jc.android.oa.common.service.IUsermapService;
import com.jc.system.CustomException;

@Service
public class ClientMessageUtil {

	public void pushMessage(Long i, String title, String message,String uri)
			throws IOException, CustomException {

		// 查询userid对应的所有android机器标识

		// title = new String(title.getBytes(), "gbk");
		// message = new String(message.getBytes(), "gbk");

		URL url = new URL("http://172.16.0.43:7070/notification.do");
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(1000);
		connection.setReadTimeout(1000);
		connection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream(), "UTF-8");
		out.write("action=send&broadcast=Y&username=" + i + "&title=" + title
				+ "&message=" + message + "&uri="+uri); // 向页面传递数据。post的关键所在！
		out.flush();
		out.close();
		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = connection.getInputStream();
		// 传说中的三层包装阿！
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(
				l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";

		}
		System.out.println(sTotalString);

	}

	public static void main(String[] args) throws IOException {
		
	}
}
