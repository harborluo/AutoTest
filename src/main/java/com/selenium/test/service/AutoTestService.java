package com.selenium.test.service;



import java.util.List;
import java.util.Random;

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
	
    @Value("${test.fd.name}")
    private String displayName;
    
    @Value("${test.fd.host}")
    private String host;
    
    @Value("${test.fd.port}")
    private String port;
    
    @Value("${test.fd.user}")
    private String code;
    
    @Value("${test.fd.pass}")
    private String pass;
    
    @Value("${test.fd.configBtnId}")
    private String configBtnId;
    
    
	
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
		
		installPlugin(driver);
		pauseSeconds(60);
		
		configInstance(driver);
		
//		driver.quit();
	}
	
	private void installPlugin(WebDriver driver){
        
        logger.info("click config button");
        WebElement configIcon = driver.findElement(By.id(configBtnId));
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
        
        pauseSeconds(40);
        
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
        
        logger.info("click next button, waiting installation to be done.");
        WebElement nextButton2 = driver.findElement(By.xpath("//span[@unselectable='on' and text()='Next']"));
        nextButton2.click();
        
        (new WebDriverWait(driver, 1200)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d2) {
                try{
                    return d2.findElement(By.xpath("//td[starts-with(@id,'loadingInfo_')]")).isDisplayed()==false;
                }catch(Exception e){
                    return true;
                }
            }
        });
        
        logger.info("Click finish button.");
        
        WebElement finishedBtn = driver.findElement(By.xpath("//span[@unselectable='on' and text()='Finish']"));
        finishedBtn.click();
        
        logger.info("plugin install complete.");
        
	}
	
	private void configInstance(WebDriver driver){
		
		pauseSeconds(3);
		
	    logger.info("click config button");
        WebElement configIcon = driver.findElement(By.id(configBtnId));
        configIcon.click();
        
        pauseSeconds(8);
        
        WebElement sulutionRow = driver.findElement(By.xpath("//div[text()='Huawei FusionDirector Management Pack for VMware vRealize Operations']"));
        sulutionRow.click();
        
        pauseSeconds(1);
        
        WebElement configSolutionLink = driver.findElement(By.xpath("//a[starts-with(@id,'configureBtn')]"));
        configSolutionLink.click();
        
        pauseSeconds(5);
        
        logger.info("add credential");
        WebElement addImage = driver.findElement(By.xpath("//img[starts-with(@id,'btnAddCredential_')]"));
        addImage.click();
        
        pauseSeconds(1);
        int index = 0;
        List<WebElement> credentailFields = driver.findElements(By.xpath("//input[@role='textbox' and starts-with(@name,'cred')]"));
        
        logger.info("{} credential fields found.", credentailFields.size());
        
        for (WebElement text : credentailFields) {
            text.click();
            pauseSeconds(1);
            
            if(index==0){
                text.sendKeys(this.code + new Random().nextInt(1000));
            } else if (index==1){
                text.sendKeys(this.code);
            } else {
                text.sendKeys(this.pass);
            }
            index++;
        }
        pauseSeconds(2);
        logger.info("click OK button to save");
        WebElement okBtn = driver.findElement(By.xpath("//span[text()='OK']"));
        okBtn.click();
        
        pauseSeconds(2);
        List<WebElement> textFields = driver.findElements(By.xpath("//input[starts-with(@id,'textfield-')]"));
        
         index = 0;
        for(WebElement text : textFields) {
            text.click();
            text.clear();
            pauseSeconds(1);
            if(index==0){
              text.sendKeys(this.displayName);
            } else if (index==1){
                text.sendKeys(this.host);
            } else {
                text.sendKeys(this.port);
            }
            index++;
        }
        
        pauseSeconds(2);
        logger.info("click button {}","save setting");
        WebElement saveSettingBtn = driver.findElement(By.xpath("//span[starts-with(@id,'button-') and text()='Save Settings']"));
        saveSettingBtn.click();
        
        pauseSeconds(2);
        WebElement lastOKBtn = driver.findElement(By.xpath("//span[starts-with(@id,'button-') and text()='OK']"));
        lastOKBtn.click();
        
        pauseSeconds(2);
        WebElement closeBtn = driver.findElement(By.xpath("//span[starts-with(@id,'button-') and text()='Close']"));
        closeBtn.click();
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
