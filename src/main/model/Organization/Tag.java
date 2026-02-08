package model.organization;

import java.awt.Color;

/*
A tag for organization
 */
public class Tag {
    private static Color defautColour = Color.DARK_GRAY; // Default colour of the tag; can be changed
    private static Color homeColour = defautColour; // Colour for the home tag, set to defaultColour by default
    private static Color favouriteColour = Color.YELLOW; // Colour for the favourite tag, set to yellow by default

    private TagType tagType; // Type of the tag, which can alter behaviour
    private enum TagType {
        NORMAL, // A generic, customizable tag
        /*
        A tag that signals the habit should be on the home page
         */
        HOME,
        /*
        A tag that signals the habit should be on the favourite page
         */
        FAVOURITE
    }

    private String title; // Title of the tag
    
    private Color colour; // Colour of tag

    /*
    REQUIRES:
        title has at least one character
    EFFECTS:
    Instantiates Tag such that
        this.title = title, with surrounding whitespace trimmed
        if title.strip().lower().equals("home")
            tagType = HOME
            colour = homeColour
            !!! constructor
        else if title.strip().lower().equals("favourite")
            tagType = FAVOURITE
            colour = favouriteColour
            !!! constructor
        else
            colour = defaultColour
     */
    public Tag(String title) {
        // !!!
    }

    /*
    REQUIRES:
        title.strip().lower() can not be "home" nor "favourite"
    EFFECTS:
    Instantiates Tag such that
        this.title = title, with surrounding whitespace trimmed
        this.colour = colour
        !!! constructor
     */
    public Tag(String title, Color colour) {
        // !!!
    }

    // REQUIRES: title has at least one character
    // EFFECTS: this.title = title, with surrounding whitespace trimmed
    public void setTitle(String title) {
        // !!!
    }

    public void setDefaultColour(Color defaultColour) {
        Tag.defautColour = defaultColour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Color getDefaultColour() {
        return defautColour;
    }

    public String getTitle() {
        return title;
    }

    public Color getColour() {
        return colour;
    }
}
