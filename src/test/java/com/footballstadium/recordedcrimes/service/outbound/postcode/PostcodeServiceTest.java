package com.footballstadium.recordedcrimes.service.outbound.postcode;

import com.footballstadium.recordedcrimes.service.outbound.exceptions.ExternalSystemException;
import com.footballstadium.recordedcrimes.service.responsedata.postcode.PostCodeFeed;
import com.footballstadium.recordedcrimes.service.responsedata.postcode.Result;
import com.footballstadium.recordedcrimes.service.responsedata.postcode.Result_;
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
import java.util.List;

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
public class PostcodeServiceTest {

    private static final String POSTCODE = "N5 1BU";
    private static final Double LATITUDE = 51.556667;
    private static final Double LONGITUDE = 0.106371;
    private static final String COUNTRY = "England";
    private static final String REGION = "London";
    private static final List<String> POSTCODES = Arrays.asList(POSTCODE);

    @InjectMocks
    private PostcodeService postcodeService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUpBefore() {
        ReflectionTestUtils.setField(postcodeService, "url", "https://api.postcodes.io/postcodes/");
    }

    @Test
    public void testGetPostCodeDataReturnsPostCodeDataSuccessfully() {
        PostCodeRequestBody postCodeRequestBody = PostCodeRequestBody.builder().postcodes(POSTCODES).build();
        mockRestTemplateCall();

        PostCodeFeed actualFeed = postcodeService.getPostCodeData(postCodeRequestBody);
        assertPostCodeFeedData(actualFeed);

        verify(restTemplate).exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<PostCodeFeed>>any());
    }

    @Test(expected = ExternalSystemException.class)
    public void testGetPostCodeDataThrowsExceptionWhenExternalPostCodeServiceApiThrowsException() {
        PostCodeRequestBody postCodeRequestBody = PostCodeRequestBody.builder().postcodes(POSTCODES).build();
        HttpServerErrorException httpServerErrorException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<PostCodeFeed>>any())).thenThrow(httpServerErrorException);

        postcodeService.getPostCodeData(postCodeRequestBody);
    }

    private void assertPostCodeFeedData(PostCodeFeed actualFeed) {
        assertNotNull(actualFeed);
        Result result = actualFeed.getResult().get(0);
        assertNotNull(result);
        assertEquals(result.getQuery(), POSTCODE);
        assertEquals(result.getResult().getLatitude(), LATITUDE);
        assertEquals(result.getResult().getLongitude(), LONGITUDE);
        assertEquals(result.getResult().getCountry(), COUNTRY);
        assertEquals(result.getResult().getRegion(), REGION);
    }

    private PostCodeFeed buildPostCodeFeed() {
        Result_ result_ = Result_.builder().latitude(LATITUDE).longitude(LONGITUDE).country(COUNTRY).region(REGION).build();
        Result result = Result.builder().query(POSTCODE).result(result_).build();
        return PostCodeFeed.builder().result(Arrays.asList(result)).status(200).build();
    }

    private void mockRestTemplateCall() {
        PostCodeFeed expectedFeed = buildPostCodeFeed();
        ResponseEntity responseEntity = new ResponseEntity(expectedFeed, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<PostCodeFeed>>any())).thenReturn(responseEntity);
    }

}