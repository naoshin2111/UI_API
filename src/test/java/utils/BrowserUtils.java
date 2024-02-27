package utils;

import aquality.selenium.browser.AqualityServices;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BrowserUtils {

    public static void goBackToPreviousPage() {
        AqualityServices.getBrowser().goBack();
    }
}
