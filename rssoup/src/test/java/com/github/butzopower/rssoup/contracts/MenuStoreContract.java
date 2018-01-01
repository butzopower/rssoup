package com.github.butzopower.rssoup.contracts;

import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.entities.Menu;
import org.junit.Test;

import java.time.LocalDate;
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

        Menu menuToSave = new Menu(null, LocalDate.of(2017, 10, 28));

        menuStore.saveMenu(menuToSave);

        List<Menu> menus = menuStore.allMenus();

        assertThat(menus.size(), equalTo(1));
        assertThat(menus, contains(menuToSave));
    }

    @Test
    public void storeCanCheckForMenusThatHaveAlreadyBeenSaved() throws Exception {
        LocalDate date = LocalDate.of(2017, 10, 28);
        LocalDate otherDate = LocalDate.of(2017, 9, 18);

        MenuStore menuStore = menuStoreFactory();
        Menu menuToSave = new Menu(null, date);

        assertThat(menuStore.menuExistsOn(date), equalTo(false));

        menuStore.saveMenu(menuToSave);

        assertThat(menuStore.menuExistsOn(date), equalTo(true));
        assertThat(menuStore.menuExistsOn(otherDate), equalTo(false));
    }
}
