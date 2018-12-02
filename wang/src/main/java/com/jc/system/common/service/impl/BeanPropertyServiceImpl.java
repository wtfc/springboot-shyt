package com.jc.system.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.service.IBeanPropertyService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;

@Service
public class BeanPropertyServiceImpl implements IBeanPropertyService {
	Logger logger = Logger.getLogger(BeanPropertyServiceImpl.class);

	public BeanPropertyServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fillProperties(BaseBean bean, boolean modify) {
		// TODO Auto-generated method stub
		Date now = new Date();
		if (bean.getCreateDate() == null && !modify) {
			bean.setCreateDate(now);
		}
		if (bean.getModifyDate() == null) {
			bean.setModifyDate(now);
		}
		if (modify) {
			bean.setModifyDateNew(now);
		}
		User user = getCurrentUser();
		if (!modify) {
			if (bean.getCreateUser() == null) {
				bean.setCreateUser(user.getId());
			}
			if (bean.getCreateUserDept() == null) {
				bean.setCreateUserDept(user.getDeptId());
			}
			if (bean.getCreateUserOrg() == null) {
				bean.setCreateUserOrg(user.getOrgId());
			}
		}
		if (bean.getModifyUser() == null) {
			bean.setModifyUser(user.getId());
		}
	}

	private User getCurrentUser() {
		User user = null;
		try {
			user = SystemSecurityUtils.getUser();
		} catch (Exception e) {
			// TODO: handle exception
			user = new User();
			user.setId(-1L);
			user.setDeptId(-1L);
			user.setOrgId(-1L);
			// logger.error("设置用户异常", e);
		}
		if(user == null){
			user = new User();
			user.setId(-1L);
			user.setDeptId(-1L);
			user.setOrgId(-1L);
		}
		return user;
	}

	@Override
	public <T extends BaseBean> List<T> fillProperties(List<T> list,
			boolean modify) {
		// TODO Auto-generated method stub
		Date now = new Date();
		User user = getCurrentUser();
		List<T> clone = new ArrayList<>();
		for (T bean : list) {
			// BaseBean bean = (BaseBean) t;
			if (bean.getCreateDate() == null && !modify) {
				bean.setCreateDate(now);
			}
			if (bean.getModifyDate() == null) {
				bean.setModifyDate(now);
			}
			if (modify) {
				bean.setModifyDateNew(now);
			}

			if (bean.getCreateUser() == null) {
				bean.setCreateUser(user.getId());
			}
			if (bean.getModifyUser() == null) {
				bean.setModifyUser(user.getId());
			}
			if (bean.getCreateUserDept() == null) {
				bean.setCreateUserDept(user.getDeptId());
			}
			if (bean.getCreateUserOrg() == null) {
				bean.setCreateUserOrg(user.getOrgId());
			}

			clone.add(bean);
		}
		return clone;
	}

}
