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
    <title>Albums</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>

<fmt:message key="main.albumNameLabel" var="nameLabel"/>
<fmt:message key="main.albumGenreLabel" var="genreLabel"/>
<fmt:message key="main.albumPriceLabel" var="priceLabel"/>
<fmt:message key="main.albumSummaryLabel" var="summaryLabel"/>
<fmt:message key="main.albumPerformerLabel" var="performerLabel"/>
<fmt:message key="main.albumDateLabel" var="dateLabel"/>
<fmt:message key="main.albumInfoLabel" var="infoLabel"/>
<fmt:message key="add.addAlbumLabel" var="addLabel"/>

<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>

<button type="button" class="btn btn-primary" onClick='location.href="/prepareAddAlbum"'><c:out value="${addLabel}"/></button>
<h3><c:out value="${albumsAll}"/></h3>
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
    <c:forEach items="${albumSearchList}" var="albumSearch">
        <tr>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <button type="button" style = "margin-left: 10px; margin-bottom: 4px; margin-right: 20px;" class="btn btn-primary" onClick='location.href="/albumInfo?albumInfoName=${albumSearch.getName()}"'>
                        <c:out value="${infoLabel}"/>
                    </button>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumSearch.getName()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumSearch.getGenre()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumSearch.getPerformer()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumSearch.getDate()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumSearch.getPrice()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumSearch.getSummary()}"/>
                </h4>
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

