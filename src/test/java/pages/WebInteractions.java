package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import config.EnvironmentConfig;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Headers;
import java.io.File;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WebInteractions {

    private static final By FOOTER_VERSION_LOCATOR = By.xpath("//span[normalize-space()='Version: 2']");
    private static final By NEXAGE_PROJECT = By.xpath("//a[normalize-space()='Nexage']");
    private static final IButton ADD_BUTTON = AqualityServices.getElementFactory().getButton(By.xpath("//a[contains(@class, 'btn') and contains(text(), '+Add')]"), "+Add Button");
    private static final ITextBox PROJECT_NAME_INPUT = AqualityServices.getElementFactory().getTextBox(By.xpath("//input[@id='projectName']"), "Project Name Input");
    private static final IButton SAVE_BUTTON = AqualityServices.getElementFactory().getButton(By.cssSelector("button[type='submit']"), "Save Button");
    private static final String SOURCE_PATH = "src/test/resources/screenshot/test.png";
    private WebDriver driver;
    private DevTools devTools;

    public WebInteractions() {
        driver = AqualityServices.getBrowser().getDriver();
        if (driver instanceof ChromeDriver) {
            devTools = ((ChromeDriver) driver).getDevTools();
            devTools.createSession();
        }
    }

    public void authenticate(String username, String password) {
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(String.format("%s:%s", username, password).getBytes()));
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", basicAuth);

        if (devTools != null) {
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
        }
        driver.get(EnvironmentConfig.getApiUrl());
    }

    public void passAuthorizationAndRefreshPage(String token) {
        driver.get(EnvironmentConfig.getUrl());

        Cookie authCookie = new Cookie.Builder("token", token)
                .path("/")
                .build();

        driver.manage().addCookie(authCookie);
        driver.navigate().refresh();
    }

    public boolean isVersionUpdatedToVariant(int expectedVariantNumber) {
        ILabel versionLabel = AqualityServices.getElementFactory().getLabel(FOOTER_VERSION_LOCATOR, "Footer Version");
        String versionText = versionLabel.getText();
        String expectedVersionText = "Version: " + expectedVariantNumber;

        return versionText.equals(expectedVersionText);
    }

    public void openProjectPage(String projectName) {
        ILink projectLink = AqualityServices.getElementFactory().getLink(NEXAGE_PROJECT, "nexage");
        projectLink.click();
        projectLink.state().waitForDisplayed(Duration.ofSeconds(10));
    }

    public void addNewProject(String projectName) {
        ADD_BUTTON.click();
        driver.get(EnvironmentConfig.getProjectUrl());
        PROJECT_NAME_INPUT.clearAndType(projectName);
        SAVE_BUTTON.click();

        AqualityServices.getBrowser().executeScript("closePopUp();");
        AqualityServices.getBrowser().refresh();
    }

    public void takeScreenshot() throws Exception {
        driver.get(EnvironmentConfig.getUrl());
        this.takeSnapShot(driver, SOURCE_PATH) ;
    }

    public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);

    }
}
