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
</head>
<body>
<fmt:message key="main.adminView" var="adminButton"/>
<fmt:message key="main.welcome" var="welcome"/>
<fmt:message key="main.role" var="as"/>
<fmt:message key="main.logout" var="logout"/>
<fmt:message key="main.ownTracks" var="ownTracks"/>
<fmt:message key="main.ownAlbums" var="ownAlbums"/>
<fmt:message key="main.ownAssemblages" var="ownAssemblages"/>

<button type="button" class="btn btn-primary" onClick='location.href="/admin"'>${adminButton}</button>
<form action="/viewUserTracks" method="get" class="navbar-form pull-right">
    <button type="submit" class="btn btn-primary">${ownTracks}</button>
</form>
<form action="/viewUserAlbums" method="get" class="navbar-form pull-right">
    <button type="submit" class="btn btn-primary">${ownAlbums}</button>
</form>
<form action="/viewUserAssemblages" method="get" class="navbar-form pull-right">
    <button type="submit" class="btn btn-primary">${ownAssemblages}</button>
</form>
<div class="nav-item">
    <h4 style="color:#66fff5"><c:out value="${welcome}, ${userName} ${as} ${userRole}"/></h4>
    <button type="button" class="btn btn-primary" onClick='location.href="/logout"'><c:out value="${logout}"/></button>
</div>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>