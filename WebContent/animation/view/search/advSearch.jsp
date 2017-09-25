<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/imgcontroller.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/scrolltopcontrol.js"></script>
<style type="text/css">
.form-div {
	float: left;
	margin: 10px;
}

.form-div td {
	padding: 0px 5px 0px 0px;
}

.noselect {
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

.search-item {
	display: inline-block;
	padding-left: 8px;
	padding-right: 8px;
	border-radius: 4px;
	margin-right: 15px;
	height: 38px;
	line-height: 38px;
	cursor: pointer;
	transition: .1s;
}

.search-item span:hover {
	color: #1E9FFF;
}

.search-item-click {
	display: inline-block;
	padding-left: 8px;
	padding-right: 8px;
	border-radius: 4px;
	margin-right: 15px;
	height: 38px;
	line-height: 38px;
	background-color: #5FB878;
	color: #FFF;
	cursor: pointer;
}

.search-checkBox {
	display: inline-block;
	padding-left: 8px;
	padding-right: 8px;
	border-radius: 4px;
	margin-right: 5px;
	height: 36px;
	line-height: 36px;
	border: 1px solid #e6e6e6;
	cursor: pointer;
	color: #555;
}

.search-checkBox-click {
	display: inline-block;
	padding-left: 8px;
	padding-right: 8px;
	border-radius: 4px;
	margin-right: 5px;
	height: 36px;
	line-height: 36px;
	border: 1px solid #5FB878;
	background-color: #5FB878;
	cursor: pointer;
	color: #FFF;
}
</style>
<form action="advSearch" id="advSearchForm" class="layui-form"
	method="post" enctype="multipart/form-data">
	<div class="form-div" style="width: 1200px;">
		<div style="display: block; margin: 5px 0px;">
			<div class="search-item-click noselect">
				<span onclick="searchClick(this)" id="ALL">全部类型</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="1">冬番</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="2">春番</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="3">夏番</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="4">秋番</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="5">OVA/OAD</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="6">映画</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="7">其他</span>
			</div>
			<input type="hidden" id="animation_type" name="animation_type"
				value="ALL">
		</div>
		<div style="display: block; margin: 5px 0px; width: 400px;">

			<div class="search-item-click noselect">
				<span onclick="searchClick(this)" id="ALL">全部来源</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="1">漫画</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="2">小说</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="3">游戏</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="4">原创</span>
			</div>
			<input type="hidden" id="animation_source" name="animation_source"
				value="ALL">
		</div>
				<div style="display: block; margin: 5px 0px; width: 400px;">

			<div class="search-item-click noselect">
				<span onclick="searchClick(this)" id="ALL">全部资源</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="0">普通资源</span>
			</div>
			<div class="search-item noselect">
				<span onclick="searchClick(this)" id="1">BD资源</span>
			</div>
			<input type="hidden" id="isBD" name="isBD"
				value="ALL">
		</div>
		
		<div style="display: block; margin: 5px 0px;">
			<div style="display: inline-block;">
				<table>
					<tr> 
						<td><input class="layui-input" id="yearRange" name="yearRange"
							placeholder="年范围" type="text" readonly="readonly"></td>
						<td width="200"><select name="disk_name" id="">
								<option value="ALL">全部硬盘</option>
								<c:forEach items="${diskList}" var="row">
									<option value="${row.data['disk_name'] }">${row.data['disk_name'] }</option>
								</c:forEach>
						</select></td>
						<td width="300"><input name="animation_name"
							autocomplete="off" placeholder="名称" id="animationName"
							class="layui-input" type="text"></td>
						<td>
							<div class="layui-btn layui-btn-normal" style=""
								onclick="advSearch()">
								<i class="layui-icon">&#xe615;</i>查询
							</div>
						</td>
						<td>
							<div class="layui-btn "
								onclick="excelDownload('excelDownloadAdv','advSearchForm')">
								<i class="layui-icon">&#xe62d;</i>下载
							</div>
						</td>

					</tr>
				</table>
			</div>
		</div>
		<%-- 
		<table>
			<tr>
				<td width="100"><select name="startYear" id="startYear">
						<c:forEach var="i" begin="1980" end="${nowYear}" step="1">
							<c:choose>
								<c:when test="${(nowYear + 1980 - i) == (nowYear-5) }">
									<option value="${nowYear + 1980 - i }" selected="selected">${nowYear + 1980 - i }年</option>
								</c:when>
								<c:otherwise>
									<option value="${nowYear + 1980 - i }">${nowYear + 1980 - i }年</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
				<td>至</td>
				<td width="100"><select name="endYear" id="endYear">
						<c:forEach var="i" begin="1980" end="${nowYear}" step="1">
							<option value="${nowYear + 1980 - i }">${nowYear + 1980 - i }年</option>
						</c:forEach>
				</select></td> 
			</tr>
		</table>--%>
	</div>
</form>
<div style="min-width: 900px;" id="advSearch_data"></div>

<script>
	function searchClick(d) {

		$(d).parent().siblings('div').removeClass("search-item-click");
		$(d).parent().siblings('div').addClass("search-item");

		$(d).parent().addClass("search-item-click");
		$(d).parent().removeClass("search-item");
		$(d).parent().nextAll('input').val($(d).attr("id"));
	}

	function checkBoxClick(d) {

		if ($(d).hasClass("search-checkBox")) {
			$(d).addClass("search-checkBox-click");
			$(d).removeClass("search-checkBox");
			$(d).find('input').val("1");
		} else {
			$(d).addClass("search-checkBox");
			$(d).removeClass("search-checkBox-click");
			$(d).find('input').val("0");
		}

	}

	layui.use([ 'form', 'laydate' ], function() {
		var form = layui.form;
		var laydate = layui.laydate;
		form.render(); //更新全部，此处只有更新一次，网页上才能正常显示

		var _tools = {
			refresh : function() {
				advSearch();
			}
		}

		window.tools = _tools;

		var myDate = new Date();
		var nowYear = myDate.getFullYear();

		//年范围
		laydate.render({
			elem : '#yearRange',
			type : 'year',
			range : true,
			format : 'yyyy年', //可任意组合yyyy年MM月dd日
			max : nowYear + '年',
			value : nowYear - 5 + '年 - ' + nowYear + '年',
			theme : '#5FB878'
		});
	});

	function advSearch() {
		$("#animationName").val($.trim($("#animationName").val())); // 去空格
		formSubmit("advSearchForm", "advSearch_data");
	}

	$(document).ready(function() {
		$("#animationName").keydown(function(e) {
			if (e.which == 13) {
				advSearch();
			}
		});
	})

	function excelDownload(url, formId) {
		var o_url = $("#" + formId).attr("action");
		$("#" + formId).attr("action", url);
		$("#" + formId).submit();
		$("#" + formId).attr("action", o_url);
	}

	function editAnimation(id) {
		$('#edit-list' + id).slideUp(100); //滑动消失 
		//iframe层
		var url = '${pageContext.request.contextPath}/toAnimationEdit/' + id;
		layer.open({
			type : 2,
			title : '编辑',
			maxmin : true,
			shadeClose : true,
			shade : 0.8,
			area : [ '60%', '90%' ],
			content : url
		});
	}

	function deleteAnimation(id) {
		$('#edit-list' + id).slideUp(100); //滑动消失  
		var indexConfirm = layer.confirm('确认删除' + name + '？', {
			btn : [ '是', '否' ], //按钮
			offset : '200px'
		}, function() {
			// 开遮罩层
			var loadIndex = layer.load(1, {
				shade : [ 0.3, '#000' ]
			// 透明度 背景色
			});
			// 关闭询问框
			layer.close(indexConfirm);
			//删除 
			var url = "${pageContext.request.contextPath}/animationDelete";
			$.ajax({
				url : url,
				type : 'POST',
				data : {
					animation_id : id
				},
				dataType : 'text',
				success : function(data) {
					//关闭遮罩层
					layer.close(loadIndex);
					if (data == "OK") {
						layer.alert('删除成功', {
							closeBtn : 0,
							offset : '200px',
							icon : 6,
							anim : 0
						//动画类型
						}, function(index) {
							layer.close(index);
							advSearch();
						});
					} else {
						layer.alert('删除失败！' + data, {
							closeBtn : 0,
							offset : '200px',
							anim : 6
						//动画类型
						});
					}
				},
				error : function(data) {
					//关闭遮罩层
					layer.close(loadIndex);
					layer.alert('删除出现异常，请稍后再试！' + JSON.stringify(data), {
						closeBtn : 0,
						offset : '200px',
						anim : 6
					//动画类型
					});

				}
			});

		}, function() {
			//不删除
		});
	}

</script>
