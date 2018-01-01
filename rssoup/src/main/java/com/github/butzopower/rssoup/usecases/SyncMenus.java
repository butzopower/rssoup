package com.github.butzopower.rssoup.usecases;

import com.github.butzopower.rssoup.PostingFetcher;
import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.entities.Menu;
import com.github.butzopower.rssoup.entities.Posting;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SyncMenus {
    private final SyncMenusObserver observer;
    private final PostingFetcher postingFetcher;
    private final MenuStore menuStore;
    private final Function<Posting, Boolean> filter;

    public SyncMenus(
            SyncMenusObserver observer,
            PostingFetcher postingFetcher,
            MenuStore menuStore,
            Function<Posting, Boolean> filter
    ) {
        this.observer = observer;
        this.postingFetcher = postingFetcher;
        this.menuStore = menuStore;
        this.filter = filter;
    }

    public void execute() {
        List<Posting> fetchedPostings = this.postingFetcher.fetchPostings();

        List<Menu> menusFromPostings = fetchedPostings.stream()
                .filter(filter::apply)
                .map(this::convertPostingToMenu)
                .collect(Collectors.toList());

        menusFromPostings.forEach(menuToSave -> {
            if (!this.menuStore.menuExistsOn(menuToSave.getDate())) {
                this.menuStore.saveMenu(menuToSave);
            }
        });

        this.observer.syncWasSuccessful();
    }

    private Menu convertPostingToMenu(Posting posting) {
        return new Menu(
                posting.getImageUrl(),
                posting.getPostedAt().toLocalDate()
        );
    }
}
