<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级学生管理</title>
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
			
			//为当前班级赋值
			var issueId="${wclassStudents.wclass.issueId}";
			var wclassId="${wclassStudents.wclass.id}";
			$.getJSON("${ctx}/zxy/issueClasses/findByIssueId?issueId="+issueId,function(result){
				var htmlStr='';
				$.each(result,function(i,d){
					htmlStr+='<option value="' + d.id;
					if(d.id==wclassId){
						htmlStr+='" selected="selected">'+d.name+'</option>';
					}else{
						htmlStr+='" >'+d.name+'</option>';
					}
				});
				$('select[data-id="wclass_id"]').html(htmlStr);
				$('select[data-id="wclass_id"]').attr('value',wclassId);
				$('select[data-id="wclass_id"]').change();
			});
			
			$('input[id="student.id"').select2({
			    placeholder          : "请输入学生姓名",
			    minimumInputLength   : 1,
			    multiple             : false,
			    initSelection        : function (element, callback) {   // 初始化时设置默认值
			    	$('div[id="s2id_student.id"]').children().children().first().html("${wclassStudents.student.name }");
			    },
			    formatSelection : function (item) { 
			    	//$("input[id='student.id']").val(item.id);
			    	return item.text; 
			    },  // 选择结果中的显示
			    formatResult    : function (item) { return item.text; },  // 搜索列表中的显示
			    ajax : {
			        url      : "${ctx}/zxy/wclassStudents/findStuByName",              // 异步请求地址
			        dataType : "json",                  // 数据类型
			        data     : function (term, page) {  // 请求参数（GET）
			            return { name: term };
			        },
			        results      : function (data, page) {
			        	return {results: data};
			        },  // 构造返回结果
			        escapeMarkup : function (m) {return m; }               // 字符转义处理
			    }
			});
		});
		
		//跟进期次筛选班级信息
		function issueChange(issueId){
			$.getJSON("${ctx}/zxy/issueClasses/findByIssueId?issueId="+issueId,function(result){
				var htmlStr='';
				$.each(result,function(i,d){
					htmlStr+='<option value="' + d.id+ '">'+d.name+'</option>';
				})
				$('select[data-id="wclass_id"]').html(htmlStr);
				$('select[data-id="wclass_id"]').attr('value','');
				$('div[id="s2id_wclass.id"]').children().first().addClass('select2-default');
				$('div[id="s2id_wclass.id"]').children().children().first().html('请选择');
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/zxy/wclassStudents?wclass.issueId=${wclassStudents.wclass.issueId}&wclass.id=${wclassStudents.wclass.id}">班级学生列表</a></li>
		<li class="active"><a href="${ctx}/zxy/wclassStudents/form?id=${wclassStudents.id}&wclass.issueId=${wclassStudents.wclass.issueId}&wclass.id=${wclassStudents.wclass.id}">班级学生<shiro:hasPermission name="zxy:wclassStudents:edit">${not empty wclassStudents.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:wclassStudents:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="wclassStudents" action="${ctx}/zxy/wclassStudents/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">所在期次：</label>
			<div class="controls">
				<form:select path="wclass.issueId" data-id="wclass_issueId" class="input-medium required" onchange="issueChange(this.value)">
					<form:option value="" label=""/>
					<form:options items="${zxy:getIssueList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在班级：</label>
			<div class="controls">
				<form:select path="wclass.id" data-id="wclass_id" class="input-medium required">
					<form:option value="" label=""/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班级学生：</label>
			<div class="controls">
				<input id="student.id" name="student.id" type="hidden" class="input-xlarge required" value="${wclassStudents.student.id }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习时长：</label>
			<div class="controls">
				<form:input path="learningtime" htmlEscape="false" maxlength="11" class="input-xlarge required" value="${(wclassStudents.learningtime eq null)?0:wclassStudents.learningtime}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">综合级别：</label>
			<div class="controls">
				<form:select path="level" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('student_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="zxy:wclassStudents:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>