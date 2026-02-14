package model.organization;

import model.organization.specialpages.TagPage;

/*
Searches for
    a tag; will give the TagPage
    a habit
    a page
    a folder
 */
public class Search {

    // EFFECTS: Instantiates Search to use search methods
    public Search() {}

    /*
    REQUIRES:
    title must have at least character
    EFFECTS:
    title will have surrounding whitespace trimmed
    if there exists a TagPage such that TagPage.getTitle().equals(title)
        return TagPage
    else
        return null
     */
    public TagPage searchTagPage(String title) {
        return null;
    }

    /*
    REQUIRES:
    title must have at least character
    EFFECTS:
    title will have surrounding whitespace trimmed
    if there exists a Habit such that Habit.getTitle().equals(title)
        return Habit
    else
        return null
     */
    public TagPage searchHabit(String title) {
        return null;
    }

    /*
    REQUIRES:
    title must have at least character
    EFFECTS:
    title will have surrounding whitespace trimmed
    if there exists a Page such that Page.getTitle().equals(title)
        return Page
    else
        return null
     */
    public TagPage searchPage(String title) {
        return null;
    }

    /*
    REQUIRES:
    title must have at least character
    EFFECTS:
    title will have surrounding whitespace trimmed
    if there exists a Folder such that Folder.getTitle().equals(title)
        return Folder
    else
        return null
     */
    public TagPage searchFolder(String title) {
        return null;
    }
}
