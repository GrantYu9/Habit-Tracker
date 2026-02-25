package model.organization;

import java.awt.Color;

/*
A tag for organization
 */
public class Tag {
    private static Color defaultColour = Color.DARK_GRAY; // Default colour of the tag; can be changed
    private static Color favouriteColour = Color.YELLOW; // Colour for the favourite tag, set to yellow by default
    private static Color homeColour = Color.LIGHT_GRAY; // Colour for the home tag, set to defaultColour by default

    private TagType tagType; // Type of the tag, which can alter behaviour
    
    public enum TagType {
        FAVOURITE, // A tag that signals the habit should be on the favourite page
        HOME, // A tag that signals the habit should be on the home page
        NORMAL // A generic, customizable tag
    }

    private String title; // Title of the tag
    
    private Color colour; // Colour of tag

    /*
    REQUIRES:
    title has at least one character
    There can not already exist a TagPage with the corresponding Tag title in AllTagPages
    EFFECTS:
    Instantiates Tag such that
        this.title = title, with surrounding whitespace trimmed
        if title.strip().lower().equals("home")
            this.title = "Home"
            tagType = HOME
            colour = homeColour
        else if title.strip().lower().equals("favourite")
            this.title = "Favourite"
            tagType = FAVOURITE
            colour = favouriteColour
        else
            tagType = NORMAL
            colour = defaultColour
     */
    public Tag(String title) {
        this.title = title.strip();
        tagType = TagType.NORMAL;
        colour = defaultColour;

        String titleLowerCase = this.title.toLowerCase();
        if (titleLowerCase.equals("home")) {
            this.title = "Home";
            tagType = TagType.HOME;
            colour = homeColour;
        } else if (titleLowerCase.equals("favourite")) {
            this.title = "Favourite";
            tagType = TagType.FAVOURITE;
            colour = favouriteColour;
        }
    }

    /*
    REQUIRES:
    title has at least one character
    EFFECTS:
    this.title = title, with surrounding whitespace trimmed
    if title.strip().lower().equals("home")
        this.title = "Home"
        tagType = HOME
        colour = homeColour
    else if title.strip().lower().equals("favourite")
        this.title = "Favourite"
        tagType = FAVOURITE
        colour = favouriteColour
     */
    public void setTitle(String title) {
        this.title = title.strip();
        tagType = TagType.NORMAL;
        colour = defaultColour;

        String titleLowerCase = this.title.toLowerCase();
        if (titleLowerCase.equals("home")) {
            this.title = "Home";
            tagType = TagType.HOME;
            colour = homeColour;
        } else if (titleLowerCase.equals("favourite")) {
            this.title = "Favourite";
            tagType = TagType.FAVOURITE;
            colour = favouriteColour;
        }
    }

    public void setDefaultColour(Color defaultColour) {
        Tag.defaultColour = defaultColour;
    }

    public void setFavouriteColour(Color favouriteColour) {
        Tag.favouriteColour = favouriteColour;
    }

    public void setHomeColour(Color homeColour) {
        Tag.homeColour = homeColour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Color getDefaultColour() {
        return defaultColour;
    }

    public Color getFavouriteColour() {
        return favouriteColour;
    }

    public Color getHomeColour() {
        return homeColour;
    }

    public TagType getTagType() {
        return tagType;
    }

    public String getTitle() {
        return title;
    }

    public Color getColour() {
        return colour;
    }
}
