<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>网关服务监控</title>
</head>
<body>
    <iframe  frameborder="0" src="http://localhost:5601/goto/${param.requestId}" height="600" width="100%"></iframe>
</body>
</html>