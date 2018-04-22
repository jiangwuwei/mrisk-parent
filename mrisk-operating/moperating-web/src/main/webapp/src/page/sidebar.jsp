<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="common/common.jsp" %>
    <title>大数据风控运营平台</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/common/sidebar.css"/>">
    <script type="text/javascript" src="<c:url value='/src/js/app/page/sidebar.js'/>"></script>
</head>
<body>
    <div class="sidebar" id="sideBarMenu">
        <c:forEach items="${sideBarList}" var="sideBarMenu">
            <dl>
                <dt class="bar-title ${sideBarMenu.cssName}">${sideBarMenu.name}</dt>
                <dd>
                    <c:forEach items="${sideBarMenu.children}" var="children">
                        <a href="javascript:void(0);" url="<c:url value='${children.url}'/>">${children.name}</a>
                    </c:forEach>
                </dd>
            </dl>
        </c:forEach>
    </div>
</body>
</html>