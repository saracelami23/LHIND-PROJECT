package BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class baseTest {
    protected WebDriver driver;
    public static Logger log;
    protected Properties properties = new Properties();
    public static final String CONFIG_PROPERTIES_PATH = "src/main/resources/config.properties";
    public static final String TEXT_SOURCE_PROPERTIES_PATH = "src/main/resources/TextSource.properties";

    public baseTest(){
        // Create Properties class object to access properties file
        FileInputStream configFileInput = null;
        FileInputStream textFileInput = null;
        try {
            configFileInput = new FileInputStream(new File(CONFIG_PROPERTIES_PATH));
            textFileInput = new FileInputStream(new File(TEXT_SOURCE_PROPERTIES_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Load properties file
        try {
            properties.load(configFileInput);
            properties.load(textFileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext ctx){
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/resources/geckodriver");
        driver = new FirefoxDriver();

        driver.manage().window().maximize();

        // Set up test name and Logger
        setCurrentThreadName();
        String testName = ctx.getCurrentXmlTest().getName();
        log = LogManager.getLogger(testName);

    }

    // waiting for Web Element to be visible
    protected void waitForElement(WebDriver driver, WebElement element){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(element));
        }
        catch(TimeoutException e){
            System.err.println(element + "Couldn't find element after waiting" + "\n");
            e.printStackTrace();
        }
        catch (NoSuchElementException e){
            System.err.println("Unable to locate element with xpath: " + element + "\n");
            e.printStackTrace();
        }
    }

    // waiting for url to change after clicking a Web Element and asserting if it redirects to the expected URL
    protected void assertUrls(WebDriver driver, String expectedUrl) {
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            log.info("URL verification is complete.");
        }
        catch(TimeoutException e){
            System.err.println("Could not find the required URL after waiting");
            System.err.println("Expected to be redirected to: " + expectedUrl);
            System.err.println("Instead redirected to: " + driver.getCurrentUrl() + "\n");
            e.printStackTrace();
        }
    }

    protected void assertPartialUrls(WebDriver driver, String expectedUrl) {
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains(expectedUrl));
            log.info("URL verification is complete.");
        }
        catch(TimeoutException e){
            System.err.println("Could not find the required URL after waiting");
            System.err.println("Expected to be redirected to: " + expectedUrl);
            System.err.println("Instead redirected to: " + driver.getCurrentUrl() + "\n");
            e.printStackTrace();
        }
    }

    // Clicks on a Web Element that redirects to another page
    protected void clickOnElementAndAssertUrl(WebDriver driver, WebElement element,String text, String expectedUrl) {
        clickAndDisplayText(driver, element, text);
        assertUrls(driver, expectedUrl);
    }

    protected void clickAndDisplayText(WebDriver driver, WebElement element, String textToBeDisplayed) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
            log.info("Clicked " + textToBeDisplayed + "\n");
        }
        catch (NoSuchElementException e){
            log.info("Unable to locate element with xpath: " + element + "\n");
            e.printStackTrace();
        }
        catch (ElementClickInterceptedException e){
            System.err.println(textToBeDisplayed + " can not be clicked" + "\n");
            e.printStackTrace();
        }
        catch (ElementNotInteractableException e) {
            System.err.println(textToBeDisplayed + " is not interactable with" + "\n");
            e.printStackTrace();
        }
    }

    protected void checkButtonState(WebElement element, String desiredState){
        String message;
        try{
            if (element.isEnabled()) {
                message = element.getText() + " is enabled and clickable";
                if (desiredState == "enabled") {
                    log.info(message);
                } else if (desiredState == "disabled") {
                    System.err.println(message);
                }
            } else {
                message = element.getText() + " is disabled and unclickable";
                if (desiredState == "enabled") {
                    System.err.println(message);
                } else if (desiredState == "disabled") {
                    log.info(message);
                }
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    protected void checkFieldValidation(WebDriver driver, WebElement element, WebElement errorElement, String text){
        try {
            clickAndDisplayText(driver, element, element.getAttribute("placeholder"));
            sendKeys(driver, element, text);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].blur();", element);
            waitForElement(driver, errorElement);
            if (errorElement.isDisplayed()){
                log.info(errorElement.getText());
            }
            else {
                System.err.println(element.getAttribute("placeholder") + " Validation is not working");
            }
            clearData(element);
        }
        catch(NoSuchElementException e){
            e.printStackTrace();
        }
        catch (ElementNotInteractableException e) {
            System.err.println(element + "is not interactable");
        }
    }

    // Clear content to an element
    protected void clearData(WebElement element) {
        try {
            element.clear();
            log.info("Data is deleted for: " + element.getAttribute("id"));
        } catch (NoSuchElementException e) {
            System.err.println("Unable to find element" + element);
        } catch (InvalidElementStateException e){
            System.err.println("Unable to clear the element that can not be edited " + element);
        }
    }

    // Type content to an element
    protected void sendKeys(WebDriver driver, WebElement element, String data) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
            element.sendKeys(data);
            log.info("Added to the " + element.getAttribute("id") + ": " + data);
        } catch (NoSuchElementException e) {
            System.err.println("Unable to find element" + element);
        } catch (ElementNotInteractableException e) {
            System.err.println(element + "is not interactable");
        }
    }


    @AfterMethod(alwaysRun = true)
    protected void tearDown() {
        // Closing driver
        log.info("[Closing driver]");
        driver.quit();
    }

    // Sets thread name which includes thread id
    private void setCurrentThreadName() {
        Thread thread = Thread.currentThread();
        String threadName = thread.getName();
        String threadId = String.valueOf(thread.getId());
        if (!threadName.contains(threadId)) {
            thread.setName(threadName + " " + threadId);
        }
    }
}
