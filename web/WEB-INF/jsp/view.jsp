<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName} <a href="?uuid=${resume.uuid}&action=edit">
        <img class="icon" src="assets/icons/edit.png" alt="Edit resume">
    </a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            ${contactEntry.key.toHtml(contactEntry.value)}<br/>
        </c:forEach>
    </p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
            <c:choose>
                <c:when test="${sectionEntry.key == SectionType.OBJECTIVE || sectionEntry.key == SectionType.PERSONAL}">
                    ${sectionEntry.key.toHtml(sectionEntry.value)}<br/>
                </c:when>
                <c:when test="${sectionEntry.key == SectionType.ACHIEVEMENT || sectionEntry.key == SectionType.QUALIFICATIONS}">
                    ${sectionEntry.key.toHtml(sectionEntry.value)}<br/>
                </c:when>
            </c:choose>
            <br/>
        </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
