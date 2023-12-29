package com.claro.amx.sp.dataconsumptionservice.controller;

import com.claro.amx.sp.dataconsumptionservice.model.response.DataConsumptionFilterListResponse;
import com.claro.amx.sp.dataconsumptionservice.model.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "Data Consumption Filter List Controller", description = "Data Consumption Filter List Api")
@Validated
@RequestMapping(path = "/dataConsumptionFilterList", produces = MediaType.APPLICATION_JSON_VALUE)
public interface DataConsumptionFilterListController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Data Consumption Filter List", description = "Get Data Consumption Filter List information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OK_MSG, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DataConsumptionFilterListResponse.class))),
            @ApiResponse(responseCode = "400", description = BUSINESS_MSG, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ServiceResponse.class))),
            @ApiResponse(responseCode = "500", description = DATABASE_MSG + COMMA + SPACE + TECHNICAL_MSG + COMMA + SPACE + EXTERNAL_MSG + COMMA + SPACE + INTERNAL_MSG, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ServiceResponse.class))),
    })
    ResponseEntity<DataConsumptionFilterListResponse> getDataConsumptionFilterList(@RequestHeader Map<String, Object> httpHeadersMap);

}
