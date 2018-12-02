package com.jc.shjfgl.machine.web;

import java.io.UnsupportedEncodingException;
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
import com.jc.shjfgl.machine.domain.View;
import com.jc.shjfgl.machine.service.IViewService;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.ActionLog;

@Controller
@RequestMapping(value="/machine/view")
public class ViewController extends BaseController{

	
	@Autowired
	private IViewService viewService;
	
	public ViewController(){};
	
	/**
	 * 保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="save",operateDescribe="对机房平面表进行发起操作")
	public Map<String,Object> save(View view,BindingResult result,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
//		resultMap = validateToken(request);
//		if (resultMap.size() > 0) {
//			return resultMap;
//		}
		if(!"false".equals(resultMap.get("success"))){
			if(view.getId()!=null){
				view.setId(null);
			}
			viewService.save(view);
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
	* @version  2014-12-08 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="update",operateDescribe="对机房平面表进行更新操作")
	public Map<String, Object> update(View view, BindingResult result,
			HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		
		Integer flag = viewService.update(view);
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
	 * @version  2014-12-08
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="get",operateDescribe="对机房平面表进行单条查询操作")
	public View get(View view,HttpServletRequest request) throws Exception{
		return viewService.get(view);
	}

	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-08 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="get",operateDescribe="机房平面表分页查询")
	public PageManager manageList(View view,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException{
		//默认排序
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			View.setContact(deptName);
//		}
		PageManager page_ = viewService.query(view, page);
		return page_; 
	}
/**
	 * 删除方法
	 * @param TimeSet timeSet 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-12-08
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="deleteByIds",operateDescribe="对机房平面表进行删除")
	public  Map<String, Object> deleteByIds(View view,String ids, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		view.setPrimaryKeys(ids.split(","));	
		if(viewService.deleteByIds(view) != 0){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
		} else {
			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_SYS_006"));
		}
		return resultMap;
	}

	/**
	 * @description 弹出对话框方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadForm.action")
	public String loadForm(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String token = getToken(request);
		map.put(GlobalContext.SESSION_TOKEN, token);
		model.addAttribute("data", map);
		return "shjfgl/view/module/viewFormModule"; 
	}
	//TODO 
	/**
	 * @description 机房平面弹出对话框方法
	 * @return String form对话框所在位置
	 * @throws Exception
	 * @author
	 * @version  2015-08-03
	 */
	@RequestMapping(value="loadFormView.action")
	public String loadFormView(Model model,HttpServletRequest request) throws Exception{
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		model.addAttribute("name", name);
		model.addAttribute("number", number);
		return "shjfgl/view/module/equipmentViewFormModule"; 
	}
	
	@RequestMapping(value="view.action")
	public String view(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			View.setContact(deptName);
//		}
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/equipmentView"; 
	}
	
	/**
	 * 分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-12-08 
	 * @throws UnsupportedEncodingException 
	 * @throws CustomException 
	 */
	@RequestMapping(value="manageViewList.action")
	@ResponseBody
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="get",operateDescribe="机房平面表分页查询")
	public List<View> manageViewList(View view,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		//默认排序
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			View.setContact(deptName);
//		}
		List<View> page_ = viewService.queryAll(view);
		return page_;
	}
	
	
	//廊坊机房平面图页
	//廊坊一层
	@RequestMapping(value="langfangOne.action")
	public String langfangOne(Model model,View view,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())){
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("廊坊一层");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/langfangOne";
	}
	//廊坊二层
	@RequestMapping(value="langfangTwo.action")
	public String langfangTwo(Model model,View view,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())){
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("廊坊二层");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/langfangTwo";
	}
	
	//亦庄大族机房平面图页
	//亦庄大族三层
	@RequestMapping(value="yizhuangdazuThree.action")
	public String yizhuangdazuThree(Model model,View view,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())){
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("大族3层");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/dazuThree";
	}
	//亦庄大族四层
	@RequestMapping(value="yizhuangdazuFour.action")
	public String yizhuangdazuFour(Model model,View view,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())){
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("大族4层");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/dazuFour";
	}
	//亦庄大族六层
	@RequestMapping(value="yizhuangdazuSix.action")
	public String yizhuangdazuSix(Model model,View view,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())){
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("大族6层");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/dazuSix";
	}

//	北区机房
//	沙河1037机房平面图页
	@RequestMapping(value="shahe1037.action")
	public String shahe1037(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("1037");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/shahe1037"; 
	}
	
//	沙河2033机房平面图页
	@RequestMapping(value="shahe2033.action")
	public String shahe2033(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
//		String deptName = SystemSecurityUtils.getUser().getDeptName();
//		if(!SystemSecurityUtils.getUser().getIsLeader().equals("1")){
//			View.setContact(deptName);
//		}
		view.setMachina("2033");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/shahe2033"; 
	}
	
//	沙河4027机房平面图页
	@RequestMapping(value="shahe2047.action")
	public String shahe2047(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("4027");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/shahe2047"; 
	}

//	沙河4033机房平面图页
	@RequestMapping(value="shahe4033.action")
	public String shahe4033(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("4033");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/shahe4033"; 
	}
	
//	兆维机房平面图页
	@RequestMapping(value="zhaowei.action")
	public String zhaowei(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/zhaowei"; 
	}

//	兆维1层机房平面图页
	@RequestMapping(value="zhaowei1f.action")
	public String zhaowei1f(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("zhaowei1f");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/zhaowei1f"; 
	}

//	兆维2层机房平面图页
	@RequestMapping(value="zhaowei2f.action")
	public String zhaowei2f(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("zhaowei2f");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/zhaowei2f"; 
	}

//	兆维3层机房平面图页
	@RequestMapping(value="zhaowei3f.action")
	public String zhaowei3f(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("zhaowei3f");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/zhaowei3f"; 
	}

//	西区机房
//	鲁谷2-1机房平面图页
	@RequestMapping(value="lugu2_1.action")
	public String lugu2_1(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("2-1");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/lugu2_1"; 
	}

//	鲁谷4-6机房平面图页
	@RequestMapping(value="lugu4_6.action")
	public String lugu4_6(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("4-6");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/lugu4_6"; 
	}


//	清华园机房平面图页
	@RequestMapping(value="qinghuayuan.action")
	public String qinghuayuan(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/qinghuayuan"; 
	}

//	南区机房
//	看丹二层机房平面图页
	@RequestMapping(value="kandan2f.action")
	public String kandan2f(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("2F");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/kandan2f"; 
	}
	
//	看丹三层机房平面图页
	@RequestMapping(value="kandan3f.action")
	public String kandan3f(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("3F");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/kandan3f"; 
	}

//	洋桥A区机房平面图页
	@RequestMapping(value="yangqiao_areaA.action")
	public String yangqiao_areaA(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("yangqiaoa");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/yangqiao_areaA"; 
	}

//	洋桥B区机房平面图页
	@RequestMapping(value="yangqiao_areaB.action")
	public String yangqiao_areaB(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("yangqiaob");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/yangqiao_areaB"; 
	}

//	富丰机房平面图页
	@RequestMapping(value="fufeng.action")
	public String fufeng(Model model,View view,HttpServletRequest request) throws Exception{
		if(StringUtil.isEmpty(view.getOrderBy())) {
			view.addOrderByFieldDesc("t.CREATE_DATE");
		}
		view.setMachina("富丰二部");
		List<View> page_ = viewService.queryAll(view);
		model.addAttribute("dList", page_);
		return "shjfgl/view/fufeng"; 
	}
	/*各个机房机柜信息维护页*/
	/**
	* 清华园机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manage.action")
	@ActionLog(operateModelNm="机房平面表",operateFuncNm="manage",operateDescribe="对机房平面表进行跳转操作")
	public String manage(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/view";
	}
	
	/**
	* 沙河机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageShahe.action")
	@ActionLog(operateModelNm="沙河机房机柜表",operateFuncNm="manage",operateDescribe="对沙河机房机柜表进行跳转操作")
	public String manageShahe(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/shaheView";
	}
	
	/**
	* 廊坊机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageLangfang.action")
	@ActionLog(operateModelNm="廊坊机房机柜表",operateFuncNm="manage",operateDescribe="对廊坊机房机柜表进行跳转操作")
	public String manageLangfang(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/langfangView";
	}
	
	/**
	* 鲁谷机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageLugu.action")
	@ActionLog(operateModelNm="鲁谷机房机柜表",operateFuncNm="manage",operateDescribe="对鲁谷机房机柜表进行跳转操作")
	public String manageLugu(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/luguView";
	}
	
	/**
	* 富丰机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageFufeng.action")
	@ActionLog(operateModelNm="富丰机房机柜表",operateFuncNm="manage",operateDescribe="对富丰机房机柜表进行跳转操作")
	public String manageFufeng(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/fufengView";
	}
	
	/**
	* 大族机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageDazu.action")
	@ActionLog(operateModelNm="大族机房机柜表",operateFuncNm="manage",operateDescribe="对大族机房机柜表进行跳转操作")
	public String manageDazu(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/dazuView";
	}
	
	/**
	* 看丹机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageKandan.action")
	@ActionLog(operateModelNm="看丹机房机柜表",operateFuncNm="manage",operateDescribe="对看丹机房机柜表进行跳转操作")
	public String manageKandan(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/kandanView";
	}
	
	/**
	* 洋桥机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageYangqiao.action")
	@ActionLog(operateModelNm="洋桥机房机柜表",operateFuncNm="manage",operateDescribe="对洋桥机房机柜表进行跳转操作")
	public String manageYangqiao(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/yangqiaoView";
	}
	
	/**
	* 兆维机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageZhaowei.action")
	@ActionLog(operateModelNm="兆维机房机柜表",operateFuncNm="manage",operateDescribe="对兆维机房机柜表进行跳转操作")
	public String manageZhaowei(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/zhaoweiView";
	}
	
	/**
	* 比目鱼机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2017-05-08 
	*/
	@RequestMapping(value="manageBimuyu.action")
	@ActionLog(operateModelNm="比目鱼机房机柜表",operateFuncNm="manageBimuyu",operateDescribe="对比目鱼机房机柜表进行跳转操作")
	public String manageBimuyu(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/bimuyuView";
	}
	
	/**
	* 小米代维机房机柜跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-12-08 
	*/
	@RequestMapping(value="manageXiaoMiDaiWei.action")
	@ActionLog(operateModelNm="兆维机房机柜表",operateFuncNm="manage",operateDescribe="对兆维机房机柜表进行跳转操作")
	public String manageXiaoMiDaiWei(Model model,HttpServletRequest request) throws Exception{
		return "shjfgl/view/xiaomidaiweiView";
	}
}