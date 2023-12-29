package com.claro.amx.sp.dataconsumptionservice.service.impl;

import com.claro.amx.sp.dataconsumptionservice.business.bo.RatingGroupDescriptionBO;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.service.RatingGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingGroupServiceTest {
    @Mock
    private RatingGroupDescriptionBO ratingGroupDescriptionBO;
    private RatingGroupService ratingGroupService;

    @BeforeEach
    void setUp() {
        when(ratingGroupDescriptionBO.getRatingGroupsDescription(anyList()))
                .thenReturn(List.of(RatingGroupDescription.builder().build()));

        ratingGroupService = new RatingGroupServiceImpl(ratingGroupDescriptionBO);
    }

    @Test
    void testGetRatingGroupsDescription() {
        final List<RatingGroupDescription> result = ratingGroupService.getRatingGroupsDescription(anyList());
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}