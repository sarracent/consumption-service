package com.claro.amx.sp.dataconsumptionservice.business.bo;

import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;

import java.util.List;

public interface RatingGroupDescriptionBO {
    List<RatingGroupDescription> getRatingGroupsDescription(List<String> ratingGroups);
}
