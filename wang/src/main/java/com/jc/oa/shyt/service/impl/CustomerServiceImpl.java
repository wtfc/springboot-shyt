package com.jc.oa.shyt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.shyt.domain.CustomerPeople;
import com.jc.oa.shyt.domain.CustomerPeoples;
import com.jc.oa.shyt.domain.Customer;
import com.jc.oa.shyt.dao.ICustomerPeopleDao;
import com.jc.oa.shyt.dao.ICustomerDao;
import com.jc.oa.shyt.service.ICustomerService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
/**
 * @author mrb
 * @version 合同资源表
*/
@Service
public class  CustomerServiceImpl  extends BaseServiceImpl<Customer> implements ICustomerService {

	public CustomerServiceImpl(){}	

    private ICustomerDao toaShytCustomerDao;
    
    @Autowired
    private ICustomerPeopleDao customerPeopleDao;

	@Autowired
	public CustomerServiceImpl(ICustomerDao toaShytCustomerDao){
		super(toaShytCustomerDao);
		this.toaShytCustomerDao = toaShytCustomerDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Customer toaShytCustomer) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaShytCustomer,true);
			result = toaShytCustomerDao.delete(toaShytCustomer);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShytCustomer);
			throw ce;
		}
		return result;
	}

	/**
	 * @description 保存方法
	 * @param MeetingSpirit meetingSpirit 实体类
	 * @return Integer 增加的记录数
	 * @author 
	 * @version 
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer savePeople(Customer toaShytCustomer,CustomerPeoples customerPeoples)throws CustomException {
		try {
			//保存通用字段
			propertyService.fillProperties(toaShytCustomer,false);
			// 保存意见
			toaShytCustomer.setExtStr1("0");
			toaShytCustomerDao.save(toaShytCustomer);
			if(!StringUtil.isEmpty(customerPeoples.getName())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard())){
					String birthday = customerPeoples.getIdCard().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel());
				customerPeople.setWeixin(customerPeoples.getWeixin());
				customerPeople.setName(customerPeoples.getName());
				customerPeople.setJob(customerPeoples.getJob());
				customerPeople.setEmail(customerPeoples.getEmail());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName1())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard1())){
					String birthday = customerPeoples.getIdCard1().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard1());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel1());
				customerPeople.setWeixin(customerPeoples.getWeixin1());
				customerPeople.setName(customerPeoples.getName1());
				customerPeople.setJob(customerPeoples.getJob1());
				customerPeople.setEmail(customerPeoples.getEmail1());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName2())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard2())){
					String birthday = customerPeoples.getIdCard2().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard2());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel2());
				customerPeople.setWeixin(customerPeoples.getWeixin2());
				customerPeople.setName(customerPeoples.getName2());
				customerPeople.setJob(customerPeoples.getJob2());
				customerPeople.setEmail(customerPeoples.getEmail2());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName3())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard3())){
					String birthday = customerPeoples.getIdCard3().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard3());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel3());
				customerPeople.setWeixin(customerPeoples.getWeixin3());
				customerPeople.setName(customerPeoples.getName3());
				customerPeople.setJob(customerPeoples.getJob3());
				customerPeople.setEmail(customerPeoples.getEmail3());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName4())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard4())){
					String birthday = customerPeoples.getIdCard4().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard4());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel4());
				customerPeople.setWeixin(customerPeoples.getWeixin4());
				customerPeople.setName(customerPeoples.getName4());
				customerPeople.setJob(customerPeoples.getJob4());
				customerPeople.setEmail(customerPeoples.getEmail4());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName5())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard5())){
					String birthday = customerPeoples.getIdCard5().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard5());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel5());
				customerPeople.setWeixin(customerPeoples.getWeixin5());
				customerPeople.setName(customerPeoples.getName5());
				customerPeople.setJob(customerPeoples.getJob5());
				customerPeople.setEmail(customerPeoples.getEmail5());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName6())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard6())){
					String birthday = customerPeoples.getIdCard6().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard6());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel6());
				customerPeople.setWeixin(customerPeoples.getWeixin6());
				customerPeople.setName(customerPeoples.getName6());
				customerPeople.setJob(customerPeoples.getJob6());
				customerPeople.setEmail(customerPeoples.getEmail6());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName7())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard7())){
					String birthday = customerPeoples.getIdCard7().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard7());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel7());
				customerPeople.setWeixin(customerPeoples.getWeixin7());
				customerPeople.setName(customerPeoples.getName7());
				customerPeople.setJob(customerPeoples.getJob7());
				customerPeople.setEmail(customerPeoples.getEmail7());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName8())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard8())){
					String birthday = customerPeoples.getIdCard8().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard8());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel8());
				customerPeople.setWeixin(customerPeoples.getWeixin8());
				customerPeople.setName(customerPeoples.getName8());
				customerPeople.setJob(customerPeoples.getJob8());
				customerPeople.setEmail(customerPeoples.getEmail8());
				customerPeopleDao.save(customerPeople);
			}
			if(!StringUtil.isEmpty(customerPeoples.getName9())){
				CustomerPeople customerPeople = new CustomerPeople();
				//保存通用字段
				propertyService.fillProperties(customerPeople,false);
				customerPeople.setCustomerId(toaShytCustomer.getId().intValue());
				customerPeople.setCompanyName(toaShytCustomer.getCompanyName());
				if(!StringUtil.isEmpty(customerPeoples.getIdCard9())){
					String birthday = customerPeoples.getIdCard9().substring(10, 12);
					customerPeople.setExtStr2(birthday);
					customerPeople.setIdCard(customerPeoples.getIdCard9());
					customerPeople.setExtStr3("0");
				}
				customerPeople.setExtStr1("0");
				customerPeople.setTel(customerPeoples.getTel9());
				customerPeople.setWeixin(customerPeoples.getWeixin9());
				customerPeople.setName(customerPeoples.getName9());
				customerPeople.setJob(customerPeoples.getJob9());
				customerPeople.setEmail(customerPeoples.getEmail9());
				customerPeopleDao.save(customerPeople);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

}
