<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>animation</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/animation/js/jquery-3.1.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/animation/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/animation/js/scrolltopcontrol.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/animation/js/system.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/animation/layui/css/layui.css" media="all">
<link rel="stylesheet" href="${pageContext.request.contextPath}/animation/css/system.css">
<script type="text/javascript">
	layui.use([ 'element', 'layer', 'form' ], function() { //独立版的layer无需执行这一句
		var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
		var layer = layui.layer; //独立版的layer无需执行这一句
		var form = layui.form;
		
		layer.ready(function() {
			document.getElementById("firstMenu").click();//点击菜单第一项
		});

	});

	function toBasicSearch() {
		pageHref("toBasicSearch", "POST");
	}

	function toAdvSearch() {
		pageHref("toAdvSearch", "POST");
	}
	function toAnimationAdd() {
		pageHref("toAnimationAdd", "POST");
	}
	
	function toStatistics(){
		pageHref("toStatistics", "POST");
	}
	
	function toDiskManage(){
		pageHref("toDiskManage", "POST");
	}

	function index() {
		var _url_head;
		var pathName = window.location.pathname.substring(1);
		_url_head = pathName == '' ? '' : pathName.substring(0, pathName
				.indexOf('/'));
		_url_head = "/" + _url_head;
		window.location.href = _url_head;
	}
</script>
</head>
<body>
	<fmt:bundle basename="common">
		<div class="layui-layout layui-layout-admin">
			<!-- 标题 -->
			<div class="layui-header">
				<div class="title-div" onclick="index()">ANIMATION</div>
			</div>
			<!-- 左侧菜单 -->
			<div class="layui-side layui-bg-black">
				<div class="layui-side-scroll">
					<ul class="layui-nav layui-nav-tree">
						<li class="layui-nav-item"><a href="javascript:toBasicSearch();" id="firstMenu">资源列表</a></li>
						<li class="layui-nav-item"><a href="javascript:toAdvSearch();">高级检索</a></li>
						<li class="layui-nav-item"><a href="javascript:toAnimationAdd();">添加</a></li>
						<li class="layui-nav-item"><a href="javascript:toStatistics();">资源统计</a></li>
						<li class="layui-nav-item"><a href="javascript:toDiskManage();">硬盘管理</a></li>
					</ul>
				</div>
			</div>
			<!-- 中间主要部分 -->
			<div class="layui-body main" id="main" style="padding-bottom: 30px;"></div>
			<!--  -->
			<div class="layui-footer" id="footer"></div>
			<!--  -->
			<input type="hidden" id="closeType" value="" />
			<!--  -->
			<input type="hidden" id="animation_id" value="" />
		</div>
	</fmt:bundle>
</body>
<script type="text/javascript">
	function showAnimationDetail(id) {
		var url = '${pageContext.request.contextPath}/animationDetail/' + id;
		//iframe层
		layer.open({
			type : 2,
			title : '详情',
			maxmin : true,
			shadeClose : true,
			shade : 0.8,
			area : [ '60%', '90%' ],
			content : url,
			cancel : function(index, layero) {
				$("#closeType").val("");
				layer.close(index);
				return false;
			}
		});
	}
</script>
</html>