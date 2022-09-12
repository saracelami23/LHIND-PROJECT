package TestCases;

import BaseTest.baseTest;
import org.testng.annotations.Test;
import pages.tableRows;

public class tableRowsTest extends baseTest {
    public static final String TABLE_URL = "tableUrl";

    @Test
    public void testTableRows(){
        tableRows tableRows = new tableRows(driver);

        driver.get(properties.getProperty(TABLE_URL));

        tableRows.checkTableRows();
    }
}
