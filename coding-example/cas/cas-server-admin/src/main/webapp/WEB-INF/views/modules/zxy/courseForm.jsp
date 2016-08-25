<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
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
			
			//为老师列表赋值
			$("#teachers").select2("val","${course.teachers}".split(","));
			
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
			<li><a href="${ctx}/zxy/course/">课程列表</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/zxy/course/form?id=${course.id}">课程<shiro:hasPermission name="zxy:course:edit">${not empty course.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:course:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="course" action="${ctx}/zxy/course/save?opType=${param.opType }" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">课程名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="63" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">录播课程数：</label>
			<div class="controls">
				<form:input path="lessoncnt" htmlEscape="false" maxlength="3" class="input-xlarge digits" value="${(course.lessoncnt eq null)?0:course.lessoncnt}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">录播课时数：</label>
			<div class="controls">
				<form:input path="lessontime" htmlEscape="false" maxlength="3" class="input-xlarge digits" value="${(course.lessontime eq null)?0:course.lessontime}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直播课程数：</label>
			<div class="controls">
				<form:input path="livecourseCnt" htmlEscape="false" maxlength="3" class="input-xlarge digits" value="${(course.livecourseCnt eq null)?0:course.livecourseCnt}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业数：</label>
			<div class="controls">
				<form:input path="homeworkCnt" htmlEscape="false" maxlength="3" class="input-xlarge digits" value="${(course.homeworkCnt eq null)?0:course.homeworkCnt}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主讲老师：</label>
			<div class="controls">
				<form:select path="teachers" class="input-xlarge " multiple="true">
					<form:options items="${teacherList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
		<div class="form-actions" style="<c:if test="${not empty param.opType}">margin-bottom: 38px!important</c:if>">
			<shiro:hasPermission name="zxy:course:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>