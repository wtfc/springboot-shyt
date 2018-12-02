package com.jc.oa.contract.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.number.service.Number;
import com.jc.system.portal.util.PortalUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.contract.domain.ToaContractIncontract;
import com.jc.oa.contract.domain.ToaContractResource;
import com.jc.oa.contract.service.IToaContractIncontractService;
import com.jc.oa.contract.service.IToaContractResourceService;
import com.jc.oa.shyt.domain.Complain;
import com.jc.oa.shyt.domain.Customer;
import com.jc.oa.shyt.domain.OutContract;
import com.jc.oa.shyt.service.ICustomerService;
/**
 * @author mrb
 * @version 收款方合同
*/
@Controller
@RequestMapping(value="/contract/toaContractIncontract")
public class ToaContractIncontractController extends BaseController{
	
	@Autowired 
	private IToaContractIncontractService toaContractIncontractService; 
	
	@Autowired 
	private IToaContractResourceService toaContractResourceService;
	
	@Autowired
	private ICustomerService customerService;
	
	public ToaContractIncontractController(){
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
	@ActionLog(operateModelNm="收款方合同",operateFuncNm="save",operateDescribe="对收款方合同进行发起操作")
	public Map<String,Object> save(ToaContractIncontract toaContractIncontract,ToaContractResource toaContractResource,BindingResult result,
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
			toaContractIncontractService.saveResource(toaContractIncontract);
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
	@ActionLog(operateModelNm="收款方合同",operateFuncNm="update",operateDescribe="对收款方合同进行更新操作")
	public Map<String, Object> update(ToaContractIncontract toaContractIncontract,ToaContractResource toaContractResource, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = toaContractIncontractService.updateResource(toaContractIncontract);
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
	@ActionLog(operateModelNm="收款方合同",operateFuncNm="get",operateDescribe="对收款方合同进行单条查询操作")
	public ToaContractIncontract get(ToaContractIncontract toaContractIncontract,HttpServletRequest request) throws Exception{
		return toaContractIncontractService.get(toaContractIncontract);
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
	@ActionLog(operateModelNm="收款方合同",operateFuncNm="get",operateDescribe="收款方合同分页查询")
	public PageManager manageList(ToaContractIncontract toaContractIncontract,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(toaContractIncontract.getOrderBy())) {
			toaContractIncontract.addOrderByFieldDesc("t.LEARD_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = toaContractIncontractService.query(toaContractIncontract, page);
		return page_; 
	}
	
	/**
     * @description 导出Excel
     * @param User user 实体类
     * @param PageManager page 分页实体类
     * @throws Exception
     * @version 2014-05-08
     * @throws IOException 
     * @throws NoSuchFieldException 
	 * @throws CustomException 
     */
    @RequestMapping(value = "exportExcel.action")
    public void exportExcel(ToaContractIncontract toaContractIncontract,HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchFieldException, CustomException {
        
        //查询数据
    	List<ToaContractIncontract>  page_= toaContractIncontractService.queryAll(toaContractIncontract);
        
        //使用模板时情况
//        ExcuteExcelUtil.setTemplatePath("E://template.xls");  //模板位置,不进行设置的话,默认为C盘根目录
//        ExcuteExcelUtil.exportExcel(page_.getData(), response);  //调用导出功能
        
        //使用自定义标题的情况
        //定义list,添加标题内容
        List<String> lstHead = new ArrayList<String>();
        lstHead.add("公司名称");
        lstHead.add("合同编码");
        lstHead.add("发起人");
        lstHead.add("发起日期");
        lstHead.add("协议类型");
        lstHead.add("合同类别");
        lstHead.add("合同金额");
        lstHead.add("盖章");
        lstHead.add("归档时间");
        lstHead.add("合同起始日期");
        lstHead.add("合同终止日期");
        //指定输出类中的哪些属性
        //定义list,添加标题内容
        List<String> lstProp = new ArrayList<String>();
        lstProp.add("companyName");
        lstProp.add("contractNumber");
        lstProp.add("leard");
        lstProp.add("leardDate");
        lstProp.add("agreement");
        lstProp.add("contractStatus");
        lstProp.add("contractMoney");
        lstProp.add("seal");
        lstProp.add("place");
        lstProp.add("startDate");
        lstProp.add("endDate");
        ExcuteExcelUtil.exportExcel(lstHead, lstProp,page_, response);  //调用导出功能
        
    }
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="收款方合同",operateFuncNm="manage",operateDescribe="对收款方合同进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "contract/toaContractIncontract/toaContractIncontract";
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
	@ActionLog(operateModelNm="收款方合同表",operateFuncNm="deleteByIds",operateDescribe="对收款方合同表进行删除")
	public  Map<String, Object> deleteByIds(ToaContractIncontract toaContractIncontract,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		toaContractIncontract.setPrimaryKeys(ids.split(","));	
		if(toaContractIncontractService.deleteByIds(toaContractIncontract) != 0){
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
	@ActionLog(operateModelNm="收款方合同表",operateFuncNm="loadForm",operateDescribe="对收款方合同表跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("Id", id);
		}
//			else{
//			//合同编号
//			INumber number = new Number();
//			String applyNum = number.getNumber("incontract", 1,2, null);
//			applyNum = applyNum.substring(1, applyNum.length());
//			model.addAttribute("applyNum",applyNum);
//		}
		return "contract/toaContractIncontract/toaContractIncontractForm";
	}

	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manageLead.action")
	@ActionLog(operateModelNm="收款合同表",operateFuncNm="manage",operateDescribe="收款合同表进行跳转操作")
	public String manageLead(Model model,HttpServletRequest request) throws Exception{
		return "sys/gdgl";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="warContract.action")
	@ActionLog(operateModelNm="收款方合同",operateFuncNm="manage",operateDescribe="对收款方合同提醒进行跳转操作")
	public String warContract(Model model,HttpServletRequest request) throws Exception{
		if(SystemSecurityUtils.getUser().getDeptId()!=1007){
			String userName = SystemSecurityUtils.getUser().getDisplayName();
			model.addAttribute("userName", userName);
		}
		return "contract/toaContractIncontract/warContractIncontract";
	}
	
	/**
	* 综合查看跳转
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage2.action")
	@ActionLog(operateModelNm="合同信息表",operateFuncNm="manage",operateDescribe="对合同信息表进行跳转操作")
	public String manage2(Customer customer,Model model,HttpServletRequest request) throws Exception{
		String id = request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			customer.setId(Long.valueOf(id));
			ToaContractIncontract toaContractIncontract =new ToaContractIncontract();
			Customer customers = customerService.get(customer);
			toaContractIncontract.setCompanyName(customers.getCompanyName());
			toaContractIncontract.setDeleteFlag(0);
			List<ToaContractIncontract> inContract = toaContractIncontractService.queryAll(toaContractIncontract);
			model.addAttribute("inContract", inContract);
		}
		return "contract/toaContractIncontract/viewContractIncontract";
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
		return "contract/toaContractIncontract/IncontractPortal";
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
		ToaContractIncontract note = new ToaContractIncontract();
		
		Calendar c = Calendar.getInstance();
		Calendar d = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		d.add(Calendar.MONTH, 1);
		note.setEndDateBegin(c.getTime());
		note.setEndDateEnd(d.getTime());
		note.setDeleteFlag(0);
		note.setDerive("否");
		note.addOrderByFieldDesc("t.MODIFY_DATE");
		if(SystemSecurityUtils.getUser().getDeptId()!=1007){
			note.setLeard(SystemSecurityUtils.getUser().getDisplayName());
		}
		List<ToaContractIncontract> noteList =  toaContractIncontractService.queryAll(note);
		result.put("notelist", noteList);
		result.put("dataRows",dataRows);
		return result;
	}
	
	/**
	* 客户服务部查看跳转
	* @return String 跳转的路径
	* @throws Exception
	* @author
	*/
	@RequestMapping(value="manage3.action")
	@ActionLog(operateModelNm="合同信息表",operateFuncNm="manage",operateDescribe="对合同信息表进行跳转操作")
	public String manage3(Model model,HttpServletRequest request) throws Exception{
//		String user = SystemSecurityUtils.getUser().getDisplayName();
//		model.addAttribute("user", user);
		return "contract/toaContractIncontract/khfwIncontract";
	}
}
