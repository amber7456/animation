<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>animation add</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/jquery-3.1.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/system.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/imgcontroller.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/layui/layui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/animation/layui/css/layui.css"
	media="all">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/animation/css/system.css">
</head>
<script type="text/javascript">
	
</script>
<style type="text/css">
html {
	overflow-y: scroll;
}

:root {
	overflow-y: auto;
	overflow-x: hidden;
}

:root body {
	position: absolute;
}

body {
	width: 100vw;
	overflow: hidden;
	display: none;
}

.form-div {
	width: 900px;
	min-width: 900px;
	min-height: 600px;
	border: 1px solid #e2e2e2;
	overflow: hidden;
	margin: 0 auto;
	padding-bottom: 100px;
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
<body>
	<fmt:bundle basename="common">
		<form action="animationEdit" id="animationEditForm" method="post"
			enctype="multipart/form-data">
			<div class="layui-form form-div">
				<div style="padding: 5px;">
					<div class="img-div" id="img_div">
						<i class="layui-icon i-del" onclick="deleteImg(this)" id="img_del">&#xe640;</i>
						<div onclick="inputClick(this)"
							style="height: 225px; width: 400px; cursor: pointer;">
							<c:choose>
								<c:when test="${animation.data['HAVE_POSTER'] eq 'YES' }">
									<img border="0" style="margin: 0 auot;" width="400"
										height="225" onload="setImgStyle(this)"
										src="${pageContext.request.contextPath}/poster/getPoster/${animation.data['ANIMATION_ID']}">
								</c:when>
								<c:otherwise>
									<img border="0" style="margin: 0 auot; display: none;"
										width="180" height="180">
								</c:otherwise>
							</c:choose>
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
										name="animation_name"
										value="${animation.data['ANIMATION_NAME']}" /></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>播出时间</td>
									<td><fmt:parseDate
											value="${animation.data['ANIMATION_BROADCAST_TIME']}"
											var="date" pattern="yyyy-MM-dd" /> <!--  --> <input
										class="layui-input" name="animation_broadcast_time"
										id="animation_broadcast_time"
										value='<fmt:formatDate pattern="yyyy-MM-dd" 
                                           value="${date}" />'
										type="text"></td>
									<td style="width: 30px;">集数</td>
									<td><input type="text" class="layui-input"
										name="animation_episode"
										value="${animation.data['ANIMATION_EPISODE']}" /></td>
								</tr>
								<tr>
									<td>类型</td>
									<td><select name="animation_type">
											<option value="1"
												${ animation.data['ANIMATION_TYPE'] =='1' ? 'selected="selected"':'' }>冬番</option>
											<option value="2"
												${ animation.data['ANIMATION_TYPE'] =='2' ? 'selected="selected"':'' }>春番</option>
											<option value="3"
												${ animation.data['ANIMATION_TYPE'] =='3' ? 'selected="selected"':'' }>夏番</option>
											<option value="4"
												${ animation.data['ANIMATION_TYPE'] =='4' ? 'selected="selected"':'' }>秋番</option>
											<option value="5"
												${ animation.data['ANIMATION_TYPE'] =='5' ? 'selected="selected"':'' }>OVA/OAD</option>
											<option value="6"
												${ animation.data['ANIMATION_TYPE'] =='6' ? 'selected="selected"':'' }>映画</option>
											<option value="7"
												${ animation.data['ANIMATION_TYPE'] =='7' ? 'selected="selected"':'' }>其他</option>
									</select></td>
									<td>原作</td>
									<td><select name="animation_source">
											<option value="1"
												${ animation.data['ANIMATION_SOURCE'] =='1' ? 'selected="selected"':'' }>漫画</option>
											<option value="2"
												${ animation.data['ANIMATION_SOURCE'] =='2' ? 'selected="selected"':'' }>轻小说</option>
											<option value="3"
												${ animation.data['ANIMATION_SOURCE'] =='3' ? 'selected="selected"':'' }>游戏</option>
											<option value="4"
												${ animation.data['ANIMATION_SOURCE'] =='4' ? 'selected="selected"':'' }>原创</option>
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
						<div class="detail-div" id="detail_div">
							<table>
								<tr>
									<td>声优</td>
								</tr>
								<tr>
									<td><textarea class="layui-textarea" name="animation_cv"
											style="">${animation.data['ANIMATION_CV']}</textarea></td>
								</tr>
								<tr>
									<td>制作</td>
								</tr>
								<tr>
									<td><textarea class="layui-textarea"
											name="animation_staff" style="">${animation.data['ANIMATION_STAFF']}</textarea></td>
								</tr>
								<tr>
									<td>详细信息</td>
								</tr>
								<tr>
									<td><textarea class="layui-textarea"
											name="animation_detail_information" style="">${animation.data['ANIMATION_DETAIL_INFORMATION']}</textarea>
									</td>
								</tr>
								<tr>
									<td>备注说明</td>
								</tr>
								<tr>
									<td><textarea class="layui-textarea"
											name="animation_remark" style="">${animation.data['ANIMATION_REMARK']}</textarea></td>
								</tr>
							</table>
						</div>
					</div>
					<div>
						<div class="layui-btn layui-btn-normal" style="margin: 10px;"
							onclick="addResourceTable()">添加收录信息</div>
						<div class="resource-div" id="resource_div">

							<c:forEach items="${resourceList}" var="row">
								<div>
									<table style="margin-top: 20px; float: left;" id="s">
										<tr>
											<td>资源类型</td>
											<td style="width: 195px;"><select name="resource_type">
													<option value="TV"
														${ row.data['RESOURCE_TYPE'] =='TV' ? 'selected="selected"':'' }>TV</option>
													<option value="BD"
														${ row.data['RESOURCE_TYPE'] =='BD' ? 'selected="selected"':'' }>BD</option>
											</select></td>

											<td>资源格式</td>
											<td style="width: 195px;"><select name="resource_format"
												id="XXX">
													<option value="MP4"
														${ row.data['RESOURCE_FORMAT'] =='MP4' ? 'selected="selected"':'' }>MP4</option>
													<option value="MKV"
														${ row.data['RESOURCE_FORMAT'] =='MKV' ? 'selected="selected"':'' }>MKV</option>
													<option value="RMVB"
														${ row.data['RESOURCE_FORMAT'] =='RMVB' ? 'selected="selected"':'' }>RMVB</option>
													<option value="AVI"
														${ row.data['RESOURCE_FORMAT'] =='AVI' ? 'selected="selected"':'' }>RMVB</option>
											</select></td>

											<td>分辨率</td>
											<td style="width: 195px;"><select
												name="resource_image_resolution">
													<option value="480P"
														${ row.data['RESOURCE_IMAGE_RESOLUTION'] =='480P' ? 'selected="selected"':'' }>480P</option>
													<option value="576P"
														${ row.data['RESOURCE_IMAGE_RESOLUTION'] =='576P' ? 'selected="selected"':'' }>576P</option>
													<option value="720P"
														${ row.data['RESOURCE_IMAGE_RESOLUTION'] =='720P' ? 'selected="selected"':'' }>720P</option>
													<option value="1080P"
														${ row.data['RESOURCE_IMAGE_RESOLUTION'] =='1080P' ? 'selected="selected"':'' }>1080P</option>
											</select></td>
										</tr>
										<tr>
											<td>字幕类型</td>
											<td style="width: 195px;"><select
												name="resource_sub_type">
													<option value="内嵌"
														${ row.data['RESOURCE_SUB_TYPE'] =='内嵌' ? 'selected="selected"':'' }>内嵌</option>
													<option value="封装"
														${ row.data['RESOURCE_SUB_TYPE'] =='封装' ? 'selected="selected"':'' }>封装</option>
													<option value="外挂"
														${ row.data['RESOURCE_SUB_TYPE'] =='外挂' ? 'selected="selected"':'' }>外挂</option>
											</select></td>

											<td>字幕格式</td>
											<td style="width: 195px;"><select
												name="resource_sub_format">
													<option value="no"
														${ row.data['RESOURCE_SUB_FORMAT'] =='no' ? 'selected="selected"':'' }>NO</option>
													<option value="ass"
														${ row.data['RESOURCE_SUB_FORMAT'] =='ass' ? 'selected="selected"':'' }>ASS</option>
													<option value="str"
														${ row.data['RESOURCE_SUB_FORMAT'] =='str' ? 'selected="selected"':'' }>STR</option>
											</select></td>
											<td>收录时间</td>
											<td style="width: 195px;"><fmt:parseDate
													value="${row.data['RESOURCE_TIME']}" var="RESOURCE_TIME"
													pattern="yyyy-MM-dd" /> <!--  --> <input type="text"
												class="layui-input" autocomplete="off" id="resource_time"
												name="resource_time"
												value="<fmt:formatDate pattern="yyyy-MM-dd" 
                                           value="${RESOURCE_TIME}" />" /></td>
											<!--  -->
										</tr>
										<tr>
											<td>存储地址</td>
											<td colspan="5" style="width: 737px;"><select
												name="resource_address">
													<c:forEach items="${diskList }" var="diskRow">
														<option value="${diskRow.data['disk_name'] }"
															${ row.data['RESOURCE_ADDRESS'] == diskRow.data['disk_name'] ? 'selected="selected"':'' }>${diskRow.data['disk_name'] }</option>
													</c:forEach>
											</select> <%-- 	<input type="text"
												class="layui-input" style="width: 750px;"
												name="resource_address"
												value="${row.data['RESOURCE_ADDRESS']}" /> --%></td>
										</tr>
										<tr>
											<td valign="top">备注</td>
											<td colspan="5" style="width: 737px;"><div
													style="margin-bottom: 20px; width: 750px;">
													<textarea class="layui-textarea" id="LAY_editor6"
														name="resource_remark" style="">${row.data['RESOURCE_REMARK']}</textarea>
												</div></td>
										</tr>
									</table>
									<i class="layui-icon" onclick="deleteResourceTable(this)"
										style="float: left; margin-left: 15px; margin-top: 20px; font-size: 32px; color: #f21111; cursor: pointer;">&#xe640;</i>
								</div>
							</c:forEach>

						</div>
					</div>
				</div>
				<div class="layui-btn layui-btn-normal"
					style="margin: 10px 100px 10px 10px; float: right;" lay-submit
					lay-filter="submitDiv">保存</div>
				<!--  -->
			</div>
			<input type="hidden" id="posterUpdateFlag" name="posterUpdateFlag"
				value="0" />
			<!--  -->
			<input type="hidden" id="" name="animation_id"
				value="${animation.data['ANIMATION_ID']}" />
			<!--  -->
			<input type="hidden" id="" name="have_poster"
				value="${animation.data['HAVE_POSTER']}" />

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
								<option value="no">NO</option>
								<option value="ass">ASS</option>
								<option value="str">STR</option>
						</select></td>
						<td>收录时间</td>
						<td style="width: 195px;"><input type="text" value=""
							class="layui-input" id="resource_time_id" name="resource_time" /></td>
						<!--  -->
					</tr>
					<tr>
						<td>存储地址</td>
						<td colspan="5" style="width: 737px;"><select
							name="resource_address">
								<c:forEach items="${diskList }" var="row">
									<option value="${row.data['disk_name'] }">${row.data['disk_name'] }</option>
								</c:forEach>
						</select> <!-- <input type="text"
							class="layui-input" style="width: 750px;" name="resource_address"
							value="" /> --></td>
					</tr>
					<tr>
						<td valign="top">备注</td>
						<td colspan="5" style="width: 737px;"><div
								style="margin-bottom: 20px; width: 750px;">
								<textarea class="layui-textarea" id="LAY_editor6"
									name="resource_remark" style=""></textarea>
							</div></td>
					</tr>
				</table>
				<i class="layui-icon" onclick="deleteResourceTable(this)"
					style="float: left; margin-left: 15px; margin-top: 20px; font-size: 32px; color: #f21111; cursor: pointer;">&#xe640;</i>
			</div>

		</div>
	</fmt:bundle>
</body>
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

	layui.use([ 'form', 'layedit', 'laydate' ],
			function() {
				var form = layui.form;
				var layer = layui.layer;
				var layedit = layui.layedit;
				var laydate = layui.laydate;

				layer.ready(function() {
					$("body").css("display", "block");
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
							getNowFormatDate()); //

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

				var url = "${pageContext.request.contextPath}/animationEdit";
				form.on('submit(submitDiv)', function(data) {
					var animationEditForm = new FormData(
							$("#animationEditForm")[0]);

					$.ajax({
						url : url,
						type : 'POST',
						data : animationEditForm,//$('#animationAddForm').serialize(),
						async : false,/*  */
						/* cache : false, */
						contentType : false, /*  */
						processData : false,
						dataType : 'text',
						success : function(data) {
							if (data == "OK") {

								layer.alert('编辑成功！', {
									closeBtn : 0,
									offset : '200px',
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
									offset : '200px',
									anim : 6,
									icon : 5
								});
							}
						},
						error : function(data) {
							layer.alert('编辑出现异常，请稍后再试！' + JSON.stringify(data),
									{
										closeBtn : 0,
										offset : '200px',
										anim : 6,
										icon : 5
									});
						}
					});
				});

				//常规用法
				laydate.render({
					elem : '#animation_broadcast_time',
					theme : '#393D49',
					btns : [ 'now', 'confirm' ]
				});

				lay('#resource_time').each(function() {
					laydate.render({
						elem : this,
						theme : '#393D49',
						btns : [ 'now', 'confirm' ]
					});
				});
			});

	function ctrlDetailDIV() {
		$("#detail_div").toggle(300);
	}

	function deleteResourceTable(i) {
		$(i).parent().slideUp(200, function() {
			$(i).parent().remove();
		});
	}

	function deleteImg(i) {
		$("#posterUpdateFlag").val("1");//图片有变动 
		$(i).next().find('img').removeAttr("src");
		$(i).next().find('img').css("display", "none");
		var obj = document.getElementById('previewImg');
		obj.outerHTML = obj.outerHTML;
	}
</script>
</html>