<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视频管理</title>
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
		<li><a href="${ctx}/zxy/vedio/">视频列表</a></li>
		<li class="active"><a href="${ctx}/zxy/vedio/form?id=${vedio.id}">视频<shiro:hasPermission name="zxy:vedio:edit">${not empty vedio.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:vedio:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="vedio" action="${ctx}/zxy/vedio/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">视频名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">视频时长：</label>
			<div class="controls">
				<form:input path="duration" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">视频地址：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="225" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">视频分类：</label>
			<div class="controls">
				<sys:treeselect id="resourseType" name="resourseType.id" value="${vedio.resourseType.id}" labelName="resourseType.name" labelValue="${vedio.resourseType.name}"
					title="分类" url="/zxy/resourseType/jsonList/0" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">视频标签：</label>
			<div class="controls">
				<form:input path="tag" htmlEscape="false" maxlength="225" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">视频状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vedio_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="zxy:vedio:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>