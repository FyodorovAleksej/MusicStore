<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctl" uri="customTags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
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
<fmt:message key="add.trackDateLabel" var="trackDateLabel"/>

<fmt:message key="add.assemblageNameLabel" var="assemblageNameLabel"/>
<fmt:message key="add.assemblageGenreLabel" var="assemblageGenreLabel"/>
<fmt:message key="add.assemblagePriceLabel" var="assemblagePriceLabel"/>
<fmt:message key="add.assemblageAddSendLabel" var="assemblageAddSendLabel"/>
<fmt:message key="add.trackChooseLabel" var="trackChooseLabel"/>

<fmt:message key="genre.classicGenre" var="classicGenreLabel"/>
<fmt:message key="genre.electroGenre" var="electroGenreLabel"/>
<fmt:message key="genre.popGenre" var="popGenreLabel"/>
<fmt:message key="genre.rockGenre" var="rockGenreLabel"/>
<fmt:message key="genre.jazzGenre" var="jazzGenreLabel"/>
<fmt:message key="genre.bluesGenre" var="bluesGenreLabel"/>


<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>

<form action="/addNewAssemblage" method="post">
    <table align="center">
        <tr>
            <td>
                <label for="name"><c:out value="${assemblageNameLabel}"/></label>
            </td>
            <td>
                <input type="text" class="text-left" id="name" style="margin-left: 10px; margin-bottom: 2px;"
                       name="assemblageName">
            </td>
        </tr>
        <tr>
            <td>
                <label for="price"><c:out value="${assemblagePriceLabel}"/></label>
            </td>
            <td>
                <input type="number" min="0" class="text-left" id="price" name="assemblagePrice" value="0"
                       style="margin-left: 10px; margin-bottom: 2px;">
            </td>
        </tr>
    </table>
    <fieldset>
        <legend><c:out value="${assemblageGenreLabel}"/></legend>
        <div>
            <input type="checkbox" id="classic" name="assemblageGenre" value="classic" ${classicGenre}>
            <label for="classic"><c:out value="${classicGenreLabel}"/></label>
        </div>
        <div>
            <input type="checkbox" id="electro" name="assemblageGenre" value="electro" ${electroGenre}>
            <label for="electro"><c:out value="${electroGenreLabel}"/></label>
        </div>
        <div>
            <input type="checkbox" id="pop" name="assemblageGenre" value="pop" ${popGenre}>
            <label for="pop"><c:out value="${popGenreLabel}"/></label>
        </div>
        <div>
            <input type="checkbox" id="rock" name="assemblageGenre" value="rock" ${rockGenre}>
            <label for="rock"><c:out value="${rockGenreLabel}"/></label>
        </div>
        <div>
            <input type="checkbox" id="jazz" name="assemblageGenre" value="jazz" ${jazzGenre}>
            <label for="jazz"><c:out value="${jazzGenreLabel}"/></label>
        </div>
        <div>
            <input type="checkbox" id="blues" name="assemblageGenre" value="blues" ${bluesGenre}>
            <label for="blues"><c:out value="${bluesGenreLabel}"/></label>
        </div>
    </fieldset>


    <table align="center" border="0">
        <tr>
            <th>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"><c:out value="${trackChooseLabel}"/></h4>
            </th>
            <th>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackNameLabel}"/>
                </h4>
            </th>
            <th>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackGenreLabel}"/>
                </h4>
            </th>
            <th>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackPerformerLabel}"/>
                </h4>
            </th>
            <th>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${trackDateLabel}"/>
                </h4>
            </th>
        </tr>
        <c:forEach items="${trackSearchList}" var="trackSearch">
            <tr>
                <td>
                    <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                        <input type="checkbox" id="trackCheck" name="chooseTrack" value="${trackSearch.getName()}">
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
            </tr>
        </c:forEach>
    </table>

    <button type="submit" class="btn btn-primary" style="margin-top: 5px"><c:out value="${assemblageAddSendLabel}"/></button>
</form>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
