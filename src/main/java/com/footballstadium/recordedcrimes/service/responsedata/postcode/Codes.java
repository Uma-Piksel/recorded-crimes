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
public class Codes {

    private String adminDistrict;
    private String adminCounty;
    private String adminWard;
    private String parish;
    private String parliamentaryConstituency;
    private String ccg;
    private String ccgId;
    private String ced;
    private String nuts;

}