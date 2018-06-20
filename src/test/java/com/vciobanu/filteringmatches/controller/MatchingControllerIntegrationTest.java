package com.vciobanu.filteringmatches.controller;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MatchingControllerIntegrationTest {

    private static final String URL_PATTERN = "http://localhost:%d/match";

    private String serviceUrl;

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setup() {
        serviceUrl = String.format(URL_PATTERN, port);

        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    return execution.execute(request, body);
                }));
    }

    @Test
    public void testFindAllMatches() {
        ResponseEntity<List> response = restTemplate.getForEntity(serviceUrl, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(25, response.getBody().size());
    }

    @Test
    public void testNoMatchFound() {
        Map<String, String> params = new HashMap<>();
        params.put("minAge", "95");

        ResponseEntity<List> response = restTemplate.getForEntity(serviceUrl + "?minAge={minAge}", List.class, params);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testFilterMatches() {
        Map<String, String> params = new HashMap<>();
        params.put("hasPhoto", "true");
        params.put("isFavourite", "true");
        params.put("inContact", "true");
        params.put("minHeightInCm", "170");
        params.put("maxHeightInCm", "180");
        params.put("minAge", "30");
        params.put("maxAge", "40");
        params.put("minCompatibilityScoreInPercentage", "98");

        String queryParams = "?hasPhoto={hasPhoto}&isFavourite={isFavourite}&inContact={inContact}"
                + "&minHeightInCm={minHeightInCm}&maxHeightInCm={maxHeightInCm}&minAge={minAge}&maxAge={maxAge}"
                + "&minCompatibilityScoreInPercentage={minCompatibilityScoreInPercentage}";

        ResponseEntity<List> response = restTemplate.getForEntity(serviceUrl + queryParams, List.class, params);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(1, response.getBody().size());

        Map match = (Map) response.getBody().get(0);

        assertFalse(match.get("main_photo").toString().isEmpty());
        assertTrue((boolean) match.get("favourite"));
        assertTrue((int) match.get("contacts_exchanged") > 0);
        assertTrue((int) match.get("height_in_cm") >= 170);
        assertTrue((int) match.get("height_in_cm") <= 180);
        assertTrue((int) match.get("age") >= 30);
        assertTrue((int) match.get("age") <= 40);
        assertTrue((double) match.get("compatibility_score") >= 0.98);
    }
}