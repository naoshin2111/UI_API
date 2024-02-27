package utils;

import config.EnvironmentConfig;
import config.WebDriverConfig;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Headers;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@UtilityClass
public class AuthenticationUtils {

    private static WebDriver driver = WebDriverConfig.getInstance();

    private static DevTools devTools;

    public static void authenticate(String username, String password) {

        if (driver instanceof ChromeDriver) {
            devTools = ((ChromeDriver) driver).getDevTools();
            devTools.createSession();
        }
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(String.format("%s:%s", username, password).getBytes()));
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", basicAuth);

        if (devTools != null) {
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
        }
        driver.get(EnvironmentConfig.getApiUrl());
    }

    public static void passAuthorizationAndRefreshPage(String token) {
        driver.get(EnvironmentConfig.getUrl());

        Cookie authCookie = new Cookie.Builder("token", token)
                .path("/")
                .build();

        driver.manage().addCookie(authCookie);
        driver.navigate().refresh();
    }
}
