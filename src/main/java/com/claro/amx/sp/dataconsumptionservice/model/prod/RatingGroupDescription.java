package com.claro.amx.sp.dataconsumptionservice.model.prod;

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
public class RatingGroupDescription {
    private String ratingGroupId;
    private String description;
}
