<%@ page import="models.WordBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="wordList" class="models.WordBeanList" scope="request"/>
<html>
<head>
    <title>Result</title>
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
<h2>Result page</h2>

<form method="Get" action="${pageContext.request.contextPath}/">
    <input type="submit" name="goBack" value="Go Back">
</form>

<p>Language selected : <%= wordList.getLanguage()%>
</p>

<table class="center">
    <tr>
        <th>Word</th>
        <th>Definition</th>
    </tr>
    <% for (WordBean wb : wordList.getWordList()) {%>
    <tr>
        <td><%=wb.getWord()%>
        </td>
        <td><%=wb.getDefinition()%>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>
