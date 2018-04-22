<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--<div class="pop-wrapper" style='display:none;' id="sqlRuleDetail">-->
    <div class="pop-black"></div>
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close" onclick="closePop()">关闭</div>
            <h2>${empty rule.id ? '新建':'编辑'}规则(手工)</h2>
        </div>
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-guize'>
                <%@include file="CommonRuleInfo.jsp"%>
                <input name="ruleMode" type="hidden" value="1">
                <tr>
                    <td valign='top'>规则内容</td>
                    <td>
                        <textarea cols="50" rows="10" class='text-area' name="ruleContent">${rule.ruleContent}</textarea>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="pop-btns">
                            <a href="javascript:void(0);" class="btn-blue left saveRulePop" onclick="saveRule()">保存</a>
                            <a href="javascript:void(0);" class="btn-white left closeRulePop" onclick="closeRulePop()">返回</a>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
<!--</div>-->