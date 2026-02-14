package model.organization.centralization;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.organization.tree.Page;

public class TestAllGenericPagesPage {
    private AllGenericPagesPage allGenericPagesPage;

    private Page pageA;
    private Page pageB;

    private List<Page> whatShouldBePage;
    
    @BeforeEach
    void runBeforeEach() {
        allGenericPagesPage = new AllGenericPagesPage();

        pageA = new Page("pageA", allGenericPagesPage);
        pageB = new Page("pageB", allGenericPagesPage);

        whatShouldBePage = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertTrue(allGenericPagesPage.getPages().isEmpty());
    }

    @Test
    void testAddToPages() {
        allGenericPagesPage.addToPages(pageA);
        whatShouldBePage.add(pageA);
        assertEquals(whatShouldBePage, allGenericPagesPage.getPages());

        allGenericPagesPage.addToPages(pageB);
        whatShouldBePage.add(pageB);
        assertEquals(whatShouldBePage, allGenericPagesPage.getPages());
    }
}
