package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.utils.StringUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            return HtmlUtil.toListHtml(((ListSection) section).getItems(), getTitle());
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml(Section section) {
            return HtmlUtil.toListHtml(((ListSection) section).getItems(), getTitle());
        }
    },
    EXPERIENCE("Опыт работы") {
        @Override
        public String toHtml(Section section) {
            return HtmlUtil.toOrgListHtml(((OrganizationSection) section).getOrganizations(), getTitle());
        }

        @Override
        public String toEditHtml(Organization org, int index) {
            return HtmlUtil.getOrgEditHtml(org, index, name());
        }
    },
    EDUCATION("Образование") {
        @Override
        public String toHtml(Section section) {
            return HtmlUtil.toOrgListHtml(((OrganizationSection) section).getOrganizations(), getTitle());
        }

        @Override
        public String toEditHtml(Organization org, int index) {
            return HtmlUtil.getOrgEditHtml(org, index, name());
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

    public String toEditHtml(Organization org, int index) {
        throw new UnsupportedOperationException(
                "toEditHtml method of section " + name() + " of class" + this.getClass().getName() + " is not implemented"
        );
    }

    private static class HtmlUtil {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        public static String toHtml(String content, String title) {
            return (content == null) ? "" : "<div class='section'><h3>" + title + "</h3> " + content + "</div>";
        }

        public static String toListHtml(List<String> list, String title) {
            String content = list
                    .stream()
                    .map(item -> "<li>" + item + "</li>")
                    .collect(Collectors.joining());
            return toHtml("<ul>" + content + "</ul>", title);
        }

        public static String toOrgListHtml(List<Organization> list, String title) {
            String content = list
                    .stream()
                    .map(HtmlUtil::toOrgHtml)
                    .collect(Collectors.joining());
            return toHtml(StringUtil.getStrOrNull(content), title);
        }

        public static String toOrgHtml(Organization org) {
            return "<div class='org-section'>" + getOrgTitleHtml(org.getName(), org.getWebsite() == null ? null : org.getWebsite()) +
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
            return startDate.format(formatter) + " - " + getDateHtml(endDate);
        }

        public static String getDateHtml(LocalDate date) {
            return date.isAfter(LocalDate.now()) ? "Сейчас" : date.format(formatter);
        }

        public static String getDescriptionHtml(String desc) {
            return desc == null ? "" : "<p>" + desc + "</p>";
        }

        public static String getOrgEditHtml(Organization org, int index, String sectionName) {
            return " <dd class='job-card' data-section='" + sectionName + index + "'>" +
                    "<input type='text' placeholder='Название' name='" + sectionName + index + "' size='30' value='" + (org.getName() == null ? "" : org.getName()) + "' required>" +
                    "<input type='text' placeholder='Ссылка' name='" + sectionName + index + "link'" + " size='30' value='" + (org.getWebsite() == null ? "" : org.getWebsite()) + "'>" +
                    org.getPeriods().stream().map(period -> "<div class='job'>" +
                            "<div class='flex'>" +
                            "<input type='text' placeholder='Начало, ММ/ГГГГ' name='" + sectionName + index + "startDate'" + " size='30' value='" + period.getStartDate().format(formatter) + "' required>" +
                            "<input type='text' placeholder='Окончание, ММ/ГГГГ' name='" + sectionName + index + "endDate'" + " size='30' value='" + getDateHtml(period.getEndDate()) + "' required>" +
                            "</div>" +
                            "<input type='text' placeholder='Заголовок' name='" + sectionName + index + "title'" + " size='30' value='" + period.getTitle() + "' required>" +
                            "<textarea placeholder='Описание' name='" + sectionName + index + "description'" + " rows='3' cols='56'>" + (period.getDescription() == null ? "" : period.getDescription()) + "</textarea>" +
                            "<button id='delete-job-btn' onclick='deleteJobFromHtml(event)' type='button'>" +
                            "Удалить Позицию<img class='icon' src='assets/icons/trash.png' alt='Delete job section'>" +
                            "</div>" +
                            "</div>"
                    ).collect(Collectors.joining()) +
                    "<div class='org-buttons'>" +
                    "<button id='add-job-btn' onclick='addJobToHtml(event)' type='button'>" +
                    "Добавить позицию<img class='icon' src='assets/icons/add.png' alt='Add job section'>" +
                    "</button>" +
                    "<button id='delete-org-btn' onclick='deleteOrganizationFromHtml(event)' type='button'>" +
                    "Удалить организацию<img class='icon' src='assets/icons/trash.png' alt='Delete organization section'>" +
                    "</button>" +
                    "</dd>" +
                    "</div>"
                    ;
        }
    }
}
