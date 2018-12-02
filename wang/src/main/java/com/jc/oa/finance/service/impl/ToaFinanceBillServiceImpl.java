package com.jc.oa.finance.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.finance.domain.ToaFinanceBill;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
import com.jc.oa.finance.dao.IToaFinanceBillDao;
import com.jc.oa.finance.dao.IToaFinanceInvoicesDao;
import com.jc.oa.finance.service.IToaFinanceBillService;
import com.jc.oa.finance.service.IToaFinanceInvoicesService;
import com.jc.oa.shyt.domain.Customer;
import com.jc.oa.shyt.domain.CustomerPeople;
import com.jc.oa.shyt.service.ICustomerPeopleService;
import com.jc.oa.shyt.service.ICustomerService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
/**
 * @author mrb
 * @version 账单信息
*/
@Service
public class  ToaFinanceBillServiceImpl  extends BaseServiceImpl<ToaFinanceBill> implements IToaFinanceBillService {

	public ToaFinanceBillServiceImpl(){}	

    private IToaFinanceBillDao toaFinanceBillDao;
    
    @Autowired
    private IToaFinanceInvoicesDao toaFinanceInvoicesDao;
    
    @Autowired
    private IToaFinanceInvoicesService toaFinanceInvoicesService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private ICustomerPeopleService customerPeopleService;

	@Autowired
	public ToaFinanceBillServiceImpl(IToaFinanceBillDao toaFinanceBillDao){
		super(toaFinanceBillDao);
		this.toaFinanceBillDao = toaFinanceBillDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaFinanceBill toaFinanceBill) throws CustomException{
		Integer result = -1;
		try{
			ToaFinanceBill toaFinanceBills = super.get(toaFinanceBill);
			propertyService.fillProperties(toaFinanceBill,true);
			result = toaFinanceBillDao.delete(toaFinanceBill);
			String ids = toaFinanceBills.getRemarkId();
			String[] idList = ids.split(",");
			ToaFinanceInvoices invoce = new ToaFinanceInvoices();
			invoce.setPrimaryKeys(idList);
			for (String id : idList) {
				//输出
				ToaFinanceInvoices invoces = new ToaFinanceInvoices();
				invoces.setId(Long.valueOf(id));
				invoces = toaFinanceInvoicesService.get(invoces);
				ToaFinanceInvoices newInvoces = new ToaFinanceInvoices();
				newInvoces.setCompanyId(invoces.getCompanyId());
				newInvoces.setCompanyName(invoces.getCompanyName());
				newInvoces.setCompanyType(invoces.getCompanyType());
				newInvoces.setInvoicesMonth(invoces.getInvoicesMonth());
				newInvoces.setResourceType(invoces.getResourceType());
				newInvoces.setDepartment(invoces.getDepartment());
				newInvoces.setMainId(invoces.getMainId());
				newInvoces.setMonthAmount(invoces.getMonthAmount());
				newInvoces.setInvoicesStartdate(invoces.getInvoicesStartdate());
				newInvoces.setInvoicesEnddate(invoces.getInvoicesEnddate());
				newInvoces.setCommision(invoces.getCommision());
				newInvoces.setOtherDeductions(0);
				propertyService.fillProperties(newInvoces,false);
				toaFinanceInvoicesService.save(newInvoces);
				}
			toaFinanceInvoicesService.deleteByIds(invoce);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinanceBill);
			throw ce;
		}
		return result;
	}

	@Override
	public List<ToaFinanceInvoices> involvesList(ToaFinanceInvoices financeInvoices)
			throws CustomException {
		// TODO Auto-generated method stub
		return toaFinanceInvoicesDao.getMain(financeInvoices);
	}

	@Override
	public Integer state(ToaFinanceBill toaFinanceBill) throws CustomException {
		Integer result = -1;
		try{
			propertyService.fillProperties(toaFinanceBill,true);
			result = toaFinanceBillDao.state(toaFinanceBill);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinanceBill);
			throw ce;
		}
		return result;
	}
	
	@Override
	public Integer invoicesBill() throws CustomException{
		Integer result=-1;
		try{
			//时间
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
			Date billDate=new Date();
			Calendar cal=Calendar.getInstance();
			cal.setTime(billDate);
			//cal.add(Calendar.MONTH, 1);
			ToaFinanceInvoices toaFinanceInvoices=new ToaFinanceInvoices();
			toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
			toaFinanceInvoices.setOtherDeductions(0);
			toaFinanceInvoices.setDeleteFlag(0);
			//查询出需要出账单的条数据(需要在写一个方法进行查询)//方法指查询出月收入和主体表中需要的信息			
			List<ToaFinanceInvoices> toaFinanceList=toaFinanceInvoicesService.queryInvoices(toaFinanceInvoices);
			int toaFinanceCount=toaFinanceList.size();
			if(toaFinanceCount>0){
				for(int i=0;i<toaFinanceCount;i++){
					//把查询出来的信息付值到BIll账单表进行保存，然后在修改月收入表中other_deductions的状态
					ToaFinanceInvoices toaInvoices=toaFinanceList.get(i);
					ToaFinanceBill toaFinanceBill=new ToaFinanceBill();
					toaFinanceBill.setCompanyId(String.valueOf(toaInvoices.getCompanyId()));
					toaFinanceBill.setCompanyName(toaInvoices.getCompanyName());
					toaFinanceBill.setBillMoney(toaInvoices.getMonthAmount());
					toaFinanceBill.setServiceType(toaInvoices.getCompanyType());
					//客户
					Customer customer=new Customer();
					customer.setId( Long.valueOf(toaInvoices.getCompanyId()));
					customer.setExtStr1("0");
					Customer newCustomer=customerService.get(customer);
					 if(newCustomer!=null){
						 //客户联系信息表
//						 CustomerPeople customerPeople=new CustomerPeople();
//						 customerPeople.setCustomerId(newCustomer.getId().intValue());
//						 customerPeople.setDeleteFlag(0);
//						 List<CustomerPeople> customerList=customerPeopleService.queryAll(customerPeople);
//						 if(customerList != null){
//							 CustomerPeople customerNewPeople=customerList.get(0);
//							 toaFinanceBill.setPhone(customerNewPeople.getTel());
//						 }
						 toaFinanceBill.setPhone(newCustomer.getExtStr2());
						 toaFinanceBill.setCustmersTaxid(newCustomer.getTaxid());
						 toaFinanceBill.setAddress(newCustomer.getNewAddress());
						 toaFinanceBill.setBankName(newCustomer.getBankName());
						 toaFinanceBill.setBankNo(newCustomer.getBankNo());
						 toaFinanceBill.setTicket(newCustomer.getTicketFlag());
						 toaFinanceBill.setStartIntel(newCustomer.getStartIntel());
						 if(StringUtil.isEmpty(newCustomer.getTradeUser())){
						 	 toaFinanceBill.setSale(newCustomer.getSale());
					     }else{
					    	 toaFinanceBill.setSale(newCustomer.getTradeUser());
					     }
					 }else{
						 continue;
//						 toaFinanceBill.setCustmersTaxid(null);
//						 toaFinanceBill.setAddress(null);
//						 toaFinanceBill.setBankName(null);
//						 toaFinanceBill.setBankNo(null);
//						 toaFinanceBill.setTicket(null);
//						 toaFinanceBill.setStartIntel(null);
//						 toaFinanceBill.setSale(null);
//						 toaFinanceBill.setPhone(null);
					 }
					 //账单表
					toaFinanceBill.setBillDate(cal.getTime());
					//月收入id整合到账单表
					ToaFinanceInvoices toaInvoicesBill=new ToaFinanceInvoices();
					toaInvoicesBill.setCompanyName(toaInvoices.getCompanyName());
					toaInvoicesBill.setInvoicesMonth(format.format(cal.getTime()));
					toaInvoicesBill.setOtherDeductions(0);
					toaInvoicesBill.setDeleteFlag(0);
					List<ToaFinanceInvoices> toaInvoicesList=toaFinanceInvoicesService.queryInvoicesName(toaInvoicesBill);
					String ids="";
					if(toaInvoicesList.size()>0){
						for(int j=0;j<toaInvoicesList.size();j++){
							Long id=toaInvoicesList.get(j).getId();
							ids+=id+",";
						}
					}
					toaFinanceBill.setRemarkId(ids);
					toaFinanceBill.setCreateUser((long)1);
					toaFinanceBill.setCreateUserDept((long)1003);
					toaFinanceBill.setModifyUser((long)1);
					//状态
					toaFinanceBill.setState(0);
					toaFinanceBill.setObankname("招商银行北京分行海淀黄庄支行");
					toaFinanceBill.setObankno("11 091 395 201 0306");
					toaFinanceBill.setOcompany("北京森华易腾通信技术有限公司");
					//保存
					propertyService.fillProperties(toaFinanceBill,false);
					toaFinanceBillDao.save(toaFinanceBill);
					//修改月收入的状态
					ToaFinanceInvoices toaFinanceInvoicesNew=new ToaFinanceInvoices();
					String[] primaryKey=ids.split(",");
					toaFinanceInvoicesNew.setPrimaryKeys(primaryKey);
					propertyService.fillProperties(toaFinanceInvoicesNew, true);
					result=toaFinanceInvoicesDao.billNewState(toaFinanceInvoicesNew);
				}
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			 ce.setBean(null);
			 throw ce;
		}
		return result;
	}

	@Override
	public  List<ToaFinanceBill> queryByMonth(ToaFinanceBill toaFinanceBill)
			throws CustomException {
		
		return toaFinanceBillDao.queryByMonth(toaFinanceBill);
	}
	/*
	 * 取一年金额
	 * */
	@Override
	public List<ToaFinanceBill> queryByYear(ToaFinanceBill toaFinanceBill) throws CustomException {
		// TODO Auto-generated method stub
		
		return toaFinanceBillDao.queryByYear(toaFinanceBill);
	}

}
