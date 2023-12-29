package com.claro.amx.sp.dataconsumptionservice.controller.impl;

import com.claro.amx.sp.dataconsumptionservice.exception.impl.BadRequestException;
import com.claro.amx.sp.dataconsumptionservice.manager.PrepayParametersManager;
import com.claro.amx.sp.dataconsumptionservice.manager.RatingGroupDescriptionManager;
import com.claro.amx.sp.dataconsumptionservice.model.bo.CdrDate;
import com.claro.amx.sp.dataconsumptionservice.model.bo.DataConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.bo.FilterList;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;
import com.claro.amx.sp.dataconsumptionservice.model.response.DataConsumptionResponse;
import com.claro.amx.sp.dataconsumptionservice.service.DataConsumptionService;
import com.claro.amx.sp.dataconsumptionservice.service.Resilience4jService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.RATING_GROUPS_SOCIAL_NETWORKS;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class DataConsumptionControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private DataConsumptionService dataConsumptionService;
    @MockBean
    private PrepayParametersManager prepayParametersManager;
    @MockBean
    private RatingGroupDescriptionManager ratingGroupDescriptionManager;
    @MockBean
    private Resilience4jService resilience4jService;
    @Autowired
    private DataConsumptionControllerImpl dataConsumptionController;


    @Test
    void testGetDataConsumptionByBillNumber() throws Exception {
        DataConsumptionRequest dataConsumptionRequest = getDataConsumptionRequest();

        when(resilience4jService.executeDataConsumption(() -> dataConsumptionService
                .getDataConsumption("3813520705", dataConsumptionRequest)))
                .thenReturn(List.of(DataConsumption.builder().build()));
        when(prepayParametersManager.getPrepayParameters(anyString())).thenReturn(PrepayParameters.builder()
                .name(RATING_GROUPS_SOCIAL_NETWORKS)
                .value("#992#993#994#995#997#")
                .build());
        when(ratingGroupDescriptionManager.getRatingGroupsDescription(anyList()))
                .thenReturn(List.of(RatingGroupDescription.builder().build()));


        MockHttpServletResponse response = mvc.perform(
                        post("/dataConsumption")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header("BillNumber", "3813520705")
                                .header("Session-Id", "sessionId")
                                .header("Channel-Id ", "channelId")
                                .header("User-Id", "userId")
                                .content(dataConsumptionRequest.toString()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testGetDataConsumptionByBillNumberResponseOK() {
        DataConsumptionRequest dataConsumptionRequest = getDataConsumptionRequest();
        final Map<String, Object> headersMap = getHeadersMap();

        final ResponseEntity<DataConsumptionResponse> response = dataConsumptionController
                .getDataConsumption(headersMap, "3813520705", dataConsumptionRequest);

        assertNotNull(response);
        assertEquals(200 ,response.getStatusCodeValue());
    }

    @Test
    void testGetDataConsumptionByBillNumberBadRequest() {
        DataConsumptionRequest dataConsumptionRequest = getDataConsumptionBadRequest();
        final Map<String, Object> headersMap = getHeadersMap();

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            dataConsumptionController.getDataConsumption(headersMap, "3813520705",
                    dataConsumptionRequest);
        });

        assertEquals("100002", thrown.getCode());
        assertEquals("Error la fecha 2018-06-06 00:00:00.000 +0000 no puede ser posterior a 2018-05-06 00:00:00.000 +0000",
                thrown.getMessage());
    }

    @Test
    void testGetDataConsumptionBadBillNumber() {
        DataConsumptionRequest dataConsumptionRequest = getDataConsumptionRequest();
        final Map<String, Object> headersMap = getHeadersMapBadBillNumber();

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            dataConsumptionController.getDataConsumption(headersMap, "38135s20705z",
                    dataConsumptionRequest);
        });

        assertEquals("100005", thrown.getCode());
        assertEquals("Error el Bill Number 38135s20705z no puede contener letras",
                thrown.getMessage());
    }

    private DataConsumptionRequest getDataConsumptionBadRequest() {
        return DataConsumptionRequest.builder()
                .dateFrom(CdrDate.builder()
                        .dateTime("2018-06-06 00:00:00.000 +0000")
                        .format("yyyy-MM-dd HH:mm:ss.SSS Z")
                        .build())
                .dateTo(CdrDate.builder()
                        .dateTime("2018-05-06 00:00:00.000 +0000")
                        .format("yyyy-MM-dd HH:mm:ss.SSS Z")
                        .build())
                .filterList(FilterList.builder()
                        .roaming("false")
                        .ratingGroupList(List.of("5", "60", "61"))
                        .build())
                .build();
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

    private Map<String, Object> getHeadersMap() {
        return Map.of("session-id", "sessionId", "channel-id", "channelId", "billnumber", "3813520705",
                "user-id", "userId");
    }

    private Map<String, Object> getHeadersMapBadBillNumber() {
        return Map.of("session-id", "sessionId", "channel-id", "channelId", "billnumber", "38135s20705z",
                "user-id", "userId");
    }
}