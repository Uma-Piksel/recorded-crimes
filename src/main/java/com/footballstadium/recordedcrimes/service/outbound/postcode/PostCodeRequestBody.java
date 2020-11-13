package com.footballstadium.recordedcrimes.service.outbound.postcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author US
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostCodeRequestBody {

    List<String> postcodes = new ArrayList<>();
}
