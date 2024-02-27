package utils;

import config.WebDriverConfig;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.File;

@UtilityClass
public class ScreenShotUtils {

    private final WebDriver DRIVER = WebDriverConfig.getInstance();

    public void takeScreenshot(String fileWithPath) throws Exception {
        TakesScreenshot scrShot = (TakesScreenshot) DRIVER;
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }
}
