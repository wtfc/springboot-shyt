package com.jc.system.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class ZipFile {

	private static Logger log = Logger.getLogger(ZipFile.class);

	/**
	 * @description 压缩单个文件
	 * @param file
	 *            要压缩的文件名
	 * @param zipfile
	 *            生成的zip文件
	 * @return 执行状态
	 * @author
	 * @version 2014-03-24
	 */
	public boolean zip(String file, String zipfile) {
		boolean result = false;
		File srcFile = new File(file);
		if (srcFile.exists()) {
			try {
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
						zipfile));
				out.setEncoding("GBK");
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(srcFile
						.getName()));
				FileInputStream in = new FileInputStream(file);
				int b;
				while ((b = in.read()) != -1) {
					out.write(b);
				}
				out.close();
				result = true;
			} catch (FileNotFoundException e) {
				result = false;

				log.error("压缩文件:" + srcFile.getName() + "异常", e);
			} catch (IOException e) {
				result = false;

				log.error("压缩文件:" + srcFile.getName() + "异常", e);
			}
		}
		return result;
	}

	/**
	 * @description 压缩多个文件
	 * @param files
	 *            要压缩的文件名数组
	 * @param zipfile
	 *            生成的zip文件
	 * @return 执行状态
	 * @author
	 * @version 2014-03-24
	 */
	public boolean zip(List files, String zipfile) {
		boolean result = true;
		if (files != null && files.size() > 0) {
			try {
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
						zipfile));
				out.setEncoding("GBK");
				for (int i = 0; i < files.size(); i++) {
					String file = (String) files.get(i);
					File srcFile = new File(file);
					out.putNextEntry(new org.apache.tools.zip.ZipEntry(srcFile
							.getName()));
					FileInputStream in = new FileInputStream(srcFile);
					int b;
					while ((b = in.read()) != -1) {
						out.write(b);
					}
				}
				out.close();
			} catch (Exception e) {
				result = false;
				log.error("压缩文件:" + zipfile + "异常", e);
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * @description 压缩多个文件
	 * @param files
	 *            <文件下载相对路径,文件下载名称> 要压缩的文件名数组
	 * @param out
	 *            输出流
	 * @return 执行状态
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean zip(Map<String, String> files, OutputStream out,
			String rootDirectory) {
		boolean result = true;
		if (files != null && files.size() > 0) {
			try {

				ZipOutputStream zipout = new ZipOutputStream(out);
				Set<String> key = files.keySet();
				Iterator<String> iter = key.iterator();
				while (iter.hasNext()) {
					String filepath = iter.next();
					File srcFile = new File(rootDirectory + filepath);
					zipout.putNextEntry(new org.apache.tools.zip.ZipEntry(files
							.get(filepath)));
					FileInputStream in = new FileInputStream(srcFile);
					byte[] buffer = new byte[Integer.parseInt(GlobalContext
							.getProperty("STREAM_SLICE"))];
					// 向压缩文件中输出数据
					int nNumber;
					while ((nNumber = in.read(buffer)) != -1) {
						zipout.write(buffer, 0, nNumber);
					}
					in.close();

				}
				zipout.close();
				out.close();
			} catch (Exception e) {
				result = false;
				log.error(e.getMessage());
			}
		} else {
			result = false;
		}
		return result;

	}

	/**
	 * @description 压缩多个文件
	 * @param files
	 *            要压缩的文件名数组
	 * @param zipfile
	 *            生成的zip文件
	 * @return 执行状态
	 * @author
	 * @version 2014-03-24
	 */
	public boolean zip(List files, ServletOutputStream out) {
		boolean result = true;
		if (files != null && files.size() > 0) {
			try {

				ZipOutputStream zipout = new ZipOutputStream(out);
				for (int i = 0; i < files.size(); i++) {
					String file = (String) files.get(i);
					File srcFile = new File(file);
					zipout.putNextEntry(new org.apache.tools.zip.ZipEntry(
							srcFile.getName()));
					FileInputStream in = new FileInputStream(srcFile);
					// 向压缩文件中输出数据
					int nNumber;
					while ((nNumber = in.read()) != -1) {
						zipout.write(nNumber);
					}
					in.close();
				}
				zipout.close();
				out.close();

			} catch (Exception e) {
				result = false;
				log.debug(e.getMessage());
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * @description 解压文件
	 * @param zipFile
	 *            要解压的.zip文件
	 * @param dest
	 *            文件解压后的路径
	 * @return 解压的目录
	 * @throws IOException
	 * @author
	 * @version 2014-03-24
	 */
	public static String unzip_new(String zipFile, String dest)
			throws IOException {
		org.apache.tools.zip.ZipFile zip = new org.apache.tools.zip.ZipFile(
				zipFile, "GBK");
		Enumeration<ZipEntry> en = zip.getEntries();
		ZipEntry entry = null;
		byte[] buffer = new byte[Integer.parseInt(GlobalContext
				.getProperty("STREAM_SLICE"))];
		int length = -1;
		InputStream input = null;
		BufferedOutputStream bos = null;
		File file = null;
		String path = "error";
		while (en.hasMoreElements()) {
			entry = (ZipEntry) en.nextElement();

			if (entry
					.toString()
					.substring(entry.toString().lastIndexOf("/") + 1,
							entry.toString().length()).equals("index.html")) {
				path = entry.toString();
			}
			if (entry.isDirectory()) {
				file = new File(dest, entry.getName());
				if (!file.exists()) {
					file.mkdir();
				}
				continue;
			}

			input = zip.getInputStream(entry);
			file = new File(dest, entry.getName());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			bos = new BufferedOutputStream(new FileOutputStream(file));

			while (true) {
				length = input.read(buffer);
				if (length == -1)
					break;
				bos.write(buffer, 0, length);
			}
			bos.close();
			input.close();
		}
		zip.close();
		return path;
	}

	/**
	 * @description 解压文件
	 * @param unzipfile
	 *            要解压的.zip文件
	 * @param unpath
	 *            文件解压后的路径
	 * @author
	 * @version 2014-03-24
	 */
	public void unzip(String unzipfile, String unpath) {
		try {
			org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(
					unzipfile);
			Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry;
			createDirectory(unpath, "");
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(unpath + File.separator + name);
					f.mkdir();
				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace("\\", "/");
					if (fileName.indexOf("/") != -1) {
						createDirectory(unpath, fileName.substring(0,
								fileName.lastIndexOf("/")));
					}
					File f = new File(unpath + zipEntry.getName());

					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);

					byte[] by = new byte[Integer.parseInt(GlobalContext
							.getProperty("STREAM_SLICE"))];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @description 创建目录
	 * @param directory
	 *            创建的目录
	 * @param subDirectory
	 *            创建的子目录
	 * @author
	 * @version 2014-03-24
	 */
	private static void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if (subDirectory == "" && fl.exists() != true)
				fl.mkdir();
			else if (subDirectory != "") {
				dir = subDirectory.replace("\\", "/").split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * @description 从文件服务器上下载文件
	 * @param openPath
	 *            http连接
	 * @param downloadpath
	 *            下载目录
	 * @param zipName
	 *            压缩的文件名
	 * @return 压缩的文件名
	 * @author
	 * @version 2014-03-24
	 */
	public String download(String openPath, String downloadpath, String zipName) {
		String new_zipindex = "";
		try {
			HttpURLConnection hc = null;
			log.debug("-打开一个http连接---" + openPath);
			URL url = new URL(openPath);
			hc = (HttpURLConnection) url.openConnection();
			InputStream instream = hc.getInputStream();
			// 创建这个文件输出流
			String tempFileName;
			tempFileName = downloadpath + zipName + ".zip";
			log.debug("需要下载的文件名称" + tempFileName);
			FileOutputStream fos = new FileOutputStream(tempFileName);
			try {
				// 定义一个大小为STREAM_SLICE的字节数组
				byte[] buf = new byte[Integer.parseInt(GlobalContext
						.getProperty("STREAM_SLICE"))];
				// 从输入流中读出字节到定义的字节数组
				int len = instream.read(buf, 0, Integer.parseInt(GlobalContext
						.getProperty("STREAM_SLICE")));
				// 循环读入字节，然后写到文件输出流中
				while (len != -1) {
					fos.write(buf, 0, len);
					len = instream.read(buf, 0, Integer.parseInt(GlobalContext
							.getProperty("STREAM_SLICE")));
				}
				try {
					new_zipindex = unzip_new(tempFileName, downloadpath
							+ zipName);// 解压
				} catch (IOException e) {
					new_zipindex = "ioerror";
					log.error("要解压的.zip文件: " + tempFileName + ",解压的输出路径:"
							+ downloadpath + zipName + ";出现异常" + e);
				} catch (Exception e1) {
					new_zipindex = "ioerror";
					log.error("要解压的.zip文件: " + tempFileName + ",解压的输出路径:"
							+ downloadpath + zipName + ";出现异常" + e1);

				}
			} finally {
				if (instream != null) {
					instream.close();
				}
				if (fos != null) {
					fos.close();
				}
			}

		} catch (Exception e) {
			log.error("下载文件出现异常", e);
		}

		return new_zipindex;
	}
}
