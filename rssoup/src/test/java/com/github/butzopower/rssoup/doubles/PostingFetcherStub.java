package com.github.butzopower.rssoup.doubles;

import com.github.butzopower.rssoup.PostingFetcher;
import com.github.butzopower.rssoup.entities.Posting;

import java.util.List;

public class PostingFetcherStub implements PostingFetcher {
    private final List<Posting> postingsToReturn;

    public PostingFetcherStub(List<Posting> postingsToReturn) {
        this.postingsToReturn = postingsToReturn;
    }

    @Override
    public List<Posting> fetchPostings() {
        return this.postingsToReturn;
    }
}
