<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
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
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="${ContactType.values()}">
            <jsp:useBean id="type" type="ru.javawebinar.basejava.model.ContactType"/>
            <dl class="flex">
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <input type="text" name="section" size="30" value="1">
        <input type="text" name="section" size="30" value="2">
        <input type="text" name="section" size="30" value="3">
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
