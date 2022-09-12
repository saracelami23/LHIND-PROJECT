package pages;

import BaseTest.baseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class logInPage extends baseTest {
    WebDriver driver;

    public static final String INVALID_EMAIL = "invalidEmail";
    public static final String PASSWORD = "password";
    public static final String LOG_IN_URL = "logInUrl";
    public static final String DASHBOARD_URL = "dashboardUrl";
    public static final String REGISTERED_EMAIL = "registeredEmail";
    public static final String REGISTERED_PASSWORD = "registeredPassword";
    public static final String ACCEPT_TEXT = "accept";
    public static final String LOG_IN_TEXT = "logIn";

    public logInPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //finding all elements
    @FindBy(id = "hs-eu-confirmation-button")
    WebElement acceptButton;

    @FindBy(xpath = "//input[@id='username']")
    WebElement email;

    @FindBy(xpath = "//input[@id='password']")
    WebElement password;

    @FindBy(xpath = "//button[@id='login_action']")
    WebElement logInButton;

    @FindBy(xpath = "//div[@data-testid='ta-web-ui-toast-text']")
    WebElement toastErrorMessage;

    public void clickOnAcceptButton() {
        waitForElement(driver, acceptButton);
        clickAndDisplayText(driver, acceptButton, ACCEPT_TEXT);
    }

    public void checkLogInState(String status){
        try {
            checkButtonState(logInButton, status);
        }
        catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void fillIncorrectInfo(){
        sendKeys(driver, email, properties.getProperty(INVALID_EMAIL));
        sendKeys(driver, password, properties.getProperty(PASSWORD));
    }

    public void checkInvalidLogIn(){
            clickOnElementAndAssertUrl(driver, logInButton, properties.getProperty(LOG_IN_TEXT), properties.getProperty(LOG_IN_URL));
    }

    // Confirm text that is displayed
    public void checkErrorDisplayed(String text) {
        try {
            waitForElement(driver, toastErrorMessage);
            String data = toastErrorMessage.getText();
            Assert.assertEquals(data, text);
            log.info("Text verification is complete. The displayed text: " + text);
        }catch (NoSuchElementException e) {
            System.err.println("Unable to find element" + toastErrorMessage);
            e.printStackTrace();
        }
    }

    public void fillCorrectInfo(){
        clearData(email);
        sendKeys(driver, email, properties.getProperty(REGISTERED_EMAIL));
        clearData(password);
        sendKeys(driver, password, properties.getProperty(REGISTERED_PASSWORD));
    }

    public void checkValidLogIn(){
        clickOnElementAndAssertUrl(driver, logInButton, properties.getProperty(LOG_IN_TEXT), properties.getProperty(DASHBOARD_URL));
    }


}
