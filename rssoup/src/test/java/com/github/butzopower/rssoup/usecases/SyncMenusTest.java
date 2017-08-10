package com.github.butzopower.rssoup.usecases;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SyncMenusTest {
    @Test
    public void notifiesWhenTheSyncingIsSuccessful() throws Exception {
        GuiSpy guiSpy = new GuiSpy();
        SyncMenus syncMenus = new SyncMenus(guiSpy);

        syncMenus.execute();

        assertThat(guiSpy.wasSyncSuccessful(), equalTo(true));
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
