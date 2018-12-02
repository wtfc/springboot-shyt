package com.jc.oa.archive.domain;

import java.util.Date;
import java.math.BigDecimal;
import com.jc.system.common.util.DateUtils;
import com.jc.foundation.domain.BaseBean;


/**
 * @title  GOA2.0源代码
 * @description OA_知识管理_知识评分 实体类
 * @author 
 * @version  2014-06-05
 */

public class Score extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long toaId;   /*主键ID*/
	private Long documentId;   /*文档ID*/
	private Integer kmScore;   /*知识管理_知识评分*/
	private Long kmUserId;   /*知识管理_评分人ID*/
	private Integer weight;   /**/

	public Long getToaId(){
		return toaId;
	}
	
	public void setToaId(Long toaId){
		this.toaId = toaId;
	}
	
	public Long getDocumentId(){
		return documentId;
	}
	
	public void setDocumentId(Long documentId){
		this.documentId = documentId;
	}
	
	public Integer getKmScore(){
		return kmScore;
	}
	
	public void setKmScore(Integer kmScore){
		this.kmScore = kmScore;
	}
	
	public Long getKmUserId(){
		return kmUserId;
	}
	
	public void setKmUserId(Long kmUserId){
		this.kmUserId = kmUserId;
	}
	
	public Integer getWeight(){
		return weight;
	}
	
	public void setWeight(Integer weight){
		this.weight = weight;
	}
	
}