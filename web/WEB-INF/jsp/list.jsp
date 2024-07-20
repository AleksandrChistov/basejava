<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список всех резюме</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="fragments/header.jsp"/>
    <section>
        <table>
            <tr>
                <th>Имя</th>
                <th>Email</th>
                <th colspan="2">
                    <a class="create_btn" href="?action=create">
                        Создать резюме
                        <img class="icon" src="assets/icons/add.png" alt="Create resume">
                    </a>
                </th>
            </tr>
            <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
            <c:forEach var="resume" items="${resumes}">
                <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
                <tr>
                    <td><a href="?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td>${ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))}</td>
                    <td><a href="?uuid=${resume.uuid}&action=delete">
                        <img class="icon" src="assets/icons/trash.png" alt="Delete resume">
                    </a></td>
                    <td><a href="?uuid=${resume.uuid}&action=edit">
                        <img class="icon" src="assets/icons/edit.png" alt="Edit resume">
                    </a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>
