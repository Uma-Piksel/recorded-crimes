package com.footballstadium.recordedcrimes.controller;

import com.footballstadium.recordedcrimes.data.FootballStadiumRecordedCrimeFeed;
import com.footballstadium.recordedcrimes.service.FootballStadiumRecordedCrimesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author US
 *
 * <p>
 * Entry point of the Recored Crimes API Resource retuns list of recored crimes for premier leage football stadiums
 * sorted by year and month
 * </p>
 */
@RestController
@RequestMapping("/premier-league/football-stadium")
@Api(value="recordedcrimes")
public class FootballStadiumRecordedCrimesController {


    @Autowired
    private FootballStadiumRecordedCrimesService service;

    /**
     * <p>
     * Returns a collection of recorded crimes occurred in premier league football stadiums for the supplied query parameters.
     *
     * <p>Allowed Parameters</p>
     * <ul>
     *    <li>date - date is in the format of year and month - YYYY-MM. Ex: 2020-11</li>
     * </ul>
     * </p>
     **/
    @GetMapping(value = "/recoredcrimes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Premier League Football Stadiums Recorded Crimes", response = FootballStadiumRecordedCrimeFeed.class)
    public FootballStadiumRecordedCrimeFeed list(@RequestParam(value = "date", required = false) String date) {
        return service.getRecordedCrimes(date);
    }
}
