<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="error" class="models.ErrorBean" scope="request"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="localeLang" class="java.lang.String" scope="session"/>

<fmt:setLocale value="${localeLang}"/>
<fmt:bundle basename="Messages">
<html>
<head>
    <title><fmt:message key="errors"></fmt:message></title>
</head>
<body>
<h2><fmt:message key="errors"></fmt:message> : </h2>
<p><jsp:getProperty name="error" property="errorText"/></p>

<form method="Get" action="${pageContext.request.contextPath}/">
    <input type="submit" name="goBack" value="<fmt:message key="go_back_btn_text">"</fmt:message>>
</form>
</body>
</html>
</fmt:bundle>
