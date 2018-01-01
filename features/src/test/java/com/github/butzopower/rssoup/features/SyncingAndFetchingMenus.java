package com.github.butzopower.rssoup.features;

import com.github.butzopower.rssoup.doubles.FakeMenuStore;
import com.github.butzopower.rssoup.doubles.PostingFetcherStub;
import com.github.butzopower.rssoup.entities.Menu;
import com.github.butzopower.rssoup.entities.Posting;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.butzopower.rssoup.RssSoup.fetchMenus;
import static com.github.butzopower.rssoup.RssSoup.syncMenus;
import static java.util.Arrays.asList;
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

    @Test
    public void syncedMenusAreFiltered() throws Exception {
        SyncedMenusAreFiltered feature = new SyncedMenusAreFiltered();

        feature.Given_somePostsHaveBeenMadeOnTwitterWithVaryingContent();
        feature.And_thosePostsHaveBeenSynced();
        feature.When_IFetchMenus();
        feature.Then_ISeeOnlyTheMenusThatMatchAFilter();
    }

    private static class SyncedMenusCanBeFetched extends MenuSyncingFeature {
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

        void Then_ISeeAListOfTheLatestMenus() {
            assertThat(fetchedMenus, equalTo(buildMenus()));
        }

        private List<Menu> buildMenus() {
            return asList(new Menu(LocalDate.of(2017, 10, 28)));
        }

        private List<Posting> buildPostings() {
            return asList(
                    new Posting("Today's Soups are delicious", LocalDateTime.of(2017, 10, 28, 0, 0))
            );
        }

    }

    private static class SyncedMenusAreFiltered extends MenuSyncingFeature {
        void Given_somePostsHaveBeenMadeOnTwitterWithVaryingContent() {
            this.menuFetcher = new PostingFetcherStub(this.buildPostings());
        }

        void And_thosePostsHaveBeenSynced() {
            syncMenus(
                    () -> {},
                    this.menuFetcher,
                    this.menuStore,
                    posting -> posting.getPostContent().toLowerCase().startsWith("today's soups")
            );
        }

        void Then_ISeeOnlyTheMenusThatMatchAFilter() {
            assertThat(fetchedMenus, equalTo(expectedMenus()));
        }

        private List<Posting> buildPostings() {
            Posting postingThatMatchesFilter = new Posting(
                    "Today's Soups are delicious",
                    LocalDateTime.of(2017, 10, 28, 0, 0)
            );

            Posting postingThatDoesNotMatchFilter = new Posting(
                    "Random picture of my dog",
                    LocalDateTime.of(2017, 10, 29, 0, 0)
            );

            return asList(
                    postingThatMatchesFilter,
                    postingThatDoesNotMatchFilter
            );
        }

        private List<Menu> expectedMenus() {
            return asList(new Menu(LocalDate.of(2017, 10, 28)));
        }
    }

    private static class MenuSyncingFeature {
        void When_IFetchMenus() {
            fetchMenus(
                    fetchedMenus -> this.fetchedMenus = fetchedMenus,
                    this.menuStore
            );
        }

        MenuSyncingFeature() {
            this.menuStore = new FakeMenuStore();
        }

        PostingFetcherStub menuFetcher;
        FakeMenuStore menuStore;
        List<Menu> fetchedMenus;
    }
}