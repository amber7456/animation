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
<style type="text/css">
td {
	padding: 3px 5px;
}
</style>
<body>
	<fmt:bundle basename="common">
		<div
			style="width: 900px; min-width: 900px; min-height: 600px; margin: 0 auto; padding-bottom: 20px; border: 1px solid #e2e2e2; overflow: hidden;">

			<div style="width: 890px; float: left; padding: 5px;">

				<c:choose>
					<c:when test="${animation.data['HAVE_POSTER'] eq 'YES'}">
						<div id="animation_poster"
							onclick="showPicture('${animation.data['ANIMATION_ID']}','${animation.data['ANIMATION_NAME']}')"
							style="border: 1px solid #e2e2e2; width: 400px; height: 225px; float: left; margin-left: 5px; cursor: pointer;">
							<img onload="setImgStyle(this)" alt="图片名" id="img"
								layer-src="${pageContext.request.contextPath}/poster/getPoster/${animation.data['ANIMATION_ID']}"
								style="display: none; width: 400px; height: 225px;"
								src="${pageContext.request.contextPath}/poster/getPoster/${animation.data['ANIMATION_ID']}">
						</div>
					</c:when>
					<c:otherwise>
						<div
							style="border: 1px solid #e2e2e2; width: 400px; height: 225px; float: left; margin-left: 5px;">
							<div
								style="position: relative; line-height: 225px; text-align: center; margin: 0 auto;">暂无图片</div>
						</div>
					</c:otherwise>
				</c:choose>

				<div style="width: 483px; height: 227px; margin: 0px; float: left;">
					<div
						style="height: 132px; width: 460px; margin-left: 15px; float: left; font-size: 24px;">
						<p>${animation.data['ANIMATION_NAME']}</p>
						<hr />
						<p>
							<fmt:message key="TYPE${animation.data['ANIMATION_TYPE']}" />
							&nbsp;${animation.data['ANIMATION_EPISODE']}集 &nbsp;
							<fmt:parseDate
								value="${animation.data['ANIMATION_BROADCAST_TIME']}" var="date"
								pattern="yyyy-MM-dd" />
							<fmt:formatDate value="${date}" type="date" pattern="yyyy年MM月dd日" />
							&nbsp;播出
						</p>
						<hr />
						<p>
							原作
							<fmt:message key="SOURCE${animation.data['ANIMATION_SOURCE']}" />
						</p>
						<p style="font-size: 14px; float: right;">${animation.data['LAST_UPDATE_TIME']}最后编辑</p>
					</div>
				</div>
			</div>
			<div
				style="position: relative; float: left; width: 890px; padding: 0 5px;">

				<div style="float: left; width: 890px;" id="resource_div">
					<c:forEach items="${resourceList}" var="row">

						<table style="margin-top: 10px; float: left;">
							<tr>
								<td>资源类型</td>
								<td>${row.data['RESOURCE_TYPE']}</td>
								<td>资源格式</td>
								<td>${row.data['RESOURCE_FORMAT']}</td>
								<td>分辨率</td>
								<td>${row.data['RESOURCE_IMAGE_RESOLUTION']}</td>
							</tr>
							<tr>
								<td>字幕类型</td>
								<td>${row.data['RESOURCE_SUB_TYPE']}</td>
								<td>字幕格式</td>
								<td>${row.data['RESOURCE_SUB_FORMAT']}</td>
								<td>收录时间</td>
								<td><fmt:parseDate value="${row.data['RESOURCE_TIME']}"
										var="date" pattern="yyyy-MM-dd" /> <fmt:formatDate
										value="${date}" type="date" /></td>
							</tr>
						</table>
						<table>
							<tr>
								<td>存储地址</td>
								<td colspan="5">${row.data['RESOURCE_ADDRESS']}</td>
							</tr>
							<tr>
								<td valign="top">备注</td>
								<td colspan="5"><div style="width: 750px;">${row.data['RESOURCE_REMARK']}</div></td>
							</tr>
						</table>
						<hr />
					</c:forEach>
				</div>

				<div
					style="width: 845px; margin: 10px 5px; display: block; min-height: 20px; float: left;"
					id="cv_div">
					声优
					<div>${animation.data['ANIMATION_CV']}</div>
				</div>
				<hr />
				<div
					style="width: 845px; margin: 10px 5px; display: block; min-height: 20px; float: left;"
					id="staff_div">
					制作
					<div>${animation.data['ANIMATION_STAFF']}</div>
				</div>
				<hr />
				<div
					style="width: 845px; margin: 10px 5px; display: block; min-height: 20px; float: left;"
					id="detail_div">
					详细信息
					<div>${animation.data['ANIMATION_DETAIL_INFORMATION']}</div>
				</div>
				<hr />
				<div
					style="width: 845px; margin: 10px 5px; display: block; min-height: 20px; float: left;"
					id="remark_div">
					备注说明
					<div>${animation.data['ANIMATION_REMARK']}</div>
				</div>

			</div>
		</div>
		<div class="layui-footer" style="height: 30px;"></div>
	</fmt:bundle>
</body>
<script type="text/javascript">
	$(document).ready(function() {
  
	});

	layui.use('layer', function() { //独立版的layer无需执行这一句
		var $ = layui.jquery;
		var layer = layui.layer; //独立版的layer无需执行这一句
	});

	

	function showPicture(id, name) {
		var json = {
			"title" : "", //相册标题
			"id" : 1, //相册id
			"start" : 0, //初始显示的图片序号，默认0
			"data" : [ //相册包含的图片，数组格式
			{
				//"alt" : name,
				//"pid" : 1, //图片id
				"src" : "${pageContext.request.contextPath}/poster/getPoster/"
						+ id, //原图地址
				"thumb" : "" //缩略图地址
			} ]
		}
		
		 layer.photos({
			photos : json,
			anim : 0
		//0-6的选择，指定弹出图片动画类型，默认随机
		});   
		 
	}
</script>
</html>