package com.jc.system.dic.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jc.system.dic.IDicManager;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Arrays;

public class DicCheckBoxTag extends BaseDiyTag {

	private static final long serialVersionUID = 3521751677008498981L;
	
	private IDicManager dicManager = new DicManagerImpl();
	
	private String name;
    private String type;

    public int doStartTag() {
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

        List<String> checkedParams = null;
        
        if(paramValue != null) {
        	checkedParams = Arrays.asList(paramValue.split(","));
        }
        for(BaseOption option : baseOptions) {
            String id = type + "_" + option.getOptionValue();
            sb.append("<label class='checkbox inline' for='").append(id).append("'>")
            	.append("<input type='checkbox' name='").append(name).append("' id='").append(id).append("'")
                    .append(" value='").append(option.getOptionValue()).append("'");
            if(checkedParams != null) {
                if(checkedParams.contains(option.getOptionValue())) {
                    sb.append(" checked ");
                }
            }
            generateAttribute(sb);
            sb.append(" /> ").append(option.getOptionName()).append("</label> ");
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
