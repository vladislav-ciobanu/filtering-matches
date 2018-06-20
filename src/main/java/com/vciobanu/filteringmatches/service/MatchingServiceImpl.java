package com.vciobanu.filteringmatches.service;

import com.vciobanu.filteringmatches.filters.MatchesFilter;
import com.vciobanu.filteringmatches.filters.MatchesFiltersProvider;
import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchSearchFields;
import com.vciobanu.filteringmatches.model.MatchSpecificationsBuilder;
import com.vciobanu.filteringmatches.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingServiceImpl implements MatchingService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchSpecificationsBuilder matchSpecificationsBuilder;

    @Autowired
    private MatchesFiltersProvider matchesFiltersProvider;

    @Override
    public List<Match> findMatches(MatchSearchFields matchSearchFields) {
        List<Match> matches = matchRepository.findAll(matchSpecificationsBuilder.build(matchSearchFields));

        for (MatchesFilter matchesFilter : matchesFiltersProvider.getMatchesFilters()) {
            matches = matchesFilter.filter(matches, matchSearchFields);
        }

        return matches;
    }
}
