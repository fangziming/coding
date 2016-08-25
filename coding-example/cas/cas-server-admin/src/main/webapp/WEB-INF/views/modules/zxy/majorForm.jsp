<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业管理管理</title>
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
		<li><a href="${ctx}/zxy/major/">专业列表</a></li>
		<li class="active"><a href="${ctx}/zxy/major/form?id=${major.id}">专业<shiro:hasPermission name="zxy:major:edit">${not empty major.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:major:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="major" action="${ctx}/zxy/major/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属学院：</label>
			<div class="controls">
				<form:select path="college" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('major_college')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业Logo：</label>
			<div class="controls">
				<form:hidden id="logo" path="logo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="logo" type="images" uploadPath="/major_file" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详情页：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关卡数：</label>
			<div class="controls">
				<form:input path="lockCnt" htmlEscape="false" maxlength="3" class="input-xlarge required" digits="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课时数：</label>
			<div class="controls">
				<form:input path="lessonCnt" htmlEscape="false" maxlength="4" class="input-xlarge required" digits="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业直播课程数：</label>
			<div class="controls">
				<form:input path="livecourseCnt" htmlEscape="false" maxlength="3" class="input-xlarge required" digits="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业数：</label>
			<div class="controls">
				<form:input path="homeworkCnt" htmlEscape="false" maxlength="3" class="input-xlarge required" digits="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业类型：</label>
			<div class="controls">
				<form:select path="kind" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('major_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('major_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="zxy:major:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>