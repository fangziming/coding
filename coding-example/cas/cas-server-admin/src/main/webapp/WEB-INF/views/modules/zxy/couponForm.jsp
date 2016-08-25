<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/zxy/coupon/">优惠券列表</a></li>
		<li class="active"><a href="${ctx}/zxy/coupon/form?id=${coupon.id}">优惠券<c:choose><c:when test="${empty coupon.id }"><shiro:hasPermission name="zxy:coupon:edit">添加</shiro:hasPermission></c:when><c:otherwise><shiro:hasPermission name="zxy:coupon:view">查看</shiro:hasPermission></c:otherwise></c:choose></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="coupon" action="${ctx}/zxy/coupon/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<c:if test="${coupon.id ne null}">
			<div class="control-group">
				<label class="control-label">兑换码：</label>
				<div class="controls">
					<form:input path="code" htmlEscape="false" maxlength="25" class="input-xlarge required" readonly="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" maxlength="4" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到期时间：</label>
			<div class="controls">
				<input name="expiredtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${coupon.expiredtime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${coupon.id ne null}">
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					<form:select path="status" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('coupon_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">优惠券类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('coupon_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠券渠道：</label>
			<div class="controls">
				<form:select path="channel" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('coupon_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${coupon.id eq null}">
			<div class="control-group">
				<label class="control-label">批量生成：</label>
				<div class="controls">
					<form:input path="batch" htmlEscape="false" maxlength="4" class="input-xlarge digits"/>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${empty coupon.id }">
				<shiro:hasPermission name="zxy:coupon:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>