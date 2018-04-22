<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <script type="text/javascript">


        $(function () {
            $(".label_tab").on("click", function(){
                if ($(this).find('.iconfont').hasClass('up')) {
                    $(this).find('.iconfont').removeClass('up');
                    $(this).find('.iconfont').addClass('down');
                    $(this).find(".iconfont").html("&#xe612");
                }else{
                    $(this).find('.iconfont').removeClass('down');
                    $(this).find('.iconfont').addClass('up');
                    $(this).find(".iconfont").html("&#xe69a");
                }
                //$(this).find(".iconfont").html("&#xe612");
                $(this).closest(".list-celue").find(".list-celue-ul").slideToggle();
            });

            $(".label_tab_2").on("click", function(){
                var liTable = $(this).parent().parent().next();
                if ( liTable ){
                    liTable.slideToggle();
                }
            });
        });

        function  closeWin() {
            $("#wrapper").hide();
        }

        /**查找到参数以及路径
         * @param paramId  前端的id
         */
        function editScardParam(paramId) {
            $("#wrapper").load("<c:url value='/scardParam/findScardParam.do'/>", {"paramId":paramId}, function () {
                $("#wrapper").show();
            })
        }

        /**
         * @param id 删除参数
         */
        function delScardParam (paramId) {
            if ( confirm("您确定要从评分卡删除该参数变量吗")) {
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/scardParam/delScardParam.do'/>",
                    dataType: 'json',
                    data: {
                        "paramId": paramId
                    },
                    error: function (request) {
                        showTempErrorPop("Connection error!");
                    },
                    success: function (data) {
                        if (data.res.code == 0 ) {
                            showTempSuccessPop("删除参数变量成功");
                            $("#scardParam_" + paramId).remove();
                        } else {
                            showTempErrorPop("删除失败,可能原因是参数已经创建了路径!");
                        }
                    }
                })
            }
        }

        /*
         *保存参数的 缺省分数已经权重
         */
        function saveParamConfig() {
            if ( confirm("您确认保存参数变量的修改吗?")) {
                var jsonData = $("#paramForm").serialize();
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/scardParam/updateParams.do'/>" ,
                    dataType: 'json',
                    data: jsonData,
                    error: function (request) {
                        showTempErrorPop("Connection error!");
                    },
                    success: function (data) {
                        if (data.res.code == 0 ) {
                            showTempSuccessPop("保存参数变量操作成功");
                        } else {
                            showTempErrorPop("保存失败,可能原因是参数名称重复!");
                        }
                    }
                })
            }
        }

        /**
         * idValue  是后端的 id
         * @param routeId  前端的id
         * @param flag
         */
        function delScardRoute(routeId, flag) {
            var idValue = $("#paramRouteForm" + routeId ).find("[name=id]").val();
            if ( idValue < 0  ){
                $("#route" + routeId).remove();
            }else {
                if (confirm("您确认要删除该分支路径吗?")) {
                    $.ajax({
                        type: "POST",
                        url: "<c:url value='/scardParamRoute/delScardParamRoute.do'/>",
                        dataType: 'json',
                        data: {"routeId": idValue },
                        error: function (request) {
                            showTempErrorPop("Connection error!");
                        },
                        success: function (data) {
                            if (data.res.code == 0) {
                                $("#route" + routeId).remove();
                                showTempSuccessPop("删除路径操作成功");
                            } else {
                                showTempErrorPop("保存失败,可能原因是参数名称重复!");
                            }
                        }
                    })
                }
            }

        }

        /**
         * 保存参数的路径
         * @param routeId
         * @param flag
         */
        function saveScardRoute(routeId, flag) {
            if (confirm("您确认保存该分支路径吗?")) {
                var jsonData = $("#paramRouteForm" + routeId ).serialize();
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/scardParamRoute/saveScardParam.do'/>",
                    dataType: 'json',
                    data: jsonData,
                    error: function (request) {
                        showTempErrorPop("Connection error!");
                    },
                    success: function (data) {
                        if (data.res.code == 0) {
                            if( data.id > 0 ) {
                                $("#paramRouteForm" + routeId).find("[name=id]").val(data.id);
                            }
                            showTempSuccessPop("保存路径操作成功");
                        } else {
                            showTempErrorPop("保存失败,可能原因是参数名称重复!");
                        }
                    }
                })
            }
        }

    </script>

<div class="list mar20 tablebox">
    <c:forEach var="item" items="${scardParamsList}" varStatus="status">
        <div class="list-celue">
        <div class="list-celue-title clearfix">
            <p class="list-icons">
                <i class="iconfont" title="添加" style="display:none;"></i>
                <i class="iconfont" title='删除指标' style="display:none;">&#xe738;</i>
            </p>
            <h2><div class="label_tab" style="width: 70%">
                <i class="iconfont up"></i><span style="font-weight: bold">${item.typeName}</span>
            </div>
            </h2>
        </div>
        <ul class="list-celue-ul" style="display: block;" id="sceneNo_${item.typeId}">
            <c:forEach var="scardParam" items="${item.paramList}" varStatus="qstatus">
                <li class="clearfix" id="scardParam_${scardParam.id}" style="line-height: 40px;">
                    <p class="list-icons">
                        <i class="iconfont" title='编辑指标' onclick="editScardParam('${scardParam.id}')">&#xe6f5;</i>
                        <i class="iconfont" title='删除指标' onclick="delScardParam('${scardParam.id}')">&#xe738;</i>
                    </p>

                    <h2>
                        <div class="label_tab_2" style="width: 70%">
                        <i class="dot"></i><span>${scardParam.chineseName}<span style="color:blue">
                            【${scardParam.paramNo} - ${scardParam.paramName} - ${scardParam.defaultScore} - ${scardParam.weightValue}】 </span></span>
                        </div>
                    </h2>
                </li>
                <li class="clearfix" style="display:none" id="scardParam_detail_${quotas.id}" style="line-height: 40px;">
                        <table style="width:95%;text-align:center;" border="1px">
                            <thead>
                            <tr style="line-height:40px;background-color:#cacaca;font-weight:bold;">
                                <td width="35%">参数名称</td>
                                <td width="10%">分数</td>
                                <td>路径表达式</td>
                            </tr>
                            </thead>
                            <c:forEach var="para" items="${scardParam.routeList}" varStatus="pstatus">
                                <tbody>
                                <tr id="para_${para.paramName}" style="line-height: 40px;">
                                    <td>${para.routeName}</td>
                                    <td>${para.routeScore}</td>
                                    <td>${para.routeExpression}</td>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                </li>
            </c:forEach>
        </ul>
    </div>
    </c:forEach>
</div>
<div class="pop-btns">
    <a href="#" class="btn-white" id="return_policy_btn" style="margin-left:500px" onclick="javascript:window.location='<c:url value="/scardPolicies/list.do?sceneNo=all"/>'">返回</a>
</div>
<div class="pop-wrapper" style="display:none; width:800px"  id="wrapper"></div>