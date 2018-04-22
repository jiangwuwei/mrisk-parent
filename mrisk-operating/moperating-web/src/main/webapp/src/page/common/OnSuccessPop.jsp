<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="pop-wrapper" style='display:none;' id="onSuccessPop">
    <div class="pop-black"></div>
    <div class="pop-tip">
        <i class="iconfont ok">&#xe72d;</i>
        <p>${empty successMsg ? '操作成功': successMsg}</p>
    </div>
</div>

<c:remove var="successMsg"/>