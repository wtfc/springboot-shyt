package com.jc.oa.finance.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 月收入
*/
public class Invoices extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String invoicesMonth12;//   应收月份	private BigDecimal monthAmount12;//   应收金额	private Date invoicesStartdate12;//   应收起始日期	private Date invoicesEnddate12;//   应收终止日期
	private String invoicesMonth1;//   应收月份
	private BigDecimal monthAmount1;//   应收金额
	private Date invoicesStartdate1;//   应收起始日期
	private Date invoicesEnddate1;//   应收终止日期
	private String invoicesMonth2;//   应收月份
	private BigDecimal monthAmount2;//   应收金额
	private Date invoicesStartdate2;//   应收起始日期
	private Date invoicesEnddate2;//   应收终止日期
	private String invoicesMonth3;//   应收月份
	private BigDecimal monthAmount3;//   应收金额
	private Date invoicesStartdate3;//   应收起始日期
	private Date invoicesEnddate3;//   应收终止日期
	private String invoicesMonth4;//   应收月份
	private BigDecimal monthAmount4;//   应收金额
	private Date invoicesStartdate4;//   应收起始日期
	private Date invoicesEnddate4;//   应收终止日期
	private String invoicesMonth5;//   应收月份
	private BigDecimal monthAmount5;//   应收金额
	private Date invoicesStartdate5;//   应收起始日期
	private Date invoicesEnddate5;//   应收终止日期
	private String invoicesMonth6;//   应收月份
	private BigDecimal monthAmount6;//   应收金额
	private Date invoicesStartdate6;//   应收起始日期
	private Date invoicesEnddate6;//   应收终止日期
	private String invoicesMonth7;//   应收月份
	private BigDecimal monthAmount7;//   应收金额
	private Date invoicesStartdate7;//   应收起始日期
	private Date invoicesEnddate7;//   应收终止日期
	private String invoicesMonth8;//   应收月份
	private BigDecimal monthAmount8;//   应收金额
	private Date invoicesStartdate8;//   应收起始日期
	private Date invoicesEnddate8;//   应收终止日期
	private String invoicesMonth9;//   应收月份
	private BigDecimal monthAmount9;//   应收金额
	private Date invoicesStartdate9;//   应收起始日期
	private Date invoicesEnddate9;//   应收终止日期
	private String invoicesMonth10;//   应收月份
	private BigDecimal monthAmount10;//   应收金额
	private Date invoicesStartdate10;//   应收起始日期
	private Date invoicesEnddate10;//   应收终止日期
	private String invoicesMonth11;//   应收月份
	private BigDecimal monthAmount11;//   应收金额
	private Date invoicesStartdate11;//   应收起始日期
	private Date invoicesEnddate11;//   应收终止日期
	private BigDecimal commision12;//代理费
	private BigDecimal commision1;//代理费
	private BigDecimal commision2;//代理费
	private BigDecimal commision3;//代理费
	private BigDecimal commision4;//代理费
	private BigDecimal commision5;//代理费
	private BigDecimal commision6;//代理费
	private BigDecimal commision7;//代理费
	private BigDecimal commision8;//代理费
	private BigDecimal commision9;//代理费
	private BigDecimal commision10;//代理费
	private BigDecimal commision11;//代理费
	private String invoicesId1; 
	private String invoicesId2;
	private String invoicesId3;
	private String invoicesId4;
	private String invoicesId5;
	private String invoicesId6;
	private Integer otherDeductions=0;
	public String getInvoicesId1() {
		return invoicesId1;
	}
	public void setInvoicesId1(String invoicesId1) {
		this.invoicesId1 = invoicesId1;
	}
	public String getInvoicesId2() {
		return invoicesId2;
	}
	public void setInvoicesId2(String invoicesId2) {
		this.invoicesId2 = invoicesId2;
	}
	public String getInvoicesId3() {
		return invoicesId3;
	}
	public void setInvoicesId3(String invoicesId3) {
		this.invoicesId3 = invoicesId3;
	}
	public String getInvoicesId4() {
		return invoicesId4;
	}
	public void setInvoicesId4(String invoicesId4) {
		this.invoicesId4 = invoicesId4;
	}
	public String getInvoicesId5() {
		return invoicesId5;
	}
	public void setInvoicesId5(String invoicesId5) {
		this.invoicesId5 = invoicesId5;
	}
	public String getInvoicesId6() {
		return invoicesId6;
	}
	public void setInvoicesId6(String invoicesId6) {
		this.invoicesId6 = invoicesId6;
	}
	public String getInvoicesId7() {
		return invoicesId7;
	}
	public void setInvoicesId7(String invoicesId7) {
		this.invoicesId7 = invoicesId7;
	}
	public String getInvoicesId8() {
		return invoicesId8;
	}
	public void setInvoicesId8(String invoicesId8) {
		this.invoicesId8 = invoicesId8;
	}
	public String getInvoicesId9() {
		return invoicesId9;
	}
	public void setInvoicesId9(String invoicesId9) {
		this.invoicesId9 = invoicesId9;
	}
	public String getInvoicesId10() {
		return invoicesId10;
	}
	public void setInvoicesId10(String invoicesId10) {
		this.invoicesId10 = invoicesId10;
	}
	public String getInvoicesId11() {
		return invoicesId11;
	}
	public void setInvoicesId11(String invoicesId11) {
		this.invoicesId11 = invoicesId11;
	}
	public String getInvoicesId12() {
		return invoicesId12;
	}
	public void setInvoicesId12(String invoicesId12) {
		this.invoicesId12 = invoicesId12;
	}
	private String invoicesId7;
	private String invoicesId8;
	private String invoicesId9;
	private String invoicesId10;
	private String invoicesId11;
	private String invoicesId12;
	public BigDecimal getCommision12() {
		return commision12;
	}
	public void setCommision12(BigDecimal commision12) {
		this.commision12 = commision12;
	}
	public BigDecimal getCommision1() {
		return commision1;
	}
	public void setCommision1(BigDecimal commision1) {
		this.commision1 = commision1;
	}
	public BigDecimal getCommision2() {
		return commision2;
	}
	public void setCommision2(BigDecimal commision2) {
		this.commision2 = commision2;
	}
	public BigDecimal getCommision3() {
		return commision3;
	}
	public void setCommision3(BigDecimal commision3) {
		this.commision3 = commision3;
	}
	public BigDecimal getCommision4() {
		return commision4;
	}
	public void setCommision4(BigDecimal commision4) {
		this.commision4 = commision4;
	}
	public BigDecimal getCommision5() {
		return commision5;
	}
	public void setCommision5(BigDecimal commision5) {
		this.commision5 = commision5;
	}
	public BigDecimal getCommision6() {
		return commision6;
	}
	public void setCommision6(BigDecimal commision6) {
		this.commision6 = commision6;
	}
	public BigDecimal getCommision7() {
		return commision7;
	}
	public void setCommision7(BigDecimal commision7) {
		this.commision7 = commision7;
	}
	public BigDecimal getCommision8() {
		return commision8;
	}
	public void setCommision8(BigDecimal commision8) {
		this.commision8 = commision8;
	}
	public BigDecimal getCommision9() {
		return commision9;
	}
	public void setCommision9(BigDecimal commision9) {
		this.commision9 = commision9;
	}
	public BigDecimal getCommision10() {
		return commision10;
	}
	public void setCommision10(BigDecimal commision10) {
		this.commision10 = commision10;
	}
	public BigDecimal getCommision11() {
		return commision11;
	}
	public void setCommision11(BigDecimal commision11) {
		this.commision11 = commision11;
	}
	public String getInvoicesMonth12() {
		return invoicesMonth12;
	}
	public void setInvoicesMonth12(String invoicesMonth12) {
		this.invoicesMonth12 = invoicesMonth12;
	}
	public BigDecimal getMonthAmount12() {
		return monthAmount12;
	}
	public void setMonthAmount12(BigDecimal monthAmount12) {
		this.monthAmount12 = monthAmount12;
	}
	public Date getInvoicesStartdate12() {
		return invoicesStartdate12;
	}
	public void setInvoicesStartdate12(Date invoicesStartdate12) {
		this.invoicesStartdate12 = invoicesStartdate12;
	}
	public Date getInvoicesEnddate12() {
		return invoicesEnddate12;
	}
	public void setInvoicesEnddate12(Date invoicesEnddate12) {
		this.invoicesEnddate12 = invoicesEnddate12;
	}
	public String getInvoicesMonth1() {
		return invoicesMonth1;
	}
	public void setInvoicesMonth1(String invoicesMonth1) {
		this.invoicesMonth1 = invoicesMonth1;
	}
	public BigDecimal getMonthAmount1() {
		return monthAmount1;
	}
	public void setMonthAmount1(BigDecimal monthAmount1) {
		this.monthAmount1 = monthAmount1;
	}
	public Date getInvoicesStartdate1() {
		return invoicesStartdate1;
	}
	public void setInvoicesStartdate1(Date invoicesStartdate1) {
		this.invoicesStartdate1 = invoicesStartdate1;
	}
	public Date getInvoicesEnddate1() {
		return invoicesEnddate1;
	}
	public void setInvoicesEnddate1(Date invoicesEnddate1) {
		this.invoicesEnddate1 = invoicesEnddate1;
	}
	public String getInvoicesMonth2() {
		return invoicesMonth2;
	}
	public void setInvoicesMonth2(String invoicesMonth2) {
		this.invoicesMonth2 = invoicesMonth2;
	}
	public BigDecimal getMonthAmount2() {
		return monthAmount2;
	}
	public void setMonthAmount2(BigDecimal monthAmount2) {
		this.monthAmount2 = monthAmount2;
	}
	public Date getInvoicesStartdate2() {
		return invoicesStartdate2;
	}
	public void setInvoicesStartdate2(Date invoicesStartdate2) {
		this.invoicesStartdate2 = invoicesStartdate2;
	}
	public Date getInvoicesEnddate2() {
		return invoicesEnddate2;
	}
	public void setInvoicesEnddate2(Date invoicesEnddate2) {
		this.invoicesEnddate2 = invoicesEnddate2;
	}
	public String getInvoicesMonth3() {
		return invoicesMonth3;
	}
	public void setInvoicesMonth3(String invoicesMonth3) {
		this.invoicesMonth3 = invoicesMonth3;
	}
	public BigDecimal getMonthAmount3() {
		return monthAmount3;
	}
	public void setMonthAmount3(BigDecimal monthAmount3) {
		this.monthAmount3 = monthAmount3;
	}
	public Date getInvoicesStartdate3() {
		return invoicesStartdate3;
	}
	public void setInvoicesStartdate3(Date invoicesStartdate3) {
		this.invoicesStartdate3 = invoicesStartdate3;
	}
	public Date getInvoicesEnddate3() {
		return invoicesEnddate3;
	}
	public void setInvoicesEnddate3(Date invoicesEnddate3) {
		this.invoicesEnddate3 = invoicesEnddate3;
	}
	public String getInvoicesMonth4() {
		return invoicesMonth4;
	}
	public void setInvoicesMonth4(String invoicesMonth4) {
		this.invoicesMonth4 = invoicesMonth4;
	}
	public BigDecimal getMonthAmount4() {
		return monthAmount4;
	}
	public void setMonthAmount4(BigDecimal monthAmount4) {
		this.monthAmount4 = monthAmount4;
	}
	public Date getInvoicesStartdate4() {
		return invoicesStartdate4;
	}
	public void setInvoicesStartdate4(Date invoicesStartdate4) {
		this.invoicesStartdate4 = invoicesStartdate4;
	}
	public Date getInvoicesEnddate4() {
		return invoicesEnddate4;
	}
	public void setInvoicesEnddate4(Date invoicesEnddate4) {
		this.invoicesEnddate4 = invoicesEnddate4;
	}
	public String getInvoicesMonth5() {
		return invoicesMonth5;
	}
	public void setInvoicesMonth5(String invoicesMonth5) {
		this.invoicesMonth5 = invoicesMonth5;
	}
	public BigDecimal getMonthAmount5() {
		return monthAmount5;
	}
	public void setMonthAmount5(BigDecimal monthAmount5) {
		this.monthAmount5 = monthAmount5;
	}
	public Date getInvoicesStartdate5() {
		return invoicesStartdate5;
	}
	public void setInvoicesStartdate5(Date invoicesStartdate5) {
		this.invoicesStartdate5 = invoicesStartdate5;
	}
	public Date getInvoicesEnddate5() {
		return invoicesEnddate5;
	}
	public void setInvoicesEnddate5(Date invoicesEnddate5) {
		this.invoicesEnddate5 = invoicesEnddate5;
	}
	public String getInvoicesMonth6() {
		return invoicesMonth6;
	}
	public void setInvoicesMonth6(String invoicesMonth6) {
		this.invoicesMonth6 = invoicesMonth6;
	}
	public BigDecimal getMonthAmount6() {
		return monthAmount6;
	}
	public void setMonthAmount6(BigDecimal monthAmount6) {
		this.monthAmount6 = monthAmount6;
	}
	public Date getInvoicesStartdate6() {
		return invoicesStartdate6;
	}
	public void setInvoicesStartdate6(Date invoicesStartdate6) {
		this.invoicesStartdate6 = invoicesStartdate6;
	}
	public Date getInvoicesEnddate6() {
		return invoicesEnddate6;
	}
	public void setInvoicesEnddate6(Date invoicesEnddate6) {
		this.invoicesEnddate6 = invoicesEnddate6;
	}
	public String getInvoicesMonth7() {
		return invoicesMonth7;
	}
	public void setInvoicesMonth7(String invoicesMonth7) {
		this.invoicesMonth7 = invoicesMonth7;
	}
	public BigDecimal getMonthAmount7() {
		return monthAmount7;
	}
	public void setMonthAmount7(BigDecimal monthAmount7) {
		this.monthAmount7 = monthAmount7;
	}
	public Date getInvoicesStartdate7() {
		return invoicesStartdate7;
	}
	public void setInvoicesStartdate7(Date invoicesStartdate7) {
		this.invoicesStartdate7 = invoicesStartdate7;
	}
	public Date getInvoicesEnddate7() {
		return invoicesEnddate7;
	}
	public void setInvoicesEnddate7(Date invoicesEnddate7) {
		this.invoicesEnddate7 = invoicesEnddate7;
	}
	public String getInvoicesMonth8() {
		return invoicesMonth8;
	}
	public void setInvoicesMonth8(String invoicesMonth8) {
		this.invoicesMonth8 = invoicesMonth8;
	}
	public BigDecimal getMonthAmount8() {
		return monthAmount8;
	}
	public void setMonthAmount8(BigDecimal monthAmount8) {
		this.monthAmount8 = monthAmount8;
	}
	public Date getInvoicesStartdate8() {
		return invoicesStartdate8;
	}
	public void setInvoicesStartdate8(Date invoicesStartdate8) {
		this.invoicesStartdate8 = invoicesStartdate8;
	}
	public Date getInvoicesEnddate8() {
		return invoicesEnddate8;
	}
	public void setInvoicesEnddate8(Date invoicesEnddate8) {
		this.invoicesEnddate8 = invoicesEnddate8;
	}
	public String getInvoicesMonth9() {
		return invoicesMonth9;
	}
	public void setInvoicesMonth9(String invoicesMonth9) {
		this.invoicesMonth9 = invoicesMonth9;
	}
	public BigDecimal getMonthAmount9() {
		return monthAmount9;
	}
	public void setMonthAmount9(BigDecimal monthAmount9) {
		this.monthAmount9 = monthAmount9;
	}
	public Date getInvoicesStartdate9() {
		return invoicesStartdate9;
	}
	public void setInvoicesStartdate9(Date invoicesStartdate9) {
		this.invoicesStartdate9 = invoicesStartdate9;
	}
	public Date getInvoicesEnddate9() {
		return invoicesEnddate9;
	}
	public void setInvoicesEnddate9(Date invoicesEnddate9) {
		this.invoicesEnddate9 = invoicesEnddate9;
	}
	public String getInvoicesMonth10() {
		return invoicesMonth10;
	}
	public void setInvoicesMonth10(String invoicesMonth10) {
		this.invoicesMonth10 = invoicesMonth10;
	}
	public BigDecimal getMonthAmount10() {
		return monthAmount10;
	}
	public void setMonthAmount10(BigDecimal monthAmount10) {
		this.monthAmount10 = monthAmount10;
	}
	public Date getInvoicesStartdate10() {
		return invoicesStartdate10;
	}
	public void setInvoicesStartdate10(Date invoicesStartdate10) {
		this.invoicesStartdate10 = invoicesStartdate10;
	}
	public Date getInvoicesEnddate10() {
		return invoicesEnddate10;
	}
	public void setInvoicesEnddate10(Date invoicesEnddate10) {
		this.invoicesEnddate10 = invoicesEnddate10;
	}
	public String getInvoicesMonth11() {
		return invoicesMonth11;
	}
	public void setInvoicesMonth11(String invoicesMonth11) {
		this.invoicesMonth11 = invoicesMonth11;
	}
	public BigDecimal getMonthAmount11() {
		return monthAmount11;
	}
	public void setMonthAmount11(BigDecimal monthAmount11) {
		this.monthAmount11 = monthAmount11;
	}
	public Date getInvoicesStartdate11() {
		return invoicesStartdate11;
	}
	public void setInvoicesStartdate11(Date invoicesStartdate11) {
		this.invoicesStartdate11 = invoicesStartdate11;
	}
	public Date getInvoicesEnddate11() {
		return invoicesEnddate11;
	}
	public void setInvoicesEnddate11(Date invoicesEnddate11) {
		this.invoicesEnddate11 = invoicesEnddate11;
	}
	public Integer getOtherDeductions() {
		return otherDeductions;
	}
	public void setOtherDeductions(Integer otherDeductions) {
		this.otherDeductions = otherDeductions;
	}}

