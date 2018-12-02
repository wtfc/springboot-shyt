package com.jc.oa.shyt.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.jc.oa.finance.domain.ToaFinanceMain;
import com.jc.oa.shyt.domain.OutContract;
import com.jc.oa.shyt.service.IOutContractService;
import com.jc.system.CustomException;
import com.jc.system.common.util.ExcuteExcelUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.util.ActionLog;
@Controller
@RequestMapping(value="/shyt/outContract")
public class OutContractController extends BaseController{
	
	@Autowired
	private IOutContractService outContractService;

	public OutContractController(){
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
	@ActionLog(operateModelNm="客户合同信息表（付款方）",operateFuncNm="save",operateDescribe="对客户合同信息表（付款方）进行发起操作")
	public Map<String,Object> save(OutContract outContract,BindingResult result,
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
			outContractService.save(outContract);
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
	@ActionLog(operateModelNm="客户合同信息表（付款方）",operateFuncNm="update",operateDescribe="对客户合同信息表（付款方）进行更新操作")
	public Map<String, Object> update(OutContract outContract, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		Integer flag = outContractService.update(outContract);
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
	@ActionLog(operateModelNm="客户合同信息表（付款方）",operateFuncNm="get",operateDescribe="对客户合同信息表（付款方）进行单条查询操作")
	public OutContract get(OutContract outContract,HttpServletRequest request) throws Exception{
		return outContractService.get(outContract);
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
	@ActionLog(operateModelNm="客户合同信息表（付款方）",operateFuncNm="get",operateDescribe="客户合同信息表（付款方）分页查询")
	public PageManager manageList(OutContract outContract,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		
		//默认排序
		if(StringUtil.isEmpty(outContract.getOrderBy())) {
			outContract.addOrderByFieldDesc("t.LEARD_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			equipment.setContact(deptName);
//		}
		PageManager page_ = outContractService.query(outContract, page);
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
    public void exportExcel(OutContract outContract,HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchFieldException, CustomException {
        
        //查询数据
    	List<OutContract>  page_= outContractService.queryAll(outContract);
        
        //使用模板时情况
//        ExcuteExcelUtil.setTemplatePath("E://template.xls");  //模板位置,不进行设置的话,默认为C盘根目录
//        ExcuteExcelUtil.exportExcel(page_.getData(), response);  //调用导出功能
        
        //使用自定义标题的情况
        //定义list,添加标题内容
        List<String> lstHead = new ArrayList<String>();
        lstHead.add("甲方公司名称");
        lstHead.add("乙方公司名称");
        lstHead.add("合同编码");
        lstHead.add("发起人");
        lstHead.add("发起日期");
        lstHead.add("合同类型");
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
        lstProp.add("customer");
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
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="客户合同信息表（付款方）",operateFuncNm="manage",operateDescribe="对客户合同信息表（付款方）进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/outContract/outContract";
	}
	
	/**
	* 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manage2.action")
	@ActionLog(operateModelNm="客户合同信息表（付款方）",operateFuncNm="manage",operateDescribe="对客户合同信息表（付款方）进行跳转操作")
	public String manage2(Model model,HttpServletRequest request) throws Exception{
		
		return "shyt/outContract/outContractView";
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
	@ActionLog(operateModelNm="客户合同信息表（付款方）",operateFuncNm="deleteByIds",operateDescribe="对客户合同信息表（付款方）进行删除")
	public  Map<String, Object> deleteByIds(OutContract outContract,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		outContract.setPrimaryKeys(ids.split(","));	
		if(outContractService.deleteByIds(outContract) != 0){
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
	@ActionLog(operateModelNm="客户合同信息表（付款方）",operateFuncNm="loadForm",operateDescribe="对客户合同信息表（付款方）跳转")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id= request.getParameter("id");
		if(!StringUtil.isEmpty(id)){
			model.addAttribute("oldId", id);
		}else{
			String token = getToken(request);
			map.put(GlobalContext.SESSION_TOKEN, token);
			model.addAttribute("data", map);
			//合同编号
			INumber number = new Number();
			String applyNum = number.getNumber("outContract", 1, 2, null);
			applyNum = applyNum.substring(1, applyNum.length());
			model.addAttribute("applyNum",applyNum);
		}
		return "shyt/outContract/outContractFrom";
	}
}
