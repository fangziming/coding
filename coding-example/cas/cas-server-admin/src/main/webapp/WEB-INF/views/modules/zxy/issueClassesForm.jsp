<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级管理</title>
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
			
			//为导学老师列表赋值
			$("#teacherStr").select2("val","${issueClasses.teacherStr}".split(","));
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/zxy/issueClasses/">班级列表</a></li>
		<li class="active"><a href="${ctx}/zxy/issueClasses/form?id=${issueClasses.id}">班级<shiro:hasPermission name="zxy:issueClasses:edit">${not empty issueClasses.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:issueClasses:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="issueClasses" action="${ctx}/zxy/issueClasses/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">班级名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属期次：</label>
			<div class="controls">
				<form:select path="issueId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${zxy:getIssueList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开班时间：</label>
			<div class="controls">
				<input name="startdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${issueClasses.startdate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结班时间：</label>
			<div class="controls">
				<input name="enddate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${issueClasses.enddate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预期班级人数：</label>
			<div class="controls">
				<form:input path="studentnum" htmlEscape="false" maxlength="5" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前班级人数：</label>
			<div class="controls">
				<form:input path="presentstunum" htmlEscape="false" maxlength="5" class="input-xlarge required digits" readonly="true" value="${(issueClasses.presentstunum eq null)?0:issueClasses.presentstunum}"/>
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前进度：</label>
			<div class="controls">
				<form:input path="stuRate" htmlEscape="false" class="input-xlarge required" readonly="true" value="${issueClasses.presentstunum/issueClasses.studentnum }"/>
				<span class="help-inline">%</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">辅导老师：</label>
			<div class="controls">
				<form:select path="teacherStr" class="input-xlarge required" multiple="true">
					<form:options items="${fdTeacher}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="zxy:issueClasses:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>