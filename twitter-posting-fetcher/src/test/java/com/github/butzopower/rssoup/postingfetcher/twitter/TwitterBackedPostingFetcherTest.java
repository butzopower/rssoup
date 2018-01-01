package com.github.butzopower.rssoup.postingfetcher.twitter;

import com.github.butzopower.rssoup.entities.Posting;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.butzopower.rssoup.postingfetcher.twitter.TestSetup.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assume.assumeThat;

public class TwitterBackedPostingFetcherTest {
    @Before
    public void setUp() throws Exception {
        assumeThat("test API key is set", API_KEY, not(isEmptyString()));
        assumeThat("test API secret is set", API_SECRET, not(isEmptyString()));
        assumeThat("test Twitter Handle is set", TWITTER_HANDLE, not(isEmptyString()));
    }

    @Test
    public void itFetchesAllPostsFromASpecificFeed() throws Exception {
        TwitterBackedPostingFetcher fetcher = new TwitterBackedPostingFetcher(
                API_KEY,
                API_SECRET,
                TWITTER_HANDLE
        );
        List<Posting> postings = fetcher.fetchPostings();
        assertThat(postings, hasSize(greaterThan(0)));
    }
}