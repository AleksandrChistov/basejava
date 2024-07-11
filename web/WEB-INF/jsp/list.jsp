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
</body>
</html>
