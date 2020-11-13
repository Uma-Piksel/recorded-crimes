package com.footballstadium.recordedcrimes.service.outbound.crimes;

import com.footballstadium.recordedcrimes.data.RecordedCrime;
import com.footballstadium.recordedcrimes.service.mapper.FootballStadiumCrimeDataMapper;
import com.footballstadium.recordedcrimes.service.outbound.exceptions.ExternalSystemException;
import com.footballstadium.recordedcrimes.service.responsedata.crimes.Crime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
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
public class CrimesServiceTest {

    private static final String DATE = "2019-08";
    private static final Double LATITUDE = 51.556667;
    private static final Double LONGITUDE = 0.106371;
    private static final String CATEGORY = "anti-social-behaviour";
    private static final Integer ID = 86788033;

    @InjectMocks
    private CrimesService crimesService;

    @Mock
    private FootballStadiumCrimeDataMapper crimeDataMapper;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUpBefore() {
        ReflectionTestUtils.setField(crimesService, "baseUrl", "https://data.police.uk/api/crimes-at-location");
    }

    @Test
    public void testGetCrimeDataReturnsCrimesDataSuccessfully() {
        Crime[] expectedCrimes = mockRestTemplateCall();
        List<RecordedCrime> expectedRecordedCrimes = mockMapper(expectedCrimes);

        List<RecordedCrime> actualResult = crimesService.getRecordedCrimeData(DATE, LATITUDE, LONGITUDE);

        assertEquals(actualResult, expectedRecordedCrimes);
        assertRecordedCrimeData(actualResult);
        verify(restTemplate).getForObject(anyString(), ArgumentMatchers.<Class<Crime[]>>any());
        verify(crimeDataMapper).mapFrom(any(Crime[].class));
    }

    @Test(expected = ExternalSystemException.class)
    public void testGetCrimeDataThrowsExceptionWhenExternalCrimeServiceThrowsException() {
        HttpServerErrorException httpServerErrorException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.<Class<Crime[]>>any())).thenThrow(httpServerErrorException);

        crimesService.getRecordedCrimeData(DATE, LATITUDE, LONGITUDE);
    }

    private RecordedCrime buildRecordedCrime() {
        return RecordedCrime.builder().crimeCategory(CATEGORY).crimeId(ID).crimeMonth(DATE).build();
    }

    private List<RecordedCrime> mockMapper(Crime[] crimes) {
        List<RecordedCrime> expectedRecordedCrimes = Arrays.asList(buildRecordedCrime());
        when(crimeDataMapper.mapFrom(crimes)).thenReturn(expectedRecordedCrimes);
        return expectedRecordedCrimes;
    }

    private Crime[] mockRestTemplateCall() {
        Crime crime = Crime.builder().category(CATEGORY).id(86788033).month(DATE).build();
        Crime[] expectedCrimes = {crime};
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.<Class<Crime[]>>any())).thenReturn(expectedCrimes);
        return expectedCrimes;
    }

    private void assertRecordedCrimeData(List<RecordedCrime> actualResult) {
        assertNotNull(actualResult);
        RecordedCrime recordedCrime = actualResult.get(0);
        assertEquals(recordedCrime.getCrimeCategory(), CATEGORY);
        assertEquals(recordedCrime.getCrimeId(), ID);
        assertEquals(recordedCrime.getCrimeMonth(), DATE);
    }

}