<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список всех резюме</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="fragments/header.jsp"/>
    <section>
        <table>
            <tr>
                <th>Имя</th>
                <th>Email</th>
            </tr>
            <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
                <tr>
                    <td>
                        <a href="?uuid=${resume.uuid}">${resume.fullName}</a>
                    </td>
                    <td>${resume.getContact(ContactType.EMAIL)}</td>
                </tr>
            </c:forEach>
        </table>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>
