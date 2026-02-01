package tests.endpoint.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.dto.ApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.clients.ApiClient;

import static models.enums.Actions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Steps {

    private static final Logger logger = LoggerFactory.getLogger(Steps.class);

    @Step("Отправление запроса на аутентификацию")
    public static Response logInWithToken(String token) {
        logger.info("Отправление запроса на аутентификацию с токеном {}", token);
        ApiRequest request = new ApiRequest(token, LOGIN.getAction());
        Response response = ApiClient.sendRequest(request);
        logResponse(response);
        return response;
    }

    private static void logResponse(Response response) {
        logger.info("Получен ответ\n{}", response.getBody().asPrettyString());
    }

    @Step("Убедиться, что запрос успешен")
    public static void assertSuccessResponse(int statusCode, String result, String message) {
        assertEquals(200, statusCode, "Получена ошибка " + message);
        assertEquals("OK", result);
    }

    @Step("Отправление запроса на совершение действия")
    public static Response sendActionRequest(String token) {
        logger.info("Отправление запроса на совершение действия с токеном {}", token);
        ApiRequest request = new ApiRequest(token, ACTION.getAction());
        Response response = ApiClient.sendRequest(request);
        logResponse(response);
        return response;
    }

    @Step("Отправление запроса на завершение сессии")
    public static Response sendLogoutRequest(String token) {
        logger.info("Отправление запроса на завершение сессии с токеном {}", token);
        ApiRequest request = new ApiRequest(token, LOGOUT.getAction());
        Response response = ApiClient.sendRequest(request);
        logResponse(response);
        return response;
    }

    @Step("Убедиться, что токен не найден")
    public static void assertTokenNotFoundResponse(int statusCode, String result, String message, String token) {
        assertEquals(403, statusCode, "Получена ошибка " + message);
        assertEquals("ERROR", result);
        assertEquals("Token '" + token + "' not found", message);
    }

    @Step("Убедиться, что токен некорректный")
    public static void assertInvalidTokenResponse(int statusCode, String result, String message) {
        assertEquals(400, statusCode, "Получена ошибка " + message);
        assertEquals("ERROR", result);
        assertEquals("token: должно соответствовать \"^[0-9A-Z]{32}$\"", message);
    }

    @Step("Отправление запроса на аутентификацию без заголовка X-API-KEY")
    public static Response logInWithoutApiKeyToken(String token) {
        logger.info("Отправление запроса на аутентификацию с токеном {} без заголовка X-API-KEY", token);
        ApiRequest request = new ApiRequest(token, LOGIN.getAction());
        Response response = ApiClient.sendRequestWithoutApiKey(request);
        logResponse(response);
        return response;
    }

    @Step("Отправление запроса на аутентификацию c некорректным заголовком X-API-KEY")
    public static Response logInWithInvalidApiKeyToken(String token) {
        logger.info("Отправление запроса на аутентификацию с токеном {} некорректным заголовком X-API-KEY", token);
        ApiRequest request = new ApiRequest(token, LOGIN.getAction());
        Response response = ApiClient.sendRequestWithInvalidApiKey(request);
        logResponse(response);
        return response;
    }

    @Step("Убедиться, что сервис отвечает ошибкой с некорректным API Key")
    public static void assertInvalidApiKeyResponse(int statusCode, String result, String message) {
        assertEquals(401, statusCode, "Получена ошибка " + message);
        assertEquals("ERROR", result);
        assertEquals("Missing or invalid API Key", message);
    }

    @Step("Отправление запроса на аутентификацию без токена")
    public static Response logInWithoutToken() {
        logger.info("Отправление запроса на аутентификацию без токена");
        ApiRequest request = new ApiRequest(null, LOGIN.getAction());
        Response response = ApiClient.sendRequestWithoutToken(request);
        logResponse(response);
        return response;
    }

    @Step("Убедиться, что сервис отвечает ошибкой с пустым токеном")
    public static void assertNullTokenResponse(int statusCode, String result, String message) {
        assertEquals(400, statusCode, "Получена ошибка " + message);
        assertEquals("ERROR", result);
        assertEquals("token: не должно равняться null", message);
    }

    @Step("Отправление запроса с неверным Action")
    public static Response invalidActionRequest(String token) {
        logger.info("Отправление запроса с неверным Action");
        ApiRequest request = new ApiRequest(token, LOGIN.getAction());
        Response response = ApiClient.sendRequestWithInvalidAction(request);
        logResponse(response);
        return response;
    }

    @Step("Убедиться, что сервис отвечает ошибкой с неверным Action")
    public static void assertInvalidActionResponse(int statusCode, String result, String message) {
        assertEquals(400, statusCode, "Получена ошибка " + message);
        assertEquals("ERROR", result);
        assertEquals("action: invalid action 'INVALID'. Allowed: LOGIN, LOGOUT, ACTION", message);
    }


    @Step("Отправление запроса с неверным ContentType")
    public static Response invalidContentTypeRequest(String token) {
        logger.info("Отправление запроса с неверным ContentType");
        ApiRequest request = new ApiRequest(token, LOGIN.getAction());
        Response response = ApiClient.sendRequestWithInvalidContentType(request);
        logResponse(response);
        return response;
    }

    @Step("Убедиться, что сервис отвечает ошибкой с неверным ContentType")
    public static void assertInvalidContentTypeResponse(int statusCode, String result, String message) {
        assertEquals(400, statusCode, "Получена ошибка " + message);
        assertEquals("ERROR", result);
        assertEquals("ContentType: invalid ContentType 'application/json'. Allowed: application/x‐www‐form‐urlencoded ", message);
    }

    @Step("Отправление запроса с ожидаемой ошибкой")
    public static Response mockErrorRequest(String token) {
        logger.info("Отправление запроса с ожидаемой ошибкой");
        ApiRequest request = new ApiRequest(token, LOGIN.getAction());
        Response response = ApiClient.sendRequest(request);
        logResponse(response);
        return response;
    }

    @Step("Убедиться, что сервис отвечает внутренней ошибкой")
    public static void assertMockErrorResponse(int statusCode, String result, String message) {
        assertEquals(500, statusCode, "Получена ошибка " + message);
        assertEquals("ERROR", result);
        assertEquals("Internal Server Error", message);
    }
}