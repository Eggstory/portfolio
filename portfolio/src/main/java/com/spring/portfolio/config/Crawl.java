package com.spring.portfolio.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Crawl {

    public void Crawl() {

        WebDriver driver = WebDriverUtil.getChromeDriver();
        List<WebElement> webElementList = new ArrayList<>();
        String url = "https://weather.naver.com/today";
        String query = ".scroll_area .weather_table_wrap ._cnTime";

        if (!ObjectUtils.isEmpty(driver)) {
            driver.get(url);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        webElementList = driver.findElements(By.cssSelector(query));

        WebElement parentElement = webElementList.get(0);
        List<WebElement> childElement = parentElement.findElements(By.tagName("td"));

        System.out.println(childElement.get(0).getText());
        System.out.println(parentElement.getAttribute("class"));
        System.out.println(parentElement.getCssValue("height"));

//        parentElement.click();
//        parentElement.submit();
    }
}
