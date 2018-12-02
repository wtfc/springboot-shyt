package com.jc.oa.ic.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.dao.IMailDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;

/**
 * @title 互动交流
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */
@Repository
public class MailDaoImpl extends BaseDaoImpl<Mail> implements IMailDao{
	
	/**
	 * 
	 * 方法描述：更新已读、未读标志
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version  2014年5月8日下午4:16:51
	 * @see
	 */
	@Override
	public Integer setReadFlag(Mail mail){
		return template.update(getNameSpace(mail)+".setReadFlag", mail);
	}
	
	/**
	 * 
	 * 方法描述：更新星标
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version  2014年5月8日下午4:26:31
	 * @see
	 */
	@Override
	public Integer setStarFlag(Mail mail){
		return template.update(getNameSpace(mail)+".setStarFlag", mail);
	}
	
	/**
	 * 
	 * 方法描述：移动到···文件夹
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version  2014年5月8日下午4:26:31
	 * @see
	 */
	@Override
	public Integer moveTo(Mail mail){
		return template.update(getNameSpace(mail)+".moveTo", mail);
	}
	
	/**
	 * 方法描述：导航查询方法，根据当前邮件Id和导航方向查询上/下一封邮件
	 * @param mail 邮件实例
	 * @return
	 * @author zhanglg
	 * @version  2014年5月12日下午2:43:02
	 * @see
	 */
	@Override
	public Mail navigate(Mail mail){
		return template.selectOne(getNameSpace(mail)+".navigate", mail);
	}
	
	/**
	 * 方法描述：条件查询方法
	 * @param mail
	 * @param page
	 * @return
	 * @author zhanglg
	 * @version  2014年5月12日下午8:55:20
	 * @see
	 */
	@Override
	public PageManager search(Mail mail,PageManager page){
		return super.queryByPage(mail, page, "searchCount", "search");
	}
	
	/**
	 * 方法描述：
	 * @param mail
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月26日上午9:06:51
	 * @see
	 */
	@Override
	public PageManager searchUnRead(Mail mail,PageManager page){
		return super.queryByPage(mail, page, "searchUnReadCount", "searchUnRead");
	}
	
	/**
	 * 方法描述：查看往来邮件
	 * @param mail
	 * @return List
	 * @author 徐伟平
	 * @version  2014年6月13日
	 * @see
	 */
	@Override
	public List<Mail> viewToAndFrom(Mail mail) {
		return  template.selectList(getNameSpace(mail) + ".viewToAndFrom", mail);
	}

	/**
	 * 方法描述：星标邮件分页查询
	 * @param queryPreProcess
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月30日下午4:06:32
	 * @see
	 */
	@Override
	public PageManager searchStar(Mail queryPreProcess, PageManager page) {
		
		return super.queryByPage(queryPreProcess, page, "searchStarCount", "searchStar");
	}
	/**
	 * 方法描述：回复提醒查询
	 * @param mail
	 * @return
	 * @author 曹杨
	 * @version  2014年7月1日下午4:48:00
	 * @see
	 */
	@Override
	public List<Mail> getReplyTexting(Mail mail) {
		return template.selectList(getNameSpace(mail) + ".queryReplyTexting", mail);
	}

	@Override
	public Long getUnreadCount(Mail mail) {
		Object rowsCountObject = template.selectOne(getNameSpace(mail) + ".queryUnReadMailCount", mail);
		if(rowsCountObject instanceof Long){
			//rowsCountObject = ((Long) rowsCountObject).intValue();
			return (Long) rowsCountObject;
		}
		else {
			return ((Integer) rowsCountObject).longValue();
		}
	}
	
	/**
	 * 方法描述：查看往来邮件
	 * @param mail
	 * @return List
	 * @author 徐伟平
	 * @version  2014年6月13日
	 * @see
	 */
	@Override
	public List<Mail> mailDelete(Mail mail) {
		return  template.selectList(getNameSpace(mail) + ".mailDelete", mail);
	}

	@Override
	public List<Mail> queryAll(Mail mail) {
		return template.selectList(getNameSpace(mail) + ".queryUnReadMail", mail);
	}

	@Override
	public List<Mail> queryAllDetail(Mail mail) {
		return template.selectList(getNameSpace(mail) + ".queryMail", mail);
	}

}