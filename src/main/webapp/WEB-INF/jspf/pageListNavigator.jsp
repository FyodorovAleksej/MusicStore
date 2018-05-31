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

    <!-- Bootstrap core CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

</head>
<body>
<fmt:message key="page.nextPageLabel" var="nextPageLabel"/>
<fmt:message key="page.previousPageLabel" var="previousPageLabel"/>
<table>
    <tr>
        <td>
            <button type="button" style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"
                    class="btn btn-primary"
                    onClick='location.href="/tracks?page=${previousPage}"' ${previousHidden}>
                <c:out value="${previousPageLabel}"/>
            </button>
        </td>
        <td>
            <a style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;">
                <c:out value="${currentPage}"/>
            </a>
        </td>
        <td>
            <button type="button" style="margin-left: 10px; margin-bottom: 4px; margin-right: 20px;"
                    class="btn btn-primary"
                    onClick='location.href="/tracks?page=${nextPage}"' ${nextHidden}>
                <c:out value="${nextPageLabel}"/>
            </button>
        </td>
    </tr>
</table>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
