<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="search clearfix">
    <div class="pop-btns right">
        <a href="javascript:void(0);" class="btn-blue left" style='width:110px;' onclick="editRule(0,1)">新建规则(手工)</a>
        <a href="javascript:void(0);" class="btn-blue left" style='width:110px;' onclick="editRule(0,0)">新建规则(配置)</a>
    </div>
    <ul class='search-ul left clearfix'>
        <li class="left">
            <label>规则名称</label>
            <div class="input-text"><input type="text" class="input"></div>
        </li>
    </ul>
    <a href='javascript:void(0);' class="btn left" id="search_btn">查询</a>
</div>
<!--search end-->
<c:forEach items="${ruleList}" var="item">
    <div class="list-celue">
        <div class="list-celue-title clearfix">
            <p class='list-icons' ruleId="${item.id}">
                <i class='boxs'>
                    <span class="to_update_status ${item.status eq 1 ? 'selected': ''}" toStatus="1">关闭</span>
                    <span class="to_update_status ${item.status eq 4 ? 'selected': ''}" toStatus="4">模拟</span>
                    <span class="to_update_status ${item.status eq 2 ? 'selected': ''}" toStatus="2">正式</span>
                </i>
                <i class="iconfont" title='编辑' onclick="editRule(${item.id},${item.ruleMode})">&#xe6f5;</i>
                <i class="iconfont" title='删除' id="delRule" toStatus="3">&#xe738;</i>
            </p>
            <h2>
                <span id="ruleName_${item.id}">${item.name}</span>
            </h2>
        </div>
    </div>
</c:forEach>
