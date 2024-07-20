package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.utils.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        final Resume resume = new Resume("Resume1");
        fillContacts(resume, 7);
        fillSections(resume);
        System.out.println("contacts > " + resume.getContacts());
        System.out.println("sections > " + resume.getSections());
    }

    public static Resume createResume(String uuid, String fullName, int contactsCount) {
        final Resume resume = new Resume(uuid, fullName);
        fillContacts(resume, contactsCount);
        fillSections(resume);
        return resume;
    }

    private static void fillContacts(Resume resume, int count) {
        ContactType[] types = new ContactType[]{ContactType.PHONE,ContactType.SKYPE,ContactType.EMAIL,ContactType.LINKEDIN,ContactType.GITHUB,ContactType.STACKOVERFLOW,ContactType.HOME_PAGE};
        String[] values = new String[]{"+7(999) 000-1100","aleksandr_chistov","aleksandrchistov.ru@yandex.ru","https://www.linkedin.com/in/","https://github.com/AleksandrChistov","https://stackoverflow.com/","https://yandex.ru/"};
        for (int i = 0; i < count; i++) {
            resume.putContact(types[i],values[i]);
        }
    }

    private static void fillSections(Resume resume) {
        final TextSection objectiveSection = new TextSection("Senior Frontend Developer");
        final TextSection personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность.");
        resume.putSection(SectionType.OBJECTIVE, objectiveSection);
        resume.putSection(SectionType.PERSONAL, personalSection);
        fillAchievementsSection(resume);
        fillQualificationsSection(resume);
        fillExperienceSection(resume);
        fillEducationSection(resume);
    }

    private static void fillAchievementsSection(Resume resume) {
        final List<String> achievements = new ArrayList<>();
        achievements.add("Проведение собеседований на позицию \"Middle Frontend разработчик\" и успешный онбординг отобранных кандидатов в проект.");
        achievements.add("Запуск c нуля веб-приложения для страховой компании ВСК на стеке технологий: Angular, SSR, GraphQL.");
        achievements.add("Успешное развитие и поддержка одновременно 3-х проектов, написанных на разных технологиях: Angular, Vue, React, Nodejs (BFF на GraphQL).");
        achievements.add("Обучение коллег из Индии работе с JavaScript-фреймворком для тестирования - Cypress.");
        final ListSection achievementsSection = new ListSection(achievements);
        resume.putSection(SectionType.ACHIEVEMENT, achievementsSection);
    }

    private static void fillQualificationsSection(Resume resume) {
        final List<String> qualifications = new ArrayList<>();
        qualifications.add("Languages: Java, JavaScript, Python");
        qualifications.add("DB: PostgreSQL MySQL, SQLite, MongoDB");
        qualifications.add("Version control: Git");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate), JUnit.");
        qualifications.add("JavaScript: Angular, React, Vue, jQuery, Bootstrap.js");
        qualifications.add("Технологии: Servlet, JSP/JSTL, REST, JDBC, JPA, SOAP, AJAX, HTML5, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, настройка Ngnix, Docker");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        final ListSection qualificationsSection = new ListSection(qualifications);
        resume.putSection(SectionType.QUALIFICATIONS, qualificationsSection);
    }


    private static void fillExperienceSection(Resume resume) {
        final List<Organization.Period> periodsForOrganization1 = List.of(new Organization.Period(DateUtil.of(2022, Month.SEPTEMBER), DateUtil.NOW, "Ведущий эксперт.", "Работа в команде по SCRUM методологии в Jira, Bitbucket, Confluence, Gitlab. Разработка новой функциональности в 3-х веб-приложениях. Фикс багов. Анализ и техническое описание техдолг-задач и багов со стороны фронтенд-разработки. Проведение собеседований, онбординг новичков."));
        final Organization organization1 = new Organization("ПАО Росбанк", "https://www.rosbank.ru/", periodsForOrganization1);
        final List<Organization.Period> periodsForOrganization2 = List.of(new Organization.Period(DateUtil.of(2020, Month.FEBRUARY), DateUtil.of(2022, Month.JUNE), "Middle Frontend Developer", "Занимался разработкой новой функциональности приложений в сфере страхования и перестрахования (заказчик - крупная швейцарская компания). Покрытием кода тестами (unit, интеграционные). Фикс багов. Оценка задач, планирование, декомпозиция. Обновление библиотек. Работа велась в Jira и Azure DevOps."));
        final Organization organization2 = new Organization("Syncretis", "https://syncretis.com/ru", periodsForOrganization2);
        final List<Organization.Period> periodsForOrganization3 = List.of(new Organization.Period(DateUtil.of(2016, Month.MARCH), DateUtil.of(2019, Month.MARCH), "HTML-верстальщик – маркетолог", "Установка и настройка сайтов на Wordpress. Создание landing page с нуля (HTML и CSS). Правка кода сайта (HTML и CSS). Создание одностраничных сайтов (landing page) на платформе lpgenerator.ru. Постоянное изучение новых сервисов и технологий. Маркетинг, SEO."));
        final Organization organization3 = new Organization("Проектная занятость в области веб-разработки и верстки", null, periodsForOrganization3);

        final List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        organizations.add(organization2);
        organizations.add(organization3);

        final OrganizationSection experienceSection = new OrganizationSection(organizations);
        resume.putSection(SectionType.EXPERIENCE, experienceSection);
    }

    private static void fillEducationSection(Resume resume) {
        final List<Organization.Period> periodsForOrganization1 = List.of(new Organization.Period(DateUtil.of(2024, Month.MAY), DateUtil.of(2024, Month.JULY), "Курс BaseJava", "Разработка web-приложения \"База данных резюме\""));
        final Organization organization1 = new Organization("javaops.ru", "https://javaops.ru/view/basejava", periodsForOrganization1);
        final List<Organization.Period> periodsForOrganization2 = List.of(new Organization.Period(DateUtil.of(2021, Month.SEPTEMBER), DateUtil.of(2025, Month.SEPTEMBER), "09.03.03 Прикладная информатика", "Институт математики, физики и информационных технологий"));
        final Organization organization2 = new Organization("Тольяттинский государственный университет", "https://www.tltsu.ru/", periodsForOrganization2);
        final List<Organization.Period> periodsForOrganization3 = List.of(new Organization.Period(DateUtil.of(2023, Month.MAY), DateUtil.of(2023, Month.NOVEMBER), "Веб-безопасность", "Специалист по веб-безопасности"));
        final Organization organization3 = new Organization("Hacktory", "https://hacktory.ai/courses/web-security", periodsForOrganization3);
        final Organization.Period period2 = new Organization.Period(DateUtil.of(2022, Month.SEPTEMBER), DateUtil.of(2022, Month.OCTOBER), "Мидл Карьерный трек", null);
        final Organization.Period period1 = new Organization.Period(DateUtil.of(2021, Month.SEPTEMBER), DateUtil.of(2022, Month.SEPTEMBER), "Мидл фронтенд-разработчик", null);
        final List<Organization.Period> periodsForOrganization4 = List.of(period1, period2);
        final Organization organization4 = new Organization("Яндекс.Практикум", "https://practicum.yandex.ru", periodsForOrganization4);

        final List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        organizations.add(organization2);
        organizations.add(organization3);
        organizations.add(organization4);

        final OrganizationSection educationSection = new OrganizationSection(organizations);
        resume.putSection(SectionType.EDUCATION, educationSection);
    }
}
