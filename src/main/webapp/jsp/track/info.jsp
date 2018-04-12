<%--
  Created by IntelliJ IDEA.
  User: Alexey
  Date: 09.04.2018
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctl" uri="customTags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Track Info</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
    <script src="${request.contextPath}/js/jquery-latest.js"></script>
    <script src="${request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<fmt:message key="main.commentSend" var="commentButton"/>
<fmt:message key="main.trackNameLabel" var="nameLabel"/>
<fmt:message key="main.trackGenreLabel" var="genreLabel"/>
<fmt:message key="main.trackPriceLabel" var="priceLabel"/>
<fmt:message key="main.trackDateLabel" var="dateLabel"/>
<fmt:message key="main.performerNameLabel" var="performerLabel"/>

<div class="navbar-form">
    <c:import url="${request.contextPath}/WEB-INF/jspf/languageSelect.jsp"/>
    <ctl:user-info/>
</div>

<div class="main-page form-horizontal">
    <h3>${trackName}</h3>
    <h4>${trackName}</h4>
    <h4>${trackGenre}</h4>
    <h4>${trackPrice}</h4>
    <h4>${trackDate}</h4>
    <h4>${performerName}</h4>
</div>
<br/>
<h4>Comments:</h4>
<br/>
<ctl:comments-view/>
</body>
</html>