package test;

import models.ApiVariant;
import models.Test;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import pages.NexagePage;
import pages.WebInteractions;
import utils.ApiUtils;
import utils.RandomUtils;
import java.io.File;
import java.util.Base64;
import java.util.List;

public class UiApiTest extends BaseTest {

    private static final int VARIANT = 2;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String SID = "1234";
    private static final String PROJECT_NAME = "ALPHA";
    private static final String PROJECT = "Nexage";
    private static final String TEST_NAME = "test";
    private static final String METHOD_NAME = "method";
    private static final String ENVIRONMENT = "env";
    private static final String TEST_ID = "324";
    private static final String CONTENT = RandomUtils.generateRandomText(10);
    private static final String SCREEN_SHOT_PATH = "src/test/resources/screenshot/test.png";
    private static final String CONTENT_TYPE = "image/png";

    @org.testng.annotations.Test
    public void testGenerateToken() throws Exception {
        ApiUtils apiUtils = new ApiUtils();
        ApiVariant apiVariant = new ApiVariant(VARIANT);
        String token = apiUtils.generateToken(apiVariant);
        Assert.assertNotNull(token, "Token was not generated");

        WebInteractions webInteractions = new WebInteractions();
        webInteractions.authenticate(LOGIN, PASSWORD);
        webInteractions.passAuthorizationAndRefreshPage(token);

        boolean isVersionCorrect = webInteractions.isVersionUpdatedToVariant(VARIANT);
        Assert.assertTrue(isVersionCorrect, "Version number did not match the expected variant number.");

        webInteractions.openProjectPage(PROJECT);

        int projectId = 1;
        List<Test> tests = apiUtils.getTestsForProject(projectId);
        Assert.assertTrue(tests.size() > 0, "Lists are not fetched correctly");

        NexagePage nexagePage = new NexagePage();
        nexagePage.goBackToPreviousPage();

        webInteractions.addNewProject(PROJECT_NAME);

        apiUtils.sendTestLog(TEST_ID, CONTENT);

        webInteractions.takeScreenshot();
        byte[] fileContent = FileUtils.readFileToByteArray(new File(SCREEN_SHOT_PATH));
        String content = Base64.getEncoder().encodeToString(fileContent);
        apiUtils.sendTestAttachment(TEST_ID, content, CONTENT_TYPE);

        String recordId = apiUtils.createTest(SID,PROJECT_NAME,TEST_NAME, METHOD_NAME,ENVIRONMENT);
        Assert.assertNotNull(recordId, "Record id is not present");
    }
}
