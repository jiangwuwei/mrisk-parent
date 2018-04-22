<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="common/common.jsp" %>
    <%@include file="common/TempErrorPop.jsp"%>
    <title>熵盾风险管理系统</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/common/top.css"/>">
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/page/index.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/page/md5.js'/>"></script>
    <script type="text/javascript">
        $(function () {
            $(".close").on("click", function () {
                $(this).closest(".pop-wrapper").hide();
            });
        })
        function show() {
          $(".editWrapper").show();
        }
        function rectifyPsw(id) {
            var prePass =hex_md5($(".prePass").val()).substring(8,24);
            var loginPsw = $(".inputPass").val();
            if (loginPsw==null||loginPsw=="") {
                $(".editWrapper").hide();
                showTempErrorPop("新密码不为空！");
                return;
            }
            var pass = hex_md5($(".inputPass").val()).substring(8,24);
            $.ajax({
                type: "POST",
                url: "<c:url value='/system/updatePsw.do'/>",
                dataType:'json',
                data:{
                    "userId":id,
                    "psw":pass,
                    "prePass":prePass
                },
                error: function(request) {
                    alert("Connection error!");
                },
                success: function(data) {
                    while(data.errMsg){
                        $(".editWrapper").hide();
                        showTempErrorPop(data.errMsg);
                        return;
                    }
                    showTempSuccessPop("修改成功!");
                    window.location.reload();
                }
            })
        }
    </script>
</head>
<body>
    <input type="hidden" value="${basePath}" id="basePath">
    <input type="hidden" value="${user.id}" id="userId">
    <div class="top clearfix">
        <h3 class="logo">熵盾风险管理系统</h3>
        <div class="menu-bar">
            <c:forEach items="${topMenuList}" var="topMenu" >
                <a href="javascript:void(0);" url="${topMenu.url}" menuId="${topMenu.id}" onclick="changeMenu(this)">${topMenu.name}</a>
            </c:forEach>
        </div>
        <ul class="manage">
            <a class="user" onclick="show()" userId="${user.id}">${user.loginName}</a>
            <a href="<c:url value='/login/logout.do'/>" class="quit">退出</a>
        </ul>
    </div>
    <iframe src="<c:url value='/src/page/main.jsp'/>" id="mainContent" name="mainContent" width="100%" height="500px" frameborder="0" scrolling="auto"></iframe>
    <div class="pop-wrapper editWrapper" style="display: none">
        <div class="pop-black"></div>
        <div class="pop">
            <div class="pop-title">
                <div class="pop-close close">关闭</div>
                <h2>修改密码</h2>
            </div>
            <div class="pop-cnt">
                <table cellpadding='0' cellspacing='0' class='table-inside'>
                    <tr>
                        <td>原密码</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input type="password" class="input prePass"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>新密码</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input type="password" class="input inputPass"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>确认新密码</td>
                        <td>
                            <div class="input-text" style='width:300px;'>
                                <input type="password" class="input"></div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div class="tip">密码位数不低于8位数，包括数字与字母</div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div class="pop-btns">
                                <div class="btn-blue left " onclick="rectifyPsw(${user.id})" style="margin-right: 20px">保存</div>
                                <div class="btn-white left close" style="margin-left: 20px">返回</div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</body>
</html>