package com.vciobanu.filteringmatches.service;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchFilter;
import com.vciobanu.filteringmatches.model.MatchSpecificationsBuilder;
import com.vciobanu.filteringmatches.repository.MatchRepository;
import com.vciobanu.filteringmatches.util.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MatchingServiceImpl implements MatchingService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchSpecificationsBuilder matchSpecificationsBuilder;

    @Override
    public List<Match> findMatches(MatchFilter matchFilter) {
        List<Match> matches = matchRepository.findAll(matchSpecificationsBuilder.build(matchFilter));

        return filterMatchesByDistanceCriteria(matches, matchFilter);
    }

    private List<Match> filterMatchesByDistanceCriteria(List<Match> matches, MatchFilter matchFilter) {
        if (matches.size() == 0 || matchFilter == null
                || (matchFilter.getLowerBoundDistanceInKm() == null
                && matchFilter.getUpperBoundDistanceInKm() == null)) {
            return matches;
        }

        Double lowerBoundDistance = matchFilter.getLowerBoundDistanceInKm();
        Double upperBoundDistance = matchFilter.getUpperBoundDistanceInKm();

        Match loggedUser = getFirstMatchAsLoggedUser();
        Stream<Match> matchStream = matches.stream();

        if (lowerBoundDistance != null) {
            matchStream = matchStream.filter(match
                    -> GeoUtil.calculateDistanceInKm(match.getCity(), loggedUser.getCity()) >= lowerBoundDistance);
        }

        if (upperBoundDistance != null) {
            matchStream = matchStream.filter(match
                    -> GeoUtil.calculateDistanceInKm(match.getCity(), loggedUser.getCity()) <= upperBoundDistance);
        }

        return matchStream.collect(Collectors.toList());
    }

    private Match getFirstMatchAsLoggedUser() {
        return matchRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
    }
}
