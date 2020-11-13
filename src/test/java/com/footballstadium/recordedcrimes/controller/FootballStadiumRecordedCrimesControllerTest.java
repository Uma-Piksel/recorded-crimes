package com.footballstadium.recordedcrimes.controller;

import com.footballstadium.recordedcrimes.data.FootballStadiumRecordedCrimeFeed;
import com.footballstadium.recordedcrimes.service.FootballStadiumRecordedCrimesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


/**
 * @author US
 */
@RunWith(MockitoJUnitRunner.class)
public class FootballStadiumRecordedCrimesControllerTest {

    private static final String COMPETITION_CODE = "PL";
    private static final String COMPETITION_NAME = "Premier League";
    private static final String COMPETITION_ID = "2021";
    private static final String DATE = "2019-08";

    @InjectMocks
    private FootballStadiumRecordedCrimesController controller;

    @Mock
    private FootballStadiumRecordedCrimesService service;

    @Test
    public void listReturnsAllCrimesDataWhenServiceReturnsData() {
        FootballStadiumRecordedCrimeFeed expectedFeed = FootballStadiumRecordedCrimeFeed.builder().competitionCode(COMPETITION_CODE).competitionName(COMPETITION_NAME).competitionId(COMPETITION_ID).build();
        when(service.getRecordedCrimes(null)).thenReturn(expectedFeed);

        FootballStadiumRecordedCrimeFeed feed = controller.list(null);

        assertResponseData(feed);
    }

    @Test
    public void listReturnsEmptyCrimesFeedDataWhenServiceDoesNotReturnsData() {
        when(service.getRecordedCrimes(null)).thenReturn(new FootballStadiumRecordedCrimeFeed());

        FootballStadiumRecordedCrimeFeed feed = controller.list(null);

        assertNotNull(feed);
    }

    @Test
    public void listReturnsAllCrimesDataForAGivenDate() {
        FootballStadiumRecordedCrimeFeed expectedFeed = FootballStadiumRecordedCrimeFeed.builder().competitionCode(COMPETITION_CODE).competitionName(COMPETITION_NAME).competitionId(COMPETITION_ID).build();
        when(service.getRecordedCrimes(DATE)).thenReturn(expectedFeed);

        FootballStadiumRecordedCrimeFeed feed = controller.list(DATE);
        assertResponseData(feed);
    }

    private void assertResponseData(FootballStadiumRecordedCrimeFeed feed) {
        assertNotNull(feed);
        assertEquals(feed.getCompetitionCode(), COMPETITION_CODE);
        assertEquals(feed.getCompetitionName(), COMPETITION_NAME);
    }

}