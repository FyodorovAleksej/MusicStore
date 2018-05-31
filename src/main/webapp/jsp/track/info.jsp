<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctl" uri="customTags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Track Info</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<fmt:message key="main.commentSend" var="commentButton"/>
<fmt:message key="main.trackNameLabel" var="nameLabel"/>
<fmt:message key="main.trackGenreLabel" var="genreLabel"/>
<fmt:message key="main.trackPriceLabel" var="priceLabel"/>
<fmt:message key="main.trackDateLabel" var="dateLabel"/>
<fmt:message key="main.performerNameLabel" var="performerLabel"/>
<fmt:message key="main.trackBuyLabel" var="buyLabel"/>
<fmt:message key="main.TrackEditLabel" var="editLabel"/>
<fmt:message key="main.TrackRemoveLabel" var="removeLabel"/>

<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>

<div class="main-page form-horizontal">
    <h3><c:out value="${trackName}"/></h3>
    <h4><c:out value="${trackName}"/></h4>
    <h4><c:out value="${trackGenre}"/></h4>
    <h4><c:out value="${trackPrice}"/></h4>
    <h4><c:out value="${trackDate}"/></h4>
    <h4><c:out value="${performerName}"/></h4>
</div>
<form action="/buyTrack" method="post">
    <button type="submit" class="btn btn-primary"><c:out value="${buyLabel}${trackPrice}"/></button>
</form>
<hr/>
<h4><c:out value="Comments:"/></h4>
<table>
    <tr>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"></h4>
        </th>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"></h4>
        </th>
        <th>
            <h4 style="padding-left: 25px; margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"></h4>
        </th>
    </tr>
    <c:forEach items="${commentList}" var="comment">
        <tr>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                    <c:out value="${comment.getUserName()}"/>
                </h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                    <c:out value="${comment.getText()}"/>
                </h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">
                    <c:out value="${comment.getDate()}"/>
                </h5>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="/prepareEditTrack" method="post">
    <button type="submit" class="btn btn-primary" style="background-color: rgba(255,155,0,0.80)"><c:out
            value="${editLabel}"/></button>
</form>
<form action="/removeTrack" method="post">
    <button type="submit" class="btn btn-primary" style="background-color: rgba(255,55,35,0.80)"><c:out
            value="${removeLabel}"/></button>
</form>

<form action="/comment" method="post">
    <textarea type="text" name="newComment" style="padding-left: 25px;"></textarea>
    <button type="submit" class="btn btn-primary"><c:out value="${commentButton}"/></button>
</form>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>