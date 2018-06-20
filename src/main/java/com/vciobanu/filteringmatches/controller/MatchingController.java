package com.vciobanu.filteringmatches.controller;

import com.vciobanu.filteringmatches.model.Match;
import com.vciobanu.filteringmatches.model.MatchSearchFields;
import com.vciobanu.filteringmatches.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @GetMapping(
            value = "/match", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Match> match(@Valid MatchSearchFields matchSearchFields) {
        return matchingService.findMatches(matchSearchFields);
    }
}