<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
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
		<li class="active"><a href="${ctx}/zxy/userInfo/">学生列表</a></li>
		<shiro:hasPermission name="zxy:userInfo:edit"><li><a href="${ctx}/zxy/userInfo/form">学生添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/zxy/userInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>真实姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="identityNumber" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="tel" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>所在地区：</label>
				<form:select path="area" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>目前状态：</label>
				<form:select path="currentstatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('user_currentstatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>真实姓名</th>
				<th>性别</th>
				<th>身份证号</th>
				<th>专业</th>
				<th>最高学历</th>
				<th>联系电话</th>
				<th>手机校验</th>
				<th>邮箱</th>
				<th>邮箱校验</th>
				<th>所在地区</th>
				<th>目前状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:userInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userInfo">
			<tr>
				<td>
					${(userInfo.telchecked eq '1' && userInfo.tel ne '')?userInfo.tel:'-'}/${(userInfo.emailchecked eq '1' && userInfo.email ne '')?userInfo.email:'-'}
				</td>
				<td><a href="${ctx}/zxy/userInfo/form?id=${userInfo.id}">
					${userInfo.name}
				</a></td>
				<td>
					${fns:getDictLabel(userInfo.sex, 'sex', '')}
				</td>
				<td>
					${userInfo.identityNumber}
				</td>
				<td>
					${fns:getDictLabel(userInfo.eduMajor, 'edu_major_type', '')}
				</td>
				<td>
					${fns:getDictLabel(userInfo.highEducation, 'high_education_type', '')}
				</td>
				<td>
					${userInfo.tel}
				</td>
				<td>
					${userInfo.tel ne ''?fns:getDictLabel(userInfo.telchecked, 'yes_no', ''):''}
				</td>
				<td>
					${userInfo.email}
				</td>
				<td>
					${userInfo.email ne ''?fns:getDictLabel(userInfo.emailchecked, 'yes_no', ''):''}
				</td>
				<td>
					${fns:getDictLabel(userInfo.area, 'area', '')}
				</td>
				<td>
					${fns:getDictLabel(userInfo.currentstatus, 'user_currentstatus', '')}
				</td>
				<td>
					<fmt:formatDate value="${userInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:userInfo:edit"><td>
    				<a href="${ctx}/zxy/userInfo/form?id=${userInfo.id}">修改</a>
					<a href="${ctx}/zxy/userInfo/delete?id=${userInfo.id}" onclick="return confirmx('确认要删除该学生吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>