package tests.endpoint.negative;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import models.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.generators.RandomTokenGenerator;

import static tests.endpoint.steps.Steps.*;

@Epic("Универсальный метод взаимодействия с сервисом")
@Feature("Некорректное взаимодействие")
public class NegativeTest {

    private String validToken;

    @BeforeEach
    public void setUp() {
        validToken = RandomTokenGenerator.generateValidToken();
    }

    @Test
    @DisplayName("Запрос с неверным Action")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithInvalidActionTest() {
        Response response = invalidActionRequest(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertInvalidActionResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @Test
    @DisplayName("Запрос с неверным ContentType")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithoutContentTypeTest() {
        Response response = invalidContentTypeRequest(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertInvalidContentTypeResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }
}
