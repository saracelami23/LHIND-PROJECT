package TestCases;
import BaseTest.baseTest;
import org.testng.annotations.Test;
import pages.searchPage;

public class searchTest extends baseTest{
    public static final String SEARCH_URL = "searchUrl";
    public static final String SEARCH_TEXT = "keyWord";

    @Test
    public void testSearch(){
        searchPage search = new searchPage(driver);
        driver.get(properties.getProperty(SEARCH_URL));
        search.enterKeyWord(properties.getProperty(SEARCH_TEXT));
        search.checkResults();
        search.checkResultUrl();
    }
}
