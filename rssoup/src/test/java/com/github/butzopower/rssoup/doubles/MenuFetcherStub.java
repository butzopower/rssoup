package com.github.butzopower.rssoup.doubles;

import com.github.butzopower.rssoup.MenuFetcher;
import com.github.butzopower.rssoup.entities.Menu;

import java.util.List;

public class MenuFetcherStub implements MenuFetcher {
    private final List<Menu> menusToReturn;

    public MenuFetcherStub(List<Menu> menusToReturn) {
        this.menusToReturn = menusToReturn;
    }

    @Override
    public List<Menu> fetchMenus() {
        return this.menusToReturn;
    }
}
