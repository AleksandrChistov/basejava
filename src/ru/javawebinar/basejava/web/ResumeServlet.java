package ru.javawebinar.basejava.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Storage sqlStorage = Config.get().getStorage();
        String uuid = request.getParameter("uuid");
        String table = """
                <!doctype html>
                <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport"
                          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
                    <meta http-equiv="X-UA-Compatible" content="ie=edge">
                    <link rel="stylesheet" href="style.css">
                    <title>Main page</title>
                    <style>
                        table, th, td {
                           border: 1px solid black;
                           border-collapse: collapse;
                        }
                    </style>
                </head>
                <body>
                  <table>
                    <tr>
                      <th>Uuid</th>
                      <th>Full name</th>
                    </tr>
                """;
        if (uuid != null) {
            table += getResumeRow(sqlStorage.get(uuid));
        } else {
            List<Resume> resumes = sqlStorage.getAllSorted();
            table += resumes.stream().map(this::getResumeRow).collect(Collectors.joining());
        }
        table += """
                  </table>
                </body>
                </html>""";
        response.getWriter().write(table);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private String getResumeRow(Resume resume) {
        return "   <tr>" +
                "      <td>" + resume.getUuid() + "</td>" +
                "      <td>" + resume.getFullName() + "</td>" +
                "   </tr>";
    }
}
