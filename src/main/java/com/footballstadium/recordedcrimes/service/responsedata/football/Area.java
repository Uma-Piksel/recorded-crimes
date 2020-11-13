package com.footballstadium.recordedcrimes.service.responsedata.football;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author US
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Area implements Serializable {

    private String id;
    private String name;
}
