package com.jc.system.notice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.system.CommonException;
import com.jc.system.CustomException;
import com.jc.system.notice.dao.INoticeMsgDao;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.notice.service.INoticeMsgService;

/**
 * @title GOA系统管理
 * @description  业务服务类
 * @author 
 * @version  2014-06-05
 */
@Service
public class NoticeMsgServiceImpl extends BaseServiceImpl<NoticeMsg> implements INoticeMsgService{

	private INoticeMsgDao noticeDao;
	
	public NoticeMsgServiceImpl(){}
	
	@Autowired
	public NoticeMsgServiceImpl(INoticeMsgDao noticeDao){
		super(noticeDao);
		this.noticeDao = noticeDao;
	}
	
	/**
	  * 根据人员id查询消息提醒信息
	  * @param userId 人员id
	  * @return 消息提醒数组
	  * @version 1.0 2014年6月6日 上午9:25:52
	 * @throws CustomException 
	*/
	@Override
	@SuppressWarnings("unchecked")
	public List<NoticeMsg> queryMsg(Long userId) throws CustomException {
		//显示最新的5条
		PageManager page = new PageManager();
		page.setPageRows(5);
		page.setPage(0);
		NoticeMsg noticeMsg = new NoticeMsg();
		noticeMsg.setReceiveUser(userId);
		noticeMsg.setShowFlag(0);
		noticeMsg.setReadFlag(0);
		noticeMsg.addOrderByFieldDesc("t.CREATE_DATE");
		List<NoticeMsg> list = (List<NoticeMsg>) dao.queryByPage(noticeMsg, page).getData();
		//当查出数据不足5条时，把 readFlag = 0以及showFlag = 1的数据读取出来，并组成5条
		if(list.size()>0&&list.size()<5){
			int num = 5-list.size();
			page.setPageRows(num);
			noticeMsg.setShowFlag(1);
			List<NoticeMsg> listTemp = (List<NoticeMsg>) dao.queryByPage(noticeMsg, page).getData();
			for(NoticeMsg item:listTemp){
				list.add(item);
			}
		}
		
		//读取之后设置弹出标志位（showFlag）为1
		if(list.size()>0){
			NoticeMsg noticeMsgUpdate = new NoticeMsg();
			noticeMsgUpdate.setShowFlag(0);
			noticeMsgUpdate.setReceiveUser(userId);
			List<NoticeMsg> listAll = dao.queryAll(noticeMsgUpdate);
			for(NoticeMsg item:listAll){
				NoticeMsg itemTemp = new NoticeMsg();
				itemTemp.setId(item.getId());
				itemTemp.setShowFlag(1);
				dao.update(itemTemp);
			}
		}
		return list;
	}
	
	@Override
	public Integer delete(NoticeMsg t) throws CustomException {
		return super.delete(t,false);
	}
	
	/**
	  * 根据人员id查询消息提醒信息
	  * @param userId 人员id
	  * @return 消息提醒数组
	  * @version 1.0 2014年6月6日 上午9:25:52
	*/
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer readNotice(NoticeMsg noticeMsg) throws CommonException {
		Integer result = -1;
		try {
			result = noticeDao.readNotice(noticeMsg);
		} catch (Exception e) {
			CommonException ce = new CommonException(e);
			ce.setBean(noticeMsg);
			throw ce;
		}
		return result;
		
	}
}