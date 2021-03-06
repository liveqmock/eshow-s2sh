package com.logo.eshow.dao;

import java.util.List;

import com.logo.eshow.bean.query.PhotoCommentQuery;
import com.logo.eshow.common.page.Page;
import com.logo.eshow.dao.GenericDao;
import com.logo.eshow.model.PhotoComment;

/**
 * An interface that provides a data management interface to the PhotoComment
 * table.
 */
public interface PhotoCommentDao extends GenericDao<PhotoComment, Integer> {

	List<PhotoComment> list(PhotoCommentQuery query);

	Page<PhotoComment> search(PhotoCommentQuery query);
}