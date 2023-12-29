package com.claro.amx.sp.dataconsumptionservice.service;

import com.claro.amx.sp.dataconsumptionservice.model.bo.DataConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;

import java.util.List;

public interface DataConsumptionService {
    List<DataConsumption> getDataConsumption(String billNumber, DataConsumptionRequest dataConsumptionRequest);
}
