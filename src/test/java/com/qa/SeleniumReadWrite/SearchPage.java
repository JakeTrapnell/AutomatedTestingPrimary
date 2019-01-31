package com.qa.SeleniumReadWrite;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage {
	
	@FindBy(xpath="/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b")
	public WebElement isLoggedIn;
	
	public String isLoggedIn() {
		return isLoggedIn.getText();
	}

}
