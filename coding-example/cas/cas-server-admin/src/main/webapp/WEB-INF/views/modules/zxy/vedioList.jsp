<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>视频管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/jquery/jQuery.md5.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		});
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
		<li class="active"><a href="${ctx}/zxy/vedio/">视频列表</a></li>
		<shiro:hasPermission name="zxy:vedio:edit"><!--<li>   <a href="${ctx}/zxy/vedio/form">视频添加</a>
		</li>--><li><a href="#" id="upload">上传</a></li></shiro:hasPermission>
	</ul>
	<script src="${ctxStatic }/polyv/polyv-upload.js"></script>
	<script src='https://player.polyv.net/script/polyvplayer.min.js'></script>
	<script type="text/javascript">
		var timestamp=new Date().getTime();
		var writeToken="ybf3g84ZsCAVqvp-EB01ale147SZ1NT0";
	    var obj = {
	            uploadButtton: "upload",   //打开上传控件按钮id
	            writeToken: writeToken,
	            userid : "4c33c1d451",
	            ts : timestamp,
	            hash : $.md5(timestamp+writeToken),
	            readToken: "zQzcNPcnEX-hZ5o3BEQzU-SCE6ZK7YE0",
	   }
	    var upload = new PolyvUpload(obj);
	    
         function playVid(name,url){
         
         	var html = "<div id='plv_video_box'></div>";

			$.jBox(html, { title: name,width:560,height:440});
	    
	    	var player = polyvObject('#plv_video_box').videoPlayer({
              'width':557,
              'height':356,
              'vid' : url,
              'flashParams':{'wmode':'window','allowScriptAccess':'always','allowFullScreen':'true','allowNetworking':'all'},
              'flashvars':{'autoplay':'1','history_video_duration':1,'df':3}
            });
         }
                  	    
	</script>
	
	
	<form:form id="searchForm" modelAttribute="vedio" action="${ctx}/zxy/vedio/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>视频名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>视频地址：</label>
				<form:input path="url" htmlEscape="false" maxlength="225" class="input-medium"/>
			</li>
			<li><label>视频状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('vedio_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>视频名称</th>
				<th>视频时长</th>
				<th>视频地址</th>
				<th>分类</th>
				<th>标签</th>
				<th>视频状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="zxy:vedio:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="vedio">
			<tr>
				<td><a href="${ctx}/zxy/vedio/form?id=${vedio.id}">
					${vedio.name}
				</a></td>
				<td>
					${vedio.duration}
				</td>
				<td>
					${vedio.url}
				</td>
				<td>
					${vedio.resourseType.name}
				</td>
				<td>
					${vedio.tag}
				</td>
				<td>
					${fns:getDictLabel(vedio.status, 'vedio_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${vedio.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="zxy:vedio:edit"><td>
					<a href="#" onclick="playVid('${vedio.name}','${vedio.url}')" >观看</a>
    				<a href="${ctx}/zxy/vedio/form?id=${vedio.id}">修改</a>
					<a href="${ctx}/zxy/vedio/delete?id=${vedio.id}" onclick="return confirmx('确认要删除该视频吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>