<%--
  Created by IntelliJ IDEA.
  User: Alexey
  Date: 29.03.2018
  Time: 14:23
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
<fmt:message key="main.welcome" var="welcome"/>
<fmt:message key="main.role" var="as"/>
<fmt:message key="main.logout" var="logout"/>
<fmt:message key="main.ownTracks" var="ownTracks"/>
<form action="/viewUserTracks" method="post" class="navbar-form pull-right">
    <button type="submit" class="btn btn-primary">${ownTracks}</button>
</form>
<div class="navbar-form pull-right">
    <h4>${welcome}, <c:out value="${userName}"/> ${as} <c:out value="${userRole}"/></h4>
    <button type="button" class="btn btn-primary" onClick='location.href="/logout"'>${logout}</button>
</div>
</body>
</html>
