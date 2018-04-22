<%@ page import="com.zoom.risk.operating.common.utils.Machines" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>熵盾风控运营平台_登录</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/src/css/page/page_login.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/src/css/page/page_index.css'/>">
    <script type="text/javascript" src="<c:url value='/src/js/lib/jquery-3.1.0.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/page/md5.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/page/login.js'/>"></script>
    <script type="text/javascript">
        $(function(){
            <c:if test="${errorFlag eq 0 }">
            showTempErrorPop("登录失败！");
            </c:if>
        });
    </script>
</head>
<body>
<div class="bg">
    <div class="login-box">
        <h1>熵盾风险管理系统</h1>
        <form id="submitForm" method="post" action="<c:url value="/login/verify.do"/>" class='form-box'>
            <div class="user-input">
                <label>用户名</label><input type="text" name="loginName" id="loginName"/>
            </div>
            <div class="pwd-input">
                <label>密&nbsp;&nbsp;&nbsp;码</label><input type="password" name="loginPwd" id="loginPwd"/>
            </div>
        </form>
        <a href='javascript:void(0);' class="btn-login" onclick="verifyAction()">登录</a>
    </div>
    <div class="copyright">
        Copyright © 2017 熵盾金融 All rights reserved| 京ICP备XXXX号-1| 京ICP证XXXX号
        <span style="display:none"><%= Machines.getMac()%></span>
    </div>
    <%@include file="../page/common/TempErrorPop.jsp" %>
</div>
</body>
</html>