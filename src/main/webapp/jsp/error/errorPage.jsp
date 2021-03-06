<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctl" uri="customTags" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<h4><c:out value="Something is wrong"></c:out></h4>
<img src="${request.contextPath}/img/404.jpg">
<button type="button" class="btn btn-primary" onClick='location.href="${request.contextPath}/"'><c:out value="Home"/></button>
<p>
    : ${pageContext.errorData.throwable.cause}
</body>
</html>
