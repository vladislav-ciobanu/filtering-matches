package com.vciobanu.filteringmatches.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchesFiltersProvider {

    @Autowired
    private List<MatchesFilter> matchesFilters;

    public List<MatchesFilter> getMatchesFilters() {
        return matchesFilters;
    }
}
