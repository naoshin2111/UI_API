package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILink;
import org.openqa.selenium.By;
import java.time.Duration;
import static constant.CommonConstants.COMMON_WAIT_TIME;

public class ProjectPage {

    private static final ILink PROJECT_LINK = AqualityServices.getElementFactory().getLink(By.xpath("//a[normalize-space()='Nexage']"), "nexage");
    private static final ILink PROJECT_ELEMENT_LINK = AqualityServices.getElementFactory().getLink(By.xpath("//a[contains(@href, 'projectId=')]"), "nexage");

    public void openProjectPage() {
        PROJECT_LINK.click();
        PROJECT_LINK.state().waitForDisplayed(Duration.ofSeconds(COMMON_WAIT_TIME));
    }

    public int getProjectIdByName() {
        String projectHref = PROJECT_ELEMENT_LINK.getAttribute("href");
        String projectId = projectHref.split("projectId=")[1].split("&")[0];

        return Integer.parseInt(projectId);
    }
}
