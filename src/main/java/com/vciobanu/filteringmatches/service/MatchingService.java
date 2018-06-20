package com.vciobanu.filteringmatches.service;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchSearchFields;

import java.util.List;

public interface MatchingService {
    List<Match> findMatches(MatchSearchFields matchSearchFields);
}
