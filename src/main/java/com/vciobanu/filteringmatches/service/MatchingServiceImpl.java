package com.vciobanu.filteringmatches.service;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchFilter;
import com.vciobanu.filteringmatches.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingServiceImpl implements MatchingService {

    @Autowired
    private MatchRepository matchRepository;

    @Override
    public List<Match> findMatches(MatchFilter matchFilter) {
        return matchRepository.findAll();
    }
}
