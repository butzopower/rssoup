package com.github.butzopower.rssoup.entities;

import java.time.LocalDateTime;

public class Posting {
    private final LocalDateTime postedAt;

    public Posting(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }
}
