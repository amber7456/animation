<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/imgcontroller.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/scrolltopcontrol.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/animation/css/basicSearch.css">
<fmt:bundle basename="common">
	<div class="layui-tab layui-tab-brief" style="min-width: 900px;"
		lay-filter="searchTab">
		<ul class="layui-tab-title">
			<li lay-id="year">按年查询</li>
			<li lay-id="name">按名称查询</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item">
				<!-- 按年搜索 -->
				<!-- 查询div-->
				<div style="float: left;">
					<form class="layui-form " id="basicSearchByYearForm"
						action="basicSearch" method="POST">
						<div class="div-search" style="width: 500px; float: left;">
							<div id="search_select"
								style="width: 200px; height: 38px; float: left;">
								<!-- <input class="layui-input" id="startYear1" name="startYear1"
									type="text"> -->
								<select name="startYear" id="startYear">
									<c:forEach var="i" begin="1980" end="${nowYear}" step="1">
										<option value="${nowYear + 1980 - i }">${nowYear + 1980 - i }年</option>
									</c:forEach>
								</select>
							</div>
							<div class="layui-btn layui-btn-normal"
								onclick="basicSearchByYear();"
								style="float: left; margin-left: 10px;">
								<i class="layui-icon">&#xe615;</i>查询
							</div>
							<div class="layui-btn "
								onclick="excelDownload('excelDownloadByYear','basicSearchByYearForm')"
								style="float: left; margin-left: 10px;">
								<i class="layui-icon">&#xe62d;</i>下载
							</div>

							<input type="hidden" name="searchType" value="year">
						</div>
					</form>
				</div>
				<!-- 按年查询结果 -->
				<div style="float: left; width: 100%;" id="year_date"></div>

			</div>
			<div class="layui-tab-item">
				<!-- 按名称搜索 -->
				<div style="float: left;">
					<form class="layui-form " id="basicSearchByNameForm"
						action="basicSearch" method="POST" enctype="multipart/form-data">
						<div class="div-search" style="width: 500px; float: left;">
							<div id="search_input"
								style="width: 200px; height: 38px; float: left;">
								<input class="layui-input " placeholder="名称" autocomplete="off"
									style="float: left; width: 200px;" type="text"
									id="animation_name" name="animation_name" />
							</div>
							<div class="layui-btn layui-btn-normal"
								onclick="basicSearchByName();"
								style="float: left; margin-left: 10px;">
								<i class="layui-icon">&#xe615;</i>查询
							</div>
							<div class="layui-btn "
								onclick="excelDownload('excelDownloadByName','basicSearchByNameForm')"
								style="float: left; margin-left: 10px;">
								<i class="layui-icon">&#xe62d;</i>下载
							</div>
							<input type="hidden" name="searchType" value="name">
						</div>
					</form>
				</div>
				<!-- 按名称搜索 结束 -->
				<div style="float: left; width: 100%;" id="name_date"></div>
			</div>
		</div>
	</div>

	<input type="hidden" id="startYearHide"
		value="${searchBean.startYear }" />
	<!--  -->
	<input type="hidden" id="searchTypeHide"
		value="${searchBean.searchType }" />
	<!--  -->
	<input type="hidden" id="animation_nameHide"
		value="${searchBean.animation_name }" />
	<!--  -->
	<input type="hidden" id="nowYear" value="${nowYear }" />

</fmt:bundle>
<script type="text/javascript">
	$(document).ready(function() {
		$("#animation_name").keydown(function(event) {
			if (event.keyCode == 13) {
				if (document.activeElement.id == "animation_name") {
					basicSearchByName();
				}
				return false;
			}
		});
	});

	layui.use([ 'element', 'form', 'laydate' ], function() {
		var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
		var form = layui.form;
		var laydate = layui.laydate;

		//回显tab
		var searchType = $("#searchTypeHide").val();
		if (searchType != "") {
			if (searchType == "name") {
				$("#animation_name").val($("#animation_nameHide").val());
				element.tabChange("searchTab", searchType);
			} else {
				element.tabChange("searchTab", searchType);
			}
		}
		form.render(); //更新全部，此处只有更新一次，网页上才能正常显示

		var myDate = new Date();
		var nowYear = myDate.getFullYear();

		//年选择器
		/* laydate.render({
			elem : '#startYear1',
			type : 'year',
			//format : 'yyyy年',
			//value : '2017',
			//max : '2017' ,
			theme : '#5FB878',
			//btns : [ 'now', 'confirm' ],
			showBottom : false
		}); */

		element.on('tab(searchTab)', function(data) {
			//console.log(this); //当前Tab标题所在的原始DOM元素
			//console.log(data.index); //得到当前Tab的所在下标
			//console.log(data.elem); //得到当前的Tab大容器
			//console.log($(this).attr('lay-id'));
			var tab = $(this).attr('lay-id');
			if (typeof (tab) == "undefined") {
				$("#searchTypeHide").val("year");
			} else {
				$("#searchTypeHide").val(tab);
			}

		});

		var _tools = {
			refresh : function() {
				var searchType = $("#searchTypeHide").val();
				if (searchType != "") {
					console.log(searchType);
					if (searchType == "name") {
						basicSearchByName();
					} else {
						basicSearchByYear();
					}
				}
			}
		}

		window.tools = _tools;

	});

	function basicSearchByName() {
		$("#animation_name").val($.trim($("#animation_name").val())); // 去空格
		var animation_name = $("#animation_name").val();
		if (animation_name == "") {

			layer.alert('请输入查询条件！', {
				closeBtn : 0,
				offset : '200px',
				anim : 5,
				icon : 0
			}, function(index) {
				$("#animation_name").focus();
				layer.close(index);
			});

			return false;
		} else {
			formSubmit("basicSearchByNameForm", "name_date");
		}
	}

	function basicSearchByYear() {
		formSubmit("basicSearchByYearForm", "year_date");
	}

	function excelDownload(url, formId) {
		if (url == "excelDownloadByName") {
			$("#animation_name").val($.trim($("#animation_name").val())); // 去空格
			var animation_name = $("#animation_name").val();
			if (animation_name == "") {

				layer.alert('请输入查询条件！', {
					closeBtn : 0,
					offset : '200px',
					anim : 5,
					icon : 0
				}, function(index) {
					$("#animation_name").focus();
					layer.close(index);
				});

				return false;
			}
		}
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

	function deleteAnimation(id, name) {
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
							var searchType = $("#searchTypeHide").val();
							if (searchType != "") {
								if (searchType == "name") {
									basicSearchByName();
								} else {
									basicSearchByYear();
								}
							}

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