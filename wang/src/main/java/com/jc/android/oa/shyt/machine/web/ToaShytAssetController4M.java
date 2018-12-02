package com.jc.android.oa.shyt.machine.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.shyt.machine.domain.Equipment4M;
import com.jc.android.oa.shyt.machine.domain.ToaShytAsset4M;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.util.ActionLog;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.shyt.domain.ToaShytAsset;
import com.jc.oa.shyt.service.IToaShytAssetService;
/**
 * @author mrb
 * @version 资产信息表
*/
@Controller
@RequestMapping(value="/android/toaShytAsset")
public class ToaShytAssetController4M extends BaseController{
	
	@Autowired 
	private IToaShytAssetService toaShytAssetService; 
	
	public ToaShytAssetController4M(){
	}
	
//	/**
//	* 修改方法
//	* @param TimeSet timeSet 实体类
//	* @return Integer 更新的数目
//	* @author
//	*/
//	@RequestMapping(value="update.action")
//	@ResponseBody
//	@ActionLog(operateModelNm="资产信息表",operateFuncNm="update",operateDescribe="对资产信息表进行更新操作")
//	public Map<String, Object> update(ToaShytAsset toaShytAsset, BindingResult result,
//			HttpServletRequest request) throws Exception{
//		Integer flag = toaShytAssetService.update(toaShytAsset);
//		if (flag == 1) {
//			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
//			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
//		}
//		String token = getToken(request);
//		resultMap.put(GlobalContext.SESSION_TOKEN, token);
//		return resultMap;
//	}

	/**
	 * APP资产信息-获取单条记录方法
	 * @param TimeSet timeSet 实体类
	 * @return TimeSet 查询结果
	 * @throws Exception
	 * @author
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="资产信息表",operateFuncNm="get",operateDescribe="对资产信息表进行单条查询操作")
	public Map<String, Object> get(String assetsNum,String id,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ToaShytAsset toaShytAsset = new ToaShytAsset();
		if(!StringUtil.isEmpty(assetsNum)){
			toaShytAsset.setAssetsNum(assetsNum);
		}
		if(!StringUtil.isEmpty(id)){
			toaShytAsset.setId(Long.valueOf(id));
		}
		ToaShytAsset toaShytAsset1 = toaShytAssetService.get(toaShytAsset);
		if(toaShytAsset1 !=null){
			ToaShytAsset4M ToaShytAsset4M = new ToaShytAsset4M();
			BeanUtils.copyProperties(toaShytAsset1, ToaShytAsset4M);// (源，目标)
			// 成功状态位和数据
			resultMap.put("state", "success");
			resultMap.put("data", ToaShytAsset4M);
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
	
	/**
	 * APP资产信息-分页查询方法
	 * @param TimeSet timeSet 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@RequestMapping(value="manageList.action")
	@ResponseBody
	@ActionLog(operateModelNm="资产信息表",operateFuncNm="get",operateDescribe="资产信息表分页查询")
	public Map<String, Object> manageList(ToaShytAsset toaShytAsset,PageManager page,HttpServletRequest request) throws UnsupportedEncodingException, CustomException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//默认排序
		if(StringUtil.isEmpty(toaShytAsset.getOrderBy())) {
			toaShytAsset.addOrderByFieldDesc("t.CREATE_DATE");
		}
		List<ToaShytAsset> page_ = toaShytAssetService.queryAll(toaShytAsset);
		if(page_ !=null){
			// 成功状态位和数据
			resultMap.put("state", "success");
			resultMap.put("data", page_);
		}else{
			// 失败状态位和错误信息
			resultMap.put("state", "failure");
			resultMap.put("errormsg",MessageUtils.getMessage("JC_OA_COMMON_016"));
		}
		return resultMap; 
	}
}
