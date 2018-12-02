package com.jc.oa.finance.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.contract.domain.ToaContractIncontract;
import com.jc.oa.contract.service.IToaContractIncontractService;
import com.jc.oa.finance.domain.ToaFinanceIncome;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
import com.jc.oa.finance.domain.ToaFinanceMain;
import com.jc.oa.finance.dao.IToaFinanceIncomeDao;
import com.jc.oa.finance.service.IToaFinanceIncomeService;
import com.jc.oa.finance.service.IToaFinanceInvoicesService;
import com.jc.oa.finance.service.IToaFinanceMainService;
import com.jc.oa.shyt.domain.Customer;
import com.jc.oa.shyt.service.ICustomerService;
import com.jc.system.CustomException;

/**
 * @author mrb
 * @version 收入底表
 */
@Service
public class ToaFinanceIncomeServiceImpl extends
		BaseServiceImpl<ToaFinanceIncome> implements IToaFinanceIncomeService {

	public ToaFinanceIncomeServiceImpl() {
	}

	private IToaFinanceIncomeDao toaFinanceIncomeDao;

	// 收入主表
	@Autowired
	private IToaFinanceMainService toaFinanceMainService;
	// 客户信息
	@Autowired
	private ICustomerService customerService;
	// 合同信息
	@Autowired
	private IToaContractIncontractService toaContractIncontractService;
	// 月收入
	@Autowired
	private IToaFinanceInvoicesService toaFinanceInvoicesService;

	@Autowired
	public ToaFinanceIncomeServiceImpl(IToaFinanceIncomeDao toaFinanceIncomeDao) {
		super(toaFinanceIncomeDao);
		this.toaFinanceIncomeDao = toaFinanceIncomeDao;
	}

	/**
	 * @description 根据主键删除多条记录方法
	 * @param EatOutreg
	 *            eatOutreg 实体类
	 * @return Integer 处理结果
	 * @author
	 * @version 2015-03-31
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer deleteByIds(ToaFinanceIncome toaFinanceIncome)
			throws CustomException {
		Integer result = -1;
		try {
			propertyService.fillProperties(toaFinanceIncome, true);
			result = toaFinanceIncomeDao.delete(toaFinanceIncome);
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinanceIncome);
			throw ce;
		}
		return result;
	}

	/**
	 * @description 收入底表定时任务方法
	 * @param EatOutreg
	 *            eatOutreg 实体类
	 * @return Integer 处理结果
	 * @author
	 * @version 2015-03-31
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void updateFinanceIncome() throws CustomException {
		// TODO Auto-generated method stub
		try {
			ToaFinanceIncome toaFinanceIncome = new ToaFinanceIncome();
			toaFinanceIncome.setTimingStatus("0");
			List<ToaFinanceIncome> toaFinance = toaFinanceIncomeDao.queryAll(toaFinanceIncome);
			int toaFinanceCount = toaFinance.size();
			if (toaFinanceCount > 0) {
				// for
				for (int i = 0; i < toaFinanceCount; i++) {
					// 单条收入底表
					ToaFinanceIncome toaIncome = toaFinance.get(i);
					// 收入主表
					ToaFinanceMain toaFinanceMain = new ToaFinanceMain();
					// 编码
					toaFinanceMain.setOrderNo(toaIncome.getOrderNo());
					// 客户
					toaFinanceMain.setCompanyName(toaIncome.getCompanyName());
					// 通过客户名称查询客户信息
					Customer customer = new Customer();
					customer.setClientName(toaIncome.getCompanyName());
					Customer customerNew = null;
					try {
						customerNew = customerService.get(customer);
					} catch (Exception e) {
						// TODO: handle exception
					}
					if(customerNew==null){
						continue;
					}
					// 业务类型
					toaFinanceMain.setCompanyType(customerNew.getServiceType());
					// 公司id，拓展组,拓展专员，维护专员
					if (customerNew.getId() != null) {
						toaFinanceMain.setCompanyId(customerNew.getId().toString());
					}
					toaFinanceMain.setDepartment(customerNew.getDepartment());
					toaFinanceMain.setSale(customerNew.getSale());
					toaFinanceMain.setMaintenanSale(customerNew.getTradeUser());
					// 复制表那数据(表数据,目标表)例（收入底表，收入主体）
					// BeanUtils.copyProperties(dest, orig);
					// 合同金额
					toaFinanceMain.setCardAmount(toaIncome.getCardAmount());
					// 合同编码
					toaFinanceMain.setCardNo(toaIncome.getCardNo());
					// 通过合同编码查询合同信息
//					ToaContractIncontract toaContractIncontract = new ToaContractIncontract();
//					toaContractIncontract.setContractNumber(toaIncome.getCardNo());
//					ToaContractIncontract toaIncontract = toaContractIncontractService.get(toaContractIncontract);
//					if(toaIncontract==null){
//						continue;
//					}
//					// 合同类别，衍生合同编码，合同起始时间，合同终止时间
//					toaFinanceMain.setContractStatus(toaIncontract.getContractStatus());
//					toaFinanceMain.setDeriveNo(toaIncontract.getDeriveNo());
//					toaFinanceMain.setStartDate(toaIncontract.getStartDate());
//					toaFinanceMain.setEndDate(toaIncontract.getEndDate());
					// 机房
					toaFinanceMain.setRoomName(toaIncome.getRoomName());
					// 超流量取值方式
					toaFinanceMain.setOverflowCategory(toaIncome.getOverflowCategory());
					// 带宽类型
					toaFinanceMain.setPerformanceAmount(toaIncome.getPerformanceAmount());
					// 首次入网时间
					toaFinanceMain.setStartIntel(toaIncome.getStartIntel());
					// 变动日期
					toaFinanceMain.setOrderDate(toaIncome.getOrderDate());
					// 客户关系维护组
					toaFinanceMain.setOldDepartment(toaIncome.getOldDepartment());
					// 登记月份
					toaFinanceMain.setMonths(toaIncome.getMonths());
					// 资源变动类型
					toaFinanceMain.setResourceType(toaIncome.getResourceType());
					// 专线类型
					toaFinanceMain.setLineCategory(toaIncome.getLineCategory());
					// 折扣
					toaFinanceMain.setDiscount(toaIncome.getDiscount());
					// 存量合同额
					toaFinanceMain.setCardStockAmount(toaIncome.getCardStockAmount());
					// 预存金额
					toaFinanceMain.setPrestoreAmount(toaIncome.getPrestoreAmount());
					// 备注
					toaFinanceMain.setOrderRemark(toaIncome.getOrderRemark());
					// 保底带宽
					toaFinanceMain.setMinBandwidth(toaIncome.getMinBandwidth());
					toaFinanceMain.setMinBandwidthPrice(toaIncome.getMinBandwidthPrice());
					// 端口带宽
					toaFinanceMain.setPortBandwidth(toaIncome.getPortBandwidth());
					toaFinanceMain.setPortBandwidthPrice(toaIncome.getPortBandwidthPrice());
					// 超流量带宽
					toaFinanceMain.setOverflowBandwidth(toaIncome.getOverflowBandwidth());
					toaFinanceMain.setOverflowBandwidthPrice(toaIncome.getOverflowBandwidthPrice());
					// 机柜
					toaFinanceMain.setCabinetNum(toaIncome.getCabinetNum());
					toaFinanceMain.setCabinetPrice(toaIncome.getCabinetPrice());
					// 服务器
					toaFinanceMain.setServiceNum(toaIncome.getServiceNum());
					toaFinanceMain.setServicePrice(toaIncome.getServicePrice());
					// IP
					toaFinanceMain.setIpNum(toaIncome.getIpNum());
					toaFinanceMain.setIpPrice(toaIncome.getIpPrice());
					// 交换机
					toaFinanceMain.setSwitchNum(toaIncome.getSwitchNum());
					toaFinanceMain.setSwitchPrice(toaIncome.getSwitchPrice());
					// 链路
					toaFinanceMain.setOdfNum(toaIncome.getOdfNum());
					toaFinanceMain.setOdfPrice(toaIncome.getOdfPrice());
					// 端口
					toaFinanceMain.setPortNum(toaIncome.getPortNum());
					toaFinanceMain.setPortPrice(toaIncome.getPortPrice());
					// 内存
					toaFinanceMain.setMemoryNum(toaIncome.getMemoryNum());
					toaFinanceMain.setMemoryPrice(toaIncome.getMemoryPrice());
					// CPU
					toaFinanceMain.setCpuNum(toaIncome.getCpuNum());
					toaFinanceMain.setCpuPrice(toaIncome.getCpuPrice());
					// 硬盘
					toaFinanceMain.setDiskNum(toaIncome.getDiskNum());
					toaFinanceMain.setDiskPrice(toaIncome.getDiskPrice());

					// 计费开始时间
					Date startDate = toaIncome.getCycleStart();
					// 计费终止时间
					Date endDate = toaIncome.getCycleEnd();
					// 付费方式
					String payType = toaIncome.getPayType();
					// 计费起始日
					toaFinanceMain.setCycleStart(startDate);
					// 计费截止日
					toaFinanceMain.setCycleEnd(endDate);
					// 单天计算方式
					toaFinanceMain.setSingleCharg(toaIncome.getSingleCharg());
					// 付费方式
					toaFinanceMain.setPayType(payType);
					toaFinanceMain.setCreateUser((long) 1);
					toaFinanceMain.setModifyUser((long) 1);
					toaFinanceMain.setCreateUserDept((long) 1003);
					toaFinanceMain.setCreateUserOrg((long) 1);
					int flag = toaFinanceMainService.save(toaFinanceMain);
					//int id = toaFinanceMain.getId().intValue();
					// 月收入总和
					String cardMoney = toaIncome.getExtStr1();
					/******* 保存收入主体结束 ********/
					// 计算情况(周期或补齐自然月)
					String state = toaIncome.getCycle();
					if (payType.equals("当月付")) {
						monthPay(startDate, endDate, state, toaFinanceMain, cardMoney);
					} else if (payType.equals("后月付")) {
						afterMonthPay(startDate, endDate, state, toaFinanceMain, cardMoney);
					} else if (payType.equals("季度付")) {
						quarterPay(startDate, endDate, state, toaFinanceMain, cardMoney);
					} else if (payType.equals("后季度付")) {
						afterQuarterPay(startDate, endDate, state, toaFinanceMain,cardMoney);
					} else if (payType.equals("半年付")) {
						halfYearPay(startDate, endDate, toaFinanceMain, cardMoney);
					} else if (payType.equals("年付")) {
						yearPay(startDate, endDate, toaFinanceMain, cardMoney);
					}else{
						otherPay(startDate,endDate,toaFinanceMain,cardMoney);
					}
					if (flag == 1) {
						propertyService.fillProperties(toaIncome, true);
						// toaIncome.setTimingStatus("1");
						toaIncome.setPrimaryKeys(toaIncome.getId().toString().split(","));
						toaFinanceIncomeDao.delete(toaIncome);
					}
				}
			}
		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setBean(null);
			throw ce;
		}
	}

	/**
	 * 当月付
	 * 
	 * @author mrb
	 * @param
	 */
	private void monthPay(Date startDate, Date endDate, String state, ToaFinanceMain toaFinanceMain,String CardAmount) {
		// 判断是否周期，0为周期
		if (state.equals("0")) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			// 取得时间
			Calendar cal = Calendar.getInstance();// 下面的就是把当前日期加一个月
			cal.setTime(startDate);
			for (int i = 1; i < 13; i++) {
				// 月收入
				ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
				int id=toaFinanceMain.getId().intValue();
				toaFinanceInvoices.setMainId(id);
				toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
				toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
				//机房
				toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
				// 保底带宽
				toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
				toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
				// 端口带宽
				toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
				toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
				// 超流量带宽
				toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
				toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
				// 机柜
				toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
				toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
				// 服务器
				toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
				toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
				// IP
				toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
				toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
				// 交换机
				toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
				toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
				// 链路
				toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
				toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
				// 端口
				toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
				toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
				// 内存
				toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
				toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
				// CPU
				toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
				toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
				// 硬盘
				toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
				toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
				toaFinanceInvoices.setOtherDeductions(0);
				toaFinanceInvoices.setCreateUser((long) 1);
				toaFinanceInvoices.setModifyUser((long) 1);
				toaFinanceInvoices.setCreateUserDept((long) 1003);
				toaFinanceInvoices.setCreateUserOrg((long) 1);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				BigDecimal cardAmount = new BigDecimal(CardAmount);
				// 保存
				try{
					String startFirst=sdf.format(startDate);
					String[] startTwoTime = startFirst.split("-");
					// 天数
					String dayFirstTime = startTwoTime[2];
					int dayFirstInt = Integer.valueOf(dayFirstTime);
					//判断天数是否大于28
					if(dayFirstInt>28){
						// 月数
						String monthFirstTime = startTwoTime[1];
						int monthFirstInt = Integer.valueOf(monthFirstTime);
						// 年数
						String yearFirstTime = startTwoTime[0];
						int yearFirstList = Integer.valueOf(yearFirstTime);
						//当月天数
						int monthInt=2;
						int dayLast = 0;
						switch (monthInt) {
						case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12:  dayLast = 31; break;
						case 4:;case 6:;case 9:;case 11:  dayLast = 30; break;
						case 2: dayLast = yearFirstList % 4 == 0 ? 29 : 28;
						default: break;
						}
						if (i == 1) {
							cal.add(Calendar.MONTH, 1);
							if(monthFirstInt==1){
								cal.set(Calendar.DATE, dayLast);
							}else{
								cal.set(Calendar.DATE, dayFirstInt-1);
							}
							//开始时间
							toaFinanceInvoices.setInvoicesStartdate(startDate);
							//结束时间
							toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
							//应收月份
							toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						} else {
							cal.add(Calendar.DATE, 1);
							//开始时间
							toaFinanceInvoices.setInvoicesStartdate(cal.getTime());
							int monthNew = cal.get(Calendar.MONTH) + 1;
							int dayNew = cal.get(Calendar.DATE);
							if(dayNew==dayFirstInt){
								cal.add(Calendar.MONTH, 1);
								if(monthNew==1){
									cal.set(Calendar.DATE, dayLast);
								}else{
									cal.set(Calendar.DATE, dayFirstInt-1);
								}
								//结束时间
								toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
								//应收月份
								toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
								//四舍五入
								toaFinanceInvoices.setMonthAmount(round(cardAmount));
							}else{
								if(monthNew==1||monthNew==3||monthNew==5||monthNew==7||monthNew==8||monthNew==10||monthNew==12){
									cal.set(Calendar.DATE, dayFirstInt-1);
									//结束时间
									toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
									//应收月份
									toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
									//四舍五入
									toaFinanceInvoices.setMonthAmount(round(cardAmount));
								}
							}
						}
					}else{
						if (i == 1) {
							toaFinanceInvoices.setInvoicesStartdate(startDate);
							cal.add(Calendar.MONTH, 1);
							cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
							toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
							toaFinanceInvoices.setInvoicesMonth(format.format(startDate));
							//toaFinanceInvoices.setMonthAmount(cardAmount);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						} else {
							cal.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
							toaFinanceInvoices.setInvoicesStartdate(cal.getTime());
							cal.add(Calendar.MONTH, 1);
							cal.add(Calendar.DATE, -1);
							toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
							//toaFinanceInvoices.setMonthAmount(cardAmount);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						}
					}
					propertyService.fillProperties(toaFinanceInvoices, false);
					toaFinanceInvoicesService.save(toaFinanceInvoices);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			// 补齐自然日
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			String startFirst = sdf.format(startDate);
			String[] startTwoTime = startFirst.split("-");
			// 天数
			String dayFirstTime = startTwoTime[2];
			int dayFirstInt = Integer.valueOf(dayFirstTime);
			// 月数
			String monthFirstTime = startTwoTime[1];
			int monthFirstInt = Integer.valueOf(monthFirstTime);
			// 年数
			String yearFirstTime = startTwoTime[0];
			int yearFirstList = Integer.valueOf(yearFirstTime);
			//当月天数
			int dayLast = 0;
			switch (monthFirstInt) {
				case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12:  dayLast = 31; break;
				case 4:;case 6:;case 9:;case 11:  dayLast = 30; break;
				case 2: dayLast = yearFirstList % 4 == 0 ? 29 : 28;
				default: break;
			}
			// 判断天数是否小于25
			if (dayFirstInt < 25) {
				try {
					//结束时间
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.set(Calendar.DATE, dayLast);
					Date lastEndDate=calendar.getTime();
					// 金额
					BigDecimal cardAmount = new BigDecimal(CardAmount);
					//int cardmoney = Integer.valueOf(CardAmount);
					// 一天的金额
					//int oneDayMoney = (cardmoney * 12) / 365;
					//月
					BigDecimal month=new BigDecimal(12);
					//天
					BigDecimal day=new BigDecimal(365);
					BigDecimal monthmount=mul(cardAmount,month);
					//一天的金额
					BigDecimal oneDayMoney=div(monthmount,day,8);
					int iList;
					if(dayFirstInt==1){
						iList=12;
					}else{
						iList=13;
					}
					// 时间
					for (int i = 0; i < iList; i++) {
						// 月收入
						ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
						int id=toaFinanceMain.getId().intValue();
						toaFinanceInvoices.setMainId(id);
						toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
						toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
						toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
						//机房
						toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
						// 保底带宽
						toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
						toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
						// 端口带宽
						toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
						toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
						// 超流量带宽
						toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
						toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
						// 机柜
						toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
						toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
						// 服务器
						toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
						toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
						// IP
						toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
						toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
						// 交换机
						toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
						toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
						// 链路
						toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
						toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
						// 端口
						toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
						toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
						// 内存
						toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
						toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
						// CPU
						toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
						toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
						// 硬盘
						toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
						toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
						toaFinanceInvoices.setOtherDeductions(0);
						toaFinanceInvoices.setCreateUser((long) 1);
						toaFinanceInvoices.setModifyUser((long) 1);
						toaFinanceInvoices.setCreateUserDept((long) 1003);
						toaFinanceInvoices.setCreateUserOrg((long) 1);
						BigDecimal oneMonthMoney=cardAmount;
						BigDecimal lastMonthMoney=new BigDecimal(0);
						//天数
						BigDecimal dayMonth=new BigDecimal(dayLast-dayFirstInt+1);
						//int oneMonthMoney=cardmoney;
						//int lastMonthMoney=0;
						// 第一个月的金额
//						if(dayFirstInt!=1){
//							oneMonthMoney= oneDayMoney * (dayLast-dayFirstInt+1);
//							// 最后一个月金额
//							lastMonthMoney=cardmoney-oneMonthMoney;
//						}
						//第一个月金额
						if(dayFirstInt!=1){
							oneMonthMoney=mul(oneDayMoney,dayMonth);
							// 最后一个月金额
							lastMonthMoney=sub(cardAmount, oneMonthMoney);
						}
//						BigDecimal oneNewMoney = new BigDecimal(oneMonthMoney);
//						BigDecimal lastNewMoney = new BigDecimal(lastMonthMoney);
						if (i == 0) {
							toaFinanceInvoices.setInvoicesStartdate(startDate);
							toaFinanceInvoices.setInvoicesEnddate(lastEndDate);
							toaFinanceInvoices.setInvoicesMonth(format.format(startDate));
							if(iList==13){
								//toaFinanceInvoices.setMonthAmount(oneMonthMoney);
								//四舍五入
								toaFinanceInvoices.setMonthAmount(round(oneMonthMoney));
							}else{
								//toaFinanceInvoices.setMonthAmount(cardAmount);
								//四舍五入
								toaFinanceInvoices.setMonthAmount(round(cardAmount));
							}
						}else if(i==12){
							calendar.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(calendar.getTime());
							toaFinanceInvoices.setInvoicesMonth(format.format(calendar.getTime()));
							calendar.set(Calendar.DATE, dayFirstInt-1);
							toaFinanceInvoices.setInvoicesEnddate(calendar.getTime());
							//toaFinanceInvoices.setMonthAmount(lastMonthMoney);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(lastMonthMoney));
						}else{
							calendar.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(calendar.getTime());
							toaFinanceInvoices.setInvoicesMonth(format.format(calendar.getTime()));
							calendar.add(Calendar.MONTH, 1);
							calendar.add(Calendar.DATE, -1);
							toaFinanceInvoices.setInvoicesEnddate(calendar.getTime());
							//toaFinanceInvoices.setMonthAmount(cardAmount);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						}
						propertyService.fillProperties(toaFinanceInvoices,false);
						toaFinanceInvoicesService.save(toaFinanceInvoices);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// 天数大于25
				try {
					//结束时间
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.set(Calendar.DATE, 1);
					calendar.add(Calendar.MONTH, 2);
					calendar.add(Calendar.DATE, -1);
					Date lastEndDate=calendar.getTime();
					// 金额
					BigDecimal cardAmount = new BigDecimal(CardAmount);
//					int cardmoney = Integer.valueOf(CardAmount);
					// 一天的金额
//					int oneDayMoney = (cardmoney * 12) / 365;
					//月
					BigDecimal month=new BigDecimal(12);
					//天
					BigDecimal day=new BigDecimal(365);
					BigDecimal monthmount=mul(cardAmount,month);
					//一天的金额
					BigDecimal oneDayMoney=div(monthmount,day,8);
					// 时间
					for (int i = 0; i < 12; i++) {
						// 月收入
						ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
						int id=toaFinanceMain.getId().intValue();
						toaFinanceInvoices.setMainId(id);
						toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
						toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
						toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
						//机房
						toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
						// 保底带宽
						toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
						toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
						// 端口带宽
						toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
						toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
						// 超流量带宽
						toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
						toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
						// 机柜
						toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
						toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
						// 服务器
						toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
						toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
						// IP
						toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
						toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
						// 交换机
						toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
						toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
						// 链路
						toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
						toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
						// 端口
						toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
						toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
						// 内存
						toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
						toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
						// CPU
						toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
						toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
						// 硬盘
						toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
						toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
						toaFinanceInvoices.setOtherDeductions(0);
						toaFinanceInvoices.setCreateUser((long) 1);
						toaFinanceInvoices.setModifyUser((long) 1);
						toaFinanceInvoices.setCreateUserDept((long) 1003);
						toaFinanceInvoices.setCreateUserOrg((long) 1);
						BigDecimal oneMonthMoney=cardAmount;
						BigDecimal lastMonthMoney=cardAmount;
						//天数
						BigDecimal dayMonth=new BigDecimal(dayLast-dayFirstInt+1); 
//						int oneMonthMoney=cardmoney;
//						int lastMonthMoney=cardmoney;
						// 第一个月的金额
//						if(dayFirstInt!=1){
//							oneMonthMoney=  oneDayMoney *(dayLast-dayFirstInt+1)+ cardmoney;
//							// 最后一个月金额
//							lastMonthMoney=cardmoney- oneDayMoney*(dayLast-dayFirstInt+1);
//						}
						// 第一个月的金额
						if(dayFirstInt!=1){
							BigDecimal oneMoney=mul(oneDayMoney,dayMonth);
							oneMonthMoney=add(oneMoney, cardAmount);
							// 最后一个月金额
							lastMonthMoney=sub(cardAmount,oneMoney);
						}
//						BigDecimal oneNewMoney = new BigDecimal(oneMonthMoney);
//						BigDecimal lastNewMoney = new BigDecimal(lastMonthMoney);
						if (i == 0) {
							toaFinanceInvoices.setInvoicesStartdate(startDate);
							toaFinanceInvoices.setInvoicesEnddate(lastEndDate);
							toaFinanceInvoices.setInvoicesMonth(format.format(lastEndDate));
							//toaFinanceInvoices.setMonthAmount(oneMonthMoney);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(oneMonthMoney));
						}else if(i==11){
							calendar.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(calendar.getTime());
							calendar.set(Calendar.DATE, dayFirstInt-1);
							toaFinanceInvoices.setInvoicesEnddate(calendar.getTime());
							toaFinanceInvoices.setInvoicesMonth(format.format(calendar.getTime()));
							//toaFinanceInvoices.setMonthAmount(lastMonthMoney);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(lastMonthMoney));
						}else{
							calendar.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(calendar.getTime());
							calendar.add(Calendar.MONTH, 1);
							calendar.add(Calendar.DATE, -1);
							toaFinanceInvoices.setInvoicesEnddate(calendar.getTime());
							toaFinanceInvoices.setInvoicesMonth(format.format(calendar.getTime()));
							//toaFinanceInvoices.setMonthAmount(cardAmount);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						}
						propertyService.fillProperties(toaFinanceInvoices,false);
						toaFinanceInvoicesService.save(toaFinanceInvoices);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 后月付
	 * 
	 * @author mrb
	 * @param
	 */
	public void afterMonthPay(Date startDate, Date endDate, String state,ToaFinanceMain toaFinanceMain, String CardAmount) {
		// 判断是否周期，0为周期
		if (state.equals("0")) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			// 取得时间
			Calendar cal = Calendar.getInstance();// 下面的就是把当前日期加一个月
			cal.setTime(startDate);
			for (int i = 1; i < 13; i++) {
				// 月收入
				ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
				int id=toaFinanceMain.getId().intValue();
				toaFinanceInvoices.setMainId(id);
				toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
				toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
				//机房
				toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
				// 保底带宽
				toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
				toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
				// 端口带宽
				toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
				toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
				// 超流量带宽
				toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
				toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
				// 机柜
				toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
				toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
				// 服务器
				toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
				toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
				// IP
				toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
				toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
				// 交换机
				toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
				toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
				// 链路
				toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
				toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
				// 端口
				toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
				toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
				// 内存
				toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
				toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
				// CPU
				toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
				toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
				// 硬盘
				toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
				toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
				toaFinanceInvoices.setOtherDeductions(0);
				toaFinanceInvoices.setCreateUser((long) 1);
				toaFinanceInvoices.setModifyUser((long) 1);
				toaFinanceInvoices.setCreateUserDept((long) 1003);
				toaFinanceInvoices.setCreateUserOrg((long) 1);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				BigDecimal cardAmount = new BigDecimal(CardAmount);
				// 保存
				try {
					String startFirst=sdf.format(startDate);
					String[] startTwoTime = startFirst.split("-");
					// 天数
					String dayFirstTime = startTwoTime[2];
					int dayFirstInt = Integer.valueOf(dayFirstTime);
					//判断天数是否大于28
					if(dayFirstInt>28){
						// 月数
						String monthFirstTime = startTwoTime[1];
						int monthFirstInt = Integer.valueOf(monthFirstTime);
						// 年数
						String yearFirstTime = startTwoTime[0];
						int yearFirstList = Integer.valueOf(yearFirstTime);
						//当月天数
						int monthInt=2;
						int dayLast = 0;
						switch (monthInt) {
						case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12:  dayLast = 31; break;
						case 4:;case 6:;case 9:;case 11:  dayLast = 30; break;
						case 2: dayLast = yearFirstList % 4 == 0 ? 29 : 28;
						default: break;
						}
						if (i == 1) {
							cal.add(Calendar.MONTH, 1);
							if(monthFirstInt==1){
								cal.set(Calendar.DATE, dayLast);
							}else{
								cal.set(Calendar.DATE, dayFirstInt-1);
							}
							//开始时间
							toaFinanceInvoices.setInvoicesStartdate(startDate);
							//结束时间
							toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
							//应收月份
							cal.add(Calendar.MONTH, 1);
							toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
							cal.add(Calendar.MONTH, -1);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						} else {
							cal.add(Calendar.DATE, 1);
							//开始时间
							toaFinanceInvoices.setInvoicesStartdate(cal.getTime());
							int monthNew = cal.get(Calendar.MONTH) + 1;
							int dayNew = cal.get(Calendar.DATE);
							if(dayNew==dayFirstInt){
								cal.add(Calendar.MONTH, 1);
								if(monthNew==1){
									cal.set(Calendar.DATE, dayLast);
								}else{
									cal.set(Calendar.DATE, dayFirstInt-1);
								}
								//结束时间
								toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
								//应收月份
								cal.add(Calendar.MONTH, 1);
								toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
								cal.add(Calendar.MONTH, -1);
								//四舍五入
								toaFinanceInvoices.setMonthAmount(round(cardAmount));
							}else{
								if(monthNew==1||monthNew==3||monthNew==5||monthNew==7||monthNew==8||monthNew==10||monthNew==12){
									cal.set(Calendar.DATE, dayFirstInt-1);
									//结束时间
									toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
									//应收月份
									cal.add(Calendar.MONTH, 1);
									toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
									cal.add(Calendar.MONTH, -1);
									//四舍五入
									toaFinanceInvoices.setMonthAmount(round(cardAmount));
								}
							}
						}
					}else{
						if (i == 1) {
							toaFinanceInvoices.setInvoicesStartdate(startDate);
							cal.add(Calendar.MONTH, 1);
							cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
							toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
							//toaFinanceInvoices.setMonthAmount(cardAmount);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						} else {
							cal.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(cal.getTime());
							cal.add(Calendar.MONTH, 1);
							cal.add(Calendar.DATE, -1);
							toaFinanceInvoices.setInvoicesEnddate(cal.getTime());
							//toaFinanceInvoices.setMonthAmount(cardAmount);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						}
						Calendar calendar=Calendar.getInstance();
						calendar.setTime(cal.getTime());
						calendar.add(Calendar.MONTH, 1);
						toaFinanceInvoices.setInvoicesMonth(format.format(calendar.getTime()));
					}
					propertyService.fillProperties(toaFinanceInvoices, false);
					toaFinanceInvoicesService.save(toaFinanceInvoices);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			// 补齐自然日
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			String startFirst = sdf.format(startDate);
			String[] startTwoTime = startFirst.split("-");
			// 天数
			String dayFirstTime = startTwoTime[2];
			int dayFirstInt = Integer.valueOf(dayFirstTime);
			// 月数
			String monthFirstTime = startTwoTime[1];
			int monthFirstInt = Integer.valueOf(monthFirstTime);
			// 年数
			String yearFirstTime = startTwoTime[0];
			int yearFirstList = Integer.valueOf(yearFirstTime);
			//当月天数
			int dayLast = 0;
			switch (monthFirstInt) {
				case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12:  dayLast = 31; break;
				case 4:;case 6:;case 9:;case 11:  dayLast = 30; break;
				case 2: dayLast = yearFirstList % 4 == 0 ? 29 : 28;
				default: break;
			}
			// 判断天数是否小于25
			if (dayFirstInt < 25) {
				try {
					//结束时间
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.set(Calendar.DATE, dayLast);
					Date lastEndDate=calendar.getTime();
					// 金额
					BigDecimal cardAmount = new BigDecimal(CardAmount);
//					int cardmoney = Integer.valueOf(CardAmount);
//					// 一天的金额
//					int oneDayMoney = (cardmoney * 12) / 365;
					//月
					BigDecimal month=new BigDecimal(12);
					//天
					BigDecimal day=new BigDecimal(365);
					BigDecimal monthmount=mul(cardAmount,month);
					//一天的金额
					BigDecimal oneDayMoney=div(monthmount,day,8);
					int iList;
					if(dayFirstInt==1){
						iList=12;
					}else{
						iList=13;
					}
					// 时间
					for (int i = 0; i < iList; i++) {
						// 月收入
						ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
						int id=toaFinanceMain.getId().intValue();
						toaFinanceInvoices.setMainId(id);
						toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
						toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
						toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
						//机房
						toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
						// 保底带宽
						toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
						toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
						// 端口带宽
						toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
						toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
						// 超流量带宽
						toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
						toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
						// 机柜
						toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
						toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
						// 服务器
						toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
						toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
						// IP
						toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
						toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
						// 交换机
						toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
						toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
						// 链路
						toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
						toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
						// 端口
						toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
						toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
						// 内存
						toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
						toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
						// CPU
						toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
						toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
						// 硬盘
						toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
						toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
						toaFinanceInvoices.setOtherDeductions(0);
						toaFinanceInvoices.setCreateUser((long) 1);
						toaFinanceInvoices.setModifyUser((long) 1);
						toaFinanceInvoices.setCreateUserDept((long) 1003);
						toaFinanceInvoices.setCreateUserOrg((long) 1);
						BigDecimal oneMonthMoney=cardAmount;
						BigDecimal lastMonthMoney=new BigDecimal(0);
						//天数
						BigDecimal dayMonth=new BigDecimal(dayLast-dayFirstInt+1); 
//						int oneMonthMoney=cardmoney;
//						int lastMonthMoney=0;
						// 第一个月的金额
//						if(dayFirstInt!=1){
//							oneMonthMoney= oneDayMoney * (dayLast-dayFirstInt+1);
//							// 最后一个月金额
//							lastMonthMoney=cardmoney-oneMonthMoney;
//						}
						// 第一个月的金额
						if(dayFirstInt!=1){
							oneMonthMoney=mul(oneDayMoney,dayMonth);
							// 最后一个月金额
							lastMonthMoney=sub(cardAmount,oneMonthMoney);
						}
//						BigDecimal oneNewMoney = new BigDecimal(oneMonthMoney);
//						BigDecimal lastNewMoney = new BigDecimal(lastMonthMoney);
						Calendar cal=Calendar.getInstance();
						if (i == 0) {
							toaFinanceInvoices.setInvoicesStartdate(startDate);
							toaFinanceInvoices.setInvoicesEnddate(lastEndDate);
							cal.setTime(startDate);
							cal.add(Calendar.MONTH,1);
							toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
							if(iList==13){
								//toaFinanceInvoices.setMonthAmount(oneMonthMoney);
								//四舍五入
								toaFinanceInvoices.setMonthAmount(round(oneMonthMoney));
							}else{
								//toaFinanceInvoices.setMonthAmount(cardAmount);
								//四舍五入
								toaFinanceInvoices.setMonthAmount(round(cardAmount));
							}
						}else if(i==12){
							calendar.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(calendar.getTime());
							calendar.set(Calendar.DATE, dayFirstInt-1);
							toaFinanceInvoices.setInvoicesEnddate(calendar.getTime());
							cal.setTime(calendar.getTime());
							cal.add(Calendar.MONTH, 1);
							toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
							//toaFinanceInvoices.setMonthAmount(lastMonthMoney);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(lastMonthMoney));
						}else{
							calendar.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(calendar.getTime());
							calendar.add(Calendar.MONTH, 1);
							calendar.add(Calendar.DATE, -1);
							toaFinanceInvoices.setInvoicesEnddate(calendar.getTime());
							cal.setTime(calendar.getTime());
							cal.add(Calendar.MONTH, 1);
							toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
							//toaFinanceInvoices.setMonthAmount(cardAmount);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						}
						propertyService.fillProperties(toaFinanceInvoices,false);
						toaFinanceInvoicesService.save(toaFinanceInvoices);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// 天数大于25
				try {
					//结束时间
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.set(Calendar.DATE, 1);
					calendar.add(Calendar.MONTH, 2);
					calendar.add(Calendar.DATE, -1);
					Date lastEndDate=calendar.getTime();
					// 金额
					BigDecimal cardAmount = new BigDecimal(CardAmount);
//					int cardmoney = Integer.valueOf(CardAmount);
//					// 一天的金额
//					int oneDayMoney = (cardmoney * 12) / 365;
					//月
					BigDecimal month=new BigDecimal(12);
					//天
					BigDecimal day=new BigDecimal(365);
					BigDecimal monthmount=mul(cardAmount,month);
					//一天的金额
					BigDecimal oneDayMoney=div(monthmount,day,8);
					// 时间
					for (int i = 0; i < 12; i++) {
						// 月收入
						ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
						int id=toaFinanceMain.getId().intValue();
						toaFinanceInvoices.setMainId(id);
						toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
						toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
						toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
						//机房
						toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
						// 保底带宽
						toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
						toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
						// 端口带宽
						toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
						toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
						// 超流量带宽
						toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
						toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
						// 机柜
						toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
						toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
						// 服务器
						toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
						toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
						// IP
						toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
						toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
						// 交换机
						toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
						toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
						// 链路
						toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
						toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
						// 端口
						toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
						toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
						// 内存
						toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
						toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
						// CPU
						toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
						toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
						// 硬盘
						toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
						toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
						toaFinanceInvoices.setOtherDeductions(0);
						toaFinanceInvoices.setCreateUser((long) 1);
						toaFinanceInvoices.setModifyUser((long) 1);
						toaFinanceInvoices.setCreateUserDept((long) 1003);
						toaFinanceInvoices.setCreateUserOrg((long) 1);
						BigDecimal oneMonthMoney=cardAmount;
						BigDecimal lastMonthMoney=cardAmount;
						//天数
						BigDecimal dayMonth=new BigDecimal(dayLast-dayFirstInt+1); 
//						int oneMonthMoney=cardmoney;
//						int lastMonthMoney=cardmoney;
						// 第一个月的金额
//						if(dayFirstInt!=1){
//							oneMonthMoney=  oneDayMoney *(dayLast-dayFirstInt+1)+ cardmoney;
//							// 最后一个月金额
//							lastMonthMoney=cardmoney- oneDayMoney*(dayLast-dayFirstInt+1);
//						}
						// 第一个月的金额
						if(dayFirstInt!=1){
							BigDecimal oneMonth=mul(oneDayMoney,dayMonth);
							oneMonthMoney=add(oneMonth,cardAmount);
							// 最后一个月金额
							lastMonthMoney=sub(cardAmount,oneMonth);
						}
//						BigDecimal oneNewMoney = new BigDecimal(oneMonthMoney);
//						BigDecimal lastNewMoney = new BigDecimal(lastMonthMoney);
						Calendar cal=Calendar.getInstance();
						if (i == 0) {
							toaFinanceInvoices.setInvoicesStartdate(startDate);
							toaFinanceInvoices.setInvoicesEnddate(lastEndDate);
							cal.setTime(startDate);
							cal.add(Calendar.MONTH, 1);
							toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
							//toaFinanceInvoices.setMonthAmount(oneMonthMoney);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(oneMonthMoney));
						}else if(i==11){
							calendar.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(calendar.getTime());
							calendar.set(Calendar.DATE, dayFirstInt-1);
							toaFinanceInvoices.setInvoicesEnddate(calendar.getTime());
							cal.setTime(calendar.getTime());
							cal.add(Calendar.MONTH, 1);
							toaFinanceInvoices.setInvoicesMonth(format.format(calendar.getTime()));
							//toaFinanceInvoices.setMonthAmount(lastMonthMoney);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(lastMonthMoney));
						}else{
							calendar.add(Calendar.DATE, 1);
							toaFinanceInvoices.setInvoicesStartdate(calendar.getTime());
							calendar.add(Calendar.MONTH, 1);
							calendar.add(Calendar.DATE, -1);
							toaFinanceInvoices.setInvoicesEnddate(calendar.getTime());
							cal.setTime(calendar.getTime());
							cal.add(Calendar.MONTH, 1);
							toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
							//toaFinanceInvoices.setMonthAmount(cardAmount);
							//四舍五入
							toaFinanceInvoices.setMonthAmount(round(cardAmount));
						}
						propertyService.fillProperties(toaFinanceInvoices,false);
						toaFinanceInvoicesService.save(toaFinanceInvoices);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 季度付
	 * 
	 * @author mrb
	 * @param
	 */
	private void quarterPay(Date startDate, Date endDate, String state,ToaFinanceMain toaFinanceMain,String CardAmount) {
		// 判断是否周期，0为周期
		if (state.equals("0")) {
			// 应收月份
			String invoicesMonth;
			// 应收起始日期
			Date invoicesStartdate;
			// 应收终止日期
			Date invoicesEnddate;
			for (int i = 3; i < 13;) {
				// 月收入
				ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
				int id=toaFinanceMain.getId().intValue();
				toaFinanceInvoices.setMainId(id);
				toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
				toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
				//机房
				toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
				// 保底带宽
				toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
				toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
				// 端口带宽
				toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
				toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
				// 超流量带宽
				toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
				toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
				// 机柜
				toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
				toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
				// 服务器
				toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
				toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
				// IP
				toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
				toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
				// 交换机
				toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
				toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
				// 链路
				toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
				toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
				// 端口
				toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
				toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
				// 内存
				toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
				toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
				// CPU
				toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
				toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
				// 硬盘
				toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
				toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
				toaFinanceInvoices.setOtherDeductions(0);
				toaFinanceInvoices.setCreateUser((long) 1);
				toaFinanceInvoices.setModifyUser((long) 1);
				toaFinanceInvoices.setCreateUserDept((long) 1003);
				toaFinanceInvoices.setCreateUserOrg((long) 1);
				Calendar cal = Calendar.getInstance();// 下面的就是把当前日期加一个月
				cal.setTime(startDate);
				cal.add(Calendar.MONTH, i);
				cal.setTime(cal.getTime());
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
				invoicesEnddate = cal.getTime();// 当月结束时间
				cal.setTime(cal.getTime());
				cal.add(Calendar.MONTH, -3);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
				invoicesStartdate = cal.getTime();// 下月开始时间
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				invoicesMonth = format.format(cal.getTime());// 应收月份
				//金额
				//int cardAmount=Integer.valueOf(CardAmount).intValue()*3;
				BigDecimal cardAmount=new BigDecimal(CardAmount);
				BigDecimal threeMount=new BigDecimal(3);
				BigDecimal cardNewAmount=mul(cardAmount,threeMount);
				//BigDecimal cardNewAmount = new BigDecimal(cardAmount);//CardNewAmount为季度付的金额
				// 保存
				try {
					if (i == 3) {
						toaFinanceInvoices.setInvoicesStartdate(startDate);
						toaFinanceInvoices.setInvoicesEnddate(invoicesEnddate);
						toaFinanceInvoices.setInvoicesMonth(format.format(startDate));
						//toaFinanceInvoices.setMonthAmount(cardNewAmount);
						//四舍五入
						toaFinanceInvoices.setMonthAmount(round(cardNewAmount));
					} else {
						toaFinanceInvoices.setInvoicesMonth(invoicesMonth);
						toaFinanceInvoices.setInvoicesStartdate(invoicesStartdate);
						toaFinanceInvoices.setInvoicesEnddate(invoicesEnddate);
						//toaFinanceInvoices.setMonthAmount(cardNewAmount);
						//四舍五入
						toaFinanceInvoices.setMonthAmount(round(cardNewAmount));
					}
					propertyService.fillProperties(toaFinanceInvoices, false);
					toaFinanceInvoicesService.save(toaFinanceInvoices);
					i = i + 3;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			// 补齐自然日
			// 应收起始日期
			Date invoicesStartdate;
			// 应收终止日期
			Date invoicesEnddate;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
			String startFirst = sdf.format(startDate);
			String[] startTwoTime = startFirst.split("-");
			// 天数
			String dayFirstTime = startTwoTime[2];
			int dayFirstInt = Integer.valueOf(dayFirstTime);
			// 月数
			String monthFirstTime = startTwoTime[1];
			int monthFirstInt = Integer.valueOf(monthFirstTime);
			// 年数
			String yearFirstTime = startTwoTime[0];
			int yearFirstList = Integer.valueOf(yearFirstTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.YEAR, 1);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
			Date invoicesLastDate = calendar.getTime();// 最后面一个季度结束时间
			//金额
			//int cardAmount=Integer.valueOf(CardAmount).intValue()*3;
			//BigDecimal cardNewAmount=new BigDecimal(cardAmount);//一个季度的金额
			//一天的金额
			//int dayCardAmount=((cardAmount/3)*12)/365;
			//一个季度的金额
			BigDecimal cardAmount=new BigDecimal(CardAmount);
			BigDecimal threeMount=new BigDecimal(3);
			BigDecimal cardNewAmount=mul(cardAmount,threeMount);
			//一天的金额
			//月
			BigDecimal monthMount=new BigDecimal(12);
			//天
			BigDecimal dayMount=new BigDecimal(365);
			BigDecimal monthMounts=mul(cardAmount,monthMount);
			//一天的金额
			BigDecimal dayCardAmount=div(monthMounts,dayMount,8);
			
			try{
				/*****第一季度开始***/
				// 月收入
				ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
				int id=toaFinanceMain.getId().intValue();
				toaFinanceInvoices.setMainId(id);
				toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
				toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
				//机房
				toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
				// 保底带宽
				toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
				toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
				// 端口带宽
				toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
				toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
				// 超流量带宽
				toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
				toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
				// 机柜
				toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
				toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
				// 服务器
				toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
				toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
				// IP
				toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
				toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
				// 交换机
				toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
				toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
				// 链路
				toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
				toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
				// 端口
				toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
				toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
				// 内存
				toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
				toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
				// CPU
				toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
				toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
				// 硬盘
				toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
				toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
				toaFinanceInvoices.setOtherDeductions(0);
				toaFinanceInvoices.setCreateUser((long) 1);
				toaFinanceInvoices.setModifyUser((long) 1);
				toaFinanceInvoices.setCreateUserDept((long) 1003);
				toaFinanceInvoices.setCreateUserOrg((long) 1);
				toaFinanceInvoices.setInvoicesStartdate(startDate);
				// 拼接第一季度结束时间
				Date invoicesEnd;
				Calendar calendarDate = Calendar.getInstance();
				calendarDate.setTime(startDate);
				//第一季度的天数
				int OneDay=0;
				int iList=5;
				//第一季度金额
				//int numberDay;
				if (monthFirstInt >= 1 && monthFirstInt <= 3) {
					if(monthFirstInt==1&&dayFirstInt==1){
						iList=4;
					}
					calendarDate.add(Calendar.MONTH, 3-monthFirstInt);
					calendarDate.set(Calendar.DATE, 31);
					invoicesEnd=calendarDate.getTime();
					//判断
					int moneyFirst=3-monthFirstInt;
					if(moneyFirst==0){
						//第一季度的天数为31-dayFirstInt(开始时间的天数)
						OneDay=31-dayFirstInt+1;
					}else if(moneyFirst==1){
						//判断一个月的天数
						int day=0;
						switch (monthFirstInt) {
							case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12: day = 31;break;
							case 4:;case 6:;case 9:;case 11:  day = 30;break;
							case 2: day = yearFirstList % 4 == 0 ? 29 : 28;
							default:break;
						}
						//第一季度的天数为     day-dayFirstInt(开始时间的天数)+31
						OneDay= day-dayFirstInt+1+31;
					}else{
						//判断一个月的天数
						int day=0;
						switch (monthFirstInt+1) {
							case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12: day = 31;break;
							case 4:;case 6:;case 9:;case 11:  day = 30;break;
							case 2: day = yearFirstList % 4 == 0 ? 29 : 28;
							default:break;
						}
						//第一季度的天数为    31-dayFirstInt(开始时间的天数)+31+day
						OneDay=31-dayFirstInt+31+day+1;
					}
				} else if (monthFirstInt >= 4 && monthFirstInt <= 6) {
					if(monthFirstInt==4&&dayFirstInt==1){
						iList=4;
					}
					calendarDate.add(Calendar.MONTH, 6-monthFirstInt);
					calendarDate.set(Calendar.DATE, 30);
					invoicesEnd=calendarDate.getTime();
					//判断
					int moneyFirst=6-monthFirstInt;
					if(moneyFirst==0){
						//第一季度的天数为31-dayFirstInt(开始时间的天数)
						OneDay=30-dayFirstInt+1;
					}else if(moneyFirst==1){
						//第一季度的天数为   
						OneDay=31-dayFirstInt+30+1;
					}else{
						//第一季度的天数为    
						OneDay=30-dayFirstInt+30+31+1;
					}
				} else if (monthFirstInt >= 7 && monthFirstInt <= 9) {
					if(monthFirstInt==7&&dayFirstInt==1){
						iList=4;
					}
					calendarDate.add(Calendar.MONTH, 9-monthFirstInt);
					calendarDate.set(Calendar.DATE, 30);
					invoicesEnd=calendarDate.getTime();
					//判断
					int moneyFirst=9-monthFirstInt;
					if(moneyFirst==0){
						//第一季度的天数为31-dayFirstInt(开始时间的天数)
						OneDay=30-dayFirstInt+1;
					}else if(moneyFirst==1){
						//第一季度的天数为
						OneDay=31-dayFirstInt+30+1;
					}else{
						//第一季度的天数为
						OneDay=31-dayFirstInt+31+30+1;
					}
				} else {
					if(monthFirstInt==10&&dayFirstInt==1){
						iList=4;
					}
					calendarDate.add(Calendar.MONTH, 12-monthFirstInt);
					calendarDate.set(Calendar.DATE, 31);
					invoicesEnd=calendarDate.getTime();
					//判断
					int moneyFirst=12-monthFirstInt;
					if(moneyFirst==0){
						//第一季度的天数为31-dayFirstInt(开始时间的天数)
						OneDay=31-dayFirstInt+1;
					}else if(moneyFirst==1){
						//第一季度的天数为
						OneDay=30-dayFirstInt+31+1;
					}else{
						//第一季度的天数为
						OneDay=31-dayFirstInt+31+30+1;
					}
				}
				//第一季度金额
				//numberDay=OneDay*dayCardAmount;
				//最后面季度的金额
				//int numberLastDay=cardAmount-numberDay;
				//第一季度金额
				BigDecimal oneDays=new BigDecimal(OneDay);
				BigDecimal numberDay=mul(oneDays,dayCardAmount);
				//最后面季度的金额
				BigDecimal numberLastDay=sub(cardNewAmount,numberDay);
				toaFinanceInvoices.setInvoicesEnddate(invoicesEnd);
				if(iList==4){
					//toaFinanceInvoices.setMonthAmount(cardNewAmount);
					//四舍五入
					toaFinanceInvoices.setMonthAmount(round(cardNewAmount));
				}else{
					//toaFinanceInvoices.setMonthAmount(numberDay);
					//四舍五入
					toaFinanceInvoices.setMonthAmount(round(numberDay));
				}
				toaFinanceInvoices.setInvoicesMonth(format.format(startDate));
				propertyService.fillProperties(toaFinanceInvoices, false);
				toaFinanceInvoicesService.save(toaFinanceInvoices);
				/***第一季度结束*****/
				//第一季度结束时间月份
				String endFirst = sdf.format(invoicesEnd);
				String[] endTwoTime = endFirst.split("-");
				// 月数
				String monthEndTime = endTwoTime[1];
				int monthEndInt = Integer.valueOf(monthEndTime);
//				int iList;
//				if(dayFirstInt==1){
//					iList=4;
//				}else{
//					iList=5;
//				}
				/**剩下季度开始**/
				Calendar cal = Calendar.getInstance();// 下面的就是把当前日期加一个月
				cal.setTime(invoicesEnd);
				for(int i=1;i<iList;i++){
					cal.add(Calendar.DATE, 1);
					invoicesStartdate=cal.getTime();
					// 下月
					int year = Calendar.YEAR;
					int month = monthEndInt + 3 * i;
					if (month > 12) {
						month = month - 12;
					}
					int day=0;
					switch (month) {
						case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12: day = 31;break;
						case 4:;case 6:;case 9:;case 11:  day = 30;break;
						case 2: day = year % 4 == 0 ? 29 : 28;
						default:break;
					}
					cal.setTime(cal.getTime());
					cal.add(Calendar.MONTH, 2);
					cal.set(Calendar.DATE, day);
					invoicesEnddate =cal.getTime();
					// 月收入
					ToaFinanceInvoices toaFinanceInvoice = new ToaFinanceInvoices();
					toaFinanceInvoice.setMainId(id);
					toaFinanceInvoice.setCompanyName(toaFinanceMain.getCompanyName());
					toaFinanceInvoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
					toaFinanceInvoice.setCompanyType(toaFinanceMain.getCompanyType());
					//机房
					toaFinanceInvoice.setRoomName(toaFinanceMain.getRoomName());
					// 保底带宽
					toaFinanceInvoice.setMinBandwidth(toaFinanceMain.getMinBandwidth());
					toaFinanceInvoice.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
					// 端口带宽
					toaFinanceInvoice.setPortBandwidth(toaFinanceMain.getPortBandwidth());
					toaFinanceInvoice.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
					// 超流量带宽
					toaFinanceInvoice.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
					toaFinanceInvoice.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
					// 机柜
					toaFinanceInvoice.setCabinetNum(toaFinanceMain.getCabinetNum());
					toaFinanceInvoice.setCabinetPrice(toaFinanceMain.getCabinetPrice());
					// 服务器
					toaFinanceInvoice.setServiceNum(toaFinanceMain.getServiceNum());
					toaFinanceInvoice.setServicePrice(toaFinanceMain.getServicePrice());
					// IP
					toaFinanceInvoice.setIpNum(toaFinanceMain.getIpNum());
					toaFinanceInvoice.setIpPrice(toaFinanceMain.getIpPrice());
					// 交换机
					toaFinanceInvoice.setSwitchNum(toaFinanceMain.getSwitchNum());
					toaFinanceInvoice.setSwitchPrice(toaFinanceMain.getSwitchPrice());
					// 链路
					toaFinanceInvoice.setOdfNum(toaFinanceMain.getOdfNum());
					toaFinanceInvoice.setOdfPrice(toaFinanceMain.getOdfPrice());
					// 端口
					toaFinanceInvoice.setPortNum(toaFinanceMain.getPortNum());
					toaFinanceInvoice.setPortPrice(toaFinanceMain.getPortPrice());
					// 内存
					toaFinanceInvoice.setMemoryNum(toaFinanceMain.getMemoryNum());
					toaFinanceInvoice.setMemoryPrice(toaFinanceMain.getMemoryPrice());
					// CPU
					toaFinanceInvoice.setCpuNum(toaFinanceMain.getCpuNum());
					toaFinanceInvoice.setCpuPrice(toaFinanceMain.getCpuPrice());
					// 硬盘
					toaFinanceInvoice.setDiskNum(toaFinanceMain.getDiskNum());
					toaFinanceInvoice.setDiskPrice(toaFinanceMain.getDiskPrice());
					toaFinanceInvoice.setOtherDeductions(0);
					toaFinanceInvoice.setCreateUser((long) 1);
					toaFinanceInvoice.setModifyUser((long) 1);
					toaFinanceInvoice.setCreateUserDept((long) 1003);
					toaFinanceInvoice.setCreateUserOrg((long) 1);
					toaFinanceInvoice.setInvoicesStartdate(invoicesStartdate);
					toaFinanceInvoice.setInvoicesMonth(format.format(invoicesStartdate));
					
					if(i==4){
						toaFinanceInvoice.setInvoicesEnddate(invoicesLastDate);
						//toaFinanceInvoice.setMonthAmount(numberLastDay);
						//四舍五入
						toaFinanceInvoice.setMonthAmount(round(numberLastDay));
					}else{
						toaFinanceInvoice.setInvoicesEnddate(invoicesEnddate);
						//toaFinanceInvoice.setMonthAmount(cardNewAmount);
						//四舍五入
						toaFinanceInvoice.setMonthAmount(round(cardNewAmount));
					}
					propertyService.fillProperties(toaFinanceInvoice, false);
					toaFinanceInvoicesService.save(toaFinanceInvoice);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 后季度付
	 * 
	 * @author
	 * @param
	 */
	public void afterQuarterPay(Date startDate, Date endDate, String state,ToaFinanceMain toaFinanceMain, String CardAmount) {
		// 判断是否周期，0为周期
		if (state.equals("0")) {
			// 应收月份
			String invoicesMonth;
			// 应收起始日期
			Date invoicesStartdate;
			// 应收终止日期
			Date invoicesEnddate;
			for (int i = 3; i < 13;) {
				// 月收入
				ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
				int id=toaFinanceMain.getId().intValue();
				toaFinanceInvoices.setMainId(id);
				toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
				toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
				//机房
				toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
				// 保底带宽
				toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
				toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
				// 端口带宽
				toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
				toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
				// 超流量带宽
				toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
				toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
				// 机柜
				toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
				toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
				// 服务器
				toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
				toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
				// IP
				toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
				toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
				// 交换机
				toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
				toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
				// 链路
				toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
				toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
				// 端口
				toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
				toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
				// 内存
				toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
				toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
				// CPU
				toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
				toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
				// 硬盘
				toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
				toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
				toaFinanceInvoices.setOtherDeductions(0);
				toaFinanceInvoices.setCreateUser((long) 1);
				toaFinanceInvoices.setModifyUser((long) 1);
				toaFinanceInvoices.setCreateUserDept((long) 1003);
				toaFinanceInvoices.setCreateUserOrg((long) 1);
				Calendar cal = Calendar.getInstance();// 下面的就是把当前日期加一个月
				cal.setTime(startDate);
				cal.add(Calendar.MONTH, i);
				cal.setTime(cal.getTime());
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
				invoicesEnddate = cal.getTime();// 当月结束时间
				cal.setTime(cal.getTime());
				cal.add(Calendar.MONTH, -3);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
				invoicesStartdate = cal.getTime();// 下月开始时间
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				invoicesMonth = format.format(cal.getTime());// 应收月份
				//金额
				//int cardAmount=Integer.valueOf(CardAmount).intValue()*3;
				//BigDecimal cardNewAmount = new BigDecimal(cardAmount);//CardNewAmount为季度付的金额
				BigDecimal cardAmount=new BigDecimal(CardAmount);
				BigDecimal threeMount=new BigDecimal(3);
				BigDecimal cardNewAmount=mul(cardAmount,threeMount);
				// 保存
				try {
					if (i == 3) {
						toaFinanceInvoices.setInvoicesStartdate(startDate);
						toaFinanceInvoices.setInvoicesEnddate(invoicesEnddate);
						toaFinanceInvoices.setInvoicesMonth(format.format(startDate));
						//toaFinanceInvoices.setMonthAmount(cardNewAmount);
						//四舍五入
						toaFinanceInvoices.setMonthAmount(round(cardNewAmount));
					} else {
						toaFinanceInvoices.setInvoicesMonth(invoicesMonth);
						toaFinanceInvoices.setInvoicesStartdate(invoicesStartdate);
						toaFinanceInvoices.setInvoicesEnddate(invoicesEnddate);
						//toaFinanceInvoices.setMonthAmount(cardNewAmount);
						//四舍五入
						toaFinanceInvoices.setMonthAmount(round(cardNewAmount));
					}
					propertyService.fillProperties(toaFinanceInvoices, false);
					toaFinanceInvoicesService.save(toaFinanceInvoices);
					i = i + 3;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			// 补齐自然日
			// 应收起始日期
			Date invoicesStartdate;
			// 应收终止日期
			Date invoicesEnddate;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
			String startFirst = sdf.format(startDate);
			String[] startTwoTime = startFirst.split("-");
			// 天数
			String dayFirstTime = startTwoTime[2];
			int dayFirstInt = Integer.valueOf(dayFirstTime);
			// 月数
			String monthFirstTime = startTwoTime[1];
			int monthFirstInt = Integer.valueOf(monthFirstTime);
			// 年数
			String yearFirstTime = startTwoTime[0];
			int yearFirstList = Integer.valueOf(yearFirstTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.YEAR, 1);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
			Date invoicesLastDate = calendar.getTime();// 最后面一个季度结束时间
			//金额
			//int cardAmount=Integer.valueOf(CardAmount).intValue()*3;
			//BigDecimal cardNewAmount=new BigDecimal(cardAmount);//一个季度的金额
			//一天的金额
			//int dayCardAmount=((cardAmount/3)*12)/365;

			//一个季度的金额
			BigDecimal cardAmount=new BigDecimal(CardAmount);
			BigDecimal threeMount=new BigDecimal(3);
			BigDecimal cardNewAmount=mul(cardAmount,threeMount);
			//一天的金额
			//月
			BigDecimal monthMount=new BigDecimal(12);
			//天
			BigDecimal dayMount=new BigDecimal(365);
			BigDecimal monthMounts=mul(cardAmount,monthMount);
			//一天的金额
			BigDecimal dayCardAmount=div(monthMounts,dayMount,8);
			try{
				/*****第一季度开始***/
				// 月收入
				ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
				int id=toaFinanceMain.getId().intValue();
				toaFinanceInvoices.setMainId(id);
				toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
				toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
				toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
				//机房
				toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
				// 保底带宽
				toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
				toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
				// 端口带宽
				toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
				toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
				// 超流量带宽
				toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
				toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
				// 机柜
				toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
				toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
				// 服务器
				toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
				toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
				// IP
				toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
				toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
				// 交换机
				toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
				toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
				// 链路
				toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
				toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
				// 端口
				toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
				toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
				// 内存
				toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
				toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
				// CPU
				toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
				toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
				// 硬盘
				toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
				toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
				toaFinanceInvoices.setOtherDeductions(0);
				toaFinanceInvoices.setCreateUser((long) 1);
				toaFinanceInvoices.setModifyUser((long) 1);
				toaFinanceInvoices.setCreateUserDept((long) 1003);
				toaFinanceInvoices.setCreateUserOrg((long) 1);
				toaFinanceInvoices.setInvoicesStartdate(startDate);
				// 拼接第一季度结束时间
				Date invoicesEnd;
				//第一季度的天数
				int OneDay=0;
				int iList=5;
				//第一季度金额
				//int numberDay;
				Calendar calendarDate = Calendar.getInstance();
				calendarDate.setTime(startDate);
				if (monthFirstInt >= 1 && monthFirstInt <= 3) {
					if(monthFirstInt==1&&dayFirstInt==1){
						iList=4;
					}
					calendarDate.add(Calendar.MONTH, 3-monthFirstInt);
					calendarDate.set(Calendar.DATE, 31);
					invoicesEnd=calendarDate.getTime();
					//判断
					int moneyFirst=3-monthFirstInt;
					if(moneyFirst==0){
						//第一季度的天数为31-dayFirstInt(开始时间的天数)
						OneDay=31-dayFirstInt+1;
					}else if(moneyFirst==1){
						//判断一个月的天数
						int day=0;
						switch (monthFirstInt) {
							case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12: day = 31;break;
							case 4:;case 6:;case 9:;case 11:  day = 30;break;
							case 2: day = yearFirstList % 4 == 0 ? 29 : 28;
							default:break;
						}
						//第一季度的天数为     day-dayFirstInt(开始时间的天数)+31
						OneDay= day-dayFirstInt+31+1;
					}else{
						//判断一个月的天数
						int day=0;
						switch (monthFirstInt+1) {
							case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12: day = 31;break;
							case 4:;case 6:;case 9:;case 11:  day = 30;break;
							case 2: day = yearFirstList % 4 == 0 ? 29 : 28;
							default:break;
						}
						//第一季度的天数为    31-dayFirstInt(开始时间的天数)+31+day
						OneDay=31-dayFirstInt+31+day+1;
					}
				} else if (monthFirstInt >= 4 && monthFirstInt <= 6) {
					if(monthFirstInt==4&&dayFirstInt==1){
						iList=4;
					}
					calendarDate.add(Calendar.MONTH, 6-monthFirstInt);
					calendarDate.set(Calendar.DATE, 30);
					invoicesEnd=calendarDate.getTime();
					//判断
					int moneyFirst=6-monthFirstInt;
					if(moneyFirst==0){
						//第一季度的天数为31-dayFirstInt(开始时间的天数)
						OneDay=30-dayFirstInt+1;
					}else if(moneyFirst==1){
						//第一季度的天数为   
						OneDay=31-dayFirstInt+30+1;
					}else{
						//第一季度的天数为    
						OneDay=30-dayFirstInt+30+31+1;
					}
				} else if (monthFirstInt >= 7 && monthFirstInt <= 9) {
					if(monthFirstInt==7&&dayFirstInt==1){
						iList=4;
					}
					calendarDate.add(Calendar.MONTH, 9-monthFirstInt);
					calendarDate.set(Calendar.DATE, 30);
					invoicesEnd=calendarDate.getTime();
					//判断
					int moneyFirst=9-monthFirstInt;
					if(moneyFirst==0){
						//第一季度的天数为31-dayFirstInt(开始时间的天数)
						OneDay=30-dayFirstInt+1;
					}else if(moneyFirst==1){
						//第一季度的天数为
						OneDay=31-dayFirstInt+30+1;
					}else{
						//第一季度的天数为
						OneDay=31-dayFirstInt+31+30+1;
					}
				} else {
					if(monthFirstInt==10&&dayFirstInt==1){
						iList=4;
					}
					calendarDate.add(Calendar.MONTH, 12-monthFirstInt);
					calendarDate.set(Calendar.DATE, 31);
					invoicesEnd=calendarDate.getTime();
					//判断
					int moneyFirst=12-monthFirstInt;
					if(moneyFirst==0){
						//第一季度的天数为31-dayFirstInt(开始时间的天数)
						OneDay=31-dayFirstInt+1;
					}else if(moneyFirst==1){
						//第一季度的天数为
						OneDay=30-dayFirstInt+31+1;
					}else{
						//第一季度的天数为
						OneDay=31-dayFirstInt+31+30+1;
					}
				}
				//第一季度金额
				//numberDay=OneDay*dayCardAmount;
				//最后面季度的金额
				//int numberLastDay=cardAmount-numberDay;
				//第一季度金额
				BigDecimal oneDays=new BigDecimal(OneDay);
				BigDecimal numberDay=mul(oneDays,dayCardAmount);
				//最后面季度的金额
				BigDecimal numberLastDay=sub(cardNewAmount,numberDay);
				toaFinanceInvoices.setInvoicesEnddate(invoicesEnd);
				if(iList==4){
					//toaFinanceInvoices.setMonthAmount(cardNewAmount);
					//四舍五入
					toaFinanceInvoices.setMonthAmount(round(cardNewAmount));
				}else{
					//toaFinanceInvoices.setMonthAmount(numberDay);
					//四舍五入
					toaFinanceInvoices.setMonthAmount(round(numberDay));
				}
				Calendar calStart=Calendar.getInstance();
				calStart.setTime(startDate);
				calStart.add(Calendar.MONTH, 1);
				toaFinanceInvoices.setInvoicesMonth(format.format(calStart.getTime()));
				propertyService.fillProperties(toaFinanceInvoices, false);
				toaFinanceInvoicesService.save(toaFinanceInvoices);
				/***第一季度结束*****/
				//第一季度结束时间月份
				String endFirst = sdf.format(invoicesEnd);
				String[] endTwoTime = endFirst.split("-");
				// 月数
				String monthEndTime = endTwoTime[1];
				int monthEndInt = Integer.valueOf(monthEndTime);
				/**剩下季度开始**/
//				int iList;
//				if(dayFirstInt==1){
//					iList=4;
//				}else{
//					iList=5;
//				}
				Calendar cal = Calendar.getInstance();// 下面的就是把当前日期加一个月
				cal.setTime(invoicesEnd);
				for(int i=1;i<iList;i++){
					cal.add(Calendar.DATE, 1);
					invoicesStartdate=cal.getTime();
					// 下月
					int year = Calendar.YEAR;
					int month = monthEndInt + 3 * i;
					if (month > 12) {
						month = month - 12;
					}
					int day=0;
					switch (month) {
						case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12: day = 31;break;
						case 4:;case 6:;case 9:;case 11:  day = 30;break;
						case 2: day = year % 4 == 0 ? 29 : 28;
						default:break;
					}
					cal.setTime(cal.getTime());
					cal.add(Calendar.MONTH, 2);
					cal.set(Calendar.DATE, day);
					invoicesEnddate =cal.getTime();
					// 月收入
					ToaFinanceInvoices toaFinanceInvoice = new ToaFinanceInvoices();
					toaFinanceInvoice.setMainId(id);
					toaFinanceInvoice.setCompanyName(toaFinanceMain.getCompanyName());
					toaFinanceInvoice.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
					toaFinanceInvoice.setCompanyType(toaFinanceMain.getCompanyType());
					//机房
					toaFinanceInvoice.setRoomName(toaFinanceMain.getRoomName());
					// 保底带宽
					toaFinanceInvoice.setMinBandwidth(toaFinanceMain.getMinBandwidth());
					toaFinanceInvoice.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
					// 端口带宽
					toaFinanceInvoice.setPortBandwidth(toaFinanceMain.getPortBandwidth());
					toaFinanceInvoice.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
					// 超流量带宽
					toaFinanceInvoice.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
					toaFinanceInvoice.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
					// 机柜
					toaFinanceInvoice.setCabinetNum(toaFinanceMain.getCabinetNum());
					toaFinanceInvoice.setCabinetPrice(toaFinanceMain.getCabinetPrice());
					// 服务器
					toaFinanceInvoice.setServiceNum(toaFinanceMain.getServiceNum());
					toaFinanceInvoice.setServicePrice(toaFinanceMain.getServicePrice());
					// IP
					toaFinanceInvoice.setIpNum(toaFinanceMain.getIpNum());
					toaFinanceInvoice.setIpPrice(toaFinanceMain.getIpPrice());
					// 交换机
					toaFinanceInvoice.setSwitchNum(toaFinanceMain.getSwitchNum());
					toaFinanceInvoice.setSwitchPrice(toaFinanceMain.getSwitchPrice());
					// 链路
					toaFinanceInvoice.setOdfNum(toaFinanceMain.getOdfNum());
					toaFinanceInvoice.setOdfPrice(toaFinanceMain.getOdfPrice());
					// 端口
					toaFinanceInvoice.setPortNum(toaFinanceMain.getPortNum());
					toaFinanceInvoice.setPortPrice(toaFinanceMain.getPortPrice());
					// 内存
					toaFinanceInvoice.setMemoryNum(toaFinanceMain.getMemoryNum());
					toaFinanceInvoice.setMemoryPrice(toaFinanceMain.getMemoryPrice());
					// CPU
					toaFinanceInvoice.setCpuNum(toaFinanceMain.getCpuNum());
					toaFinanceInvoice.setCpuPrice(toaFinanceMain.getCpuPrice());
					// 硬盘
					toaFinanceInvoice.setDiskNum(toaFinanceMain.getDiskNum());
					toaFinanceInvoice.setDiskPrice(toaFinanceMain.getDiskPrice());
					toaFinanceInvoice.setOtherDeductions(0);
					toaFinanceInvoice.setCreateUser((long) 1);
					toaFinanceInvoice.setModifyUser((long) 1);
					toaFinanceInvoice.setCreateUserDept((long) 1003);
					toaFinanceInvoice.setCreateUserOrg((long) 1);
					toaFinanceInvoice.setInvoicesStartdate(invoicesStartdate);
					if(i==4){
						//toaFinanceInvoice.setMonthAmount(numberLastDay);
						//四舍五入
						toaFinanceInvoice.setMonthAmount(round(numberLastDay));
						toaFinanceInvoice.setInvoicesEnddate(invoicesLastDate);
						//应收月份
						Calendar calInvoicesEnd = Calendar.getInstance();
						calInvoicesEnd.setTime(invoicesLastDate);
						calInvoicesEnd.add(Calendar.MONTH,1);
						toaFinanceInvoice.setInvoicesMonth(format.format(calInvoicesEnd.getTime()));
					}else{
						//toaFinanceInvoice.setMonthAmount(cardNewAmount);
						//四舍五入
						toaFinanceInvoice.setMonthAmount(round(cardNewAmount));
						toaFinanceInvoice.setInvoicesEnddate(invoicesEnddate);
						//应收月份
						Calendar calInvoicesEnd = Calendar.getInstance();
						calInvoicesEnd.setTime(invoicesEnddate);
						calInvoicesEnd.add(Calendar.MONTH,1);
						toaFinanceInvoice.setInvoicesMonth(format.format(calInvoicesEnd.getTime()));
					}
					propertyService.fillProperties(toaFinanceInvoice, false);
					toaFinanceInvoicesService.save(toaFinanceInvoice);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 半年付
	 * 
	 * @author mrb
	 * @param
	 */
	public void halfYearPay(Date startDate, Date endDate,ToaFinanceMain toaFinanceMain,String CardAmount) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date firstDate;// 上半年结束时间
		Date firstOneDate;// 下半年开始时间
		Date firstLastDate;// 下半年结束时间
		String invoicesMonthOne;
		String invoicesMonthTwo;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.MONTH, 6);
		cal.add(Calendar.DATE, -1);
		firstDate = cal.getTime();
		cal.setTime(cal.getTime());
		cal.add(Calendar.DATE, 1);
		firstOneDate = cal.getTime();
		cal.setTime(cal.getTime());
		cal.add(Calendar.MONTH, 6);
		cal.add(Calendar.DATE, -1);
		firstLastDate = cal.getTime();
		String startFirst = sdf.format(startDate);
		String[] startTwoTime = startFirst.split("-");
		// 天数
		String dayFirstTime = startTwoTime[2];
		int dayFirstInt = Integer.valueOf(dayFirstTime);
		if (dayFirstInt < 25) {
			invoicesMonthOne = format.format(startDate);
			cal.setTime(cal.getTime());
			cal.add(Calendar.MONTH, -6);
			invoicesMonthTwo = format.format(cal.getTime());
		} else {
			cal.setTime(cal.getTime());
			cal.add(Calendar.MONTH, -11);
			invoicesMonthOne = format.format(cal.getTime());
			cal.setTime(cal.getTime());
			cal.add(Calendar.MONTH, 6);
			invoicesMonthTwo = format.format(cal.getTime());
		}
		// 金额
		//int cardmoney = Integer.valueOf(CardAmount);
		//int countMoney = cardmoney * 6;
		BigDecimal cardmoney=new BigDecimal(CardAmount);
		BigDecimal sixMount=new BigDecimal(6);
		BigDecimal countMoney=mul(cardmoney,sixMount);
		for (int i = 0; i < 2; i++) {
			// 月收入
			ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
			int id=toaFinanceMain.getId().intValue();
			toaFinanceInvoices.setMainId(id);
			toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
			toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
			toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
			//机房
			toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
			// 保底带宽
			toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
			toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
			// 端口带宽
			toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
			toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
			// 超流量带宽
			toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
			toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
			// 机柜
			toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
			toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
			// 服务器
			toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
			toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
			// IP
			toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
			toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
			// 交换机
			toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
			toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
			// 链路
			toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
			toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
			// 端口
			toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
			toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
			// 内存
			toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
			toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
			// CPU
			toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
			toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
			// 硬盘
			toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
			toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
			toaFinanceInvoices.setOtherDeductions(0);
			toaFinanceInvoices.setCreateUser((long) 1);
			toaFinanceInvoices.setModifyUser((long) 1);
			toaFinanceInvoices.setCreateUserDept((long) 1003);
			toaFinanceInvoices.setCreateUserOrg((long) 1);
			try {
				if (i == 0) {
					toaFinanceInvoices.setInvoicesStartdate(startDate);
					toaFinanceInvoices.setInvoicesEnddate(firstDate);
					toaFinanceInvoices.setInvoicesMonth(invoicesMonthOne);
				} else {
					toaFinanceInvoices.setInvoicesStartdate(firstOneDate);
					toaFinanceInvoices.setInvoicesEnddate(firstLastDate);
					toaFinanceInvoices.setInvoicesMonth(invoicesMonthTwo);
				}
				//toaFinanceInvoices.setMonthAmount(countMoney);
				//四舍五入
				toaFinanceInvoices.setMonthAmount(round(countMoney));
				propertyService.fillProperties(toaFinanceInvoices, false);
				toaFinanceInvoicesService.save(toaFinanceInvoices);
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 年付
	 * 
	 * @author mrb
	 * @param
	 */
	public void yearPay(Date startDate, Date endDate,ToaFinanceMain toaFinanceMain, String CardAmount) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			// 月收入
			ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
			int id=toaFinanceMain.getId().intValue();
			toaFinanceInvoices.setMainId(id);
			toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
			toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
			toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
			//机房
			toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
			// 保底带宽
			toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
			toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
			// 端口带宽
			toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
			toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
			// 超流量带宽
			toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
			toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
			// 机柜
			toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
			toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
			// 服务器
			toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
			toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
			// IP
			toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
			toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
			// 交换机
			toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
			toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
			// 链路
			toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
			toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
			// 端口
			toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
			toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
			// 内存
			toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
			toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
			// CPU
			toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
			toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
			// 硬盘
			toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
			toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
			toaFinanceInvoices.setOtherDeductions(0);
			toaFinanceInvoices.setCreateUser((long) 1);
			toaFinanceInvoices.setModifyUser((long) 1);
			toaFinanceInvoices.setCreateUserDept((long) 1003);
			toaFinanceInvoices.setCreateUserOrg((long) 1);
			// 后面时间
			Date invoicesEnddate;
			// 应收月份
			String invoicesMonth;
			String startFirst = sdf.format(startDate);
			String[] startTwoTime = startFirst.split("-");
			// 天数
			String dayFirstTime = startTwoTime[2];
			int dayFirstInt = Integer.valueOf(dayFirstTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, 12);
			cal.add(Calendar.DATE, -1);
			invoicesEnddate = cal.getTime();
			// 判断天数
			if (dayFirstInt < 25) {
				invoicesMonth = format.format(startDate);
			} else {
				cal.setTime(startDate);
				cal.add(Calendar.MONTH, -11);
				invoicesMonth = format.format(cal.getTime());
			}
			// 金额
			//int money = Integer.valueOf(CardAmount);
			//int countMoney = money * 12;
			BigDecimal money=new BigDecimal(CardAmount);
			BigDecimal yearMount=new BigDecimal(12);
			BigDecimal countMoney=mul(money,yearMount);
			toaFinanceInvoices.setInvoicesStartdate(startDate);
			toaFinanceInvoices.setInvoicesEnddate(invoicesEnddate);
			toaFinanceInvoices.setInvoicesMonth(invoicesMonth);
			//toaFinanceInvoices.setMonthAmount(countMoney);
			//四舍五入
			toaFinanceInvoices.setMonthAmount(round(countMoney));
			propertyService.fillProperties(toaFinanceInvoices, false);
			toaFinanceInvoicesService.save(toaFinanceInvoices);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 其他付
	 * 
	 * @author 
	 * @param
	 */
	public void otherPay(Date startDate, Date endDate,ToaFinanceMain toaFinanceMain, String CardAmount){
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			// 月收入
			ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
			int id=toaFinanceMain.getId().intValue();
			toaFinanceInvoices.setMainId(id);
			toaFinanceInvoices.setCompanyName(toaFinanceMain.getCompanyName());
			toaFinanceInvoices.setCompanyId(Integer.valueOf(toaFinanceMain.getCompanyId()));
			toaFinanceInvoices.setCompanyType(toaFinanceMain.getCompanyType());
			//机房
			toaFinanceInvoices.setRoomName(toaFinanceMain.getRoomName());
			// 保底带宽
			toaFinanceInvoices.setMinBandwidth(toaFinanceMain.getMinBandwidth());
			toaFinanceInvoices.setMinBandwidthPrice(toaFinanceMain.getMinBandwidthPrice());
			// 端口带宽
			toaFinanceInvoices.setPortBandwidth(toaFinanceMain.getPortBandwidth());
			toaFinanceInvoices.setPortBandwidthPrice(toaFinanceMain.getPortBandwidthPrice());
			// 超流量带宽
			toaFinanceInvoices.setOverflowBandwidth(toaFinanceMain.getOverflowBandwidth());
			toaFinanceInvoices.setOverflowBandwidthPrice(toaFinanceMain.getOverflowBandwidthPrice());
			// 机柜
			toaFinanceInvoices.setCabinetNum(toaFinanceMain.getCabinetNum());
			toaFinanceInvoices.setCabinetPrice(toaFinanceMain.getCabinetPrice());
			// 服务器
			toaFinanceInvoices.setServiceNum(toaFinanceMain.getServiceNum());
			toaFinanceInvoices.setServicePrice(toaFinanceMain.getServicePrice());
			// IP
			toaFinanceInvoices.setIpNum(toaFinanceMain.getIpNum());
			toaFinanceInvoices.setIpPrice(toaFinanceMain.getIpPrice());
			// 交换机
			toaFinanceInvoices.setSwitchNum(toaFinanceMain.getSwitchNum());
			toaFinanceInvoices.setSwitchPrice(toaFinanceMain.getSwitchPrice());
			// 链路
			toaFinanceInvoices.setOdfNum(toaFinanceMain.getOdfNum());
			toaFinanceInvoices.setOdfPrice(toaFinanceMain.getOdfPrice());
			// 端口
			toaFinanceInvoices.setPortNum(toaFinanceMain.getPortNum());
			toaFinanceInvoices.setPortPrice(toaFinanceMain.getPortPrice());
			// 内存
			toaFinanceInvoices.setMemoryNum(toaFinanceMain.getMemoryNum());
			toaFinanceInvoices.setMemoryPrice(toaFinanceMain.getMemoryPrice());
			// CPU
			toaFinanceInvoices.setCpuNum(toaFinanceMain.getCpuNum());
			toaFinanceInvoices.setCpuPrice(toaFinanceMain.getCpuPrice());
			// 硬盘
			toaFinanceInvoices.setDiskNum(toaFinanceMain.getDiskNum());
			toaFinanceInvoices.setDiskPrice(toaFinanceMain.getDiskPrice());
			toaFinanceInvoices.setOtherDeductions(0);
			toaFinanceInvoices.setCreateUser((long) 1);
			toaFinanceInvoices.setModifyUser((long) 1);
			toaFinanceInvoices.setCreateUserDept((long) 1003);
			toaFinanceInvoices.setCreateUserOrg((long) 1);
			toaFinanceInvoices.setInvoicesStartdate(startDate);
			toaFinanceInvoices.setInvoicesEnddate(endDate);
			//根据操作类型区分应收月份
			if(toaFinanceMain.getPayType().equals("超流量")){
				Calendar cal=Calendar.getInstance();
				cal.setTime(startDate);
				cal.add(Calendar.MONTH, 1);
				toaFinanceInvoices.setInvoicesMonth(format.format(cal.getTime()));
			}else{
				toaFinanceInvoices.setInvoicesMonth(format.format(startDate));
			}
			//toaFinanceInvoices.setInvoicesMonth(format.format(startDate));
			//四舍五入
			BigDecimal cardNewAmount=new BigDecimal(CardAmount);
			toaFinanceInvoices.setMonthAmount(round(cardNewAmount));
			//toaFinanceInvoices.setMonthAmount(new BigDecimal(CardAmount));
			propertyService.fillProperties(toaFinanceInvoices, false);
			toaFinanceInvoicesService.save(toaFinanceInvoices);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//乘法
	public static BigDecimal mul(BigDecimal d1,BigDecimal d2){
		return d1.multiply(d2);
	}
	//除法
	public static BigDecimal div(BigDecimal d1,BigDecimal d2,int len){
		return d1.divide(d2, len, BigDecimal.ROUND_HALF_UP);
	}
	//减法
	public static BigDecimal sub(BigDecimal d1,BigDecimal d2){
		return d1.subtract(d2);
	}
	//加法
	public static BigDecimal add(BigDecimal d1, BigDecimal d2){
		return d1.add(d2);
	}
	//四舍五入方法
	public static BigDecimal round(BigDecimal b){
		return b.setScale(0, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);  
	}
	

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
	//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String invoicesStartdate;
//		String invoicesEnddate;
//		try {
//			String newDate = "2017-02-01";
//			String[] startTwoTime = newDate.split("-");
//			// 月数
//			String monthFirstTime = startTwoTime[1];
//			int monthFirstInt = Integer.valueOf(monthFirstTime);
//			Date newDate1 = sdf.parse(newDate);
//			// 时间
//			Calendar cal = Calendar.getInstance();// 下面的就是把当前日期加一个月
//			cal.setTime(newDate1);
//			cal.set(Calendar.MONTH, 3);
//			cal.add(Calendar.DATE, -1);
//			System.out.println(sdf.format(cal.getTime()));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    //保留2位小数  
//	    BigDecimal result = b.setScale(0, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);  
//	    System.out.println(result);  //111231.56 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		String startFirst = "2017-12-29";
		Calendar casa=Calendar.getInstance();
		try {
			casa.setTime(sdf.parse(startFirst));
			casa.add(Calendar.MONTH, 1);
			System.out.print(sdf1.format(casa.getTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.print(startFirst);
		System.out.print("---");
		String[] startTwoTime = startFirst.split("-");
		// 天数
		String dayFirstTime = startTwoTime[2];
		int dayFirstInt = Integer.valueOf(dayFirstTime);
		// 月数
		String monthFirstTime = startTwoTime[1];
		int monthFirstInt = Integer.valueOf(monthFirstTime);
		// 年数
		String yearFirstTime = startTwoTime[0];
		int yearFirstList = Integer.valueOf(yearFirstTime);
		//当月天数
		int monthInt=2;
		int dayLast = 0;
		switch (monthInt) {
			case 1:;case 3:;case 5:;case 7:;case 8:;case 10:;case 12:  dayLast = 31; break;
			case 4:;case 6:;case 9:;case 11:  dayLast = 30; break;
			case 2: dayLast = yearFirstList % 4 == 0 ? 29 : 28;
			default: break;
		}
		Calendar cal=Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(startFirst));
			for(int i=1;i<13;i++){
				if (i == 1) {
					cal.add(Calendar.MONTH, 1);
					if(monthFirstInt==1){
						cal.set(Calendar.DATE, dayLast);
					}else{
						cal.set(Calendar.DATE, dayFirstInt-1);
					}
					System.out.println(sdf.format(cal.getTime()));
				} else {
					cal.add(Calendar.DATE, 1);
					System.out.print(sdf.format(cal.getTime()));
					System.out.print("----");
					int monthNew = cal.get(Calendar.MONTH) + 1;
					int dayNew = cal.get(Calendar.DATE);
					if(dayNew==dayFirstInt){
						cal.add(Calendar.MONTH, 1);
						if(monthNew==1){
							cal.set(Calendar.DATE, dayLast);
						}else{
							cal.set(Calendar.DATE, dayFirstInt-1);
						}
						System.out.println(sdf.format(cal.getTime()));
					}else{
						if(monthNew==1||monthNew==3||monthNew==5||monthNew==7||monthNew==8||monthNew==10||monthNew==12){
							cal.set(Calendar.DATE, dayFirstInt-1);
							System.out.println(sdf.format(cal.getTime()));
						}
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
