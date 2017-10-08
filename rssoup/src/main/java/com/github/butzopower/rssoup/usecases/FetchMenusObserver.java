package com.github.butzopower.rssoup.usecases;

import com.github.butzopower.rssoup.entities.Menu;

import java.util.List;

public interface FetchMenusObserver {
    void menusFetched(List<Menu> fetchedMenus);
}
