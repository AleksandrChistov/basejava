INSERT INTO resume (uuid, full_name)
VALUES ('7de882da-02f2-4d16-8daa-60660aaf4071', 'Name1'),
       ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'Name2'),
       ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'Name3');

INSERT INTO contact (resume_uuid, type, value)
VALUES ('7de882da-02f2-4d16-8daa-60660aaf4071', 'SKYPE', 'skype_name'),
       ('7de882da-02f2-4d16-8daa-60660aaf4071', 'EMAIL', 'name@mail.ru');

INSERT INTO section (resume_uuid, type, value)
VALUES ('7de882da-02f2-4d16-8daa-60660aaf4071', 'OBJECTIVE', 'Senior Frontend Developer'),
       ('7de882da-02f2-4d16-8daa-60660aaf4071', 'ACHIEVEMENT', 'Проведение собеседований на позицию "Middle Frontend разработчик" и успешный онбординг отобранных кандидатов в проект.\nЗапуск c нуля веб-приложения для страховой компании ВСК на стеке технологий: Angular, SSR, GraphQL.\nУспешное развитие и поддержка одновременно 3-х проектов, написанных на разных технологиях: Angular, Vue, React, Nodejs (BFF на GraphQL).\nОбучение коллег из Индии работе с JavaScript-фреймворком для тестирования - Cypress.');
