package com.fairmontsinternational.charlie.fairmontsinternational.Classes;

public class NotificationClass {
    private String date;

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public NotificationClass(String date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
    }

    private String title;
    private String description;
}
