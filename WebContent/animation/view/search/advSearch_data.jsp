<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="animation_type" scope="page"
	value="${searchBean.animation_type}" />
<fmt:bundle basename="common">
	<div style="min-width: 900px;">
		<c:forEach var="y" begin="${searchBean.startYear}"
			end="${searchBean.endYear}">

			<c:set var="dataFlag" scope="page" value="0" />
			<c:forEach items="${animationList}" var="row">
				<fmt:parseDate value="${row.data['ANIMATION_BROADCAST_TIME']}"
					var="date" pattern="yyyy" dateStyle="LONG" />
				<fmt:formatDate var="year" pattern="yyyy" value="${date}" />
				<c:if test="${year == y }">
					<c:set var="dataFlag" scope="page" value="1" />
				</c:if>
			</c:forEach>

			<c:if test="${dataFlag == 1 }">
				<c:forEach var="i" begin="1" end="7">
					<c:set var="count" scope="page" value="0" />

					<c:choose>
						<c:when test="${animation_type != 'ALL' && i ==  animation_type }">
							<div class="animation-type" id="TYPE${i}${y}"
								style="margin-top: 10px;">
								<span>${y}年<fmt:message key="TYPE${i}" /></span>
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="${animation_type == 'ALL'}">
								<div class="animation-type" id="TYPE${i}${y}"
									style="margin-top: 10px;">
									<span>${y}年<fmt:message key="TYPE${i}" /></span>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>

					<c:forEach items="${animationList}" var="row">
						<c:if test="${row.data['ANIMATION_TYPE'] == i }">
							<fmt:parseDate value="${row.data['ANIMATION_BROADCAST_TIME']}"
								var="date" pattern="yyyy" dateStyle="LONG" />
							<fmt:formatDate var="year" pattern="yyyy" value="${date}" />

							<c:if test="${year == y }">

								<c:set var="count" scope="page" value="${count +1 }" />
								<div class="animation">
									<div class="animation-info"
										onclick="showAnimationDetail('${row.data['ANIMATION_ID']}')">
										<div class="animation-poster">
											<c:choose>
												<c:when test="${row.data['HAVE_POSTER'] eq 'YES'}">
													<img onload="setImgStyle(this)" style="display: none;"
														src="${pageContext.request.contextPath}/poster/getPoster/${row.data['ANIMATION_ID']}">
												</c:when>
												<c:otherwise>
													<div class="animation-poster-text">暂无图片</div>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="animation-name">${row.data['ANIMATION_NAME']}</div>
										<div class="animation-date">
											<fmt:parseDate
												value="${row.data['ANIMATION_BROADCAST_TIME']}" var="date"
												pattern="yyyy-MM-dd" />
											<fmt:formatDate value="${date}" type="date" dateStyle="full" />
										</div>
									</div>
									<div>
										<div style="text-align: center;"
											class="edit-div layui-btn layui-btn-normal layui-btn-mini"
											id="edit-div${row.data['ANIMATION_ID']}">
											<i class="layui-icon" style="margin-right: 0px;">&#xe65f;</i>
										</div>
										<div class="edit-list"
											id="edit-list${row.data['ANIMATION_ID']}"
											style="display: none; padding: 5px;">
											<div style="margin: 5px 0px;">
												<div class="layui-btn layui-btn-small layui-btn-normal"
													onclick="editAnimation('${row.data['ANIMATION_ID']}')"
													style="width: 100%;">
													<i class="layui-icon">&#xe642;</i>编辑
												</div>
											</div>
											<div style="margin: 5px 0px;">
												<div class="layui-btn layui-btn-small layui-btn-danger"
													onclick="deleteAnimation('${row.data['ANIMATION_ID']}','${row.data['ANIMATION_NAME']}')"
													style="width: 100%;">
													<i class="layui-icon">&#xe640;</i>删除
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</c:if>
					</c:forEach>
					<input type="hidden" id="countTYPE${i}${y}" value="${count}" />
					<c:choose>
						<c:when
							test="${animation_type != 'ALL' && i == animation_type && searchBean.endYear != y }">
							<hr class="layui-bg-green" />
						</c:when>
						<c:otherwise>
							<c:if
								test="${animation_type == 'ALL' && searchBean.endYear+i != searchBean.endYear+7  }">
								<hr class="layui-bg-green" />
							</c:if>
							<c:if test="${animation_type == 'ALL' && i == 7 }">
								<hr class="layui-bg-orange" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:if>
		</c:forEach>
	</div>
</fmt:bundle>
<script type="text/javascript">
$(document).ready(function() {
	$('.edit-div').click(function(event) {
		//取消事件冒泡  
		event.stopPropagation();
		//按钮的toggle,如果div是可见的,点击按钮切换为隐藏的;如果是隐藏的,切换为可见的。  
		$(this).parent().find(".edit-list").slideToggle(100);
		var thisId = $(this).parent().find(".edit-list").attr('id');

		$(".edit-list").each(function() {
			if ($(this).attr('id') != thisId) {
				$(this).slideUp(100);
			}
		});
		return false;
	});

	//点击空白处隐藏弹出层，下面为滑动消失效果和淡出消失效果。
	$(document).click(function(event) {
		var _con = $('.edit-list'); // 设置目标区域
		if (!_con.is(event.target) && _con.has(event.target).length === 0) { // Mark 1
			$('.edit-list').slideUp(100); //滑动消失 
		}
	});

	//计数 
	$("input[id*='countTYPE']").each(function(){//遍历
         //console.log($(this).val());
         var count = $(this).val();
         if(count >5){
        	 var id = $(this).attr("id").replace("count","");
             $("#"+id).append('<span style="font-size:16px;">'+count+'</span>');
         }
         
	});
      
})
</script>
