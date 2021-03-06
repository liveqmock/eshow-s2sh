<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="myid">
	<authz:authentication operation='id' />
</c:set>
<%@ taglib uri="/WEB-INF/stringutil.tld" prefix="util"%>
<head>
	<title>日志</title>
	<link rel="stylesheet" href="<c:url value='/admin/styles/blog.css'/>"
		type="text/css" />
</head>
<body>
	<div class="container mt">
		<div class="row-fluid">
			<s:include value="../left.jsp"></s:include>
			<div class="span10">
				<ul class="breadcrumb">
					<li><a href="${ctx}/admin/index">首页</a> <span class="divider">/</span>
					</li>
					<li><a href="${ctx}/admin/blog/">日志管理</a> <span
						class="divider">/</span>
					</li>
					<li class="active">日志列表</li>
				</ul>
				<div class="well com">
					<div class="page-header">
						<div class="pull-right">
							<a href="<c:url value='/admin/blog/add'/>" class="btn btn-primary">添加</a>
						</div>
						<h3 class="yahei">日志列表</h3>
					</div>
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="<c:url value='/admin/blog'/>">日志列表</a></li>
						<li><a data-toggle="tab" href="<c:url value='/admin/blog/mine'/>">我的日志</a></li>
						<li><a data-toggle="tab" href="<c:url value='/admin/blog/workmate'/>">同事的日志</a></li>
					</ul>
						<div id="blog">
							<s:action name="blog!search" id="newsBlogList"
								executeResult="false">
								<s:param name="query.order">addTime</s:param>
								<s:param name="query.desc">true</s:param>
								<s:param name="query.pagesize">9</s:param>
							</s:action>
						<ul class="flist">
							<s:iterator value="%{#newsBlogList.blogs}" status="rowStatus">
								<li id="blog${id}"
									onmouseover='this.style.backgroundColor ="#f9f9f9"'
									onmouseout='this.style.backgroundColor =""'>
									<div class="avatar">
										<a href="<c:url value="/admin/user/view/${user.id}"/>"> <c:if
												test="${user.photo==null}">
												<img
													src="${pageContext.request.contextPath}/images/base/user50-50.jpg"
													alt="${user.nickname}" width="50" height="50" />
											</c:if> <c:if test="${user.photo!=null}">
												<img
													src="${pageContext.request.contextPath}/upload/user/<s:date name='%{user.addTime}' format='yyyyMMdd' />/${user.photo}"
													alt="${user.nickname}" width="50" height="50" />
											</c:if> </a>
									</div>
									<div class="name">
										<a href="<c:url value='/user/view/${user.id}'/>">${user.nickname}</a>
									</div>
									<div class="cont">
										<div class="tips">
											评论(${commentSize
											})&nbsp;&nbsp;|&nbsp;&nbsp;浏览(${count})&nbsp;&nbsp;|&nbsp;&nbsp;
											<c:if test="true">
												<span> <a
													href="<c:url value='/admin/blog/edit/${id}'/>">修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;
													<a href="${ctx }/blog!delete.action?id=${id}"
													onclick="return deleteData('blog',${id});">删除</a>&nbsp;&nbsp;
												</span>
											</c:if>
										</div>
										<div class="title">
											<a href="<c:url value='/admin/blog/view/${id}'/>">${title}</a>&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
										<div class="time">
											<c:if test="${category.id==null}">[默认分类] </c:if>
											<c:if test="${category.id!=null}">
												<a href="<c:url value="/admin/blog/c/${category.id}"/>">[${category.name}]</a>
											</c:if>
											&nbsp;&nbsp;|&nbsp;&nbsp;发表于
											<s:date name='%{addTime}' format='yyyy-MM-dd HH:mm:ss' />
										</div>
										<div class="contxt">
											${util:preview(content,100)}
											<p class="r">
												<a href="<c:url value='/admin/blog/view/${id}'/>">查看全文</a>
											</p>
										</div>
									</div>
									<div class="c"></div></li>
							</s:iterator>
							<%@ include file="/common/page.jsp"%>
						</ul>
					</div>
					</div>
				</div>

			</div>
		</div>
</body>

