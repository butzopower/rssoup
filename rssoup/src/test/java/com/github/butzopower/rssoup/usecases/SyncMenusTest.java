package com.github.butzopower.rssoup.usecases;

import com.github.butzopower.rssoup.MenuFetcher;
import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.doubles.FakeMenuStore;
import com.github.butzopower.rssoup.doubles.MenuFetcherStub;
import com.github.butzopower.rssoup.entities.Menu;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SyncMenusTest {
    GuiSpy guiSpy;
    MenuFetcher menuFetcher;
    MenuStore menuStore;

    List<Menu> expectedMenus = Arrays.asList(new Menu(), new Menu());

    @Before
    public void setUp() throws Exception {
        this.guiSpy = new GuiSpy();
        this.menuFetcher = new MenuFetcherStub(expectedMenus);
        this.menuStore = new FakeMenuStore();
    }

    @Test
    public void notifiesWhenTheSyncingIsSuccessful() throws Exception {
        SyncMenus syncMenus = new SyncMenus(guiSpy, menuFetcher, menuStore);

        syncMenus.execute();

        assertThat(guiSpy.wasSyncSuccessful(), equalTo(true));
    }

    @Test
    public void syncsMenusFromAMenuFetcherToAMenuRepository() throws Exception {
        SyncMenus syncMenus = new SyncMenus(guiSpy, menuFetcher, menuStore);

        syncMenus.execute();

        assertThat(this.menuStore.allMenus(), equalTo(expectedMenus));
    }

    class GuiSpy implements SyncMenusObserver {
        private boolean syncWasSuccessful;

        @Override
        public void syncWasSuccessful() {
            this.syncWasSuccessful = true;
        }

        boolean wasSyncSuccessful() {
            return syncWasSuccessful;
        }
    }
}
