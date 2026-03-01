package persistence;

import java.io.IOException;

import model.organization.centralization.AllGenericPagesPage;

// Reads and writes data of AllGenericPages from a destination
public class AllGenericPagesPageDataManager {
    private static final int TAB = 4; // Identation

    private String destination; // destination to write to

    private AllGenericPagesPage allGenericPagesPage; // Where all the pages are

    /*
     * Instantiates AllGenericPagesDataManager such that
     * this.destination = destination
     * this.allGenericPagesPage = allGenericPagesPage
     */
    public AllGenericPagesPageDataManager(String destination, AllGenericPagesPage allGenericPagesPage) {
        // !!!
    }

    /*
     * MODIFIES:
     * allGenericPagesPage
     * EFFECTS:
     * Reconstructs allGenericPagesPage from file
     * Throws IOException upon failure to properly read
     */
    public void readFromFile() throws IOException {
        // !!!
    }

    /*
     * MODIFIES:
     * this
     * EFFECTS:
     * Writes allGenericPagesPage to file as JSON
     * Throws IOException upon failure to properly write
     */
    public void writeToFile() throws IOException {
        // !!!
    }

    public String getDestination() {
        return destination;
    }

    public AllGenericPagesPage getAllGenericPagesPage() {
        return allGenericPagesPage;
    }
}
