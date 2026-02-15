package model.organization.centralization;

import java.util.ArrayList;
import java.util.List;

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
    // EFFECTS: Adds page to pages
    public void addToPages(Page page) {
        pages.add(page);
    }
    
    public List<Page> getPages() {
        return pages;
    }
}
