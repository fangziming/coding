<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
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
		<li class="active"><a href="${ctx}/zxy/course/">课程列表</a></li>
		<shiro:hasPermission name="zxy:course:edit"><li><a href="${ctx}/zxy/course/form">课程添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="course" action="${ctx}/zxy/course/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>课程名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="63" class="input-medium"/>
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
				<th>录播课程数</th>
				<th>录播课时数</th>
				<th>直播课程数</th>
				<th>作业数</th>
				<th>课程版本</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:course:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="course">
			<tr>
				<td><a href="${ctx}/zxy/course/index?id=${course.id}">
					${course.name}
				</a></td>
				<td>
					${course.lessoncnt}
				</td>
				<td>
					${course.lessontime}
				</td>
				<td>
					${course.livecourseCnt}
				</td>
				<td>
					${course.homeworkCnt}
				</td>
				<td>
					${course.version}
				</td>
				<td>
					<fmt:formatDate value="${course.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:course:edit"><td>
    				<a href="${ctx}/zxy/course/index?id=${course.id}">设计</a>
    				<a href="${ctx}/zxy/course/copy?id=${course.id}" onclick="return confirmx('确认要复制该课程吗？', this.href)">复制</a>
					<a href="${ctx}/zxy/course/delete?id=${course.id}" onclick="return confirmx('确认要删除该课程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>