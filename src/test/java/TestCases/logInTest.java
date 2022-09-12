package TestCases;

import BaseTest.baseTest;
import org.testng.annotations.Test;
import pages.logInPage;

public class logInTest extends baseTest {
    public static final String LOG_IN_URL = "logInUrl";
    public static final String BUTTON_STATE_ENABLED = "enabled";
    public static final String TOAST_ERROR_MESSAGE = "toastErrorMessage";

    @Test
    public void logInTest(){
        logInPage logIn = new logInPage(driver);

        driver.get(properties.getProperty(LOG_IN_URL));

        logIn.clickOnAcceptButton();
        logIn.checkLogInState(BUTTON_STATE_ENABLED);
        logIn.fillIncorrectInfo();
        logIn.checkLogInState(BUTTON_STATE_ENABLED);
        logIn.checkInvalidLogIn();
        logIn.checkErrorDisplayed(properties.getProperty(TOAST_ERROR_MESSAGE));
        logIn.fillCorrectInfo();
        logIn.checkValidLogIn();


    }
}
