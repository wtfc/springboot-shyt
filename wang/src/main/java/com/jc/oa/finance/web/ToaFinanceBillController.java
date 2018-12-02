package com.jc.oa.finance.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.finance.domain.ToaFinanceBill;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
import com.jc.oa.finance.service.IToaFinanceBillService;
import com.jc.oa.finance.service.IToaFinanceInvoicesService;
import com.jc.oa.network.domain.ToaNetworkIpstop;
/**
 * @author mrb
 * @version 账单信息
*/
@Controller
@RequestMapping(value="/finance/toaFinanceBill")
public class ToaFinanceBillController extends BaseController{
	
	@Autowired 
	private IToaFinanceBillService toaFinanceBillService; 
	@Autowired 
	private IToaFinanceInvoicesService toaFinanceInvoicesService;
	
	public ToaFinanceBillController(){
	}
	
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单信息",operateFuncNm="save",operateDescribe="对账单信息进行发起操作")
	public Map<String,Object> save(ToaFinanceBill toaFinanceBill,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(!"false".equals(resultMap.get("success"))){
			toaFinanceBillService.save(toaFinanceBill);
			resultMap.put("success", "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
		}
		return resultMap;
	}

	/**
	* 修改方法
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单信息",operateFuncNm="update",operateDescribe="对账单信息进行更新操作")
	public Map<String, Object> update(ToaFinanceBill toaFinanceBill, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaFinanceBillService.update(toaFinanceBill);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/**
	* 提交预审
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateForm.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单信息",operateFuncNm="update",operateDescribe="对账单信息进行更新操作")
	public Map<String, Object> updateForm(ToaFinanceBill toaFinanceBill, BindingResult result,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		toaFinanceBill.setState(0);
		Integer flag = toaFinanceBillService.update(toaFinanceBill);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}

	/**
	* 修改方法
	* @param TimeSet timeSet 实体类
	* @return Integer 更新的数目
	* @author
	*/
	@RequestMapping(value="updateFalse.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单回退",operateFuncNm="updateFalse",operateDescribe="对账单账单回退进行操作")
	public Map<String, Object> updateFalse(ToaFinanceBill toaFinanceBill, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		toaFinanceBill.setState(3);
		Integer flag = toaFinanceBillService.update(toaFinanceBill);
		if (flag == 1) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
		}
		String token = getToken(request);
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	/**
	 * 获取单条记录方法
	 * @param TimeSet timeSet 实体类
	 * @return TimeSet 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单信息",operateFuncNm="get",operateDescribe="对账单信息进行单条查询操作")
	public ToaFinanceBill get(ToaFinanceBill toaFinanceBill,HttpServletRequest request) throws Exception{
		return toaFinanceBillService.get(toaFinanceBill);
	}
	
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单信息",operateFuncNm="get",operateDescribe="账单信息分页查询")
	public PageManager manageList(ToaFinanceBill toaFinanceBill,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaFinanceBill.getOrderBy())) {
			toaFinanceBill.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaFinanceBillService.query(toaFinanceBill, page);
		return page_; 
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="账单信息",operateFuncNm="manage",operateDescribe="对账单信息进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "finance/toaFinanceBill/toaFinanceBill";
	}
	
	/**
	 * 删除方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单信息表",operateFuncNm="deleteByIds",operateDescribe="对账单信息表进行删除")
	public  Map<String, Object> deleteByIds(ToaFinanceBill toaFinanceBill,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaFinanceBill.setPrimaryKeys(ids.split(","));	
		toaFinanceBill.setId(Long.valueOf(ids));
		if(toaFinanceBillService.deleteByIds(toaFinanceBill) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}

	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	@ActionLog(operateModelNm="账单信息表",operateFuncNm="loadForm",operateDescribe="对账单信息表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "finance/toaFinanceBill/toaFinanceBillForm";
	}
	
	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="print.action")
	@ActionLog(operateModelNm="账单打印",operateFuncNm="print",operateDescribe="对账单打印跳转")
	public String print(ToaFinanceBill toaFinanceBill,Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		String companyType= request.getParameter("companyType");
		if(!StringUtil.isEmpty(id)){
			toaFinanceBill.setId(Long.valueOf(id));
			ToaFinanceBill toaFinanceBills=toaFinanceBillService.get(toaFinanceBill);
			model.addAttribute("toaFinanceBill", toaFinanceBills);
			String[] ids = toaFinanceBills.getRemarkId().split(",");
			List<ToaFinanceInvoices> invoicesList =new ArrayList<ToaFinanceInvoices>();
			for(int i=0;i<ids.length;i++){
				ToaFinanceInvoices toaFinanceInvoices=new ToaFinanceInvoices();
				toaFinanceInvoices.setId(Long.valueOf(ids[i]));
				invoicesList.add(toaFinanceInvoicesService.get(toaFinanceInvoices));
				}
			model.addAttribute("invoicesList", invoicesList);
			model.addAttribute("Id", id);
		}
		if(companyType.equals("云服务")||companyType.equals("CDN")){
			return "finance/toaFinanceBill/isp_print";
		}else{
			return "finance/toaFinanceBill/idc_print";
		}
	}
	
	/**
	 * 业务审核 state 1
	 * @param TimeSet timeSet 实体类
	 * @param String ids 主键
	 * @return Integer 
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="state.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单信息表",operateFuncNm="state",operateDescribe="对账单信息表进行删除")
	public  Map<String, Object> state(ToaFinanceBill toaFinanceBill,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaFinanceBill.setPrimaryKeys(ids.split(","));
		toaFinanceBill.setState(0);
		if(toaFinanceBillService.state(toaFinanceBill) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_002"));
		}
		return resultMap;
	}
	
	/**
	 * 审批状态
	 * @param TimeSet timeSet 实体类
	 * @param String ids 主键
	 * @return Integer 
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="printState.action")
	@ResponseBody
	@ActionLog(operateModelNm="账单信息表",operateFuncNm="printState",operateDescribe="已打印状态")
	public  Map<String, Object> printState(ToaFinanceBill toaFinanceBill,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaFinanceBill.setPrimaryKeys(ids.split(","));
		toaFinanceBill.setState(2);
		if(toaFinanceBillService.state(toaFinanceBill) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_001"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_WORKFLOW_002"));
		}
		return resultMap;
	}
	
	/**
	 * @description 账单审核跳转
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="billState.action")
	@ActionLog(operateModelNm="账单审核",operateFuncNm="billState",operateDescribe="对账单审核跳转")
	public String billState(ToaFinanceBill toaFinanceBill,Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		String companyType= request.getParameter("companyType");
		if(!StringUtil.isEmpty(id)){
			toaFinanceBill.setId(Long.valueOf(id));
			ToaFinanceBill toaFinanceBills=toaFinanceBillService.get(toaFinanceBill);
			model.addAttribute("toaFinanceBill", toaFinanceBills);
			String[] ids = toaFinanceBills.getRemarkId().split(",");
			List<ToaFinanceInvoices> invoicesList =new ArrayList<ToaFinanceInvoices>();
			for(int i=0;i<ids.length;i++){
				ToaFinanceInvoices toaFinanceInvoices=new ToaFinanceInvoices();
				toaFinanceInvoices.setId(Long.valueOf(ids[i]));
				invoicesList.add(toaFinanceInvoicesService.get(toaFinanceInvoices));
				}
			model.addAttribute("invoicesList", invoicesList);
			model.addAttribute("Id", id);
		}
		if(companyType.equals("云服务")||companyType.equals("CDN")){
			return "finance/toaFinanceBill/isp_state";
		}else{
			return "finance/toaFinanceBill/idc_state";
		}
	}

	/**
	* 账单审核list
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="stateList.action")
	@ActionLog(operateModelNm="账单信息",operateFuncNm="manage",operateDescribe="对账单信息进行跳转操作")
	public String stateList(Model model,HttpServletRequest request) throws Exception{
		if(SystemSecurityUtils.getUser().getIsLeader()==0){
			String user = SystemSecurityUtils.getUser().getDisplayName();
			model.addAttribute("user",user);
		};
		return "finance/toaFinanceBill/toaFinanceBillstate";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageLead.action")
	@ActionLog(operateModelNm="财务首页表",operateFuncNm="manageLead",operateDescribe="财务首页表进行跳转操作")
	public String manageLead(Model model,HttpServletRequest request) throws Exception{
		return "sys/cuxx";
	}
	
	/**
	 * @description 账单录入请求方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="billInvoices.action")
	@ActionLog(operateModelNm="账单审核",operateFuncNm="billInvoices",operateDescribe="对账单审核跳转")
	public String billInvoices(ToaFinanceBill toaFinanceBill,Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		ToaFinanceBill toaFinanceBills=null;
		if(!StringUtil.isEmpty(id)){
			toaFinanceBill.setId(Long.valueOf(id));
			toaFinanceBills=toaFinanceBillService.get(toaFinanceBill);
			ToaFinanceInvoices financeInvoices = new ToaFinanceInvoices();
			financeInvoices.setCompanyId(Integer.valueOf(toaFinanceBills.getCompanyId()));
			financeInvoices.setDeleteFlag(0);
			financeInvoices.setOtherDeductions(1);
			List<ToaFinanceInvoices> toaFinanceInvoicesMain = toaFinanceBillService.involvesList(financeInvoices);
			String[] ids = toaFinanceBills.getRemarkId().split(",");
			List<ToaFinanceInvoices> invoicesList =new ArrayList<ToaFinanceInvoices>();
			double money = 0.00;
			double addmoney = 0.00;//新增到账
			double scaleMoney = 0.00;//扩容到账
			double stackMoney = 0.00;//存量到账
			double backMoney = 0.00;//回款金额
			double nobackkMoney = 0.00;//未回款金额
			int kaipiao=0;
			DecimalFormat    df   = new DecimalFormat("######0.00");   
			for(int i=0;i<toaFinanceInvoicesMain.size();i++){
				for(int j=0;j<ids.length;j++){
					if(ids[j].equals(toaFinanceInvoicesMain.get(i).getId().toString())){
						invoicesList.add(toaFinanceInvoicesMain.get(i));
						money=toaFinanceInvoicesMain.get(i).getMonthAmount().doubleValue()+money;
						if(toaFinanceInvoicesMain.get(i).getReceivedAccount()!=null&&!toaFinanceInvoicesMain.get(i).getReceivedAccount().equals("")){
						backMoney=toaFinanceInvoicesMain.get(i).getReceivedAccount().doubleValue()+backMoney;
						}
						if(toaFinanceInvoicesMain.get(i).getArrearage()!=null&&!toaFinanceInvoicesMain.get(i).getArrearage().equals("")){
						nobackkMoney=toaFinanceInvoicesMain.get(i).getArrearage().doubleValue()+nobackkMoney;
						}
						if(toaFinanceInvoicesMain.get(i).getInvoicesState()!=null&&toaFinanceInvoicesMain.get(i).getInvoicesState().equals("是")){
							kaipiao=1;
						}
						if(toaFinanceInvoicesMain.get(i).getReceivedAccount()!=null&&!toaFinanceInvoicesMain.get(i).getReceivedAccount().equals("")){
							if(toaFinanceInvoicesMain.get(i).getDepartment().equals("客户维护组")){
								if(toaFinanceInvoicesMain.get(i).getResourceType().equals("存量")){
									stackMoney=toaFinanceInvoicesMain.get(i).getReceivedAccount().doubleValue()+stackMoney;
								}else{
									scaleMoney=toaFinanceInvoicesMain.get(i).getReceivedAccount().doubleValue()+scaleMoney;
								}
							}else{
								addmoney=toaFinanceInvoicesMain.get(i).getReceivedAccount().doubleValue()+addmoney;
							}
						}
						continue;
					}
				}
			}
			model.addAttribute("toaFinanceBill", toaFinanceBills);
			model.addAttribute("money", df.format(money));
			model.addAttribute("addmoney", df.format(addmoney));
			model.addAttribute("scaleMoney", df.format(scaleMoney));
			model.addAttribute("stackMoney", df.format(stackMoney));
			model.addAttribute("backMoney", df.format(backMoney));
			double nobackMoney=money-backMoney;
			if(kaipiao==1&&toaFinanceBills.getState()==0){
				toaFinanceBill.setPrimaryKeys(toaFinanceBills.getId().toString().split(","));
				toaFinanceBill.setOweMoney(new BigDecimal(money));
				toaFinanceBill.setComeMoney(new BigDecimal(backMoney));
				toaFinanceBill.setState(1);
				toaFinanceBillService.state(toaFinanceBill);
			}else if(backMoney!=0.0&&nobackMoney==0.0&&toaFinanceBills.getState()==1){
				toaFinanceBill.setOweMoney(new BigDecimal(nobackMoney));
				toaFinanceBill.setPrimaryKeys(toaFinanceBills.getId().toString().split(","));
				toaFinanceBill.setComeMoney(new BigDecimal(backMoney));
				toaFinanceBill.setState(2);
				toaFinanceBillService.state(toaFinanceBill);
			}else if(nobackMoney!=0.0&&toaFinanceBill.getOweMoney()!=new BigDecimal(nobackMoney)&&toaFinanceBills.getState()==1){
				toaFinanceBill.setPrimaryKeys(toaFinanceBills.getId().toString().split(","));
				toaFinanceBill.setState(1);
				toaFinanceBill.setOweMoney(new  BigDecimal(nobackMoney));
				toaFinanceBill.setComeMoney(new BigDecimal(backMoney));
				toaFinanceBillService.state(toaFinanceBill);
			}
			model.addAttribute("nobackkMoney", df.format(nobackMoney));
			model.addAttribute("invoicesList", invoicesList);
			model.addAttribute("Id", id);
		}
		String service = toaFinanceBills.getServiceType();
		if(service.equals("云服务")||service.equals("CDN")){
			return "finance/toaFinanceBill/isp_invoices";
		}else{
			return "finance/toaFinanceBill/idc_invoices";
		}
	}
	
	/**
	* 账单综合查询跳转
	* @return String 账单通过跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="tongguoManage.action")
	@ActionLog(operateModelNm="账单表",operateFuncNm="manage",operateDescribe="账单通过表进行跳转操作")
	public String tongguoManage(Model model,HttpServletRequest request) throws Exception{
		if(SystemSecurityUtils.getUser().getIsLeader()==0){
			String user = SystemSecurityUtils.getUser().getDisplayName();
			model.addAttribute("user",user);
		};
		return "finance/toaFinanceBill/tongguoBill";
	}
	
	/**
	* 账单回退list方法
	* @return String 账单待审核跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="jujueManage.action")
	@ActionLog(operateModelNm="账单表",operateFuncNm="manage",operateDescribe="账单通过表进行跳转操作")
	public String jujueManage(Model model,HttpServletRequest request) throws Exception{
		return "finance/toaFinanceBill/jujueBill";
	}
	
	/**
	* 欠费提醒方法
	* @return String 账单待审核跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="oweMoney.action")
	@ActionLog(operateModelNm="欠费提醒",operateFuncNm="oweMoney",operateDescribe="欠费提醒进行跳转操作")
	public String oweMoney(Model model,HttpServletRequest request) throws Exception{
		return "finance/toaFinanceBill/oweMoneyBill";
	}
	
	/**
	* @description 跳转到视图测试页面
	* @return String 跳转的路径
	* @throws Exception
	* @version  2014-12-23 
	*/
	@RequestMapping(value="getManageMonthSumBill.action")
	public String getManageMonthSumBill(Model model,HttpServletRequest request) throws Exception{

		return "finance/toaFinanceBill/toaFinanceBillView";
	}
	
	/**
	* @description 取一年中  每月总实际回款金额、应收金额、欠费金额
	* @return Map 数据集合
	* @throws Exception
	* @version  2017-3-16 
	*/
	@RequestMapping(value="getMonthSumBill.action")
	@ResponseBody
	@ActionLog(operateModelNm="统计视图",operateFuncNm="getMonthSumBill",operateDescribe="取当年中每月总实际回款金额、应收金额、欠费金额")
	public Map<String, Object> getMonthSumBill(ToaFinanceBill toaFinanceBill,HttpServletRequest request) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		if(toaFinanceBill.getBillDate()==null){
			toaFinanceBill.setBillDate(Calendar.getInstance().getTime());
		}
		if("".equals(toaFinanceBill.getCompanyName())){
			toaFinanceBill.setCompanyName(null);
		}
		if("".equals(toaFinanceBill.getSale())){
			toaFinanceBill.setSale(null);
		}
		if("".equals(toaFinanceBill.getServiceType())){
			toaFinanceBill.setServiceType(null);
		}
		List<ToaFinanceBill> billList =  toaFinanceBillService.queryByMonth(toaFinanceBill);
		result.put("billList", billList);
		//取当年中每月总实际回款金额、应收金额、欠费金额
		result.putAll(this.sumBillYear(toaFinanceBill, request));
		//取当年中每个机房总实际应收金额占比
		result.putAll(this.sumBillYearByRoom(toaFinanceBill, request));
		return result;
	}

	/**
	 * 按照年份进行统计
	 * */
	@RequestMapping(value="sumBillYear.action")
	@ResponseBody
	@ActionLog(operateModelNm="统计视图",operateFuncNm="getMonthSumBill",operateDescribe="取当年中每月总实际回款金额、应收金额、欠费金额")
	public Map<String, Object> sumBillYear(ToaFinanceBill toaFinanceBill,HttpServletRequest request) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		if(toaFinanceBill.getBillDate()==null){
			toaFinanceBill.setBillDate(Calendar.getInstance().getTime());
		}
		if(StringUtil.isEmpty(toaFinanceBill.getCompanyName())){
			toaFinanceBill.setCompanyName(null);
		}
		if(StringUtil.isEmpty(toaFinanceBill.getSale())){
			toaFinanceBill.setSale(null);
		}
		if(StringUtil.isEmpty(toaFinanceBill.getServiceType())){
			toaFinanceBill.setServiceType(null);
		}
		List<ToaFinanceBill> billYearList =  toaFinanceBillService.queryByYear(toaFinanceBill);
		result.put("billYearList", billYearList);
		return result;
	}
	
	/**
	 * 按照年份进行统计各个机房应收金额占比
	 * */
	@RequestMapping(value="sumBillYearByRoom.action")
	@ResponseBody
	@ActionLog(operateModelNm="统计视图",operateFuncNm="sumBillYearByRoom",operateDescribe="取当年中每个机房总实际应收金额占比")
	public Map<String, Object> sumBillYearByRoom(ToaFinanceBill toaFinanceBill,HttpServletRequest request) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		if(toaFinanceBill.getBillDate()==null){
			toaFinanceBill.setBillDate(Calendar.getInstance().getTime());
		}
		if(StringUtil.isEmpty(toaFinanceBill.getCompanyName())){
			toaFinanceBill.setCompanyName(null);
		}
		if(StringUtil.isEmpty(toaFinanceBill.getSale())){
			toaFinanceBill.setSale(null);
		}
		if(StringUtil.isEmpty(toaFinanceBill.getServiceType())){
			toaFinanceBill.setServiceType(null);
		}
		//标识使用按机房分组取数据，得到当年中每个机房总实际应收金额占比
		toaFinanceBill.setRoomFlg("true");
		List<ToaFinanceBill> billYearListByRoom =  toaFinanceBillService.queryByYear(toaFinanceBill);
		result.put("billYearListByRoom", billYearListByRoom);
		return result;
	}
	
	/**
	* @description 门户便签组件数据
	* @return String 跳转的路径
	* @throws Exception
	* @version  2014-12-23 
	*/
	@RequestMapping(value="boxPortalData.action")
	public String boxPortalData(Model model,HttpServletRequest request) throws Exception{
		//门户引用类固定代码
		model = PortalUtils.getPortalParameter(model, request);
		return "finance/toaFinanceBill/oweMoneyBillPortal";
	}
	
	/**
	* @description 门户便签组件数据
	* @return Map 数据集合
	* @throws Exception
	* @version  2014-12-23 
	*/
	@RequestMapping(value="getBoxData.action")
	@ResponseBody
	public Map<String, Object> getBoxData(Long columnId,String funViewType,int dataRows) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		ToaFinanceBill note = new ToaFinanceBill();
		note.setDeleteFlag(0);
		note.setState(3);
		//note.setOweMoney(new );
		note.addOrderByFieldDesc("t.MODIFY_DATE");
		List<ToaFinanceBill> noteList =  toaFinanceBillService.queryAll(note);
		result.put("notelist", noteList);
		result.put("dataRows",dataRows);
		return result;
	}
	
}
