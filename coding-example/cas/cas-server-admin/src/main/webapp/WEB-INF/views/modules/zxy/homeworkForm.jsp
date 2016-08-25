<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>作业管理</title>
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
			var curNode=window.parent.curNode;
			if(curNode){
				$("#resourseTypeId").val(curNode.id);
				$("#resourseTypeName").val(curNode.name);
				$("#lx").attr("href",$("#lx").attr("href")+"?resourseType.id="+curNode.id);
			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a id="lx" href="${ctx}/zxy/homework/">作业列表</a></li>
		<li class="active"><a href="${ctx}/zxy/homework/form?id=${homework.id}">作业<shiro:hasPermission name="zxy:homework:edit">${not empty homework.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:homework:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="homework" action="${ctx}/zxy/homework/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">资源分类：</label>
			<div class="controls">
				<sys:treeselect id="resourseType" name="resourseType.id" value="${homework.resourseType.id}" labelName="resourseType.name" labelValue="${homework.resourseType.name}"
					title="分类" url="/zxy/resourseType/jsonList/1" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业附件：</label>
			<div class="controls">
				<form:hidden id="attachment" path="attachment" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="attachment" type="files" uploadPath="/homework_file" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="zxy:homework:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>