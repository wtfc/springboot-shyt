/**
 * 
 */
package com.jc.oa.common.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.jc.system.job.CustomJob;
import com.jc.system.security.domain.User;

/**
 * @title GOA V2.0
 * @description 
 * @author 孙鹏
 * @version 1.0 2014年12月22日
 */
public class WorkflowQueryJob extends CustomJob{

	
	public static Map<String,Map<Integer,Integer>> workflowQueryMap = new HashMap<String,Map<Integer,Integer>>();
	
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		// TODO Auto-generated method stub
	}
	public static Integer getCountWithUserAndType(User user,Integer type){
		return 0;
	}
	
	public Integer getCount(String count){
		if(count != null){
			return Integer.parseInt(count);
		}else{
			return 0;
		}
	}

}
