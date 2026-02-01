package utils.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockSetup {

    protected static WireMockServer wireMockServer;
    protected static final int WIREMOCK_PORT = 8888;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(WIREMOCK_PORT);
        wireMockServer.start();
        WireMock.configureFor("localhost", WIREMOCK_PORT);
    }

    @AfterAll
    static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    protected void authSuccessStub(String token) {
        stubFor(post(urlEqualTo("/auth"))
                .withRequestBody(containing("token=" + token))
                .willReturn(aResponse().withStatus(200)));
    }

    protected void authSuccessStub() {
        stubFor(post(urlEqualTo("/auth"))
                .willReturn(aResponse().withStatus(200)));
    }

    protected void authFailureStub(String token, int statusCode) {
        stubFor(post(urlEqualTo("/auth"))
                .withRequestBody(containing("token=" + token))
                .willReturn(aResponse().withStatus(statusCode)));
    }

    protected void actionSuccessStub(String token) {
        stubFor(post(urlEqualTo("/doAction"))
                .withRequestBody(containing("token=" + token))
                .willReturn(aResponse().withStatus(200)));
    }

    protected void actionFailureStub(String token, int statusCode) {
        stubFor(post(urlEqualTo("/doAction"))
                .withRequestBody(containing("token=" + token))
                .willReturn(aResponse().withStatus(statusCode)));
    }
}