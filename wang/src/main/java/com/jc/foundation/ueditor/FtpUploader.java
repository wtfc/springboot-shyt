package com.jc.foundation.ueditor;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUploader {

	/**
	 * Description: 向FTP服务器上传文件
	
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);//连接FTP服务器
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			//ftp.enterRemotePassiveMode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			String file_name = filename;
			if(!"".equals(path)){
				if(!ftp.changeWorkingDirectory(path)){
					ftp.makeDirectory(path);	
				}
				file_name=path+"\\"+filename;
			}
			
			ftp.storeFile(file_name, input);
			
						
			
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		uploadFile("172.16.3.82",21,"admin","admin","","aaa",null);
		// TODO Auto-generated method stub

	}

}
