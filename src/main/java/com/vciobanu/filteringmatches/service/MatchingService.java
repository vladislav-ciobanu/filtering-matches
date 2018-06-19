package com.vciobanu.filteringmatches.service;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchFilter;

import java.util.List;

public interface MatchingService {
    List<Match> findMatches(MatchFilter matchFilter);
}
