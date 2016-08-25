<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>期次管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/zxy/issue/">期次列表</a></li>
		<shiro:hasPermission name="zxy:issue:edit">
			<li><a href="${ctx}/zxy/issue/form">期次添加</a></li>
			<li  class="active"><a href="${ctx}/zxy/issue/form">期次设计</a></li>
		</shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">
		    		<i class="icon-refresh pull-right" onclick="refreshTree(true);"></i>
		    		<!-- <i class="icon-arrow-down pull-right hide"></i>
		    		<i class="icon-arrow-up pull-right hide"></i> -->
		    		<i class="icon-remove pull-right hide"></i>
		    		<i class="icon-plus pull-right"></i>
		    	</a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="chapterContent"  width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var curNode;
		var setting = {view:{showIcon:false},data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					curNode=treeNode;
					showIcon(treeNode.level);
					var url="${ctx}/zxy/";
					if(treeNode.level == 0){
						url+="issue/form?opType=0&id="+treeNode.id
					}else if(treeNode.level == 1){
						url+="locks/form?opType=0&id="+treeNode.id
					}else if(treeNode.level == 2){
						url+="unit/form?opType=0&id="+treeNode._id
					}else if(treeNode.level == 3){
						url+="chapter/form?opType=0&id="+treeNode._id
					}
					$('#chapterContent').attr("src",url);
				}
			}
		};
		
		function refreshTree(flag){
			$.getJSON("${ctx}/zxy/issue/treeData?id=${param.id}",function(data){
				var treeObj=$.fn.zTree.init($("#ztree"), setting, data);
				treeObj.expandAll(true);
				
				if(flag){
					showIcon(0);
					treeObj.selectNode(treeObj.getNodeByParam("id", data[0].id));
					$("#chapterContent").attr("src","${ctx}/zxy/issue/form?opType=0&id="+data[0].id);
				}else{
					treeObj.selectNode(curNode);
				}
				curNode=treeObj.getSelectedNodes()[0];
			});
		}
		refreshTree(true);
		
		function showIcon(level){
			if(level==0){
				$(".icon-remove").hide();
				//$(".icon-arrow-up").hide();
				//$(".icon-arrow-down").hide();
			}else{
				/**if(curNode.getPreNode()==null){
					$(".icon-arrow-up").hide();
				}else{
					$(".icon-arrow-up").show();
				}
				if(curNode.getNextNode()==null){
					$(".icon-arrow-down").hide();
				}else{
					$(".icon-arrow-down").show();
				}**/
				$(".icon-remove").show();
			}
		}
		
		$(".icon-plus").click(function(){
			//var zTree = $.fn.zTree.getZTreeObj("ztree");
			//var node=zTree.getSelectedNodes()[0];
			var node=curNode;
			if(node.level==0){
				$('#chapterContent').attr("src","${ctx}/zxy/locks/form?opType=0&mid="+node.mid+"&sort="+node.sort);
			}else if(node.level==1){
				$('#chapterContent').attr("src","${ctx}/zxy/unit/form?opType=0&cid="+node.cid+"&sort="+node.sort);
			}else if(node.level==2){
				$('#chapterContent').attr("src","${ctx}/zxy/chapter/form?opType=0&cid="+node.cid+"&uid="+node._id+"&sort="+node.sort);
			}else if(node.level==3){
				$('#chapterContent').attr("src","${ctx}/zxy/chapter/form?opType=0&cid="+node.cid+"&uid="+node.uid+"&sort="+node.sort);
			}
		});
		
		$(".icon-remove").click(function(){
			var url="${ctx}/zxy/";
			var tip="";
			if(curNode.level == 1){
				url+="locks/delete?opType=0&id="+curNode.id
				tip="关卡【"+curNode.name+"】";
			}else if(curNode.level == 2){
				url+="unit/delete?opType=0&id="+curNode._id
				tip="章节【"+curNode.name+"】";
			}else if(curNode.level == 3){
				url+="chapter/delete?opType=0&id="+curNode._id
				tip="小节【"+curNode.name+"】";
			}
			
			confirmx("确认要删除"+tip+"吗？",function(){
				$.getJSON(url,function(mes){
					if(mes!=''){
						showTip(mes,"error");
					}else{
						refreshTree(true);
					}
				});
			});
		});
		
		var leftWidth = 250; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>