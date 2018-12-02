package com.jc.oa.ic.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.oa.ic.domain.Mail;


/**
 * @title 互动交流
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */
 
public interface IMailDao extends IBaseDao<Mail>{
	
	/**
	 * 
	 * 方法描述：更新已读、未读标志
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version  2014年5月8日下午4:16:51
	 * @see
	 */
	public Integer setReadFlag(Mail mail);
	
	/**
	 * 方法描述： 更新星标
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version  2014年5月8日下午4:25:36
	 * @see
	 */
	public Integer setStarFlag(Mail mail);
	
	/**
	 * 
	 * 方法描述：移动到···文件夹
	 * @param mail
	 * @return
	 * @author zhanglg
	 * @version  2014年5月8日下午4:26:31
	 * @see
	 */
	public Integer moveTo(Mail mail);

	/**
	 * 方法描述：导航查询方法，根据当前邮件Id和导航方向查询上/下一封邮件
	 * @param mail 邮件实例
	 * @return
	 * @author zhanglg
	 * @version  2014年5月12日下午2:43:02
	 * @see
	 */
	public Mail navigate(Mail mail);

	/**
	 * 方法描述：查询查询方法
	 * @param mail
	 * @param page
	 * @return
	 * @author zhanglg
	 * @version  2014年5月12日下午8:55:50
	 * @see
	 */
	public PageManager search(Mail mail, PageManager page);

	/**
	 * 方法描述：查看往来附件
	 * @param mail
	 * @return list
	 * @author 徐伟平
	 * @version  2014.06.13
	 */
	public List<Mail> viewToAndFrom(Mail mail);
	
	/**
	 * 方法描述：未读邮件分页条件查询，使用like查询
	 * @param queryPreProcess
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月26日上午9:06:11
	 * @see
	 */
	public PageManager searchUnRead(Mail queryPreProcess, PageManager page);
	
	/**
	 * 方法描述：星标寻呼分页查询
	 * @param queryPreProcess
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月30日下午3:35:34
	 * @see
	 */
	public PageManager searchStar(Mail queryPreProcess, PageManager page);
	/**
	 * 方法描述：回复提醒查询
	 * @param mail
	 * @return
	 * @author 曹杨
	 * @version  2014年7月1日下午4:48:00
	 * @see
	 */
	public List<Mail> getReplyTexting(Mail mail);

	/**
	 * 方法描述：查询未读数量
	 * @param mail
	 * @return
	 * @author zhangligang
	 * @version  2014年7月25日下午1:39:56
	 * @see
	 */
	public Long getUnreadCount(Mail mail);
	
	public List<Mail> mailDelete(Mail mail);

	public List<Mail> queryAll(Mail queryPreProcess);

	public List<Mail> queryAllDetail(Mail queryPreProcess);
}
