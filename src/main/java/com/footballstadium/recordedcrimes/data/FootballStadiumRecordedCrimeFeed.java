package com.footballstadium.recordedcrimes.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A web representation of recorded crimes for premier league football stadiums.
 *
 * @author US
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FootballStadiumRecordedCrimeFeed implements Serializable {
    private String competitionId;
    private String competitionName;
    private String competitionCode;
    private FootballSeason footballSeason;
    private List<FootballStadium> footballStadiums = new ArrayList<>();
}
