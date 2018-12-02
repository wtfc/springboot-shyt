package com.jc.system.remind.domain;
/**
 * @title GOA系统管理
 * @description 通知消息表 实体类
 * @author 
 * @version  2014-07-23
 */
public class Remind{

	public static final long serialVersionUID = 1L;
	
		public String id;
	
		public String iclass ;  
		
		public String dataoriginaltitle;
		
		public String onclickurl;  
	
		public String divid;
		
		public String  tegartClass ;
		
		public String  num ;
		
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getIclass() {
			return iclass;
		}
		public void setIclass(String iclass) {
			this.iclass = iclass;
		}
		public String getDataoriginaltitle() {
			return dataoriginaltitle;
		}
		public void setDataoriginaltitle(String dataoriginaltitle) {
			this.dataoriginaltitle = dataoriginaltitle;
		}
		public String getOnclickurl() {
			return onclickurl;
		}
		public void setOnclickurl(String onclickurl) {
			this.onclickurl = onclickurl;
		}
		public String getDivid() {
			return divid;
		}
		public void setDivid(String divid) {
			this.divid = divid;
		}
		public String getTegartClass() {
			return tegartClass;
		}
		public void setTegartClass(String tegartClass) {
			this.tegartClass = tegartClass;
		}
		
		
		
		
}
