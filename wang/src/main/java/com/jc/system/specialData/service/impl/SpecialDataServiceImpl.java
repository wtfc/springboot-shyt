package com.jc.system.specialData.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.UserUtils;
import com.jc.system.specialData.dao.ISpecialDataDao;
import com.jc.system.specialData.domain.SpecialData;
import com.jc.system.specialData.service.ISpecialDataService;
import com.jc.system.specialData.service.ISpecialdataShareService;
import com.jc.system.specialData.util.SolarorlunarUtils;


/**
 * @title 172.16.3.68
 * @description  业务服务类
 * @author 
 * @version  2014-12-02
 */
@Service
public class SpecialDataServiceImpl extends BaseServiceImpl<SpecialData> implements ISpecialDataService{

	private ISpecialDataDao specialDataDao;
	
	@Autowired
	private ISpecialdataShareService specialdataShareService;
	
	@Autowired
	IInteractFacadeService interactFacadeService;
	
	public SpecialDataServiceImpl(){}
	
	@Autowired
	public SpecialDataServiceImpl(ISpecialDataDao specialDataDao){
		super(specialDataDao);
		this.specialDataDao = specialDataDao;
	}

	/**
	* @description 根据主键删除多条记录方法
	* @param SpecialData specialData 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-12-02 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(SpecialData specialData) throws CustomException{
		Integer result = -1;
		try{
			result = specialDataDao.delete(specialData);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(specialData);
			throw ce;
		}
		return result;
	}

	public Map<String, Object> querySpecialForPortal(Long columnId,Long shareUserId,
			Long shareDeptId, Long shareOrganId) throws CustomException {
		Map<String,Object> result = new HashMap<String,Object>();
		
		SpecialData specialData = new SpecialData();
		if(columnId != null)
			specialData.setId(columnId);
		specialData.addOrderByField("t.SPECIAL_DATA");
		specialData.setDeleteFlag(0);
		specialData.setInfoType("1");
		List<SpecialData> birthdayList = specialDataDao.queryAll(specialData);
		List<SpecialData> reutrnbirthdays = new ArrayList<SpecialData>(); 
		if(birthdayList != null && birthdayList.size() > 0){
			for(int i=0;i<birthdayList.size();i++){
				SpecialData tempSpecialData = birthdayList.get(i);
				Date tempData = tempSpecialData.getSpecialData();
				int birthdayYear = Integer.parseInt(DateUtils.getYear());
				int birthdayMonth = DateUtils.getMonthByDate(tempData)+1;
				int birthdayDay = DateUtils.getDayByDate(tempData);
				String datestr = birthdayYear+"-"+birthdayMonth+"-"+birthdayDay;
				Date roledate = DateUtils.parseDate(datestr);
				
				User user = UserUtils.getUser(tempSpecialData.getUserId());
				
				if(tempSpecialData.getSolarorlunar().equals("1")){//阳历
					if(DateUtils.subtractionDays(roledate, DateUtils.getSysDate()) == 0){
						if(DateUtils.subtractionDays(roledate, tempData) != 0 && tempSpecialData.getInfoCirculate().equals("2"))
							continue;
						
						int flag = 0;
						try {
							//判断是否是同机构
							if(user.getOrgId().equals(SystemSecurityUtils.getUser().getOrgId())){
								flag = 1;
							} else {
								flag = specialdataShareService.checkUserPower(tempSpecialData.getId(), shareUserId, shareDeptId, shareOrganId);
							}
							if(flag == 1){
								reutrnbirthdays.add(tempSpecialData);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if(tempSpecialData.getSolarorlunar().equals("2")){//阴历
					String solarday = SolarorlunarUtils.lunarTosolar(datestr);
					if(DateUtils.subtractionDays(DateUtils.parseDate(solarday), DateUtils.getSysDate()) == 0){
						if(DateUtils.subtractionDays(roledate, tempData) != 0 && tempSpecialData.getInfoCirculate().equals("2"))
							continue;
						int flag = 0;
						try {
							//判断是否是同机构
							if(user.getOrgId().equals(SystemSecurityUtils.getUser().getOrgId())){
								flag = 1;
							} else {
								flag = specialdataShareService.checkUserPower(tempSpecialData.getId(), shareUserId, shareDeptId, shareOrganId);									
							}
							if(flag == 1){
								reutrnbirthdays.add(tempSpecialData);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		result.put("birthdayList",reutrnbirthdays);
		specialData.setInfoType("0");
		List<SpecialData> festivalList = specialDataDao.queryAll(specialData);
		List<SpecialData> reutrnfestivals = new ArrayList<SpecialData>(); 
		if(festivalList != null && festivalList.size() > 0){
			int birthdayYear = Integer.parseInt(DateUtils.getYear());
			
			//排序节日信息
			List<SpecialData> tempfestivals = new ArrayList<SpecialData>(); 
			for(int i=0;i<festivalList.size();i++){
				SpecialData tempSpecialData = festivalList.get(i);
				Date tempData = tempSpecialData.getSpecialData();
				int birthdayMonth = DateUtils.getMonthByDate(tempData)+1;
				int birthdayDay = DateUtils.getDayByDate(tempData);
				String datestr = birthdayYear+"-"+birthdayMonth+"-"+birthdayDay;
				Date roledate = DateUtils.parseDate(datestr);
				tempSpecialData.setSpecialData(roledate);
				tempfestivals.add(tempSpecialData);
			}
			
			Collections.sort(tempfestivals, new Comparator<SpecialData>(){  
		          public int compare(SpecialData a, SpecialData b) {  
		        	 //第一次比较行标
		        	 int i = a.getSpecialData().compareTo(b.getSpecialData());
		        	 return i;
		        }
		    }); 
			
			for(int i=0;i<tempfestivals.size();i++){
				SpecialData tempSpecialData = tempfestivals.get(i);
				Date tempData = tempSpecialData.getSpecialData();
				int birthdayMonth = DateUtils.getMonthByDate(tempData)+1;
				int birthdayDay = DateUtils.getDayByDate(tempData);
				String datestr = birthdayYear+"-"+birthdayMonth+"-"+birthdayDay;
				Date roledate = DateUtils.parseDate(datestr);
				if(tempSpecialData.getSolarorlunar().equals("1")){//阳历
					if(DateUtils.subtractionDays(roledate, DateUtils.getSysDate()) >= 0){
						int flag;
						try {
							if(tempSpecialData.getOpenLevel().equals("2"))
								flag = specialdataShareService.checkUserPower(tempSpecialData.getId(), shareUserId, shareDeptId, shareOrganId);
							else
								flag = 1;
							if(flag == 1){
								tempSpecialData.setSpecialData(roledate);
								reutrnfestivals.add(tempSpecialData);
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if(tempSpecialData.getSolarorlunar().equals("2")){//阴历
					String solarday = SolarorlunarUtils.lunarTosolar(datestr);
					if(DateUtils.subtractionDays(DateUtils.parseDate(solarday), DateUtils.getSysDate()) >= 0){
						int flag;
						try {
							if(tempSpecialData.getOpenLevel().equals("2"))
								flag = specialdataShareService.checkUserPower(tempSpecialData.getId(), shareUserId, shareDeptId, shareOrganId);
							else
								flag = 1;
							if(flag == 1){
								tempSpecialData.setSpecialData(roledate);
								reutrnfestivals.add(tempSpecialData);
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			if(reutrnfestivals.size() == 0){
				birthdayYear = birthdayYear + 1;
				Date tempSysDate = DateUtils.parseDate(birthdayYear+"-1-1");
				for(int i=0;i<tempfestivals.size();i++){
					SpecialData tempSpecialData = tempfestivals.get(i);
					Date tempData = tempSpecialData.getSpecialData();
					int birthdayMonth = DateUtils.getMonthByDate(tempData)+1;
					int birthdayDay = DateUtils.getDayByDate(tempData);
					String datestr = birthdayYear+"-"+birthdayMonth+"-"+birthdayDay;
					Date roledate = DateUtils.parseDate(datestr);
					if(tempSpecialData.getSolarorlunar().equals("1")){//阳历
						if(DateUtils.subtractionDays(roledate, tempSysDate) >= 0){
							int flag;
							try {
								if(tempSpecialData.getOpenLevel().equals("2"))
									flag = specialdataShareService.checkUserPower(tempSpecialData.getId(), shareUserId, shareDeptId, shareOrganId);
								else
									flag = 1;
								if(flag == 1){
									tempSpecialData.setSpecialData(roledate);
									reutrnfestivals.add(tempSpecialData);
									break;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if(tempSpecialData.getSolarorlunar().equals("2")){//阴历
						String solarday = SolarorlunarUtils.lunarTosolar(datestr);
						if(DateUtils.subtractionDays(DateUtils.parseDate(solarday), tempSysDate) >= 0){
							int flag;
							try {
								if(tempSpecialData.getOpenLevel().equals("2"))
									flag = specialdataShareService.checkUserPower(tempSpecialData.getId(), shareUserId, shareDeptId, shareOrganId);
								else
									flag = 1;
								if(flag == 1){
									tempSpecialData.setSpecialData(roledate);
									reutrnfestivals.add(tempSpecialData);
									break;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		
		result.put("festivalList",reutrnfestivals);
		
		return result;
	}

	public SpecialData getBirthdayBlessing(Long UserId) throws CustomException {
		SpecialData specialData = new SpecialData();
		specialData.addOrderByField("t.SPECIAL_DATA");
		specialData.setUserId(UserId);
		specialData.setDeleteFlag(0);
		specialData.setInfoType("1");
		specialData.setSendpictureStatus("2");
		List<SpecialData> birthdayList = specialDataDao.queryAll(specialData);
		if(birthdayList != null && birthdayList.size() > 0){
			for(int i=0;i<birthdayList.size();i++){
				SpecialData temp = birthdayList.get(i);
				if(temp.getSendmailStatus().equals("2")){
					Map<String,String> paramMap = new HashMap<String, String>();
					paramMap.put(IInteractFacadeService.MAIL_SENDER_ID, UserId.toString());
					paramMap.put(IInteractFacadeService.MAIL_RECEIVER_IDS, UserId.toString());
					paramMap.put(IInteractFacadeService.MAIL_CONTENT,temp.getSummaryContent());
					//paramMap.put(IInteractFacadeService.MAIL_CONTENT,"年年有今朝，岁岁乐陶陶。愿你一直保持嘴角30度的微笑弧度，永远拥有内心100度的青春热度。请感受朋友120度真诚的祝福温度：生日快乐！");
					paramMap.put(IInteractFacadeService.MAIL_SUBJECT,"祝您生日快乐");
					interactFacadeService.sendMail(paramMap);
				}
				temp.setSendpictureStatus("1");
				temp.setSendmailStatus("1");
				temp.setModifyDateNew(new Date());
				specialDataDao.update(temp);
			}
//			SpecialData vo = new SpecialData();
//			vo.setModifyDateBegin(new Date());
//			specialDataDao.updateSendStatus(vo);
			return specialData;
		}else
			return null;
	}

}