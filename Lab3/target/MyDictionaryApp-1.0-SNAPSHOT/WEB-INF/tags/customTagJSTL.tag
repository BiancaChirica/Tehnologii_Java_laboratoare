<%@ attribute name="wordList" required="true" type="models.WordBeanList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="localeLang" class="java.lang.String" scope="session"/>

<fmt:setLocale value="${localeLang}" />
<fmt:bundle basename="Messages">
<table class="center">
    <tr class="tableRow">
        <th><p> <fmt:message key="word"></fmt:message></p></th>
        <th><p> <fmt:message key="definition"></fmt:message></p></th>
        <th><p> <fmt:message key="date"></fmt:message></p></th>
    </tr>
    <c:forEach var="word" items="${wordList.wordList}">
    <tr>
        <td><p><c:out value="${word.word}"/> </p></td>
        <td><p><c:out value="${word.definition}"/></p></td>
        <td><p><fmt:formatDate value="${word.getDate()}" type="both"
                               dateStyle="full" timeStyle="full" /></p></td>
    </tr>
    </c:forEach>
</table>
</fmt:bundle>