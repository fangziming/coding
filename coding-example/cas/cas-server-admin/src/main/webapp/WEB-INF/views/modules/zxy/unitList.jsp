<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>章节管理</title>
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
		<li class="active"><a href="${ctx}/zxy/unit/">章节列表</a></li>
		<shiro:hasPermission name="zxy:unit:edit"><li><a href="${ctx}/zxy/unit/form">章节添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="unit" action="${ctx}/zxy/unit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>章节名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>所属课程：</label>
				<form:select path="courseId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${zxy:getCourseList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
				<th>章节名称</th>
				<th>序号</th>
				<th>所属课程</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:unit:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="unit">
			<tr>
				<td>
					<a href="${ctx}/zxy/unit/form?id=${unit.id}">${unit.name}</a>
				</td>
				<td>
					${unit.sort}
				</td>
				<td>
					${zxy:getCourse(unit.courseId).name}
				</td>
				<td>
					<fmt:formatDate value="${unit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:unit:edit"><td>
    				<a href="${ctx}/zxy/unit/form?id=${unit.id}">修改</a>
					<a href="${ctx}/zxy/unit/delete?id=${unit.id}" onclick="return confirmx('确认要删除该章节吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>