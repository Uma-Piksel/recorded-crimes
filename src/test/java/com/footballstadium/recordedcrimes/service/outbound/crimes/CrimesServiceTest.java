package com.footballstadium.recordedcrimes.service.outbound.crimes;

import com.footballstadium.recordedcrimes.service.responsedata.crimes.Crime;
import org.junit.Assert;
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
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author US
 */
@RunWith(MockitoJUnitRunner.class)
public class CrimesServiceTest {

    private static final String DATE = "2019-08";
    private static final Double LATITUDE = 51.556667;
    private static final Double LONGITUDE = 0.106371;
    private static final String CATEGORY = "anti-social-behaviour";
    private static final Integer ID = 86788033;

    @InjectMocks
    private CrimesService crimesService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUpBefore() {
        ReflectionTestUtils.setField(crimesService, "baseUrl", "https://data.police.uk/api/crimes-at-location");
    }

    @Test
    public void testCrimeService() {
        Crime crime = Crime.builder().category(CATEGORY).id(86788033).month(DATE).build();
        Crime[] expectedCrimes = {crime};
        ResponseEntity responseEntity = new ResponseEntity(expectedCrimes, HttpStatus.OK);
       // when(restTemplate.getForObject(anyString(), ArgumentMatchers.<Class<String>>any())).thenReturn(responseEntity);

        Crime[] result = crimesService.getCrimeData(DATE, LATITUDE, LONGITUDE);
        Assert.assertNotNull(result);
        Crime crimeResult = result[0];
        Assert.assertEquals(crimeResult.getCategory(), CATEGORY);
        Assert.assertEquals(crimeResult.getId(), ID);
        Assert.assertEquals(crimeResult.getMonth(), DATE);


    }

}