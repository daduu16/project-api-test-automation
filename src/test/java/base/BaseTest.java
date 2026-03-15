package base;

import config.ApiConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public  class BaseTest {

    @BeforeAll
    static void setup() {
        initRestAssured();
    }

    @BeforeClass(alwaysRun = true)
    public void setupTestNg() {
        initRestAssured();
    }

    private static synchronized void initRestAssured() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addFilter(new AllureRestAssured())
                .build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterAll
    static void tearDown() {
        RestAssured.reset();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownTestNg() {
        RestAssured.reset();
    }
}
