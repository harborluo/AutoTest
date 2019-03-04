package com.selenium.test.service;

import org.openqa.selenium.WebDriver;


public interface TestService {

    void start();

    void installPlugin(WebDriver driver);

    void configInstance(WebDriver driver);

}