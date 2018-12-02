/**
 * 
 */
package com.jc.system.job.example;

import java.util.Date;

import org.quartz.JobExecutionContext;

import com.jc.system.job.CustomJob;

/**
 * @author Administrator
 * 
 */

public class TestJob extends CustomJob {

	public TestJob() {
	}

	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		System.out.println("This is a example job." + new Date());
	}

}
