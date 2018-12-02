package com.jc.oa.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.archive.dao.ICommentDao;
import com.jc.oa.archive.domain.Comment;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.service.ICommentService;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-06-05
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements ICommentService{

	private ICommentDao commentDao;
	
	public CommentServiceImpl(){}
	
	@Autowired
	public CommentServiceImpl(ICommentDao commentDao){
		super(commentDao);
		this.commentDao = commentDao;
	}

}