<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>作业管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if("${homework.resourseType.name}"==""){
				var curNode=window.parent.curNode;
				if(curNode){
					$("#resourseTypeName").val(curNode.name);
				}
			}
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
		<li class="active"><a href="${ctx}/zxy/homework/">作业列表</a></li>
		<shiro:hasPermission name="zxy:homework:edit"><li><a href="${ctx}/zxy/homework/form">作业添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="homework" action="${ctx}/zxy/homework/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>资源分类：</label>
				<sys:treeselect id="resourseType" name="resourseType.id" value="${homework.resourseType.id}" labelName="resourseType.name" labelValue="${homework.resourseType.name}"
					title="分类" url="/zxy/resourseType/jsonList/1" />
			</li>
			<li><label>作业标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>作业附件：</label>
				<form:input path="attachment" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>作业标题</th>
				<th>资源分类</th>
				<th>作业附件</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:homework:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="homework">
			<tr>
				<td><a href="${ctx}/zxy/homework/form?id=${homework.id}">
					${homework.title}
				</a></td>
				<td>
					${homework.resourseType.name }
				</td>
				<td>
					<c:choose>
						<c:when test="${homework.attachment ne null}">
							<a href="${domainFile }/homework_file/${homework.attachment}" target="_blank">下载</a>
						</c:when>
						<c:otherwise>
							<font color="red">缺少附件</font>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${homework.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:homework:edit"><td>
    				<a href="${ctx}/zxy/homework/form?id=${homework.id}">修改</a>
					<a href="${ctx}/zxy/homework/delete?id=${homework.id}" onclick="return confirmx('确认要删除该作业吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>