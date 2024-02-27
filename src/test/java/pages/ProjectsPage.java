package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

public class ProjectsPage {

    private static final By FOOTER_VERSION_LOCATOR = By.xpath("//footer//span[starts-with(normalize-space(text()), 'Version:')]");

    public boolean isVersionUpdatedToVariant(int expectedVariantNumber) {
        ILabel versionLabel = AqualityServices.getElementFactory().getLabel(FOOTER_VERSION_LOCATOR, "Footer Version");
        String versionText = versionLabel.getText();
        String versionNumberStr = versionText.split(":")[1].trim();
        int versionNumber = Integer.parseInt(versionNumberStr);
        return versionNumber == expectedVariantNumber;
    }

}
