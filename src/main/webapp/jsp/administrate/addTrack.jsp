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
    <title>users</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>

<fmt:message key="add.trackNameLabel" var="trackNameLabel"/>
<fmt:message key="add.trackGenreLabel" var="trackGenreLabel"/>
<fmt:message key="add.trackPriceLabel" var="trackPriceLabel"/>
<fmt:message key="add.trackPerformerLabel" var="trackPerformerLabel"/>
<fmt:message key="add.trackAddSendLabel" var="trackAddSendLabel"/>
<fmt:message key="add.trackFileLabel" var="trackFileLabel"/>


<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>

<form action="/addNewTrack" method="post" enctype='multipart/form-data'>
    <table align="center">
        <tr>
            <td>
                <label for="name"><c:out value="${trackNameLabel}"/></label>
            </td>
            <td>
                <input type="text" class="text-left" id="name" style="margin-left: 10px; margin-bottom: 2px;" name="trackName">
            </td>
        </tr>
        <tr>
            <td>
                <label for="genre"><c:out value="${trackGenreLabel}"/></label>
            </td>
            <td>
                <input type="text" class="text-left" id="genre" name="trackGenre" style="margin-left: 10px; margin-bottom: 2px;">
            </td>
        </tr>
        <tr>
            <td>
                <label for="price"><c:out value="${trackPriceLabel}"/></label>
            </td>
            <td>
                <input type="number" min="0" class="text-left" id="price" name="trackPrice" value="0" style="margin-left: 10px; margin-bottom: 2px;">
            </td>
        </tr>
        <tr>
            <td>
                <label for="performer"><c:out value="${trackPerformerLabel}"/></label>
            </td>
            <td>
                <input type="text" class="text-left" id="performer" name="trackPerformer" style="margin-left: 10px; margin-bottom: 2px;">
            </td>
        </tr>
        <tr>
            <td>
                <label for="file"><c:out value="${trackFileLabel}"/></label>
            </td>
            <td>
                <input type="file" accept=".mp3, .wav" id="file" name="inputFile" class="btn" style="margin-left: 10px; margin-bottom: 2px;"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button type="submit" class="btn btn-primary" style="margin-top: 5px"><c:out value="${trackAddSendLabel}"/></button>
            </td>
        </tr>
    </table>
</form>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
