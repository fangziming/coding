<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级管理</title>
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
		<li class="active"><a href="${ctx}/zxy/issueClasses/">班级列表</a></li>
		<shiro:hasPermission name="zxy:issueClasses:edit"><li><a href="${ctx}/zxy/issueClasses/form">班级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="issueClasses" action="${ctx}/zxy/issueClasses/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>班级名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>所属期次：</label>
				<form:select path="issueId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${zxy:getIssueList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>班级名称</th>
				<th>所属期次</th>
				<th>开班时间</th>
				<th>结班时间</th>
				<th>预期班级人数</th>
				<th>当前班级人数</th>
				<th>当前进度</th>
				<th>班级学生</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:issueClasses:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="issueClasses">
			<tr>
				<td><a href="${ctx}/zxy/issueClasses/form?id=${issueClasses.id}">
					${issueClasses.name}
				</a></td>
				<td>
					${zxy:getIssue(issueClasses.issueId).name}
				</td>
				<td>
					<fmt:formatDate value="${issueClasses.startdate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${issueClasses.enddate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${issueClasses.studentnum}
				</td>
				<td>
					${issueClasses.presentstunum}
				</td>
				<td>
					${issueClasses.stuRate}
				</td>
				<td>
					<a href="${ctx}/zxy/wclassStudents?wclass.issueId=${issueClasses.issueId}&wclass.id=${issueClasses.id}">现有学生</a>
				</td>
				<td>
					<fmt:formatDate value="${issueClasses.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:issueClasses:edit"><td>
    				<a href="${ctx}/zxy/issueClasses/form?id=${issueClasses.id}">修改</a>
					<a href="${ctx}/zxy/issueClasses/delete?id=${issueClasses.id}" onclick="return confirmx('确认要删除该班级吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>