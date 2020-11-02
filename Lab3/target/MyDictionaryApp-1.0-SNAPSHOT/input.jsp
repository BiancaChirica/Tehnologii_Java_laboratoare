<%@ page import="models.Languages" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Bean for languages  --%>
<jsp:useBean id="languages" class="models.Languages" scope="request"/>

<fmt:setLocale value="${param.lang}"/>
<fmt:bundle basename="Messages">
    <html>
    <head>
        <title><fmt:message key="title"></fmt:message></title>

    </head>
    <body>
    <h2><fmt:message key="title"></fmt:message></h2>

    <p><fmt:message key="locale"></fmt:message></p>
    <ul>
        <li><a href="?lang=en">Eng</a></li>
        <li><a href="?lang=ro">Ro</a></li>
    </ul>

        <%-- The form to insert a word --%>
    <h4><fmt:message key="instruction"></fmt:message></h4>
    <form method="POST" action="FlowController">
        <label for="language"><fmt:message key="select_language_text"></fmt:message></label>
        <select name="language" id="language" onchange="saveLanguageFunction()">
            <option value=<%= languages.getDefaultLanguage() %> selected> <%= languages.getDefaultLanguage() %>
                        <% for (int i = 0; i < languages.getLanguages().size(); i++)
            if ( !languages.getLanguages().get(i).equals(languages.getDefaultLanguage()) ) {%>
            <option value=<%= languages.getLanguages().get(i)%>><%= languages.getLanguages().get(i) %>
            </option>
            <% } %>
        </select>
        <br/>
        <fmt:message key="insert_word_text"></fmt:message>
        <label>
            <input type="text" name="word" id="word" size="20" value=""/>
        </label> <br/>
        <fmt:message key="insert_definition_text"></fmt:message>
        <label>
            <input type="text" name="definition" size="20" value=""/>
        </label> <br/>

        <img src="http://localhost:8080/MyDictionaryApp_war_exploded/captcha" alt="Captcha text">
        <br/>
        <fmt:message key="captcha_input"></fmt:message>
        <label>
            <input type="text" name="captchaInput" id="captchaInput" size="20" value=""/>
        </label> <br/>
        <input type="submit" name="submit" value="<fmt:message key="submit_btn_text"></fmt:message>">
    </form>
    </body>
    </html>
</fmt:bundle>

<script>
    function saveLanguageFunction() {
        const langSelected = document.getElementById("language").value;

        const httpx = new XMLHttpRequest();
        httpx.open("GET", "http://localhost:8080/MyDictionaryApp_war_exploded/FlowController?lang=" + langSelected, true)
        httpx.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpx.send();
    }

</script>

