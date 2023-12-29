package com.claro.amx.sp.dataconsumptionservice.controller.impl;

import com.claro.amx.sp.dataconsumptionservice.manager.RatingGroupDescriptionManager;
import com.claro.amx.sp.dataconsumptionservice.model.response.DataConsumptionFilterListResponse;
import com.claro.amx.sp.dataconsumptionservice.service.Resilience4jService;
import com.claro.amx.sp.dataconsumptionservice.service.impl.DataConsumptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class DataConsumptionFilterListControllerTest {
    @MockBean
    private RatingGroupDescriptionManager ratingGroupDescriptionManager;
    @MockBean
    private Resilience4jService resilience4jService;
    @MockBean
    private DataConsumptionServiceImpl dataConsumptionService;
    @Autowired
    private DataConsumptionFilterListControllerImpl dataConsumptionFilterListController;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(dataConsumptionService, "ggsmDecimalDivisor", BigDecimal.valueOf(100000));
    }

    @Test
    void testGetDataConsumptionFilterList() {
        final Map<String, Object> headersMap = getHeadersMap();

        final ResponseEntity<DataConsumptionFilterListResponse> response = dataConsumptionFilterListController
                .getDataConsumptionFilterList(headersMap);
        assertNotNull(response);
        assertEquals(200 ,response.getStatusCodeValue());
    }

    private Map<String, Object> getHeadersMap() {
        return Map.of("session-id", "sessionId", "channel-id", "channelId"
                , "user-id", "userId");
    }
}