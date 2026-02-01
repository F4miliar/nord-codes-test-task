package tests.endpoint.action;

import io.qameta.allure.*;
import io.restassured.response.Response;
import models.dto.ApiResponse;
import org.junit.jupiter.api.*;
import utils.generators.RandomTokenGenerator;
import utils.mocks.WireMockSetup;

import static tests.endpoint.steps.Steps.*;

@Epic("Универсальный метод взаимодействия с сервисом")
@Feature("Действие")
public class ActionTest extends WireMockSetup {

    private String validToken;

    @BeforeEach
    public void setUp() {
        validToken = RandomTokenGenerator.generateValidToken();
    }

    @Test
    @DisplayName("Успешное выполнение действия после авторизации")
    @Severity(SeverityLevel.CRITICAL)
    public void successfulActionAfterLoginTest() {
        authSuccessStub(validToken);
        actionSuccessStub(validToken);

        logInWithToken(validToken);
        Response response = sendActionRequest(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertSuccessResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @Test
    @DisplayName("Выполнение действия с ошибкой без авторизации")
    @Severity(SeverityLevel.CRITICAL)
    public void actionWithoutLoginTest() {
        authSuccessStub(validToken);
        actionSuccessStub(validToken);

        Response response = sendActionRequest(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertTokenNotFoundResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage(), validToken);
    }

    @Test
    @DisplayName("Ошибка внешнего сервиса при выполнении действия")
    @Severity(SeverityLevel.CRITICAL)
    public void actionMockErrorTest() {
        authSuccessStub(validToken);
        actionFailureStub(validToken, 500);

        logInWithToken(validToken);
        Response response = sendActionRequest(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertMockErrorResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }
}