package com.github.butzopower.rssoup.doubles;

import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.entities.Menu;

import java.util.ArrayList;
import java.util.List;

public class FakeMenuStore implements MenuStore {
    private final ArrayList<Menu> menus;

    public FakeMenuStore() {
        this.menus = new ArrayList<>();
    }

    @Override
    public List<Menu> allMenus() {
        return this.menus;
    }

    @Override
    public void saveMenu(Menu menuToSave) {
        this.menus.add(menuToSave);
    }
}
