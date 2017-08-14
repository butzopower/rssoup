package com.github.butzopower.rssoup.doubles;

import com.github.butzopower.rssoup.MenuStore;
import com.github.butzopower.rssoup.contracts.MenuStoreContract;

public class FakeMenuStoreTest extends MenuStoreContract {
    @Override
    public MenuStore menuStoreFactory() {
        return new FakeMenuStore();
    }
}
