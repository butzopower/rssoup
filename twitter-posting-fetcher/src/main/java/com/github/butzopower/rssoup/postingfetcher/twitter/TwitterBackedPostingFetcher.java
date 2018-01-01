package com.github.butzopower.rssoup.postingfetcher.twitter;

import com.github.butzopower.rssoup.PostingFetcher;
import com.github.butzopower.rssoup.entities.Posting;
import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TwitterBackedPostingFetcher implements PostingFetcher {
    private final TwitterTemplate twitter;
    private final String twitterHandle;

    public TwitterBackedPostingFetcher(String apiKey, String apiSecret, String twitterHandle) {
        this.twitter = buildTwitterTemplate(apiKey, apiSecret);
        this.twitterHandle = twitterHandle;
    }

    @Override
    public List<Posting> fetchPostings() {
        List<Tweet> tweets = twitter.timelineOperations().getUserTimeline(twitterHandle);
        return tweets.stream()
                .map(this::mapTweetToPosting)
                .collect(toList());
    }

    private Posting mapTweetToPosting(Tweet tweet) {
        String postContent = tweet.getText();
        String imageUrl = extractUrlFromMedia(tweet.getEntities().getMedia());
        LocalDateTime createdAt = convertDateToLocalDateTime(tweet.getCreatedAt());

        return new Posting(postContent, imageUrl, createdAt);
    }

    private TwitterTemplate buildTwitterTemplate(String apiKey, String apiSecret) {
        return new TwitterTemplate(
                apiKey,
                apiSecret
        );
    }

    private LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    private String extractUrlFromMedia(List<MediaEntity> media) {
        return media.stream()
                .findFirst()
                .map(MediaEntity::getMediaUrl)
                .orElse(null);
    }
}
