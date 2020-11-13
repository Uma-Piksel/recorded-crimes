package com.footballstadium.recordedcrimes.service.mapper;

import com.footballstadium.recordedcrimes.data.FootballSeason;
import com.footballstadium.recordedcrimes.data.FootballStadium;
import com.footballstadium.recordedcrimes.data.FootballStadiumRecordedCrimeFeed;
import com.footballstadium.recordedcrimes.service.responsedata.football.Team;
import com.footballstadium.recordedcrimes.service.responsedata.football.TeamFeed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author US
 */
@Component
public class FootballStadiumTeamDataMapper {

    public FootballStadiumRecordedCrimeFeed mapFrom(TeamFeed teamFeed) {
        return FootballStadiumRecordedCrimeFeed.builder()
                .competitionId(teamFeed.getCompetition().getId())
                .competitionName(teamFeed.getCompetition().getName())
                .competitionCode(teamFeed.getCompetition().getCode())
                .footballSeason(buildFootballSeason(teamFeed))
                .footballStadiums(buildFootballStadium(teamFeed.getTeams()))
                .build();
    }

    private FootballSeason buildFootballSeason(TeamFeed teamFeed) {
        return teamFeed.getSeason() != null ?
                FootballSeason.builder().startDate(teamFeed.getSeason().getStartDate()).endDate(teamFeed.getSeason().getEndDate())
                        .winner(teamFeed.getSeason().getWinner()).currentMatchDay(teamFeed.getSeason().getCurrentMatchDay()).build() : new FootballSeason();
    }

    private List<FootballStadium> buildFootballStadium(List<Team> teams) {
        return teams.stream().map(this::buildFootballStadium).collect(Collectors.toList());
    }

    private FootballStadium buildFootballStadium(Team team) {
        return FootballStadium.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .footballStadiumAddress(team.getAddress())
                .footballStadiumPostCode(getPostcode(team.getAddress()))
                .venue(team.getVenue())
                .email(team.getEmail())
                .founded(team.getFounded())
                .build();
    }

    private String getPostcode(String addressStr) {
        String[] address = addressStr.split("\\s");
        return address[address.length - 2] + " " + address[address.length - 1];
    }
}
