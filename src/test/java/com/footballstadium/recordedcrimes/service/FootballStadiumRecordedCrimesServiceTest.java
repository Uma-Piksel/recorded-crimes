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
import com.footballstadium.recordedcrimes.service.responsedata.postcode.Result_;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author US
 */
@RunWith(MockitoJUnitRunner.class)
public class FootballStadiumRecordedCrimesServiceTest {

    private static final String DATE = "2019-08";
    private static final String COMPETITION_CODE = "PL";
    private static final String COMPETITION_NAME = "Premier League";
    private static final String COMPETITION_ID = "2021";
    private static final String ADDRESS = "75 Drayton Park London N5 1BU";
    private static final String TEAM_NAME = "Arsenal FC";
    private static final String TEAM_ID = "57";
    private static final String POSTCODE = "N5 1BU";
    private static final Double LATITUDE = 51.556667;
    private static final Double LONGITUDE = 0.106371;
    private static final String COUNTRY = "England";
    private static final String REGION = "London";
    private static final String CATEGORY = "anti-social-behaviour";
    private static final Integer ID = 86788033;


    @InjectMocks
    private FootballStadiumRecordedCrimesService service;

    @Mock
    private FootballStadiumService footballStadiumService;

    @Mock
    private PostcodeService postcodeService;

    @Mock
    private CrimesService crimesService;

    @Test
    public void testGetRecordedCrimesSuccessfully() {
        FootballStadiumRecordedCrimeFeed expectedFeed = buildFootballStadiumRecordedCrimesFeed();
        when(footballStadiumService.getTeamFeedForFootballCompetition()).thenReturn(expectedFeed);
        when(postcodeService.getPostCodeData(any(PostCodeRequestBody.class))).thenReturn(buildPostCodeFeed());
        when(crimesService.getRecordedCrimeData(anyString(), anyDouble(), anyDouble())).thenReturn(Arrays.asList(buildRecordedCrime()));

        FootballStadiumRecordedCrimeFeed actualFeed = service.getRecordedCrimes(DATE);

        assertDataFeed(actualFeed);
        verify(footballStadiumService).getTeamFeedForFootballCompetition();
        verify(postcodeService).getPostCodeData(any(PostCodeRequestBody.class));
        verify(crimesService).getRecordedCrimeData(anyString(), anyDouble(), anyDouble());
    }

    private void assertDataFeed(FootballStadiumRecordedCrimeFeed actualFeed) {
        Assert.assertNotNull(actualFeed);
        assertEquals(actualFeed.getCompetitionId(), COMPETITION_ID);
        assertEquals(actualFeed.getCompetitionCode(), COMPETITION_CODE);
        assertEquals(actualFeed.getCompetitionName(), COMPETITION_NAME);
        assertEquals(actualFeed.getFootballStadiums().size(), 1);
        FootballStadium footballStadium = actualFeed.getFootballStadiums().get(0);
        assertEquals(footballStadium.getFootballStadiumAddress(), ADDRESS);
        assertEquals(footballStadium.getTeamId(), TEAM_ID);
        assertEquals(footballStadium.getTeamName(), TEAM_NAME);
        RecordedCrime recordedCrime = footballStadium.getRecordedCrimes().get(0);
        assertEquals(recordedCrime.getCrimeCategory(), CATEGORY);
        assertEquals(recordedCrime.getCrimeId(), ID);
        assertEquals(recordedCrime.getCrimeMonth(), DATE);
    }

    private RecordedCrime buildRecordedCrime() {
        return RecordedCrime.builder().crimeCategory(CATEGORY).crimeId(ID).crimeMonth(DATE).build();
    }

    private PostCodeFeed buildPostCodeFeed() {
        Result_ result_ = Result_.builder().latitude(LATITUDE).longitude(LONGITUDE).country(COUNTRY).region(REGION).build();
        Result result = Result.builder().query(POSTCODE).result(result_).build();
        return PostCodeFeed.builder().result(Arrays.asList(result)).status(200).build();
    }

    private FootballStadiumRecordedCrimeFeed buildFootballStadiumRecordedCrimesFeed() {
        return FootballStadiumRecordedCrimeFeed.builder()
                .competitionId(COMPETITION_ID).competitionCode(COMPETITION_CODE).competitionName(COMPETITION_NAME)
                .footballStadiums(Arrays.asList(FootballStadium.builder().teamId(TEAM_ID).teamName(TEAM_NAME).footballStadiumAddress(ADDRESS).footballStadiumPostCode(POSTCODE).build())).build();
    }

}