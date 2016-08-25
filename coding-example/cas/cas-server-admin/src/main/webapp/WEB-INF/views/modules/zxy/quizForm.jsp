<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测验管理管理</title>
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
		<li><a id="lx" href="${ctx}/zxy/quiz/">测验列表</a></li>
		<li class="active"><a href="${ctx}/zxy/quiz/form?id=${quiz.id}">测验<shiro:hasPermission name="zxy:quiz:edit">${not empty quiz.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:quiz:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="quiz" action="${ctx}/zxy/quiz/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">资源分类：</label>
			<div class="controls">
				<sys:treeselect id="resourseType" name="resourseType.id" value="${quiz.resourseType.id}" labelName="resourseType.name" labelValue="${quiz.resourseType.name}"
					title="分类" url="/zxy/resourseType/jsonList/2" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测验名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测验附件：</label>
			<div class="controls">
				<form:hidden id="attachment" path="attachment" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="attachment" type="files" uploadPath="/quiz_file" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="zxy:quiz:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>