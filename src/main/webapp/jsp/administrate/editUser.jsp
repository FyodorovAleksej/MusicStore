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
    <title>User Edit</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<fmt:message key="user.userRoleLabel" var="userRoleLabel"/>
<fmt:message key="user.userRoleUserLabel" var="userRoleUserLabel"/>
<fmt:message key="user.userRoleAdminLabel" var="userRoleAdminLabel"/>
<fmt:message key="user.userDiscountLabel" var="userDiscountLabel"/>
<fmt:message key="user.bonusesLabel" var="userBonusesLabel"/>
<fmt:message key="user.trackBonusLabel" var="userTrackBonusLabel"/>
<fmt:message key="user.albumBonusLabel" var="userAlbumBonusLabel"/>
<fmt:message key="user.assemblageBonusLabel" var="userAssemblageBonusLabel"/>
<fmt:message key="user.sendEditLabel" var="userSendEditLabel"/>

<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>

<form action="/editUser" method="post">
    <div>
        <label for="role"><c:out value="${userRoleLabel}"/></label>
        <select id="role" name="userEditRole">
            <option value="user" ${userEditUserRole}><c:out value="${userRoleUserLabel}"/></option>
            <option value="admin" ${userEditAdminRole}><c:out value="${userRoleAdminLabel}"/></option>
        </select>
    </div>
    <div>
        <label for="discount"><c:out value="${userDiscountLabel}"/></label>
        <input type="number" id="discount" min="0" max="100" name="userEditDiscount" value="${userEditDiscount}"/>
    </div>
    <fieldset>
        <legend><c:out value="${userBonusesLabel}"/></legend>
        <div>
            <input type="checkbox" id="track" name="userBonus" value="free track" ${userEditTrackBonus}>
            <label for="track"><c:out value="${userTrackBonusLabel}"/></label>
        </div>
        <div>
            <input type="checkbox" id="album" name="userBonus" value="free album" ${userEditAlbumBonus}>
            <label for="album"><c:out value="${userAlbumBonusLabel}"/></label>
        </div>
        <div>
            <input type="checkbox" id="assemblage" name="userBonus" value="free assemblage" ${userEditAssemblageBonus}>
            <label for="assemblage"><c:out value="${userAssemblageBonusLabel}"/></label>
        </div>
    </fieldset>
    <button type="submit" class="btn btn-primary" style="margin-top: 5px"><c:out value="${userSendEditLabel}"/></button>
</form>
</body>
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</html>
