package com.footballstadium.recordedcrimes.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author US
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FootballStadium {
    private String teamId;
    private String teamName;
    private String footballStadiumAddress;
    private String footballStadiumPostCode;
    private Double latitude;
    private Double longitude;
    private String venue;
    private String email;
    private Integer founded;
    private List<RecordedCrime> recordedCrimes = new ArrayList<>();

}
