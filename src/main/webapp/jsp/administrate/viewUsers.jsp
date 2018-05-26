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

<fmt:message key="main.userNameLabel" var="nameLabel"/>
<fmt:message key="main.userEmailLabel" var="emailLabel"/>
<fmt:message key="main.userRoleLabel" var="roleLabel"/>
<fmt:message key="main.userEditLabel" var="editLabel"/>

<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>
<c:import url="/WEB-INF/jspf/languageSelect.jsp"/>
<ctl:user-info/>

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
                <c:out value="${emailLabel}"/>
            </h4>
        </th>
        <th>
            <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${roleLabel}"/>
            </h4>
        </th>
    </tr>
    <c:forEach items="${usersList}" var="user">
        <tr>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <button type="button" style = "margin-left: 10px; margin-bottom: 4px; margin-right: 20px;" class="btn btn-primary" onClick='location.href="/editUser?editUser=${user.getUserName()}"'>
                        <c:out value="${editLabel}"/>
                    </button>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${user.getUserName()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${user.getEmail()}"/>
                </h4>
            </td>
            <td>
                <h4 style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                    <c:out value="${user.getRole()}"/>
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

