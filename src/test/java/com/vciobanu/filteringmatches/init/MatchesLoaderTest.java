package com.vciobanu.filteringmatches.init;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.repository.MatchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MatchesLoaderTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchesLoader matchesLoader;

    @Mock
    private ContextRefreshedEvent event;

    @Captor
    private ArgumentCaptor<List<Match>> matchesCaptor;

    @Test
    public void onApplicationEvent() throws IOException {
        matchesLoader.onApplicationEvent(event);

        verify(matchRepository).saveAll(matchesCaptor.capture());
        verifyNoMoreInteractions(matchRepository);

        List<Match> matches = matchesCaptor.getValue();

        assertEquals(25, matches.size());
    }
}