package com.github.butzopower.rssoup.usecases;

public class SyncMenus {
    private final SyncMenusObserver observer;

    public SyncMenus(SyncMenusObserver observer) {
        this.observer = observer;
    }

    public void execute() {
        this.observer.syncWasSuccessful();
    }
}
