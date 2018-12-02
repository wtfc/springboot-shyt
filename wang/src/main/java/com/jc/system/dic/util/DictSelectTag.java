package com.jc.system.dic.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.jc.system.dic.IDicManager;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.impl.DicManagerImpl;

public class DictSelectTag extends BaseDiyTag {
	
	private static final long serialVersionUID = 8090362971179236804L;
	
	private IDicManager dicManager = new DicManagerImpl();
	
	private String name;
    private String type;
    private boolean group;
    private String grouplabel;
    private String headName;
    private String headValue;
    private String headType;//1全部 2请选择
    private boolean isHasLinkage;//是否二级联动下拉列表
    private String selectLinkage;//二级联动下拉列表选中值

    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        StringBuilder childsb = new StringBuilder();
        
        List<Dic> dict_list = dicManager.getDicsByTypeCode(this.getDictName(), true);
        
        sb.append("<select name='"+this.getName()+"' id='"+this.getId()+"' dataType='"+this.getType()+"'");
        generateAttribute(sb);
        sb.append(">");
        
        if(headName != null) {
            sb.append("<option value='").append(headValue).append("' selected>")
                    .append(headName).append("</option>");
        }
        
        if(headType != null) {
        	if(headType.equals("1"))
        		sb.append("<option value='").append(headValue).append("' selected>")
                	.append("-全部-").append("</option>");
        	if(headType.equals("2"))
        		sb.append("<option value='").append(headValue).append("' selected>")
                	.append("-请选择-").append("</option>");
        }
        
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
            	if(isHasLinkage)
            		getChildList(childsb,dc.getCode());
        	} else if(filter == null){
        		BaseOption option = new BaseOption();
            	option.setOptionValue(dc.getCode());
            	option.setOptionName(dc.getValue());
            	baseOptions.add(option);
            	if(paramValue == null && dc.getDefaultValue() == 1)
            		paramValue = dc.getCode();
            	if(isHasLinkage)
            		getChildList(childsb,dc.getCode());
        	}
        }

        int index = 0;
        for(BaseOption option : baseOptions) {
            sb.append("<option value='").append(option.getOptionValue()).append("'");
            if(paramValue != null) {
                 if(paramValue.equals(option.getOptionValue())) {
                     sb.append(" selected ");
                 }
            } else {
                 if(index == 0 && headName == null && headType == null) {
                     sb.append(" selected ");
                 }
            }
            sb.append(">").append(option.getOptionName()).append("</option>");
            index ++;
         }

        sb.append("</select>");
        sb.append(childsb);
        
        try {
            this.pageContext.getOut().print(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
    
    public StringBuilder getChildList(StringBuilder childsb,String dictName) { 
    	List<Dic> dict_list = dicManager.getDicsByTypeCode(dictName, true);
    	List<BaseOption> baseOptions = new ArrayList<BaseOption>();
    	for(Dic dc:dict_list){
    		BaseOption option = new BaseOption();
        	option.setOptionValue(dc.getCode());
        	option.setOptionName(dc.getValue());
        	baseOptions.add(option);
    	}
    	int index = 0;
    	childsb.append("<div style='display: none'><select class='noneWorkflow' id='").append(dictName).append("'>");
        for(BaseOption option : baseOptions) {
        	childsb.append("<option value='").append(option.getOptionValue()).append("'");
        	if(selectLinkage != null) {
                if(selectLinkage.equals(option.getOptionValue())) {
                	childsb.append(" selected ");
                }
           } else  {
        	   if(index == 0) {
        		   childsb.append(" selected ");
               }
           }
        		
        	childsb.append(">").append(option.getOptionName()).append("</option>");
            index ++;
        }
        childsb.append("</select>");
        childsb.append("</div>");
    	return childsb;
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

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public String getGrouplabel() {
        return grouplabel;
    }

    public void setGrouplabel(String grouplabel) {
        this.grouplabel = grouplabel;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadValue() {
        return headValue;
    }

    public void setHeadValue(String headValue) {
        this.headValue = headValue;
    }

	public String getHeadType() {
		return headType;
	}

	public void setHeadType(String headType) {
		this.headType = headType;
	}

	public boolean isHasLinkage() {
		return isHasLinkage;
	}

	public void setIsHasLinkage(boolean isHasLinkage) {
		this.isHasLinkage = isHasLinkage;
	}

	public String getSelectLinkage() {
		return selectLinkage;
	}

	public void setSelectLinkage(String selectLinkage) {
		this.selectLinkage = selectLinkage;
	}

}
