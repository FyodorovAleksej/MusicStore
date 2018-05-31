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
    <title>Tracks</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>

<fmt:message key="main.trackNameLabel" var="nameLabel"/>
<fmt:message key="main.trackGenreLabel" var="genreLabel"/>
<fmt:message key="main.trackPriceLabel" var="priceLabel"/>
<fmt:message key="main.trackSummaryLabel" var="summaryLabel"/>
<fmt:message key="main.trackPerformerLabel" var="performerLabel"/>
<fmt:message key="main.trackDateLabel" var="dateLabel"/>
<fmt:message key="main.trackInfoLabel" var="infoLabel"/>
<fmt:message key="add.trackAddSendLabel" var="addLabel"/>

<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>

<button type="button" class="btn btn-primary" onClick='location.href="/prepareAddTrack"'><c:out value="${addLabel}"/></button>
<h3><c:out value="${tracksAll}"/></h3>
<table align="center" border="0">
    <tr>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"></h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${nameLabel}"/>
            </h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${genreLabel}"/>
            </h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${performerLabel}"/>
            </h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${dateLabel}"/>
            </h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${priceLabel}"/>
            </h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${summaryLabel}"/>
            </h4>
        </th>
    </tr>
    <c:forEach items="${trackSearchList}" var="trackSearch">
        <tr>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <button type="button" style = "margin-left: 10px; margin-bottom: 4px; margin-right: 20px;" class="btn btn-primary" onClick='location.href="/trackInfo?trackInfoName=${trackSearch.getName()}"'>
                        <c:out value="${infoLabel}"/>
                    </button>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackSearch.getName()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackSearch.getGenre()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackSearch.getPerformer()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackSearch.getDate()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackSearch.getPrice()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackSearch.getSummary()}"/>
                </h4>
            </td>
        </tr>
    </c:forEach>
</table>
<ctl:pageList-view/>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>