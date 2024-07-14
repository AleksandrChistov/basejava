<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
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
    <form class="form" action="resume" method="post" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl class="flex">
            <dt>Имя</dt>
            <dd><input required type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты</h3>
        <c:forEach var="contactType" items="${ContactType.values()}">
            <jsp:useBean id="contactType" type="ru.javawebinar.basejava.model.ContactType"/>
            <dl class="flex">
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size="30" value="${resume.getContact(contactType)}">
                </dd>
            </dl>
        </c:forEach>
        <h3>Секции</h3>
        <c:forEach var="sectionType" items="${SectionType.values()}">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
            <c:choose>
                <c:when test="${sectionType == SectionType.OBJECTIVE || sectionType == SectionType.PERSONAL}">
                    <dl class="flex">
                        <dt>${sectionType.title}</dt>
                        <dd>
                                <textarea rows="3" cols="40"
                                          name="${sectionType.name()}">${(resume.getSection(sectionType)).getContent()}</textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS}">
                    <dl class="flex">
                        <dt>${sectionType.title}</dt>
                        <dd>
                            <textarea
                                    rows="10"
                                    cols="40"
                                    name="${sectionType.name()}"><%--
                                --%><c:forEach var="item" items="${(resume.getSection(sectionType)).getItems()}"><%--
                                    --%><jsp:useBean id="item" type="java.lang.String"/><%--
                                        --%>${item}&#13;&#10;<%--
                                    --%></c:forEach><%--
                        --%></textarea>
                        </dd>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <div class="flex btn-wrapper">
            <button type="submit">
                Сохранить
                <img class="icon" src="assets/icons/save.png" alt="Save resume">
            </button>
            <button type="button" onclick="window.history.back()">
                Отменить
                <img class="icon" src="assets/icons/cancel.png" alt="Cancel saving resume">
            </button>
        </div>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
