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

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<fmt:message key="main.trackFind" var="trackFindButton"/>
<fmt:message key="main.albumFind" var="albumFindButton"/>
<fmt:message key="main.assemblageFind" var="assemblageFindButton"/>

<div class="navbar-header">
    <table>
        <tr>
            <th>
                <form action="/tracks" method="get">
                    <button type="submit" class="btn btn-primary"><c:out value="${trackFindButton}"/></button>
                </form>
            </th>
            <th>
                <form action="/albums" method="get">
                    <button type="submit" class="btn btn-primary"><c:out value="${albumFindButton}"/></button>
                </form>
            </th>
            <th>
                <form action="/assemblages" method="get">
                    <button type="submit" class="btn btn-primary"><c:out value="${assemblageFindButton}"/></button>
                </form>
            </th>
        </tr>
    </table>
</div>
<!-- Bootstrap -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
