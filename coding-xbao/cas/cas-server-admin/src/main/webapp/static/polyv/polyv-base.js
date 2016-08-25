var polyvVideo = {
	tag: "",
	pageNum: 1,
	video_domain: "v.polyv.net",
	video_domain_beta: "beta.polyv.net",
	player_domain: "player.polyv.net",
	list: $("#exist-video-list"),
	showVideo: function(json) {
		var list = polyvVideo.list,
			dom = "";
		list.hide();
		var that = this;
		if(json.data){
			$(json.data).each(function(index) {
				var status = that.getStatus(this.status);
				var imgUrl = this.first_image;
				var ele = "<tr data-vid=" + this.vid + "><td><img src=" +
					imgUrl + " /></td><td><span class='v-title'>" +
					this.title + "</span> <br /> " +
					this.duration + "</td><td>" +
					status + "</td><td> " +
					this.ptime + "</td></tr>";
				dom += ele;
			})
		}else{
			dom = "<tr><td>暂无视频</td></tr>"
		}
		list.html(dom);
		$("#loading").toggle();
		list.show();
	},
	getStatus: function(statusNum) {
		var status;
		switch (statusNum) {
			case "60":
				status = "已发布";
				break;
			case "61":
				status = "已发布";
				break;
			case "10":
				status = "等待编码";
				break;
			case "20":
				status = "正在编码";
				break;
			case "50":
				status = "等待审核";
				break;
			case "51":
				status = "审核不通过";
				break;
			case "5":
				status = "上传中";
				break;
			default:
				status = "已删除"
		}
		return status;
	},
	searchVideo: function() {
		var val = $.trim($("#search-video").val())
		if (val == "") return;
		polyvVideo.list.empty();
		$.getJSON("http://" + polyvVideo.video_domain + "/uc/services/rest?jsonp=?", {
			"method": "searchByTitle",
			"readtoken": polyvVideo.readToken,
			"pageNum": 1,
			"numPerPage": 1000,
			"tag": polyvVideo.tag,
			"keyword": val,
			"catatree": polyvVideo.catatree
		}, function(json) {
			polyvVideo.showVideo(json);
			$("#pageMove").hide();
			$("#returnHome").show();
			if (json.data.length == 0) {
				alert("搜索结果为空，请更换关键词!");
			}
		})
	},
	changeSize: function(bytes) {
		var bt = parseInt(bytes);
		var result;
		if (bt === 0) {
			result = "0B";
		} else {
			var k = 1024;
			sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
			i = Math.floor(Math.log(bt) / Math.log(k));
			result = (bt / Math.pow(k, i)).toFixed(2) + sizes[i];
		}
		return result;
	},
	setStatus: function(json) {
		var vstatus = $("#vstatus"),
			dom = "",
			dom2 = "";
		dom += "<td>" + this.getStatus(json.status) + "</td>";
		dom += "<td>" + json.df + "</td>";
		for (var i = 0; i < 3; i++) {
			var size = mSize = "-";
			if (json.filesize[i]) {
				size = this.changeSize(json.filesize[i]);
			}
			if (json["tsfilesize" + (i+1)]) {
				mSize = this.changeSize(json["tsfilesize" + (i+1)]);
			}
			dom += "<td>" + size + "</td>";
			dom2 += "<td>" + mSize + "</td>";
		}
		vstatus.html(dom + dom2);
	},
	selectVideo: function(vid) {
		var param = arguments,
			that = this;
		$.getJSON("http://" + polyvVideo.video_domain + "/uc/services/rest?jsonp=?", {
			"method": "getById",
			"readtoken": polyvVideo.readToken,
			"vid": vid
		}, function(json) {
			$("#video-detail").fadeIn();
			var cancle = $("#xxxx");
			var data = json.data[0]
			var img = $(".first-img>img");
			cancle.html("x");
			var first_image = data.first_image;
			if (param[1]) {
				if (first_image == img.attr("src")) {
					setTimeout(function() {
						param.callee(vid, true);
					}, 500)
				} else {
					img.attr("src", first_image);
					$("#submitBtn span").toggle().
					$("#xxxx").text("保存");
				}
				return;
			}
			that.setStatus(data);
			img.attr("src", first_image);
			$("#vtitle").text(data.title);
			$("#vsrc").text(data.swf_link);
			$("#vvid").text(data.vid);
			$("#vtime").text(data.ptime);
			$("#vduration").text(data.duration);
			var imgWrap = $("#video-all-img");
			images = data.images;
			var dom = "";
			for (var i in images) {
				dom += "<img src=" + images[i] + ">";
			}
			imgWrap.html(dom);
		})
	},
	getNewImg: function() {
		$.ajax({
			url: "http://v.polyv.net/uc/video/recentFirstImages",
			data: {
				"userid": polyvVideo.userid
			},
			success: function(data) {
				var imgWrap = $("#exist-all-img");
				var dom = "";
				$.each(data, function() {
					dom += "<img src=" + this.imgurlsmall + " data-id=" + this.id + ">";
				});
				imgWrap.html(dom);
			},
			dataType: "jsonp"
		})
	},
	addHander: function(ele, type, handler) {
		if (ele.addEventListener) {
			ele.addEventListener(type, handler, false);
		} else if (ele.attachEvent) {
			ele.attachEvent("on" + type, handler);
		} else {
			ele["on" + type] = handler;
		}
	}
};

polyvVideo.addHander(window, "message", function(event) {
	obj = $.parseJSON(event.data);
	polyvVideo.url = obj.url;
	polyvVideo.readToken = obj.readToken;
	polyvVideo.writeToken = obj.writeToken;
	polyvVideo.userid = obj.userid;
	polyvVideo.ts = obj.ts;
	polyvVideo.hash = obj.hash;
	polyvVideo.cataid = obj.cataid;
	(function() {
		$.ajax({
			dataType: 'jsonp',
			url: 'http://v.polyv.net/uc/cata/listjson?cataid=1&userid=' + polyvVideo.userid,
			success: function(data) {
				var a = sliceArray(data,polyvVideo.cataid);
				polyvVideo.catatree = polyvVideo.cataid ? a[0].catatree : "1";
				createMenu(a)
				$("#btn-cls a").each(function() {
					$(this).click(function() {
						$("#cls").html($(this).attr("data-cataname")).attr("data-cataid", $(this).attr("data-cataid"));
						$("#cata").hide(100);
					})
				});
			}
		}).fail(function() {
			alert("获取分类目录失败，请刷新页面");
		})

		function sliceArray(ary,cataid) {
			var i=0,
					len = ary.length,
					startIndex = 0,
					bol = false;
					endIndex = len,
					pat = new RegExp(polyvVideo.cataid);
			for(i;i<len;i++){
				if(ary[i].cataid / 1 == polyvVideo.cataid / 1){
					startIndex = i;
					bol = true;
				}
				if(bol && !pat.test(ary[i].catatree)){
					endIndex = i;
					break;
				}
			}
			return ary.slice(startIndex,endIndex);
		}

		function createMenu(arr) {
			var minLen =  arr[0].catatree.match(/,/g).length - 1,
					dom = '';
			$(arr).each(function(index) {
				var cataid = this.cataid,
					cataname = this.cataname,
					catatree = this.catatree;
				var level = catatree.match(/,/g).length - 1 - minLen;
				var i = 0,
					val = "";
				for (i; i < level; i++) {
					val += "-- ";
				}
				val += cataname;
				dom += "<li><a href='javascript:;'" + " data-cataid=" + this.cataid + " data-cataname=" + this.cataname + ">" + val + "</a></li>";
			})
			$("#cata").html(dom);
			$("#btn-cls").click(function() {
				$("#cata").toggle();
			});

		}
	})()
	polyvVideo.getVideo = function(num) {
		polyvVideo.list.empty();
		$("#loading").toggle();
		$.getJSON("http://" + polyvVideo.video_domain + "/uc/services/rest?jsonp=?", {
			"method": "getNewList",
			"readtoken": polyvVideo.readToken,
			"pageNum": num,
			"catatree": polyvVideo.catatree
		}, function(json) {
			if(!json.data){
					$("[data-pagenum='next']").hide();
			}else{
				$("[data-pagenum='next']").show();
			}
			console.log(json);
			polyvVideo.showVideo(json);
		})
	}
});
(function() {
	var $videoDetail = $("#video-detail"),
		$existList = $("#exist-list"),
		$refresh = $("#refresh"),
		$videoAllImg = $("#video-all-img"),
		$firstImg = $(".first-img>img"),
		$existAllImg = $("#exist-all-img"),
		$upImg = $("#upImg"),
		$fileName = $("#fileName");
	$("#upload-list").click(function() {
		$(this).addClass("label-primary").removeClass("label-default");
		$existList.addClass("label-default").removeClass("label-primary");
		$(".exist-list").fadeOut();
		$(".upload-list").fadeIn();
	});
	$existList.click(function() {
		polyvVideo.list.hide();
		$(this).addClass("label-primary").removeClass("label-default");
		$("#upload-list").addClass("label-default").removeClass("label-primary");
		$(".upload-list").fadeOut();
		$(".exist-list").fadeIn();
		$("#pageMove").show();
		polyvVideo.pageNum = 1;
		polyvVideo.getVideo(polyvVideo.pageNum);
		$videoDetail.fadeOut();
		$("#returnHome").fadeOut();
	});
	$("#search-btn").click(function() {
		$videoDetail.fadeOut();
		$refresh.hide();
		polyvVideo.search = true;
		$("#loading").toggle();
		polyvVideo.searchVideo();
	});
	$("#pageMove").on("click", "span", function() {
		polyvVideo.list.hide();
		var pn = $(this).attr("data-pagenum");
		if (pn == "pre") {
			if (polyvVideo.pageNum > 1) {
				polyvVideo.pageNum -= 1;
				polyvVideo.getVideo(polyvVideo.pageNum);
			} else {
				polyvVideo.getVideo(1);
			}
		} else {
			polyvVideo.pageNum += 1;
			polyvVideo.getVideo(polyvVideo.pageNum);
		}
	});
	$("#returnHome").click(function() {
		$existList.click();
		$("#search-video").val("");
		$refresh.hide();
		polyvVideo.search = false;
	});
	$("#exist-video-list").on("click", "tr", function() {
		var vid = $(this).attr("data-vid");
		$videoDetail.attr("data-vid", vid);
		$("#upVid").val(vid);
		$("#upWritetoken").val(polyvVideo.writeToken);
		polyvVideo.selectVideo(vid);
		$refresh.show();
		$("#imgBtn span").get(0).click();
		$upImg.val("");
		$fileName.html("");
	});

	$videoAllImg.on("click", "img", function() {
		var src = $(this).attr("src");
		if (src == $firstImg.attr("src")){
			$(this).attr("id", "selected");
			return;
		}
		$firstImg.attr("src", src).addClass("ischange").removeAttr("data-id");
		$("#selected").removeAttr("id");
		$("#chosen").removeAttr("id");
		$(this).attr("id", "selected");
		$("#xxxx").html("保存");
	});
	$existAllImg.on("click", "img", function() {
		var src = $(this).attr("src");
		if (src == $firstImg.attr("src")) return;
		$firstImg.attr("src", src).addClass("ischange").attr("data-id", $(this).attr("data-id"));
		$("#selected").removeAttr("id");
		$("#chosen").removeAttr("id");
		$(this).attr("id", "chosen");
		$("#xxxx").html("保存");
	});
	$("#imgBtn").on("click", "span", function() {
		var dataId = $(this).attr("data-id");
		$("#imgBtn span").addClass("label-default").removeClass("label-info");
		$(this).removeClass("label-default").addClass("label-info");
		if (dataId == "video-all-img") {
			$existAllImg.hide();
			$videoAllImg.show();
		} else {
			$videoAllImg.hide();
			$existAllImg.show();
		}
	});

	$("#existImgBtn").click(function() {
		polyvVideo.getNewImg();
	})

	$("#xxxx").click(function() {
		var vid = $videoDetail.attr("data-vid");
		if ($("#selected").length > 0) {
			var index = $("#selected").prevAll("img").length;
			$.getJSON("http://" + polyvVideo.video_domain + "/uc/services/rest?jsonp=?", {
				"method": "selectImage",
				"writetoken": polyvVideo.writeToken,
				"selectedIndex": index,
				"vid": vid
			});
			$(this).text("x");
			$("#selected").removeAttr("id");
			return;
		}
		if ($("#chosen").length > 0) {
			var recentId = $firstImg.attr("data-id");
			$.getJSON("http://" + polyvVideo.video_domain + "/uc/services/rest?jsonp=?", {
				"method": "selectImage",
				"writetoken": polyvVideo.writeToken,
				"recentId": recentId,
				"vid": vid
			});
			$(this).text("x");
			$("#chosen").removeAttr("id");
			return;
		}
		if (!polyvVideo.search && $firstImg.hasClass("ischange")) {
			polyvVideo.getVideo(polyvVideo.pageNum);
		} else if (polyvVideo.search && $firstImg.hasClass("ischange")) {
			$("#search-btn").click();
		}
		polyvVideo.getVideo(polyvVideo.pageNum);
		$firstImg.removeClass("ischange");
		$videoDetail.fadeOut();
		$refresh.hide();
	});
	$refresh.click(function(){
		var vid = $videoDetail.attr("data-vid");
		$upImg.val("");
		$fileName.html("");
		polyvVideo.selectVideo(vid);
	})
	$("#returnMsg").click(function() {
		var vid = $videoDetail.attr("data-vid");
		$.getJSON("http://" + polyvVideo.video_domain + "/uc/services/rest?jsonp=?", {
			"method": "getById",
			"readtoken": polyvVideo.readToken,
			"vid": vid
		}, function(obj) {
			window.parent.postMessage(JSON.stringify(obj.data[0]), polyvVideo.url);
		})
	});
	$upImg.change(function() {
		var file = document.getElementById("upImg").files[0];
		$fileName.text(file.name);
	});

	$("#upImgForm").submit(function() {
		if (!document.getElementById("upImg").files[0]) {
			alert("请选择图片");
			return;
		}
		$("#submitBtn span").toggle();
		$("#selected").removeAttr("id");
		$("#chosen").removeAttr("id");
		var vid = $videoDetail.attr("data-vid");
		polyvVideo.selectVideo(vid, true);
	})
})();
