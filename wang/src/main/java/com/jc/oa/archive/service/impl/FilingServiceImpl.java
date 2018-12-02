package com.jc.oa.archive.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.dao.IFilingDao;
import com.jc.oa.archive.domain.Filing;
import com.jc.oa.archive.service.IFilingService;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;

import flex.messaging.util.URLEncoder;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-07-09
 */
@Service
public class FilingServiceImpl extends BaseServiceImpl<Filing> implements IFilingService{

	private IFilingDao filingDao;
	
	public FilingServiceImpl(){}
	
	@Autowired
	public FilingServiceImpl(IFilingDao filingDao){
		super(filingDao);
		this.filingDao = filingDao;
	}
	
	/**
	 * 下载附件
	 * @param Filing filing 实体类
	 * @author 闻瑜
	 * @version  2014-07-10
	 * @throws ArchiveException 
	 */
	@Override
	public void downLoad(Long id,String dmName, HttpServletRequest request,
			HttpServletResponse response) throws ArchiveException {
		String path = request.getRealPath(File.separator);
		Filing fil = new Filing();
		fil.setDocumentId(id);
		fil.setFileType(1);
		List<Filing> listFil = filingDao.getDownLoad(fil);
		File[] file1 = new File[listFil.size()];
		if (listFil != null) {
			for (int i = 0; i < listFil.size(); i++) {
				Filing filing = listFil.get(i);
				file1[i] = new File(path + filing.getFilePath());
			}
		}
		// 生成的ZIP文件名为Demo.zip
		String tmpFileName = dmName;
		byte[] buffer = new byte[1024];
		String strZipPath = path + tmpFileName;
		ZipOutputStream out = null;
		try {
			tmpFileName = URLEncoder.encode(tmpFileName,"UTF-8");
			tmpFileName = tmpFileName + ".zip";
			strZipPath = URLEncoder.encode(strZipPath,"UTF-8");
			strZipPath = strZipPath + ".zip";
			out = new ZipOutputStream(new FileOutputStream(tmpFileName));
			for (int i = 0; i < file1.length; i++) {
				FileInputStream fis = new FileInputStream(file1[i]);
				if(listFil.get(i).getFileName() != null && listFil.get(i).getFileName().indexOf(".") > -1) {
					out.putNextEntry(new ZipEntry(listFil.get(i).getFileName()));
				} else {
					out.putNextEntry(new ZipEntry(file1[i].getName()));
				}
				// 设置压缩文件内的字符编码，不然会变成乱码
				out.setEncoding("GBK");
				
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();

			File file = new File(tmpFileName);
			if (file.exists()) {

				response.setCharacterEncoding("UTF-8");
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + file.getName() + "");

				InputStream inputStream;
				inputStream = new FileInputStream(file);
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[Integer.parseInt(GlobalContext
						.getProperty("STREAM_SLICE"))];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					os.write(b, 0, length);
				}
				os.close();
				inputStream.close();
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw ae;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				ArchiveException ae = new ArchiveException();
				ae.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
				throw ae;
			}
		}
	}
	
	/**
	 * 下载附件check
	 * @param Filing filing 实体类
	 * @author 闻瑜
	 * @version  2014-07-10
	 * @throws ArchiveException 
	 */
	@Override
	public boolean downLoadCheck(Long id, HttpServletRequest request) throws ArchiveException {
		String path = request.getRealPath(File.separator);
		Filing fil = new Filing();
		fil.setDocumentId(id);
		fil.setFileType(1);
		List<Filing> listFil = filingDao.getDownLoad(fil);
		File[] file1 = new File[listFil.size()];
		String check = "";
		if (listFil != null) {
			for (int i = 0; i < listFil.size(); i++) {
				Filing filing = listFil.get(i);
				file1[i] = new File(path + filing.getFilePath());
				if(!file1[i].exists()){
					check = "1";
					break;
				}
			}
		}
		if("1".equals(check) || listFil.size()<1){
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 下载附件check
	 * @param Filing filing 实体类
	 * @author 闻瑜
	 * @version  2014-07-10
	 * @throws ArchiveException 
	 */
	@Override
	public void getDeleteFiling(List<Filing> file) throws ArchiveException {
		filingDao.getDeleteFiling(file);
	}
}