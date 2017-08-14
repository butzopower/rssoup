package com.github.butzopower.rssoup.usecases;

import com.github.butzopower.rssoup.MenuFetcher;
import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.entities.Menu;

import java.util.List;

public class SyncMenus {
    private final SyncMenusObserver observer;
    private final MenuFetcher menuFetcher;
    private final MenuStore menuStore;

    public SyncMenus(SyncMenusObserver observer, MenuFetcher menuFetcher, MenuStore menuStore) {
        this.observer = observer;
        this.menuFetcher = menuFetcher;
        this.menuStore = menuStore;
    }

    public void execute() {
        List<Menu> fetchedMenus = this.menuFetcher.fetchMenus();
        fetchedMenus.forEach(this.menuStore::saveMenu);
        this.observer.syncWasSuccessful();
    }
}
