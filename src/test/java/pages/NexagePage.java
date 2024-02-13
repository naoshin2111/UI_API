package pages;

import aquality.selenium.browser.AqualityServices;

public class NexagePage {

    public void goBackToPreviousPage() {
        AqualityServices.getBrowser().goBack();
    }
}
