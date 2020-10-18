<%@ page import="models.Languages" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Bean for languages  --%>
<jsp:useBean id="languages" class="models.Languages" scope="request"/>
<html>
<head>
    <title>Get Word</title>

</head>
<body>
<h2>My Dictionary</h2>
<h4> Please enter a new word : </h4>
<form method="POST" action="FlowController">
    <label for="language">Select a language :</label>
    <select name="language" id="language" onchange="saveLanguageFunction()">
        <option value=<%= languages.getDefaultLanguage() %> selected> <%= languages.getDefaultLanguage() %>
                <% for (int i = 0; i < languages.getLanguages().size(); i++)
    if ( !languages.getLanguages().get(i).equals(languages.getDefaultLanguage()) ) {%>
        <option value=<%= languages.getLanguages().get(i)%>><%= languages.getLanguages().get(i) %>
        </option>
        <% } %>
    </select>
    <br/>
    Insert word :
    <label>
        <input type="text" name="word" id="word" size="20" value=""/>
    </label> <br/>
    Insert definition :
    <label>
        <input type="text" name="definition" size="20" value=""/>
    </label> <br/>
    <input type="submit" name="submit" value="Submit">
</form>
</body>
</html>


<script>
    function saveLanguageFunction() {
        const langSelected = document.getElementById("language").value;

        const httpx = new XMLHttpRequest();
        httpx.open("GET", "http://localhost:8080/MyDictionaryApp_war_exploded/FlowController?lang=" + langSelected, true)
        httpx.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpx.send();
    }
</script>

