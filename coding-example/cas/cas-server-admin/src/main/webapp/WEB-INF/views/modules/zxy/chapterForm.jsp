<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小节管理</title>
	<meta name="decorator" content="default"/>
	<script src='https://player.polyv.net/script/polyvplayer.min.js'></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
			
			//期次设计界面，更新后刷新左侧菜单
			if("${refresh}"=="0"){
				window.parent.refreshTree();
			}
			
			var courseId='${chapter.courseId}';
			var unitId='${chapter.unitId}';
			//期次设计时，初始化课程信息
			if("${param.cid}"!=""){
				$("#courseId").select2("val","${param.cid}");
				courseId="${param.cid}";
				
				//期次设计时，初始化章节信息
				if("${param.uid}"!=""){
					unitId="${param.uid}";
				}
			}
			
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
			
			var type='${chapter.type}';
			typeChange(type);
		});
		
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
		
		var objType;
		
		function typeChange(type){
			objType = type;
			
			$("#videoSpan").html('<font color="red">*</font>');
			if(type){
				var url=null;
				if(type==0){
					url="${ctx}/zxy/vedio/all";
				}else if(type==1){
					url="${ctx}/zxy/homework/all";
				}else if(type==2){
					url="${ctx}/zxy/liveCourse/all";
				}
				if(url!=null){
					$.getJSON(url,function(result){
						var name=$('#type option[value="'+type+'"]').html();
						var objid='${chapter.objid}';
						var htmlStr='';
						var selected=false;
						$.each(result,function(i,d){
							htmlStr+='<option value="' + d.id;
							if(d.id==objid){
								htmlStr+='" selected="selected">'+(type==1?d.title:d.name)+'</option>';
								selected=true;
							}else{
								htmlStr+='" >'+(type==1?d.title:d.name)+'</option>';
							}
						});
						$('#objid').html(htmlStr);
						$('#objid').attr('value',objid);
						$('#objid').change();
						if(!selected){
							$('#s2id_objid').children().first().addClass('select2-default');
							$('#s2id_objid').children().children().first().html('请选择'+name);
						}
					});
				}
			}else{
				$('#s2id_objid').children().children().first().html('请选择小节形式');
			}
		}
		
		function objChange(objid){
			
			if(objType && objid){
				if(objType==0){
					$.getJSON("${ctx}/zxy/vedio/vedioJson?id="+objid,function(result){
					
						$("#videoSpan").html('<font color="red">*</font> <a href="#" onclick="playVid(\''+result.name+'\',\''+result.url+'\')" >观看</a>');
						
					});
				}else{
						$("#videoSpan").html('<font color="red">*</font>');
				}
			}else{
				$('#s2id_objid').children().children().first().html('请选择小节形式');
			}
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${empty param.opType}">
			<li><a href="${ctx}/zxy/chapter/">小节列表</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/zxy/chapter/form?id=${chapter.id}">小节<shiro:hasPermission name="zxy:chapter:edit">${not empty chapter.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:chapter:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="chapter" action="${ctx}/zxy/chapter/save?opType=${param.opType }" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">小节名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属课程：</label>
			<div class="controls">
				<form:select path="courseId" class="input-xlarge required" onchange="courseChange(this.value)">
					<form:option value="" label=""/>
					<form:options items="${zxy:getCourseList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属章节：</label>
			<div class="controls">
				<form:select path="unitId" class="input-xlarge required">
					<form:option value="" label=""/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">小节形式：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required" onchange="typeChange(this.value)">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('chapter_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">形式对象：</label>
			<div class="controls">
				<form:select path="objid" class="input-xlarge required"  onchange="objChange(this.value)">
					<form:option value="" label=""/>
				</form:select>
				<span class="help-inline"  id="videoSpan"></span>
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
			<shiro:hasPermission name="zxy:chapter:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>