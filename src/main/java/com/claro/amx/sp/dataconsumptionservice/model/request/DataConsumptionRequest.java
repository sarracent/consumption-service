package com.claro.amx.sp.dataconsumptionservice.model.request;

import com.claro.amx.sp.dataconsumptionservice.model.bo.CdrDate;
import com.claro.amx.sp.dataconsumptionservice.model.bo.FilterList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Object containing ConsumptionRequest")
public class DataConsumptionRequest {
    @Valid
    @NotNull(message = "Error el campo dateFrom es requerido.")
    private CdrDate dateFrom;
    @Valid
    @NotNull(message = "Error el campo dateTo es requerido.")
    private CdrDate dateTo;
    @Valid
    @NotNull(message = "Error el campo filterList es requerido.")
    private FilterList filterList;
}
