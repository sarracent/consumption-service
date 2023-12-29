package com.claro.amx.sp.dataconsumptionservice.service.impl;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogService;
import com.claro.amx.sp.dataconsumptionservice.business.bo.DataConsumptionBO;
import com.claro.amx.sp.dataconsumptionservice.manager.PrepayParametersManager;
import com.claro.amx.sp.dataconsumptionservice.manager.RatingGroupDescriptionManager;
import com.claro.amx.sp.dataconsumptionservice.model.bo.CdrDate;
import com.claro.amx.sp.dataconsumptionservice.model.bo.Cost;
import com.claro.amx.sp.dataconsumptionservice.model.bo.DataConsumption;
import com.claro.amx.sp.dataconsumptionservice.model.bo.Volume;
import com.claro.amx.sp.dataconsumptionservice.model.request.DataConsumptionRequest;
import com.claro.amx.sp.dataconsumptionservice.service.DataConsumptionService;
import com.claro.amx.sp.dataconsumptionservice.utility.ConvertSizeFormatUtil;
import com.claro.amx.sp.dataconsumptionservice.utility.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class DataConsumptionServiceImpl implements DataConsumptionService {
    @Value("${currency}")
    private String currency;
    @Value("${unit}")
    private String unit;
    @Value("${TZ}")
    private String timeZone;
    @Value("${gsm.decimal.divisor}")
    private BigDecimal ggsmDecimalDivisor;
    private final DataConsumptionBO dataConsumptionBO;
    private final RatingGroupDescriptionManager ratingGroupDescriptionManager;
    private final PrepayParametersManager prepayParametersManager;

    @Override
    @LogService
    public List<DataConsumption> getDataConsumption(String billNumber, DataConsumptionRequest dataConsumptionRequest) {
        List<DataConsumption> dataConsumptions = new ArrayList<>();
        String dateFrom = getFormatZonedDateTime(Util.getZonedDateTime(dataConsumptionRequest.getDateFrom().getDateTime(),
                dataConsumptionRequest.getDateFrom().getFormat()), DD_MM_YYY, timeZone);
        String dateTo = getFormatZonedDateTime(Util.getZonedDateTime(dataConsumptionRequest.getDateTo().getDateTime(),
                dataConsumptionRequest.getDateTo().getFormat()), DD_MM_YYY, timeZone);
        dataConsumptionBO.getDataConsumption(billNumber, dateFrom, dateTo, dataConsumptionRequest).forEach(dataDailyConsumption -> {
            DataConsumption dataConsumption = DataConsumption.builder()
                    .cdrDate(CdrDate.builder()
                            .dateTime(getFormatZonedDateTime(dataDailyConsumption.getCdrDate(), FORMATTER, timeZone))
                            .format(FORMATTER)
                            .build())
                    .roaming(Util.getRoaming(dataDailyConsumption.getRoaming()))
                    .cost(Cost.builder()
                            .currency(currency)
                            .value(dataDailyConsumption.getCost().divide(ggsmDecimalDivisor, 2, RoundingMode.HALF_EVEN))
                            .build())
                    .volume(Volume.builder()
                            .unit(unit)
                            .value(ConvertSizeFormatUtil.convertSize(dataDailyConsumption.getVolume(), unit))
                            .build())
                    .ratingGroup(dataDailyConsumption.getRatingGroup())
                    .ratingGroupDescription(ratingGroupDescriptionManager
                            .getRatingGroupsDescription(List.of(dataDailyConsumption
                            .getRatingGroup())).get(0).getDescription())
                    .isSocialNetwork(Util.getValueList(prepayParametersManager
                                    .getPrepayParameters(RATING_GROUPS_SOCIAL_NETWORKS)
                                    .getValue(), HASH)
                            .contains(dataDailyConsumption.getRatingGroup()))
                    .build();
            dataConsumptions.add(dataConsumption);
        });
        return dataConsumptions;
    }

    public String getFormatZonedDateTime(LocalDateTime date, String pattern, String timeZone) {
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.of(timeZone));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return zdt.format(dtf);
    }
}
