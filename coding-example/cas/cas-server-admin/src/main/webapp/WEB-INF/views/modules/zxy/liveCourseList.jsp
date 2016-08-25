<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直播管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/zxy/liveCourse/">直播列表</a></li>
		<shiro:hasPermission name="zxy:liveCourse:edit"><li><a href="${ctx}/zxy/liveCourse/form">直播添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="liveCourse" action="${ctx}/zxy/liveCourse/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>课程名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>课程类型：</label>
				<form:select path="lciKind" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('live_course_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属专业：</label>
				<form:select path="majorId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${zxy:getMajorList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>主讲企业：</label>
				<form:input path="company" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>主讲人：</label>
				<form:input path="lecturer" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>课程名称</th>
				<th>课程类型</th>
				<th>所属专业</th>
				<th>主讲企业</th>
				<th>主讲人</th>
				<th>观看人数</th>
				<th>助教口令</th>
				<th>老师口令</th>
				<th>预期开始时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:liveCourse:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="liveCourse">
			<tr>
				<td><a href="${ctx}/zxy/liveCourse/form?id=${liveCourse.id}">
					${liveCourse.name}
				</a></td>
				<td>
					${fns:getDictLabel(liveCourse.lciKind, 'live_course_type', '')}
				</td>
				<td>
					${zxy:getMajor(liveCourse.majorId).name}
				</td>
				<td>
					${liveCourse.company}
				</td>
				<td>
					${liveCourse.lecturer}
				</td>
				<td>
					${liveCourse.viewcnt}
				</td>
				<td>
					${liveCourse.assistanttoken}
				</td>
				<td>
					${liveCourse.teachertoken}
				</td>
				<td>
					<fmt:formatDate value="${liveCourse.startdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${liveCourse.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:liveCourse:edit"><td>
    				<a href="${ctx}/zxy/liveCourse/form?id=${liveCourse.id}">修改</a>
					<a href="${ctx}/zxy/liveCourse/delete?id=${liveCourse.id}" onclick="return confirmx('确认要删除该直播吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>