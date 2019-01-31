package com.qa.SeleniumReadWrite;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.PageFactory;

@RunWith(Parameterized.class)
public class ExcelParaTest {

	@Parameters
	public static Collection<Object[]> data() throws IOException{
		FileInputStream file = new FileInputStream("C:\\Users\\Admin\\Desktop\\DemoSiteDDT.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		Object[][] ob = new Object[sheet.getPhysicalNumberOfRows()-1][4];
		
		//reading
		for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
			ob[rowNum-1][0] = sheet.getRow(rowNum).getCell(0).getStringCellValue();
			ob[rowNum-1][1] = sheet.getRow(rowNum).getCell(1).getStringCellValue();
			ob[rowNum-1][2] = sheet.getRow(rowNum).getCell(2).getStringCellValue();
			ob[rowNum-1][3] = rowNum;
		}
	return Arrays.asList(ob);
	}
	
	private String userName;
	private String password;
	private String expected;
	private int row;
	
	public ExcelParaTest(String userName, String password, String expected, int row) {
		this.userName = userName;
		this.password = password;
		this.expected = expected;
		this.row = row;
	}
	
	WebDriver driver;
	LandingPage landingPage; 
	SearchPage search;
	
	@Before
	public void setUp() {
		System.setProperty("phantomjs.binary.path", "C:\\Users\\Admin\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		driver = new PhantomJSDriver();
		driver.manage().window().maximize();
		driver.get(Constants.DEMOSITE);
		search = PageFactory.initElements(driver, SearchPage.class);
	}

	@After
	public void tearDown() {
		driver.close();
	}
	
	@Test
	public void testOne() {
		System.out.println(userName + " " + password + " " + expected + " " + row);
		
		System.out.println("actual result needs to be printed in row:" + row);
		
		WebElement addUserTab =	driver.findElement(By.xpath("/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[3]"));
		addUserTab.click();
		WebElement makeNameBox = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[1]/td[2]/p/input")); 
		makeNameBox.sendKeys(userName);
		WebElement makePassBox = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[2]/td[2]/p/input")); 
		makePassBox.sendKeys(password);
		WebElement makeSubmit = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[3]/td[2]/p/input"));
		makeSubmit.submit();
		
		WebElement loginTab =	driver.findElement(By.xpath("/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[4]"));
		loginTab.click();
		WebElement loginNameBox = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[1]/td[2]/p/input")); 
		loginNameBox.sendKeys(userName);
		WebElement loginPassBox = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/p/input")); 
		loginPassBox.sendKeys(password);
		WebElement loginSubmit = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/p/input"));
		loginSubmit.submit();	
		
		try {
			assertEquals("login failed", expected, search.isLoggedIn());
			//assertEquals("error message", expected value, actual value);
			// write pass to excel sheet
		}
		catch (AssertionError e) {
			// write fail to excel sheet
			Assert.fail(); 
			// Because AssertionError was caught, test no longer fails,
			// therefore we force it to fail after we have written it to excel sheet
		}
	}
}
