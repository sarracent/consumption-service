package com.claro.amx.sp.dataconsumptionservice.model.ccard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
public class DataDailyConsumption {
    private LocalDateTime cdrDate;
    private String roaming;
    private BigDecimal cost;
    private BigDecimal volume;
    private String ratingGroup;
}
