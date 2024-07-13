package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел.") {
        @Override
        public String toHtml0(String value) {
            return toLink("tel:" + value, value);
        }
    },
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return toLink("skype:" + value, value);
        }
    },
    EMAIL("Email") {
        @Override
        public String toHtml0(String value) {
            return toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("Linkedin") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Github") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("StackOverflow") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    protected String toLink(String href) {
        return toLink(href, title);
    }

    protected String toLink(String href, String value) {
        return "<a href='" + href + "' target='_blank'>" + value + "</a>";
    }
}
