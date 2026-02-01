package utils.clients;

import models.dto.ApiRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String API_KEY = "qazWSXedc";

    public static Response sendRequest(ApiRequest request) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("X-Api-Key", API_KEY)
                .contentType(ContentType.URLENC)
                .formParam("token", request.token())
                .formParam("action", request.action())
                .accept(ContentType.JSON)
                .post("/endpoint");
    }

    public static Response sendRequestWithoutApiKey(ApiRequest request) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.URLENC)
                .formParam("token", request.token())
                .formParam("action", request.action())
                .accept(ContentType.JSON)
                .post("/endpoint");
    }

    public static Response sendRequestWithInvalidApiKey(ApiRequest request) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("X-Api-Key", "INVALID_API_KEY")
                .contentType(ContentType.URLENC)
                .formParam("token", request.token())
                .formParam("action", request.action())
                .accept(ContentType.JSON)
                .post("/endpoint");
    }

    public static Response sendRequestWithoutToken(ApiRequest request) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("X-Api-Key", API_KEY)
                .contentType(ContentType.URLENC)
                .formParam("action", request.action())
                .accept(ContentType.JSON)
                .post("/endpoint");
    }

    public static Response sendRequestWithInvalidAction(ApiRequest request) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("X-Api-Key", API_KEY)
                .contentType(ContentType.URLENC)
                .formParam("token", request.token())
                .formParam("action", "INVALID")
                .accept(ContentType.JSON)
                .post("/endpoint");
    }

    public static Response sendRequestWithInvalidContentType(ApiRequest request) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("X-Api-Key", API_KEY)
                .contentType(ContentType.JSON)
                .formParam("token", request.token())
                .formParam("action", request.action())
                .accept(ContentType.JSON)
                .post("/endpoint");
    }
}