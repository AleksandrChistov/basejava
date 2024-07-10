INSERT INTO resume (uuid, full_name)
VALUES ('7de882da-02f2-4d16-8daa-60660aaf4071', 'Name1'),
       ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'Name2'),
       ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'Name3');

INSERT INTO contact (resume_uuid, type, value)
VALUES ('7de882da-02f2-4d16-8daa-60660aaf4071', 'SKYPE', 'skype_name'),
       ('7de882da-02f2-4d16-8daa-60660aaf4071', 'EMAIL', 'name@mail.ru');

INSERT INTO section (resume_uuid, type, value)
VALUES ('7de882da-02f2-4d16-8daa-60660aaf4071', 'OBJECTIVE', 'Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям'),
       ('7de882da-02f2-4d16-8daa-60660aaf4071', 'ACHIEVEMENT', 'Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет\nС 2013 года: разработка проектов "Разработка Web приложения","Java Enterprise", "Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.');
