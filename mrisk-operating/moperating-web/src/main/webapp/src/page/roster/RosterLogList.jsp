<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>名单库管理_名单库操作日志</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/src/css/common/jquery.page.css'/>">
    <script type="text/javascript" src="<c:url value="/src/js/lib/jquery-3.1.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/jquery.page.js"/>"></script>
    <script type="text/javascript">
        $(function(){
            $("#page").Page({
                totalPages:${page.totalPage},//分页总数
                liNums: 9,//分页的数字按钮数(建议取奇数)
                currentPage:"${page.currentPage}",
                activeClass:'activP', //active 类样式定义
                limit: "${page.pageSize}",
                totalCount: "${page.totalCount}",
                callBack : function (page) {
                    $("#currentPage").val(page);
                    $("#searchForm").submit();
                }
            });

            $("#search_btn").on("click",function () {
                $("#searchForm").submit();
            })
        });
    </script>
</head>
<body>
    <div class="content">
        <form method="post" id="searchForm" action="<c:url value='/rosterController/getLogList.do'/>">
        <div class="search clearfix">
            <tr style="display:none">
                <td colspan="6">
                    <input name="currentPage" id="currentPage" value="${page.currentPage}" type="hidden">
                    <input name="pageSize" id="pageSize" value="${page.pageSize}" type="hidden">
                </td>
            </tr>
            <ul class='search-ul left clearfix'>
                <li class="left">
                    <label>名单库类型</label>
                    <select class="input-select" style='width:200px;' id="rosterBusiType" name="rosterBusiType">
                        <option value="">--所有类型--</option>
                        <c:forEach items="${rosterBusiTypeList}" var="item">
                            <option value="${item.id}" <c:if test="${item.id eq rosterBusiType}">selected="selected"</c:if>>&nbsp;${item.name}(${item.id})</option>
                        </c:forEach>
                    </select>
                </li>
                <li class="left">
                    <label>内容类型</label>
                    <select class="input-select"  id="rosterType" name="rosterType">
                        <option value="">&nbsp;---全部内容类型---</option>
                        <c:forEach items="${rosterTypeList}" var="item">
                            <option value="${item.id}" <c:if test="${item.id eq rosterType}">selected="selected"</c:if>>&nbsp;${item.name}</option>
                        </c:forEach>
                    </select>
                </li>
                <li class="left">
                    <label>内容</label>
                    <div class="input-text">
                        <input type="text" name="content" class="input" id="content" value="${param.content}"/>
                        </div>
                        <%--<input type="text" class="input"></div>--%>
                </li>
            </ul>
            <div id="search_btn" class="btn left">查询</div>
        </div>
            </form>
        <!--search end-->
        <div class="list mar20">
            <table cellpadding='0' cellspacing='0' class='table mar20'>
                <thead>
                    <tr>
                        <td>名单库类型</td>
                        <td>内容类型</td>
                        <td>内容</td>
                        <td>执行操作</td>
                        <td>操作结果</td>
                        <td>结果描述</td>
                        <td>操作人</td>
                        <td>操作时间</td>                        
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${list}" varStatus="status">
                    <tr>
                        <td>${item.roster_busi_name}</td>
                        <td>${item.roster_type_name}</td>
                        <td>${item.content}</td>
                        <td>
                            <c:if test="${item.oper_type == 1}">
                                导入操作
                            </c:if>
                            <c:if test="${item.oper_type == 2}">
                                删除操作
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${item.status == 1}">
                                成功
                            </c:if>
                            <c:if test="${item.status == 0}">
                                失败
                            </c:if>
                        </td>
                        <td>${item.status_desc}</td>
                        <td>${item.creator}</td>
                        <td><fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm:ss" value="${item.created_date}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${not empty list}">
                <div id="page" style="float:right;"></div>
            </c:if>
            <div class="clear"></div>
        </div>
    </div>
</body>
</html>