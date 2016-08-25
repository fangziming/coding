<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关卡管理</title>
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
		<li class="active"><a href="${ctx}/zxy/locks/">关卡列表</a></li>
		<shiro:hasPermission name="zxy:locks:edit"><li><a href="${ctx}/zxy/locks/form">关卡添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="locks" action="${ctx}/zxy/locks/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>关卡名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>所属专业：</label>
				<form:select path="majorId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${zxy:getMajorList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属课程：</label>
				<form:select path="course.id" class="input-medium">
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
				<th>关卡名称</th>
				<th>所属专业</th>
				<th>所属课程</th>
				<th>关卡位置</th>
				<th>是否设有奖学金</th>
				<th>关卡测验</th>
				<th>通关人数</th>
				<th>最早开启时间</th>
				<th>极速闯关天数</th>
				<th>极速闯关奖学金</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:locks:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="locks">
			<tr>
				<td><a href="${ctx}/zxy/locks/form?id=${locks.id}">
					${locks.name}
				</a></td>
				<td>
					${zxy:getMajor(locks.majorId).name}
				</td>
				<td>
					${zxy:getCourse(locks.course.id).name}
				</td>
				<td>
					${locks.position}
				</td>
				<td>
					${fns:getDictLabel(locks.isreward, 'yes_no', '')}
				</td>
				<td>
					${zxy:getQuiz(locks.quizId).name}
				</td>
				<td>
					${locks.passcnt}
				</td>
				<td>
					<fmt:formatDate value="${locks.startdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${locks.topspeedDays}
				</td>
				<td>
					${locks.topspeedScholarship}
				</td>
				<td>
					<fmt:formatDate value="${locks.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:locks:edit"><td>
    				<a href="${ctx}/zxy/locks/form?id=${locks.id}">修改</a>
					<a href="${ctx}/zxy/locks/delete?id=${locks.id}" onclick="return confirmx('确认要删除该关卡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>