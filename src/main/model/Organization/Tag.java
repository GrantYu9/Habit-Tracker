package model.organization;

import java.awt.Color;

/*
A tag for organization
 */
public class Tag {
    private static Color defautColour = Color.DARK_GRAY; // Default colour of the tag; can be changed

    private String title; // Title of the tag
    
    private Color colour; // Colour of tag

    /*
    EFFECTS:
    Instantiates Tag such that
        this.title = title, with surrounding whitespace trimmed
        
        colour = defaultColour
     */
    public Tag(String title) {
        // !!!
    }

    /*
    EFFECTS:
    Instantiates Tag such that
        this.title = title, with surrounding whitespace trimmed
        this.colour = colour
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
