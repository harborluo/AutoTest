package com.selenium.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.selenium.test.service.AutoTestService;

@SpringBootApplication
@EnableScheduling
public class TestAutomationApplication implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(TestAutomationApplication.class);

    @Autowired
    private AutoTestService service;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestAutomationApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    public void run(String... args) throws Exception {
        logger.info("Auto test begin...");
        service.test();
    }

}
