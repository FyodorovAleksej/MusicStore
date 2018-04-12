<%--
  Created by IntelliJ IDEA.
  User: Alexey
  Date: 31.03.2018
  Time: 03:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctl" uri="customTags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
    <script src="${request.contextPath}/js/jquery-latest.js"></script>
    <script src="${request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>

<fmt:message key="main.loginLabel" var="loginLabel"/>
<fmt:message key="main.passwordLabel" var="passwordLabel"/>
<fmt:message key="main.signInSend" var="signInSend"/>
<fmt:message key="main.signUpSend" var="signUpSend"/>


<form action="/login" method="post" class="navbar-form pull-right">
    <input class="span2" type="text" placeholder="${loginLabel}" name="login">
    <input class="span2" type="password" placeholder="${passwordLabel}" href="password" name="password">
    <button type="submit" class="btn btn-primary">${signInSend}</button>
    <button type="button" class="btn btn-primary" onClick='location.href="/jsp/authority/signUp.jsp"'>${signUpSend}</button>
</form>
</body>
</html>
