package com.claro.amx.sp.dataconsumptionservice.controller.impl;

import com.claro.amx.sp.dataconsumptionservice.annotations.auditable.AuditableApi;
import com.claro.amx.sp.dataconsumptionservice.annotations.auditable.AuditableParamIgnore;
import com.claro.amx.sp.dataconsumptionservice.annotations.auditable.AuditableReturn;
import com.claro.amx.sp.dataconsumptionservice.controller.DataConsumptionController;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.BadRequestException;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;
import com.claro.amx.sp.dataconsumptionservice.model.response.DataConsumptionResponse;
import com.claro.amx.sp.dataconsumptionservice.service.DataConsumptionService;
import com.claro.amx.sp.dataconsumptionservice.service.Resilience4jService;
import com.claro.amx.sp.dataconsumptionservice.utility.Util;
import io.micrometer.core.annotation.Timed;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

import static com.claro.amx.sp.dataconsumptionservice.commons.resolver.CustomHeadersResolver.*;
import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.*;
import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.*;

@RestController
public class DataConsumptionControllerImpl implements DataConsumptionController {
    @Value("${country}")
    private String country;
    @Autowired
    private DataConsumptionService dataConsumptionService;
    @Autowired
    private Resilience4jService resilience4jService;
    @Autowired
    private PrometheusMeterRegistry meterRegistry;

    @Override
    @Timed(value = "dataConsumption_method",extraTags = {"Version", "1.0"})
    @AuditableApi(
            description = "getDataConsumptionByBillNumber Api",
            parameterIgnore = @AuditableParamIgnore(nameToAudit = "httpHeadersMap", type = Map.class),
            returnMethod = @AuditableReturn(type = DataConsumptionResponse.class))
    public ResponseEntity<DataConsumptionResponse> getDataConsumption(final Map<String, Object> httpHeadersMap,
                                                                                  final String billNumber,
                                                                                  final DataConsumptionRequest consumptionRequest) {

        final Map<String, String> headersMap = getHeadersMap(httpHeadersMap);
        headersMap.put(BILL_NUMBER, searchValueHeader(httpHeadersMap, BILL_NUMBER.toLowerCase()));
        validateHeaders(headersMap);
        validateRequest(consumptionRequest, headersMap);

        DataConsumptionRequest request = new DataConsumptionRequest();
        DataConsumptionResponse response = new DataConsumptionResponse();
        long startTime = System.currentTimeMillis();
        request.setDateFrom(consumptionRequest.getDateFrom());
        request.setDateTo(consumptionRequest.getDateTo());
        request.setFilterList(consumptionRequest.getFilterList());
        response.setDataConsumptionDetailsList(resilience4jService.executeDataConsumption(() -> dataConsumptionService
                .getDataConsumption(headersMap.get(BILL_NUMBER), request)));
        response.setResultCode(ZERO_MSG);
        response.setResultMessage(OK_MSG);
        meterRegistry.timer("getDataConsumption_timer", "country", country
                        ,"session", headersMap.get(CHANNEL_NAME)
                        ,"channel", headersMap.get(CHANNEL_NAME)
                        ,"user", headersMap.get(USER_NAME)
                        ,"responseCode", response.getResultCode()
                        ,"responseDesc" , response.getResultMessage())
                .record(Duration.ofMillis(System.currentTimeMillis() - startTime));

        return ResponseEntity.ok()
                .body(response);
    }

    private void validateRequest(final DataConsumptionRequest consumptionRequest, final Map<String, String> headersMap) {
        if (Util.getZonedDateTime(consumptionRequest.getDateFrom().getDateTime(),
                        consumptionRequest.getDateFrom().getFormat()).
                isAfter(Util.getZonedDateTime(consumptionRequest.getDateTo().getDateTime(),
                        consumptionRequest.getDateTo().getFormat()))) {
            throw new BadRequestException(ERROR_BADREQUEST_FAIL_DATE.getCode(),
                    String.format(ERROR_BADREQUEST_FAIL_DATE.getMessage(), consumptionRequest.getDateFrom().getDateTime(),
                            consumptionRequest.getDateTo().getDateTime()));
        } else if (Util.validateBillNumber(headersMap.get(BILL_NUMBER))) {
            throw new BadRequestException(ERROR_BADREQUEST_FAIL_BILL_NUMBER.getCode(),
                    String.format(ERROR_BADREQUEST_FAIL_BILL_NUMBER.getMessage(), headersMap.get(BILL_NUMBER)));
        }
    }
}
