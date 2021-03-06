package com.logo.eshow.webapp.action;

import com.logo.eshow.bean.query.TopicCommentQuery;
import com.logo.eshow.bean.query.TopicQuery;
import com.logo.eshow.common.page.Page;
import com.logo.eshow.model.Topic;
import com.logo.eshow.model.TopicComment;
import com.logo.eshow.service.TopicCommentManager;
import com.logo.eshow.service.TopicManager;
import com.logo.eshow.util.CommonUtil;
import com.logo.eshow.util.PageUtil;
import com.logo.eshow.webapp.action.BaseAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

@Results({ @Result(name = "input", location = "add"),
		@Result(name = "list", type = "redirect", location = ""),
		@Result(name = "success", type = "redirect", location = "view/${id}"),
		@Result(name = "redirect", type = "redirect", location = "${redirect}") })
public class TopicCommentAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TopicCommentManager topicCommentManager;
	@Autowired
	private TopicManager topicManager;
	private List<TopicComment> topicComments;
	private TopicComment topicComment;
	private TopicCommentQuery query;
	private TopicQuery topicQuery;
	private Integer topicId;
	private Integer boardId;

	public String list() {
		topicComments = topicCommentManager.list(query);
		return LIST;
	}

	public String search() {
		Page<TopicComment> page = topicCommentManager.search(query);
		topicComments = page.getDataList();
		saveRequest("page", PageUtil.conversion(page));
		return LIST;
	}

	/**
	 * 我参与的话题
	 * 
	 * @return
	 */
	public String searchReply() {
		List<Topic> topics = new ArrayList<Topic>();
		List<TopicComment> list = new ArrayList<TopicComment>();// 存放查找出来的回复
		Page<TopicComment> page = null;
		topicQuery = new TopicQuery();
		topicQuery.setBoardId(boardId);
		topics = topicManager.list(topicQuery);
		for (Topic tc : topics) {
			query.setTopicId(tc.getId());
			page = topicCommentManager.search(query);
			topicComments = page.getDataList();
			if (!topicComments.isEmpty()) { // 去掉同一话题的多次回复，取一个
				list.add(topicComments.get(0));
			}
		}
		topicComments = list;
		saveRequest("page", PageUtil.conversion(page));
		return LIST;
	}

	public String delete() {
		TopicComment old = topicCommentManager.get(id);
		if (old.getUser().getId().equals(getSessionUser().getId())) {
			Topic topic = old.getTopic();
			topic.setCommentSize(topic.getCommentSize() - 1);
			topicManager.save(topic);
			topicCommentManager.remove(id);
			saveMessage("删除成功");
		} else {
			saveMessage("无权删除");
		}
		return LIST;
	}

	public String view() {
		if (id != null) {
			topicComment = topicCommentManager.get(id);
		} else {
			return INDEX;
		}
		return NONE;
	}

	public String update() throws Exception {
		TopicComment old = topicCommentManager.get(id);
		old.setName(topicComment.getName());
		old.setContent(topicComment.getContent());
		topicCommentManager.save(old);
		saveMessage("修改成功");
		return SUCCESS;
	}

	public String save() throws Exception {
		topicComment.setAddTime(new Date());
		topicComment.setUser(getSessionUser());
		Topic topic = topicManager.get(topicId);
		topic.setCommentSize(CommonUtil.count(topic.getCommentSize()));
		topicManager.save(topic);
		topicComment.setTopic(topic);
		topicComment = topicCommentManager.save(topicComment);
		id = topicComment.getTopic().getId();
		saveMessage("添加成功");
		return SUCCESS;
	}

	public TopicCommentManager getTopicCommentManager() {
		return topicCommentManager;
	}

	public void setTopicCommentManager(TopicCommentManager topicCommentManager) {
		this.topicCommentManager = topicCommentManager;
	}

	public TopicManager getTopicManager() {
		return topicManager;
	}

	public void setTopicManager(TopicManager topicManager) {
		this.topicManager = topicManager;
	}

	public List<TopicComment> getTopicComments() {
		return topicComments;
	}

	public void setTopicComments(List<TopicComment> topicComments) {
		this.topicComments = topicComments;
	}

	public TopicComment getTopicComment() {
		return topicComment;
	}

	public void setTopicComment(TopicComment topicComment) {
		this.topicComment = topicComment;
	}

	public TopicCommentQuery getQuery() {
		return query;
	}

	public void setQuery(TopicCommentQuery query) {
		this.query = query;
	}

	public TopicQuery getTopicQuery() {
		return topicQuery;
	}

	public void setTopicQuery(TopicQuery topicQuery) {
		this.topicQuery = topicQuery;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getBoardId() {
		return boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

}