package TestCases;

import BaseTest.baseTest;
import org.testng.annotations.Test;
import pages.registerPage;

public class registerTest extends baseTest {
    public static final String REGISTER_URL = "registerUrl";
    public static final String BUTTON_STATE_DISABLED = "disabled";
    public static final String BUTTON_STATE_ENABLED = "enabled";

    @Test
    public void registerTest(){
        registerPage register = new registerPage(driver);

        driver.get(properties.getProperty(REGISTER_URL));

        register.clickOnAcceptButton();
        register.checkSignUpState(BUTTON_STATE_DISABLED);
        register.checkFormValidation();
        register.fillSignUpForm();
        register.checkSignUpState(BUTTON_STATE_ENABLED);
        register.clickSignUp();



    }

}
