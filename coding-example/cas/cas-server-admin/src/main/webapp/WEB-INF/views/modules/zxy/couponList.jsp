<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券管理</title>
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
		<li class="active"><a href="${ctx}/zxy/coupon/">优惠券列表</a></li>
		<shiro:hasPermission name="zxy:coupon:edit"><li><a href="${ctx}/zxy/coupon/form">优惠券添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="coupon" action="${ctx}/zxy/coupon/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>兑换码：</label>
				<form:input path="code" htmlEscape="false" maxlength="25" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('coupon_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>优惠券类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('coupon_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>优惠券渠道：</label>
				<form:select path="channel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('coupon_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>兑换码</th>
				<th>价格</th>
				<th>到期时间</th>
				<th>使用时间</th>
				<th>状态</th>
				<th>优惠券类型</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:coupon:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="coupon">
			<tr>
				<td><a href="${ctx}/zxy/coupon/form?id=${coupon.id}">
					${coupon.code}
				</a></td>
				<td>
					${coupon.price}
				</td>
				<td>
					<fmt:formatDate value="${coupon.expiredtime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					<fmt:formatDate value="${coupon.usedtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(coupon.status, 'coupon_status', '')}
				</td>
				<td>
					${fns:getDictLabel(coupon.type, 'coupon_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${coupon.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
    				<shiro:hasPermission name="zxy:coupon:view"><a href="${ctx}/zxy/coupon/form?id=${coupon.id}">查看</a></shiro:hasPermission>
					<c:if test="${coupon.status eq '0'}"><shiro:hasPermission name="zxy:coupon:edit"><a href="${ctx}/zxy/coupon/delete?id=${coupon.id}" onclick="return confirmx('确认要删除该优惠券吗？', this.href)">删除</a></shiro:hasPermission></c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>