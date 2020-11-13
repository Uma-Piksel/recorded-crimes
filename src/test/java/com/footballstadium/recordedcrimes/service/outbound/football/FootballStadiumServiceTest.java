package com.footballstadium.recordedcrimes.service.outbound.football;

import com.footballstadium.recordedcrimes.data.FootballStadium;
import com.footballstadium.recordedcrimes.data.FootballStadiumRecordedCrimeFeed;
import com.footballstadium.recordedcrimes.service.mapper.FootballStadiumTeamDataMapper;
import com.footballstadium.recordedcrimes.service.outbound.exceptions.ExternalSystemException;
import com.footballstadium.recordedcrimes.service.responsedata.football.Competition;
import com.footballstadium.recordedcrimes.service.responsedata.football.Team;
import com.footballstadium.recordedcrimes.service.responsedata.football.TeamFeed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author US
 */
@RunWith(MockitoJUnitRunner.class)
public class FootballStadiumServiceTest {

    private static final String COMPETITION_CODE = "PL";
    private static final String COMPETITION_NAME = "Premier League";
    private static final String COMPETITION_ID = "2021";
    private static final String ADDRESS = "75 Drayton Park London N5 1BU";
    private static final String TEAM_NAME = "Arsenal FC";
    private static final String TEAM_ID = "57";

    @InjectMocks
    private FootballStadiumService footballStadiumService;

    @Mock
    private FootballStadiumTeamDataMapper teamDataMapper;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUpBefore() {
        ReflectionTestUtils.setField(footballStadiumService, "url", "http://api.football-data.org/v2/competitions/");
        ReflectionTestUtils.setField(footballStadiumService, "apiKey", "a48bbf4c07f64893b69262a410cc6e3d");
    }

    @Test
    public void testGetTeamFeedForPLCompetitionReturnsTeamsFeedDataSuccessfully() {
        TeamFeed teamFeed = mockRestTemplateCall();
        FootballStadiumRecordedCrimeFeed expectedFeed = mockMapper(teamFeed);

        FootballStadiumRecordedCrimeFeed actualFeed = footballStadiumService.getTeamFeedForFootballCompetition();

        assertEquals(actualFeed, expectedFeed);
        assertFeedData(actualFeed);
        verify(restTemplate).exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<String>>any());
    }

    @Test(expected = ExternalSystemException.class)
    public void testGetTeamFeedForPLCompetitionThrowsExceptionWhenExternalServiceReturnsErrorResponse() {
        HttpServerErrorException httpServerErrorException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<String>>any())).thenThrow(httpServerErrorException);

        footballStadiumService.getTeamFeedForFootballCompetition();
    }

    private FootballStadiumRecordedCrimeFeed buildFootballStadiumRecordedCrimesFeed() {
        return FootballStadiumRecordedCrimeFeed.builder()
                .competitionId(COMPETITION_ID).competitionCode(COMPETITION_CODE).competitionName(COMPETITION_NAME)
                .footballStadiums(Arrays.asList(FootballStadium.builder().teamId(TEAM_ID).teamName(TEAM_NAME).footballStadiumAddress(ADDRESS).build())).build();
    }

    private TeamFeed mockRestTemplateCall() {
        Team team = Team.builder().id(TEAM_ID).name(TEAM_NAME).address(ADDRESS).build();
        TeamFeed teamFeed = TeamFeed.builder().competition(Competition.builder().code(COMPETITION_CODE).name(COMPETITION_NAME).id(COMPETITION_ID).build()).teams(Arrays.asList(team)).build();
        ResponseEntity responseEntity = new ResponseEntity(teamFeed, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<String>>any())).thenReturn(responseEntity);
        return teamFeed;
    }

    private FootballStadiumRecordedCrimeFeed mockMapper(TeamFeed teamFeed) {
        FootballStadiumRecordedCrimeFeed expectedFeed = buildFootballStadiumRecordedCrimesFeed();
        when(teamDataMapper.mapFrom(teamFeed)).thenReturn(expectedFeed);
        return expectedFeed;
    }

    private void assertFeedData(FootballStadiumRecordedCrimeFeed actualFeed) {
        assertNotNull(actualFeed);
        assertEquals(actualFeed.getCompetitionId(), COMPETITION_ID);
        assertEquals(actualFeed.getCompetitionCode(), COMPETITION_CODE);
        assertEquals(actualFeed.getCompetitionName(), COMPETITION_NAME);
        assertEquals(actualFeed.getFootballStadiums().size(), 1);
        FootballStadium footballStadium = actualFeed.getFootballStadiums().get(0);
        assertEquals(footballStadium.getFootballStadiumAddress(), ADDRESS);
        assertEquals(footballStadium.getTeamId(), TEAM_ID);
        assertEquals(footballStadium.getTeamName(), TEAM_NAME);
    }

}