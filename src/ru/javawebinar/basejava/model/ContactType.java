package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Позиция"),
    SKYPE("Личные качества"),
    EMAIL("Достижения"),
    LINKEDIN("Квалификация"),
    GITHUB("Опыт работы"),
    STACKOVERFLOW("Образование"),
    HOME_PAGE("Образование");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
