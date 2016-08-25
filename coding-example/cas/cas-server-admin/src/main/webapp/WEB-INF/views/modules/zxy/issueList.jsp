<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>期次管理</title>
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
		<li class="active"><a href="${ctx}/zxy/issue/">期次列表</a></li>
		<shiro:hasPermission name="zxy:issue:edit">
			<li><a href="${ctx}/zxy/issue/form">期次添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="issue" action="${ctx}/zxy/issue/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属专业：</label>
				<form:select path="majorId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${zxy:getMajorList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>期次名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('issue_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>期次名称</th>
				<th>所属专业</th>
				<th>期次号</th>
				<th>本期专业售价</th>
				<th>购买部分关卡数</th>
				<th>本期专业首付</th>
				<th>预售数量</th>
				<th>已售数量</th>
				<th>状态</th>
				<th>开班时间</th>
				<th>本期QQ群号</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:issue:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="issue">
			<tr>
				<td><a href="${ctx}/zxy/issue/form?id=${issue.id}">
					${issue.name}
				</a></td>
				<td>
					${zxy:getMajor(issue.majorId).name}
				</td>
				<td>
					${issue.num}
				</td>
				<td>
					${issue.price}
				</td>
				<td>
					${issue.locknumdownpayment}
				</td>
				<td>
					${issue.downpayment}
				</td>
				<td>
					${issue.sellcntprepared}
				</td>
				<td>
					${issue.soldcnt}
				</td>
				<td>
					${fns:getDictLabel(issue.status, 'issue_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${issue.startdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${issue.qq}
				</td>
				<td>
					<fmt:formatDate value="${issue.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:issue:edit"><td>
    				<a href="${ctx}/zxy/issue/index?id=${issue.id}">设计</a>
    				<!-- <a href="${ctx}/zxy/issue/form?id=${issue.id}">修改</a> -->
					<a href="${ctx}/zxy/issue/delete?id=${issue.id}" onclick="return confirmx('确认要删除该期次吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>