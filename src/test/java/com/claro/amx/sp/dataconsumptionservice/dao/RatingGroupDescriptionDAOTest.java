package com.claro.amx.sp.dataconsumptionservice.dao;

import com.claro.amx.sp.dataconsumptionservice.dao.prod.RatingGroupDescriptionDAO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.DataBaseException;
import com.claro.amx.sp.dataconsumptionservice.mapper.prod.RatingGroupDescriptionMapper;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.SqlSessionFactoryBean;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingGroupDescriptionDAOTest {
    @Mock
    private RatingGroupDescriptionMapper mapper;
    @Mock
    private SqlSessionFactoryBean mockedSessionFactoryBean;
    @Mock
    private SqlSessionFactory mockedSessionFactory;
    @Mock
    private SqlSession mockedSession;
    private RatingGroupDescriptionDAO ratingGroupDescriptionDAO;

    @BeforeEach
    void setUp() throws Exception {
        when(mockedSession.getMapper(RatingGroupDescriptionMapper.class)).thenReturn(mapper);
        when(mockedSession.getConfiguration()).thenReturn(new Configuration());
        when(mockedSessionFactoryBean.getObject()).thenReturn(mockedSessionFactory);
        when(Objects.requireNonNull(mockedSessionFactoryBean.getObject()).openSession()).thenReturn(mockedSession);

        ratingGroupDescriptionDAO = new RatingGroupDescriptionDAO(mockedSessionFactoryBean);
    }

    @Test
    void testGetRatingGroupDescriptionDataOK() {
        when(mapper.getRatingGroups(List.of("5"))).thenReturn(List.of(RatingGroupDescription.builder()
                .ratingGroupId("5")
                .description("Conexion Movil")
                .build()));

        final List<RatingGroupDescription> result = ratingGroupDescriptionDAO.getRatingGroupDescriptionData(List.of("5"));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetRatingGroupDescriptionDataError() {
        when(mapper.getRatingGroups(anyList())).thenThrow(DataBaseException.class);

        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            ratingGroupDescriptionDAO.getRatingGroupDescriptionData(null);
        });

        assertEquals("200007", thrown.getCode());
        assertTrue(thrown.getMessage().contains("Error al consultar la base de datos PROD RatingGroupDescriptionDAO"));
    }

    @Test
    void testGetRatingGroupDescriptionDataPersistenceExceptionError() {
        when(mapper.getRatingGroups(List.of("5"))).thenThrow(PersistenceException.class);

        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            ratingGroupDescriptionDAO.getRatingGroupDescriptionData(List.of("5"));
        });

        assertEquals("200007", thrown.getCode());
    }
}