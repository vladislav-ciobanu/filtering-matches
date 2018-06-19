package com.vciobanu.filteringmatches.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Match {
    @JsonProperty("display_name")
    private String displayName;

    private Integer age;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("height_in_cm")
    private Integer heightInCm;

    private City city;

    @JsonProperty("main_photo")
    private String mainPhoto;

    @JsonProperty("compatibility_score")
    private Double compatibilityScore;

    @JsonProperty("contacts_exchanged")
    private Integer contactsExchanged;

    private Boolean favourite;
    private String religion;
}
