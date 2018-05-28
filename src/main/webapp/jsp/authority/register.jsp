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

    <title>MusicStore</title>
</head>
<body>
<fmt:message key="main.signUpSend" var="sendButton"/>
<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>
<br/>
<c:import url="${request.contextPath}/WEB-INF/jspf/languageSelect.jsp"/>
<form action="${pageContext.request.contextPath}/register" method="post">
    <input type="text" name="uuid"/>
    <button type="submit" class="btn btn-primary"><c:out value="${sendButton}"/></button>
</form>
<h2>${uuidResult}</h2>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>