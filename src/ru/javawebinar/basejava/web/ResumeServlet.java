package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {
    private Storage storage;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        if (fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }

        Resume r = new Resume(uuid, fullName);

        Arrays.stream(ContactType.values()).forEach(type -> {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.putContact(ContactType.valueOf(type.name()), value);
            }
        });

        Arrays.stream(SectionType.values()).forEach(type -> {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                SectionType sectionType = SectionType.valueOf(type.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> r.putSection(sectionType, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        String[] strings = value.split("\r\n");
                        r.putSection(sectionType, new ListSection(strings));
                    }
                }
            }
        });

        storage.update(r);
        response.sendRedirect("resume");
    }
}
