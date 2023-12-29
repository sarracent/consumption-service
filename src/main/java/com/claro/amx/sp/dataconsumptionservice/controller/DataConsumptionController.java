package com.claro.amx.sp.dataconsumptionservice.controller;

import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;
import com.claro.amx.sp.dataconsumptionservice.model.response.DataConsumptionResponse;
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

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.*;
import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.INTERNAL_MSG;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "Data Consumption Controller", description = "The Data Consumption Api")
@Validated
@RequestMapping(path = "/dataConsumption", produces = MediaType.APPLICATION_JSON_VALUE)
public interface DataConsumptionController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Data Consumption by billNumber", description = "Get Data Consumption information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = OK_MSG, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = DataConsumptionResponse.class))),
            @ApiResponse(responseCode = "400", description = BUSINESS_MSG, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ServiceResponse.class))),
            @ApiResponse(responseCode = "500", description = DATABASE_MSG + COMMA + SPACE + TECHNICAL_MSG + COMMA + SPACE + EXTERNAL_MSG + COMMA + SPACE + INTERNAL_MSG, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ServiceResponse.class))),
    })
    ResponseEntity<DataConsumptionResponse> getDataConsumption(
            @RequestHeader Map<String, Object> httpHeadersMap,
            @NotEmpty(message = "The Bill-Number is required.") @RequestHeader(value = BILL_NUMBER) String billNumber,
            @Valid @RequestBody DataConsumptionRequest consumptionRequest);
}
