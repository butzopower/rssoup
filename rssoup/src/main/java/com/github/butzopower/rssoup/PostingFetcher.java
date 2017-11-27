package com.github.butzopower.rssoup;

import com.github.butzopower.rssoup.entities.Posting;

import java.util.List;

public interface PostingFetcher {
    List<Posting> fetchPostings();
}
