package model.organization.centralization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.organization.Tag;
import model.organization.specialpages.TagPage;

@ExcludeFromJacocoGeneratedReport
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

        tagPageA = new TagPage(tagA);
        tagPageB = new TagPage(tagB);

        whatShouldBeTagPage = new ArrayList<>();
    }
    
    @Test
    void testConstructor() {
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
