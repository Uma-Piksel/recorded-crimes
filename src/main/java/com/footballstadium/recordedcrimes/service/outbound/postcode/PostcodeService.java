package com.footballstadium.recordedcrimes.service.outbound.postcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.footballstadium.recordedcrimes.service.outbound.exceptions.ExternalSystemException;
import com.footballstadium.recordedcrimes.service.outbound.exceptions.InternalSystemException;
import com.footballstadium.recordedcrimes.service.responsedata.postcode.PostCodeFeed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A service retrieves the postcode data from external postcode service API.
 *
 * @author US
 */

@Component
@Slf4j
public class PostcodeService {

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String MSG_INVALID_RESPONSE = "Invalid response";
    private static final String SYSTEM = "PostCode API Service";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${postcode.service.url}")
    private String url;


    public PostCodeFeed getPostCodeData(PostCodeRequestBody payload) {
        log.info("Call to PostCode API Service {} {}", url, payload.toString());
        String jsonRequestBody = getJsonPostcodeRequestBody(payload);
        return post(url, jsonRequestBody);
    }

    private String getJsonPostcodeRequestBody(PostCodeRequestBody payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new InternalSystemException("JsonProcessingException: error while mapping java object into json", e);
        }
    }

    private PostCodeFeed post(String url, String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE_HEADER_NAME, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<PostCodeFeed> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, PostCodeFeed.class);
            return response != null ? response.getBody() : new PostCodeFeed();
        } catch (Exception e) {
            log.info("There is some error posting to external postcode service API ", e);
            throw new ExternalSystemException(SYSTEM, MSG_INVALID_RESPONSE, e);
        }
    }
}
