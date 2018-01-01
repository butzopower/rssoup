package com.github.butzopower.rssoup;

import com.github.butzopower.rssoup.usecases.FetchMenus;
import com.github.butzopower.rssoup.usecases.FetchMenusObserver;
import com.github.butzopower.rssoup.usecases.SyncMenus;
import com.github.butzopower.rssoup.usecases.SyncMenusObserver;

public abstract class RssSoup {
    public static void syncMenus(
            SyncMenusObserver syncMenusObserver,
            PostingFetcher postingFetcher,
            MenuStore menuStore
    ) {
        new SyncMenus(syncMenusObserver, postingFetcher, menuStore).execute();
    }

    public static void fetchMenus(
            FetchMenusObserver fetchMenusObserver,
            MenuStore menuStore
    ) {
        new FetchMenus(fetchMenusObserver, menuStore).execute();
    }
}
