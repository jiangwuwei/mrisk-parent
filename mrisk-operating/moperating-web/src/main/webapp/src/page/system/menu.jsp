<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <%@include file="/src/page/common/common.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/lib/jquery-3.1.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript">
        $(function () {
            $(".label_tab").on("click", function(){
                if ($(this).find('.another').hasClass('up')) {
                    $(this).find('.another').removeClass('up');
                    $(this).find('.another').addClass('down');
                    $(this).find(".another").html("&#xe612");
                }else {
                    $(this).find('.another').removeClass('down');
                    $(this).find('.another').addClass('up');
                    $(this).find(".another").html("&#xe69a");
                }
            });
            $(".close").on("click", function () {
                $(this).closest(".pop-wrapper").hide();
            });
            var addMenu = new Pop({
                'wrapper': $(".menuWrapper"),
                'trigger': $(".add-menu"),
                'submitBtn': $(".pop_submit_btn"),
                'submitHandel': function () {
                    saveMenu();
                },
                'beforeOpen': function ($wrapper,$trigger) {
                    $wrapper.find(".pop_title").text("添加菜单");
                    $wrapper.find(".menuId").val("");
                    $wrapper.find(".iconcls").val("");
                    $wrapper.find(".menuOrder").val("");
                    $wrapper.find(".parentId").val($trigger.closest('.clearfix').attr('data-id'));
                    $wrapper.find(".menuUrl").val("");
                    $wrapper.find(".menuCode").val("");
                    $wrapper.find(".menuName").val("");
                    $("#editType").val(1);
                }
            });
            var editMenu = new Pop({
                'wrapper': $(".menuWrapper"),
                'trigger': $(".editMenu"),
                'submitHandel': function () {
                    saveMenu();
                },
                'beforeOpen': function ($wrapper,$trigger) {
                    $wrapper.find(".pop_title").text("编辑菜单");
                    $wrapper.find(".menuName").val($trigger.closest('.clearfix').attr('menuName'));
                    $wrapper.find(".menuCode").val($trigger.closest('.clearfix').attr('menuCode'));
                    $wrapper.find(".menuUrl").val($trigger.closest('.clearfix').attr('menuUrl'));
                    $wrapper.find(".menuOrder").val($trigger.closest('.clearfix').attr('menuOrder'));
                    $wrapper.find(".menuId").val($trigger.closest('.clearfix').attr('data-id'));
                    $wrapper.find(".parentId").val($trigger.closest('.clearfix').attr('parentId'));
                    $wrapper.find(".iconcls").val($trigger.closest('.clearfix').attr('menuIconcls'));
                    $("#editType").val(2);
                }
            });
            var confirm_del_menu = new Confirm({
                'wrapper': $(".delMenu"),
                'trigger': {'parent': $(".clearfix"), 'selector': '.delete'},
                confirmHandel: function ($theBtn) {
                    delById($theBtn.closest('.clearfix').attr('data-id'), '${basePath}/system/delMenuById.do');
                }
            });
            addMenu.init();
            editMenu.init();
            confirm_del_menu.init();
        })
        function showColor(thisObject) {
            $(".clearfix").removeClass("selected");
            $(thisObject).addClass("selected");
        }
        function saveMenu() {
            var formId = 'editMenuForm';
            var form_exp = "#"+formId;
            var url = '';
            if($("#editType").val() == 1){
                url = $("#insertUrl").val();
            }else{
                url = $("#updateUrl").val();
            }
            /*    if(!validatePolicies(form_exp)){
             return;
             }*/
            submitAjaxForm(form_exp,url,'reload');
        }

        function childTaggle(id){
            $("#ul_"+id).toggle();
        }
    </script>
</head>
<body>
<div class="content">
    <div class="part left" style="width: 100%">
        <div class="part-title">
            <input type="hidden" id="updateUrl" value="${basePath}/system/updateMenu.do">
            <input type="hidden" id="insertUrl" value="${basePath}/system/insertMenu.do">
            <input type="hidden" id="editType">
            <p class="part-btn add-menu" id="add-menu">添加分类</p>
            <h2>分类</h2>
        </div>
        <ul>
            <c:forEach items="${menus}" var="item">
                <li class="part-one clearfix" menuName="${item.menuName}" data-id ="${item.id}"  menuCode="${item.menuCode}" menuUrl="${item.menuUrl}" menuOrder="${item.menuOrder}" parentId="${item.parentId}" menuIconcls="${item.iconcls}">
                    <div class="part-one-title list-celue-title label_tab" id="li_${item.id}" onclick="childTaggle('${item.id}')" style="margin: 0px;padding: 0px 10px;">
                        <p class='list-icons'>
                            <i class="iconfont add-menu" title='添加'>&#xe6df;</i><i class="iconfont editMenu" title='编辑'>&#xe6f5;</i><i class="iconfont delete" title='删除'>&#xe738;</i>
                        </p>
                        <h2>
                            <i class="another iconfont up"></i><span>${item.menuName}</span>
                        </h2>
                    </div>
                    <ul id="ul_${item.id}" style="display:none">
                        <c:forEach items="${item.children}" var="child">
                            <li class="part-one clearfix" menuName="${child.menuName}" data-id ="${child.id}"  menuCode="${child.menuCode}" menuUrl="${child.menuUrl}"  menuOrder="${child.menuOrder}" parentId="${child.parentId}" menuIconcls="${child.iconcls}">
                                <div class="part-one-title"  id="li_${child.id}" onclick="childTaggle('${child.id}')" style="padding-left: 40px; margin: 0;">
                                    <p class='list-icons'>
                                        <i class="iconfont add-menu" title='添加'>&#xe6df;</i><i class="iconfont editMenu" title='编辑'>&#xe6f5;</i><i class="iconfont delete" title='删除'>&#xe738;</i>
                                    </p>
                                    <h2>
                                        <i class="dot"></i><span>${child.menuName}</span>
                                    </h2>
                                </div>
                                <ul id="ul_${child.id}" style="display:none">
                                    <c:forEach items="${child.children}" var="ch">
                                        <li class="part-one clearfix" menuName="${ch.menuName}" data-id ="${ch.id}"  menuCode="${ch.menuCode}" menuUrl="${ch.menuUrl}"  menuOrder="${ch.menuOrder}" parentId="${ch.parentId}" menuIconcls="${ch.iconcls}">
                                            <div class="part-one-title" style="margin: 0;padding-left: 63px;">
                                                <p class='list-icons'>
                                                    <i class="iconfont editMenu" title='编辑'>&#xe6f5;</i><i class="iconfont delete" title='删除'>&#xe738;</i>
                                                </p>
                                                <h2>
                                                    <span>${ch.menuName}</span>
                                                </h2>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </div>
    <!--添加分类-->
    <div class="pop-wrapper menuWrapper" style="display: none">
        <div class="pop-black"></div>
        <div class="pop">
            <div class="pop-title">
                <div class="pop-close">关闭</div>
                <h2 class="pop_title">添加分类</h2>
            </div>
            <form id="editMenuForm">
                <div class="pop-cnt">
                    <table cellpadding='0' cellspacing='0' class='table-inside'>
                        <tr>
                            <td>菜单名称</td>
                            <td>
                                <div class="input-text" style='width:300px;'><input type="text" class="input menuName" name="menuName"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>菜单码</td>
                            <td>
                                <div class="input-text" style='width:300px;'><input type="text" class="input menuCode" name="menuCode"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>菜单URL</td>
                            <td>
                                <div class="input-text" style='width:300px;'><input type="text" class="input menuUrl" name="menuUrl"></div>
                            </td>
                        </tr>
                        <input type="text" class="input parentId" name="parentId" style="display: none">
                        <tr>
                            <td>样式</td>
                            <td>
                                <div class="input-text" style='width:300px;'><input type="text" class="input iconcls" name="iconcls"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>排序号</td>
                            <td>
                                <div class="input-text" style='width:300px;'>
                                    <input type="text" class="input menuOrder" name="menuOrder">
                                    <input type="text" class="input menuId" style="display: none" name="id"></div>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <div class="pop-btns">
                                    <div class="btn-blue left pop_submit_btn" style="margin-right: 20px">保存</div>
                                    <div class="btn-white left close" style="margin-left: 20px">返回</div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
    <!--角色 end-->
    <div class="clear"></div>
    <div class="part-btns clearfix" style="display:none">
        <a href="#" class="btn-blue left">保存</a>
        <a href="#" class="btn-white left">返回</a>
    </div>
</div>
<div class="pop-wrapper delMenu" style='display:none;'>
    <div class="pop-black"></div>
    <div class="pop-tips">
        <h2>您确定要执行删除该分类吗？</h2>
        <div class="pop-btns">
            <a href="#" class="btn-blue left orange confirm_btn">确定</a>
            <a href="#" class="btn-white left close">取消</a>
        </div>
    </div>
</div>
<!--确认弹框 end-->
</body>
</html>