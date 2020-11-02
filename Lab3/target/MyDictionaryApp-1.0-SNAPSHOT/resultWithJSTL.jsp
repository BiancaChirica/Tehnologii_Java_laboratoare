<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="viewDictionary" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="wordList" class="models.WordBeanList" scope="request"/>
<jsp:useBean id="localeLang" class="java.lang.String" scope="session"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${localeLang}"/>
<fmt:bundle basename="Messages">
<html>
<head>
    <title> <fmt:message key="result_page_title"></fmt:message></title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 10px;
        }

        table.center {
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<body>
<h2> <fmt:message key="result_page_title"></fmt:message></h2>

<form method="Get" action="${pageContext.request.contextPath}/">
    <input type="submit" name="goBack" value="<fmt:message key="go_back_btn_text"></fmt:message>">
</form>

<p> <fmt:message key="language_selected"></fmt:message> : "${wordList.getLanguage()}"
</p>

<viewDictionary:customTagJSTL wordList="${wordList}" />


</body>
</html>
</fmt:bundle>