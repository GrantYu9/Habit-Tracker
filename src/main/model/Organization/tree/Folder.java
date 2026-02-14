package model.organization.tree;

import java.util.ArrayList;
import java.util.List;

// A container for folders and pages
public class Folder {
    private String title;

    private Folder parentFolder;
    private List<Folder> subFolders;
    private List<Page> pages;

    /*
    REQUIRES:
    title has at least one character
    A folder of title can not already be in AllFolders
    EFFECTS: 
    Initializes Page such that 
        this.title = title, with surrounding whitespace removed

        folders = new ArrayList<>()
        pages = new ArrayList<>()
     */
    public Folder(String title) {
        // !!!
    }

    // REQUIRES: folder operand must be new
    // MODIFIES: this
    // EFFECTS: adds folder to subfolders
    public void addToSubPages(Folder folder) {
        // !!!
    }

    // REQUIRES: page operand must be new
    // MODIFIES: this
    // EFFECTS: adds page to pages
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

    public Folder getParentFolder() {
        return parentFolder;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public List<Page> getPages() {
        return pages;
    }
}
