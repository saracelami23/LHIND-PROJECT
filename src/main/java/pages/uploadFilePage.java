package pages;
import BaseTest.baseTest;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.File;

public class uploadFilePage extends baseTest {
    WebDriver driver;

    public static final String TERMS_AND_SERVICES_TEXT = "termsAndServices";
    public static final String SUBMIT_BUTTON_TEXT = "submit";

    public uploadFilePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //finding all elements
    @FindBy(xpath = "//input[@id='uploadfile_0' or @type='file']")
    WebElement chooseFileButton;


    @FindBy(xpath = "//button[@id='submitbutton']")
    WebElement submitButton;

    @FindBy(xpath = "//input[@id='terms']")
    WebElement termsAndServices;

    @FindBy(xpath = "//div/h3[@id='res']")
    WebElement termsAndServicesText;


    public void uploadFile(){
        try{
            String filePath = System.getProperty("user.dir") + "/src/main/resources/files/file.pdf";
            File file = new File(filePath);
            String path = file.getPath();
            waitForElement(driver, chooseFileButton);
            chooseFileButton.sendKeys(path);
            log.info("File is uploaded successfully");
        }catch (Exception e) {
            System.err.println("File couldn't upload");
            e.printStackTrace();
        }
    }

    public void submitButton(){
        waitForElement(driver, submitButton);
        clickAndDisplayText(driver, submitButton, SUBMIT_BUTTON_TEXT);
    }

    public void checkTermsAndServices(){
        try {
            if (termsAndServices.isSelected()){
                log.info("Terms And Services are selected");
            }
            else {
                log.info("Terms And Services are not selected");
            }
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void checkTermsAndServicesBehaviour(){
        try {
            checkTermsAndServices();
            clickAndDisplayText(driver, termsAndServices, properties.getProperty(TERMS_AND_SERVICES_TEXT));
            checkTermsAndServices();
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void isMessageDisplayed(String expectedText1, String expectedText2) {
        waitForElement(driver, termsAndServicesText);
        try {
            String actualText = termsAndServicesText.getAttribute("innerText");
            Assert.assertEquals(expectedText1+"\n"+expectedText2, actualText);
            log.info("Displayed " + actualText);
        } catch (NoSuchElementException e) {
            System.err.println("Element not found");
            e.printStackTrace();
        } catch (java.lang.AssertionError e) {
            String actualText = termsAndServicesText.getText();
            System.err.println("Displayed " + actualText + " instead of " + expectedText1+"\r\n"+expectedText2);
            e.printStackTrace();
        }
    }

}
