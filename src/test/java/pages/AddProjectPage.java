package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import config.EnvironmentConfig;
import config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddProjectPage {

    private static final IButton ADD_BUTTON = AqualityServices.getElementFactory().getButton(By.xpath("//a[contains(@class, 'btn') and contains(text(), '+Add')]"), "+Add Button");
    private static final ITextBox PROJECT_NAME_INPUT = AqualityServices.getElementFactory().getTextBox(By.xpath("//input[@id='projectName']"), "Project Name Input");
    private static final IButton SAVE_BUTTON = AqualityServices.getElementFactory().getButton(By.cssSelector("button[type='submit']"), "Save Button");
    private WebDriver driver = WebDriverConfig.getInstance();

    public void addNewProject(String projectName) {
        ADD_BUTTON.click();
        driver.get(EnvironmentConfig.getProjectUrl());
        PROJECT_NAME_INPUT.clearAndType(projectName);
        SAVE_BUTTON.click();

        AqualityServices.getBrowser().refresh();
    }
}
