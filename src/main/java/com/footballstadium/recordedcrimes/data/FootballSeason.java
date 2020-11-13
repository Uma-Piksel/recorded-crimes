package com.footballstadium.recordedcrimes.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author US
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FootballSeason implements Serializable {
    private String startDate;
    private String endDate;
    private String currentMatchDay;
    private String winner;
}
