package tests.endpoint.logout;

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
import utils.mocks.WireMockSetup;

import static tests.endpoint.steps.Steps.*;

@Epic("Универсальный метод взаимодействия с сервисом")
@Feature("Завершение сессии юзера")
public class LogoutTest extends WireMockSetup {

    private String validToken;

    @BeforeEach
    public void setUp() {
        validToken = RandomTokenGenerator.generateValidToken();
    }

    @Test
    @DisplayName("Успешный завершение сессии после аутентификации")
    @Severity(SeverityLevel.CRITICAL)
    public void successfulLogoutAfterLoginTest() {
        authSuccessStub(validToken);

        logInWithToken(validToken);
        Response response = sendLogoutRequest(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertSuccessResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @Test
    @DisplayName("Повторное завершение сессии")
    @Severity(SeverityLevel.NORMAL)
    public void repeatedLogoutAfterLoginTest() {
        authSuccessStub(validToken);

        logInWithToken(validToken);
        sendLogoutRequest(validToken);

        Response response = sendLogoutRequest(validToken);
        ApiResponse apiResponse  = response.as(ApiResponse.class);

        assertTokenNotFoundResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage(), validToken);
    }
}
