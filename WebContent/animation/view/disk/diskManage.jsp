<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/scrolltopcontrol.js"></script>

<style>
.div-diskAdd td {
	padding: 0px 5px 5px 5px;
}
</style>
<fmt:bundle basename="common">
	<div class="layui-tab layui-tab-brief" style="min-width: 900px;"
		lay-filter="tab">
		<ul class="layui-tab-title">
			<li lay-id="diskList" class="layui-this">硬盘列表</li>
			<li lay-id="diskAdd">添加硬盘</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">
				<!-- 查询div-->
				<div style="float: left;">
					<form class="layui-form " id="diskSearch" action="diskSearch"
						method="POST">
						<div class="div-search" style="width: 500px; float: left;">
							<div style="width: 200px; height: 38px; float: left;">
								<input class="layui-input " placeholder="名称" autocomplete="off"
									style="float: left; width: 200px;" type="text" id="disk_name"
									name="disk_name" />
							</div>
							<div class="layui-btn layui-btn-normal" onclick="diskSearch();"
								style="float: left; margin-left: 10px;">
								<i class="layui-icon">&#xe615;</i>查询
							</div>
							<input type="hidden" name="searchType" value="year">
						</div>
					</form>
				</div>
				<!-- 按年查询结果 -->
				<div style="float: left; width: 100%;" id="diskList"></div>

			</div>
			<div class="layui-tab-item">

				<div style="float: left;">
					<form class="layui-form " id="diskAddForm" action="diskAdd"
						method="POST" enctype="multipart/form-data">
						<div class="div-diskAdd" style="float: left;">
							<table>
								<tr>
									<td>硬盘名称</td>
									<td><input class="layui-input" lay-verify="disk_name"
										style="float: left; width: 200px;" type="text"
										name="disk_name" /></td>
								</tr>
								<tr>
									<td>硬盘容量</td>
									<td><input class="layui-input" lay-verify="disk_capacity"
										style="float: left; width: 200px;" type="text"
										name="disk_capacity" /></td>
								</tr>
							</table>
							<div class="layui-btn layui-btn-normal" lay-submit lay-filter="submitDiv"
								style="float: right; margin-right: 10px;">提交</div>

						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</fmt:bundle>
<script type="text/javascript">
	/* $(document).ready(function() {
		$("#disk_name").keydown(function(event) {
			if (event.keyCode == 13) {
				if (document.activeElement.id == "disk_name") {
					diskSearch();
				}
				return false;
			}
		});
	}); */

	layui.use([ 'element', 'form' ], function() {
		var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
		var form = layui.form;
		//自定义验证规则
		form.verify({
			disk_name : function(value) {
				if (value.trim().length < 1) {
					return '硬盘名不能为空';
				}
			},
			disk_capacity : function(value) {
				if (value.trim().length < 1) {
					return '硬盘容量不能为空';
				}
			},
		});

		var url = "${pageContext.request.contextPath}/diskAdd";
		form.on('submit(submitDiv)', function(data) {
			//console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
			//console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
			//console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
			var diskAddForm = new FormData($("#diskAddForm")[0]);
			$.ajax({
				url : url,
				type : 'POST',
				data : diskAddForm,// 
				async : false,/*  */
				contentType : false, /*  */
				processData : false,
				dataType : 'text',
				success : function(data) {
					if (data == "OK") {
						layer.alert('添加成功！', {
							closeBtn : 0,
							offset : '200px',
							anim : 5,
							icon : 6
						}, function(index) {
							layer.close(index);
							element.tabChange("tab", "diskList");
							diskSearch();
						});
					} else {
						layer.alert('添加失败！' + data, {
							closeBtn : 0,
							offset : '200px',
							anim : 6,
							icon : 5
						});
					}
				},
				error : function(data) {
					layer.alert('添加出现异常，请稍后再试！' + JSON.stringify(data), {
						closeBtn : 0,
						offset : '200px',
						anim : 6,
						icon : 5
					});
				}
			});
		});

		form.render(); //更新全部，此处只有更新一次，网页上才能正常显示

		var _tools = {
			refresh : function() {
				diskSearch();
			}
		}

		window.tools = _tools;

	});

	function diskSearch() {
		$("#disk_name").val($("#disk_name").val().trim());
		formSubmit("diskSearch", "diskList");
	}
</script>