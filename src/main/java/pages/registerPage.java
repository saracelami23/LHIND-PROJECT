package pages;

import BaseTest.baseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class registerPage extends baseTest {
    WebDriver driver;

    public static final String SIGN_UP_XPATH = "//body//button[@id='create_account_action']";
    public static final String WELCOME_URL = "welcomeUrl";
    public static final String INVALID_EMAIL = "invalidEmail";
    public static final String REGISTERED_EMAIL = "registeredEmail";
    public static final String INVALID_NUMBER_PASSWORD = "invalidNumberPassword";
    public static final String INVALID_LETTER_PASSWORD = "invalidLetterPassword";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PASSWORD = "password";
    public static final String ACCEPT_TEXT = "accept";
    public static final String REGISTER_TEXT = "register";

    public registerPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //finding all elements
    @FindBy(id = "hs-eu-confirmation-button")
    WebElement acceptButton;

    @FindBy(xpath = "//input[@id='first_name']")
    WebElement firstNameInput;

    @FindBy(xpath = "//body//div[@data-testid='ta-web-ui-input-first_name-error']")
    WebElement firstNameValidationError;

    @FindBy(xpath = "//input[@id='last_name']")
    WebElement lastNameInput;

    @FindBy(xpath = "//body//div[@data-testid='ta-web-ui-input-last_name-error']")
    WebElement lastNameValidationError;

    @FindBy(xpath = "//body//input[@type='email']")
    WebElement emailInput;

    @FindBy(xpath = "//body//div[@data-testid='ta-web-ui-input-email-error']")
    WebElement emailValidationError;

    @FindBy(xpath = "//body//input[@placeholder='Password']")
    WebElement passwordInput;

    @FindBy(xpath = "//body//div[@data-testid='ta-web-ui-input-password-error']")
    WebElement passwordValidationError;

    @FindBy(xpath = "//body//button[@id='create_account_action']")
    WebElement signUpButton;

    public String getRandomNumber() {
        Random rnd = new Random();
        String randomChar = "+";
        for(int i=0; i<10; i++){
            randomChar = randomChar + rnd.nextInt(99);
        }
        return randomChar;
    }

    String email = "qateam" + getRandomNumber() + "@skooli.com";


    public void clickOnAcceptButton() {
        waitForElement(driver, acceptButton);
        clickAndDisplayText(driver, acceptButton, ACCEPT_TEXT);
    }

    public void checkSignUpState(String status){
        try {
            checkButtonState(signUpButton, status);
        }
        catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void checkFormValidation(){
        checkFieldValidation(driver, firstNameInput, firstNameValidationError, " ");
        checkFieldValidation(driver, lastNameInput, lastNameValidationError, " ");
        checkFieldValidation(driver, emailInput, emailValidationError, " ");
        checkFieldValidation(driver, passwordInput, passwordValidationError, " ");
        checkFieldValidation(driver, emailInput, emailValidationError, properties.getProperty(INVALID_EMAIL));
        checkFieldValidation(driver, emailInput, emailValidationError, properties.getProperty(REGISTERED_EMAIL));
        checkFieldValidation(driver, passwordInput, passwordValidationError, properties.getProperty(INVALID_NUMBER_PASSWORD));
        checkFieldValidation(driver, passwordInput, passwordValidationError, properties.getProperty(INVALID_LETTER_PASSWORD));
    }


    public void fillSignUpForm(){
        sendKeys(driver, firstNameInput, properties.getProperty(FIRST_NAME));
        sendKeys(driver, lastNameInput, properties.getProperty(LAST_NAME));
        sendKeys(driver, emailInput, email);
        sendKeys(driver, passwordInput, properties.getProperty(PASSWORD));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SIGN_UP_XPATH)));
    }

    public void clickSignUp(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SIGN_UP_XPATH)));
            clickOnElementAndAssertUrl(driver, signUpButton, properties.getProperty(REGISTER_TEXT), properties.getProperty(WELCOME_URL));
        }
        catch(TimeoutException e){
            e.printStackTrace();
        }
    }

}
