package com.footballstadium.recordedcrimes.service.responsedata.football;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author US
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamFeed implements Serializable {

    private Competition competition;
    private Season season;
    List<Team> teams = new ArrayList<>();

}
