package com.claro.amx.sp.dataconsumptionservice.model.response;

import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
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
@Schema(description = "Object containing ConsumptionFilterListResponse")
public class DataConsumptionFilterListResponse extends ServiceResponse {
    @Schema(description = "List of Consumption Filter List")
    private List<RatingGroupDescription> ratingGroupList;
    private List<RatingGroupDescription> ratingGroupSocialNetworkList;

}

