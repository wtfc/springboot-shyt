package com.jc.android.oa.shyt.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 资产信息表
*/
public class ToaShytAsset4M extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String assetsName;//   资产名称
	public String getAssetsName() {
	@JsonSerialize(using = CustomDateSerializer.class)
