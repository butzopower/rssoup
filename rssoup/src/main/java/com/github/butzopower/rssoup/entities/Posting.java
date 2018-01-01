package com.github.butzopower.rssoup.entities;

import java.time.LocalDateTime;

public class Posting {
    private final String postContent;
    private final LocalDateTime postedAt;

    public Posting(String postContent, LocalDateTime postedAt) {
        this.postContent = postContent;
        this.postedAt = postedAt;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public String getPostContent() {
        return postContent;
    }
}
