package com.claro.amx.sp.dataconsumptionservice.service;

import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;

import java.util.List;

public interface RatingGroupService {
    List<RatingGroupDescription> getRatingGroupsDescription(List<String> ratingGroups);
}
