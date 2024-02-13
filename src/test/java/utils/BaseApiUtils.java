package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import config.EnvironmentConfig;
import io.restassured.specification.RequestSpecification;

public class BaseApiUtils {

    private static final String API_BASE_URL = EnvironmentConfig.getApiUrl();

    protected RequestSpecification getBaseRequestSpecification() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri(API_BASE_URL);
    }
}
