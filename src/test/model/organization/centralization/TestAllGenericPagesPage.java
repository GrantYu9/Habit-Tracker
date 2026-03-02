package model.organization.centralization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.organization.tree.Page;

@ExcludeFromJacocoGeneratedReport
public class TestAllGenericPagesPage {
    private AllGenericPagesPage allGenericPagesPage;

    private Page pageA;
    private Page pageB;

    private List<Page> whatShouldBePage;
    
    @BeforeEach
    void runBeforeEach() {
        allGenericPagesPage = new AllGenericPagesPage();

        pageA = new Page("pageA");
        pageB = new Page("pageB");

        whatShouldBePage = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals(whatShouldBePage, allGenericPagesPage.getPages());
    }

    @Test
    void testAddToPages() {
        allGenericPagesPage.getPages().clear();
        
        allGenericPagesPage.addToPages(pageA);
        whatShouldBePage.add(pageA);
        assertEquals(whatShouldBePage, allGenericPagesPage.getPages());

        allGenericPagesPage.addToPages(pageB);
        whatShouldBePage.add(pageB);
        assertEquals(whatShouldBePage, allGenericPagesPage.getPages());
    }
}
