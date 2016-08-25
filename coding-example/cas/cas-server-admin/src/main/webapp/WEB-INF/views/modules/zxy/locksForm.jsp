<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关卡管理</title>
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
			
			rewardDisplay("${locks.isreward}");
			
			$("input[name='isreward']").click(function(){
				var isreward=$(this).val();
				rewardDisplay(isreward);
			});
			
			function rewardDisplay(flag){
				if(flag==1){
					$("#topspeedDays_group").show();
					$("#topspeedScholarship_group").show();
				}else if(flag==0){
					$("#topspeedDays_group").hide();
					$("#topspeedScholarship_group").hide();
				}
			}
			
			//期次设计时，初始化专业信息
			if("${param.mid}"!=""){
				$("#majorId").select2("val","${param.mid}");
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
			<li><a href="${ctx}/zxy/locks/">关卡列表</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/zxy/locks/form?id=${locks.id}">关卡<shiro:hasPermission name="zxy:locks:edit">${not empty locks.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:locks:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="locks" action="${ctx}/zxy/locks/save?opType=${param.opType }" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">关卡名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
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
			<label class="control-label">所属课程：</label>
			<div class="controls">
				<form:select path="course.id" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${zxy:getCourseList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关卡位置：</label>
			<div class="controls">
				<form:input path="position" htmlEscape="false" maxlength="3" class="input-xlarge required digits" value="${param.sort }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过关条件：</label>
			<div class="controls">
				<form:input path="conditions" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关卡测验：</label>
			<div class="controls">
				<form:select path="quizId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${zxy:getQuizList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通关人数：</label>
			<div class="controls">
				<form:input path="passcnt" htmlEscape="false" maxlength="8" class="input-xlarge required digits" value="${(locks.passcnt eq null)?0:locks.passcnt}" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最早开启时间：</label>
			<div class="controls">
				<input name="startdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${locks.startdate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否设有奖学金：</label>
			<div class="controls">
				<form:radiobuttons path="isreward" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group" id="topspeedDays_group">
			<label class="control-label">极速闯关天数：</label>
			<div class="controls">
				<form:input path="topspeedDays" htmlEscape="false" maxlength="2" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group" id="topspeedScholarship_group">
			<label class="control-label">极速闯关奖学金：</label>
			<div class="controls">
				<form:input path="topspeedScholarship" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions" style="<c:if test="${not empty param.opType}">margin-bottom: 38px!important</c:if>">
			<shiro:hasPermission name="zxy:locks:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>