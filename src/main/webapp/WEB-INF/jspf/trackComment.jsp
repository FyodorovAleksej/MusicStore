<%--
  Created by IntelliJ IDEA.
  User: Alexey
  Date: 11.04.2018
  Time: 11:35
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
</head>
<body>

<fmt:message key="main.commentUserNameLabel" var="userNameLabel"/>
<fmt:message key="main.commentTextLabel" var="commentTextLabel"/>
<fmt:message key="main.commentDateLabel" var="commentDateLabel"/>

<table align="center" border="0">
    <tr>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${userNameLabel}"/>
            </h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${commentTextLabel}"/>
            </h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${commentDateLabel}"/>
            </h4>
        </th>
    </tr>
    <c:out value="${comments}"/>
</table>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
