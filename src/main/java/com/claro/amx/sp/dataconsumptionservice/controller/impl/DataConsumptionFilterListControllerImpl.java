package com.claro.amx.sp.dataconsumptionservice.controller.impl;

import com.claro.amx.sp.dataconsumptionservice.annotations.auditable.AuditableApi;
import com.claro.amx.sp.dataconsumptionservice.annotations.auditable.AuditableParamIgnore;
import com.claro.amx.sp.dataconsumptionservice.annotations.auditable.AuditableReturn;
import com.claro.amx.sp.dataconsumptionservice.controller.DataConsumptionFilterListController;
import com.claro.amx.sp.dataconsumptionservice.manager.RatingGroupDescriptionManager;
import com.claro.amx.sp.dataconsumptionservice.model.response.DataConsumptionFilterListResponse;
import com.claro.amx.sp.dataconsumptionservice.service.Resilience4jService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

import static com.claro.amx.sp.dataconsumptionservice.commons.resolver.CustomHeadersResolver.getHeadersMap;
import static com.claro.amx.sp.dataconsumptionservice.commons.resolver.CustomHeadersResolver.validateHeaders;
import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.*;

@RestController
public class DataConsumptionFilterListControllerImpl implements DataConsumptionFilterListController {

    @Value("${country}")
    private String country;
    @Autowired
    private RatingGroupDescriptionManager ratingGroupDescriptionManager;
    @Autowired
    private Resilience4jService resilience4jService;
    @Autowired
    private PrometheusMeterRegistry meterRegistry;

    @Override
    @Timed(value = "dataConsumptionFilterList_method",extraTags = {"Version", "1.0"})
    @AuditableApi(
            description = "getDataConsumptionFilterList Api",
            parameterIgnore = @AuditableParamIgnore(nameToAudit = "httpHeadersMap", type = Map.class),
            returnMethod = @AuditableReturn(type = DataConsumptionFilterListResponse.class))
    public ResponseEntity<DataConsumptionFilterListResponse> getDataConsumptionFilterList(
            final Map<String, Object> httpHeadersMap) {

        final Map<String, String> headersMap = getHeadersMap(httpHeadersMap);
        validateHeaders(headersMap);

        DataConsumptionFilterListResponse response = new DataConsumptionFilterListResponse();
        long startTime = System.currentTimeMillis();
        response.setRatingGroupList(resilience4jService.executeFilterList(() -> ratingGroupDescriptionManager
                .getRatingGroupsDescription(RATING_GROUPS_FILTERS)));
        response.setRatingGroupSocialNetworkList(resilience4jService.executeFilterList(() -> ratingGroupDescriptionManager
                .getRatingGroupsDescription(RATING_GROUPS_SOCIAL_NETWORKS)));

        response.setResultCode(ZERO_MSG);
        response.setResultMessage(OK_MSG);
        meterRegistry.timer("getDataConsumptionFilterList_timer", "country", country
                        ,"session", headersMap.get(CHANNEL_NAME)
                        ,"channel", headersMap.get(CHANNEL_NAME)
                        ,"user", headersMap.get(USER_NAME)
                        ,"responseCode", response.getResultCode()
                        ,"responseDesc" , response.getResultMessage())
                .record(Duration.ofMillis(System.currentTimeMillis() - startTime));

        return ResponseEntity.ok()
                .body(response);
    }

}
