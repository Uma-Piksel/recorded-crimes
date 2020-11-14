package com.footballstadium.recordedcrimes.service;

import com.footballstadium.recordedcrimes.data.FootballStadium;
import com.footballstadium.recordedcrimes.data.FootballStadiumRecordedCrimeFeed;
import com.footballstadium.recordedcrimes.data.RecordedCrime;
import com.footballstadium.recordedcrimes.service.outbound.crimes.CrimesService;
import com.footballstadium.recordedcrimes.service.outbound.football.FootballStadiumService;
import com.footballstadium.recordedcrimes.service.outbound.postcode.PostCodeRequestBody;
import com.footballstadium.recordedcrimes.service.outbound.postcode.PostcodeService;
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

    public FootballStadiumRecordedCrimeFeed getRecordedCrimes(String date) {
        FootballStadiumRecordedCrimeFeed feed = footballStadiumService.getTeamFeedForFootballCompetition();
        enrichWithPostcodeData(feed);
        enrichWithCrimeData(date, feed);
        return feed;
    }

    private void enrichWithPostcodeData(FootballStadiumRecordedCrimeFeed stadiumFeed) {
        //TODO -Improvement - cache the postcode data as the data seems never changing
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
            //TODO - Improvement -Find an API - make a call to crime service api for collection of latitude and longitude values instead of making a call for each latitude and longitude
            recordedCrimes = crimesService.getRecordedCrimeData(date, footballStadium.getLatitude(), footballStadium.getLongitude());
        }
        return recordedCrimes;
    }

    private PostCodeRequestBody getPostCodePayload(List<FootballStadium> footballStadiums) {
        List<String> postCodes = footballStadiums.stream().map(FootballStadium::getFootballStadiumPostCode).collect(Collectors.toList());
        return PostCodeRequestBody.builder().postcodes(postCodes).build();
    }


}
