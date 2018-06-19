package com.vciobanu.filteringmatches.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class MatchFilter {
    private Boolean hasPhoto = false;
    private Boolean inContact = false;
    private Boolean isFavourite = false;

    @Range(min = 1, max = 99)
    private Integer minCompatibilityScoreInPercentage;

    @Range(min = 1, max = 99)
    private Integer maxCompatibilityScoreInPercentage;

    @Range(min = 18, max = 95)
    private Integer minAge;

    @Range(min = 18, max = 95)
    private Integer maxAge;

    @Range(min = 135, max = 210)
    private Integer minHeightInCm;

    @Range(min = 135, max = 210)
    private Integer maxHeightInCm;

    @Range(min = 30, max = 300)
    private Double lowerBoundDistanceInKm;

    @Range(min = 30, max = 300)
    private Double upperBoundDistanceInKm;
}