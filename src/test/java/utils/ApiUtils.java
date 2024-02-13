package utils;

import constant.Endpoints;
import constant.Parameters;
import io.restassured.response.Response;
import models.ApiVariant;
import models.Test;
import org.apache.http.HttpStatus;
import java.util.List;

public class ApiUtils extends BaseApiUtils {

    public String generateToken(ApiVariant apiVariant) {
        Response response = getBaseRequestSpecification()
                .queryParam(Parameters.VARIENT, apiVariant.getVariantNumber())
                .when()
                .post(Endpoints.GET_TOKEN)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        return response.asString();
    }

    public List<Test> getTestsForProject(int projectId) {
        Response response = getBaseRequestSpecification()
                .queryParam(Parameters.PROJECT_ID, projectId)
                .when()
                .post(Endpoints.GET_TEST_JSON)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        return response.jsonPath().getList("", Test.class);
    }

    public String createTest(String sId, String projectName, String testName, String methodName, String env) {
        Response response = getBaseRequestSpecification()
                .queryParam(Parameters.SESSION_ID, sId)
                .queryParam(Parameters.PROJECT_NAME, projectName)
                .queryParam(Parameters.TEST_NAME, testName)
                .queryParam(Parameters.METHOD_NAME, methodName)
                .queryParam(Parameters.ENVIRONMENT, env)
                .when()
                .post(Endpoints.PUT_TEST)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        return response.asString();
    }

    public void sendTestLog(String testId, String content) {
        getBaseRequestSpecification()
                .queryParam(Parameters.TEST_ID, testId)
                .queryParam(Parameters.CONTENT, content)
                .when()
                .post(Endpoints.PUT_TEST_LOG)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public void sendTestAttachment(String testId, String content, String contentType) {
        getBaseRequestSpecification()
                .contentType(Parameters.URL_ENCODED_CONTENT)
                .formParam(Parameters.TEST_ID, testId)
                .formParam(Parameters.CONTENT, content)
                .formParam(Parameters.CONTENT_TYPE, contentType)
                .when()
                .post(Endpoints.PUT_TEST_ATTACHMENT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
