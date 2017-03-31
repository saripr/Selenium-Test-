package com.company.secure.selenium.tests;

import org.testng.annotations.Test;

import com.company.secure.utils.common.SeleniumUtils;

/**
 * Test for checking user forgot password to company website. Checks both positive and
 * negative scenarios for the same.
 * 
 * @author saritharajagopal
 *
 */
import org.testng.annotations.BeforeTest;

import static com.company.secure.utils.common.StringUtils.BASE_URL;
import static com.company.secure.utils.common.StringUtils.ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED;
import static com.company.secure.utils.common.StringUtils.ID_EMAIL;
import static com.company.secure.utils.common.StringUtils.ID_PASSWORD;
import static com.company.secure.utils.common.StringUtils.MSG_HOME_PAGE_TITLE;
import static com.company.secure.utils.common.StringUtils.MSG_MY_ZEN_MATE_ACCOUNT_TEXT;
import static com.company.secure.utils.common.StringUtils.MSG_WELCOME_BACK_TEXT_PAGE;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.remote.server.Session;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class UserForgotPassword_oldwithUI {
    /**
     * Checks user forgot password for positive scenarios. Uses internally
     * {@link com.company.secure.utils.common.StringUtils}
     */
  @Test(description = "Tests positive user forgot password scenarios")
  public void validLoginTest() {
      driver.get(BASE_URL);
      Assert.assertEquals(driver.getTitle(), MSG_HOME_PAGE_TITLE, ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED);
      SeleniumUtils.findPresentElementByClassNameWithWait(driver, "btn-link").click();
      WebElement email=SeleniumUtils.findElementByName(driver, "email");
      email.sendKeys("saripr@gmail.com");
      SeleniumUtils.findPresentElementByClassNameWithWait(driver, "btn-primary").click();
      WebElement newtab=SeleniumUtils.findPresentElementByCssWithWait(driver, "body");
      newtab.sendKeys(Keys.COMMAND+"t");
      ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
      driver.switchTo().window(tabs.get(0));
      driver.get("https://mail.google.com/");
      WebElement email_id=SeleniumUtils.findPresenceofElementByIdWithWait(driver, "Email");
      email_id.sendKeys("saripr@gmail.com");
      WebElement next=SeleniumUtils.findPresenceofElementByIdWithWait(driver, "next");
      next.sendKeys(Keys.ENTER);
      WebElement pwd_id=SeleniumUtils.findPresenceofElementByIdWithWait(driver, "Passwd");
      pwd_id.sendKeys("indirarajagopal");
      WebElement signin=SeleniumUtils.findPresenceofElementByIdWithWait(driver, "signIn");
      signin.sendKeys(Keys.ENTER);
      //clicking on inbox
      WebElement inbox=SeleniumUtils.findClickableElementByPartialLinkTextWithWait(driver, "Inbox");
      inbox.sendKeys(Keys.ENTER);
      driver.manage().timeouts().pageLoadTimeout(30000,TimeUnit.MILLISECONDS);
      
      //clicking on subject
      WebElement webElementSub=SeleniumUtils.findPresentElementByCssWithWait(driver, "div.y6");
      String subject=webElementSub.getText();
      //System.out.println(subject);
      driver.manage().timeouts().pageLoadTimeout(30000,TimeUnit.MILLISECONDS);
     /* try {
	Thread.sleep(2000);
    } catch (InterruptedException e) {
	
	e.printStackTrace();
    }*/
      //needs to be checked from here
      //Assert.assertTrue(subject.contains("Reset password instructions"));
      //webElementSub.sendKeys(Keys.ENTER);
      webElementSub.click();
      String parentHandle = driver.getWindowHandle();
      //clicking on reset my password button
      WebElement resettext=SeleniumUtils.findClickableElementByLinkTextWithWait(driver, "Reset My Password");
      String reset=SeleniumUtils.findClickableElementByLinkTextWithWait(driver, "Reset My Password").getText();
      Assert.assertEquals(reset, "Reset My Password");
      resettext.click();
      
      //switching to the new window
      String newWindow = null;
      for (String winHandle : driver.getWindowHandles()) {
	  if (!parentHandle.equals(winHandle)) {
	            newWindow = winHandle;
	        }
	  }
      Assert.assertNotNull(newWindow);
      driver.switchTo().window(newWindow);
      //In new window of reset password now
      String resetTextNewPage=SeleniumUtils.findPresentElementByCssWithWait(driver, "h2.text-center").getText();
      Assert.assertEquals(resetTextNewPage, "Reset your password");
      WebElement passwordTxtbox=SeleniumUtils.findElementByName(driver, "password");
      passwordTxtbox.clear();
      passwordTxtbox.sendKeys("1234567891\t1234567891");
     // passwordTxtbox.sendKeys("12345678");
      //passwordTxtbox.sendKeys(Keys.TAB);
      //WebElement passwordCnfrmTxtbox=SeleniumUtils.findElementByName(driver, "passwordConfirmation");
     // passwordCnfrmTxtbox.sendKeys("12345678");
      WebElement setPasswordbtn=SeleniumUtils.findElementByClassName(driver, "btn-primary");
      setPasswordbtn.click();
      String setPasswordbtntext=SeleniumUtils.findElementByClassName(driver, "btn-primary").getText();
      Assert.assertEquals(setPasswordbtntext, "Set Password");
      String conttext=SeleniumUtils.findPresentElementByClassNameWithWait(driver,"alert-success").getText();
      Assert.assertTrue(conttext.contains("Password reset successfully")); 
      SeleniumUtils.login(driver,"saripr@gmail.com","1234567891");
      
  }
  

   

WebDriver driver;
  
  @BeforeTest(description = "Navigates to company website")
  public void setup() {
	driver = new FirefoxDriver();
	driver.manage().window().maximize();
	driver.manage().deleteAllCookies();

  }

  @AfterTest(description = "Closes the browser")
  public void teardown() {
	driver.quit();
  }
}

