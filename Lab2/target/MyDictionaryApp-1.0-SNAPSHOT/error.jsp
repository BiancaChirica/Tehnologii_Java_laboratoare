<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="error" class="models.ErrorBean" scope="request"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Errors : </h2>
<p><jsp:getProperty name="error" property="errorText"/></p>

<form method="Get" action="${pageContext.request.contextPath}/">
    <input type="submit" name="goBack" value="Go Back">
</form>
</body>
</html>
