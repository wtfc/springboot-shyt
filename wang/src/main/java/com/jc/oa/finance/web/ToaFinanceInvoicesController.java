package com.jc.oa.finance.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.ExcuteExcelUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
import com.jc.oa.finance.service.IToaFinanceInvoicesService;
/**
 * @author mrb
 * @version 月收入
*/
@Controller
@RequestMapping(value="/finance/toaFinanceInvoices")
public class ToaFinanceInvoicesController extends BaseController{
	
	@Autowired 
	private IToaFinanceInvoicesService toaFinanceInvoicesService; 
	
	public ToaFinanceInvoicesController(){
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
	@ActionLog(operateModelNm="月收入",operateFuncNm="save",operateDescribe="对月收入进行发起操作")
	public Map<String,Object> save(ToaFinanceInvoices toaFinanceInvoices,BindingResult result,
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
			toaFinanceInvoicesService.save(toaFinanceInvoices);
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
	@ActionLog(operateModelNm="月收入",operateFuncNm="update",operateDescribe="对月收入进行更新操作")
	public Map<String, Object> update(ToaFinanceInvoices toaFinanceInvoices, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaFinanceInvoicesService.update(toaFinanceInvoices);
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
	@ActionLog(operateModelNm="月收入",operateFuncNm="get",operateDescribe="对月收入进行单条查询操作")
	public ToaFinanceInvoices get(ToaFinanceInvoices toaFinanceInvoices,HttpServletRequest request) throws Exception{
		return toaFinanceInvoicesService.get(toaFinanceInvoices);
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
	@ActionLog(operateModelNm="月收入",operateFuncNm="get",operateDescribe="月收入分页查询")
	public PageManager manageList(ToaFinanceInvoices toaFinanceInvoices,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaFinanceInvoices.getOrderBy())) {
			toaFinanceInvoices.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaFinanceInvoicesService.query(toaFinanceInvoices, page);
		return page_; 
	}
	
	
	/**
	 * 分页查询方法(收入主体与月收入一一对应查询)
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@RequestMapping(value="mainInvoicesList.action")
	@ResponseBody
	@ActionLog(operateModelNm="主体月收入",operateFuncNm="get",operateDescribe="主体月收入分页查询")
	public PageManager mainInvoicesList(ToaFinanceInvoices toaFinanceInvoices,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		
		//默认排序
		if(StringUtil.isEmpty(toaFinanceInvoices.getOrderBy())) {
			toaFinanceInvoices.addOrderByFieldDesc("t.CREATE_DATE");
		}
		PageManager page_ = toaFinanceInvoicesService.mainInvoices(toaFinanceInvoices, page);
		return page_; 
	}
	
	
	/**
     * @description 导出Excel
     * @param User user 实体类
     * @param PageManager page 分页实体类
     * @throws Exception
     * @author 都业广
     * @version 2014-05-08
     * @throws IOException 
     * @throws NoSuchFieldException 
	 * @throws CustomException 
     */
    @RequestMapping(value = "exportExcel.action")
    public void exportExcel(ToaFinanceInvoices toaFinanceInvoices,HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchFieldException, CustomException {
        
        //查询数据
    	List<ToaFinanceInvoices>  page_= toaFinanceInvoicesService.getMain(toaFinanceInvoices);
        
        //使用模板时情况
//        ExcuteExcelUtil.setTemplatePath("E://template.xls");  //模板位置,不进行设置的话,默认为C盘根目录
//        ExcuteExcelUtil.exportExcel(page_.getData(), response);  //调用导出功能
        
        //使用自定义标题的情况
        //定义list,添加标题内容
        List<String> lstHead = new ArrayList<String>();
        lstHead.add("变动 日期");
        lstHead.add("客户名称");
        lstHead.add("业务类型");
        lstHead.add("资源变动类型");
        lstHead.add("部 门");
        lstHead.add("客户经理");
        lstHead.add("维护经理");
        lstHead.add("机房");
        lstHead.add("付费方式");
        lstHead.add("计费起始时间");
        lstHead.add("计费终止时间");
        lstHead.add("专线类型");
        lstHead.add("单天计算方式");
        lstHead.add("超流量取值方式");
        lstHead.add("合同金额");
        lstHead.add("绩效合同额");
        lstHead.add("存量合同额");
        lstHead.add("预存金额");
        lstHead.add("折扣");
        lstHead.add("保底带宽");
        lstHead.add("单价");
        lstHead.add("端口带宽");
        lstHead.add("单价");
        lstHead.add("超流量带宽");
        lstHead.add("单价");
        lstHead.add("机柜");
        lstHead.add("单价");
        lstHead.add("服务器");
        lstHead.add("单价");
        lstHead.add("IP");
        lstHead.add("单价");
        lstHead.add("交换机");
        lstHead.add("单价");
        lstHead.add("链路");
        lstHead.add("单价");
        lstHead.add("端口");
        lstHead.add("单价");
        lstHead.add("内存");
        lstHead.add("单价");
        lstHead.add("CPU");
        lstHead.add("单价");
        lstHead.add("硬盘");
        lstHead.add("单价");
        lstHead.add("应收 月份");
        lstHead.add("应收金额");
        lstHead.add("发票金额");
        lstHead.add("未开票金额");
        lstHead.add("回款金额");
        lstHead.add("未回款金额");
        lstHead.add("代理费");
        //指定输出类中的哪些属性
        //定义list,添加标题内容
        List<String> lstProp = new ArrayList<String>();
        lstProp.add("orderDate");
        lstProp.add("companyName");
        lstProp.add("companyTypeValue");
        lstProp.add("resourceType");
        lstProp.add("department");
        lstProp.add("sale");
        lstProp.add("maintenanSale");
        lstProp.add("roomNameValue");
        lstProp.add("payType");
        lstProp.add("cycleStart");
        lstProp.add("cycleEnd");
        lstProp.add("lineCategory");
        lstProp.add("singleCharg");
        lstProp.add("overflowCategory");
        lstProp.add("cardAmount");
        lstProp.add("performanceAmount");
        lstProp.add("cardStockAmount");
        lstProp.add("prestoreAmount");
        lstProp.add("discount");
        lstProp.add("minBandwidth");   
		lstProp.add("minBandwidthPrice");   
		lstProp.add("portBandwidth");   
		lstProp.add("portBandwidthPrice");   
		lstProp.add("overflowBandwidth");   
		lstProp.add("overflowBandwidthPrice");   
		lstProp.add("cabinetNum");   
		lstProp.add("cabinetPrice");   
		lstProp.add("serviceNum");   
		lstProp.add("servicePrice");   
		lstProp.add("ipNum");   
		lstProp.add("ipPrice");   
		lstProp.add("switchNum");   
		lstProp.add("switchPrice");   
		lstProp.add("odfNum");   
		lstProp.add("odfPrice");   
		lstProp.add("portNum");   
		lstProp.add("portPrice");   
		lstProp.add("memoryNum");   
        lstProp.add("memoryPrice");   
    	lstProp.add("cpuNum");   
    	lstProp.add("cpuPrice");   
    	lstProp.add("diskNum");   
    	lstProp.add("diskPrice");
        lstProp.add("invoicesMonth");
        lstProp.add("monthAmount");
        lstProp.add("invoicesAccount");
        lstProp.add("noinvoicesAccount");
        lstProp.add("receivedAccount");
        lstProp.add("arrearage");
        lstProp.add("commision");
        ExcuteExcelUtil.exportExcel(lstHead, lstProp,page_, response);  //调用导出功能
    }
	
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="mainInvoices.action")
	@ActionLog(operateModelNm="月收入",operateFuncNm="manage",operateDescribe="对月收入进行跳转操作")
	public String mainInvoices(Model model,HttpServletRequest request) throws Exception{
		return "finance/toaFinanceInvoices/toaFinanceMainInvoices";
	}
	
	/**
	* 客户服务部跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="khfwInvoices.action")
	@ActionLog(operateModelNm="月收入",operateFuncNm="manage",operateDescribe="对月收入进行跳转操作")
	public String khfwInvoices(Model model,HttpServletRequest request) throws Exception{
		int leader = SystemSecurityUtils.getUser().getIsLeader();
		if(leader!=1){
			String user = SystemSecurityUtils.getUser().getDisplayName();
			model.addAttribute("user", user);
		}
		return "finance/toaFinanceInvoices/khfwInvoices";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="月收入",operateFuncNm="manage",operateDescribe="对月收入进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "finance/toaFinanceInvoices/toaFinanceInvoices";
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
	@ActionLog(operateModelNm="月收入表",operateFuncNm="deleteByIds",operateDescribe="对月收入表进行删除")
	public  Map<String, Object> deleteByIds(ToaFinanceInvoices toaFinanceInvoices,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaFinanceInvoices.setPrimaryKeys(ids.split(","));	
		if(toaFinanceInvoicesService.deleteByIds(toaFinanceInvoices) != 0){
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
	@ActionLog(operateModelNm="月收入表",operateFuncNm="loadForm",operateDescribe="对月收入表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "finance/toaFinanceInvoices/toaFinanceInvoicesForm";
	}
	
	/**
	 * 删除方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="createBill.action")
	@ResponseBody
	@ActionLog(operateModelNm="月收入表",operateFuncNm="createBill",operateDescribe="对月收入账单生成")
	public  Map<String, Object> createBill(ToaFinanceInvoices toaFinanceInvoices,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaFinanceInvoices.setPrimaryKeys(ids.split(","));	
		if(toaFinanceInvoicesService.createBill(toaFinanceInvoices,ids) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_141"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_142"));
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
	@RequestMapping(value="invocesLoadForm.action")
	@ActionLog(operateModelNm="月收入账单表",operateFuncNm="loadForm",operateDescribe="对月收入表跳转")
	public String invocesLoadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "finance/toaFinanceBill/module/invoicesFormModule";
	}
	
	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="invocesCndLoadForm.action")
	@ActionLog(operateModelNm="月收入账单表",operateFuncNm="invocesCndLoadForm",operateDescribe="对月收入表跳转")
	public String invocesCndLoadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
		return "finance/toaFinanceBill/module/invoicesCdnFormModule";
	}

}
