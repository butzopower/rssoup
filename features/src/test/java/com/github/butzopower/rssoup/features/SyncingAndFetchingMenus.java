package com.github.butzopower.rssoup.features;

import com.github.butzopower.rssoup.doubles.FakeMenuStore;
import com.github.butzopower.rssoup.doubles.PostingFetcherStub;
import com.github.butzopower.rssoup.entities.Menu;
import com.github.butzopower.rssoup.entities.Posting;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.github.butzopower.rssoup.RssSoup.fetchMenus;
import static com.github.butzopower.rssoup.RssSoup.syncMenus;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SyncingAndFetchingMenus {
    @Test
    public void syncedMenusCanBeFetched() throws Exception {
        SyncedMenusCanBeFetched feature = new SyncedMenusCanBeFetched();

        feature.Given_somePostsHaveBeenMadeOnTwitter();
        feature.And_thosePostsHaveBeenSynced();
        feature.When_IFetchMenus();
        feature.Then_ISeeAListOfTheLatestMenus();
    }

    private static class SyncedMenusCanBeFetched {
        private FakeMenuStore menuStore;
        private PostingFetcherStub menuFetcher;
        private List<Menu> fetchedMenus;

        public SyncedMenusCanBeFetched() {
            this.menuStore = new FakeMenuStore();
        }

        void Given_somePostsHaveBeenMadeOnTwitter() {
            this.menuFetcher = new PostingFetcherStub(this.buildPostings());
        }

        void And_thosePostsHaveBeenSynced() {
            syncMenus(
                    () -> {},
                    this.menuFetcher,
                    this.menuStore
            );
        }

        void When_IFetchMenus() {
            fetchMenus(
                    fetchedMenus -> this.fetchedMenus = fetchedMenus,
                    this.menuStore
            );
        }

        void Then_ISeeAListOfTheLatestMenus() {
            assertThat(fetchedMenus, equalTo(buildMenus()));
        }

        private List<Menu> buildMenus() {
            return Arrays.asList(new Menu(LocalDate.of(2017, 10, 28)));
        }

        private List<Posting> buildPostings() {
            return Arrays.asList(new Posting(LocalDateTime.of(2017, 10, 28, 0, 0)));
        }

    }

}