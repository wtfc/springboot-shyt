package com.jc.system.number.domain;

/**
 * 
 * @description: 处理传递的参数
 * @author：高研
 * @created: 2013-4-1 下午2:49:44
 */
public class Parameter {

	private String parameter[];

	/**
	 * 初始化参数信息
	 * @param p
	 */
	
	private void init(){
		parameter = new String[9];
		for(int i=0;i<9;i++){
			parameter[i] = "";
		}
	}

	 public Parameter(String[] p) {
		 init();
//		 if(p == null){
//			 p = new String[9];
//		 }
		 if(p != null)
		 for(int i=0;i<p.length;i++) {
			 try {
				 parameter[i] = p[i];
			 } catch(Exception e) {
				 parameter[i] = "";
			 }
	 	}
	 }

	/**
	 * 获取参数数组
	 * @return
	 */
	public String[] getParameter() {
		return this.parameter;
	}

	/**
	 * 根据下标获取参数数组
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public String[] getParameter(int startIndex, int endIndex){
		String s[] = new String[7];
		int j = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			s[j] = parameter[i].trim();
			j++;
		}
		return s;
	}

}
