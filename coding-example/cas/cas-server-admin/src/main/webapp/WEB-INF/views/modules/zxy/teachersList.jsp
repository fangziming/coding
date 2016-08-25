<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>老师管理</title>
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
		<li class="active"><a href="${ctx}/zxy/teachers/">老师列表</a></li>
		<shiro:hasPermission name="zxy:teachers:edit"><li><a href="${ctx}/zxy/teachers/form">老师添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="teachers" action="${ctx}/zxy/teachers/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登录账号：</label>
				<form:input path="user.mobile" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('teacher_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>登录账号</th>
				<th>姓名</th>
				<th>头衔</th>
				<th>类型</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:teachers:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="teachers">
			<tr>
				<td>
					${teachers.user.mobile}
				</td>
				<td><a href="${ctx}/zxy/teachers/form?id=${teachers.id}">
					${teachers.name}
				</a></td>
				<td>
					${teachers.title}
				</td>
				<td>
					${fns:getDictLabel(teachers.type, 'teacher_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${teachers.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:teachers:edit"><td>
    				<a href="${ctx}/zxy/teachers/form?id=${teachers.id}">修改</a>
					<a href="${ctx}/zxy/teachers/delete?id=${teachers.id}" onclick="return confirmx('确认要删除该老师吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>