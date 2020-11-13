package com.footballstadium.recordedcrimes.service.mapper;

import com.footballstadium.recordedcrimes.data.CrimeLocation;
import com.footballstadium.recordedcrimes.data.CrimeOutcomeStatus;
import com.footballstadium.recordedcrimes.data.RecordedCrime;
import com.footballstadium.recordedcrimes.service.responsedata.crimes.Crime;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author US
 */
@Component
public class FootballStadiumCrimeDataMapper {

    public List<RecordedCrime> mapFrom(Crime[] crimes) {
        return Arrays.stream(crimes).map(this::buildRecordedCrime).collect(Collectors.toList());
    }

    private RecordedCrime buildRecordedCrime(Crime crime) {
        return RecordedCrime.builder()
                .crimeId(crime.getId())
                .crimeCategory(crime.getCategory())
                .crimeLocationType(crime.getLocationType())
                .crimeLocation(buildCrimeLocation(crime))
                .crimeOutcomeStatus(buildCrimeOutcomeStatus(crime))
                .crimeMonth(crime.getMonth())
                .build();
    }

    private CrimeLocation buildCrimeLocation(Crime crime) {
        return crime.getLocation() != null ?
                CrimeLocation.builder()
                        .streetId(crime.getLocation().getStreet().getId())
                        .streetName(crime.getLocation().getStreet().getName())
                        .build() : new CrimeLocation();
    }

    private CrimeOutcomeStatus buildCrimeOutcomeStatus(Crime crime) {
        return crime.getOutcomeStatus() != null ?
                CrimeOutcomeStatus.builder()
                        .category(crime.getOutcomeStatus().getCategory())
                        .date(crime.getOutcomeStatus().getDate())
                        .build() : new CrimeOutcomeStatus();
    }
}
