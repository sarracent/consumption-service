package com.claro.amx.sp.dataconsumptionservice.mapper.ccard;

import com.claro.amx.sp.dataconsumptionservice.model.ccard.DataDailyConsumption;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataConsumptionMapper {
    List<DataDailyConsumption> getDataConsumption(String billNumber, String keyPartition, String dateFrom,
                                                  String dateTo, List<String> ratingGroups, String roaming);
}
