package com.github.butzopower.rssoup.postingfetcher.twitter;

import com.github.butzopower.rssoup.PostingFetcher;
import com.github.butzopower.rssoup.entities.Posting;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        return tweets.stream().map(this::mapTweetToPosting).collect(toList());
    }

    private Posting mapTweetToPosting(Tweet tweet) {
        LocalDateTime createdAt = tweet.getCreatedAt().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        return new Posting(createdAt);
    }

    private TwitterTemplate buildTwitterTemplate(String apiKey, String apiSecret) {
        return new TwitterTemplate(
                apiKey,
                apiSecret
        );
    }
}
