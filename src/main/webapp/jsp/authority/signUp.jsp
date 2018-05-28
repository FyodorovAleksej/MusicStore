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
    <title>Sign Up</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<br/>
<c:import url="/WEB-INF/jspf/mainHeader.jsp"/>
<fmt:message key="main.signUpLabel" var="signUpLabel"/>
<fmt:message key="main.loginLabel" var="loginLabel"/>
<fmt:message key="main.emailLabel" var="emailLabel"/>
<fmt:message key="main.passwordLabel" var="passwordLabel"/>
<fmt:message key="main.repeatPasswordLabel" var="repeatPasswordLabel"/>
<fmt:message key="main.signUpSend" var="signUpSend"/>

<br/>
<br/>
<br/>

<form action="/signUp" method="post" class="text-left">
    <table align="center">
        <tr>
            <td>
                <label for="loginRegister"><c:out value="${loginLabel}"/></label>
            </td>
            <td>
                <input type="text" class="text-left" id="loginRegister" style="margin-left: 10px; margin-bottom: 2px;" name="login">
            </td>
        </tr>
        <tr>
            <td>
                <label for="emailRegister"><c:out value="${emailLabel}"/></label>
            </td>
            <td>
                <input type="email" class="text-left" id="emailRegister" name="email" style="margin-left: 10px; margin-bottom: 2px;">
            </td>
        </tr>
        <tr>
            <td>
                <label for="passwordRegister"><c:out value="${passwordLabel}"/></label>
            </td>
            <td>
                <input type="password" class="text-left" id="passwordRegister" name="password" style="margin-left: 10px; margin-bottom: 2px;">
            </td>
        </tr>
        <tr>
            <td>
                <label for="repPasswordRegister"><c:out value="${repeatPasswordLabel}"/></label>
            </td>
            <td>
                <input type="password" class="text-left" id="repPasswordRegister" name="repeatPassword" style="margin-left: 10px; margin-bottom: 2px;">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button type="submit" class="btn btn-primary" style="margin-top: 5px"><c:out value="${signUpSend}"/></button>
            </td>
        </tr>
    </table>
</form>
<br/>
<h4 class="text-center" align="center"><c:out value="${signUpResult}"/></h4>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
