package model.organization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.organization.Tag.TagType;

@ExcludeFromJacocoGeneratedReport
public class TestTag {
    private Tag tagA; // Normal title
    private Tag tagB; // Funky title
    private Tag tagFavourite;
    private Tag tagHome;

    @BeforeEach
    void runBeforeEach() {
        tagA = new Tag("tagA");
        tagB = new Tag(" sUper funky Title ");
        tagFavourite = new Tag("Favourite");
        tagHome = new Tag("Home");
    }

    @Test
    void testConstructorNormal() {
        assertTrue(tagA.getTitle().equals("tagA"));
        assertEquals(TagType.NORMAL, tagA.getTagType());
        assertTrue(tagA.getColour().equals(tagA.getDefaultColour()));
    }

    @Test
    void testConstructorTitleTrim() {
        assertTrue(tagB.getTitle().equals("sUper funky Title"));
        assertEquals(TagType.NORMAL, tagB.getTagType());
        assertTrue(tagB.getColour().equals(tagB.getDefaultColour()));
    }

    @Test
    void testConstructorFavourite() {
        assertTrue(tagFavourite.getTitle().equals("Favourite"));
        assertEquals(TagType.FAVOURITE, tagFavourite.getTagType());
        assertTrue(tagFavourite.getColour().equals(tagA.getFavouriteColour()));
    }

    @Test
    void testConstructorHome() {
        assertTrue(tagHome.getTitle().equals("Home"));
        assertEquals(TagType.HOME, tagHome.getTagType());
        assertTrue(tagHome.getColour().equals(tagHome.getHomeColour()));
    }

    @Test
    void testSetTitle() {
        tagA.setTitle(" aNother sUper FunKy Title ");
        assertTrue(tagA.getTitle().equals("aNother sUper FunKy Title"));
        assertEquals(TagType.NORMAL, tagA.getTagType());
        assertTrue(tagA.getColour().equals(tagA.getDefaultColour()));

        tagA.setTitle(" HoMe ");
        assertTrue(tagA.getTitle().equals("Home"));
        assertEquals(TagType.HOME, tagA.getTagType());
        assertTrue(tagA.getColour().equals(tagA.getHomeColour()));

        tagA.setTitle(" Favorite ");
        assertTrue(tagA.getTitle().equals("Favorite"));
        assertEquals(TagType.NORMAL, tagA.getTagType());
        assertTrue(tagA.getColour().equals(tagA.getDefaultColour()));

        tagA.setTitle(" FAvourite ");
        assertTrue(tagA.getTitle().equals("Favourite"));
        assertEquals(TagType.FAVOURITE, tagA.getTagType());
        assertTrue(tagA.getColour().equals(tagA.getFavouriteColour()));
    }
}
