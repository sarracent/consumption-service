package com.claro.amx.sp.dataconsumptionservice.business.bo;

import com.claro.amx.sp.dataconsumptionservice.model.ccard.DataDailyConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;

import java.util.List;

public interface DataConsumptionBO {
    List<DataDailyConsumption> getDataConsumption(String billNumber, String dateFrom, String dateTo,
                                                  DataConsumptionRequest dataConsumptionRequest);
}
