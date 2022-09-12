package pages;
import BaseTest.baseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class tableRows extends baseTest {
    WebDriver driver;

    public tableRows(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //finding elements
    @FindBy(xpath = "//table/tbody/tr/td[1]")
    List<WebElement> companies;

    public void checkTableRows(){
        try{
            log.info("List of companies is :");
            List<WebElement> allOptions = companies;
            for (int i = 0; i <= allOptions.size() - 1; i++) {
                log.info(allOptions.get(i).getText());
            }
            log.info("Total of companies is : " + companies.size());
        }catch (Exception e) {
            System.err.println("Items are not displayed");
            e.printStackTrace();
        }

        }
}
