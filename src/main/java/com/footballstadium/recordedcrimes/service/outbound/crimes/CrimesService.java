package com.footballstadium.recordedcrimes.service.outbound.crimes;

import com.footballstadium.recordedcrimes.service.outbound.exceptions.ExternalSystemException;
import com.footballstadium.recordedcrimes.service.responsedata.crimes.Crime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${crime.service.url}")
    private String baseUrl;


    public Crime[] getCrimeData(String date, Double latitude, Double longitude) {
        String crimeServiceUrl = baseUrl + "?lat=" + latitude + "&lng=" + longitude;
        if (StringUtils.hasLength(date)) {
            crimeServiceUrl = crimeServiceUrl + "&date=" + date;
        }
        log.info("Call to Police API Crime Service {}", crimeServiceUrl);
        try {
            return restTemplate.getForObject(crimeServiceUrl, Crime[].class);
        } catch (Exception e) {
            log.info("There is some error while getting data from external PoliceService API", e);
            throw new ExternalSystemException(SYSTEM, MSG_INVALID_RESPONSE, e);
        }
    }


}
