package model.organization;

import java.util.ArrayList;
import java.util.List;

// A container for folders and pages
public class Folder {
    private String title;

    private List<Folder> folders;
    private List<Page> pages;

    /*
    REQUIRES:
        title has at least one character
    EFFECTS: initializes Page such that 
        this.title = title, with surrounding whitespace removed

        folders = new ArrayList<>()
        pages = new ArrayList<>()
     */
    public Folder(String title) {
        // !!!
    }

    public void addToFolder(Folder folder) {
        // !!!
    }

    public void addToFolder(Page page) {
        // !!!
    }

    // REQUIRES: title has at least one character
    // EFFECTS: this.title = title, with surrounding whitespace trimmed
    public void setTitle(String title) {
        // !!!
    }

    public String getTitle(String title) {
        return title;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public List<Page> getPages() {
        return pages;
    }
}
