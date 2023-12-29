package com.claro.amx.sp.dataconsumptionservice.business.bo.impl;

import com.claro.amx.sp.dataconsumptionservice.business.bo.RatingGroupDescriptionBO;
import com.claro.amx.sp.dataconsumptionservice.dao.prod.RatingGroupDescriptionDAO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.BusinessException;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingGroupDescriptionBOTest {
    @Mock
    private RatingGroupDescriptionDAO ratingGroupDescriptionDAO;
    private RatingGroupDescriptionBO ratingGroupDescriptionBO;

    @BeforeEach
    void setUp() {
        ratingGroupDescriptionBO = new RatingGroupDescriptionBOImpl(ratingGroupDescriptionDAO);
    }

    @Test
    void TestGetRatingGroupsDescription() {
        when(ratingGroupDescriptionDAO.getRatingGroupDescriptionData(anyList()))
                .thenReturn(List.of(RatingGroupDescription.builder().build()));

        final List<RatingGroupDescription> result = ratingGroupDescriptionBO.getRatingGroupsDescription(List.of("5"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void TestGetRatingGroupsDescriptionIsEmpty() {
        when(ratingGroupDescriptionDAO.getRatingGroupDescriptionData(anyList()))
                .thenReturn(Collections.emptyList());

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            ratingGroupDescriptionBO.getRatingGroupsDescription(List.of("5"));
        });

        assertEquals("200003", thrown.getCode());
        assertEquals("Error no se encontraron registros en V_RATING_GROUP_DESCRIPTION con el valor [5]",
                thrown.getMessage());
    }
}