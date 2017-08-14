package com.github.butzopower.rssoup;

import com.github.butzopower.rssoup.entities.Menu;

import java.util.List;

public interface MenuStore {
    List<Menu> allMenus();
    void saveMenu(Menu menuToSave);
}
