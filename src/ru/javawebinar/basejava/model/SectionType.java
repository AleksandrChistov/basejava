package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public enum SectionType {
    OBJECTIVE("Позиция") {
        @Override
        public String toHtml(Section section) {
            return HtmlUtil.toHtml(((TextSection) section).getContent(), getTitle());
        }
    },
    PERSONAL("Личные качества") {
        @Override
        public String toHtml(Section section) {
            return HtmlUtil.toHtml(((TextSection) section).getContent(), getTitle());
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml(Section section) {
            String content = ((ListSection) section).getItems()
                    .stream()
                    .map(item -> "<li>" + item + "</li>")
                    .collect(Collectors.joining());
            return HtmlUtil.toHtml("<ul>" + content + "</ul>", getTitle());
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml(Section section) {
            String content = ((ListSection) section).getItems()
                    .stream()
                    .map(item -> "<li>" + item + "</li>")
                    .collect(Collectors.joining());
            return HtmlUtil.toHtml("<ul>" + content + "</ul>", getTitle());
        }
    },
    EXPERIENCE("Опыт работы") {
        @Override
        public String toHtml(Section section) {
            String content = ((OrganizationSection) section).getOrganizations()
                    .stream()
                    .map(HtmlUtil::toOrganizationHtml)
                    .collect(Collectors.joining());
            return HtmlUtil.toHtml(content, getTitle());
        }
    },
    EDUCATION("Образование") {
        @Override
        public String toHtml(Section section) {
            String content = ((OrganizationSection) section).getOrganizations()
                    .stream()
                    .map(HtmlUtil::toOrganizationHtml)
                    .collect(Collectors.joining());
            return HtmlUtil.toHtml(content, getTitle());
        }
    };

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(Section section) {
        throw new UnsupportedOperationException(
                "toHtml method of section " + section + " of class" + this.getClass().getName() + " is not implemented"
        );
    }

    private static class HtmlUtil {
        public static String toHtml(String content, String title) {
            return (content == null) ? "" : "<div class='section'><h3>" + title + "</h3> " + content + "</div>";
        }

        public static String toOrganizationHtml(Organization org) {
            return "<div class='org-section'>" + getOrgTitleHtml(org.getName(), org.getWebsite()) +
                    getOrgPeriodsHtml(org.getPeriods()) + "</div>";
        }

        public static String getOrgTitleHtml(String title, String url) {
            return "<div class='flex align-start'>" +
                    "<span class='org-section__period'></span>" +
                    "<h3 class='org-section__content'>" + (url == null ? title : "<a href='" + url + "' target='_blank'>" + title + "</a>") + "</h3>" +
                    "</div>";
        }

        public static String getOrgPeriodsHtml(List<Organization.Period> periods) {
            return periods.stream().map(period ->
                    "<div class='flex align-start'>" +
                            "<span class='org-section__period'>" + getDatePeriodHtml(period.getStartDate(), period.getEndDate()) + "</span>" +
                            "<div class='org-section__content'><h4>" + period.getTitle() + "</h4>" + getDescriptionHtml(period.getDescription()) + "</div>" +
                            "</div>"
            ).collect(Collectors.joining());
        }

        public static String getDatePeriodHtml(LocalDate startDate, LocalDate endDate) {
            return getDateHtml(startDate) + " - " + getDateHtml(endDate);
        }

        public static String getDateHtml(LocalDate date) {
            return date.isAfter(LocalDate.now()) ? "Сейчас" : date.getMonthValue() + "/" + date.getYear();
        }

        public static String getDescriptionHtml(String desc) {
            return desc == null ? "" : "<p>" + desc + "</p>";
        }
    }
}
