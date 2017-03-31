package com.company.secure.selenium.tests;

import static com.company.secure.utils.common.StringUtils.ALERT_FAILURE_CLASS;
import static com.company.secure.utils.common.StringUtils.BASE_URL;
import static com.company.secure.utils.common.StringUtils.BUTTON_CLASS;
import static com.company.secure.utils.common.StringUtils.CLASS_MY_COMPANY_ACCOUNT;
import static com.company.secure.utils.common.StringUtils.CREATE_ACCOUNT_CLASS;
import static com.company.secure.utils.common.StringUtils.CSS_CONFIRMEMAIL_LINE2;
import static com.company.secure.utils.common.StringUtils.CSS_EMAIL_EMPTY_MSG;
import static com.company.secure.utils.common.StringUtils.CSS_PASSWORD_CONFIRM_EMPTY_MSG;
import static com.company.secure.utils.common.StringUtils.CSS_PASSWORD_EMPTY_MSG;
import static com.company.secure.utils.common.StringUtils.EMAIL_REGEX;
import static com.company.secure.utils.common.StringUtils.ERR_CONTAINER_NOT_VISIBLE;
import static com.company.secure.utils.common.StringUtils.ERR_EMPTY_FIELD;
import static com.company.secure.utils.common.StringUtils.ERR_TEXT_EMAILID_NOTINMAIL;
import static com.company.secure.utils.common.StringUtils.ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED;
import static com.company.secure.utils.common.StringUtils.FORGOT_GMAIL_PWD;
import static com.company.secure.utils.common.StringUtils.FORGOT_PWD_EMAIL_ID;
import static com.company.secure.utils.common.StringUtils.ID_CONFIRM_PASSWORD;
import static com.company.secure.utils.common.StringUtils.ID_EMAIL;
import static com.company.secure.utils.common.StringUtils.ID_PASSWORD;
import static com.company.secure.utils.common.StringUtils.INVALID_EMAIL_ID_1;
import static com.company.secure.utils.common.StringUtils.LINK_IN_EMAIL;
import static com.company.secure.utils.common.StringUtils.MSG_CREATE_COMPANY_ACCOUNT;
import static com.company.secure.utils.common.StringUtils.MSG_FAILURE_CONFIRMEMAIL_3;
import static com.company.secure.utils.common.StringUtils.MSG_HOME_PAGE_TITLE;
import static com.company.secure.utils.common.StringUtils.MSG_MY_ZEN_MATE_ACCOUNT_TEXT;
import static com.company.secure.utils.common.StringUtils.MSG_PASSWORD_LENGTH_CHECK;
import static com.company.secure.utils.common.StringUtils.MSG_SUCCESS_CONFIRMEMAIL_1;
import static com.company.secure.utils.common.StringUtils.MSG_SUCCESS_CONFIRMEMAIL_2;
import static com.company.secure.utils.common.StringUtils.MSG_THE_PASSWORDS_MUST_MATCH;
import static com.company.secure.utils.common.StringUtils.MSG_VALID_EMAIL_ADDRESS;
import static com.company.secure.utils.common.StringUtils.SMALL_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.SUB_TEXT_SIGNUP;
//import static com.company.secure.utils.common.StringUtils.NONEXISTING_EMAIL_ID_2;
import static com.company.secure.utils.common.StringUtils.VALID_EMAIL_USER_1;
import static com.company.secure.utils.common.StringUtils.VALID_EMAIL_USER_2;
import static com.company.secure.utils.common.StringUtils.VALID_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.VALID_PASSWORD_2;
import static com.company.secure.utils.common.StringUtils.XPATH_CREATE_NEW_ACCOUNT_TEXT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.company.secure.utils.common.SeleniumUtils;

/**
 * Test for checking user signup to company website. Checks both positive and
 * negative scenarios for the same.
 * 
 * @author saritharajagopal
 *
 */
public class UserSignUp {

    /**
     * Checks use sign-up for negative scenarios. Uses internally
     * {@link com.company.secure.utils.common.StringUtils}
     * @throws IOException 
     * @throws SecurityException 
     */
    @Test(description = "Tests negative user signup scenarios")
    public void invalidSignUpTest() throws SecurityException, IOException {
	driver.get(BASE_URL);
	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
	WebElement dynamicLinkElementtest = SeleniumUtils.findClikableElementByXpathWithWait(driver,
		XPATH_CREATE_NEW_ACCOUNT_TEXT);

	dynamicLinkElementtest.sendKeys(Keys.RETURN);

	String[][] invalidCombinations = invalidCombinations();

	WebElement email = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_EMAIL);
	WebElement pass = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_PASSWORD);
	WebElement passConfirm = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_CONFIRM_PASSWORD);
	String firstLineWelcome = SeleniumUtils.findElementByClassName(driver, CREATE_ACCOUNT_CLASS).getText();
	for (String[] row : invalidCombinations) {
	    email.clear();
	    pass.clear();
	    passConfirm.clear();
	    email.sendKeys(row[0]);
	    pass.sendKeys(row[1]);
	    passConfirm.sendKeys(row[2]);
	    logger.info("UserSignUp::invalidSignUpTest::Testing username: " + row[0] + " password:" + row[1]
		    + " confirm password:" + row[2]);
	    SeleniumUtils.findElementByClassName(driver, BUTTON_CLASS).click();

	    if (row[0].isEmpty()) {
		String errorTextConfirmPassword = SeleniumUtils
			.findPresentElementByCssWithWait(driver, CSS_EMAIL_EMPTY_MSG).getText();
		Assert.assertEquals(errorTextConfirmPassword, ERR_EMPTY_FIELD);
	    }
	    if (row[1].isEmpty()) {
		String errorTextConfirmPassword = SeleniumUtils
			.findPresentElementByCssWithWait(driver, CSS_PASSWORD_EMPTY_MSG).getText();
		Assert.assertEquals(errorTextConfirmPassword, ERR_EMPTY_FIELD);
	    }
	    if (row[2].isEmpty()) {
		String errorTextConfirmPassword = SeleniumUtils
			.findPresentElementByCssWithWait(driver, CSS_PASSWORD_CONFIRM_EMPTY_MSG).getText();
		Assert.assertEquals(errorTextConfirmPassword, ERR_EMPTY_FIELD);
	    }

	    if (row[1] != row[2]) {
		if (!row[2].isEmpty()) {
		    String errorTextConfirmPassword = SeleniumUtils
			    .findPresentElementByCssWithWait(driver, CSS_PASSWORD_CONFIRM_EMPTY_MSG).getText();
		    Assert.assertEquals(errorTextConfirmPassword, MSG_THE_PASSWORDS_MUST_MATCH);
		}
	    }
	    if (!row[0].isEmpty() && !row[0].matches(EMAIL_REGEX)) {
		String errorTextInvalidEmail = SeleniumUtils
			.findPresentElementByCssWithWait(driver, CSS_EMAIL_EMPTY_MSG).getText();
		Assert.assertEquals(errorTextInvalidEmail, MSG_VALID_EMAIL_ADDRESS);
	    }

	    if (!row[1].isEmpty() && row[1].length() < 6) {

		String errorTextPasswordLength = SeleniumUtils
			.findPresentElementByCssWithWait(driver, CSS_PASSWORD_EMPTY_MSG).getText();
		Assert.assertEquals(errorTextPasswordLength, MSG_PASSWORD_LENGTH_CHECK);
	    }
	    if (!row[0].isEmpty() && !row[1].isEmpty() && !row[2].isEmpty()) {

		if (row[0] == VALID_EMAIL_USER_1) {
		    WebElement errorContainerUserexists = SeleniumUtils.findPresentElementByClassNameWithWait(driver,
			    ALERT_FAILURE_CLASS);
		    Assert.assertTrue(errorContainerUserexists.isDisplayed(), ERR_CONTAINER_NOT_VISIBLE);
		}
	    }
	    Assert.assertEquals(firstLineWelcome, MSG_CREATE_COMPANY_ACCOUNT);
	}

    }

    private String[][] invalidCombinations() {
	String invalidcombinations[][] = {

		{ VALID_EMAIL_USER_1, VALID_PASSWORD_1, VALID_PASSWORD_1 }, // already
		// existing
		// user
		{ VALID_EMAIL_USER_2, SMALL_PASSWORD_1, SMALL_PASSWORD_1 }, // small
		// passwords
		{ VALID_EMAIL_USER_1, VALID_PASSWORD_1, VALID_PASSWORD_2 }, // password
		// not
		// equal

		{ "", "", "" }, // all 3 empty

		{ VALID_EMAIL_USER_1, "", "" }, // only one entry
		{ "", VALID_PASSWORD_1, "" }, // only one entry
		{ "", "", VALID_PASSWORD_1 }, // only one entry

		{ "", VALID_PASSWORD_1, VALID_PASSWORD_1 }, // two entry
		{ VALID_EMAIL_USER_1, VALID_PASSWORD_1, "" }, // two entry
		{ VALID_EMAIL_USER_1, "", VALID_PASSWORD_1 }, // two entry
		{ INVALID_EMAIL_ID_1, VALID_PASSWORD_1, VALID_PASSWORD_1 }// invlaid
		// email
		// id
	};
	return invalidcombinations;
    }

    /**
     * Checks user login for positive scenarios. Uses internally
     * {@link com.company.secure.utils.common.StringUtils}
     * @throws Exception 
     */
    @Test(description = "Tests positive user sign up scenarios")
    public void validSignUpTest() throws Exception {
	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
	Calendar cal = Calendar.getInstance();
	String nonexistingEmailId2="companyqa+"+cal.getTimeInMillis()+ "@gmail.com";
	driver.get(BASE_URL);
	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	WebElement dynamicLinkElementtest = SeleniumUtils.findClikableElementByXpathWithWait(driver,
		XPATH_CREATE_NEW_ACCOUNT_TEXT);
	dynamicLinkElementtest.sendKeys(Keys.RETURN);
	String validcombinations[][] = { { nonexistingEmailId2, VALID_PASSWORD_1, VALID_PASSWORD_1 }// new email
	};
	WebElement email = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_EMAIL);
	WebElement pass = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_PASSWORD);
	WebElement passConfirm = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_CONFIRM_PASSWORD);

	for (String[] row : validcombinations) {
	    email.clear();
	    pass.clear();
	    passConfirm.clear();
	    email.sendKeys(row[0]);
	    pass.sendKeys(row[1]);
	    passConfirm.sendKeys(row[2]);
	    logger.info("UserSignUp::validSignUpTest::Testing username: " + row[0] + " password:" + row[1]
		    + " confirm password:" + row[2]);
	    SeleniumUtils.findElementByClassName(driver, BUTTON_CLASS).click();
	    String newAccountElement = SeleniumUtils.findPresentElementByClassNameWithWait(driver, CLASS_MY_COMPANY_ACCOUNT)
		    .getText();
	    Assert.assertEquals(newAccountElement, MSG_MY_ZEN_MATE_ACCOUNT_TEXT);
	    SeleniumUtils.logout(driver,row[0]);  
	}
	Date datesent = new Date();
	long timesent = datesent.getTime();
	Timestamp tssenttime = new Timestamp(timesent);
	//System.out.println("UserSignUp::validSignUpTest::Testing received mail timestamp: " + tssenttime);
	driver.manage().timeouts().pageLoadTimeout(30000, TimeUnit.MILLISECONDS);
	Properties props = System.getProperties();
	props.setProperty("mail.store.protocol", "imaps");
	Session session = Session.getDefaultInstance(props, null);
	Store store = session.getStore("imaps");
	store.connect("imap.gmail.com", FORGOT_PWD_EMAIL_ID, FORGOT_GMAIL_PWD);
	Folder folder = store.getFolder("INBOX");
	folder.open(Folder.READ_ONLY);
	Message[] messages = null;
	boolean isMailFound = false;
	Message mailFromCompany = null;

	// Search for mail from company
	for (int i = 0; i < 10; i++) {
	    messages = folder.search(new SubjectTerm(SUB_TEXT_SIGNUP), folder.getMessages());
	    Date currentdate = new Date();
	    long currenttime = currentdate.getTime();
	    Timestamp currentts = new Timestamp(currenttime);

	    // Search for unread mail from company
	    // This is to avoid using the mail for which
	    // Registration is already done
	    for (Message mail : messages) {
		mailFromCompany = mail;
		if (!mail.isSet(Flags.Flag.SEEN) && mail.getReceivedDate().before(currentts)
			&& mail.getReceivedDate().after(tssenttime)) {
		    logger.info("UserSignUp::validSignUpTest::Received Mail Timestamp: "
			    + mail.getReceivedDate());
		    isMailFound = true;
		    break;
		}
	    }
	    if (isMailFound) {
		break;
	    }
	    Thread.sleep(7000);
	}

	// Test fails if no unread mail was found from company
	if (!isMailFound) {
	    throw new Exception("Could not find new mail");

	    // Read the content of mail and launch registration URL
	} else {
	    String line;
	    StringBuffer buffer = new StringBuffer();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromCompany.getInputStream()));
	    while ((line = reader.readLine()) != null) {
		buffer.append(line); 
	    }
	    Assert.assertTrue(buffer.toString().contains(nonexistingEmailId2), ERR_TEXT_EMAILID_NOTINMAIL);
	    /*System.out.println(buffer.toString().contains(nonexistingEmailId2));
	    System.out.println(buffer.toString());*/
	   /* int intStartIndex = buffer.indexOf("https");
	    int intEndIndex = buffer.indexOf("If");*/
	    int intStartIndex = buffer.indexOf(LINK_IN_EMAIL);
		   for(int testval=0;testval<1;testval++){
		    
			//System.out.println(intStartIndex);
			intStartIndex=buffer.indexOf(LINK_IN_EMAIL,intStartIndex+1);
		    }
		     int intEndIndex = buffer.indexOf("\"",intStartIndex+1);
		    
		    //System.out.println("buffer:\n"+buffer);
		    String linkSignUp = buffer.substring(intStartIndex,intEndIndex).replace("=", "");
		    //System.out.println("linkSignUp "+linkSignUp);
		    driver.get(linkSignUp);
		    Thread.sleep(2000);
		    String SignUpSuccess=SeleniumUtils.findElementByTagName(driver, "h3").getText();
		    Assert.assertEquals(SignUpSuccess, MSG_SUCCESS_CONFIRMEMAIL_1);
		    String SignUpSuccessMessage=SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_CONFIRMEMAIL_LINE2).getText();
		    Assert.assertTrue(SignUpSuccessMessage.contains(MSG_SUCCESS_CONFIRMEMAIL_2));
		    
		    driver.get(linkSignUp);
		    String SignUpFailure=SeleniumUtils.findElementByTagName(driver, "h3").getText();
		    Assert.assertEquals(SignUpFailure, MSG_SUCCESS_CONFIRMEMAIL_1);
		    String SignUpFailureMessage=SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_CONFIRMEMAIL_LINE2).getText();
		    Assert.assertTrue(SignUpFailureMessage.contains(MSG_FAILURE_CONFIRMEMAIL_3));
		    Thread.sleep(2000);
		   /* WebElement accountLink = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT.toUpperCase())));
		    accountLink.sendKeys(Keys.ENTER);*/
		   
		    //SeleniumUtils.logout(driver, nonexistingEmailId2);
		
		    
	Thread.sleep(2000);
	
    }}
    
    WebDriver driver;
    @Parameters({ "browser" })
    @BeforeTest(description = "Navigates to company website")
    /*public void setup() {
	driver = new FirefoxDriver();
	driver.manage().window().maximize();

    }*/
    public void setup(String browser) throws Exception {
	
	try {
	if (browser.equalsIgnoreCase("Firefox")) {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	} 
	   
	else if(browser.equalsIgnoreCase("Chrome"))
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
	driver.quit();
    }
}

