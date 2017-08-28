//图片上传插件
function inputClick(div) {
	$(div).parent().find('input').click();
}
// 图片上传插件
function previewImage(file) {
	var div = $(file).prev();// 当前元素前一个兄弟节点
	var MAXWIDTH = div.css('width').replace("px", "") * 1;
	var MAXHEIGHT = div.css('height').replace("px", "") * 1;
	var imgFlag = $(file).val();

	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG|BMP|bmp)$/.test(imgFlag)) {
		alert("图片格式不正确！图片类型必须是jpeg,jpg,png,gif中的一种")
		return false;
	}

	if (imgFlag == "" || imgFlag == null) {
		var img = $(file).parent().find('div').find('img');// document.getElementById('imghead');
		var imgUrl = "${pageContext.request.contextPath}/animation/images/photo_icon.png"
		img.css("width", MAXWIDTH - 2);// img.width = MAXWIDTH;
		img.css("height", MAXHEIGHT - 2);// img.height = MAXHEIGHT;
		img.css("marginLeft", '0px');// img.style.marginLeft = '0px';
		img.css("marginTop", '0px');// img.style.marginTop = '0px';
		img.css("display", 'block');
	} else {
		var div = $(file).parent().find('div');// document.getElementById('preview');
		if (file.files && file.files[0]) {

			var myData = new Date();
			var times = myData.getTime();// 当前时间的毫秒数
			div.html('');
			div.html('<img id="' + times + '" width=0 height=0 >');// div.innerHTML
			// = '<img
			// id="imghead1"
			// >';
			// 设定img的长宽
			var img = document.getElementById(times);
			img.onload = function() {
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
						img.naturalWidth, img.naturalHeight);// 图片原始大小
				img.width = rect.width;
				img.height = rect.height;
				img.style.marginLeft = rect.left + 'px';
				img.style.marginTop = rect.top + 'px';
				img.style.display = 'block';
			}
			var reader = new FileReader();
			if (window.FileReader) {
				reader.onload = function(evt) {
					img.src = evt.target.result;
				}
				reader.readAsDataURL(file.files[0]);
			} else {
				alert("不支持您当前使用的浏览器的图片预览!请用最新版本的Firefox，chrome或其他主流浏览器（IE除外！）");
			}

		}
	}

	//存在元素，则重置图片状态为有改动
	if (document.getElementById("posterUpdateFlag")) {
		$("#posterUpdateFlag").val("1");//图片有变动
	}
}

// 图片上传插件
function clacImgZoomParam(maxWidth, maxHeight, width, height) {
	var param = {
		top : 0,
		left : 0,
		width : width,
		height : height
	};
	if (width > maxWidth || height > maxHeight) {
		rateWidth = width / maxWidth;
		rateHeight = height / maxHeight;

		if (rateWidth > rateHeight) {
			param.width = maxWidth;
			param.height = Math.round(height / rateWidth);
		} else {
			param.width = Math.round(width / rateHeight);
			param.height = maxHeight;
		}
	}
	param.left = Math.round((maxWidth - param.width) / 2);
	param.top = Math.round((maxHeight - param.height) / 2);
	return param;
}

/**
 * 重置图片大小
 */
function setImgStyle(img) {
	var image = new Image();
	image.src = $(img).attr("src");
	var div = $(img).parent();// document.getElementById('preview');
	var MAXWIDTH = $(img).parent().css('width').replace("px", "") * 1;
	var MAXHEIGHT = $(img).parent().css('height').replace("px", "") * 1;
	var img = $(img);// document.getElementById('imghead');
	var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, image.width, image.height);
	img.css("width", rect.width);// img.width = rect.width;
	img.css("height", rect.height);// img.height = rect.height;
	img.css("marginLeft", rect.left + 'px');// img.style.marginLeft = rect.left
	// + 'px';
	img.css("marginTop", rect.top + 'px');// img.style.marginTop = rect.top +
	img.css("display", "block");// img.style.display = "block";
}