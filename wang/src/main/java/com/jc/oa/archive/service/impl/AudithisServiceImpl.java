package com.jc.oa.archive.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.dao.IAudithisDao;
import com.jc.oa.archive.domain.Audithis;
import com.jc.oa.archive.service.IAudithisService;
import com.jc.system.CustomException;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.Utils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.UserUtils;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-06-30
 */
@Service
public class AudithisServiceImpl extends BaseServiceImpl<Audithis> implements IAudithisService{

	private IAudithisDao audithisDao;
	
	public AudithisServiceImpl(){}
	
	@Autowired
	public AudithisServiceImpl(IAudithisDao audithisDao){
		super(audithisDao);
		this.audithisDao = audithisDao;
	}
	
	/**
	 * 方法描述：
	 * @param request 
	 * @param dataId 操作对象ID
	 * @param dataType 操作对象类型：0 文件夹，1 文档
	 * @param auditType 审计类型
	 * @param desc 描述
	 * @throws ArchiveException
	 * @author zhangligang
	 * @version  2014年7月1日上午10:50:29
	 * @see
	 */
	@Override
	public void audithis(HttpServletRequest request,Long dataId,String dataName,Integer dataType,String auditType,String desc) throws ArchiveException{
		Audithis audithis = new Audithis();
		// 人员id
		audithis.setUserId(SystemSecurityUtils.getUser().getId());
		// 操作时间
		audithis.setOperTime(new Date());
		// IP
		audithis.setIp(Utils.getIpAddr(request));
		// 文档或文件夹id
		audithis.setDataId(dataId);
		// 文档或文件夹名字
		audithis.setDataName(dataName);
		// 0文件夹  1 文档
		audithis.setDataType(dataType);
		// 审计类型
		audithis.setAuditType(auditType);
		// 事件描述
		audithis.setOperDesc(desc);
		audithis.setWeight(0);
		audithis.setCreateDate(new Date());
		audithis.setCreateUser(SystemSecurityUtils.getUser().getId());
		audithis.setCreateUserOrg(UserUtils.getUser(SystemSecurityUtils.getUser().getId()).getOrgId());
		audithis.setCreateUserDept(UserUtils.getUser(SystemSecurityUtils.getUser().getId()).getDeptId());
		audithis.setModifyUser(SystemSecurityUtils.getUser().getId());
		audithis.setModifyDate(new Date());
		try {
			this.save(audithis);
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw ae;
		}
	}
	public PageManager queryByPermission(Audithis audithis, PageManager page) {
		return queryForPermission(audithis, page, "queryCount", "query");
	}
}