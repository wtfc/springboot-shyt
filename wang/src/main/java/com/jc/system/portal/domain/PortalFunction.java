package com.jc.system.portal.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;


/**
 * @title GOA2.0
 * @description  实体类
 * @author 
 * @version  2014-06-11
 */

public class PortalFunction extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String funName;   /*功能名称*/
	private String funCode;   /*功能编码*/
	private String funUrl;   /*功能链接URL*/
	private String lineType;   /*链接方式*/
	private String viewType;   /*功能视图类型*/
	private String funColumnShowName;   /*显示字段名*/
	private String funColumnName;   /*字段*/
	private String funUrlparameter;   /*链接参数*/
	private String funUrlmore;   /*链接更多*/
	private String funParametertype;   /*参数类型*/
	private Integer funRows;   /*显示行数*/
	private String funUrlParametername;   /*链接参数名称*/
	
	public String getFunName(){
		return funName;
	}
	
	public void setFunName(String funName){
		this.funName = funName;
	}
	
	public String getFunCode(){
		return funCode;
	}
	
	public void setFunCode(String funCode){
		this.funCode = funCode;
	}
	
	public String getFunUrl(){
		return funUrl;
	}
	
	public void setFunUrl(String funUrl){
		this.funUrl = funUrl;
	}
	
	public String getLineType(){
		return lineType;
	}
	
	public void setLineType(String lineType){
		this.lineType = lineType;
	}
	
	public String getViewType(){
		return viewType;
	}
	
	public void setViewType(String viewType){
		this.viewType = viewType;
	}
	
	public String getViewTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("pviewType", viewType).getValue();
	}
	
	public String getFunColumnShowName(){
		return funColumnShowName;
	}
	
	public void setFunColumnShowName(String funColumnShowName){
		this.funColumnShowName = funColumnShowName;
	}
	
	public String getFunColumnName(){
		return funColumnName;
	}
	
	public void setFunColumnName(String funColumnName){
		this.funColumnName = funColumnName;
	}
	
	public String getFunUrlparameter(){
		return funUrlparameter;
	}
	
	public void setFunUrlparameter(String funUrlparameter){
		this.funUrlparameter = funUrlparameter;
	}
	
	public String getFunUrlmore(){
		return funUrlmore;
	}
	
	public void setFunUrlmore(String funUrlmore){
		this.funUrlmore = funUrlmore;
	}
	
	public String getFunParametertype(){
		return funParametertype;
	}
	
	public void setFunParametertype(String funParametertype){
		this.funParametertype = funParametertype;
	}
	
	public Integer getFunRows(){
		return funRows;
	}
	
	public void setFunRows(Integer funRows){
		this.funRows = funRows;
	}
	
	public String getFunUrlParametername(){
		return funUrlParametername;
	}
	
	public void setFunUrlParametername(String funUrlParametername){
		this.funUrlParametername = funUrlParametername;
	}
	
}