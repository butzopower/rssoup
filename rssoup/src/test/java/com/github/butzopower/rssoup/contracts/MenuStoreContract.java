package com.github.butzopower.rssoup.contracts;

import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.entities.Menu;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public abstract class MenuStoreContract {
    public abstract MenuStore menuStoreFactory();

    public MenuStoreContract() { }

    @Test
    public void savedMenusAreRetrievable() throws Exception {
        MenuStore menuStore = menuStoreFactory();

        Menu menuToSave = new Menu();

        menuStore.saveMenu(menuToSave);

        List<Menu> menus = menuStore.allMenus();

        assertThat(menus.size(), equalTo(1));
        assertThat(menus, contains(menuToSave));
    }
}
