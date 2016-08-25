<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var tel=$("#tel").val();
					var email=$("#email").val();
					if(tel!=""||email!=""){
						loading('正在提交，请稍等...');
						form.submit();
					}
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
		<li><a href="${ctx}/zxy/userInfo/">学生列表</a></li>
		<li class="active"><a href="${ctx}/zxy/userInfo/form?id=${userInfo.id}">学生<shiro:hasPermission name="zxy:userInfo:edit">${not empty userInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:userInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userInfo" action="${ctx}/zxy/userInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">真实姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="16" class="input-xlarge required realName"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">昵称：</label>
			<div class="controls">
				<form:input path="nickname" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头像：</label>
			<div class="controls">
				<form:hidden id="photo" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="photo" type="images" uploadPath="/portrait_file" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="identityNumber" htmlEscape="false" maxlength="32" class="input-xlarge card" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业：</label>
			<div class="controls">
				<form:select path="eduMajor" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('edu_major_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最高学历：</label>
			<div class="controls">
				<form:select path="highEducation" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('high_education_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生年月：</label>
			<div class="controls">
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${userInfo.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="32" class="input-xlarge mobile"/>
				<span class="help-inline"><c:if test="${userInfo.tel ne null && userInfo.tel ne '' && userInfo.telchecked eq '1'}"><font color="red">已校验&nbsp;</font></c:if>手机号或邮箱请至少填写一项用于创建账号，默认密码888888</span>
			</div>
		</div>
		<c:if test="${userInfo.tel eq null || userInfo.tel eq '' || userInfo.telchecked ne '1'}">
			<div class="control-group">
				<label class="control-label">手机校验：</label>
				<div class="controls">
					<form:radiobuttons path="telchecked" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="32" class="input-xlarge email"/>
				<span class="help-inline"><c:if test="${userInfo.email ne null && userInfo.email ne '' && userInfo.emailchecked eq '1'}"><font color="red">已校验&nbsp;</font></c:if>手机号或邮箱请至少填写一项用于创建账号，默认密码888888</span>
			</div>
		</div>
		<c:if test="${userInfo.email eq null || userInfo.email eq '' || userInfo.emailchecked ne '1'}">
			<div class="control-group">
				<label class="control-label">邮箱校验：</label>
				<div class="controls">
					<form:radiobuttons path="emailchecked" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">QQ：</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="16" class="input-xlarge qq"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在地区：</label>
			<div class="controls">
				<form:select path="area" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目前状态：</label>
			<div class="controls">
				<form:select path="currentstatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('user_currentstatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否结婚：</label>
			<div class="controls">
				<form:radiobuttons path="ismarried" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目前学校或单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业院校：</label>
			<div class="controls">
				<form:input path="university" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始工作时间：</label>
			<div class="controls">
				<input name="startWorkyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${userInfo.startWorkyear}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">求职意向：</label>
			<div class="controls">
				<form:input path="intention" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">技术博客地址：</label>
			<div class="controls">
				<form:input path="blogUrl" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">星座：</label>
			<div class="controls">
				<form:select path="constellation" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('constellation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="zxy:userInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>