package TestCases;

import BaseTest.baseTest;
import org.testng.annotations.Test;
import pages.uploadFilePage;

public class uploadFileTest extends baseTest {
    public static final String UPLOAD_URL = "uploadUrl";
    public static final String TERMS_AND_SERVICES_TEXT1 = "termsAndServicesText1";
    public static final String TERMS_AND_SERVICES_TEXT2 = "termsAndServicesText2";

    @Test
    public void uploadFile(){
        uploadFilePage uploadFile = new uploadFilePage(driver);

        driver.get(properties.getProperty(UPLOAD_URL));

        uploadFile.uploadFile();
        uploadFile.checkTermsAndServicesBehaviour();
        uploadFile.submitButton();
        uploadFile.isMessageDisplayed(properties.getProperty(TERMS_AND_SERVICES_TEXT1), properties.getProperty(TERMS_AND_SERVICES_TEXT2));
    }
}
