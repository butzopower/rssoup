package com.github.butzopower.rssoup.usecases;

import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.doubles.FakeMenuStore;
import com.github.butzopower.rssoup.entities.Menu;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static com.github.butzopower.rssoup.RssSoup.fetchMenus;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class FetchMenusTest {
    @Test
    public void fetchingMenusReportsThemToObserverOnSuccess() throws Exception {
        GuiSpy guiSpy = new GuiSpy();

        Menu menu1 = new Menu(null, LocalDate.of(2017, 10, 28));
        Menu menu2 = new Menu(null, LocalDate.of(2017, 9, 14));

        MenuStore menuStore = new FakeMenuStore();
        menuStore.saveMenu(menu1);
        menuStore.saveMenu(menu2);

        fetchMenus(guiSpy, menuStore);

        assertThat(guiSpy.getMostRecentlyFetchMenus(), containsInAnyOrder(menu1, menu2));
    }

    class GuiSpy implements FetchMenusObserver {
        private List<Menu> mostRecentlyFetchMenus;

        @Override
        public void menusFetched(List<Menu> fetchedMenus) {
            this.mostRecentlyFetchMenus = fetchedMenus;
        }

        public List<Menu> getMostRecentlyFetchMenus() {
            return mostRecentlyFetchMenus;
        }
    }
}