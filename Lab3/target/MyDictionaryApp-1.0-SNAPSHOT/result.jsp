<%@ page import="models.WordBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="wordList" class="models.WordBeanList" scope="request"/>
<jsp:useBean id="localeLang" class="java.lang.String" scope="session"/>
<%@ taglib prefix ="definition" uri="WEB-INF/customDefTag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${localeLang}"/>
<fmt:bundle basename="Messages">
<html>
<head>
    <title>  <fmt:message key="result_page_title"></fmt:message></title>
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

<p> <fmt:message key="language_selected"></fmt:message> : <%= wordList.getLanguage()%>
</p>

<%--definition custom tag--%>
<table class="center">
    <tr class="tableRow">
        <th><p> <fmt:message key="word"></fmt:message></p></th>
        <th><p> <fmt:message key="definition"></fmt:message></p></th>
    </tr>
    <% for (WordBean wb : wordList.getWordList()) {%>
    <tr>
        <td><p><%=wb.getWord()%></p>
        </td>
        <td><p> <definition: word="<%=wb.getWord()%>" language="<%=wb.getLanguage()%>"/></p>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>
</fmt:bundle>
