package com.footballstadium.recordedcrimes.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author US
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrimeOutcomeStatus {
    String category;
    String date;
}
