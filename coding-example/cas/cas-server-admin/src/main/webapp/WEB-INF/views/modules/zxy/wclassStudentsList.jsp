<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级学生管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
			
			$("#btnReturn").click(function(){
				document.location.href="${ctx}/zxy/issueClasses";
			});
		});
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
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/zxy/wclassStudents?wclass.issueId=${wclassStudents.wclass.issueId}&wclass.id=${wclassStudents.wclass.id}">班级学生列表</a></li>
		<shiro:hasPermission name="zxy:wclassStudents:edit"><li><a href="${ctx}/zxy/wclassStudents/form?wclass.issueId=${wclassStudents.wclass.issueId}&wclass.id=${wclassStudents.wclass.id}">班级学生添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wclassStudents" action="${ctx}/zxy/wclassStudents/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属期次：</label>
				<form:select path="wclass.issueId" data-id="wclass_issueId" class="input-medium" onchange="issueChange(this.value)">
					<form:option value="" label=""/>
					<form:options items="${zxy:getIssueList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属班级：</label>
				<form:select path="wclass.id" data-id="wclass_id" class="input-medium">
					<form:option value="" label=""/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnReturn" class="btn btn-primary" type="button" value="返回班级列表"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学生名称</th>
				<th>所在班级</th>
				<th>学习时长</th>
				<th>综合级别</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="zxy:wclassStudents:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wclassStudents">
			<tr>
				<td><a href="${ctx}/zxy/wclassStudents/form?id=${wclassStudents.id}">
					${wclassStudents.student.name}
				</a></td>
				<td>
					${wclassStudents.wclass.name}
				</td>
				<td>
					${wclassStudents.learningtime}
				</td>
				<td>
					${fns:getDictLabel(wclassStudents.level, 'student_level', '')}
				</td>
				<td>
					<fmt:formatDate value="${wclassStudents.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wclassStudents.remarks}
				</td>
				<shiro:hasPermission name="zxy:wclassStudents:edit"><td>
    				<a href="${ctx}/zxy/wclassStudents/form?id=${wclassStudents.id}">修改</a>
					<a href="${ctx}/zxy/wclassStudents/delete?id=${wclassStudents.id}" onclick="return confirmx('确认要删除该班级学生吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>