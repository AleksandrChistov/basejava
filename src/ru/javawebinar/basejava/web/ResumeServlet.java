package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.utils.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
            case "create":
                r = new Resume();
                action = "edit";
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("/WEB-INF/jsp/" + action + ".jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName").trim();

        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }

        Resume r = uuid.isEmpty() ? new Resume(fullName) : new Resume(uuid, fullName);

        Arrays.stream(ContactType.values()).forEach(type -> {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.putContact(ContactType.valueOf(type.name()), value.trim());
            }
        });

        Arrays.stream(SectionType.values()).forEach(type -> {
            String value = request.getParameter(type.name());
            SectionType sectionType = SectionType.valueOf(type.name());
            if (value != null && !value.trim().isEmpty()) {
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> r.putSection(sectionType, new TextSection(value.trim()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> strings = Arrays.stream(value.trim().split("\n"))
                                .map(String::trim)
                                .filter(val -> !val.isBlank())
                                .toList();
                        r.putSection(sectionType, new ListSection(strings));
                    }
                }
            } else {
                switch (sectionType) {
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> orgs = new ArrayList<>();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                        int index = 0;
                        while (true) {
                            value = request.getParameter(type.name() + index);
                            if (value == null) {
                                break;
                            }
                            List<Organization.Period> periods = new ArrayList<>();
                            String webSite = request.getParameter(type.name() + index + "link").trim();
                            String[] startDates = request.getParameterValues(type.name() + index + "startDate");
                            for (int i = 0; i < startDates.length; i++) {
                                String[] endDates = request.getParameterValues(type.name() + index + "endDate");
                                String[] titles = request.getParameterValues(type.name() + index + "title");
                                String[] descriptions = request.getParameterValues(type.name() + index + "description");
                                LocalDate formattedStartDate = YearMonth.parse(startDates[i], formatter).atDay(1);
                                LocalDate formattedEndDate = endDates[i].equals("Сейчас") ?
                                        LocalDate.of(3000, 1, 1) :
                                        YearMonth.parse(endDates[i], formatter).atDay(1);
                                periods.add(new Organization.Period(
                                        formattedStartDate,
                                        formattedEndDate,
                                        StringUtil.getStrOrNull(titles[i]),
                                        StringUtil.getStrOrNull(descriptions[i])
                                        )
                                );
                            }
                            List<Organization.Period> sortedPeriods = periods.stream().sorted(Comparator.comparing(Organization.Period::getEndDate).reversed()).toList();
                            orgs.add(new Organization(value, StringUtil.getStrOrNull(webSite), sortedPeriods));
                            index++;
                        }
                        List<Organization> sortedOrgs = orgs.stream().sorted((o1, o2) -> o2.getPeriods().get(o2.getPeriods().size() - 1).getEndDate().compareTo(o1.getPeriods().get(o1.getPeriods().size() - 1).getEndDate())).toList();
                        r.putSection(sectionType, new OrganizationSection(sortedOrgs));
                    }
                }
            }
        });

        if (uuid.isEmpty()) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}
