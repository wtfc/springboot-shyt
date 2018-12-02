package com.jc.system.dic.domain;

/**
 * @description: 自定义字典bean
 */
public class OtherDic {
	private String flag;
	private String beanStr;
	private String serviceStr;

	public OtherDic() {
	}

	public OtherDic(String flag, String beanStr, String serviceStr) {
		this.flag = flag;
		this.beanStr = beanStr;
		this.serviceStr = serviceStr;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBeanStr() {
		return beanStr;
	}

	public void setBeanStr(String beanStr) {
		this.beanStr = beanStr;
	}

	public String getServiceStr() {
		return serviceStr;
	}

	public void setServiceStr(String serviceStr) {
		this.serviceStr = serviceStr;
	}

	@Override
	public String toString() {
		return "配置信息：flag:" + flag + ",bean:" + beanStr + ",service:"
				+ serviceStr;
	}
}
