package model.organization.centralization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.organization.Tag;
import model.organization.specialpages.TagPage;

public class TestAllTagPagesPage {
    private AllTagPagesPage allTagPages;

    private Tag tagA;
    private Tag tagB;

    private TagPage tagPageA;
    private TagPage tagPageB;

    private List<TagPage> whatShouldBeTagPage;

    @BeforeEach
    void runBeforeEach() {
        allTagPages = new AllTagPagesPage();

        tagA = new Tag("tagA");
        tagB = new Tag("tagB");

        tagPageA = new TagPage(tagA, allTagPages);
        tagPageB = new TagPage(tagB, allTagPages);

        whatShouldBeTagPage = new ArrayList<>();
    }
    
    @Test
    void testConstructor() {
        whatShouldBeTagPage.add(tagPageA);
        whatShouldBeTagPage.add(tagPageB);
        assertEquals(whatShouldBeTagPage, allTagPages.getTagPages());
    }

    @Test
    void testAddToTagPage() {
        allTagPages.getTagPages().clear();
        
        allTagPages.addToTagPages(tagPageA);
        whatShouldBeTagPage.add(tagPageA);
        assertEquals(whatShouldBeTagPage, allTagPages.getTagPages());

        allTagPages.addToTagPages(tagPageB);
        whatShouldBeTagPage.add(tagPageB);
        assertEquals(whatShouldBeTagPage, allTagPages.getTagPages());
    }
}
