<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/jquery-3.1.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/system.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/layui/layui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/animation/layui/css/layui.css"
	media="all">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/animation/css/system.css">
<style>
.layui-form td {
	padding: 5px;
}
</style>
<fmt:bundle basename="common">
	<div class="layui-form form-div" style="height: 230px; display: none;">
		<form action="" id="diskEditForm">
			<c:forEach items="${diskList}" var="row">
				<div style="margin: 20px 0px 10px 100px;">
					<span style="margin-right: 10px; font-size: 18px;">${row.data['disk_name'] }</span><span>${row.data['create_time'] }服役</span>
				</div>
				<div style="margin-left: 95px;">
					<table>
						<tr>
							<td><input class="layui-input" lay-verify="disk_capacity"
								style="width: 100px;" type="text" name="disk_capacity"
								value="${row.data['disk_capacity'] }" /></td>
							<td>
								<div style="width: 100px;">
									<select name="disk_state">
										<option value="1"
											${ row.data['disk_state'] =='1' ? 'selected="selected"':'' }>服役</option>
										<option value="0"
											${ row.data['disk_state'] =='0' ? 'selected="selected"':'' }>退役</option>
									</select>
								</div>
							</td>
							<td>
								<div style="width: 100px;">
									<select name="is_full">
										<option value="1"
											${ row.data['is_full'] =='1' ? 'selected="selected"':'' }>已满</option>
										<option value="0"
											${ row.data['is_full'] =='0' ? 'selected="selected"':'' }>未满</option>
									</select>
								</div>
							</td>
							<td><div class="layui-btn layui-btn-normal" style=""
									lay-submit lay-filter="submitDiv">提交</div></td>
						</tr>
					</table>
				</div>
				<input type="hidden" name="disk_id" value="${row.data['disk_id'] }" />
			</c:forEach>
		</form>
	</div>
</fmt:bundle>
<script type="text/javascript">
	layui.use('form', function() {
		var form = layui.form;
		var layer = layui.layer;

		layer.ready(function() {
			$(".layui-form").css("display", "block");
		});

		form.render(); //更新全部
		var url = "${pageContext.request.contextPath}/diskEdit";
		form.on('submit(submitDiv)', function(data) {
			var diskEditForm = new FormData($("#diskEditForm")[0]);
			//console.log(JSON.stringify(data.field));
			$.ajax({
				url : url,
				type : 'POST',
				data : diskEditForm,
				async : false,
				contentType : false,
				processData : false,
				dataType : 'text',
				success : function(data) {
					if (data == "OK") {

						layer.alert('编辑成功！', {
							closeBtn : 0,
							anim : 5,
							icon : 6
						}, function(index) {
							var indexP = parent.layer
									.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(indexP); //再执行关闭   
							parent.tools.refresh(); // 外部调用，如子页面
							layer.close(index);
						});
					} else {
						layer.alert('编辑失败！' + data, {
							closeBtn : 0,
							anim : 6,
							icon : 5
						});
					}
				},
				error : function(data) {
					layer.alert('编辑出现异常，请稍后再试！' + JSON.stringify(data), {
						closeBtn : 0,
						anim : 6,
						icon : 5
					});
				}
			});
		});

	});
</script>