<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!--<div class="slide-item2 mar20" id="slideInfoContentSaas" style="display:none">-->
    <div class="box">
        <h2>uid关联设备(uid:${param.uid})</h2>
        <table cellpadding='0' cellspacing='0'>
            <thead>
            <tr>
                <td class='blue'>设备</td>
                <td class='blue'>出现次数</td>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${deviceFingerprints}" var="entry">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="box mar20">
        <h2>设备关联uid(设备ID)</h2>
        <table cellpadding='0' cellspacing='0'>
            <thead>
            <tr>
                <td class='blue'>设备</td>
                <td class='blue'>出现次数</td>
            </tr>
            </thead>
            <tbody>
            <c:if test="${ not empty uids }">
                <c:forEach items="${uids}" var="entry">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
   <!-- <div class="box mar20">
        <h2>业务数据</h2>
        <table cellpadding='0' cellspacing='0'>
            <tr>
                <td>设备精准ID：203.203.42.429</td>
                <td>用户ID：37258365683</td>
            </tr>
            <tr>
                <td>设备智能ID：35436476586796969969696</td>
                <td>事件时间：2017-03-23 11:23:12</td>
            </tr>
            <tr>
                <td colspan='2'>事件标识：131313</td>
            </tr>
        </table>
        <div class="line"></div>
        <table cellpadding='0' cellspacing='0'>
            <tr>
                <td>IP归属城市：北京市</td>
                <td>IP归属省份：北京市</td>
            </tr>
        </table>
    </div>-->
<!--</div>-->