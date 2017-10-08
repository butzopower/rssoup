package com.github.butzopower.rssoup.usecases;

import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.entities.Menu;

import java.util.List;

public class FetchMenus {
    private final FetchMenusObserver observer;
    private final MenuStore menuStore;

    public FetchMenus(FetchMenusObserver observer, MenuStore menuStore) {
        this.observer = observer;
        this.menuStore = menuStore;
    }

    public void execute() {
        List<Menu> fetchedMenus = menuStore.allMenus();
        this.observer.menusFetched(fetchedMenus);
    }
}
