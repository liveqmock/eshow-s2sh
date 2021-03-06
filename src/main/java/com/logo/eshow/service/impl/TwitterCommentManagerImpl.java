package com.logo.eshow.service.impl;

import java.util.List;

import com.logo.eshow.bean.query.TwitterCommentQuery;
import com.logo.eshow.common.page.Page;
import com.logo.eshow.dao.TwitterCommentDao;
import com.logo.eshow.model.TwitterComment;
import com.logo.eshow.service.TwitterCommentManager;
import com.logo.eshow.service.impl.GenericManagerImpl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@WebService(serviceName = "TwitterCommentService", endpointInterface = "com.logo.eshow.service.TwitterCommentManager")
@Service
public class TwitterCommentManagerImpl extends GenericManagerImpl<TwitterComment, Integer>
		implements TwitterCommentManager {
	@Autowired
	TwitterCommentDao twitterCommentDao;

	@Autowired
	public TwitterCommentManagerImpl(TwitterCommentDao twitterCommentDao) {
		super(twitterCommentDao);
		this.twitterCommentDao = twitterCommentDao;
	}

	public List<TwitterComment> list(TwitterCommentQuery query) {
		return twitterCommentDao.list(query);
	}

	public Page<TwitterComment> search(TwitterCommentQuery query) {
		return twitterCommentDao.search(query);
	}

}