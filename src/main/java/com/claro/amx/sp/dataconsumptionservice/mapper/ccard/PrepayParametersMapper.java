package com.claro.amx.sp.dataconsumptionservice.mapper.ccard;


import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrepayParametersMapper {
    List<PrepayParameters> getPrepayParametersData(String parameterName);
}