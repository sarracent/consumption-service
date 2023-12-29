package com.claro.amx.sp.dataconsumptionservice.service.impl;

import com.claro.amx.sp.dataconsumptionservice.business.bo.DataConsumptionBO;
import com.claro.amx.sp.dataconsumptionservice.manager.PrepayParametersManager;
import com.claro.amx.sp.dataconsumptionservice.manager.RatingGroupDescriptionManager;
import com.claro.amx.sp.dataconsumptionservice.model.bo.CdrDate;
import com.claro.amx.sp.dataconsumptionservice.model.bo.DataConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.bo.FilterList;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.DataDailyConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;
import com.claro.amx.sp.dataconsumptionservice.service.DataConsumptionService;
import com.claro.amx.sp.dataconsumptionservice.utility.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.RATING_GROUPS_SOCIAL_NETWORKS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class DataConsumptionServiceTest {
    @Mock
    private DataConsumptionBO dataConsumptionBO;
    @Mock
    private RatingGroupDescriptionManager ratingGroupDescriptionManager;
    @Mock
    private PrepayParametersManager prepayParametersManager;
    private DataConsumptionService dataConsumptionService;

    @BeforeEach
    void setUp() {
        when(dataConsumptionBO.getDataConsumption("3813520705", "06-06-2018", "06-06-2018",
                getDataConsumptionRequest())).thenReturn(List.of(DataDailyConsumption.builder()
                .cdrDate(Util.getZonedDateTime("2018-06-06 00:00:00.000 -0300", "yyyy-MM-dd HH:mm:ss.SSS Z"))
                .roaming("N")
                .cost(BigDecimal.valueOf(-30901001))
                .volume(BigDecimal.valueOf(123456.0))
                .ratingGroup("5")
                .build()));
        when(ratingGroupDescriptionManager.getRatingGroupsDescription(anyList()))
                .thenReturn(List.of(RatingGroupDescription.builder().build()));
        when(prepayParametersManager.getPrepayParameters(anyString())).thenReturn(PrepayParameters.builder()
                .name(RATING_GROUPS_SOCIAL_NETWORKS)
                .value("#992#993#994#995#997#")
                .build());

        dataConsumptionService = new DataConsumptionServiceImpl(dataConsumptionBO, ratingGroupDescriptionManager,
                prepayParametersManager);

        ReflectionTestUtils.setField(dataConsumptionService, "timeZone", "America/Buenos_Aires");
        ReflectionTestUtils.setField(dataConsumptionService, "unit", "MB");
        ReflectionTestUtils.setField(dataConsumptionService, "ggsmDecimalDivisor", BigDecimal.valueOf(100000));
    }

    @Test
    void testGetDataConsumption() {
        DataConsumptionRequest dataConsumptionRequest = getDataConsumptionRequest();

        final List<DataConsumption> result = dataConsumptionService.getDataConsumption("3813520705",
                dataConsumptionRequest);
        assertNotNull(result);
        assertFalse(result.get(0).isRoaming());
        assertEquals(BigDecimal.valueOf(-309.01), result.get(0).getCost().getValue());
        assertEquals(BigDecimal.valueOf(0.12), result.get(0).getVolume().getValue());
        assertEquals("5", result.get(0).getRatingGroup());
        assertFalse(result.get(0).isSocialNetwork());
        assertEquals(1, result.size());
    }

    private DataConsumptionRequest getDataConsumptionRequest() {
        return DataConsumptionRequest.builder()
                .dateFrom(CdrDate.builder()
                        .dateTime("2018-06-06 00:00:00.000 -0300")
                        .format("yyyy-MM-dd HH:mm:ss.SSS Z")
                        .build())
                .dateTo(CdrDate.builder()
                        .dateTime("2018-06-06 00:00:00.000 -0300")
                        .format("yyyy-MM-dd HH:mm:ss.SSS Z")
                        .build())
                .filterList(FilterList.builder()
                        .roaming("false")
                        .ratingGroupList(List.of("5", "60", "61"))
                        .build())
                .build();
    }
}