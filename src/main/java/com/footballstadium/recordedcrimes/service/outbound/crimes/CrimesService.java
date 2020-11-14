package com.footballstadium.recordedcrimes.service.outbound.crimes;

import com.footballstadium.recordedcrimes.data.RecordedCrime;
import com.footballstadium.recordedcrimes.service.mapper.FootballStadiumCrimeDataMapper;
import com.footballstadium.recordedcrimes.service.outbound.exceptions.ExternalSystemException;
import com.footballstadium.recordedcrimes.service.responsedata.crimes.Crime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * A service connects to external police service crime api and retrieves the crime data.
 *
 * @author US
 */
@Component
@Slf4j
public class CrimesService {
    private static final String MSG_INVALID_RESPONSE = "Invalid response";
    private static final String SYSTEM = "PoliceService API";

    @Autowired
    private FootballStadiumCrimeDataMapper crimeDataMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${crime.service.url}")
    private String baseUrl;

    public List<RecordedCrime> getRecordedCrimeData(String date, Double latitude, Double longitude) {
        Crime[] crimes = getCrimeDataFromService(date, latitude, longitude);
        List<RecordedCrime> list = crimeDataMapper.mapFrom(crimes);
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    private Crime[] getCrimeDataFromService(String date, Double latitude, Double longitude) {
        String crimeServiceUrl = buildUrl(date, latitude, longitude);
        log.info("Call to Police API Crime Service {}", crimeServiceUrl);
        try {
            return restTemplate.getForObject(crimeServiceUrl, Crime[].class);
        } catch (Exception e) {
            log.info("There is some error while getting data from external PoliceService API", e);
            throw new ExternalSystemException(SYSTEM, MSG_INVALID_RESPONSE, e);
        }
    }

    private String buildUrl(String date, Double latitude, Double longitude) {
        String crimeServiceUrl = baseUrl + "?lat=" + latitude + "&lng=" + longitude;
        if (StringUtils.hasLength(date)) {
            crimeServiceUrl = crimeServiceUrl + "&date=" + date;
        }
        return crimeServiceUrl;
    }


}
