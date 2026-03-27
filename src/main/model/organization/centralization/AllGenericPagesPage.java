package model.organization.centralization;

import java.util.ArrayList;
import java.util.List;

import logging.Event;
import logging.EventLog;
import model.organization.tree.Page;

// Where all the pages will be located
public class AllGenericPagesPage {
    List<Page> pages;
    
    // EFFECTS: Instantiates allGenericPages such that pages = new ArrayList
    public AllGenericPagesPage() {
        pages = new ArrayList<>();
    }

    // REQUIRES: page not already in pages
    // MODIFIES: this
    // EFFECTS: Adds page to pages and logs the event in eventLog
    public void addToPages(Page page, EventLog eventLog) {
        pages.add(page);
        eventLog.logEvent(new Event("Page of title \"" + page.getTitle() + "\" added."));
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this == object) {
            return true;
        }

        AllGenericPagesPage allGenericPagesPage = (AllGenericPagesPage) object;

        if (pages.size() != allGenericPagesPage.getPages().size()) {
            return false;
        }

        for (int i = 0; i < pages.size(); i++) {
            if (!pages.get(i).equals(allGenericPagesPage.getPages().get(i))) {
                return false;
            }
        }

        return true;
    }
    
    public List<Page> getPages() {
        return pages;
    }

    // !!! handle logging method
}
