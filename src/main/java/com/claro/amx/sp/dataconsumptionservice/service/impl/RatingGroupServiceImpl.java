package com.claro.amx.sp.dataconsumptionservice.service.impl;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogService;
import com.claro.amx.sp.dataconsumptionservice.business.bo.RatingGroupDescriptionBO;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.service.RatingGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingGroupServiceImpl implements RatingGroupService {

    private final RatingGroupDescriptionBO ratingGroupDescriptionBO;

    @Override
    @LogService
    public List<RatingGroupDescription> getRatingGroupsDescription(List<String> ratingGroupsIds) {
        return ratingGroupDescriptionBO.getRatingGroupsDescription(ratingGroupsIds);
    }

}
