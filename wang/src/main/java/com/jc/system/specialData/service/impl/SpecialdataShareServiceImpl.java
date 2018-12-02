package com.jc.system.specialData.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.UserUtils;
import com.jc.system.specialData.dao.ISpecialdataShareDao;
import com.jc.system.specialData.domain.SpecialdataShare;
import com.jc.system.specialData.service.ISpecialdataShareService;


/**
 * @title 172.16.3.68
 * @description  业务服务类
 * @author 
 * @version  2014-12-02
 */
@Service
public class SpecialdataShareServiceImpl extends BaseServiceImpl<SpecialdataShare> implements ISpecialdataShareService{

	private ISpecialdataShareDao specialdataShareDao;
	
	public SpecialdataShareServiceImpl(){}
	
	@Autowired
	public SpecialdataShareServiceImpl(ISpecialdataShareDao specialdataShareDao){
		super(specialdataShareDao);
		this.specialdataShareDao = specialdataShareDao;
	}

	/**
	* @description 根据主键删除多条记录方法
	* @param SpecialdataShare specialdataShare 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-12-02 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(SpecialdataShare specialdataShare) throws CustomException{
		Integer result = -1;
		try{
			result = specialdataShareDao.delete(specialdataShare);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(specialdataShare);
			throw ce;
		}
		return result;
	}

	public Map<String, String> getIds(SpecialdataShare SpecialdataShare)
			throws Exception {
		List<SpecialdataShare> list = specialdataShareDao.queryAll(SpecialdataShare);
		Map<String, String> result = new HashMap<String, String>();
		String userids="";
		String deptids="";
		String organids="";
		Iterator<SpecialdataShare> iterator = list.iterator();
		while(iterator.hasNext()){
			SpecialdataShare rShare = iterator.next();
			String userid = String.valueOf(rShare.getShareUsers());
			if(!(userid==null||"".equals(String.valueOf(userid))||"null".equals(String.valueOf(userid)))){
				User user = UserUtils.getUser(Long.parseLong(userid));
				if(user != null){
					userids += "{\"id\":\""+userid+"\",\"text\":\""+user.getDisplayName()+"\"},";
				}
			}
			String deptid = String.valueOf(rShare.getShareDept());
			if(!(deptid==null||"".equals(String.valueOf(deptid))||"null".equals(String.valueOf(deptid)))){
				if("".equals(deptids))
					deptids = deptid;
				else
					deptids+=","+deptid;
			}
			String organid = String.valueOf(rShare.getShareOrg());
			if(!(organid==null||"".equals(String.valueOf(organid))||"null".equals(String.valueOf(organid)))){
				if("".equals(organids))
					organids = organid;
				else
					organids+=","+organid;
			}
		}
		if(userids.length() > 0){
			result.put("userids","["+userids.substring(0,userids.length()-1)+"]");
		} else {
			result.put("userids",userids);
		}
		result.put("deptids",deptids);
		result.put("organids",organids);
		return result;
	}

	public Integer delAndsave(SpecialdataShare specialdataShare,
			String userids, String deptids, String organids) throws Exception {
		
		List<SpecialdataShare> list = new ArrayList<SpecialdataShare>();
		String[] user = userids.split(",");
		Long specialdataId = specialdataShare.getSpecialdataId();
		for(int i=0;i<user.length;i++){
			SpecialdataShare item = new SpecialdataShare();
			if(user[i]==null||"".equals(user[i]))
				continue;
			item.setSpecialdataId(specialdataId);
			item.setShareUsers(Long.parseLong(user[i]));
			list.add(item);
		}
		String[] dept = deptids.split(",");
		for(int i=0;i<dept.length;i++){
			SpecialdataShare item = new SpecialdataShare();
			if(dept[i]==null||"".equals(dept[i]))
				continue;
			item.setSpecialdataId(specialdataId);
			item.setShareDept(Long.parseLong(dept[i]));
			list.add(item);
		}
		if(StringUtils.isNotEmpty(organids)){
			String[] organ = organids.split(",");
			for(int i=0;i<organ.length;i++){
				SpecialdataShare item = new SpecialdataShare();
				if(organ[i]==null||"".equals(organ[i]))
					continue;
				item.setSpecialdataId(specialdataId);
				item.setShareOrg(Long.parseLong(organ[i]));
				list.add(item);
			}
		}
		specialdataShareDao.deleteForSpecialId(specialdataShare);
		
		return specialdataShareDao.save(list);
	}

	@Override
	public Integer checkUserPower(Long specialDataId,Long shareUserId, Long shareDeptId, Long shareOrganId)
			throws Exception {
		int flag = 0;
		SpecialdataShare item = new SpecialdataShare();
		item.setSpecialdataId(specialDataId);
		List<SpecialdataShare> list = specialdataShareDao.queryAll(item);
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				SpecialdataShare vo = list.get(i);
				if(vo.getShareUsers() != null && vo.getShareUsers().equals(shareUserId)){
					flag = 1;
				} else if(vo.getShareDept() != null && vo.getShareDept().equals(shareDeptId)){
					flag = 1;
				} else if(vo.getShareOrg() != null && vo.getShareOrg().equals(shareOrganId)){
					flag = 1;
				}
			}
		}
		
		return flag;
	}

}