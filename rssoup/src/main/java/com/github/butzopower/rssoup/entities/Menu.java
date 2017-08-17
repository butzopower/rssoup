package com.github.butzopower.rssoup.entities;

import java.time.LocalDate;

public class Menu {
    private final LocalDate date;

    public Menu(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
