package com.footballstadium.recordedcrimes.service.responsedata.football;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author US
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team implements Serializable {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String website;
    private String email;
    private String venue;
    private Integer founded;
    private String lastUpdated;
}
