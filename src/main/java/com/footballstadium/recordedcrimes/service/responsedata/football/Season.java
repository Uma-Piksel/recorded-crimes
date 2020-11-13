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
public class Season implements Serializable {

    private String id;
    private String startDate;
    private String endDate;
    private String currentMatchDay;
    private String winner;

}
