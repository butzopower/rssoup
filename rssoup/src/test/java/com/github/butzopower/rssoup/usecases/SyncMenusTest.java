package com.github.butzopower.rssoup.usecases;

import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.PostingFetcher;
import com.github.butzopower.rssoup.doubles.FakeMenuStore;
import com.github.butzopower.rssoup.doubles.PostingFetcherStub;
import com.github.butzopower.rssoup.entities.Menu;
import com.github.butzopower.rssoup.entities.Posting;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.github.butzopower.rssoup.RssSoup.syncMenus;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class SyncMenusTest {
    GuiSpy guiSpy;
    PostingFetcher postingFetcher;
    MenuStore menuStore;

    LocalDate date1 = LocalDate.of(2017, 10, 28);
    LocalDate date2 = LocalDate.of(2017, 11, 17);

    LocalDateTime dateTime1 = LocalDateTime.of(date1, LocalTime.of(0, 0));
    LocalDateTime dateTime2 = LocalDateTime.of(date2, LocalTime.of(0, 0));

    List<Posting> postings = asList(
            new Posting("Today's Soups are delicious", "image1.jpg", dateTime1),
            new Posting("Next Month's Soups are delicious", "image2.jpg", dateTime2)
    );

    Menu expectedMenu1 = new Menu("image1.jpg", date1);
    Menu expectedMenu2 = new Menu("image2.jpg", date2);

    List<Menu> expectedMenus = asList(expectedMenu1, expectedMenu2);

    @Before
    public void setUp() throws Exception {
        this.guiSpy = new GuiSpy();
        this.postingFetcher = new PostingFetcherStub(postings);
        this.menuStore = new FakeMenuStore();
    }

    @Test
    public void notifiesWhenTheSyncingIsSuccessful() throws Exception {
        syncMenus(guiSpy, postingFetcher, menuStore);

        assertThat(guiSpy.wasSyncSuccessful(), equalTo(true));
    }

    @Test
    public void syncsMenusFromAMenuFetcherToAMenuRepository() throws Exception {
        syncMenus(guiSpy, postingFetcher, menuStore);

        assertThat(this.menuStore.allMenus(), equalTo(expectedMenus));
    }

    @Test
    public void syncingTheSameItemsMultipleTimesOnlyIncludesTheMenusOnce() throws Exception {
        syncMenus(guiSpy, postingFetcher, menuStore);
        syncMenus(guiSpy, postingFetcher, menuStore);

        assertThat(this.menuStore.allMenus(), equalTo(expectedMenus));
    }

    @Test
    public void onlyItemsThatPassAFilterAreSynced() throws Exception {
        syncMenus(
                guiSpy,
                postingFetcher,
                menuStore,
                posting -> posting.getPostContent().contains("Today")
        );

        List<Menu> menus = this.menuStore.allMenus();

        assertThat(menus, contains(expectedMenu1));
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
