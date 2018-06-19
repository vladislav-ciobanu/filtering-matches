package com.vciobanu.filteringmatches.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "matches", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @JsonProperty("display_name")
    @Column(name = "display_name")
    private String displayName;

    private Integer age;

    @JsonProperty("job_title")
    @Column(name = "job_title")
    private String jobTitle;

    @JsonProperty("height_in_cm")
    @Column(name = "height_in_cm")
    private Integer heightInCm;

    @Embedded
    private City city;

    @JsonProperty("main_photo")
    @Column(name = "main_photo")
    private String mainPhoto;

    @JsonProperty("compatibility_score")
    @Column(name = "compatibility_score")
    private Double compatibilityScore;

    @JsonProperty("contacts_exchanged")
    @Column(name = "contacts_exchanged")
    private Integer contactsExchanged;

    private Boolean favourite;
    private String religion;
}
