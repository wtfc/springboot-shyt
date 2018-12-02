package com.jc.system.dic.util;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;

import com.jc.system.dic.IDicManager;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.impl.DicManagerImpl;

public class DicRadioTag extends BaseDiyTag{
    private static final long serialVersionUID = 8248756476934340948L;
    
    private IDicManager dicManager = new DicManagerImpl();
    
    private String name;
    private String type;

    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        
        List<Dic> dict_list = dicManager.getDicsByTypeCode(this.getDictName(), true);

        String filter = this.getFilter();
        List<BaseOption> baseOptions = new ArrayList<BaseOption>();
        
        String paramValue = this.getDefaultValue().toString();
        
        for(Dic dc:dict_list){
        	if(filter != null && filter.indexOf(dc.getCode()) == -1){
        		BaseOption option = new BaseOption();
            	option.setOptionValue(dc.getCode());
            	option.setOptionName(dc.getValue());
            	baseOptions.add(option);
            	if(paramValue == null && dc.getDefaultValue() == 1)
            		paramValue = dc.getCode();
        	} else if(filter == null){
        		BaseOption option = new BaseOption();
            	option.setOptionValue(dc.getCode());
            	option.setOptionName(dc.getValue());
            	baseOptions.add(option);
            	if(paramValue == null && dc.getDefaultValue() == 1)
            		paramValue = dc.getCode();
        	}
        }

        int index = 0;
        for(BaseOption option : baseOptions) {
            String id = type + "_" + option.getOptionValue();
            sb.append("<label class='radio inline' for='").append(id).append("'>").append(" <input type='radio' name='").append(name).append("' id='").append(id).append("'")
                    .append(" value='").append(option.getOptionValue()).append("'");
            if(index == 0) {
                sb.append(" checked ");
            }
            if(paramValue.equals(option.getOptionValue())) {
                sb.append(" checked ");
            }

            generateAttribute(sb);
            sb.append(" /> ").append(option.getOptionName()).append("</label>");
            index ++;
        }
        try {
            pageContext.getOut().print(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
