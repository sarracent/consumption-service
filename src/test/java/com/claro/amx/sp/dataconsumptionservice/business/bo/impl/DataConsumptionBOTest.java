package com.claro.amx.sp.dataconsumptionservice.business.bo.impl;

import com.claro.amx.sp.dataconsumptionservice.business.bo.DataConsumptionBO;
import com.claro.amx.sp.dataconsumptionservice.dao.ccard.DataConsumptionDAO;
import com.claro.amx.sp.dataconsumptionservice.model.bo.CdrDate;
import com.claro.amx.sp.dataconsumptionservice.model.bo.FilterList;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.DataDailyConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataConsumptionBOTest {
    @Mock
    private DataConsumptionDAO dataConsumptionDAO;
    private DataConsumptionBO dataConsumptionBO;

    @BeforeEach
    void setUp() {
        when(dataConsumptionDAO.getDataConsumption(anyString(), anyString(), anyString(),anyString(), anyList(),
                anyString())).thenReturn(List.of(DataDailyConsumption.builder().build()));

        dataConsumptionBO = new DataConsumptionBOImpl(dataConsumptionDAO);
    }

    @Test
    void testGetDataConsumption() {
        DataConsumptionRequest dataConsumptionRequest = getDataConsumptionRequest();

        final List<DataDailyConsumption> result = dataConsumptionBO.getDataConsumption("3813520705", "06-06-2018",
                "06-06-2018", dataConsumptionRequest);
        assertNotNull(result);
        assertEquals(1, result.size());
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