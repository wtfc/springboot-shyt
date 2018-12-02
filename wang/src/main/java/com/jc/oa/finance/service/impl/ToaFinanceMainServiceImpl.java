package com.jc.oa.finance.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.finance.domain.Invoices;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
import com.jc.oa.finance.domain.ToaFinanceMain;
import com.jc.oa.finance.dao.IToaFinanceInvoicesDao;
import com.jc.oa.finance.dao.IToaFinanceMainDao;
import com.jc.oa.finance.service.IToaFinanceMainService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;

/**
 * @author mrb
 * @version 收入主体表
*/
@Service
public class  ToaFinanceMainServiceImpl  extends BaseServiceImpl<ToaFinanceMain> implements IToaFinanceMainService {

	public ToaFinanceMainServiceImpl(){}	

    private IToaFinanceMainDao toaFinanceMainDao;
    
    @Autowired
    private IToaFinanceInvoicesDao toaFinanceInvoicesDao;

	@Autowired
	public ToaFinanceMainServiceImpl(IToaFinanceMainDao toaFinanceMainDao){
		super(toaFinanceMainDao);
		this.toaFinanceMainDao = toaFinanceMainDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaFinanceMain toaFinanceMain) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaFinanceMain,true);
			result = toaFinanceMainDao.delete(toaFinanceMain);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinanceMain);
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
	public Integer saveMainInvoices(ToaFinanceMain toaFinanceMain,Invoices invoices)throws CustomException {
		try {
			//保存通用字段
			propertyService.fillProperties(toaFinanceMain,false);
			// 保存意见
			toaFinanceMainDao.save(toaFinanceMain);
//				if(invoice.getId() != null){
//					invoice.setModifyDate(DateUtils.getSysDate());
//					toaFinanceInvoicesDao.update(invoice);
//				} else{
//					invoice.setCreateDate(DateUtils.getSysDate());
//					invoice.setModifyDate(DateUtils.getSysDate());
//			Date lastDate=null;
//			for(int i=1;i<13;i++){
//				if(i==12&&invoices.getMonthAmount12()!=null&&!invoices.getMonthAmount12().equals("")){
//					lastDate=invoices.getInvoicesEnddate12();
//				}else if(i==11&&invoices.getMonthAmount11()!=null&&!invoices.getMonthAmount11().equals("")){
//					lastDate=invoices.getInvoicesEnddate11();
//				}else if(i==10&&invoices.getMonthAmount10()!=null&&!invoices.getMonthAmount10().equals("")){
//					lastDate=invoices.getInvoicesEnddate10();
//				}else if(i==9&&invoices.getMonthAmount9()!=null&&!invoices.getMonthAmount9().equals("")){
//					lastDate=invoices.getInvoicesEnddate9();
//				}else if(i==8&&invoices.getMonthAmount8()!=null&&!invoices.getMonthAmount8().equals("")){
//					lastDate=invoices.getInvoicesEnddate8();
//				}else if(i==7&&invoices.getMonthAmount7()!=null&&!invoices.getMonthAmount7().equals("")){
//					lastDate=invoices.getInvoicesEnddate7();
//				}else if(i==6&&invoices.getMonthAmount6()!=null&&!invoices.getMonthAmount6().equals("")){
//					lastDate=invoices.getInvoicesEnddate6();
//				}else if(i==5&&invoices.getMonthAmount5()!=null&&!invoices.getMonthAmount5().equals("")){
//					lastDate=invoices.getInvoicesEnddate5();
//				}else if(i==4&&invoices.getMonthAmount4()!=null&&!invoices.getMonthAmount4().equals("")){
//					lastDate=invoices.getInvoicesEnddate4();
//				}else if(i==3&&invoices.getMonthAmount3()!=null&&!invoices.getMonthAmount3().equals("")){
//					lastDate=invoices.getInvoicesEnddate3();
//				}else if(i==2&&invoices.getMonthAmount2()!=null&&!invoices.getMonthAmount2().equals("")){
//					lastDate=invoices.getInvoicesEnddate2();
//				}else if(i==1&&invoices.getMonthAmount1()!=null&&!invoices.getMonthAmount1().equals("")){
//					lastDate=invoices.getInvoicesEnddate1();
//				}
//			}
//			Date firstDate=invoices.getInvoicesStartdate1();
			if(invoices.getMonthAmount12()!=null&&!invoices.getMonthAmount12().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				propertyService.fillProperties(invoice,false);
				invoice.setInvoicesMonth(invoices.getInvoicesMonth12());
				invoice.setMonthAmount(invoices.getMonthAmount12());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate12());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate12());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCommision(invoices.getCommision12());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount1()!=null&&!invoices.getMonthAmount1().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth1());
				invoice.setMonthAmount(invoices.getMonthAmount1());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate1());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate1());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				propertyService.fillProperties(invoice,false);
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision1());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount2()!=null&&!invoices.getMonthAmount2().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth2());
				invoice.setMonthAmount(invoices.getMonthAmount2());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate2());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate2());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision2());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount3()!=null&&!invoices.getMonthAmount3().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth3());
				invoice.setMonthAmount(invoices.getMonthAmount3());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate3());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate3());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision3());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount4()!=null&&!invoices.getMonthAmount4().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth4());
				invoice.setMonthAmount(invoices.getMonthAmount4());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate4());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate4());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision4());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount5()!=null&&!invoices.getMonthAmount5().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth5());
				invoice.setMonthAmount(invoices.getMonthAmount5());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate5());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate5());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision5());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount6()!=null&&!invoices.getMonthAmount6().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth6());
				invoice.setMonthAmount(invoices.getMonthAmount6());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate6());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate6());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision6());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount7()!=null&&!invoices.getMonthAmount7().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth7());
				invoice.setMonthAmount(invoices.getMonthAmount7());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate7());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate7());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCommision(invoices.getCommision7());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount8()!=null&&!invoices.getMonthAmount8().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth8());
				invoice.setMonthAmount(invoices.getMonthAmount8());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate8());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate8());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision8());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount9()!=null&&!invoices.getMonthAmount9().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth9());
				invoice.setMonthAmount(invoices.getMonthAmount9());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate9());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate9());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCommision(invoices.getCommision9());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount10()!=null&&!invoices.getMonthAmount10().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth10());
				invoice.setMonthAmount(invoices.getMonthAmount10());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate10());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate10());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision10());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(invoices.getMonthAmount11()!=null&&!invoices.getMonthAmount11().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth11());
				invoice.setMonthAmount(invoices.getMonthAmount11());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate11());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate11());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setSale(toaFinanceMain.getSale());
				invoice.setMaintenanSale(toaFinanceMain.getMaintenanSale());
				invoice.setCommision(invoices.getCommision11());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Integer updateMainInvoices(ToaFinanceMain toaFinanceMain,
			Invoices invoices) throws CustomException {
		Integer result = -1;
		try{
			propertyService.fillProperties(toaFinanceMain,true);
			toaFinanceMainDao.update(toaFinanceMain);
			
			String ids="";
			if(!StringUtil.isEmpty(invoices.getInvoicesId1())){
				ids=invoices.getInvoicesId1();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId2())){
				ids=ids+","+invoices.getInvoicesId2();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId3())){
				ids=ids+","+invoices.getInvoicesId3();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId4())){
				ids=ids+","+invoices.getInvoicesId4();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId5())){
				ids=ids+","+invoices.getInvoicesId5();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId6())){
				ids=ids+","+invoices.getInvoicesId6();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId7())){
				ids=ids+","+invoices.getInvoicesId7();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId8())){
				ids=ids+","+invoices.getInvoicesId8();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId9())){
				ids=ids+","+invoices.getInvoicesId9();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId10())){
				ids=ids+","+invoices.getInvoicesId10();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId11())){
				ids=ids+","+invoices.getInvoicesId11();
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesId12())){
				ids=ids+","+invoices.getInvoicesId12();
			}
			ToaFinanceInvoices invoice1 = new ToaFinanceInvoices();
			propertyService.fillProperties(invoice1,true);
			invoice1.setPrimaryKeys(ids.split(","));
			toaFinanceInvoicesDao.delete(invoice1);
			
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth12())&&invoices.getMonthAmount12()!=null&&!invoices.getMonthAmount12().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				propertyService.fillProperties(invoice,false);
				invoice.setInvoicesMonth(invoices.getInvoicesMonth12());
				invoice.setMonthAmount(invoices.getMonthAmount12());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate12());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate12());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision12());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth1())&&invoices.getMonthAmount1()!=null&&!invoices.getMonthAmount1().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth1());
				invoice.setMonthAmount(invoices.getMonthAmount1());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate1());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate1());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				propertyService.fillProperties(invoice,false);
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision1());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth2())&&invoices.getMonthAmount2()!=null&&!invoices.getMonthAmount2().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth2());
				invoice.setMonthAmount(invoices.getMonthAmount2());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate2());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate2());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision2());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth3())&&invoices.getMonthAmount3()!=null&&!invoices.getMonthAmount3().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth3());
				invoice.setMonthAmount(invoices.getMonthAmount3());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate3());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate3());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision3());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth4())&&invoices.getMonthAmount4()!=null&&!invoices.getMonthAmount4().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth4());
				invoice.setMonthAmount(invoices.getMonthAmount4());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate4());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate4());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision4());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth5())&&invoices.getMonthAmount5()!=null&&!invoices.getMonthAmount5().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth5());
				invoice.setMonthAmount(invoices.getMonthAmount5());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate5());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate5());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision5());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth6())&&invoices.getMonthAmount6()!=null&&!invoices.getMonthAmount6().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth6());
				invoice.setMonthAmount(invoices.getMonthAmount6());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate6());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate6());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision6());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth7())&&invoices.getMonthAmount7()!=null&&!invoices.getMonthAmount7().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth7());
				invoice.setMonthAmount(invoices.getMonthAmount7());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate7());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate7());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision7());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth8())&&invoices.getMonthAmount8()!=null&&!invoices.getMonthAmount8().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth8());
				invoice.setMonthAmount(invoices.getMonthAmount8());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate8());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate8());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision8());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth9())&&invoices.getMonthAmount9()!=null&&!invoices.getMonthAmount9().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth9());
				invoice.setMonthAmount(invoices.getMonthAmount9());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate9());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate9());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision9());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth10())&&invoices.getMonthAmount10()!=null&&!invoices.getMonthAmount10().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth10());
				invoice.setMonthAmount(invoices.getMonthAmount10());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate10());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate10());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision10());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			if(!StringUtil.isEmpty(invoices.getInvoicesMonth11())&&invoices.getMonthAmount11()!=null&&!invoices.getMonthAmount11().equals("")){
				ToaFinanceInvoices invoice = new ToaFinanceInvoices();
				invoice.setInvoicesMonth(invoices.getInvoicesMonth11());
				invoice.setMonthAmount(invoices.getMonthAmount11());
				invoice.setInvoicesStartdate(invoices.getInvoicesStartdate11());
				invoice.setInvoicesEnddate(invoices.getInvoicesEnddate11());
				invoice.setMainId(toaFinanceMain.getId().intValue());
				invoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				invoice.setCompanyName(toaFinanceMain.getCompanyName());
				invoice.setCompanyType(toaFinanceMain.getCompanyType());
				invoice.setCommision(invoices.getCommision11());
				invoice.setResourceType(toaFinanceMain.getResourceType());
				invoice.setDepartment(toaFinanceMain.getDepartment());
				invoice.setOtherDeductions(0);
				propertyService.fillProperties(invoice,false);
				toaFinanceInvoicesDao.save(invoice);
			}
			result=1;
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinanceMain);
			throw ce;
		}
		return result;
	}

}
