<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测验管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if("${quiz.resourseType.name}"==""){
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
		<li class="active"><a href="${ctx}/zxy/quiz/">测验列表</a></li>
		<shiro:hasPermission name="zxy:quiz:edit"><li><a href="${ctx}/zxy/quiz/form">测验添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="quiz" action="${ctx}/zxy/quiz/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>资源分类：</label>
				<sys:treeselect id="resourseType" name="resourseType.id" value="${quiz.resourseType.id}" labelName="resourseType.name" labelValue="${quiz.resourseType.name}"
					title="分类" url="/zxy/resourseType/jsonList/2" />
			</li>
			<li><label>测验名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>测验附件：</label>
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
				<th>测验名称</th>
				<th>资源分类</th>
				<th>测验附件</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:quiz:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="quiz">
			<tr>
				<td><a href="${ctx}/zxy/quiz/form?id=${quiz.id}">
					${quiz.name}
				</a></td>
				<td>
					${quiz.resourseType.name }
				</td>
				<td>
					<c:choose>
						<c:when test="${quiz.attachment ne null}">
							<a href="${domainFile }/quiz_file/${quiz.attachment}" target="_blank">下载</a>
						</c:when>
						<c:otherwise>
							<font color="red">缺少附件</font>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${quiz.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:quiz:edit"><td>
    				<a href="${ctx}/zxy/quiz/form?id=${quiz.id}">修改</a>
					<a href="${ctx}/zxy/quiz/delete?id=${quiz.id}" onclick="return confirmx('确认要删除该测验管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>