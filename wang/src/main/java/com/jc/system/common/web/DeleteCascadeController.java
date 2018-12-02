package com.jc.system.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.web.BaseController;
import com.jc.system.CustomException;
import com.jc.system.common.util.DeleteCascadeUtils;
import com.jc.system.security.util.ActionLog;

/**
 * 
 * @title GOA V2.0
 * @description  级联数据的完整性控制类
 * @author zhangligang
 * @version  2014年7月22日 下午4:27:32
 */
@Controller
@RequestMapping(value = "/cascade")
public class DeleteCascadeController extends BaseController {

	public DeleteCascadeController() {
	}
	
	/**
	 * 方法描述：
	 * @param bizId 级联配置业务ID
	 * @param columnValue 验证值
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author zhangligang
	 * @version  2014年7月22日下午4:49:55
	 * @see
	 */
	@RequestMapping(value = "canDelete.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_公共/数据完整性", operateFuncNm = "canDelete", operateDescribe = "完整性检查/ 检查数据是否可删除")
	public boolean canDelete(String bizId,  String columnValue,HttpServletRequest request) throws CustomException {
		return DeleteCascadeUtils.canDelete(bizId,  columnValue);
	}
	
	/**
	 * 方法描述：
	 * @param bizId 级联配置业务ID
	 * @param columnValue 待验证多个业务值，用逗号分隔;
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author zhangligang
	 * @version  2014年7月22日下午4:49:55
	 * @see
	 */
	@RequestMapping(value = "canBatchDelete.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_公共/数据完整性", operateFuncNm = "canBatchDelete", operateDescribe = "完整性检查/ 检查数据是否可删除")
	public boolean canBatchDelete(String bizId,  String columnValue,HttpServletRequest request) throws CustomException {
		return DeleteCascadeUtils.canBatchDelete(bizId,  columnValue);
	}
	
	/**
	 * 方法描述：检查业务记录是否存在
	 * @param functionId 级联配置业务ID
	 * @param columnValue 验证值
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author zhangligang
	 * @version  2014年7月22日下午4:49:55
	 * @see
	 */
	@RequestMapping(value = "checkExist.action")
	@ResponseBody
	@ActionLog(operateModelNm = "OA_公共/数据完整性", operateFuncNm = "checkExist", operateDescribe = "完整性检查/ 检查数据是否存在")
	public boolean checkExist(String functionId,  String columnValue,HttpServletRequest request) throws CustomException {
		return DeleteCascadeUtils.checkExist(functionId,  columnValue);
	}
}
