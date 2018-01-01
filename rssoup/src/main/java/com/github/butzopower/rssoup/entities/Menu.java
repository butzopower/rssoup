package com.github.butzopower.rssoup.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Menu {
    private final String imageUrl;
    private final LocalDate date;

    public Menu(String imageUrl, LocalDate date) {
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        boolean equals =
                Objects.equals(date, menu.date)
                && Objects.equals(imageUrl, menu.imageUrl)
                ;
        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
