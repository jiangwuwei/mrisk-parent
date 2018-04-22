<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="pop-wrapper" style='display:none;' id="onErrorPop">
    <div class="pop-black"></div>
    <div class="pop-tip">
        <i class="iconfont error">&#xe6f2;</i>
        <p>${empty errorMsg ? '操作失败': errorMsg}</p>
    </div>
</div>
<c:remove var="errorMsg"/>