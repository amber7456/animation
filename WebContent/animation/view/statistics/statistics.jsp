<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/echarts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/animation/js/scrolltopcontrol.js"></script>
<style type="text/css">
.form-div {
	margin: 10px 0px;
}

.form-div td {
	padding: 0px 5px 0px 0px;
}
</style>
<div class="layui-tab layui-tab-brief" lay-filter="statisticsTab"
	style="min-width: 900px;">
	<ul class="layui-tab-title">
		<li class="layui-this">趋势统计</li>
		<li>比例统计</li>
		<li>BD资源统计</li>
	</ul>
	<div class="layui-tab-content" style="height: 100px;">
		<div class="layui-tab-item layui-show">
			<form action="" id="getStatisticsForLineForm" class="layui-form"
				method="post" enctype="multipart/form-data">
				<div class="form-div">
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
							<td>
								<div class="layui-btn layui-btn-normal" lay-submit
									lay-filter="getStatisticsForLine">趋势统计</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
			<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
			<div id="echarts-line" style="width: 90%; height: 600px;"></div>
		</div>
		<div class="layui-tab-item">
			<form action="" id="getStatisticsForPieForm" class="layui-form"
				method="post" enctype="multipart/form-data">
				<div class="form-div">
					<table>
						<tr>
							<td width="100"><select name="startYear" id="startYear">
									<c:forEach var="i" begin="1900" end="${nowYear}" step="1">
										<option value="${nowYear + 1900 - i }">${nowYear + 1900 - i }年</option>
									</c:forEach>
							</select></td>
							<td>
								<div class="layui-btn layui-btn-normal" lay-submit
									lay-filter="getStatisticsForPie">比例统计</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
			<div id="echarts-pie" style="width: 90%; height: 600px; float: left;"></div>
		</div>
		<div class="layui-tab-item">
			<form action="" id="getStatisticsForBDForm" class="layui-form"
				method="post" enctype="multipart/form-data">
				<div class="form-div">
					<table>
						<tr>
							<td width="100"><select name="startYear" id="startYear">
									<c:forEach var="i" begin="1900" end="${nowYear}" step="1">
										<option value="${nowYear + 1900 - i }">${nowYear + 1900 - i }年</option>
									</c:forEach>
							</select></td>
							<td>
								<div class="layui-btn layui-btn-normal" lay-submit
									lay-filter="getStatisticsForBD">BD资源统计</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
			<div id="echarts-BD" style="width: 90%; height: 600px; float: left;"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
	var myChart_line;
	var myChart_pie;
	var myChart_BD;
	var url_line = "${pageContext.request.contextPath}/getStatisticsForLine";
	var url_pie = "${pageContext.request.contextPath}/getStatisticsForPie";
	var url_BD = "${pageContext.request.contextPath}/getStatisticsForBD";
	layui.use([ 'element', 'form' ], function() {
		var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
		var form = layui.form;
		form.render(); //更新全部，此处只有更新一次，网页上才能正常显示

		element.on('tab(statisticsTab)', function(data) {
			//console.log(this); //当前Tab标题所在的原始DOM元素
			//console.log(data.index); //得到当前Tab的所在下标
			//console.log(data.elem); //得到当前的Tab大容器
			if (data.index == 0) {
				// 基于准备好的dom，初始化echarts实例 
				myChart_line = echarts.init(document
						.getElementById('echarts-line'));
			}
			if (data.index == 1) {
				myChart_pie = echarts.init(document
						.getElementById('echarts-pie'));
			}
			if (data.index == 2) {
				myChart_BD = echarts
						.init(document.getElementById('echarts-BD'));
			}

		});

		form.on('submit(getStatisticsForLine)', function(data) {
			myChart_line = echarts
					.init(document.getElementById('echarts-line'));

			var getStatisticsForLineForm = new FormData(
					$("#getStatisticsForLineForm")[0]);

			$.ajax({
				url : url_line,
				type : 'POST',
				data : getStatisticsForLineForm,// 
				async : false,/*  */
				contentType : false, /*  */
				processData : false,
				dataType : 'json',
				success : function(data) {
					resetChartLine(data);
				},
				error : function(data) {
					layer.alert('获取数据异常，请稍后再试！' + JSON.stringify(data), {
						closeBtn : 0,
						offset : '200px',
						anim : 6,
						icon : 5
					});
				}
			});
		});

		form.on('submit(getStatisticsForPie)', function(data) {
			var getStatisticsForPieForm = new FormData(
					$("#getStatisticsForPieForm")[0]);
			$.ajax({
				url : url_pie,
				type : 'POST',
				data : getStatisticsForPieForm,// 
				async : false,
				contentType : false,
				processData : false,
				dataType : 'json',
				success : function(data) {
					$("#echarts-pie").css("border", "1px solid #e2e2e2");
					resetChartPie(data)
				},
				error : function(data) {
					layer.alert('获取数据异常，请稍后再试！' + JSON.stringify(data), {
						closeBtn : 0,
						offset : '200px',
						anim : 6,
						icon : 5
					});
				}
			});
		});

		form.on('submit(getStatisticsForBD)', function(data) {
			myChart_BD = echarts.init(document.getElementById('echarts-BD'));

			var getStatisticsForBDForm = new FormData(
					$("#getStatisticsForBDForm")[0]);
			$.ajax({
				url : url_BD,
				type : 'POST',
				data : getStatisticsForBDForm,//  
				async : false,
				contentType : false,
				processData : false,
				dataType : 'json',
				success : function(data) {
					resetChartBD(data);
				},
				error : function(data) {
					layer.alert('获取数据异常，请稍后再试！' + JSON.stringify(data), {
						closeBtn : 0,
						offset : '200px',
						anim : 6,
						icon : 5
					});
				}
			});
		});

	});

	function resetChartLine(lineData) {
		$("#echarts-line").css("border", "1px solid #e2e2e2");
		option_line = {
			/* title : {
				text : '折线图堆叠'
			}, */
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend : {
				data : [ '冬番', '春番', '夏番', '秋番', 'OVA/OAD', '映画', '其他', '季番总和',
						'全部总和' ]
			},
			color : [ '#01AAED', '#5FB878', '#F7B824', '#FF5722', '#d48265',
					'#7bbfea', '#BDB76A', '#BF0A10', '#2a5caa' ],
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				/* boundaryGap : false, */
				data : lineData.yearArr
			},
			yAxis : {
				type : 'value'
			},
			series : [ {
				name : '冬番',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.TYPE1
			}, {
				name : '春番',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.TYPE2
			}, {
				name : '夏番',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.TYPE3
			}, {
				name : '秋番',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.TYPE4
			}, {
				name : 'OVA/OAD',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.TYPE5
			}, {
				name : '映画',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.TYPE6
			}, {
				name : '其他',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.TYPE7
			}, {
				name : '季番总和',
				type : 'line',
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.yearSeasonSum
			}, {
				name : '全部总和',
				type : 'line',
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : lineData.yearSum
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart_line.setOption(option_line);
	}

	function resetChartPie(lineData) {
		$("#echarts-pie").css("border", "1px solid #e2e2e2");
		option_pie = {
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b}: {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : [ '冬番', '春番', '夏番', '秋番', 'OVA/OAD', '映画', '其他' ]
			},
			color : [ '#01AAED', '#5FB878', '#F7B824', '#FF5722', '#d48265',
					'#7bbfea', '#BDB76A' ],
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			series : [ {
				name : '番组',
				type : 'pie',
				selectedMode : 'single',
				radius : [ 0, '60%' ],
				label : {
					normal : {
						position : 'outside'
					}
				},
				labelLine : {
					normal : {
						show : true
					}
				},
				data : [ {
					value : lineData.TYPE1,
					name : '冬番'
				/*   selected : true */
				}, {
					value : lineData.TYPE2,
					name : '春番'
				}, {
					value : lineData.TYPE3,
					name : '夏番'
				}, {
					value : lineData.TYPE4,
					name : '秋番'
				}, {
					value : lineData.TYPE5,
					name : 'OVA/OAD'
				}, {
					value : lineData.TYPE6,
					name : '映画'
				}, {
					value : lineData.TYPE7,
					name : '其他'
				} ]
			} ]
		};
		myChart_pie.setOption(option_pie);
	}

	function resetChartBD(data) {
		$("#echarts-BD").css("border", "1px solid #e2e2e2");
		option_BD = {
			/* title : {
				text : '折线图堆叠'
			}, */
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend : {
				data : [ '全部收录', 'BD收录' ]
			},
			color : [ '#5FB878', '#01AAED', '#F7B824', '#FF5722', '#d48265',
					'#7bbfea', '#EEE7CE', '#BF0A10', '#2a5caa' ],
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				/* boundaryGap : false, */
				data : [ '冬番', '春番', '夏番', '秋番', 'OVA/OAD', '映画', '其他', '季番总和',
						'全部总和' ]
			},
			yAxis : {
				type : 'value'
			},
			series : [ {
				name : '全部收录',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : data.all
			}, {
				name : 'BD收录',
				type : 'bar',
				barMaxWidth : 20,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				data : data.BD
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart_BD.setOption(option_BD);
	}
</script>