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
    <title>Own Album</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<fmt:message key="main.ownAlbumHeader" var="albumHeader"/>
<fmt:message key="main.albumNameLabel" var="nameLabel"/>
<fmt:message key="main.albumGenreLabel" var="genreLabel"/>
<fmt:message key="main.albumDateLabel" var="dateLabel"/>
<fmt:message key="main.performerNameLabel" var="performerLabel"/>

<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>
    <h1><c:out value="${albumHeader}"/></h1>
<hr/>
<table>
    <tr>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"><c:out value="${nameLabel}"/></h4>
        </th>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"><c:out value="${genreLabel}"/></h4>
        </th>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"><c:out value="${dateLabel}"/></h4>
        </th>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"><c:out value="${performerLabel}"/></h4>
        </th>
    </tr>
    <c:forEach items="${albumOwnList}" var="albumView">
        <tr>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                    <c:out value="${albumView.getName()}"/>
                </h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                    <c:out value="${albumView.getGenre()}"/>
                </h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                    <c:out value="${albumView.getDate()}"/>
                </h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                    <c:out value="${albumView.getPerformer()}"/>
                </h5>
            </td>
        </tr>
    </c:forEach>
</table>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>