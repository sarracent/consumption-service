package com.claro.amx.sp.dataconsumptionservice.model.response;

import com.claro.amx.sp.dataconsumptionservice.model.bo.DataConsumption;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Object containing ConsumptionResponse")
public class DataConsumptionResponse extends ServiceResponse{
    @Schema(description = "List of Data Consumption")
    private List<DataConsumption> dataConsumptionDetailsList;
}
