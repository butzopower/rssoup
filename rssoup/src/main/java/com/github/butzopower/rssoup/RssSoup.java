package com.github.butzopower.rssoup;

import com.github.butzopower.rssoup.entities.Posting;
import com.github.butzopower.rssoup.usecases.FetchMenus;
import com.github.butzopower.rssoup.usecases.FetchMenusObserver;
import com.github.butzopower.rssoup.usecases.SyncMenus;
import com.github.butzopower.rssoup.usecases.SyncMenusObserver;

import java.util.function.Function;

public abstract class RssSoup {
    public static void syncMenus(
            SyncMenusObserver syncMenusObserver,
            PostingFetcher postingFetcher,
            MenuStore menuStore
    ) {
        syncMenus(syncMenusObserver, postingFetcher, menuStore, (posting) -> true);
    }

    public static void syncMenus(
            SyncMenusObserver syncMenusObserver,
            PostingFetcher postingFetcher,
            MenuStore menuStore,
            Function<Posting, Boolean> filter
    ) {
        new SyncMenus(syncMenusObserver, postingFetcher, menuStore, filter).execute();
    }

    public static void fetchMenus(
            FetchMenusObserver fetchMenusObserver,
            MenuStore menuStore
    ) {
        new FetchMenus(fetchMenusObserver, menuStore).execute();
    }
}
