package com.jc.system.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * @title GOA2.0
 * @version 2014-03-24
 * 
 */
public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	private static String message;

	/**
	 * @description 读取文本文件内容
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 * @throws Exception
	 * @author
	 * @version 2014-03-24
	 */
	@SuppressWarnings("resource")
	public static String readTxt(String filePathAndName, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + " ");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
		} catch (IOException es) {
			st = "";
		}
		return st;
	}

	/**
	 * @description 新建目录
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 * @author
	 * @version 2014-03-24
	 */
	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			message = "创建目录操作出错";
		}
		return txt;
	}

	/**
	 * @description 新建目录
	 * @param folderPaths
	 *            多个目录
	 * @author
	 * @version 2014-03-24
	 */
	public static void createFolder(String[] folderPaths) {
		for (int i = 0; i < folderPaths.length; i++) {
			createFolder(folderPaths[i]);
		}
	}

	/**
	 * @description 多级目录创建
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:myfa/c
	 * @author
	 * @version 2014-03-24
	 */
	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			while (st.hasMoreTokens()) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + File.separator + txt);
				} else {
					txts = createFolder(txts + txt + File.separator);
				}
			}
		} catch (Exception e) {
			message = "创建目录操作出错！";
		}
		return txts;
	}

	/**
	 * @description 新建文件
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @author
	 * @version 2014-03-24
	 */
	public static void createFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();

			int position = filePath.lastIndexOf(File.separator);
			String folderPath = filePath.substring(0, position);
			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(filePathAndName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(fileContent);
			out.close();
			fos.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
		}
	}

	/**
	 * @description 有编码方式的文件创建
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 * @author
	 * @version 2014-03-24
	 */
	public static void createFile(String filePathAndName, String fileContent,
			String encoding) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			PrintWriter myFile = new PrintWriter(myFilePath, encoding);
			String strContent = fileContent;
			myFile.println(strContent);
			myFile.close();
		} catch (Exception e) {
			message = "创建文件操作出错";
		}
	}

	/**
	 * @description 删除文件
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			int position = filePathAndName.lastIndexOf(File.separator);
			String forderPath = filePathAndName.substring(0, position - 1);
			File forder = new File(forderPath);
			if (!forder.exists()) {
				forder.mkdirs();
			}
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				message = (filePathAndName + "删除文件操作出错");
			}
		} catch (Exception e) {
			message = e.toString();
		}
		return bea;
	}

	/**
	 * @description 删除文件夹
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean delFolder(String folderPath) {
		boolean bea = false;
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
			bea = true;
		} catch (Exception e) {
			message = ("删除文件夹操作出错");
			bea = false;
		}
		return bea;
	}

	/**
	 * @description 删除指定文件夹下所有文件
	 * @param path
	 *            文件夹完整绝对路径
	 * @return boolean 成功删除返回true遭遇异常返回false
	 * @author
	 * @version 2014-03-24
	 */
	public static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * @description 复制单个文件
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @author
	 * @version 2014-03-24
	 */
	@SuppressWarnings("resource")
	public static void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[Integer.parseInt(GlobalContext
						.getProperty("STREAM_SLICE"))];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			message = ("复制单个文件操作出错");
		}
	}

	/**
	 * @description 复制整个文件夹的内容
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return 所有复制后的新文件
	 * @author
	 * @version 2014-03-24
	 */
	public static List<File> copyFolder(String oldPath, String newPath) {
		List<File> list = new ArrayList<File>();
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[Integer.parseInt(GlobalContext
							.getProperty("STREAM_SLICE"))];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
					list.add(new File(newPath + "/"
							+ (temp.getName()).toString()));
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					List<File> childList = copyFolder(oldPath + "/" + file[i],
							newPath + "/" + file[i]);
					for (int j = 0; j < childList.size(); j++) {
						list.add(childList.get(j));
					}
				}
			}
		} catch (Exception e) {
			message = "复制整个文件夹内容操作出错";
			logger.error(message, e);
		}
		return list;
	}

	/**
	 * @description 移动文件
	 * @param oldPath
	 *            准备移动的文件路径
	 * @param newPath
	 *            移动之后的文件路径
	 * @author
	 * @version 2014-03-24
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * @description 移动目录
	 * @param oldPath
	 *            准备移动的目录路径
	 * @param newPath
	 *            移动之后的目录路径
	 * @author
	 * @version 2014-03-24
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * @description 得到错误信息
	 * @return 错误信息内容
	 * @author
	 * @version 2014-03-24
	 */
	public static String getMessage() {
		return message;
	}

	/**
	 * @description 打包文件
	 * @param filePath
	 *            打包文件目录
	 * @return 打包后的路径
	 * @author
	 * @version 2014-03-24
	 */
	public static String zip(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		String outPath= file.getParent() + File.separator + file.getName() + ".zip";;
		return zip(filePath,outPath);
	}
	
	/**
	 * @description 打包文件
	 * @param filePath
	 *            打包文件目录
	 * @param outFilePath
	 *            压缩文件输出打包文件目录
	 * @return 打包后的路径
	 * @author
	 * @version 2014-03-24
	 */
	public static String zip(String filePath,String outFilePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		String name = file.getName();
		String outPath = null;
		ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(outFilePath));
			zip(out, file, name);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return outPath;
	}
	
	/**
	 * @description 压缩文件
	 * @param out
	 *            输出的文件流
	 * @param f
	 *            打包的文件
	 * @param base
	 *            根目录
	 * @throws Exception
	 * @author
	 * @version 2014-03-24
	 */
	private static void zip(ZipOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] content = new byte[Integer.parseInt(GlobalContext
					.getProperty("STREAM_SLICE"))];
			int len;
			while ((len = in.read(content)) != -1) {
				out.write(content, 0, len);
				out.flush();
			}
			in.close();
		}
	}

	/**
	 * @description 读取所有目录下的文件
	 * @param filepath
	 *            读取的目录
	 * @return list 文件列表
	 * @author
	 * @version 2014-03-24
	 */
	public static List<File> readfileList(String filepath) {
		List<File> fileList = new ArrayList<File>();
		return readfileList(filepath, fileList);
	}

	/**
	 * @description 读取所有目录下的文件
	 * @param filepath
	 *            读取的目录
	 * @param list
	 *            文件列表（包括目录）
	 * @return list 文件列表
	 * @author
	 * @version 2014-03-24
	 */
	private static List<File> readfileList(String filepath, List<File> list) {
		File file = new File(filepath);
		// 文件
		if (!file.isDirectory()) {
			list.add(file);
		} else if (file.isDirectory()) { // 如果是目录， 遍历所有子目录取出所有文件名
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "/" + filelist[i]);
				if (!readfile.isDirectory()) {
					list.add(readfile);
				} else if (readfile.isDirectory()) { // 子目录的目录
					readfileList(filepath + "/" + filelist[i], list);
				}
			}
		}
		return list;
	}

	/**
	 * @description 读取所有目录下的文件
	 * @param filepath
	 *            读取的目录
	 * @param list
	 *            文件列表（包括目录）
	 * @return list 文件列表
	 * @throws Exception
	 * @author
	 * @version 2014-03-24
	 */
	public static List<String> readfile(String filepath, List<String> list)
			throws Exception {

		if (list == null) {
			list = new ArrayList<String>();
		}
		File file = new File(filepath);
		// 文件
		if (!file.isDirectory()) {
			list.add(file.getPath());

		} else if (file.isDirectory()) { // 如果是目录， 遍历所有子目录取出所有文件名
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "/" + filelist[i]);
				if (!readfile.isDirectory()) {
					list.add(readfile.getPath());
				} else if (readfile.isDirectory()) { // 子目录的目录
					readfile(filepath + "/" + filelist[i], list);
				}
			}
		}
		return list;
	}

	
	
	private static List<String> imgExt = new ArrayList<>();
	static {
		imgExt.add("png");
		imgExt.add("jpg");
		imgExt.add("bmp");
		imgExt.add("jpeg");
		imgExt.add("jpe");
		imgExt.add("gif");
	}
	

	/**
	 * 获取文件名后缀
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName){
		String fileExt = null;
		int index = fileName.lastIndexOf(".") ;
		if(index ==-1){
			fileExt = "";
		}
		else{
			fileExt = fileName.substring(index+1)
				.toLowerCase();
		}
		return fileExt;
	}
	
	public static boolean isImage(String fileName){
		return imgExt.contains(getFileExt(fileName));
	}
	
	public static boolean isImageExt(String ext){
		return imgExt.contains(ext);
	}
	
	public static String getFileName(String fullFileName){
		int index = fullFileName.lastIndexOf(".") ;
		if(index ==-1){
			return  fullFileName;
		}
		else{
			return fullFileName.substring(0,index);
		}
	}
	
}
