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
    <title>Own Assemblage</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
    <script src="${request.contextPath}/js/jquery-latest.js"></script>
    <script src="${request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<fmt:message key="main.ownAssemblegeHeader" var="assemblageHeader"/>
<fmt:message key="main.assemblageNameLabel" var="nameLabel"/>
<fmt:message key="main.assemblageGenreLabel" var="genreLabel"/>
<fmt:message key="main.assemblageDateLabel" var="dateLabel"/>
<fmt:message key="main.assemblageOwnerLabel" var="ownerLabel"/>

<div class="navbar-form">
    <c:import url="${request.contextPath}/WEB-INF/jspf/languageSelect.jsp"/>
    <ctl:user-info/>
</div>
<h1><c:out value="${assemblageHeader}"></c:out></h1>
<hr/>
<table>
    <tr>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">${nameLabel}</h4>
        </th>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">${genreLabel}</h4>
        </th>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">${dateLabel}</h4>
        </th>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">${ownerLabel}</h4>
        </th>
    </tr>
    <c:forEach items="${assemblageOwnList}" var="assemblageView">
        <tr>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                        ${assemblageView.getName()}
                </h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                        ${assemblageView.getGenre()}
                </h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                        ${assemblageView.getDate()}
                </h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                        ${assemblageView.getOwner()}
                </h5>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
