<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctl" uri="customTags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${request.contextPath}/js/bootstrap/favicon.ico">

    <title>Sticky Footer Navbar Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="${request.contextPath}/js/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${request.contextPath}/css/navbar.css" rel="stylesheet">
</head>
<body>
<fmt:message key="main.trackFind" var="trackLink"/>
<fmt:message key="main.albumFind" var="albumLink"/>
<fmt:message key="main.assemblageFind" var="assemblageLink"/>
<fmt:message key="main.home" var="homeLink"/>
<header>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="navbar-brand" href="/"><c:out value="${homeLink}"/></a>
                </li>
                <li class="nav-item">
                    <a class="navbar-brand" href="/tracks"><c:out value="${trackLink}"/></a>
                </li>
                <li class="nav-item">
                    <a class="navbar-brand" href="/albums"><c:out value="${albumLink}"/></a>
                </li>
                <li class="nav-item">
                    <a class="navbar-brand" href="/assemblages"><c:out value="${assemblageLink}"/></a>
                </li>
            </ul>
            <div class="navbar-form pull-left">
                <form class="orm-inline mt-2 mt-md-0">
                    <select class="custom-select" id="language" name="language" onchange="submit()">
                        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
                        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                    </select>
                </form>
            </div>
            <div class="navbar-form">
                <ctl:user-info/>
            </div>
        </div>
    </nav>
</header>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script>window.jQuery || document.write('<script src="${request.contextPath}/js/bootstrap/assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
<script src="${request.contextPath}/js/bootstrap/assets/js/vendor/popper.min.js"></script>
<script src="${request.contextPath}/js/bootstrap/dist/js/bootstrap.min.js"></script>
</body>
</html>
