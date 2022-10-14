package org.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(options().stubRequestLoggingDisabled(false).port(8080)); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.addMockServiceRequestListener((request, response) -> LOG.info("received {} request at path  {}", request.getMethod(), request.getAbsoluteUrl()));
        wireMockServer.stubFor(post(urlMatching("/.*")).willReturn(
            aResponse()
                .withFault(Fault.RANDOM_DATA_THEN_CLOSE)
        ));
        wireMockServer.start();
    }

}