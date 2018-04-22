<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <%@include file="/src/page/common/common.jsp" %>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/lib/jquery-3.1.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/page/scenesPolicies.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/page/md5.js'/>"></script>
    <script type="text/javascript">
        $(function () {
            $(".close").on("click", function () {
                $(this).closest(".pop-wrapper").hide();
            });
            $(".delete").on("click", function () {
                $(".delUser").show();
            });
            var addUser = new Pop({
                'wrapper': $(".userWrapper"),
                'trigger': $("#add-user"),
                'submitBtn': $(".pop_submit_btn"),
                'submitHandel': function () {
                    saveUser();
                },
                'beforeOpen': function ($wrapper) {
                    $wrapper.find(".pop_title").text("添加用户");
                    $wrapper.find(".userId").val("");
                    $wrapper.find(".loginName").val("");
                    $wrapper.find(".name").val("");
                    $wrapper.find(".email").val("");
                    $wrapper.find(".telephone").val("");
                    $wrapper.find(".companyNo").val("");
                    $("#editType").val(1);
                }
            });
            var editUser = new Pop({
                'wrapper': $(".userWrapper"),
                'trigger': $(".editUser"),
                'submitHandel': function () {
                    saveUser();
                },
                'beforeOpen': function ($wrapper,$trigger) {
                    $wrapper.find(".pop_title").text("编辑用户");
                    $wrapper.find(".name").val($trigger.closest('.clearfix').attr('name'));
                    $wrapper.find(".loginName").val($trigger.closest('.clearfix').attr('loginName'));
                    $wrapper.find(".email").val($trigger.closest('.clearfix').attr('email'));
                    $(".roleId").attr("data-roleId",$trigger.closest('.clearfix').attr('roleId'));
                    var popRoleList = $("#popRoleList");
                    for(var item in popRoleList.children()){
                        if(popRoleList[0].options[item].value == $trigger.closest('.clearfix').attr('roleId')){
                            popRoleList[0].options[item].selected = true;
                            break;
                        }
                    }
                    $wrapper.find(".telephone").val($trigger.closest('.clearfix').attr('telephone'));
                    $wrapper.find(".companyNo").val($trigger.closest('.clearfix').attr('companyNo'));
                    $wrapper.find(".userId").val($trigger.closest('.clearfix').attr('data-id'));
                    $("#editType").val(2);
                }
            });
            var confirm_del_user = new Confirm({
                'wrapper': $(".delUser"),
                'trigger': {'parent': $(".clearfix"), 'selector': '.delete'},
                confirmHandel: function ($theBtn) {
                    delById($theBtn.closest('.clearfix').attr('data-id'), '${basePath}/system/delUserById.do');
                }
            });
            addUser.init();
            editUser.init();
            confirm_del_user.init();
        })
        function showColor(thisObject) {
            $(".clearfix").removeClass("selected");
            $(thisObject).addClass("selected");
        }
        function saveUser() {
            var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            var mailReg =  /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/ ;
            var psw =  /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/;
                if (!phoneReg.test($(".telephone").val())) {
                    showTempErrorPop("不符合手机号码的格式！");
                    return;
                }
                if (!mailReg.test($(".email").val())) {
                    showTempErrorPop("不符合邮箱的格式！");
                    return;
                }
                if($("#editType").val() == 1){
                    if (!psw.test($(".loginPsw").val())) {
                        showTempErrorPop("不符合密码的格式！");
                        return;
                    }
                }
                if($("#editType").val() == 2 && $(".loginPsw").val()!=""){
                    if (!psw.test($(".loginPsw").val())) {
                        showTempErrorPop("不符合密码的格式！");
                        return;
                    }
                }
                if ($(".loginPsw").val()!=$(".reloginPsw").val()) {
                    showTempErrorPop("两次密码不一致！");
                    return;
                }
                if ($(".loginName").val() == "") {
                    showTempErrorPop("账户名不为空！");
                    return;
                }
                var loginPsw = $(".loginPsw").val();
                if (loginPsw==null||loginPsw=="") {
                    $(".loginPsw").val(null);
                    } else{
                    $(".loginPsw").val(hex_md5(loginPsw).substring(8,24));
                }
            var formId = 'editUserForm';
                var form_exp = "#"+formId;
                var url = '';
                if($("#editType").val() == 1){
                    url = $("#insertUrl").val();
                }else{
                    url = $("#updateUrl").val();
                }
                submitAjaxForm(form_exp,url,'reload');
            }

        function checkSubmitEmailAndPhone() {
     /*       if ($(".telephone").val() == "") {
                showTempErrorPop("手机号码不为空！");
                return;
            }
            var pattern = /^1[34578]\d{9}$/;
            if (!pattern.test($(".telephone").val())) {
                showTempErrorPop("手机号码格式不正确！");
                return;
            }
            if ($(".email").val() == "") {
                showTempErrorPop("邮箱不能为空！");
                return;
            }
            if (!$(".email").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)) {
                showTempErrorPop("邮箱格式不正确！");
                return;
            }
            return;*/
        }
    </script>
</head>
<body>
<div class="content">
    <div class="part left" style="width: 100%">
        <div class="part-title">
            <input type="hidden" id="updateUrl" value="${basePath}/system/updateUser.do">
            <input type="hidden" id="insertUrl" value="${basePath}/system/insertUser.do">
            <input type="hidden" id="editType">
            <p class="part-btn" id="add-user">添加用户</p>
            <h2>用户</h2>
        </div>
        <div class="part-cnt">
            <ul>
                <c:forEach items="${users}" var="item">
                <li class='clearfix' data-id="${item.id}" onclick="showColor(this)" loginName="${item.loginName}"
                                    name="${item.name}" companyNo="${item.companyNo}" telephone="${item.telephone}"
                                    email="${item.email}" roleId="${item.roleId}">
                    <p class='list-icons'>
                        <i class="iconfont editUser" title='编辑'>&#xe6f5;</i><i class="iconfont delete" data-id="${item.id}" title='删除'>&#xe738;</i>
                    </p>
                    <h2>
                        <span>${item.loginName}(${item.name}---${item.telephone})</span>
                    </h2>
                </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <!--用户 end-->
</div>
<div class="pop-wrapper userWrapper" style="display: none">
    <div class="pop-black"></div>
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close">关闭</div>
            <h2 class="pop_title">添加用户</h2>
        </div>
        <form id="editUserForm">
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-inside'>
                <tr>
                    <td>账户</td>
                    <td>
                        <div class="input-text " style='width:300px;'><input type="text" class="input loginName" name="loginName"></div>
                    </td>
                    <td style="text-align: left"><span class="redDot">*</span></td>
                </tr>
                <tr>
                    <td>姓名</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" class="input  name" name="name"></div>
                    </td>
                    <td style="text-align: left"><span class="redDot">*</span></td>
                </tr>
                <tr>
                    <td>员工号</td>
                    <td>
                        <div class="input-text " style='width:300px;'><input type="text" class="input companyNo" name="companyNo"></div>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td>手机号</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" class="input telephone" name="telephone" onblur="checkSubmitEmailAndPhone()"></div>
                    </td>
                    <td style="text-align: left"><span class="redDot">*</span></td>
                </tr>
                <tr>
                    <td> 角色</td>
                    <td>
                        <select class="input-select roleId" style='width:300px;' name="roleId" id="popRoleList">
                            <c:forEach items="${roles}" var="item">
                                <option roleId="${item.id}" class="selectedRole" value="${item.id}">&nbsp;${item.roleName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td style="text-align: left"><span class="redDot">*</span></td>
                </tr>
                <tr>
                    <td>邮箱</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" class="input email" name="email" onblur="checkSubmitEmailAndPhone()"></div>
                    </td>
                    <td style="text-align: left"><span class="redDot">*</span></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="password" class="input  loginPsw" name="loginPsw"></div>
                    </td>
                    <td style="text-align: left"><span class="redDot">*</span></td>
                </tr>
                <tr>
                    <td>确认密码</td>
                    <td>
                        <div class="input-text" style='width:300px;'>
                            <input type="text" class="input userId" style="display: none" name="id">
                            <input type="password" class="input reloginPsw"></div>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="tip">密码位数不低于8位数，包括数字与字母</div>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="pop-btns">
                            <div class="btn-blue left pop_submit_btn" style="margin-right: 20px">保存</div>
                            <div class="btn-white left close" style="margin-left: 20px">返回</div>
                        </div>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
            </form>
    </div>
</div>
<!--添加用户end-->
<!--修改密码-->
<div class="pop-wrapper editWrapper" style="display: none">
    <div class="pop-black"></div>
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close">关闭</div>
            <h2>修改密码</h2>
        </div>
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-inside'>
                <tr>
                    <td>新密码</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" class="input"></div>
                    </td>
                </tr>
                <tr>
                    <td>确认新密码</td>
                    <td>
                        <div class="input-text" style='width:300px;'>
                            <input type="text" class="input"></div>
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
                            <div class="btn-blue left" style="margin-right: 20px">保存</div>
                            <div class="btn-white left close" style="margin-left: 20px">返回</div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="pop-wrapper" style='display:none;'>
    <div class="pop-black"></div>
    <div class="pop-tip">
        <i class="iconfont ok">&#xe72d;</i>
        <p>用户添加成功</p>
    </div>
</div>
<!--用户添加成功 end-->
<div class="pop-wrapper" style='display:none;'>
    <div class="pop-black"></div>
    <div class="pop-tip">
        <i class="iconfont error">&#xe6f2;</i>
        <p>用户添加失败</p>
    </div>
</div>
<!--用户添加失败 end-->
<div class="pop-wrapper delUser" style='display:none;'>
    <div class="pop-black"></div>
    <div class="pop-tips">
        <h2>您确定要执行删除该用户吗？</h2>
        <div class="pop-btns">
            <div class="btn-blue left confirm_btn" style="margin-right: 20px">保存</div>
            <div class="btn-white left close" style="margin-left: 20px">返回</div>
        </div>
    </div>
</div>
<!--确认弹框 end-->
</body>
</html>
