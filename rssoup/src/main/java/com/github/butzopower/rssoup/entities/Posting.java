package com.github.butzopower.rssoup.entities;

import java.time.LocalDateTime;

public class Posting {
    private final String postContent;
    private final String imageUrl;
    private final LocalDateTime postedAt;

    public Posting(String postContent, String imageUrl, LocalDateTime postedAt) {
        this.postContent = postContent;
        this.imageUrl = imageUrl;
        this.postedAt = postedAt;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
