package com.company.secure.selenium.tests;

import static com.company.secure.utils.common.StringUtils.ALERT_SUCCESS_CLASS;
import static com.company.secure.utils.common.StringUtils.BASE_URL;
import static com.company.secure.utils.common.StringUtils.BUTTON_CLASS;
import static com.company.secure.utils.common.StringUtils.CHANGE_PWD_NEW_VALID_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.CHANGE_PWD_OLD_VALID_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.CHANGE_PWD_VALID_EMAIL_USER_1;
import static com.company.secure.utils.common.StringUtils.CSS_ACCOUNT_SETTING;
import static com.company.secure.utils.common.StringUtils.CSS_CHANGE_PWD;
import static com.company.secure.utils.common.StringUtils.ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED;
import static com.company.secure.utils.common.StringUtils.LINK_MYACCOUNT;
import static com.company.secure.utils.common.StringUtils.LINK_PASSWORD;
import static com.company.secure.utils.common.StringUtils.MSG_HOME_PAGE_TITLE;
import static com.company.secure.utils.common.StringUtils.SMALL_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.TEXT_ACCNT_SETTING;
import static com.company.secure.utils.common.StringUtils.TEXT_CHANGE_PASSWORD;
import static com.company.secure.utils.common.StringUtils.TEXT_SUCCESS;
import static com.company.secure.utils.common.StringUtils.VALID_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.VALID_PASSWORD_2;
import static com.company.secure.utils.common.StringUtils.XPATH_CONFIRM_NEW_PWD;
import static com.company.secure.utils.common.StringUtils.XPATH_CURRENT_PWD;
import static com.company.secure.utils.common.StringUtils.XPATH_NEW_PWD;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.company.secure.utils.common.SeleniumUtils;

/**
 * Test for checking if the user can change his password. Checks for both
 * positive and negative scenarios.
 * 
 * @author saritharajagopal
 *
 */
public class UserChangePassword {

    /**
     * Checks if users can change their passwords for negative scenarios. Uses
     * internally {@link com.company.secure.utils.common.StringUtils}
     * @throws IOException 
     * @throws SecurityException 
     * 
     */

    @Test(description = "Tests negative user change password scenarios")
    public void invalidChangePasswordTest() throws InterruptedException, SecurityException, IOException {
	driver.get(BASE_URL);
	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
	String[][] invalidCombinationspassword = invalidCombinationspassword();
	SeleniumUtils.login(driver, CHANGE_PWD_VALID_EMAIL_USER_1, CHANGE_PWD_OLD_VALID_PASSWORD_1);
	WebElement account = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT)));
	account.sendKeys(Keys.ENTER);
	WebElement passwordchange = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_PASSWORD)));
	passwordchange.click();
	WebElement passwordcurrent = driver.findElement(By.xpath(XPATH_CURRENT_PWD));
	WebElement newpassword = driver.findElement(By.xpath(XPATH_NEW_PWD));
	WebElement confirmNewPassword = driver.findElement(By.xpath(XPATH_CONFIRM_NEW_PWD));
	for (String[] row : invalidCombinationspassword) {

	    /*//WebElement passwordcurrent = (new WebDriverWait(driver, 10))
		    .until(ExpectedConditions.elementToBeClickable(By.className("form-group")));
	    //passwordcurrent.clear();
	   // passwordcurrent.sendKeys(row[0] + "\t" + row[1] + "\t" + row[2]);
*/		 
	    passwordcurrent.clear();
	    newpassword.clear(); 
	    confirmNewPassword.clear(); 
	    
	    logger.info("UserChangePassword::invalidChangePasswordTest::Testing password: " + row[0]
		    + " new password:" + row[1] + " confirm new password:" + row[2]);
	    
	    passwordcurrent.sendKeys(row[0]);
	    newpassword.sendKeys(row[1]);
	    confirmNewPassword.sendKeys(row[2]);
	    
	    confirmNewPassword.sendKeys(Keys.TAB);
	    Thread.sleep(2000);
	    SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS).click();
	    String accountSetting = SeleniumUtils.findPresentElementByCssWithWait(driver,CSS_ACCOUNT_SETTING).getText();
	    String changePwdText = SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_CHANGE_PWD).getText();
	    Assert.assertEquals(accountSetting, TEXT_ACCNT_SETTING);
	    Assert.assertEquals(changePwdText, TEXT_CHANGE_PASSWORD);
	    //Important Note:These can be checked only after the error message is corrected.
	    /*String conttext = SeleniumUtils.findPresentElementByClassNameWithWait(driver, "alert-danger").getText();
	    Assert.assertTrue(conttext.contains("The password you provided is not correct"));*/
	    //passwordcurrent.sendKeys("''\t''\t''");
	}
	SeleniumUtils.logout(driver, CHANGE_PWD_VALID_EMAIL_USER_1);
    }

    private String[][] invalidCombinationspassword() {
	String invalidCombinationspassword[][] = {

		{ VALID_PASSWORD_1, SMALL_PASSWORD_1, SMALL_PASSWORD_1 }, // small
		// passwords
		{ VALID_PASSWORD_1, SMALL_PASSWORD_1, VALID_PASSWORD_2 }, // password
		// not
		// equal
		{ SMALL_PASSWORD_1, SMALL_PASSWORD_1, SMALL_PASSWORD_1 }, 
		 // all
		// 3
		// empty

		{ VALID_PASSWORD_1, "", "" }, // only one entry
		{ "", VALID_PASSWORD_1, "" }, // only one entry
		{ "", "", VALID_PASSWORD_1 }, // only one entry

		{ "", VALID_PASSWORD_1, VALID_PASSWORD_1 }, // two entry
		{ VALID_PASSWORD_1, VALID_PASSWORD_2, "" }, // two entry
		{ VALID_PASSWORD_1, "", VALID_PASSWORD_2 }, // two entry
		// passowrd
		{ "", "", "" }//all three empty
	};
	return invalidCombinationspassword;
    }

    /**
     * Checks if users can change their passwords for positive scenarios. Uses
     * internally {@link com.company.secure.utils.common.StringUtils}
     * @throws InterruptedException 
     * @throws IOException 
     * @throws SecurityException 
     */

    @Test(description = "Tests positive user change password scenarios")
    public void validUserChangePassword() throws InterruptedException, SecurityException, IOException {
	driver.get(BASE_URL);
	/*
	 * WebElement email =
	 * SeleniumUtils.findPresenceofElementByIdWithWait(driver,ID_EMAIL);
	 * WebElement pass
	 * =SeleniumUtils.findPresenceofElementByIdWithWait(driver,
	 * ID_PASSWORD); email.clear(); pass.clear();
	 * email.sendKeys(CHANGE_PWD_VALID_EMAIL_USER_1);
	 * pass.sendKeys(CHANGE_PWD_OLD_VALID_PASSWORD_1);
	 */
	SeleniumUtils.login(driver, CHANGE_PWD_VALID_EMAIL_USER_1, CHANGE_PWD_OLD_VALID_PASSWORD_1);
	// SeleniumUtils.findElementByClassName(driver, "btn-primary").click();

	WebElement account = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT)));
	account.sendKeys(Keys.ENTER);
	WebElement passwordchange = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_PASSWORD)));
	passwordchange.click();
	// WebElement passwordcurrent= (new
	// WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.className("form-group")));
	//WebElement passwordcurrent = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.className(INPUT_CURRENT_PWD)));
	WebElement passwordcurrent = driver.findElement(By.xpath(XPATH_CURRENT_PWD));
	WebElement newpassword = driver.findElement(By.xpath(XPATH_NEW_PWD));
	WebElement confirmNewPassword = driver.findElement(By.xpath(XPATH_CONFIRM_NEW_PWD));
	passwordcurrent.clear();
	//passwordcurrent.sendKeys(CHANGE_PWD_OLD_VALID_PASSWORD_1 + "\t" + CHANGE_PWD_NEW_VALID_PASSWORD_1 + "\t"+ CHANGE_PWD_NEW_VALID_PASSWORD_1);
	passwordcurrent.sendKeys(CHANGE_PWD_OLD_VALID_PASSWORD_1);
	newpassword.clear();
	newpassword.sendKeys(CHANGE_PWD_NEW_VALID_PASSWORD_1);
	confirmNewPassword.clear();
	confirmNewPassword.sendKeys(CHANGE_PWD_NEW_VALID_PASSWORD_1);
	confirmNewPassword.sendKeys(Keys.TAB);
	Thread.sleep(2000);
	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
	logger.info("UserChangePassword::validChangePasswordTest::Testing password: "
		+ CHANGE_PWD_OLD_VALID_PASSWORD_1 + " new password:" + CHANGE_PWD_NEW_VALID_PASSWORD_1
		+ " confirm new password:" + CHANGE_PWD_NEW_VALID_PASSWORD_1);
	WebElement buttonSetPwd = SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS);
	buttonSetPwd.sendKeys(Keys.ENTER);
	String conttext = SeleniumUtils.findPresentElementByClassNameWithWait(driver, ALERT_SUCCESS_CLASS).getText();
	Assert.assertTrue(conttext.contains(TEXT_SUCCESS));
	SeleniumUtils.logout(driver, CHANGE_PWD_VALID_EMAIL_USER_1);
	SeleniumUtils.login(driver, CHANGE_PWD_VALID_EMAIL_USER_1, CHANGE_PWD_NEW_VALID_PASSWORD_1);
	SeleniumUtils.logout(driver, CHANGE_PWD_VALID_EMAIL_USER_1);
	SeleniumUtils.incorrectlogin(driver, CHANGE_PWD_VALID_EMAIL_USER_1, CHANGE_PWD_OLD_VALID_PASSWORD_1);
    }

    WebDriver driver;
    @Parameters({ "browser" })
    @BeforeTest(description = "Navigates to company website")
   /*public void setup() {
	driver = new OperaDriver();
	System.setProperty("webdriver.chrome.driver","/Applications/selenium/chromedriver");
	//driver.manage().window().maximize();

    }*/
    public void setup(String browser) throws Exception {
	
	try {
	if (browser.equalsIgnoreCase("Firefox")) {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	} 
	   
	else if (browser.equalsIgnoreCase("Chrome"))
	{	
	    	System.setProperty("webdriver.chrome.driver","/Applications/selenium/chromedriver");
		driver =  new ChromeDriver();
		driver.manage().window().maximize();
	} 
	else{
	    
            //If no browser passed throw exception
	    	throw new Exception("Browser is not correct");
 
        }
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    } 
	catch (WebDriverException e) {
	System.out.println(e.getMessage());
	}
}	

    @AfterTest(description = "Closes the browser")
    public void teardown() {
	SeleniumUtils.login(driver, CHANGE_PWD_VALID_EMAIL_USER_1, CHANGE_PWD_NEW_VALID_PASSWORD_1);
	WebElement account = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT)));
	account.sendKeys(Keys.ENTER);
	WebElement passwordchange = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_PASSWORD)));
	passwordchange.click();
	WebElement passwordcurrent = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.className("form-group")));
	passwordcurrent.clear();
	passwordcurrent.sendKeys(CHANGE_PWD_NEW_VALID_PASSWORD_1 + "\t" + CHANGE_PWD_OLD_VALID_PASSWORD_1 + "\t"
		+ CHANGE_PWD_OLD_VALID_PASSWORD_1);
	passwordcurrent.sendKeys(Keys.TAB);
	WebElement buttonSetPwd = SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS);
	buttonSetPwd.sendKeys(Keys.ENTER);
	SeleniumUtils.logout(driver, CHANGE_PWD_VALID_EMAIL_USER_1);
	driver.quit();
    }
}

