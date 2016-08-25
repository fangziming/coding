<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>直播管理</title>
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
		<li><a href="${ctx}/zxy/liveCourse/">直播列表</a></li>
		<li class="active"><a href="${ctx}/zxy/liveCourse/form?id=${liveCourse.id}">直播<shiro:hasPermission name="zxy:liveCourse:edit">${not empty liveCourse.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:liveCourse:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="liveCourse" action="${ctx}/zxy/liveCourse/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">课程名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程类型：</label>
			<div class="controls">
				<form:select path="lciKind" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('live_course_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属专业：</label>
			<div class="controls">
				<form:select path="majorId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${zxy:getMajorList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程介绍：</label>
			<div class="controls">
				<form:textarea path="introduce" htmlEscape="false" rows="4" maxlength="512" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主讲企业：</label>
			<div class="controls">
				<form:input path="company" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主讲人：</label>
			<div class="controls">
				<form:input path="lecturer" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">讲师头像：</label>
			<div class="controls">
				<form:hidden id="lecturerpicUrl" path="lecturerpicUrl" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="lecturerpicUrl" type="images" uploadPath="/livecourse_file" selectMultiple="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">讲师介绍：</label>
			<div class="controls">
				<form:textarea path="lecturerIntroduce" htmlEscape="false" rows="4" maxlength="512" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直播课轮播封面：</label>
			<div class="controls">
				<form:hidden id="videobigpicUrl" path="videobigpicUrl" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="videobigpicUrl" type="images" uploadPath="/livecourse_file" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预期开始时间：</label>
			<div class="controls">
				<input name="startdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${liveCourse.startdate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">观看人数：</label>
			<div class="controls">
				<form:input path="viewcnt" htmlEscape="false" maxlength="11" class="input-xlarge required" digits="true" value="${(liveCourse.viewcnt eq null)?0:liveCourse.viewcnt }" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直播课录播文件：</label>
			<div class="controls">
				<form:input path="vedioUrl" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直播课录播字幕：</label>
			<div class="controls">
				<form:input path="videosrtUrl" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直播课录播封面：</label>
			<div class="controls">
				<form:input path="videopicUrl" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课堂SDK编号：</label>
			<div class="controls">
				<form:input path="sdkId" htmlEscape="false" maxlength="16" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课堂编号：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="16" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">助教口令：</label>
			<div class="controls">
				<form:input path="assistanttoken" htmlEscape="false" maxlength="16" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学生web端口令：</label>
			<div class="controls">
				<form:input path="studenttoken" htmlEscape="false" maxlength="16" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">老师口令：</label>
			<div class="controls">
				<form:input path="teachertoken" htmlEscape="false" maxlength="16" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学生客户端口令：</label>
			<div class="controls">
				<form:input path="studentclienttoken" htmlEscape="false" maxlength="16" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否允许web端加入：</label>
			<div class="controls">
				<form:radiobuttons path="webjoin" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" value="0"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否允许客户端加入：</label>
			<div class="controls">
				<form:radiobuttons path="clientjoin" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失效时间：</label>
			<div class="controls">
				<input name="invaliddate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${liveCourse.invaliddate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">讲师/助教加入地址：</label>
			<div class="controls">
				<form:input path="teacherjoinurl" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学员加入地址：</label>
			<div class="controls">
				<form:input path="studentjoinurl" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">code：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="1" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结果说明：</label>
			<div class="controls">
				<form:textarea path="message" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="zxy:liveCourse:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>