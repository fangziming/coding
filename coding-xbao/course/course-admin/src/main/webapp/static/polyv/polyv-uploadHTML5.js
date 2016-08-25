$(function() {
	function addList(file) {
		var name = file.name.replace(/\.\w+$/, '');
		type = file.type.replace(/.+\//, ''),
			size = getSize(file.size);
		var htl = "<tr><td spellcheck='false' rowspan='1' class='titledes inp'><p>" +
			name +
			"</p><input type='text' class='form-control'></td><td>" +
			"<textarea class='form-control' rows='2' placeholder='添加描述' spellcheck='false'></textarea>" +
			"</td><td class='progress-wrap'><p><span class='file-size'>" +
			size + " / " + type +
			"</span></p><div class='progress'><div class='progress-bar progress-bar-success progress-bar-striped active' style='width: 0%'></div></div></td><td class='bar'><button class='btn btn-danger delete'><i class='glyphicon glyphicon-trash'></i></button></td></tr>";
		
		$("#files-list").append(htl);
	}

	function getSize(bytes) {
		var sizes = ['Bytes', 'KB', 'MB', "GB"];
		if (bytes == 0) return 'n/a';
		var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
		return (bytes / Math.pow(1024, i)).toFixed(1) + ' ' + sizes[i];
	}

	function clearFiles() {
		var control = $("#file_upload");
		control.replaceWith(control = control.clone(true));
	}


	var fileList = {
		files: [],
		filesName: [],
		num: 0,
		allowUpload: false
	}
	var start = function() {
		var files = this.files,
			uploadBtn = $("#upload");
		fileList.list = $("#files-list tr");
		$(files).each(function(index) {
			if (fileList.filesName.indexOf(this.name) < 0) {
				addList(this);
				fileList.filesName.push(files[index].name);
				fileList.files.push(files[index]);
			}
		})
		if (fileList.filesName.length > 0) {
			uploadBtn.removeAttr("disabled");
			$("#cover01").removeClass("cover01");
			if (!$(fileList.list[fileList.num]).hasClass("uploading")) {
				fileList.allowUpload = true;
			}
		}


		$("#files-list").on("click", ".delete", function() {
			if (window.speedTime != undefined) {
				clearInterval(speedTime);
			}
			var index = $(this).parents("tr").prevAll().length;
			fileList.files.splice(index, 1);
			fileList.filesName.splice(index, 1);
			var len = fileList.filesName.length;
			if (index == fileList.num && $(fileList.list[fileList.num]).hasClass("uploading")) {
				$(this).parents("tr").remove();
				upload.stop();
				if (len - fileList.num > 0) {
					fileList.allowUpload = true;
					uploadBtn.click();
				}
			} else {
				$(this).parents("tr").hide(300, function() {
					$(this).remove();
				});
			}
			if (len == 0) {
				$("#pause").hide();
				$("#speed").hide();
			}
		})

		clearFiles();
	}

	$("#files-list").on("dblclick", ".titledes", function() {
		var des = $(this).find("p");
		var inp = $(this).find("input");
		des.hide();
		inp.show().val(des.text()).focus();
		inp.blur(function() {
			var val = $.trim(inp.val());
			$(this).hide();
			if (!val == "") {
				des.show().html(inp.val());
			} else {
				des.show()
			}
		})
	})

	$("#empty").click(function() {
		var r = confirm("确认清空上传列表？");
		if (r == true) {
			if (upload.stop) {
				upload.stop();
			}
			$("#files-list").empty();
			fileList.files = [];
			fileList.filesName = [];
			fileList.num = 0;
			fileList.allowUpload = false;
			$("#upload").attr("disabled", "disabled");
			$("#pause").css("display", "none").removeClass("pause").html('<i class="glyphicon glyphicon-pause"></i><span>暂停</span>');
			$("#speed").css("display", "none");
			$("#cls").removeAttr("disabled").next().removeAttr("disabled");
			$("#tag").removeAttr("disabled").css("width", "30%");
		}
	})


	$("#file_upload").change(start);


	$("#pause").click(function(e) {
		e.preventDefault();
		$(this).toggleClass("pause");
		$("#files-list .progress-bar").each(function(index) {
			if (index > fileList.num - 1) {
				$(this).toggleClass("active");
			}
		});
		clearInterval(speedTime);
		if ($(this).hasClass("pause")) {
			if (upload) {
				upload.stop();
				$(this).html('<i class="glyphicon glyphicon-stop"></i><span>继续</span>');
				$("#speed").hide();
			}
		} else {
			fileList.allowUpload = true;
			$("#upload").click();
			$(this).html('<i class="glyphicon glyphicon-pause"></i><span>暂停</span>');
			$("#speed").show();
		}
	})

	$("#upload").click(function() {
		if (!fileList.allowUpload) {
			return false;
		}
		fileList.allowUpload = false;
		var starSize = 0,
			endSize = 0;
		$(this).attr("disabled", "disabled");
		$("#cls").attr("disabled", "disabled").next().attr("disabled", "disabled");
		$("#tag").attr("disabled", "disabled").css("width", "20%");
		$("#pause").show();
		$("#speed").show();
		speedTime = setInterval(function() {
			var val = ((endSize - starSize) / 1024).toFixed(1);
			starSize = endSize;
			$("#speed").html(val + " kb/s");
		}, 1000);
		var uploadFile = function(file) {
			var progressBar = $("#files-list .progress-bar");
			fileList.list = $("#files-list tr");
			var ele = $(fileList.list[fileList.num]).find("td"),
				re = /(?:\.([^.]+))?$/,
				//ext = re.exec(file.name);
				ext = file.type.replace(/.+\//, '');
			var options = {
				endpoint: 'http://upload.polyv.net:1080/files/',
				resetBefore: false,
				resetAfter: true,
				title: $(ele[0]).find("p").text(),
				tag: $("#tag").val(),
				desc: $(ele[1]).children().val(),
				ext: ext,
				cataid: $("#cls").attr("data-cataid"),
				ts: polyvVideo.ts,
				hash: polyvVideo.hash,
				userid: polyvVideo.userid
			};
			$(fileList.list[fileList.num]).addClass("uploading");
			$(ele).find("textarea").attr("disabled", "disabled");
			$(ele[0]).removeClass("titledes");

			upload = polyv.upload(file, options)
				.fail(function(error) {
					console.log(error);
				})
				.progress(function(e, bytesUploaded, bytesTotal) {
					endSize = bytesUploaded;
					var percentage = (bytesUploaded / bytesTotal * 100).toFixed(2);
					$(ele).find(".progress-bar").css('width', percentage + '%');
					console.log(bytesUploaded, bytesTotal, percentage + '%');

				})
				.done(function() {
					$($(progressBar)[fileList.num]).removeClass("active");
					$(fileList.list[fileList.num]).removeClass("uploading");
					var done = "<i class='glyphicon glyphicon-ok ok'></i>";
					$($(".bar")[fileList.num]).html(done);
					fileList.num++;
					if (fileList.num < fileList.files.length) {
						uploadFile(fileList.files[fileList.num]);
					} else {
						$(this).removeAttr("disabled");
						$("#pause").hide();
						clearInterval(speedTime);
						$("#speed").html("上传完毕！");
						alert("上传完毕");
						console.log("done");
						return
					}
				});

		}
		uploadFile(fileList.files[fileList.num]);
	})
})