package test;

import config.TestUserConfig;
import models.ApiVariant;
import models.TestModel;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;
import org.testng.Assert;
import pages.AddProjectPage;
import pages.ProjectPage;
import pages.ProjectsPage;
import pages.TestsPage;
import utils.*;
import java.io.File;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UiApiTest extends BaseTest {

    private static final int VARIANT = 2;
    private static final String LOGIN = TestUserConfig.getLogin();
    private static final String PASSWORD = TestUserConfig.getPassword();
    private static final String SID = RandomUtils.generateRandomNumber(4);
    private static final String PROJECT_NAME = RandomUtils.generateRandomText(5);
    private static final String TEST_NAME = RandomUtils.generateRandomText(8);
    private static final String METHOD_NAME = RandomUtils.generateRandomText(10);
    private static final String ENVIRONMENT = "env";
    private static final String CONTENT = RandomUtils.generateRandomText(10);
    private static final String SCREEN_SHOT_PATH = "src/test/resources/screenshot/test.png";
    private static final String CONTENT_TYPE = "image/png";

    @Test
    public void testGenerateToken() throws Exception {

        ApiUtils apiUtils = new ApiUtils();
        ApiVariant apiVariant = new ApiVariant(VARIANT);
        String token = apiUtils.generateToken(apiVariant);
        Assert.assertNotNull(token, "Token was not generated");

        AuthenticationUtils.authenticate(LOGIN, PASSWORD);
        AuthenticationUtils.passAuthorizationAndRefreshPage(token);

        ProjectsPage projectsPage = new ProjectsPage();
        boolean isVersionCorrect = projectsPage.isVersionUpdatedToVariant(VARIANT);
        Assert.assertTrue(isVersionCorrect, "Version number did not match the expected variant number.");

        ProjectPage projectPage = new ProjectPage();
        projectPage.openProjectPage();

        int projectId = projectPage.getProjectIdByName();
        List<TestModel> tests = apiUtils.getTestsForProject(projectId);

        TestsPage testsPage = new TestsPage();
        boolean areTestsInOrder = testsPage.areTestsInDescendingOrder();
        Assert.assertTrue(areTestsInOrder, "Tests are not in descending order.");

        List<Date> apiDates = tests.stream()
                .map(TestModel::getStartTime)
                .map(DateUtils::parseStringAsDate)
                .limit(testsPage.startTimeColumnSize())
                .collect(Collectors.toList());

        List<Date> uiDates = testsPage.fetchTestStartTimeFromUI()
                .stream()
                .map(DateUtils::parseStringAsDate)
                .collect(Collectors.toList());

        Assert.assertEquals(uiDates, apiDates, "The list of tests fetched from the UI does not match the list fetched from the API.");

        BrowserUtils.goBackToPreviousPage();

        AddProjectPage addProjectPage = new AddProjectPage();
        addProjectPage.addNewProject(PROJECT_NAME);

        BrowserUtils.goBackToPreviousPage();

        ScreenShotUtils.takeScreenshot(SCREEN_SHOT_PATH);
        String recordId = apiUtils.createTest(SID,PROJECT_NAME,TEST_NAME, METHOD_NAME,ENVIRONMENT);
        Assert.assertNotNull(recordId, "Record id is not present");

        byte[] fileContent = FileUtils.readFileToByteArray(new File(SCREEN_SHOT_PATH));
        String content = Base64.getEncoder().encodeToString(fileContent);
        apiUtils.sendTestAttachment(recordId, content, CONTENT_TYPE);

        apiUtils.sendTestLog(recordId, CONTENT);
    }
}
