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
</style>
<form action="advSearch" id="advSearchForm" class="layui-form"
	method="post" enctype="multipart/form-data">
	<div class="form-div" style="width: 1000px;">
		<table>
			<tr>
				<td width="100"><select name="startYear" id="startYear">
						<c:forEach var="i" begin="1900" end="${nowYear}" step="1">
							<c:choose>
								<c:when test="${(nowYear + 1900 - i) == (nowYear-5) }">
									<option value="${nowYear + 1900 - i }" selected="selected">${nowYear + 1900 - i }年</option>
								</c:when>
								<c:otherwise>
									<option value="${nowYear + 1900 - i }">${nowYear + 1900 - i }年</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
				<td>至</td>
				<td width="100"><select name="endYear" id="endYear">
						<c:forEach var="i" begin="1900" end="${nowYear}" step="1">
							<option value="${nowYear + 1900 - i }">${nowYear + 1900 - i }年</option>
						</c:forEach>
				</select></td>
				<td width="100"><select name="animation_type"
					id="animationType">
						<option value="ALL">全部</option>
						<option value="1">冬番</option>
						<option value="2">春番</option>
						<option value="3">夏番</option>
						<option value="4">秋番</option>
						<option value="5">OVA/OAD</option>
						<option value="6">映画</option>
						<option value="7">其他</option>
				</select></td>
				<td width="400"><input name="animation_name" autocomplete="off"
					placeholder="名称" id="animationName" class="layui-input" type="text"></td>
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
</form>
<div style="min-width: 900px;" id="advSearch_data"></div>

<script>
	layui.use('form', function() {
		var form = layui.form;
		form.render(); //更新全部，此处只有更新一次，网页上才能正常显示

		var _tools = {
			refresh : function() {
				advSearch();
			}
		}

		window.tools = _tools;

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

	/* var startYear = $("#startYearHide").val();
	if (startYear != "") {
		$("#startYear").val(startYear);
	}

	var endYear = $("#endYearHide").val();
	if (endYear != "") {
		$("#endYear").val(endYear);
	}

	var animationType = $("#animationTypeHide").val();
	if (animationType != "") {
		$("#animationType").val(animationType);
	}

	var animationName = $("#animationNameHide").val();
	if (animationName != "") {
		$("#animationName").val(animationName);
	} */
</script>
