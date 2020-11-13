package com.footballstadium.recordedcrimes.service.outbound.football;

import com.footballstadium.recordedcrimes.data.FootballStadiumRecordedCrimeFeed;
import com.footballstadium.recordedcrimes.service.mapper.FootballStadiumTeamDataMapper;
import com.footballstadium.recordedcrimes.service.outbound.exceptions.ExternalSystemException;
import com.footballstadium.recordedcrimes.service.responsedata.football.TeamFeed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author US
 * A service connects to external footbal data service API to fetch the data.
 */
@Component
@Slf4j
public class FootballStadiumService {

    private static final String SYSTEM = "Football Service Api";
    private static final String AUTH_TOKEN_HEADER_NAME = "X-Auth-Token";
    private static final String MSG_INVALID_RESPONSE = "Invalid response";
    private static final String PREMIER_LEAGUE_FOOTBALL_COMPETITION_CODE = "PL";

    @Autowired
    private FootballStadiumTeamDataMapper teamDataMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${football.stadium.service.url}")
    private String url;

    @Value("${api.key}")
    private String apiKey;

    public FootballStadiumRecordedCrimeFeed getTeamFeedForFootballCompetition() {
        TeamFeed teamFeed = getTeamFeedFromExternalService();
        return teamDataMapper.mapFrom(teamFeed);
    }

    private TeamFeed getTeamFeedFromExternalService() {
        String baseUrl = url + PREMIER_LEAGUE_FOOTBALL_COMPETITION_CODE + "/teams/";
        return get(baseUrl, TeamFeed.class);
    }

    private <T> T get(String url, Class<T> responseType) {
        log.info("Call to Football data service API {} ", url);
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_TOKEN_HEADER_NAME, apiKey);
        HttpEntity<T> requestEntity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
            return response.getBody();
        } catch (Exception e) {
            log.info("There is some error while getting data from external service", e);
            throw new ExternalSystemException(SYSTEM, MSG_INVALID_RESPONSE, e);
        }
    }


}