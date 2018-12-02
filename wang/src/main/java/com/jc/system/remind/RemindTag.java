package com.jc.system.remind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.jc.system.remind.domain.Remind;
import com.jc.system.remind.util.FileXmlUtil;
/**
 * @title GOA系统管理
 * @description  SimpleTagSupport类
 * @version  2014-07-24
 */
public class RemindTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		// 对象相应的getXXX()
		PageContext pageContext = (PageContext) this.getJspContext();
		HttpServletRequest request = (HttpServletRequest) ((PageContext) this
				.getJspContext()).getRequest();
		List<Remind> resourceList = FileXmlUtil.getFieldList(request);
		for (Remind resource : resourceList) {
			pageContext.setAttribute("button", resource); // 执行标签体的内容
			getJspBody().invoke(null);
		}
	}
}
