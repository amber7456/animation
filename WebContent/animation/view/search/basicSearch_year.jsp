<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 按年查询结果 -->
<fmt:bundle basename="common">
	<c:if test="${ searchBean.searchType =='year' }">
		<div style="font-size: 20px; margin: 3px 0;">
			<p>${YearList.year}年收录季番${YearList.count.fuyu + YearList.count.haru +YearList.count.natsu+YearList.count.aki}部。
				冬季番${YearList.count.fuyu}部，春季番${YearList.count.haru}部，夏季番${YearList.count.natsu}部，秋季番${YearList.count.aki}部。
				OVA/OAD${YearList.count.ova}部，映画${YearList.count.movie}部，其他${YearList.count.other}部。</p>
		</div>

		<c:forEach var="i" begin="1" end="7">

			<div class="animation-type" style="margin-top: 10px;">
				<fmt:message key="TYPE${i}" />
			</div>

			<c:forEach items="${YearList.animationList}" var="row">
				<c:if test="${row.data['ANIMATION_TYPE'] == i }">
					<div class="animation">

						<c:if test="${row.data['HAVE_BD_RESOURCE'] == 1}">
							<div
								style="position: absolute; width: 10px; height: 10px; color: #01AAED; background-color: #01AAED; border-radius: 5px; top: 5px; left: 5px;">
							</div>
						</c:if>
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
								<fmt:parseDate value="${row.data['ANIMATION_BROADCAST_TIME']}"
									var="date" pattern="yyyy-MM-dd" />
								<fmt:formatDate value="${date}" type="date" dateStyle="full" />
							</div>
						</div>
						<div>
							<div style="text-align: center;"
								class="edit-div layui-btn layui-btn-normal layui-btn-mini"
								id="edit-div${row.data['ANIMATION_ID']}">
								<i class="layui-icon" style="margin-right: 0px;">&#xe65f;</i>
							</div>
							<div class="edit-list" id="edit-list${row.data['ANIMATION_ID']}"
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
			</c:forEach>
			<c:if test="${i != 7 }">
				<hr class="layui-bg-green" />
			</c:if>
		</c:forEach>
	</c:if>
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
	})
</script>









