<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业管理管理</title>
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
		<li class="active"><a href="${ctx}/zxy/major/">专业列表</a></li>
		<shiro:hasPermission name="zxy:major:edit"><li><a href="${ctx}/zxy/major/form">专业添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="major" action="${ctx}/zxy/major/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>专业名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>专业状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('major_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>专业名称</th>
				<th>关卡数</th>
				<th>课时数</th>
				<th>专业直播课程数</th>
				<th>作业数</th>
				<th>专业类型</th>
				<th>专业版本</th>
				<th>专业状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:major:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="major">
			<tr>
				<td><a href="${ctx}/zxy/major/form?id=${major.id}">
					${major.name}
				</a></td>
				<td>
					${major.lockCnt}
				</td>
				<td>
					${major.lessonCnt}
				</td>
				<td>
					${major.livecourseCnt}
				</td>
				<td>
					${major.homeworkCnt}
				</td>
				<td>
					${fns:getDictLabel(major.kind, 'major_type', '')}
				</td>
				<td>
					${major.version}
				</td>
				<td>
					${fns:getDictLabel(major.status, 'major_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${major.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:major:edit"><td>
    				<a href="${ctx}/zxy/major/form?id=${major.id}">修改</a>
    				<a href="${ctx}/zxy/major/copy?id=${major.id}">复制</a>
					<a href="${ctx}/zxy/major/delete?id=${major.id}" onclick="return confirmx('确认要删除该专业管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>