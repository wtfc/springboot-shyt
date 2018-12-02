package com.jc.system.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class FtpUploader {
	static Logger log = Logger.getLogger(FtpUploader.class);
	private static Map<String, String> uploadmap = PropertiesUtil
			.getProperties("/ftp.properties");

//	public static Map<String, String> getUploadmap() {
//		return uploadmap;
//	}
//
//	public static void setUploadmap(Map<String, String> uploadmap) {
//		FtpUploader.uploadmap = uploadmap;
//	}

	/**
	 * @description 连接ftp服务器
	 * @return 服务器客户端
	 * @throws IOException
	 * @author
	 * @version 2014-03-24
	 */
	public static FTPClient getFtpClient() throws IOException {
		FTPClient ftp = new FTPClient();
		int reply;
		ftp.connect(uploadmap.get("ftpHost"),
				Integer.parseInt(uploadmap.get("ftpPort")));// 连接FTP服务器
		// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		ftp.login(uploadmap.get("ftpUser"), uploadmap.get("ftpPwd"));// 登录
		ftp.setBufferSize(10000);
		reply = ftp.getReplyCode();
		// ftp.enterRemotePassiveMode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			log.warn("ftp 连接异常 ftp 地址:" + uploadmap.get("ftpHost"));
			return null;
		}
		return ftp;
	}

	/**
	 * @description 向FTP服务器上传文件
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean uploadFile(String path, String filename,
			InputStream input) {
		boolean success = false;
		FTPClient ftp = null;
		try {
			ftp = getFtpClient();
			if (ftp == null)
				return false;
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			String file_name = filename;
			if (!"".equals(path)) {
				ftp.makeDirectory(path);
				file_name = path + File.separator + filename;
			}

			ftp.storeFile(file_name, input);
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			log.error("FTP上传文件异常" + e, e);
		} finally {
			if (ftp!=null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					log.error("FTP连接关闭异常" + ioe, ioe);
				}
			}
		}
		return success;
	}

	/**
	 * @description 删除fpt服务器上指定的文件
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存文件地址(e.g abc/hello.txt)
	 * @return 执行状态
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean deleteFile(String path) {
		boolean success = false;
		FTPClient ftp = null;
		try {
			ftp = getFtpClient();
			if (ftp == null)
				return false;
			success = ftp.deleteFile(path);
		} catch (SocketException e) {
			log.error("FTP删除文件异常,文件路径为" + path + ";" + e, e);
		} catch (IOException e) {
			log.error("FTP删除文件异常,文件路径为" + path + ";" + e, e);
		} finally {
			if (ftp!=null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					log.error("FTP连接关闭异常" + ioe, ioe);
				}
			}
		}
		return success;
	}

	/**
	 * @description 从FTP服务器多文件下载
	 * @param remotePath
	 *            [] FTP服务器上的相对路径集合
	 * @param response
	 *            请求回答
	 * @return 执行状态
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean downFile(Map<String, String> remotePath,
			HttpServletResponse response) {
		boolean success = false;
		FTPClient ftp = null;
		try {
			ftp = getFtpClient();
			if (ftp == null)
				return false;
			ZipOutputStream zipout = new ZipOutputStream(
					response.getOutputStream());
			Set<String> key = remotePath.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String filepath = iter.next();
				zipout.putNextEntry(new org.apache.tools.zip.ZipEntry(
						remotePath.get(filepath)));
				InputStream in = (InputStream) ftp.retrieveFileStream(filepath);
				byte[] buffer = new byte[Integer.parseInt(GlobalContext
						.getProperty("STREAM_SLICE"))];
				// 向压缩文件中输出数据
				int nNumber;
				while ((nNumber = in.read(buffer)) != -1) {
					zipout.write(buffer, 0, nNumber);
				}
				ftp.completePendingCommand();// 多文件下载必须. 防止
												// ftp.retrieveFileStream(remotePath[i]);为null
				in.close();
			}
			zipout.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			log.error("FTP删除文件下载异常,文件路径为" + remotePath + ";" + e, e);
		} finally {
			if (ftp!=null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					log.error("FTP连接关闭异常" + ioe, ioe);
				}
			}
		}
		return success;
	}

	/**
	 * @description 从FTP服务器单文件下载
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param response
	 *            请求回答
	 * @return 执行状态
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean downFile(String remotePath,
			HttpServletResponse response) {
		boolean success = false;
		FTPClient ftp = null;
		try {
			ftp = getFtpClient();
			if (ftp == null)
				return false;
			ftp.retrieveFile(remotePath, response.getOutputStream());
			ftp.logout();
			success = true;
		} catch (IOException e) {
			log.error("FTP删除文件下载异常,文件路径为" + remotePath + ";" + e, e);
		} finally {
			if (ftp!=null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					log.error("FTP连接关闭异常" + ioe, ioe);
				}
			}
		}
		return success;
	}

}
