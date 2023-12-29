package com.claro.amx.sp.dataconsumptionservice.config;

import com.claro.amx.sp.dataconsumptionservice.exception.impl.InternalException;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.ERROR_GENERAL;

@Configuration
@MapperScan({"com.claro.amx.sp.dataconsumptionservice.mapper.ccard", "com.claro.amx.sp.dataconsumptionservice.mapper.prod"})
public class MyBatisConfig {
    public static final String PROD_SESSION_FACTORY = "prodSessionFactory";
    public static final String CCARD_SESSION_FACTORY = "ccardSessionFactory";
    @Value("${mybatis.config.ccard.mapper-locations:-}")
    private String ccardMapperLocations;
    @Value("${mybatis.config.prod.mapper-locations:-}")
    private String prodMapperLocations;

    @Bean(name = CCARD_SESSION_FACTORY, destroyMethod = "")
    public SqlSessionFactoryBean ccardSQLSessionFactory(@Qualifier(DataSourceConfig.CCARD_DATASOURCE) final DataSource dataSource) {
        return getSqlSessionFactoryBean(dataSource, ccardMapperLocations);
    }

    @Bean(name = PROD_SESSION_FACTORY, destroyMethod = "")
    @Primary
    public SqlSessionFactoryBean prodSQLSessionFactory(@Qualifier(DataSourceConfig.PROD_DATASOURCE) final DataSource dataSource) {
        return getSqlSessionFactoryBean(dataSource, prodMapperLocations);
    }

    private SqlSessionFactoryBean getSqlSessionFactoryBean(final DataSource dataSource, String mapperLocations) {
        try {
            final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources(mapperLocations));
            return sqlSessionFactoryBean;
        } catch (Exception e) {
            throw new InternalException(ERROR_GENERAL.getCode(), String.format(ERROR_GENERAL.getMessage(), e.getCause(), e));
        }
    }

}

