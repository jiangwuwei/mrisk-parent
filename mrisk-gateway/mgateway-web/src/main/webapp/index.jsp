<%@ page import="com.zoom.risk.gateway.service.utils.Machines" %>
<%@page language="java" pageEncoding="utf-8" contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>mrisk-gateway</title>
</head>
<body>
<h2>mrisk-gateway</h2>
<h4 style="display:none">Mac Address: <span style="color:blue"><%= Machines.getMac()%></span></h4>
</body>
</html>

