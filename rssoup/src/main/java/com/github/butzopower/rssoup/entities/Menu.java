package com.github.butzopower.rssoup.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Menu {
    private final LocalDate date;

    public Menu(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(date, menu.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
