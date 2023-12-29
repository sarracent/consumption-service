package com.claro.amx.sp.dataconsumptionservice.mapper.prod;

import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RatingGroupDescriptionMapper {
    List<RatingGroupDescription> getRatingGroups(List<String> ratingGroups);
}
