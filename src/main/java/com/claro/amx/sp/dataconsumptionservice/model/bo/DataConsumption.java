package com.claro.amx.sp.dataconsumptionservice.model.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
public class DataConsumption {
    private CdrDate cdrDate;
    private boolean roaming;
    private Cost cost;
    private Volume volume;
    private String ratingGroup;
    private String ratingGroupDescription;
    private boolean isSocialNetwork;
}
