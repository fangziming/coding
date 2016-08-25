<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资源分类管理</title>
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
			
			if("${resourseType.pid}"==""){
				var curNode=window.parent.curNode;
				if(curNode){
					$("#pidId").val(curNode.id);
					$("#pidName").val(curNode.name);
				}else{
					$("#pidId").val("0");
					$("#pidName").val("顶级分类");
				}
			}else if("${resourseType.pid}"=="0"){
				$("#pidId").val("0");
				$("#pidName").val("顶级分类");
			}
			
			$("#topt").click(function(){
				if($(this).attr("checked")){
					$("#pidId").val("0");
					$("#pidName").val("顶级分类");
				}
			});
			
			if("${refresh}"=="0"){
				window.parent.refreshTree(false);
			}
			
			var curNode=window.parent.curNode;
			var sort=curNode&&curNode.children?(curNode.children.length):0;
			$("#sort").val(sort);
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/zxy/resourseType/form?id=${resourseType.id}">资源分类<shiro:hasPermission name="zxy:resourseType:edit">${not empty resourseType.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:resourseType:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="resourseType" action="${ctx}/zxy/resourseType/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="type" value="${param.type }"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">分类名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级分类：</label>
			<div class="controls">
				<sys:treeselect id="pid" name="pid" value="${resourseType.pid }" labelName="pname" labelValue="${resourseType.pname }"
					title="分类" url="/zxy/resourseType/jsonList/1" />
				<input type="checkbox" value="0" id="topt"/>顶级分类
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" max="999" class="input-xlarge required" digits="true"/>
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
			<shiro:hasPermission name="zxy:resourseType:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>