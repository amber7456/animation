var divBody = "main";

function pageHref(url, type) {

	layui.use([ 'layer' ], function() { // 独立版的layer无需执行这一句
		var layer = layui.layer; // 独立版的layer无需执行这一句
		var loadIndex = layer.load(1, {
			shade : [ 0.6, '#000' ]
		});
		$.ajax({
			url : url,
			type : type,
			dataType : 'html',
			async : false,
			success : function(data) {
				layer.close(loadIndex);
				$("#" + divBody).html("");
				$("#" + divBody).html(data);
			},
			error : function(data) {
				layer.close(loadIndex);
				$("#" + divBody).html("");
				$("#" + divBody).html(data.responseText);
			}
		});
	});
};

function pageSubmit(formId) {

	layui.use([ 'layer' ], function() { // 独立版的layer无需执行这一句
		var layer = layui.layer; // 独立版的layer无需执行这一句
		var loadIndex;
		loadIndex = layer.load(1, {
			shade : [ 0.6, '#000' ]
		});
		// 取 action 地址
		var url = $("#" + formId).attr("action");
		$.ajax({
			url : url,
			type : 'post',
			data : $('#' + formId).serialize(),
			dataType : 'html',
			async : false,
			success : function(data) {
				layer.close(loadIndex);
				$("#" + divBody).html("");
				$("#" + divBody).html(data);
			},
			error : function(data) {
				layer.close(loadIndex);
				$("#" + divBody).html("");
				$("#" + divBody).html(data.responseText);
			}
		});
	});
};

function formSubmit(formId, div) {

	layui.use([ 'layer' ], function() { // 独立版的layer无需执行这一句
		var layer = layui.layer; // 独立版的layer无需执行这一句
		var loadIndex = layer.load(1, {
			shade : [ 0.6, '#000' ]
		});
		// 取 action 地址
		var url = $("#" + formId).attr("action");
		$.ajax({
			url : url,
			type : 'post',
			data : $('#' + formId).serialize(),
			dataType : 'html',
			async : false,
			success : function(data) {
				layer.close(loadIndex);
				$("#" + div).html("");
				$("#" + div).html(data);
			},
			error : function(data) {
				layer.close(loadIndex);
				$("#" + div).html("");
				$("#" + div).html(data.responseText);
			}
		});
	});

};

function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1
			+ strDate;
	return currentdate;
}
