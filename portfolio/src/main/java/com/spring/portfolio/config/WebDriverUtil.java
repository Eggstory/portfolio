package com.spring.portfolio.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebDriverUtil {

    private static String WEB_DRIVER_PATH = "C:\\chromeDriver\\132.0.6834.83\\chromedriver-win64\\chromedriver.exe";

    public static WebDriver getChromeDriver() {
        if (ObjectUtils.isEmpty(System.getProperty("webdriver.chrome.driver"))) {
            System.setProperty("webdriver.chrome.driver", WEB_DRIVER_PATH);
        }

        // webDriver 옵션 설정
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setHeadless(true); //한줄 아래껄로 대체됨
        chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("--lang=ko");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.setCapability("ignoreProtectedModeSettings", true);

        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        return driver;

    }

//    @Value("#{resource[driver.chrome.driver_path']}")
    @Value("${driver.chrome.driver_path}")
    public void initDriver(String path) {
        WEB_DRIVER_PATH = path;
    }

    public static void quit(WebDriver driver) {
        if(!ObjectUtils.isEmpty(driver)) {
            driver.quit();
        }
    }

    public static void close(WebDriver driver) {
        if(!ObjectUtils.isEmpty(driver)) {
            driver.close();
        }
    }

}
