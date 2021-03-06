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
    <title>Album Info</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<fmt:message key="main.trackNameLabel" var="nameLabel"/>
<fmt:message key="main.trackGenreLabel" var="genreLabel"/>
<fmt:message key="main.trackDateLabel" var="dateLabel"/>
<fmt:message key="main.performerNameLabel" var="performerLabel"/>
<fmt:message key="main.trackInfoLabel" var="infoLabel"/>
<fmt:message key="main.albumBuyLabel" var="buyLabel"/>
<fmt:message key="main.albumTracksLabel" var="tracksLabel"/>
<fmt:message key="main.albumEditLabel" var="editLabel"/>
<fmt:message key="main.albumRemoveLabel" var="removeLabel"/>

<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>

<div class="main-page form-horizontal">
    <h3><c:out value="${assemblageName}"/></h3>
    <h4><c:out value="${assemblageName}"/></h4>
    <h4><c:out value="${albumInfoGenre}"/></h4>
    <h4><c:out value="${albumInfoPrice}"/></h4>
    <h4><c:out value="${albumInfoDate}"/></h4>
    <h4><c:out value="${albumInfoPerformer}"/></h4>
</div>
<form action="/buyAlbum" method="post">
    <button type="submit" class="btn btn-primary"><c:out value="${buyLabel}${albumInfoSummary}"/></button>
</form>
<hr/>
<h4><c:out value="${tracksLabel}"/></h4>
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
    </tr>
    <c:forEach items="${albumTrackList}" var="albumTrack">
        <tr>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <button type="button" style = "margin-left: 10px; margin-bottom: 4px; margin-right: 20px;" class="btn btn-primary" onClick='location.href="/trackInfo?trackInfoName=${albumTrack.getName()}"'>
                        <c:out value="${infoLabel}"/>
                    </button>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumTrack.getName()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumTrack.getGenre()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumTrack.getPerformer()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${albumTrack.getDate()}"/>
                </h4>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="/editAlbum" method="post">
    <button type="submit" class="btn btn-primary" style="background-color: rgba(255,155,0,0.80)"><c:out value="${editLabel}"/></button>
</form>
<form action="/removeAlbum" method="post">
    <button type="submit" class="btn btn-primary" style="background-color: rgba(255,55,35,0.80)"><c:out value="${removeLabel}"/></button>
</form>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>