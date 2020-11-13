package com.footballstadium.recordedcrimes.service.responsedata.crimes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author US
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Crime implements Serializable {
    private String category;
    private String locationType;
    private Location location;
    private String context;
    private OutcomeStatus outcomeStatus;
    private String persistentId;
    private Integer id;
    private String locationSubtype;
    private String month;
}
