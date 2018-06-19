package com.vciobanu.filteringmatches.init;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.repository.MatchRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class MatchesLoader {

    private static final String MATCHES_RESOURCE = "/matches.json";

    @Autowired
    private MatchRepository matchRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {
        InputStream matchesStream = getClass().getResourceAsStream(MATCHES_RESOURCE);

        Matches matches = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(matchesStream, Matches.class);

        matchRepository.saveAll(matches.getMatches());
    }

    @Data
    private static class Matches {
        private List<Match> matches;
    }
}
