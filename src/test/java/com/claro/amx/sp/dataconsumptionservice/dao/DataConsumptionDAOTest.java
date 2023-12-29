package com.claro.amx.sp.dataconsumptionservice.dao;

import com.claro.amx.sp.dataconsumptionservice.dao.ccard.DataConsumptionDAO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.DataBaseException;
import com.claro.amx.sp.dataconsumptionservice.mapper.ccard.DataConsumptionMapper;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.DataDailyConsumption;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataConsumptionDAOTest {
    @Mock
    private DataConsumptionMapper mapper;
    @Mock
    private SqlSessionFactoryBean mockedSessionFactoryBean;
    @Mock
    private SqlSessionFactory mockedSessionFactory;
    @Mock
    private SqlSession mockedSession;
    private DataConsumptionDAO dataConsumptionDAO;

    @BeforeEach
    void setUp() throws Exception {
        when(mockedSession.getMapper(DataConsumptionMapper.class)).thenReturn(mapper);
        when(mockedSession.getConfiguration()).thenReturn(new Configuration());
        when(mockedSessionFactoryBean.getObject()).thenReturn(mockedSessionFactory);
        when(Objects.requireNonNull(mockedSessionFactoryBean.getObject()).openSession()).thenReturn(mockedSession);

        dataConsumptionDAO = new DataConsumptionDAO(mockedSessionFactoryBean);
    }

    @Test
    void testGetDataConsumptionOK() {
        when(mapper.getDataConsumption("billNumber", "keyPartition", "dateFrom",
                "dateTo", List.of("ratingGroup"), "roaming")).thenReturn(List.of(DataDailyConsumption.builder().build()));

        final List<DataDailyConsumption> result = dataConsumptionDAO.getDataConsumption("billNumber",
                "keyPartition", "dateFrom", "dateTo", List.of("ratingGroup"), "roaming");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetDataConsumptionError() {
        when(mapper.getDataConsumption(null, "keyPartition", "dateFrom",
                "dateTo", List.of("ratingGroup"), "roaming")).thenThrow(DataBaseException.class);

        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            dataConsumptionDAO.getDataConsumption(null,
                    "keyPartition", "dateFrom", "dateTo", List.of("ratingGroup"), "roaming");
        });

        assertEquals("200006", thrown.getCode());
        assertTrue(thrown.getMessage().contains("Error al consultar la base de datos CCARD DataConsumptionDAO"));
    }

    @Test
    void testGetDataConsumptionPersistenceExceptionError() {
        when(mapper.getDataConsumption("billNumber", "keyPartition", "dateFrom",
                "dateTo", List.of("ratingGroup"), "roaming")).thenThrow(PersistenceException.class);

        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            dataConsumptionDAO.getDataConsumption("billNumber", "keyPartition", "dateFrom",
                    "dateTo", List.of("ratingGroup"), "roaming");
        });

        assertEquals("200006", thrown.getCode());
    }
}