<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>章节管理</title>
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
			
			//期次设计时，初始化课程信息
			if("${param.cid}"!=""){
				$("#courseId").select2("val","${param.cid}");
			}
			
			//期次设计界面，更新后刷新左侧菜单
			if("${refresh}"=="0"){
				window.parent.refreshTree();
			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${empty param.opType}">
			<li><a href="${ctx}/zxy/unit/">章节列表</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/zxy/unit/form?id=${unit.id}">章节<shiro:hasPermission name="zxy:unit:edit">${not empty unit.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:unit:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="unit" action="${ctx}/zxy/unit/save?opType=${param.opType }" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">章节名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属课程：</label>
			<div class="controls">
				<form:select path="courseId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${zxy:getCourseList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="3" class="input-xlarge required" digits="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions" style="<c:if test="${not empty param.opType}">margin-bottom: 38px!important</c:if>">
			<shiro:hasPermission name="zxy:unit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>