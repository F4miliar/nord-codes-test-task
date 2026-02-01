package tests.endpoint.login;

import io.qameta.allure.*;
import io.restassured.response.Response;
import models.dto.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.generators.RandomTokenGenerator;
import utils.mocks.WireMockSetup;

import static tests.endpoint.steps.Steps.*;

@Epic("Универсальный метод взаимодействия с сервисом")
@Feature("Аутентификация")
public class LoginTest extends WireMockSetup {

    @ParameterizedTest(name = "Аутентификация с токеном {0}")
    @DisplayName("Тест успешной аутентификации с разными валидными токенами")
    @Severity(SeverityLevel.CRITICAL)
    @ValueSource(strings = {
            "00000000000000000000000000000000",
            "99999999999999999999999999999999",
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ"
    })
    public void successfulLoginTest(String token) {
        authSuccessStub(token);

        Response response = logInWithToken(token);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertSuccessResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @Test
    @DisplayName("Повторная аутентификация после завершения сессии")
    @Severity(SeverityLevel.CRITICAL)
    public void successfulRepeatedLoginTest() {
        String validToken = RandomTokenGenerator.generateValidToken();
        authSuccessStub(validToken);

        logInWithToken(validToken);
        sendLogoutRequest(validToken);
        Response response = logInWithToken(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertSuccessResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @ParameterizedTest(name = "Аутентификация с токеном {0}")
    @DisplayName("Тест неуспешной аутентификации с разными невалидными токенами")
    @Severity(SeverityLevel.CRITICAL)
    @ValueSource(strings = {
            "000000000000000000000000000000000",
            "9999999999999999999999999999999",
            "*0000000000000000000000000000000",
            "0000000000000000000000000000000/",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
    })
    public void unsuccessfulLoginTest(String token) {
        authSuccessStub(token);

        Response response = logInWithToken(token);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertInvalidTokenResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @Test
    @DisplayName("Аутентификация без заголовка X-API-KEY")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithoutApiKeyTest() {
        String validToken = RandomTokenGenerator.generateValidToken();
        authSuccessStub(validToken);
        actionSuccessStub(validToken);

        Response response = logInWithoutApiKeyToken(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertInvalidApiKeyResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @Test
    @DisplayName("Аутентификация c некорректным заголовком X-API-KEY")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithInvalidApiKeyTest() {
        String validToken = RandomTokenGenerator.generateValidToken();
        authSuccessStub(validToken);
        actionSuccessStub(validToken);

        Response response = logInWithInvalidApiKeyToken(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertInvalidApiKeyResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @Test
    @DisplayName("Аутентификация c пустым токеном")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithoutTokenTest() {
        authSuccessStub();

        Response response = logInWithoutToken();
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertNullTokenResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }

    @Test
    @DisplayName("Аутентификация c ошибкой внешнего сервиса")
    @Severity(SeverityLevel.CRITICAL)
    public void loginMockErrorTest() {
        String validToken = RandomTokenGenerator.generateValidToken();
        authFailureStub(validToken, 500);

        Response response = mockErrorRequest(validToken);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertMockErrorResponse(response.statusCode(), apiResponse.getResult(), apiResponse.getMessage());
    }
}
