package com.vciobanu.filteringmatches.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class City {
    private String name;
    private Double lat;
    private Double lon;
}
