package model.organization.centralization;

import java.util.ArrayList;
import java.util.List;

import model.organization.specialpages.TagPage;

// Where all the tag pages will be located
public class AllTagPagesPage {
    private List<TagPage> tagPages;

    // EFFECTS: Instantiates AllTagPages such that tagPages = new ArrayList
    public AllTagPagesPage() {
        tagPages = new ArrayList<>();
    }

    // REQUIRES: tagPage is not already in tagPages
    // MODIFIES: this
    // EFFECTS: Adds tagPage to tagPages
    public void addToTagPages(TagPage tagPage) {
        tagPages.add(tagPage);
    }

    public List<TagPage> getTagPages() {
        return tagPages;
    }
}
