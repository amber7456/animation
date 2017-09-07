<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<fmt:bundle basename="common">
	<div style="width: 800px;">
		<table class="layui-table">
			<thead>
				<tr>
					<th>硬盘名称</th>
					<th>硬盘容量</th>
					<th>硬盘状态</th>
					<th>是否装满</th>
					<th>入役时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:forEach items="${diskList}" var="row">
				<tr>
					<td>${row.data['disk_name'] }</td>
					<td>${row.data['disk_capacity'] }</td>
					<td><fmt:message key="DISK_STATE${row.data['disk_state'] }" /></td>
					<td><fmt:message key="IS_FULL${row.data['is_full'] }" /></td>
					<td>${row.data['create_time'] }</td>
					<td>
						<div class="layui-btn layui-btn-normal layui-btn-small"
							onclick="diskEdit('${row.data['disk_id'] }')">编辑</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</fmt:bundle>
<script type="text/javascript">

function diskEdit(id) { 
	//iframe层
	var url = '${pageContext.request.contextPath}/toDiskEdit/'+id;
	layer.open({
		type : 2,
		title : '编辑',
		offset: '100px',
		maxmin : true,
		shadeClose : true,
		shade : 0.8,
		area : [ '600px', '300px' ],
		content : url
	});
}
</script>