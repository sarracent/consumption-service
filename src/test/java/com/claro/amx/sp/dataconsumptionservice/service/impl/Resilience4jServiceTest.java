package com.claro.amx.sp.dataconsumptionservice.service.impl;

import com.claro.amx.sp.dataconsumptionservice.manager.RatingGroupDescriptionManager;
import com.claro.amx.sp.dataconsumptionservice.model.bo.CdrDate;
import com.claro.amx.sp.dataconsumptionservice.model.bo.DataConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.bo.FilterList;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;
import com.claro.amx.sp.dataconsumptionservice.service.DataConsumptionService;
import com.claro.amx.sp.dataconsumptionservice.service.Resilience4jService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.RATING_GROUPS_FILTERS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Resilience4jServiceTest {
    @Mock
    private RatingGroupDescriptionManager ratingGroupDescriptionManager;
    @Mock
    private DataConsumptionService dataConsumptionService;
    private Resilience4jService resilience4jService;

    @BeforeEach
    void setUp() {
        resilience4jService = new Resilience4jServiceImpl();
    }

    @Test
    void testExecuteFilterList() {
        when(ratingGroupDescriptionManager.getRatingGroupsDescription(RATING_GROUPS_FILTERS))
                .thenReturn(List.of(RatingGroupDescription.builder().build()));

        assertNotNull(resilience4jService.executeFilterList(() -> ratingGroupDescriptionManager
                .getRatingGroupsDescription(RATING_GROUPS_FILTERS)));
    }

    @Test
    void testExecuteDataConsumption() {
        DataConsumptionRequest dataConsumptionRequest = getDataConsumptionRequest();

        when(dataConsumptionService.getDataConsumption("3813520705", dataConsumptionRequest))
                .thenReturn(List.of(DataConsumption.builder().build()));

        assertNotNull(resilience4jService.executeDataConsumption(() -> dataConsumptionService
                .getDataConsumption("3813520705", dataConsumptionRequest)));
    }

    private DataConsumptionRequest getDataConsumptionRequest() {
        return DataConsumptionRequest.builder()
                .dateFrom(CdrDate.builder()
                        .dateTime("2018-06-06 00:00:00.000 +0000")
                        .format("yyyy-MM-dd HH:mm:ss.SSS Z")
                        .build())
                .dateTo(CdrDate.builder()
                        .dateTime("2018-06-06 00:00:00.000 +0000")
                        .format("yyyy-MM-dd HH:mm:ss.SSS Z")
                        .build())
                .filterList(FilterList.builder()
                        .roaming("false")
                        .ratingGroupList(List.of("5", "60", "61"))
                        .build())
                .build();
    }
}