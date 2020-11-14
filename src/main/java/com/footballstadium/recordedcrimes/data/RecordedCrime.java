package com.footballstadium.recordedcrimes.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * @author US
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordedCrime implements Comparable<RecordedCrime>, Serializable {
    private Integer crimeId;
    private String crimeCategory;
    private String crimeLocationType;
    private CrimeLocation crimeLocation;
    private CrimeOutcomeStatus crimeOutcomeStatus;
    private String crimeMonth;

    @Override
    public int compareTo(RecordedCrime o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return YearMonth.parse(this.crimeMonth, formatter).compareTo(YearMonth.parse(o.crimeMonth, formatter));
    }
}
