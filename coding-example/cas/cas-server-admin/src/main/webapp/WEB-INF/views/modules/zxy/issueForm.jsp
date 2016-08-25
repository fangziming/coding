<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>期次管理</title>
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
			//为导学老师列表赋值
			$("#fdTeachers").select2("val","${issue.fdTeachers}".split(","));
			
			//关卡分类赋值
			setTimeout(function(){
				<c:forEach items="${issue.issueLockList}" var="issueLock" varStatus="ix">
					$("select[for='lockType${ix.index}']").select2("val","${issueLock.lockType}");
				</c:forEach>
			},500);
			
			//期次设计界面，更新后刷新左侧菜单
			if("${refresh}"=="0"){
				window.parent.refreshTree();
			}
		});
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
				$(this).select2({placeholder:"请选择关卡分类"});
			});
			
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${empty param.opType}">
			<li><a href="${ctx}/zxy/issue/">期次列表${param.type }</a></li>
		</c:if>
		<li class="active">
			<a href="${ctx}/zxy/issue/form?id=${issue.id}">期次<shiro:hasPermission name="zxy:issue:edit">${not empty issue.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="zxy:issue:edit">查看</shiro:lacksPermission></a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="issue" action="${ctx}/zxy/issue/save?opType=${param.opType }" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
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
			<label class="control-label">期次名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期次号：</label>
			<div class="controls">
				<form:input path="num" htmlEscape="false" maxlength="3" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详情页：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本期封面：</label>
			<div class="controls">
				<form:hidden id="picUrl" path="picUrl" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="picUrl" type="images" uploadPath="/issue_file" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本期专业售价：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买部分关卡数：</label>
			<div class="controls">
				<form:input path="locknumdownpayment" htmlEscape="false" maxlength="4" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本期专业首付：</label>
			<div class="controls">
				<form:input path="downpayment" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预售数量：</label>
			<div class="controls">
				<form:input path="sellcntprepared" htmlEscape="false" maxlength="6" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已售数量：</label>
			<div class="controls">
				<form:input path="soldcnt" htmlEscape="false" maxlength="6" class="input-xlarge required digits" value="${(issue.soldcnt eq null)?0:issue.soldcnt}" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('issue_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开班时间：</label>
			<div class="controls">
				<input name="startdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${issue.startdate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="enddate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${issue.enddate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本期QQ群号：</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
				<label class="control-label">关卡设置：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>关卡分类</th>
								<th>阶段标题</th>
								<th>关卡数</th>
								<th>阶段介绍</th>
								<shiro:hasPermission name="zxy:issue:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="issueLockList">
						</tbody>
						<shiro:hasPermission name="zxy:issue:edit"><tfoot>
							<tr><td colspan="6"><a href="javascript:" onclick="addRow('#issueLockList', issueLockRowIdx, issueLockTpl);issueLockRowIdx = issueLockRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="issueLockTpl">//<!--
						<tr id="issueLockList{{idx}}">
							<td class="hide">
								<input id="issueLockList{{idx}}_id" name="issueLockList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="issueLockList{{idx}}_delFlag" name="issueLockList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<select for="lockType{{idx}}" id="issueLockList[{{idx}}]_lockType" name="issueLockList[{{idx}}].lockType" class="required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('issue_lock_type')}" var="dic">
										<option value="${dic.value}">${dic.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="issueLockList{{idx}}_title" name="issueLockList[{{idx}}].title" type="text" value="{{row.title}}" maxlength="64" class="input-small required"  style="width:80%"/>
							</td>
							<td>
								<input id="issueLockList{{idx}}_num" name="issueLockList[{{idx}}].num" type="text" value="{{row.num}}" number="true" maxlength="3" class="input-small digits required"  style="width:80%"/>
							</td>
							<td>
								<input id="issueLockList{{idx}}_remarks" name="issueLockList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="255" class="input-small required"  style="width:90%"/>
							</td>
							<shiro:hasPermission name="zxy:issue:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#issueLockList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var issueLockRowIdx = 0, issueLockTpl = $("#issueLockTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(issue.issueLockList)};
							for (var i=0; i<data.length; i++){
								addRow('#issueLockList', issueLockRowIdx, issueLockTpl, data[i]);
								issueLockRowIdx = issueLockRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">辅导老师：</label>
				<div class="controls">
					<form:select path="fdTeachers" class="input-xlarge required" multiple="true">
						<form:options items="${fdTeacher}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
		<div class="form-actions"  style="<c:if test="${not empty param.opType}">margin-bottom: 38px!important</c:if>">
			<shiro:hasPermission name="zxy:issue:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>