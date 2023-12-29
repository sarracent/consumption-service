package com.claro.amx.sp.dataconsumptionservice.business.bo.impl;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogBO;
import com.claro.amx.sp.dataconsumptionservice.business.bo.DataConsumptionBO;
import com.claro.amx.sp.dataconsumptionservice.dao.ccard.DataConsumptionDAO;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.DataDailyConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;
import com.claro.amx.sp.dataconsumptionservice.utility.Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataConsumptionBOImpl implements DataConsumptionBO {

    private final DataConsumptionDAO dataConsumptionDAO;

    @LogBO
    @Override
    public List<DataDailyConsumption> getDataConsumption(String billNumber, String dateFrom, String dateTo,
                                                         DataConsumptionRequest dataConsumptionRequest) {

        String keyPartition = billNumber.substring(billNumber.length() - 1);
        List<String> ratingGroups = dataConsumptionRequest.getFilterList().getRatingGroupList();
        String roaming = Util.getYesOrNoRoaming(dataConsumptionRequest.getFilterList().getRoaming());

        return dataConsumptionDAO.getDataConsumption(billNumber,
                keyPartition, dateFrom, dateTo, ratingGroups, roaming);

    }
}
