package com.github.butzopower.rssoup.usecases;

import com.github.butzopower.rssoup.PostingFetcher;
import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.doubles.FakeMenuStore;
import com.github.butzopower.rssoup.doubles.PostingFetcherStub;
import com.github.butzopower.rssoup.entities.Menu;
import com.github.butzopower.rssoup.entities.Posting;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SyncMenusTest {
    GuiSpy guiSpy;
    PostingFetcher postingFetcher;
    MenuStore menuStore;

    LocalDate date1 = LocalDate.of(2017, 10, 28);
    LocalDate date2 = LocalDate.of(2017, 11, 17);

    LocalDateTime dateTime1 = LocalDateTime.of(date1, LocalTime.of(0, 0));
    LocalDateTime dateTime2 = LocalDateTime.of(date2, LocalTime.of(0, 0));

    List<Posting> postings = Arrays.asList(new Posting(dateTime1), new Posting(dateTime2));
    List<Menu> expectedMenus = Arrays.asList(new Menu(date1), new Menu(date2));

    @Before
    public void setUp() throws Exception {
        this.guiSpy = new GuiSpy();
        this.postingFetcher = new PostingFetcherStub(postings);
        this.menuStore = new FakeMenuStore();
    }

    @Test
    public void notifiesWhenTheSyncingIsSuccessful() throws Exception {
        SyncMenus syncMenus = new SyncMenus(guiSpy, postingFetcher, menuStore);

        syncMenus.execute();

        assertThat(guiSpy.wasSyncSuccessful(), equalTo(true));
    }

    @Test
    public void syncsMenusFromAMenuFetcherToAMenuRepository() throws Exception {
        SyncMenus syncMenus = new SyncMenus(guiSpy, postingFetcher, menuStore);

        syncMenus.execute();

        assertThat(this.menuStore.allMenus(), equalTo(expectedMenus));
    }

    @Test
    public void syncingTheSameItemsMultipleTimesOnlyIncludesTheMenusOnce() throws Exception {
        SyncMenus syncMenus = new SyncMenus(guiSpy, postingFetcher, menuStore);

        syncMenus.execute();
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
