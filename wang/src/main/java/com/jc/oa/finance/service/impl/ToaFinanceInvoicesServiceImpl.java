package com.jc.oa.finance.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.finance.domain.ToaFinanceBill;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
import com.jc.oa.finance.domain.ToaFinanceMain;
import com.jc.oa.finance.domain.ToaFinancePercentage;
import com.jc.oa.finance.dao.IToaFinanceBillDao;
import com.jc.oa.finance.dao.IToaFinanceInvoicesDao;
import com.jc.oa.finance.dao.IToaFinanceMainDao;
import com.jc.oa.finance.dao.IToaFinancePercentageDao;
import com.jc.oa.finance.service.IToaFinanceInvoicesService;
import com.jc.oa.project.domain.Monitors;
import com.jc.oa.shyt.dao.ICustomerDao;
import com.jc.oa.shyt.domain.Customer;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
/**
 * @author mrb
 * @version 月收入
*/
@Service
public class  ToaFinanceInvoicesServiceImpl  extends BaseServiceImpl<ToaFinanceInvoices> implements IToaFinanceInvoicesService {

	public ToaFinanceInvoicesServiceImpl(){}	

    private IToaFinanceInvoicesDao toaFinanceInvoicesDao;
    
    @Autowired
    private IToaFinanceBillDao toaFinanceBillDao;
    
    @Autowired
    private IToaFinanceMainDao toaFinanceMainDao;
    
    @Autowired
    private ICustomerDao customerDao;
    
    @Autowired
    private IToaFinancePercentageDao toaFinancePercentageDao;

	@Autowired
	public ToaFinanceInvoicesServiceImpl(IToaFinanceInvoicesDao toaFinanceInvoicesDao){
		super(toaFinanceInvoicesDao);
		this.toaFinanceInvoicesDao = toaFinanceInvoicesDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaFinanceInvoices toaFinanceInvoices) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaFinanceInvoices,true);
			result = toaFinanceInvoicesDao.delete(toaFinanceInvoices);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinanceInvoices);
			throw ce;
		}
		return result;
	}

	/**
	* @description 出账单方法账单
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer createBill(ToaFinanceInvoices toaFinanceInvoices,String ids)
			throws CustomException {
		ToaFinanceBill bill = new ToaFinanceBill();
		try{
			String[] invoiceIds=ids.split(",");
			propertyService.fillProperties(toaFinanceInvoices,true);
			toaFinanceInvoicesDao.billState(toaFinanceInvoices);
			for(int i=0;i<invoiceIds.length;i++){//通过arr.length获取字符串数组长度
				    toaFinanceInvoices.setId(Long.valueOf(invoiceIds[i]));
				    ToaFinanceInvoices toaFinanceInvoice=toaFinanceInvoicesDao.get(toaFinanceInvoices);
				    Customer customer = new Customer();
				    customer.setId(Long.valueOf(toaFinanceInvoice.getCompanyId()));
				    customer.setDeleteFlag(0);
				    Customer customers = customerDao.get(customer);
				    bill.setCompanyName(customers.getCompanyName());
				    bill.setCompanyId(String.valueOf(toaFinanceInvoice.getCompanyId()));
				    bill.setBankName(customers.getBankName());
				    bill.setAddress(customers.getAddress());
				    bill.setBankNo(customers.getBankNo());
				    bill.setCustmersTaxid(customers.getTaxid());
				    if(StringUtil.isEmpty(customers.getTradeUser())){
				    	bill.setSale(customers.getSale());
				    }else{
				    	bill.setSale(customers.getTradeUser());
				    }
				    bill.setTicket(customers.getTicketFlag());
				    bill.setStartIntel(customers.getStartIntel());
				    bill.setBillDate(DateUtils.getSysDate());
				    bill.setState(0);
				    bill.setServiceType(toaFinanceInvoice.getCompanyType());
				    break;
				}
			bill.setObankname("招商银行北京分行海淀黄庄支行");
			bill.setOcompany("北京森华易腾通信技术有限公司");
			bill.setObankno("11 091 395 201 0306");
			bill.setRemarkId(ids);
			propertyService.fillProperties(bill,false);
			toaFinanceBillDao.save(bill);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(bill);
			throw ce;
		}
		 return 1;
	}
	/*
	 * update 方法
	 * **/
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer update(ToaFinanceInvoices toaFinanceInvoices)throws CustomException {
			try {
				//保存通用字段
				propertyService.fillProperties(toaFinanceInvoices,true);
				//修改月收入
				toaFinanceInvoicesDao.update(toaFinanceInvoices);
				if(toaFinanceInvoices.getJixiaoticheng().equals("是")){
					ToaFinanceMain finance = new ToaFinanceMain();
					finance.setId((long)toaFinanceInvoices.getMainId());
					finance=toaFinanceMainDao.get(finance);
					ToaFinancePercentage percentage = new ToaFinancePercentage();
					percentage.setBillDate(toaFinanceInvoices.getReceivedDate());//回款日期
					
					//编号
					INumber number = new Number();
					String applyNum = number.getNumber("toaFinancePercentage", 1,2, null);
					applyNum = applyNum.substring(1, applyNum.length());
					
					percentage.setPerNumber(applyNum);//生成编号
					percentage.setCompanyId(finance.getCompanyId());//客户id
					percentage.setCompanyName(finance.getCompanyName());//客户名称
					percentage.setSale(finance.getSale());//销售经理
					percentage.setDepartment(finance.getDepartment());//部门
					//获取年份月份
					Calendar a=Calendar.getInstance();
					percentage.setPerYear(String.valueOf(a.get(Calendar.YEAR)));//年份
					percentage.setPerMonth(String.valueOf(a.get(Calendar.MONTH)+1));//月份
					percentage.setTradeDepartment(finance.getOldDepartment());//客户关系维护组
					if(finance.getDepartment().equals("客户维护组")){
						percentage.setPerType("存量");//类型（新增或存量）
						long str1 = toaFinanceInvoices.getReceivedDate().getTime();
						long str2 = finance.getStartIntel().getTime();
						if((str1-str2)/(24*60*60*1000)>365){
							percentage.setPerYers("1年以上");//年限
							percentage.setKeweiRatio(BigDecimal.valueOf(0.01));//客维计提比例
							percentage.setKeweiMoney(BigDecimal.valueOf(0.01).multiply(toaFinanceInvoices.getAllmoney()));//客维金额
							percentage.setTuozhanRatio(BigDecimal.valueOf(0.02));//   拓展组计提比例
							percentage.setTuozhanMoney(BigDecimal.valueOf(0.02).multiply(toaFinanceInvoices.getAllmoney()));//拓展组计提金额
						}else{
							percentage.setPerYers("1年以内");//年限
							percentage.setKeweiRatio(BigDecimal.valueOf(0.01));//客维计提比例
							percentage.setKeweiMoney(BigDecimal.valueOf(0.01).multiply(toaFinanceInvoices.getAllmoney()));//客维金额
							percentage.setTuozhanRatio(BigDecimal.valueOf(0.07));//   拓展组计提比例
							percentage.setTuozhanMoney(BigDecimal.valueOf(0.07).multiply(toaFinanceInvoices.getAllmoney()));//拓展组计提金额
						}
					}else{
						percentage.setPerType("新增");//类型（新增或存量）
						percentage.setPerYers("1年以内");//年限
						percentage.setKeweiRatio(BigDecimal.valueOf(0.00));//客维计提比例
						percentage.setKeweiMoney(BigDecimal.valueOf(0.00));//客维金额
						percentage.setTuozhanRatio(BigDecimal.valueOf(0.07));//   拓展组计提比例
						percentage.setTuozhanMoney(BigDecimal.valueOf(0.07).multiply(toaFinanceInvoices.getAllmoney()));//拓展组计提金额
					}
					percentage.setPerStart(finance.getStartIntel());//首次入网时间
					percentage.setPerPureAccount(toaFinanceInvoices.getAllmoney());//净到账收入
					percentage.setPerEnSale(finance.getMaintenanSale());//维护经理
					percentage.setPerAccount(toaFinanceInvoices.getAllmoney());//   到账总金额
					//percentage.setPerAgentAccount();//代理费
					percentage.setAddBill(toaFinanceInvoices.getAddmoney());//   新增到账收入
					percentage.setKuorong(toaFinanceInvoices.getScaleMoney());//   扩容到账收入
					percentage.setBillAccount(toaFinanceInvoices.getStackMoney());//   存量到账收入
					percentage.setBillNumber(toaFinanceInvoices.getInvoicesNo());//   发票号
					propertyService.fillProperties(percentage,false);
					toaFinancePercentageDao.save(percentage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return 1;
	}

	@Override
	public PageManager mainInvoices(ToaFinanceInvoices toaFinanceInvoices,PageManager page) throws CustomException {
		return toaFinanceInvoicesDao.mainInvoices(toaFinanceInvoices, page);
	}

	@Override
	public List<ToaFinanceInvoices> getMain(ToaFinanceInvoices toaFinanceInvoices) throws CustomException {
		// TODO Auto-generated method stub
		return toaFinanceInvoicesDao.getMain(toaFinanceInvoices);
	}

    //应收月份
	@Override
	public List<ToaFinanceInvoices> queryInvoices(ToaFinanceInvoices toaFinanceInvoices) throws CustomException {
		// TODO Auto-generated method stub
		return toaFinanceInvoicesDao.queryInvoices(toaFinanceInvoices);
	}
	//客户名称与应收月份
	@Override
	public List<ToaFinanceInvoices> queryInvoicesName(ToaFinanceInvoices toaFinanceInvoices) throws CustomException {
		// TODO Auto-generated method stub
		return toaFinanceInvoicesDao.queryInvoicesName(toaFinanceInvoices);
	}
}
