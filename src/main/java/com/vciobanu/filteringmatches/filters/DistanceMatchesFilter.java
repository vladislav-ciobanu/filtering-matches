package com.vciobanu.filteringmatches.filters;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchSearchFields;
import com.vciobanu.filteringmatches.repository.MatchRepository;
import com.vciobanu.filteringmatches.util.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DistanceMatchesFilter implements MatchesFilter {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private GeoUtil geoUtil;

    @Override
    public List<Match> filter(List<Match> matches, MatchSearchFields matchSearchFields) {
        if (matches.size() == 0 || matchSearchFields == null
                || (matchSearchFields.getLowerBoundDistanceInKm() == null
                && matchSearchFields.getUpperBoundDistanceInKm() == null)) {
            return matches;
        }

        Double lowerBoundDistance = matchSearchFields.getLowerBoundDistanceInKm();
        Double upperBoundDistance = matchSearchFields.getUpperBoundDistanceInKm();

        Match loggedUser = getFirstMatchAsLoggedUser();
        Stream<Match> matchStream = matches.stream().filter(match -> !loggedUser.getId().equals(match.getId()));

        if (lowerBoundDistance != null) {
            matchStream = matchStream.filter(match
                    -> geoUtil.calculateDistanceInKm(match.getCity(), loggedUser.getCity()) >= lowerBoundDistance);
        }

        if (upperBoundDistance != null) {
            matchStream = matchStream.filter(match
                    -> geoUtil.calculateDistanceInKm(match.getCity(), loggedUser.getCity()) <= upperBoundDistance);
        }

        return matchStream.collect(Collectors.toList());
    }

    private Match getFirstMatchAsLoggedUser() {
        return matchRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
    }
}
