package com.footballstadium.recordedcrimes.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author US
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordedCrime implements Serializable {
    private Integer crimeId;
    private String crimeCategory;
    private String crimeLocationType;
    private CrimeLocation crimeLocation;
    private CrimeOutcomeStatus crimeOutcomeStatus;
    private String crimeMonth;

}
