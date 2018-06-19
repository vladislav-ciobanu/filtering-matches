package com.vciobanu.filteringmatches.service;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchFilter;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MatchingServiceImpl implements MatchingService {

    @Override
    public List<Match> findMatches(MatchFilter matchFilter) {
        return Collections.emptyList();
    }
}
