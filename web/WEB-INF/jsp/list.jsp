<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.basejava.model.Resume" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <%
                for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
            %>
            <tr>
                <td>
                    <a href="?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%></a>
                </td>
                <td><%=resume.getContact(ContactType.EMAIL)%></td>
            </tr>
            <%
                }
            %>
        </table>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>
