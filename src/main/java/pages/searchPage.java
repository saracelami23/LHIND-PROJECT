package pages;
import BaseTest.baseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class searchPage extends baseTest {
    WebDriver driver;

    public static final String RESULT_EXPECTED_URL = "resultUrl";

    public searchPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //finding all elements
    @FindBy(xpath = "//input[@name='q']")
    WebElement searchField;

    @FindBy(xpath = "//ul[@role='listbox']//li/descendant::div[@class='wM6W7d']")
    List<WebElement> results;

    public void enterKeyWord(String text){
        sendKeys(driver, searchField, text);
        try {
            Thread.sleep(Long.parseLong("1000"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkResults(){
        try{
            List<WebElement> allOptions = results;
            for (int i = 0; i <= allOptions.size() - 1; i++) {
                if (allOptions.get(i).getText().contains("css grid"))
                {
                    log.info("Css Grid Option is displayed");
                    allOptions.get(i).click();
                    break;
                }
            }
        }catch (Exception e) {
            System.err.println("Options are not displayed");
            e.printStackTrace();
        }
    }

    public void checkResultUrl(){
        assertPartialUrls(driver, properties.getProperty(RESULT_EXPECTED_URL));
    }

}
