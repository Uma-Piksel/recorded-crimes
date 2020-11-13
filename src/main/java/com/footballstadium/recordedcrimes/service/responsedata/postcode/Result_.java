package com.footballstadium.recordedcrimes.service.responsedata.postcode;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author US
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result_ {
    private String postcode;
    private Integer quality;
    private Integer eastings;
    private Integer northings;
    private String country;
    private String nhsHa;
    private Double longitude;
    private Double latitude;
    private String europeanElectoralRegion;
    private String primaryCareTrust;
    private String region;
    private String lsoa;
    private String msoa;
    private String incode;
    private String outcode;
    private String parliamentaryConstituency;
    private String adminDistrict;
    private String parish;
    private Object adminCounty;
    private String adminWard;
    private Object ced;
    private String ccg;
    private String nuts;
    private Codes codes;

}
