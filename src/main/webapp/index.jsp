<%--
  Created by IntelliJ IDEA.
  User: Alexey
  Date: 13.02.2018
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctl" uri="customTags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<html lang="${language}">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet"/>

    <title>MusicStore</title>
</head>
<body>
<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>
<div class="navbar-form">
    <c:import url="/WEB-INF/jspf/languageSelect.jsp"/>
    <ctl:user-info/>
</div>
<!-- Bootstrap CSS -->
<script src="js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="js/jquery-latest.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
