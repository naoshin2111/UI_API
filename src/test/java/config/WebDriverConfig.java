package config;

import aquality.selenium.browser.AqualityServices;
import org.openqa.selenium.WebDriver;

public class WebDriverConfig {

    private static WebDriver webDriver = null;

    private WebDriverConfig() {

    }

    public static WebDriver getInstance() {
        if (webDriver == null) {
            return AqualityServices.getBrowser().getDriver();
        }
        return webDriver;
    }
}
