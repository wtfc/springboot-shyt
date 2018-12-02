package com.jc.oa.shyt.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 资产信息表
*/
public class ToaShytAsset extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String assetsName;//   资产名称	private Integer type;//   类别: 0办公家具 1办公设备	private String bard;//   品牌	private String assetsNum;//   资产编号	private String machineNum;//   设备参数	private String unit;//   单位	private String number;//   数量	private String price;//   单价	private Date inDate;//   入库日期	private String useDept;//   使用部门	private String address;//   存放地点	private String tiaoboneirong;//   调拨内容
	public String getAssetsName() {	    return assetsName;	}	public void setAssetsName(String assetsName) {	    this.assetsName=assetsName;	}	public Integer getType() {	    return type;	}	public void setType(Integer type) {	    this.type=type;	}	public String getBard() {	    return bard;	}	public void setBard(String bard) {	    this.bard=bard;	}	public String getAssetsNum() {	    return assetsNum;	}	public void setAssetsNum(String assetsNum) {	    this.assetsNum=assetsNum;	}	public String getMachineNum() {	    return machineNum;	}	public void setMachineNum(String machineNum) {	    this.machineNum=machineNum;	}	public String getUnit() {	    return unit;	}	public void setUnit(String unit) {	    this.unit=unit;	}	public String getNumber() {	    return number;	}	public void setNumber(String number) {	    this.number=number;	}	public String getPrice() {	    return price;	}	public void setPrice(String price) {	    this.price=price;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getInDate() {	    return inDate;	}	public void setInDate(Date inDate) {	    this.inDate=inDate;	}	public String getUseDept() {	    return useDept;	}	public void setUseDept(String useDept) {	    this.useDept=useDept;	}	public String getAddress() {	    return address;	}	public void setAddress(String address) {	    this.address=address;	}	public String getTiaoboneirong() {	    return tiaoboneirong;	}	public void setTiaoboneirong(String tiaoboneirong) {	    this.tiaoboneirong=tiaoboneirong;	}}

