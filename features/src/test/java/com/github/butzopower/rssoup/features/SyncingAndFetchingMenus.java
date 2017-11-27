package com.github.butzopower.rssoup.features;

import com.github.butzopower.rssoup.doubles.FakeMenuStore;
import com.github.butzopower.rssoup.doubles.PostingFetcherStub;
import com.github.butzopower.rssoup.entities.Menu;
import com.github.butzopower.rssoup.entities.Posting;
import com.github.butzopower.rssoup.usecases.FetchMenus;
import com.github.butzopower.rssoup.usecases.SyncMenus;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
            SyncMenus syncMenusUseCase = new SyncMenus(() -> {}, this.menuFetcher, this.menuStore);
            syncMenusUseCase.execute();
        }

        void When_IFetchMenus() {
            FetchMenus fetchMenus = new FetchMenus(
                    fetchedMenus -> this.fetchedMenus = fetchedMenus,
                    this.menuStore);
            fetchMenus.execute();
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