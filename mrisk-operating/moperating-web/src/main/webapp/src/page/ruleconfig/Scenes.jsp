<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html  lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/src/page/common/common.jsp" %>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="../common/TempSuccessPop.jsp"%>
    <title>配置规则管理_业务场景管理</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/common/jquery.page.css"/>" />
    <script type="text/javascript" src="<c:url value="/src/js/lib/jquery-3.1.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/jquery.page.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/page/scenesPolicies.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/WdatePicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript">
        $(function(){
            $("#search_btn").on("click", function(){
                window.location = '${basePath}/scenes/list.do?sceneNo='+$("#searchSceneNo").val()+"&sceneType="+${sceneType};
            });
            $(".s_select").on("change", function(){
                window.location = '${basePath}/scenes/list.do?sceneNo='+$("#selectSceneNo").val()+"&sceneType="+${sceneType};
            });
            $("#save_btn").on("click", function(){
                var url = '';
                var judge = $("#scence-no").attr("data-judge");
                if(judge != 'true'){
                    url = $("#updateUrl").val();
                }else{
                    url = $("#insertUrl").val();
                }
                var form_exp = "#form0";
                if(!validateScenes(form_exp)){
//                    $("#wrapper").hide();
                    return;
                }
                submitAjaxForm(form_exp,url,'reload');
            });

            $("#newBuilt").on("click", function () {
                var div = $("#wrapper");
                if (div.css("display") === "none") {
                    $("#newScence").html("新建场景");
                    $("#scence-no").removeAttr("readonly");
                    $("#scence-no").attr("data-judge",true);
                    $(".input").val("");
                    div.show();
                } else {
                    div.hide();
                }
            });
            $("#page").Page({
                        totalPages: ${totalPages},//分页总数
                        liNums: 9,//分页的数字按钮数(建议取奇数)
                        currentPage:${offset}/${limit}+1,
                      activeClass: 'activP', //active 类样式定义
                     limit:${limit},
            totalCount: ${totalCount},
            callBack : function(page){
                var offset = (page-1)*${limit};
                window.location = '${basePath}/scenes/list.do?sceneNo='+$("#selectSceneNo").val()+'&offset='+offset+'&limit=${limit}'+"&sceneType="+${sceneType};
            }
        });

            $(".close_btn").on("click", function(){
                 $("#wrapper").hide();
            });
            var confirm_del_scene = new Confirm({
                'wrapper':$("#confirm_del_scene"),
                'trigger':{'parent':$(".tablebox"), 'selector':'.del_scene'},
                confirmHandel:function($theBtn){
                    delBySceneNo($theBtn.attr('sceneNo'), '${basePath}/scenes/delById.do');
                }
            });
            confirm_del_scene.init();
        })

        function editScene(thisObject){
            $("#newScence").html("编辑场景");
            $("#scence-name").val($(thisObject).attr("data-name"));
            $("#scence-no").val($(thisObject).attr("data-no"));
            $("#createdDate").val($(thisObject).attr("create-time"));
            $("#scence-no").attr("data-judge",false);
            $("#scence-no").attr("readonly","true");
            $("#scence-des").val($(thisObject).attr("data-des"));
            var div = $("#wrapper");
            if (div.css("display") === "none") {
                div.show();
            } else {
                div.hide();
            }
        }

    </script>
</head>
<body>
    <div class="content">
        <input type="hidden" id="updateUrl" value="${basePath}/scenes/update.do">
        <input type="hidden" id="insertUrl" value="${basePath}/scenes/insert.do">
        <div class="search clearfix">
            <ul class='search-ul left clearfix'>
                <li class="left">
                    <label>场景名称</label>
                    <select class="input-select s_select"  id="selectSceneNo">
                        <option value="all" <c:if test="${sceneNo eq 'all'}">selected="selected"</c:if>>全部</option>
                        <c:forEach items="${allScenes}" var="item">
                            <option value="${item.sceneNo}" <c:if test="${item.sceneNo eq sceneNo}">selected="selected"</c:if>>${item.name}(${item.sceneNo})</option>
                        </c:forEach>
                    </select>
                </li>
                <li class="left">
                    <label>场景标示号</label>
                    <div class="input-text"><input type="text" class="input"  value="${sceneNo}" id="searchSceneNo"></div>
                </li>
            </ul>
            <div id="search_btn" class="btn left">查询</div>
        </div>
        <!--search end-->
        <!--新建场景按钮开始-->
        <div class="btn-add btn-add-long" id="newBuilt">新建场景</div>
        <div class="list mar20 tablebox">
            <ul class="list-changjing">
                <c:forEach var="item" items="${list}" varStatus="status">
                <li>
                    <p>
                        <i class="iconfont" create-time="${item.createdDateStr}" data-name ="${item.name}" data-no="${item.sceneNo}" data-des="${item.description}" title='编辑' onclick="editScene(this)">&#xe6f5;</i>
                        <i class="iconfont del_scene" sceneNo="${item.sceneNo}"  title='删除'>&#xe738;</i>
                    </p>
                    <h2>${item.name}（${item.sceneNo}）</h2>
                </li>
                </c:forEach>
            </ul>
            <div class="pagination clearfix">
            <c:if test="${not empty list}">
                <div id="page" style="float:right;"></div>
            </c:if>
            <div class="clear"></div>
            </div>

        </div>
    </div>
    <!--content value="${item.sceneNo}" readonly="true"  end-->
    <div class="pop-wrapper" style='display:none;'  id="wrapper">
        <div class="pop-black"></div>
        <div class="pop">
            <form method="post" id="form0">
            <div class="pop-title">
                <div class="pop-close">关闭</div>
                <h2 id="newScence">新建场景</h2>
            </div>
            <div class="pop-cnt">
                <table cellpadding='0' cellspacing='0' class='table-inside'>
                    <tr>
                        <td>场景名称</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input type="text" id="scence-name" class="input" name="name"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>场景标识</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input type="text"  name="sceneNo" id="scence-no" name="sceneNo" class="input"></div>
                        </td>
                    </tr>
                    <input type="hidden" name="sceneType" value="${sceneType}">
                    <tr>
                        <td>场景描述</td>
                        <td>
                            <div class="input-text" style='width:300px;'>
                                <input type="text" id="scence-des" class="input" name="description">
                                <%--<input type="hidden" name="createdDate" id="createdDate">--%>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div class="pop-btns">
                                <div  class="btn-blue left" id="save_btn"  style="margin-right: 5%">保存</div>
                                <div  class="btn-white left close_btn" style="margin-left: 5%">返回</div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
                </form>
        </div>
    </div>
    <div class="pop-wrapper sure" style='display:none;' id="confirm_del_scene">
        <div class="pop-black"></div>
        <div class="pop-tips">
            <h2>您确定要执行该操作吗？</h2>
            <div class="pop-btns">
                <a javascript:void(0); class="btn-blue left orange confirm_btn">确定</a>
                <a javascript:void(0); class="btn-white left cancel_btn">取消</a>
            </div>
        </div>
    </div>
    <!--新建场景end
    <div class="pop-wrapper" style='display:none;'>
        <div class="pop-black"></div>
        <div class="pop-tip">
            <i class="iconfont ok">&#xe72d;</i>
            <p>新建场景成功</p>
        </div>
    </div>
    <!--新建场景成功 end
    <div class="pop-wrapper">
        <div class="pop-black"></div>
        <div class="pop-tip">
            <i class="iconfont error">&#xe6f2;</i>
            <p>新建场景失败</p>
        </div>
    </div>
    <!--新建场景失败 end-->
</body>
</html>