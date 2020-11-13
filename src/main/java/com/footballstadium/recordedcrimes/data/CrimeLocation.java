package com.footballstadium.recordedcrimes.data;

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
public class CrimeLocation implements Serializable {
    private Integer streetId;
    private String streetName;
}
