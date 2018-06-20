package com.vciobanu.filteringmatches.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vciobanu.filteringmatches.model.City;
import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchSearchFields;
import com.vciobanu.filteringmatches.service.MatchingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MatchingController.class, secure = false)
public class MatchingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchingService matchingService;

    @MockBean
    private MatchSearchFields matchSearchFields;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void match() throws Exception {
        City city = new City();
        city.setName("Berlin");

        Match match1 = new Match();
        match1.setCity(city);
        match1.setAge(19);
        match1.setDisplayName("Foo");

        Match match2 = new Match();
        match2.setJobTitle("Dev");

        List<Match> matches = Arrays.asList(match1, match2);

        when(matchingService.findMatches(Mockito.any(MatchSearchFields.class))).thenReturn(matches);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/match")
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        JSONAssert.assertEquals(objectMapper.writeValueAsString(matches), response.getContentAsString(), true);
    }
}