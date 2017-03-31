
package com.company.secure.selenium.tests;

import static com.company.secure.utils.common.StringUtils.ALERT_FAILURE_CLASS;
import static com.company.secure.utils.common.StringUtils.ALERT_SUCCESS_CLASS;
import static com.company.secure.utils.common.StringUtils.BASE_URL;
import static com.company.secure.utils.common.StringUtils.BUTTON_CLASS;
import static com.company.secure.utils.common.StringUtils.CSS_ACCOUNT_SETTING;
import static com.company.secure.utils.common.StringUtils.ERR_FORGOT_NON_EXIST_MAIL;
import static com.company.secure.utils.common.StringUtils.ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED;
import static com.company.secure.utils.common.StringUtils.FORGOT_GMAIL_PWD;
import static com.company.secure.utils.common.StringUtils.FORGOT_NONEXIST_EMAIL;
import static com.company.secure.utils.common.StringUtils.FORGOT_PWD;
import static com.company.secure.utils.common.StringUtils.FORGOT_PWD_EMAIL_ID;
import static com.company.secure.utils.common.StringUtils.ID_EMAIL;
import static com.company.secure.utils.common.StringUtils.ID_PASSWORD;
import static com.company.secure.utils.common.StringUtils.INVALID_EMAIL_ID_1;
import static com.company.secure.utils.common.StringUtils.LINK_FORGOT_PASSWORD;
import static com.company.secure.utils.common.StringUtils.MSG_HOME_PAGE_TITLE;
import static com.company.secure.utils.common.StringUtils.MSG_RESET_PWD_SUCCESS;
import static com.company.secure.utils.common.StringUtils.SUB_TEXT_RESET_PASSWORD;
import static com.company.secure.utils.common.StringUtils.TEXT_FORGOT_PWD;
import static com.company.secure.utils.common.StringUtils.TEXT_RESET_PWD;
import static com.company.secure.utils.common.StringUtils.TEXT_RESET_PWD_PAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.TreeMap;
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
/**
 * Test for checking user forgot password to company website. Checks both positive and
 * negative scenarios for the same.
 * 
 * @author saritharajagopal
 *
 */
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
/**
 * @author saritharajagopal
 *
 */
import org.testng.annotations.Test;

import com.company.secure.utils.common.SeleniumUtils;

public class UserForgotPassword {
   
    /**
     * Checks user forgot password for positive scenarios. Uses internally
     * {@link com.company.secure.utils.common.StringUtils}
     * 
     * @throws Exception
     */
    @Test(description = "Tests positive user forgot password scenarios")
    public void validForgotPasswordTest() throws Exception {
	driver.get(BASE_URL);
	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, LINK_FORGOT_PASSWORD).click();
	WebElement email = SeleniumUtils.findElementByName(driver, ID_EMAIL);
	email.sendKeys(FORGOT_PWD_EMAIL_ID);
	logger.info("UserForgotPassword::validForgotPasswordTest::Mail id: " + FORGOT_PWD_EMAIL_ID);
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS).click();
	//Assert.assertEquals(actual, expected);
	Date datesent = new Date();
	long timesent = datesent.getTime();
	Timestamp tssenttime = new Timestamp(timesent);
	//System.out.println("UserForgotPassword::validForgotPasswordTest::Testing received mail timestamp: " + tssenttime);
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
	    messages = folder.search(new SubjectTerm(SUB_TEXT_RESET_PASSWORD), folder.getMessages());
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
		    logger.info("UserForgotPassword::validForgotPasswordTest::Received Mail Timestamp: "
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
	    int intStartIndex = buffer.indexOf("http");
	    int intEndIndex = buffer.indexOf("If");
	    String linkButtonContent = buffer.substring(intStartIndex, intEndIndex).replace("=", "");
	    logger.info("UserForgotPassword::validForgotPasswordTest::Button link: " + linkButtonContent);
	    driver.get(linkButtonContent);
	    String resetTextNewPage = SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_ACCOUNT_SETTING).getText();
	    Assert.assertEquals(resetTextNewPage, TEXT_RESET_PWD);
	    //Added for checking th confirmation mail message
	    
	    WebElement passwordTxtbox = SeleniumUtils.findElementByName(driver, ID_PASSWORD);
	    passwordTxtbox.clear();
	    passwordTxtbox.sendKeys(FORGOT_PWD + "\t" + FORGOT_PWD);
	    passwordTxtbox.sendKeys(Keys.TAB);
	    Thread.sleep(7000);
	    WebElement setPasswordbtn = SeleniumUtils.findElementByClassName(driver, BUTTON_CLASS);
	    setPasswordbtn.click();
	    String setPasswordbtntext = SeleniumUtils.findElementByClassName(driver, BUTTON_CLASS).getText();
	    Assert.assertEquals(setPasswordbtntext, TEXT_RESET_PWD_PAGE);
	    String conttext = SeleniumUtils.findPresentElementByClassNameWithWait(driver, ALERT_SUCCESS_CLASS).getText();
	    Assert.assertTrue(conttext.contains(MSG_RESET_PWD_SUCCESS));
	    SeleniumUtils.login(driver, FORGOT_PWD_EMAIL_ID, FORGOT_PWD);
	    SeleniumUtils.logout(driver, FORGOT_PWD_EMAIL_ID);     
	}

    }

    @Test(description = "Tests negative user forgot password scenarios")
    public void invalidForgotPasswordTest() throws SecurityException, IOException {
	driver.get(BASE_URL);
	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	Logger logger = Logger.getLogger("MyLog");
	SeleniumUtils.loggingInfo(driver, logger,"/Users/saritharajagopal/Desktop/Screenshots/TestLog.log");
	String[] invalidCombinationsemail = invalidCombinationsemail();
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, LINK_FORGOT_PASSWORD).click();
	WebElement email = SeleniumUtils.findElementByName(driver, ID_EMAIL);
	for (String row : invalidCombinationsemail) {
	    logger.info("UserForgotPassword::invalidForgotPasswordTest::Mail Id: " + row);
	    email.sendKeys(row);
	    SeleniumUtils.findPresentElementByClassNameWithWait(driver, BUTTON_CLASS).click();
	    String forgotPwdInvalidPage = SeleniumUtils.findPresentElementByCssWithWait(driver, CSS_ACCOUNT_SETTING)
		    .getText();
	    Assert.assertEquals(forgotPwdInvalidPage, TEXT_FORGOT_PWD);
	  
	    
	    email.clear();
	    if(row==FORGOT_NONEXIST_EMAIL){
	  		String contextError = SeleniumUtils.findPresentElementByClassNameWithWait(driver, ALERT_FAILURE_CLASS).getText();
	  		Assert.assertTrue(contextError.contains(ERR_FORGOT_NON_EXIST_MAIL));
	  	    }
	   
	}

    }

    private String[] invalidCombinationsemail() {
	String invalidCombinationsemail[] = { "" , INVALID_EMAIL_ID_1 ,FORGOT_NONEXIST_EMAIL };
	return invalidCombinationsemail;
    }

   @Test(description = "Tests scenario for persistent mail id")
        public void validEMailPersistTest() {
	driver.get(BASE_URL);
	Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, LINK_FORGOT_PASSWORD).click();
	WebElement email = SeleniumUtils.findElementByName(driver, ID_EMAIL);
	email.sendKeys(FORGOT_PWD_EMAIL_ID);
	email.sendKeys(Keys.TAB);
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, LINK_FORGOT_PASSWORD).click();
	String emailLogin=SeleniumUtils.findPresenceofElementByIdWithWait(driver, ID_EMAIL).getAttribute("value");
	Assert.assertEquals(emailLogin,FORGOT_PWD_EMAIL_ID);
    }
    
  @Test(enabled=false)//(description = "Tests negative  user forgot password scenarios for invalid email token")
   public void invalidEmailToken() throws Exception {
      driver.get(BASE_URL);
     // ArrayList<String> myArray= new ArrayList<String>();
      
	for(int i=0;i<3;i++){
	
	
	WebElement email = SeleniumUtils.findElementByName(driver, "email");
	//Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
	email.clear();
	email.sendKeys(FORGOT_PWD_EMAIL_ID);
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, "btn-link").click();
	SeleniumUtils.findPresentElementByClassNameWithWait(driver, "btn-primary").click();
	Thread.sleep(7000);
	
	}
	System.out.println("UserForgotPassword::validForgotPasswordEmailTokenTest::Mail id: " + FORGOT_PWD_EMAIL_ID);  
	
	//ArrayList<String> myArray= new ArrayList<String>();
	//for(String str:myArray){
	    
	//}
	//
	Date datesent = new Date();
	long timesent = datesent.getTime();
	Timestamp tssenttime = new Timestamp(timesent);
	System.out
	.println("UserForgotPassword::validForgotPasswordEmailTokenTest::Testing received mail timestamp: " + tssenttime);
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
	TreeMap<Long, String> map = new TreeMap<Long, String>();
//adding the below code for looping
 //for (int j = 0; j < 5; j++) {
	// Search for mail from company
	 for (int i = 0; i < 10; i++) {
	    messages = folder.search(new SubjectTerm("Reset password instructions"), folder.getMessages());
	    Date currentdate = new Date();
	    long currenttime = currentdate.getTime();
	    Timestamp currentts = new Timestamp(currenttime);
	   
	    // Search for unread mail from company
	    // This is to avoid using the mail for which
	    // Registration is already done
	    for (Message mail : messages) {
		mailFromCompany = mail;
		Thread.sleep(2000);
		if (!mail.isSet(Flags.Flag.SEEN) && mail.getReceivedDate().before(currentts)
			&& mail.getReceivedDate().after(tssenttime)) {
		    System.out.println("UserForgotPassword::validForgotPasswordEmailTokenTest::Received Mail Timestamp: "
			    + mail.getReceivedDate());
		    isMailFound = true;
		    String line;
		    StringBuffer buffer = new StringBuffer();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromCompany.getInputStream()));
		    while ((line = reader.readLine()) != null) {
			buffer.append(line); 
		    }
		    int intStartIndex = buffer.indexOf("http");
		    int intEndIndex = buffer.indexOf("If");
		    String linkButtonContent = buffer.substring(intStartIndex, intEndIndex).replace("=", "");
		    driver.get(linkButtonContent);
		    WebElement message=SeleniumUtils.findPresenceofElementByIdWithWait(driver,"alerts_container");
		    String messageText=message.getText();
		    System.out.println(messageText);
		    map.put(mail.getReceivedDate().getTime(),messageText);
		    // Get a set of the entries
		    System.out.println(map);
		   /* Set set= map.entrySet();
		    Iterator iterator = set.iterator();
		    //myArray.add(linkButtonContent);
		    //break;
		    while(iterator.hasNext()) {
		         Map.Entry me = (Map.Entry)iterator.next();
		         System.out.print(me.getKey() + ": ");
		         System.out.println(me.getValue());
		      }*/
		    Thread.sleep(7000);
		    //continue;
		    
		}
	    }
	    }
	    //}
	   /*if (isMailFound) {
	       Thread.sleep(7000);
	    }*/
	   
	//}

	// Test fails if no unread mail was found from company
	if (!isMailFound) {
	    throw new Exception("Could not find new mail");

	    // Read the content of mail and launch registration URL
	} else {
	    /*String line;
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

    @AfterTest(description = "Closes the browser")
    public void teardown() {
	driver.quit();
    }
}

