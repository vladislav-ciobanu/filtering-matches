package com.vciobanu.filteringmatches.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.vciobanu.filteringmatches.filters.MatchesFilter;
import com.vciobanu.filteringmatches.filters.MatchesFiltersProvider;
import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchSearchFields;
import com.vciobanu.filteringmatches.model.MatchSpecificationsBuilder;
import com.vciobanu.filteringmatches.repository.MatchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MatchingServiceImplTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MatchSpecificationsBuilder matchSpecificationsBuilder;

    @Mock
    private MatchesFiltersProvider matchesFiltersProvider;

    @InjectMocks
    private MatchingServiceImpl matchingService;

    @Mock
    private MatchSearchFields matchSearchFields;

    @Mock
    private MatchesFilter matchesFilter;

    @Mock
    private Specification<Match> matchSpecification;

    @Mock
    private Match match1;

    @Mock
    private Match match2;

    @Test
    public void findMatches() {
        List<Match> matches = Arrays.asList(match1, match2);

        when(matchSpecificationsBuilder.build(matchSearchFields)).thenReturn(matchSpecification);
        when(matchRepository.findAll(matchSpecification)).thenReturn(matches);
        when(matchesFiltersProvider.getMatchesFilters()).thenReturn(Collections.singletonList(matchesFilter));
        when(matchesFilter.filter(matches, matchSearchFields)).thenReturn(Collections.singletonList(match2));

        List<Match> matchList = matchingService.findMatches(matchSearchFields);
        assertEquals(1, matchList.size());
        assertSame(matchList.get(0), match2);

        verify(matchSpecificationsBuilder).build(matchSearchFields);
        verify(matchRepository).findAll(matchSpecification);
        verify(matchesFiltersProvider).getMatchesFilters();
        verify(matchesFilter).filter(matches, matchSearchFields);
        verifyNoMoreInteractions(matchSpecificationsBuilder, matchRepository, matchesFiltersProvider, matchesFilter);
    }
}