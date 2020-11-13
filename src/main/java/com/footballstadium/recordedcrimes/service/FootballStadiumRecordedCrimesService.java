package com.footballstadium.recordedcrimes.service;

import com.footballstadium.recordedcrimes.data.FootballStadium;
import com.footballstadium.recordedcrimes.data.FootballStadiumRecordedCrimeFeed;
import com.footballstadium.recordedcrimes.data.RecordedCrime;
import com.footballstadium.recordedcrimes.service.mapper.FootballStadiumCrimeDataMapper;
import com.footballstadium.recordedcrimes.service.outbound.crimes.CrimesService;
import com.footballstadium.recordedcrimes.service.outbound.football.FootballStadiumService;
import com.footballstadium.recordedcrimes.service.mapper.FootballStadiumTeamDataMapper;
import com.footballstadium.recordedcrimes.service.outbound.postcode.PostCodeRequestBody;
import com.footballstadium.recordedcrimes.service.outbound.postcode.PostcodeService;
import com.footballstadium.recordedcrimes.service.responsedata.crimes.Crime;
import com.footballstadium.recordedcrimes.service.responsedata.football.TeamFeed;
import com.footballstadium.recordedcrimes.service.responsedata.postcode.PostCodeFeed;
import com.footballstadium.recordedcrimes.service.responsedata.postcode.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service responsible for processing recorded crimes for premier league football stadiums.
 *
 * @author US
 */
@Component
public class FootballStadiumRecordedCrimesService {

    @Autowired
    private FootballStadiumService footballStadiumService;

    @Autowired
    private PostcodeService postcodeService;

    @Autowired
    private CrimesService crimesService;

    @Autowired
    private FootballStadiumCrimeDataMapper crimeDataMapper;

    @Autowired
    private FootballStadiumTeamDataMapper teamDataMapper;

    public FootballStadiumRecordedCrimeFeed getRecordedCrimes(String date) {
        FootballStadiumRecordedCrimeFeed feed = enrichWithFootballTeamData();
        enrichWithPostcodeData(feed);
        enrichWithCrimeData(date, feed);
        return feed;
    }

    private void enrichWithPostcodeData(FootballStadiumRecordedCrimeFeed stadiumFeed) {
        PostCodeFeed postcodeFeed = postcodeService.getPostCodeData(getPostCodePayload(stadiumFeed.getFootballStadiums()));
        stadiumFeed.getFootballStadiums().forEach(stadium -> {
            Optional<Result> postcodeFeedResult = postcodeFeed.getResult().stream().filter(result -> result.getQuery().equals(stadium.getFootballStadiumPostCode())).findFirst();
            if (postcodeFeedResult.isPresent() && postcodeFeedResult.get().getResult() != null) {
                stadium.setLatitude(postcodeFeedResult.get().getResult().getLatitude());
                stadium.setLongitude(postcodeFeedResult.get().getResult().getLongitude());
            }
        });
    }

    private void enrichWithCrimeData(String date, FootballStadiumRecordedCrimeFeed feed) {
        feed.getFootballStadiums().forEach(footballStadium -> footballStadium.setRecordedCrimes(getCrimes(date, footballStadium)));
    }

    private List<RecordedCrime> getCrimes(String date, FootballStadium footballStadium) {
        List<RecordedCrime> recordedCrimes = null;
        if (footballStadium.getLatitude() != null && footballStadium.getLongitude() != null) {
            Crime[] crimes = crimesService.getCrimeData(date, footballStadium.getLatitude(), footballStadium.getLongitude());
            recordedCrimes = crimeDataMapper.mapFrom(crimes);
        }
        return recordedCrimes;
    }

    private PostCodeRequestBody getPostCodePayload(List<FootballStadium> footballStadiums) {
        List<String> postCodes = footballStadiums.stream().map(FootballStadium::getFootballStadiumPostCode).collect(Collectors.toList());
        return PostCodeRequestBody.builder().postcodes(postCodes).build();
    }

    private FootballStadiumRecordedCrimeFeed enrichWithFootballTeamData() {
        TeamFeed teamFeed = footballStadiumService.getTeamFeedForFootballCompetition();
        return teamDataMapper.mapFrom(teamFeed);
    }

}
