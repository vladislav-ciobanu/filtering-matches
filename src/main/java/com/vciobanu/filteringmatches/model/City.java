package com.vciobanu.filteringmatches.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class City {
    private String name;
    private Double lat;
    private Double lon;
}
