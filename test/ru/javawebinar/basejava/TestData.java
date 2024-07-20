package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "Resume 1", 3);
    public static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, "Resume 2", 2);
    public static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, "Resume 3", 0);
    public static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "Resume 4", 7);
}
