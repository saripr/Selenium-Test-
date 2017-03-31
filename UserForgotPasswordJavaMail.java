/*package com.company.secure.selenium.tests;

import org.testng.annotations.Test;

import com.company.secure.utils.common.SeleniumUtils;

*//**
 * Test for checking user forgot password to company website. Checks both positive and
 * negative scenarios for the same.
 * 
 * @author saritharajagopal
 *
 *//*
import org.testng.annotations.BeforeTest;

import static com.company.secure.utils.common.StringUtils.BASE_URL;
import static com.company.secure.utils.common.StringUtils.ERR_TITLE_OF_LOADED_PAGE_IS_NOT_AS_EXPECTED;
import static com.company.secure.utils.common.StringUtils.MSG_HOME_PAGE_TITLE;

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
import javax.mail.util.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public static void main(String[] args) throws Exception {
    Properties props = System.getProperties();
    props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", "secretmail@gmail.com",
                "qwerty!@#$%");

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        System.out.println("Total Message:" + folder.getMessageCount());
        System.out.println("Unread Message:"
                + folder.getUnreadMessageCount());
        
        Message[] messages = null;
        boolean isMailFound = false;
        Message mailFromGod= null;

        //Search for mail from God
        for (int i = 0; i &amp;lt; 5; i++) {
            messages = folder.search(new SubjectTerm(
                    "Welcome to Heaven"),
                    folder.getMessages());
            //Wait for 10 seconds
            if (messages.length == 0) {
                Thread.sleep(10000);
            }
        }

        //Search for unread mail from God
        //This is to avoid using the mail for which 
        //Registration is already done
        for (Message mail : messages) {
            if (!mail.isSet(Flags.Flag.SEEN)) {
                mailFromGod = mail;
                System.out.println("Message Count is: "
                        + mailFromGod.getMessageNumber());
                isMailFound = true;
            }
        }

        //Test fails if no unread mail was found from God
        if (!isMailFound) {
            throw new Exception(
                    "Could not find new mail from God :-(");
        
        //Read the content of mail and launch registration URL                
        } else {
            String line;
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(mailFromGod
                            .getInputStream()));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            System.out.println(buffer);

            //Your logic to split the message and get the Registration URL goes here
            String registrationURL = buffer.toString().split("&amp;gt;http://www.god.de/members/?")[0]
                    .split("href=")[1];
            System.out.println(registrationURL);                            
        }
}*/
