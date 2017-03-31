package com.company.secure.selenium.tests;
import static com.company.secure.utils.common.StringUtils.ALERT_FAILURE_CLASS;
import static com.company.secure.utils.common.StringUtils.ALERT_SUCCESS_CLASS;
import static com.company.secure.utils.common.StringUtils.BASE_URL;
import static com.company.secure.utils.common.StringUtils.BUTTON_CLASS;
import static com.company.secure.utils.common.StringUtils.CHANGE_EMAIL_GMAILID;
import static com.company.secure.utils.common.StringUtils.CHANGE_EMAIL_VALID_GMAIL_PASSWORD;
import static com.company.secure.utils.common.StringUtils.CHANGE_EMAIL_VALID_NONEXISTING_EMAILID;
//import static com.company.secure.utils.common.StringUtils.CHANGE_EMAIL_VALID_OLD_EMAILID;
import static com.company.secure.utils.common.StringUtils.CHANGE_EMAIL_VALID_PASSWORD;
import static com.company.secure.utils.common.StringUtils.CSS_ACCOUNT_SETTING;
import static com.company.secure.utils.common.StringUtils.CSS_CHANGE_PWD;
import static com.company.secure.utils.common.StringUtils.CSS_CONFIRMEMAIL_LINE2;
import static com.company.secure.utils.common.StringUtils.ERR_TEXT_EMAILID_NOTINMAIL;
import static com.company.secure.utils.common.StringUtils.ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED;
import static com.company.secure.utils.common.StringUtils.ID_CONFIRM_PASSWORD;
import static com.company.secure.utils.common.StringUtils.ID_EMAIL;
import static com.company.secure.utils.common.StringUtils.ID_PASSWORD;
import static com.company.secure.utils.common.StringUtils.INPUT_CURRENT_PWD;
import static com.company.secure.utils.common.StringUtils.INVALID_EMAIL_ID_1;
import static com.company.secure.utils.common.StringUtils.INVALID_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.LINK_IN_EMAIL;
import static com.company.secure.utils.common.StringUtils.LINK_MYACCOUNT;
import static com.company.secure.utils.common.StringUtils.MSG_FAILURE_CONFIRMEMAIL_3;
import static com.company.secure.utils.common.StringUtils.MSG_HOME_PAGE_TITLE;
//import static com.company.secure.utils.common.StringUtils.SUB_TEXT_CHANGE_EMAIL;
import static com.company.secure.utils.common.StringUtils.MSG_PARTIAL_CONFIRMATIONEMAIL;
import static com.company.secure.utils.common.StringUtils.MSG_SUCCESS_CONFIRMEMAIL_1;
import static com.company.secure.utils.common.StringUtils.MSG_SUCCESS_CONFIRMEMAIL_2;
import static com.company.secure.utils.common.StringUtils.SMALL_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.SUB_TEXT_SIGNUP;
import static com.company.secure.utils.common.StringUtils.TEXT_ACCNT_SETTING;
import static com.company.secure.utils.common.StringUtils.TEXT_CHANGE_EMAIL;
import static com.company.secure.utils.common.StringUtils.VALID_EMAIL_USER_1;
import static com.company.secure.utils.common.StringUtils.VALID_EMAIL_USER_2;
import static com.company.secure.utils.common.StringUtils.VALID_PASSWORD_1;
import static com.company.secure.utils.common.StringUtils.VALID_PASSWORD_2;
import static com.company.secure.utils.common.StringUtils.XPATH_CREATE_NEW_ACCOUNT_TEXT;
import static com.company.secure.utils.common.StringUtils.XPATH_CURRENT_PWD;
import static com.company.secure.utils.common.StringUtils.XPATH_EMAIL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
/**
 * Test for checking user change email to company website. Checks both positive and
 * negative scenarios for the same.
 * 
 * @author saritharajagopal
 *
 */
import org.testng.annotations.Test;

import com.company.secure.utils.common.SeleniumUtils;

/**
 * Checks user change email for positive scenarios. Uses internally
 * {@link com.company.secure.utils.common.StringUtils}
 * 
 * @throws Exception
 */
public class UserChangeEmailId {
  @Test(description = "Tests positive user change email scenarios")
  public void validChangeEmail() throws Exception {
      
	
      	driver.get(BASE_URL);
      	//added the below code for signing up with a new account and replacing with company qa gmail address
      	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
      	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
      	WebElement dynamicLinkElementtest = SeleniumUtils.findClikableElementByXpathWithWait(driver,
		XPATH_CREATE_NEW_ACCOUNT_TEXT);
	dynamicLinkElementtest.sendKeys(Keys.RETURN);
	String randomStringOriginal = RandomStringUtils.random(6, false, true);
	String randomStringUpdated = RandomStringUtils.random(6, false, true);
	WebElement email = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_EMAIL);
	WebElement pass = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_PASSWORD);
	WebElement passConfirm = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_CONFIRM_PASSWORD);
	email.clear();
	pass.clear();
	passConfirm.clear();
	//Calendar cal = Calendar.getInstance();
	String nonexistingEmailIdOriginal="companyqa+"+randomStringOriginal+ "@gmail.com";
	logger.info("UserChangeEmailPassword::validChangeEMailTest::Old email id: " + nonexistingEmailIdOriginal);
	email.sendKeys(nonexistingEmailIdOriginal);//CHANGE_EMAIL_VALID_OLD_EMAILID);
	pass.sendKeys(CHANGE_EMAIL_VALID_PASSWORD);
	passConfirm.sendKeys(CHANGE_EMAIL_VALID_PASSWORD);
	//end of the code addition
      	//SeleniumUtils.login(driver,CHANGE_EMAIL_VALID_OLD_EMAILID,CHANGE_EMAIL_VALID_PASSWORD);
	SeleniumUtils.findElementByClassName(driver, BUTTON_CLASS).click();
	
	// SeleniumUtils.findElementByClassName(driver, "btn-primary").click();
	Thread.sleep(9000);
	WebElement account = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT)));
	account.sendKeys(Keys.ENTER);
	WebElement emailLogin = SeleniumUtils.findClikableElementByXpathWithWait(driver, XPATH_EMAIL);
	WebElement currentPwd = SeleniumUtils.findClikableElementByXpathWithWait(driver, XPATH_CURRENT_PWD);
	
	//String emailLoginText=SeleniumUtils.findElementByClassName(driver, "form-group").getAttribute("value");
	//Assert.assertEquals(emailLoginText,CHANGE_EMAIL_VALID_OLD_EMAILID);
	//emailLogin.sendKeys( "\t" + CHANGE_EMAIL_VALID_PASSWORD);
	Thread.sleep(9000);
	//String nonexistingEmailId3="companyqa+"+cal.getTimeInMillis()+ "@gmail.com";
	String nonexistingEmailIdUpdated="companyqa+"+randomStringUpdated+ "@gmail.com";
	logger.info("UserChangeEmailPassword::validChangeEMailTest::New email id: " + nonexistingEmailIdUpdated);
	emailLogin.clear();
	emailLogin.sendKeys(nonexistingEmailIdUpdated);
	currentPwd.sendKeys(CHANGE_EMAIL_VALID_PASSWORD);
	
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS).click();
	String confirmtext = SeleniumUtils.findPresentElementByClassNameWithWait(driver, ALERT_SUCCESS_CLASS).getText();
	Assert.assertTrue(confirmtext.contains(MSG_PARTIAL_CONFIRMATIONEMAIL));
	
	//Code for checking update email
	Date datesent = new Date();
	long timesent = datesent.getTime();
	Timestamp tssenttime = new Timestamp(timesent);
	logger.info("UserChangeEmailPassword::validChangeEMailTest::Testing received mail timestamp: " + tssenttime);
	driver.manage().timeouts().pageLoadTimeout(30000, TimeUnit.MILLISECONDS);
	Properties props = System.getProperties();
	props.setProperty("mail.store.protocol", "imaps");
	Session session = Session.getDefaultInstance(props, null);
	Store store = session.getStore("imaps");
	store.connect("imap.gmail.com", CHANGE_EMAIL_GMAILID,CHANGE_EMAIL_VALID_GMAIL_PASSWORD);
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
		    logger.info("UserChangeEmail::validChangeEmailTest::Received Mail Timestamp: "
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
	    StringBuffer bufferUpdateEmail = new StringBuffer();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromCompany.getInputStream()));
	    while ((line = reader.readLine()) != null) {
		bufferUpdateEmail.append(line); 
	    }
	    Thread.sleep(2000);
	    //System.out.println(bufferUpdateEmail.toString());
	    Assert.assertTrue(bufferUpdateEmail.toString().contains(nonexistingEmailIdUpdated), ERR_TEXT_EMAILID_NOTINMAIL);
	    
	    int intStartIndex = bufferUpdateEmail.indexOf(LINK_IN_EMAIL);
	   for(int testval=0;testval<1;testval++){
	    
		//System.out.println(intStartIndex);
		intStartIndex=bufferUpdateEmail.indexOf(LINK_IN_EMAIL,intStartIndex+1);
	    }
	     int intEndIndex = bufferUpdateEmail.indexOf("\"",intStartIndex+1);
	    
	    //System.out.println("buffer:\n"+bufferUpdateEmail);
	    String linkUpdateButtonContentnew = bufferUpdateEmail.substring(intStartIndex,intEndIndex).replace("=", "");
	    logger.info("linkUpdateButtonContentnew"+linkUpdateButtonContentnew);
	    driver.get(linkUpdateButtonContentnew);
	    Thread.sleep(2000);
	    String SignUpSuccess=SeleniumUtils.findElementByTagName(driver, "h3").getText();
	    Assert.assertEquals(SignUpSuccess, MSG_SUCCESS_CONFIRMEMAIL_1);
	    String SignUpSuccessMessage=SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_CONFIRMEMAIL_LINE2).getText();
	    Assert.assertTrue(SignUpSuccessMessage.contains(MSG_SUCCESS_CONFIRMEMAIL_2));
	    Thread.sleep(2000);
	    
	    //test
	    driver.get(linkUpdateButtonContentnew);
	    String UpdateFailure=SeleniumUtils.findElementByTagName(driver, "h3").getText();
	    Assert.assertEquals(UpdateFailure, MSG_SUCCESS_CONFIRMEMAIL_1);
	    String UpdateFailureMessage=SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_CONFIRMEMAIL_LINE2).getText();
	    Assert.assertTrue(UpdateFailureMessage.contains(MSG_FAILURE_CONFIRMEMAIL_3));
	    Thread.sleep(2000);
	    WebElement accountLink = (new WebDriverWait(driver, 10))
			.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT.toUpperCase())));
	    accountLink.sendKeys(Keys.ENTER);
	    //SeleniumUtils.findClickableElementByLinkTextWithWait(driver, nonexistingEmailIdUpdated.toLowerCase()).click();
	    SeleniumUtils.logout(driver, nonexistingEmailIdUpdated);
	    SeleniumUtils.login(driver,nonexistingEmailIdUpdated,CHANGE_EMAIL_VALID_PASSWORD);
	    SeleniumUtils.logout(driver,nonexistingEmailIdUpdated);
	    SeleniumUtils.incorrectlogin(driver,nonexistingEmailIdOriginal,CHANGE_EMAIL_VALID_PASSWORD);
	    
	   // String UpdateTextNewPage = SeleniumUtils.findPresentElementByCssWithWait(driver, "h2.text-center").getText();
	   // Assert.assertEquals(UpdateTextNewPage, "Welcome back!");
	   /* String UpdateSuccess=SeleniumUtils.findPresentElementByClassNameWithWait(driver, ALERT_SUCCESS_CLASS).getText();
	    Assert.assertTrue(UpdateSuccess.contains(MSG_POSTCONFIRMEMAIL));
	    String dynamicElement = SeleniumUtils.findPresentElementByClassNameWithWait(driver,CLASS_MY_COMPANY_ACCOUNT).getText();
	    Assert.assertEquals(dynamicElement, MSG_MY_ZEN_MATE_ACCOUNT_TEXT);*/
	    
	   // SeleniumUtils.incorrectlogin(driver,CHANGE_EMAIL_VALID_OLD_EMAILID,CHANGE_EMAIL_VALID_PASSWORD);
	   // SeleniumUtils.login(driver,CHANGE_EMAIL_VALID_NEW_EMAILID,CHANGE_EMAIL_VALID_NEW_PASSWORD);
	   /* SeleniumUtils.logout(driver,nonexistingEmailId2);
	    SeleniumUtils.incorrectlogin(driver,nonexistingEmailId2,CHANGE_EMAIL_VALID_PASSWORD);*/
	}
  }
  
  @Test(description = "Tests negative user change email scenarios")
  public void invalidChangeEmailTest() throws InterruptedException, SecurityException, IOException {
	driver.get(BASE_URL);
	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
	
	String[][] invalidCombinationsEmailpassword = invalidCombinationsEmailpassword();
	SeleniumUtils.login(driver, VALID_EMAIL_USER_2, VALID_PASSWORD_2);
	WebElement account = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT)));
	account.sendKeys(Keys.ENTER);
	WebElement emailLoginInvalidCase = driver.findElement(By.xpath(XPATH_EMAIL));
	WebElement pwdLoginInvalidCase = driver.findElement(By.xpath(XPATH_CURRENT_PWD));
		//SeleniumUtils.findElementByClassName(driver, "form-group");
	
	for (String[] row : invalidCombinationsEmailpassword) {

	    emailLoginInvalidCase.clear();
	    //emailLoginInvalidCase.sendKeys(row[0] + "\t" + row[1]);
	    pwdLoginInvalidCase.clear();
	    
	    logger.info("UserChangeEmail::invalidChangeEmailTest::Testing Change Email: " + row[0]
		    + " Current Password:" + row[1]);
	    emailLoginInvalidCase.sendKeys(row[0]);
	    pwdLoginInvalidCase.sendKeys(row[1]);
	    pwdLoginInvalidCase.sendKeys(Keys.TAB);
	    Thread.sleep(1000);
	    SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS).click();
	    String accountSetting = SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_ACCOUNT_SETTING).getText();
	    String changePwdText = SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_CHANGE_PWD).getText();
	    Assert.assertEquals(accountSetting, TEXT_ACCNT_SETTING);
	    Assert.assertEquals(changePwdText, TEXT_CHANGE_EMAIL);
	    
	   // emailLoginInvalidCase.sendKeys("''\t''");
	   /* emailLoginInvalidCase.clear();
	    emailLoginInvalidCase.sendKeys(Keys.TAB);
	    emailLoginInvalidCase.clear();*/
	}
	SeleniumUtils.logout(driver,VALID_EMAIL_USER_2);
	
  }
	 private String[][] invalidCombinationsEmailpassword() {
		String invalidCombinationspassword[][] = {
			{INVALID_EMAIL_ID_1,INVALID_PASSWORD_1},
			{"",""},
			{VALID_EMAIL_USER_1,""},
			{"",VALID_PASSWORD_1}
		};
		return invalidCombinationspassword;
	 }
 @Test(description = "Tests negative user change email scenarios for existing Company accounts")
	    public void invalidUserChangeExistingEmail() throws SecurityException, IOException {
		driver.get(BASE_URL);
		SeleniumUtils.login(driver, VALID_EMAIL_USER_2, VALID_PASSWORD_2);
		Logger logger = Logger.getLogger("MyLog");
		SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
		WebElement account = (new WebDriverWait(driver, 10))
			.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT)));
		account.sendKeys(Keys.ENTER);
		WebElement emailLoginInvalidCase = SeleniumUtils.findElementByClassName(driver, INPUT_CURRENT_PWD);
		    emailLoginInvalidCase.clear();
		    emailLoginInvalidCase.sendKeys(VALID_EMAIL_USER_1 + "\t" + VALID_PASSWORD_1);
		logger.info("UserChangeEmail::invalidChangeEmailTest::Testing ChangeEmail: "
			+ VALID_EMAIL_USER_1  + "Existing Email:" + VALID_PASSWORD_1
			+ " Existing passsword");
		WebElement buttonSetEmail = SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS);
		buttonSetEmail.sendKeys(Keys.ENTER);
		String conttext = SeleniumUtils.findPresentElementByClassNameWithWait(driver, ALERT_FAILURE_CLASS).getText();
		Assert.assertTrue(conttext.contains("This email address has already been taken"));
		SeleniumUtils.logout(driver, VALID_EMAIL_USER_2);
	    }
 @Test(description = "Tests negative user change email scenarios for small passwords")
 public void invalidUserChangeEmailSmallPwd() throws SecurityException, IOException {
	driver.get(BASE_URL);
	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
	SeleniumUtils.login(driver, VALID_EMAIL_USER_2, VALID_PASSWORD_2);
	WebElement account = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT)));
	account.sendKeys(Keys.ENTER);
	//WebElement emailLoginInvalidCase = SeleniumUtils.findElementByClassName(driver, "form-group");
	WebElement emailInvalidCase = driver.findElement(By.xpath(XPATH_EMAIL));
	WebElement pwdInvalidCase = driver.findElement(By.xpath(XPATH_CURRENT_PWD));
	emailInvalidCase.clear();
	pwdInvalidCase.clear();
	emailInvalidCase.sendKeys(CHANGE_EMAIL_VALID_NONEXISTING_EMAILID);
	pwdInvalidCase.sendKeys(SMALL_PASSWORD_1);
	    //emailLoginInvalidCase.sendKeys(VALID_EMAIL_USER_1 + "\t" + SMALL_PASSWORD_1);
	logger.info("UserChangeEmail::invalidUserChangeEmailSmallPwd::Testing ChangeEmail: "
		+ CHANGE_EMAIL_VALID_NONEXISTING_EMAILID  + "Small passsword" + SMALL_PASSWORD_1);
	WebElement buttonSetEmail = SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS);
	buttonSetEmail.sendKeys(Keys.ENTER);
	String errortext = SeleniumUtils.findPresentElementByClassNameWithWait(driver, ALERT_FAILURE_CLASS).getText();
	Assert.assertTrue(errortext.contains("Error:Current password"));
	SeleniumUtils.logout(driver, VALID_EMAIL_USER_2);
 }
  
 @Test(enabled=false)//(description = "Tests negative  user forgot password scenarios for invalid email token")
 public void invalidEmailToken() throws Exception {
    driver.get(BASE_URL);
   // ArrayList<String> myArray= new ArrayList<String>();
	driver.get(BASE_URL);
      	//added the below code for signing up with a new account and replacing with company qa gmail address
      	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
      	WebElement dynamicLinkElementtest = SeleniumUtils.findClikableElementByXpathWithWait(driver,
		XPATH_CREATE_NEW_ACCOUNT_TEXT);
	dynamicLinkElementtest.sendKeys(Keys.RETURN);
	String randomStringOriginal = RandomStringUtils.random(6, false, true);
	String randomStringUpdated = RandomStringUtils.random(6, false, true);
	WebElement email = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_EMAIL);
	WebElement pass = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_PASSWORD);
	WebElement passConfirm = SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_CONFIRM_PASSWORD);
	email.clear();
	pass.clear();
	passConfirm.clear();
	//Calendar cal = Calendar.getInstance();
	String nonexistingEmailIdOriginal="companyqa+"+randomStringOriginal+ "@gmail.com";
	System.out.println("UserChangeEmailPassword::validChangeEMailTest::Old email id: " + nonexistingEmailIdOriginal);
	email.sendKeys(nonexistingEmailIdOriginal);//CHANGE_EMAIL_VALID_OLD_EMAILID);
	pass.sendKeys(CHANGE_EMAIL_VALID_PASSWORD);
	passConfirm.sendKeys(CHANGE_EMAIL_VALID_PASSWORD);
	//end of the code addition
      	//SeleniumUtils.login(driver,CHANGE_EMAIL_VALID_OLD_EMAILID,CHANGE_EMAIL_VALID_PASSWORD);
	SeleniumUtils.findElementByClassName(driver, BUTTON_CLASS).click();
	
	// SeleniumUtils.findElementByClassName(driver, "btn-primary").click();
	Thread.sleep(9000);
	WebElement account = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.elementToBeClickable(By.linkText(LINK_MYACCOUNT)));
	account.sendKeys(Keys.ENTER);
	WebElement emailLogin = SeleniumUtils.findClikableElementByXpathWithWait(driver, XPATH_EMAIL);
	WebElement currentPwd = SeleniumUtils.findClikableElementByXpathWithWait(driver, XPATH_CURRENT_PWD);
	
	//String emailLoginText=SeleniumUtils.findElementByClassName(driver, "form-group").getAttribute("value");
	//Assert.assertEquals(emailLoginText,CHANGE_EMAIL_VALID_OLD_EMAILID);
	//emailLogin.sendKeys( "\t" + CHANGE_EMAIL_VALID_PASSWORD);
	Thread.sleep(9000);
	//String nonexistingEmailId3="companyqa+"+cal.getTimeInMillis()+ "@gmail.com";
	String nonexistingEmailIdUpdated="companyqa+"+randomStringUpdated+ "@gmail.com";
	System.out.println("UserChangeEmailPassword::validChangeEMailTest::New email id: " + nonexistingEmailIdUpdated);
	for(int i=0;i<3;i++){
	emailLogin.clear();
	emailLogin.sendKeys(nonexistingEmailIdUpdated);
	currentPwd.clear();
	currentPwd.sendKeys(CHANGE_EMAIL_VALID_PASSWORD);
	
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS).click();
	
	Thread.sleep(4000);
	}
	
	/*for(int i=0;i<3;i++){
	
	
	WebElement email = SeleniumUtils.findElementByName(driver, "email");
	//Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	email.clear();
	email.sendKeys(FORGOT_PWD_EMAIL_ID);
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, "btn-link").click();
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, "btn-primary").click();
	Thread.sleep(7000);
	
	}*/
	//System.out.println("UserForgotPassword::validForgotPasswordEmailTokenTest::Mail id: " + FORGOT_PWD_EMAIL_ID);  
	
	//ArrayList<String> myArray= new ArrayList<String>();
	//for(String str:myArray){
	    
	//}
	//
	
	Date datesent = new Date();
	long timesent = datesent.getTime();
	Timestamp tssenttime = new Timestamp(timesent);
	System.out.println("UserChangeEmailPassword::validChangeEMailTest::Testing received mail timestamp: " + tssenttime);
	driver.manage().timeouts().pageLoadTimeout(30000, TimeUnit.MILLISECONDS);
	Properties props = System.getProperties();
	props.setProperty("mail.store.protocol", "imaps");
	Session session = Session.getDefaultInstance(props, null);
	Store store = session.getStore("imaps");
	store.connect("imap.gmail.com", CHANGE_EMAIL_GMAILID,CHANGE_EMAIL_VALID_GMAIL_PASSWORD);
	Folder folder = store.getFolder("INBOX");
	folder.open(Folder.READ_ONLY);
	Message[] messages = null;
	boolean isMailFound = false;
	Message mailFromCompany = null;
	TreeMap<Long, String> map = new TreeMap<Long, String>();
	Long receivedMailTimestamp = null;
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
		    System.out.println("UserChangeEmail::validChangeEmailTest::Received Mail Timestamp: "
			    + mail.getReceivedDate());
		    receivedMailTimestamp=mail.getReceivedDate().getTime();
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
	    StringBuffer bufferUpdateEmail = new StringBuffer();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromCompany.getInputStream()));
	    while ((line = reader.readLine()) != null) {
		bufferUpdateEmail.append(line); 
	    }
	    Thread.sleep(2000);
	    System.out.println(bufferUpdateEmail.toString());
	    Assert.assertTrue(bufferUpdateEmail.toString().contains(nonexistingEmailIdUpdated), ERR_TEXT_EMAILID_NOTINMAIL);

	    int intStartIndex = bufferUpdateEmail.indexOf(LINK_IN_EMAIL);
	   for(int testval=0;testval<1;testval++){
	    
		System.out.println(intStartIndex);
		intStartIndex=bufferUpdateEmail.indexOf(LINK_IN_EMAIL,intStartIndex+1);
	    }
	   
	     int intEndIndex = bufferUpdateEmail.indexOf("\"",intStartIndex+1);
	    
	    //System.out.println("buffer:\n"+bufferUpdateEmail);
	    String linkUpdateButtonContentnew = bufferUpdateEmail.substring(intStartIndex,intEndIndex).replace("=", "");
	    System.out.println("linkUpdateButtonContentnew"+linkUpdateButtonContentnew);
	    driver.get(linkUpdateButtonContentnew);
	    Thread.sleep(2000);
	    String UpdateSuccess=SeleniumUtils.findElementByTagName(driver, "h3").getText();
	    Assert.assertEquals(UpdateSuccess, MSG_SUCCESS_CONFIRMEMAIL_1);
	    String UpdateSuccessMessage=SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_CONFIRMEMAIL_LINE2).getText();
	    Assert.assertTrue(UpdateSuccessMessage.contains(MSG_SUCCESS_CONFIRMEMAIL_2));
	    Thread.sleep(2000);
	    map.put(receivedMailTimestamp,UpdateSuccessMessage);
	    System.out.println(map);
	    Set set= map.entrySet();
	    Iterator iterator = set.iterator();
	    while(iterator.hasNext()) {
	         Map.Entry me = (Map.Entry)iterator.next();
	         System.out.print(me.getKey() + ": ");
	         System.out.println(me.getValue());
	      }
	
	
	
	

		 /*   int intStartIndex = buffer.indexOf("http");
		    int intEndIndex = buffer.indexOf("If");
		    String linkButtonContent = buffer.substring(intStartIndex, intEndIndex).replace("=", "");
		    driver.get(linkButtonContent);
		    WebElement message=SeleniumUtils.findPresenceofElementByIdWithWait(driver,"alerts_container");
		    String messageText=message.getText();
		    System.out.println(messageText);
		    map.put(mail.getReceivedDate().getTime(),messageText);
		    // Get a set of the entries
		    System.out.println(map);
		    Set set= map.entrySet();
		    Iterator iterator = set.iterator();
		    //myArray.add(linkButtonContent);
		    //break;
		    while(iterator.hasNext()) {
		         Map.Entry me = (Map.Entry)iterator.next();
		         System.out.print(me.getKey() + ": ");
		         System.out.println(me.getValue());
		      }
		    Thread.sleep(7000);
		    //continue;
		    
		}
	    }
	    }
	    //}
	   if (isMailFound) {
	       Thread.sleep(7000);
	    }
	   
	//}

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
	    int intStartIndex = buffer.indexOf("http");
	    int intEndIndex = buffer.indexOf("If");
	    String linkButtonContent = buffer.substring(intStartIndex, intEndIndex).replace("=", "");*/
	    
	    //commenting below
	  /*  int successcount=0,failurecount=0;
	    for (int j = 0; j < 3; j++) {
		
		driver.get(myArray.get(j));
		
		    if(SeleniumUtils.findPresentElementByClassNameWithWait(driver, "alert-success").getText().contains("Password reset successfully")){
			successcount++;
		    }
		    //Assert.assertTrue(conttextError.contains("Error:Something went wrong"));
		    if(SeleniumUtils.findPresentElementByClassNameWithWait(driver, "alert-danger").getText().contains("Error:Something went wrong")){
			failurecount++;
		    }
		
	    }*/
	    //
	    /*System.out.println("UserForgotPassword::validForgotPasswordEmailTokenTest::Button link: " + linkButtonContent);
	    driver.get(linkButtonContent);
	    System.out.println("link"+linkButtonContent);
	    String resetTextNewPage = SeleniumUtils.findPresentElementByCssWithWait(driver, "h2.text-center").getText();
	    Assert.assertEquals(resetTextNewPage, "Reset your password");
	    
	    WebElement passwordTxtboxInvalidCheck = SeleniumUtils.findElementByName(driver, "password");*/
	    /*passwordTxtboxInvalidCheck.clear();
	    passwordTxtboxInvalidCheck.sendKeys(FORGOT_PWD + "\t" + CHANGE_EMAIL_VALID_NEW_PASSWORD);
	    passwordTxtboxInvalidCheck.sendKeys(Keys.TAB);
	    Thread.sleep(7000);
	    WebElement setPasswordbtn = SeleniumUtils.findElementByClassName(driver, "btn-primary");
	    setPasswordbtn.click();
	    String resetTextNewPageInvalidCheck = SeleniumUtils.findPresentElementByCssWithWait(driver, "h2.text-center").getText();
	    Assert.assertEquals(resetTextNewPageInvalidCheck, "Reset your password");*/
	    
	    
	    /*passwordTxtboxInvalidCheck.clear();
	    passwordTxtboxInvalidCheck.sendKeys(FORGOT_PWD + "\t" + FORGOT_PWD);
	    passwordTxtboxInvalidCheck.sendKeys(Keys.TAB);
	    Thread.sleep(7000);
	    WebElement setPasswordbtnInvalid = SeleniumUtils.findElementByClassName(driver, "btn-primary");
	    setPasswordbtnInvalid.click();
	    String setPasswordbtntext = SeleniumUtils.findElementByClassName(driver, "btn-primary").getText();
	    Assert.assertEquals(setPasswordbtntext, "Set Password");*/
	    //String conttextSuccess = SeleniumUtils.findPresentElementByClassNameWithWait(driver, "alert-success").getText();
	   // Assert.assertEquals(conttextSuccess,conttextSuccess.contains("Password reset successfully"));
	    //String conttextError = SeleniumUtils.findPresentElementByClassNameWithWait(driver, "alert-danger").getText();
	    //Assert.assertEquals(conttextError,conttextError.contains("Error:Something went wrong"));
	   //Assert.assertEquals(actual, expected);
	    
	    //commetimg
	    
	   /* 
	    System.out.println("successcount"+successcount);
	    System.out.println("failurecount"+failurecount);*/
	    
	    ///
	    //SeleniumUtils.login(driver, FORGOT_PWD_EMAIL_ID, FORGOT_PWD);
	    //SeleniumUtils.logout(driver, FORGOT_PWD_EMAIL_ID);    
	}//else

}//test end
  WebDriver driver;
  @Parameters({ "browser" })
  @BeforeTest(description = "Navigates to company website")
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

  /*@BeforeTest(description = "Navigates to company website")
  public void setup() {
	driver = new FirefoxDriver();
	driver.manage().window().maximize();

  }*/

  @AfterTest(description = "Closes the browser")
  public void teardown() {
	driver.quit();
  }

}

