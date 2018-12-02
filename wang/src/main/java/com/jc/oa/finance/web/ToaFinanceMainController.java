package com.jc.oa.finance.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.ExcuteExcelUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.finance.domain.Invoices;
import com.jc.oa.finance.domain.ToaFinanceInvoices;
import com.jc.oa.finance.domain.ToaFinanceMain;
import com.jc.oa.finance.service.IToaFinanceInvoicesService;
import com.jc.oa.finance.service.IToaFinanceMainService;
/**
 * @author mrb
 * @version 收入主体表
*/
@Controller
@RequestMapping(value="/finance/toaFinanceMain")
public class ToaFinanceMainController extends BaseController{
	
	@Autowired 
	private IToaFinanceMainService toaFinanceMainService; 
	
	@Autowired
	private IToaFinanceInvoicesService toaFinanceInvoicesService;
	
	public ToaFinanceMainController(){
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
	@ActionLog(operateModelNm="收入主体表",operateFuncNm="save",operateDescribe="对收入主体表进行发起操作")
	public Map<String,Object> save(ToaFinanceMain toaFinanceMain,Invoices invoices ,BindingResult result,
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
			toaFinanceMainService.saveMainInvoices(toaFinanceMain, invoices);
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
	@ActionLog(operateModelNm="收入主体表",operateFuncNm="update",operateDescribe="对收入主体表进行更新操作")
	public Map<String, Object> update(ToaFinanceMain toaFinanceMain,Invoices invoices , BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaFinanceMainService.updateMainInvoices(toaFinanceMain,invoices);
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
	@ActionLog(operateModelNm="收入主体表",operateFuncNm="get",operateDescribe="对收入主体表进行单条查询操作")
	public ToaFinanceMain get(ToaFinanceMain toaFinanceMain,HttpServletRequest request) throws Exception{
		return toaFinanceMainService.get(toaFinanceMain);
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
	@ActionLog(operateModelNm="收入主体表",operateFuncNm="get",operateDescribe="收入主体表分页查询")
	public PageManager manageList(ToaFinanceMain toaFinanceMain,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaFinanceMain.getOrderBy())) {
			toaFinanceMain.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaFinanceMainService.query(toaFinanceMain, page);
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
    public void exportExcel(ToaFinanceMain toaFinanceMain,HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchFieldException, CustomException {
        
        //查询数据
    	List<ToaFinanceMain>  page_= toaFinanceMainService.queryAll(toaFinanceMain);
        
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
        //指定输出类中的哪些属性
        //定义list,添加标题内容
        List<String> lstProp = new ArrayList<String>();
        lstProp.add("orderDate");
        lstProp.add("companyName");
        lstProp.add("companyType");
        lstProp.add("resourceType");
        lstProp.add("department");
        lstProp.add("sale");
        lstProp.add("maintenanSale");
        lstProp.add("roomName");
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
        ExcuteExcelUtil.exportExcel(lstHead, lstProp,page_, response);  //调用导出功能
        
    }
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="收入主体表",operateFuncNm="manage",operateDescribe="对收入主体表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "finance/toaFinanceMain/toaFinanceMain";
	}
	
	/**
	* 客户服务跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="managekhfw.action")
	@ActionLog(operateModelNm="收入主体表",operateFuncNm="managekhfw",operateDescribe="对收入主体表客户服务查询进行跳转操作")
	public String managekhfw(Model model,HttpServletRequest request) throws Exception{
		if(SystemSecurityUtils.getUser().getIsLeader()==0){
			String user = SystemSecurityUtils.getUser().getDisplayName();
			model.addAttribute("user",user);
		};
		return "finance/toaFinanceMain/khfwFinanceMain";
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
	@ActionLog(operateModelNm="收入主体表表",operateFuncNm="deleteByIds",operateDescribe="对收入主体表表进行删除")
	public  Map<String, Object> deleteByIds(ToaFinanceMain toaFinanceMain,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaFinanceMain.setPrimaryKeys(ids.split(","));	
		if(toaFinanceMainService.deleteByIds(toaFinanceMain) != 0){
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
	@ActionLog(operateModelNm="收入主体表表",operateFuncNm="loadForm",operateDescribe="对收入主体表表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
			toaFinanceInvoices.setDeleteFlag(0);
			toaFinanceInvoices.setMainId(Integer.valueOf(id));
			List<ToaFinanceInvoices> invoices = toaFinanceInvoicesService.queryAll(toaFinanceInvoices);
			model.addAttribute("dicNodeInfo", invoices);
			model.addAttribute("list", invoices.size());
			model.addAttribute("Id", id);
		}else{
			//收入编号
			INumber number = new Number();
			String applyNum = number.getNumber("inCome", 1,2, null);
			applyNum = applyNum.substring(1, applyNum.length());
			model.addAttribute("applyNum",applyNum);
			model.addAttribute("dates", DateUtils.getSysDate());
		}
		return "finance/toaFinanceMain/toaFinanceMainForm";
	}
	
	/**
	 * @description 新增与修改方法跳转页面
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormRead.action")
	@ActionLog(operateModelNm="收入主体表表",operateFuncNm="loadForm",operateDescribe="对收入主体表查看表跳转")
	public String loadFormRead(ToaFinanceMain toaFinanceMain,Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			ToaFinanceInvoices toaFinanceInvoices = new ToaFinanceInvoices();
			toaFinanceInvoices.setDeleteFlag(0);
			toaFinanceInvoices.setMainId(Integer.valueOf(id));
			List<ToaFinanceInvoices> invoices = toaFinanceInvoicesService.queryAll(toaFinanceInvoices);
			toaFinanceMain.setId(Long.valueOf(id));
			model.addAttribute("dicNodeInfo", invoices);
			model.addAttribute("financeMain", toaFinanceMainService.get(toaFinanceMain));
			model.addAttribute("Id", id);
		}
		return "finance/toaFinanceMain/readFinanceMainForm";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage2.action")
	@ActionLog(operateModelNm="计费信息表",operateFuncNm="manage2",operateDescribe="计费信息表进行跳转操作")
	public String manage2(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		ToaFinanceMain toaFinanceMain = new ToaFinanceMain();
		toaFinanceMain.setCompanyId(id);
		List<ToaFinanceMain> toaFinanceMains = toaFinanceMainService.queryAll(toaFinanceMain);
		model.addAttribute("toaFinanceMains", toaFinanceMains);
		model.addAttribute("oldId", id);
		return "finance/toaFinanceMain/viewFinanceMain";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageResource.action")
	@ActionLog(operateModelNm="计费信息表",operateFuncNm="manageResource",operateDescribe="计费信息表进行跳转操作")
	public String manageResource(Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		ToaFinanceMain toaFinanceMain = new ToaFinanceMain();
		toaFinanceMain.setCompanyId(id);
		List<ToaFinanceMain> toaFinanceMains = toaFinanceMainService.queryAll(toaFinanceMain);
		model.addAttribute("toaFinanceMains", toaFinanceMains);
		model.addAttribute("oldId", id);
		return "finance/toaFinanceMain/resourcePrice";
	}
}
