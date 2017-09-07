<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/imgcontroller.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/scrolltopcontrol.js"></script>
<style type="text/css">
.form-div {
	width: 900px;
	min-width: 900px;
}

.img-div {
	border: 1px solid #e2e2e2;
	width: 400px;
	height: 225px;
	float: left;
	margin-left: 5px;
}

.img-box {
	height: 225px;
	width: 400px;
	cursor: pointer;
}

.i-del {
	float: right;
	font-size: 32px;
	color: #f21111;
	cursor: pointer;
	position: absolute;
	margin-left: 370px;
	height: 28px;
	width: 28px;
	line-height: 28px;
	display: none;
}

.animation-information-div {
	width: 450px;
	height: 227px;
	margin: 0 15px;
	float: left;
}

.animation-information-div td {
	padding: 3px 5px;
}

.resource-div {
	float: left;
	margin-left: 5px;
	/* width: 895px; */
}

.resource-div td {
	padding: 3px 5px;
}

.detail-div {
	width: 845px;
	margin: 10px 10px;
	display: none;
}

.detail-div table {
	width: 100%;
}

.detail-div td {
	padding: 3px 5px;
}
</style>

<fmt:bundle basename="common">
	<form action="animationAdd" id="animationAddForm" method="post"
		class="layui-form " enctype="multipart/form-data">
		<div class="layui-form-item form-div" style="">
			<div style="padding: 5px;">
				<div class="img-div" id="img_div">
					<i class="layui-icon i-del" onclick="deleteImg(this)" id="img_del"
						style="">&#xe640;</i>
					<div onclick="inputClick(this)" class="img-box">
						<img border="0" style="margin: 0 auot;">
					</div>
					<input onchange="previewImage(this)" style="display: none;"
						type="file" name="poster_hq" id="previewImg">
				</div>
				<div class="animation-information-div">
					<div style="height: 132px;">
						<table>
							<tr>
								<td style="width: 60px;">动画名称</td>
								<td colspan="3"><input type="text" class="layui-input"
									name="animation_name" value="" autocomplete="off" /></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>播出时间</td>
								<td><input name="animation_broadcast_time"
									id="animation_broadcast_time" class="layui-input" type="text">
								</td>
								<td style="width: 30px;">集数</td>
								<td><input type="text" class="layui-input"
									name="animation_episode" value="13" /></td>
							</tr>
							<tr>
								<td>类型</td>
								<td><select name="animation_type">
										<option value="1">冬番</option>
										<option value="2">春番</option>
										<option value="3">夏番</option>
										<option value="4">秋番</option>
										<option value="5">OVA/OAD</option>
										<option value="6">映画</option>
										<option value="7">其他</option>
								</select></td>
								<td>来源</td>
								<td><select name="animation_source">
										<option value="1">漫画</option>
										<option value="2">小说</option>
										<option value="3">游戏</option>
										<option value="4">原创</option>
								</select></td>
							</tr>
						</table>
					</div>
					<div style="margin-top: 20px;"></div>
				</div>
			</div>
			<div style="position: relative; float: left; width: 100%;">
				<div>
					<div class="layui-btn layui-btn-normal" style="margin: 10px;"
						onclick="ctrlDetailDIV()">详细信息</div>
					<div class="detail-div" style="" id="detail_div">
						<table>
							<tr>
								<td>声优</td>
							</tr>
							<tr>
								<td><textarea class="layui-textarea" name="animation_cv"></textarea></td>
							</tr>
							<tr>
								<td>制作</td>
							<tr>
								<td><textarea class="layui-textarea" name="animation_staff"></textarea></td>
							</tr>
							<tr>
								<td>详细信息</td>
							</tr>
							<tr>
								<td><textarea class="layui-textarea"
										name="animation_detail_information"></textarea></td>
							</tr>
							<tr>
								<td>备注说明</td>
							</tr>
							<tr>
								<td><textarea class="layui-textarea"
										name="animation_remark"></textarea></td>
							</tr>
						</table>
					</div>
				</div>
				<div>
					<div class="layui-btn layui-btn-normal" id="addResource"
						style="margin: 10px;" onclick="addResourceTable()">添加收录信息</div>
					<div class="resource-div" style="" id="resource_div"></div>
				</div>
			</div>
			<div class="layui-btn layui-btn-normal" lay-submit
				lay-filter="submitDiv"
				style="margin: 10px 100px 10px 10px; float: right;">保存</div>
			<!--  -->
		</div>
	</form>
	<!-- 模板 -->
	<div style="display: none" id="resource_model">
		<div style="display: none">
			<table style="margin-top: 20px; float: left;" id="s">
				<tr>
					<td>资源类型</td>
					<td style="width: 195px;"><select name="resource_type">
							<option value="TV">TV</option>
							<option value="BD">BD</option>
					</select></td>
					<td>资源格式</td>
					<td style="width: 195px;"><select name="resource_format"
						id="XXX">
							<option value="MP4">MP4</option>
							<option value="MKV">MKV</option>
							<option value="RMVB">RMVB</option>
					</select></td>
					<td>分辨率</td>
					<td style="width: 195px;"><select
						name="resource_image_resolution">
							<option value="480P">480P</option>
							<option value="576P">576P</option>
							<option value="720P" selected="selected">720P</option>
							<option value="1080P">1080P</option>
					</select></td>
				</tr>
				<tr>
					<td>字幕类型</td>
					<td style="width: 195px;"><select name="resource_sub_type">
							<option value="内嵌">内嵌</option>
							<option value="封装">封装</option>
							<option value="外挂">外挂</option>
					</select></td>
					<td>字幕格式</td>
					<td style="width: 195px;"><select name="resource_sub_format">
							<option value="NO">NO</option>
							<option value="ASS">ASS</option>
							<option value="STR">STR</option>
					</select></td>
					<td>收录时间</td>
					<td style="width: 195px;"><input type="text"
						class="layui-input" autocomplete="off" id="resource_time_id"
						name="resource_time" value="" /> <!--   --></td>
				</tr>
				<tr>
					<td>存储地址</td>
					<td colspan="5" style="width: 737px;"><select
						name="resource_address">
							<c:forEach items="${diskList }" var="row">
								<option value="${row.data['disk_name'] }">${row.data['disk_name'] }</option>
							</c:forEach>
					</select> <!-- <input type="text"
						class="layui-input" style="" name="resource_address" value="" /> -->
					</td>
				</tr>
				<tr>
					<td valign="top">备注</td>
					<td colspan="5" style="width: 737px;"><div
							style="margin-bottom: 20px;">
							<textarea class="layui-textarea" name="resource_remark" style=""></textarea>
						</div></td>
				</tr>
			</table>
			<i class="layui-icon" onclick="deleteResourceTable(this)"
				style="float: left; margin-left: 15px; margin-top: 20px; font-size: 32px; color: #f21111; cursor: pointer;">&#xe640;</i>
		</div>
	</div>
</fmt:bundle>

<script type="text/javascript">
	$(document).ready(function() {

		$("#img_div").mouseover(function() {
			var imgsrc = $("#img_del").next().find('img').attr('src');
			if (typeof (imgsrc) != "undefined") {
				$("#img_del").css("display", "block");
			}
		});

		$("#img_div").mouseout(function() {
			var imgsrc = $("#img_del").next().find('img').attr('src');
			$("#img_del").css("display", "none");
		});

	});

	layui.use([ 'form', 'laydate' ],
			function() {
				var form = layui.form;
				var laydate = layui.laydate;

				layer.ready(function() {
					$("#animation_broadcast_time").val(getNowFormatDate());//设置初试时间 
					addResourceTable(form);
				});

				window.addResourceTable = function() {
					addResourceTable(form);
				}

				function addResourceTable(form) {
					var timestamp = Date.parse(new Date());
					timestamp = timestamp / 1000 + "";
					$("#resource_model").find('table').attr('id', timestamp);
					$("#resource_model").find('div').attr('id',
							'resource' + timestamp);
					var newTable = $("#resource_model").html();

					newTable = newTable.replace(/resource_time_id/g,
							"resource_time_id" + timestamp);

					$("#resource_div").append(newTable);
					$("#resource_div").find("#" + timestamp).find(
							"#resource_time_id" + timestamp).val(
							getNowFormatDate());

					$("#resource" + timestamp).slideDown(300);

					lay('#resource_time_id' + timestamp).each(function() {
						laydate.render({
							elem : this,
							theme : '#393D49',
							btns : [ 'now', 'confirm' ]
						});
					});

					form.render(); //更新全部

				}

				var url = "${pageContext.request.contextPath}/animationAdd";
				form.on('submit(submitDiv)', function(data) {
					//console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
					//console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
					//console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
					var animationAddForm = new FormData(
							$("#animationAddForm")[0]);
					$.ajax({
						url : url,
						type : 'POST',
						data : animationAddForm,// 
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
									toAnimationAdd();
									layer.close(index);
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
							layer.alert('添加出现异常，请稍后再试！' + JSON.stringify(data),
									{
										closeBtn : 0,
										offset : '200px',
										anim : 6,
										icon : 5
									});
						}
					});
				});

				form.render();

				//常规用法
				laydate.render({
					elem : '#animation_broadcast_time',
					theme : '#393D49',
					btns : [ 'now', 'confirm' ]
				});

			});
	/**
	 * 控制详情展开
	 */
	function ctrlDetailDIV() {
		$("#detail_div").toggle(300);
	}

	function deleteResourceTable(i) {
		$(i).parent().slideUp(300, function() {
			$(i).parent().remove();
		});
	}

	function deleteImg(i) {
		//var imgUrl = "${pageContext.request.contextPath}/animation/images/photo_icon.png"
		$(i).next().find('img').removeAttr("src");
		var obj = document.getElementById('previewImg');
		obj.outerHTML = obj.outerHTML;
	}
</script>
