package com.vciobanu.filteringmatches.filters;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchSearchFields;

import java.util.List;

public interface MatchesFilter {
    List<Match> filter(List<Match> matches, MatchSearchFields matchSearchFields);
}
