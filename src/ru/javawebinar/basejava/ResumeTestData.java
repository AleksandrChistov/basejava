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
        String[] values = new String[]{"+7(921) 855-0482","grigory.kislin","gkislin@yandex.ru","https://www.linkedin.com/in/gkislin","https://github.com/gkislin","https://stackoverflow.com/users/548473","http://gkislin.ru/"};
        for (int i = 0; i < count; i++) {
            resume.putContact(types[i],values[i]);
        }
    }

    private static void fillSections(Resume resume) {
        final TextSection objectiveSection = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        final TextSection personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.putSection(SectionType.OBJECTIVE, objectiveSection);
        resume.putSection(SectionType.PERSONAL, personalSection);
        fillAchievementsSection(resume);
        fillQualificationsSection(resume);
        fillExperienceSection(resume);
        fillEducationSection(resume);
    }

    private static void fillAchievementsSection(Resume resume) {
        final List<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        final ListSection achievementsSection = new ListSection(achievements);
        resume.putSection(SectionType.ACHIEVEMENT, achievementsSection);
    }

    private static void fillQualificationsSection(Resume resume) {
        final List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        final ListSection qualificationsSection = new ListSection(qualifications);
        resume.putSection(SectionType.QUALIFICATIONS, qualificationsSection);
    }


    private static void fillExperienceSection(Resume resume) {
        final List<Organization.Period> periodsForOrganization1 = List.of(new Organization.Period(DateUtil.of(2013, Month.OCTOBER), DateUtil.NOW, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        final Organization organization1 = new Organization("Java Online Projects", "http://javaops.ru/", periodsForOrganization1);
        final List<Organization.Period> periodsForOrganization2 = List.of(new Organization.Period(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        final Organization organization2 = new Organization("Wrike", "https://www.wrike.com/", periodsForOrganization2);
        final List<Organization.Period> periodsForOrganization3 = List.of(new Organization.Period(DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        final Organization organization3 = new Organization("RIT Center", null, periodsForOrganization3);
        final List<Organization.Period> periodsForOrganization4 = List.of(new Organization.Period(DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        final Organization organization4 = new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", periodsForOrganization4);
        final List<Organization.Period> periodsForOrganization5 = List.of(new Organization.Period(DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER), "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        final Organization organization5 = new Organization("Yota", "https://www.yota.ru/", periodsForOrganization5);

        final List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        organizations.add(organization2);
        organizations.add(organization3);
        organizations.add(organization4);
        organizations.add(organization5);

        final OrganizationSection experienceSection = new OrganizationSection(organizations);
        resume.putSection(SectionType.EXPERIENCE, experienceSection);
    }

    private static void fillEducationSection(Resume resume) {
        final List<Organization.Period> periodsForOrganization1 = List.of(new Organization.Period(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), "'Functional Programming Principles in Scala' by Martin Odersky", null));
        final Organization organization1 = new Organization("Coursera", "https://www.coursera.org/course/progfun", periodsForOrganization1);
        final List<Organization.Period> periodsForOrganization2 = List.of(new Organization.Period(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL), "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null));
        final Organization organization2 = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", periodsForOrganization2);
        final Organization.Period period1 = new Organization.Period(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "Аспирантура (программист С, С++)", null);
        final Organization.Period period2 = new Organization.Period(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "Инженер (программист Fortran, C)", null);
        final List<Organization.Period> periodsForOrganization3 = List.of(period1, period2);
        final Organization organization3 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", periodsForOrganization3);

        final List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        organizations.add(organization2);
        organizations.add(organization3);

        final OrganizationSection educationSection = new OrganizationSection(organizations);
        resume.putSection(SectionType.EDUCATION, educationSection);
    }
}
