package ru.javawebinar.basejava.model;

import java.util.stream.Collectors;

public enum SectionType {
    OBJECTIVE("Позиция") {
        @Override
        public String toHtml(Section section) {
            return toHtml0(((TextSection) section).getContent());
        }
    },
    PERSONAL("Личные качества") {
        @Override
        public String toHtml(Section section) {
            return toHtml0(((TextSection) section).getContent());
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml(Section section) {
            String content = ((ListSection) section).getItems().stream().map(item -> "<br>- " + item).collect(Collectors.joining());
            return toHtml0(content);
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml(Section section) {
            String content = ((ListSection) section).getItems().stream().map(item -> "<br>- " + item).collect(Collectors.joining());
            return toHtml0(content);
        }
    },
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

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

    protected String toHtml0(String content) {
        return (content == null) ? "" : "<strong>" + title + "</strong>" + ": " + content;
    }
}
