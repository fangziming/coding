<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小节管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var courseId=$('#courseId').val();
			var unitId='${param.unitId}';
			$.getJSON("${ctx}/zxy/unit/listByCourseId?courseId="+courseId,function(result){
				var htmlStr='';
				$.each(result,function(i,d){
					htmlStr+='<option value="' + d.id;
					if(d.id==unitId){
						htmlStr+='" selected="selected">'+d.name+'</option>';
					}else{
						htmlStr+='" >'+d.name+'</option>';
					}
				});
				$('#unitId').html(htmlStr);
				$('#unitId').attr('value',unitId);
				$('#unitId').change();
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function courseChange(courseId){
			$.getJSON("${ctx}/zxy/unit/listByCourseId?courseId="+courseId,function(result){
				var htmlStr='';
				$.each(result,function(i,d){
					htmlStr+='<option value="' + d.id+ '">'+d.name+'</option>';
				})
				$('#unitId').html(htmlStr);
				$('#unitId').attr('value','');
				$('#s2id_unitId').children().first().addClass('select2-default');
				$('#s2id_unitId').children().children().first().html('请选择');
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/zxy/chapter/">小节列表</a></li>
		<shiro:hasPermission name="zxy:chapter:edit"><li><a href="${ctx}/zxy/chapter/form">小节添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="chapter" action="${ctx}/zxy/chapter/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>小节名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>小节形式：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('chapter_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属课程：</label>
				<form:select path="courseId" class="input-medium" onchange="courseChange(this.value)">
					<form:option value="" label=""/>
					<form:options items="${zxy:getCourseList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属章节：</label>
				<form:select path="unitId" class="input-medium">
					<form:option value="" label=""/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>小节名称</th>
				<th>小节形式</th>
				<th>序号</th>
				<th>所属课程</th>
				<th>所属章节</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:chapter:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="chapter">
			<tr>
				<td><a href="${ctx}/zxy/chapter/form?id=${chapter.id}">
					${chapter.name}
				</a></td>
				<td>
					${fns:getDictLabel(chapter.type, 'chapter_type', '')}
				</td>
				<td>${chapter.sort}</td>
				<td>
					${zxy:getCourse(chapter.courseId).name}
				</td>
				<td>
					${zxy:getUnit(chapter.unitId).name}
				</td>
				<td>
					<fmt:formatDate value="${chapter.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:chapter:edit"><td>
    				<a href="${ctx}/zxy/chapter/form?id=${chapter.id}">修改</a>
					<a href="${ctx}/zxy/chapter/delete?id=${chapter.id}" onclick="return confirmx('确认要删除该小节吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>