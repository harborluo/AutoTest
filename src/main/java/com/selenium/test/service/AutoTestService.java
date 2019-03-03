package com.selenium.test.service;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class AutoTestService {
	
	private static Logger logger = LoggerFactory.getLogger(AutoTestService.class);
	

	@Value("${browser.driver}")
	private String driverLocation;
	
	@Value("${test.url}")
	private String url;
	
	@Value("${test.user}")
	private String user;
	
	@Value("${test.password}")
	private String password;
	
	@Value("${test.file}")
	private String pakFile;
	
	public void test(){
		
		System.setProperty("webdriver.chrome.driver", driverLocation);
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		
		
		logger.info("Open chrome broswer with url {}", url);
		
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.id("userName-inputEl")).isDisplayed();
			}
		});
		
		logger.info("find input field for user and password.");
		
		WebElement userInput = driver.findElement(By.id("userName-inputEl"));
		userInput.click();
		
		pauseSeconds(1);
		
		userInput.sendKeys(user);
		logger.info("send keys {} to input field {}", user, "userName-inputEl");
		
		WebElement passwordInput = driver.findElement(By.id("password-inputEl"));
		passwordInput.click();
		passwordInput.sendKeys(password);
		logger.info("send keys {} to input field {}", password, "password-inputEl");
		
		WebElement loginButton = driver.findElement(By.id("loginBtn-btnInnerEl"));
		loginButton.click();
		
		logger.info("click login button");
		pauseSeconds(10);
		
		logger.info("click config button");
		WebElement configIcon = driver.findElement(By.id("objectNavigatorToolbarAdministrationBtn-btnIconEl"));
		configIcon.click();
		
		pauseSeconds(8);
		
		logger.info("click add solution button");
		WebElement addIcon = driver.findElement(By.xpath("//a[starts-with(@id,'addSolutionBtn')]"));
		addIcon.click();
		
		pauseSeconds(2);
		
		logger.info("select pak file");
		WebElement file = driver.findElement(By.xpath("//input[@data-ref='fileInputEl']"));
		file.sendKeys(pakFile);
		
		pauseSeconds(1);
		
		logger.info("Make checkbox selected");
		WebElement checkbox = driver.findElement(By.xpath("//label[@data-ref='boxLabelEl']"));
		checkbox.click();
		
		pauseSeconds(1);
		
		logger.info("upload pak file.");
		WebElement uploadButton = driver.findElement(By.xpath("//span[@data-ref='btnInnerEl' and text()='Upload']"));
		uploadButton.click();
		
		pauseSeconds(8);
		
		logger.info("click next button.");
		WebElement nextButton = driver.findElement(By.xpath("//span[@unselectable='on' and text()='Next']"));
		nextButton.click();
		
		pauseSeconds(2);
		
		logger.info("click Yes button.");
		WebElement yesButton = driver.findElement(By.xpath("//span[@unselectable='on' and text()='Yes']"));
		yesButton.click();
		
		pauseSeconds(3);
		
		logger.info("Accept end user license agreement.");
		WebElement labelAccept = driver.findElement(By.xpath("//label[text()='I accept the terms of this agreement']"));
		labelAccept.click();
		
		pauseSeconds(1);
		
		logger.info("clicke next button.");
		WebElement nextButton2 = driver.findElement(By.xpath("//span[@unselectable='on' and text()='Next']"));
		nextButton2.click();
		
		(new WebDriverWait(driver, 360)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d2) {
				try{
					return d2.findElement(By.xpath("//td[starts-with(@id,'loadingInfo_')]")).isDisplayed()==false;
				}catch(Exception e){
					return true;
				}
			}
		});
		logger.info("plugin install complete.");
		
		WebElement finishedBtn = driver.findElement(By.xpath("//span[@unselectable='on' and text()='Finish']"));
		finishedBtn.click();
		
//		driver.quit();
	}
	
	private void pauseSeconds(int sec){
		
		logger.info("Pause for {} seconds", sec);
		
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
