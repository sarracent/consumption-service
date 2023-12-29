package com.claro.amx.sp.dataconsumptionservice.business.bo.impl;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogBO;
import com.claro.amx.sp.dataconsumptionservice.business.bo.RatingGroupDescriptionBO;
import com.claro.amx.sp.dataconsumptionservice.dao.prod.RatingGroupDescriptionDAO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.BusinessException;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION_NOT_FOUND;

@Component
@AllArgsConstructor
public class RatingGroupDescriptionBOImpl implements RatingGroupDescriptionBO {

    private final RatingGroupDescriptionDAO ratingGroupDescriptionDAO;

    @LogBO
    @Override
    public List<RatingGroupDescription> getRatingGroupsDescription(List<String> ratingGroups) {
        final List<RatingGroupDescription> ratingGroupDescriptionList = ratingGroupDescriptionDAO
                .getRatingGroupDescriptionData(ratingGroups);
        if (ratingGroupDescriptionList == null || ratingGroupDescriptionList.isEmpty())
                throw new BusinessException(ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION_NOT_FOUND.getCode(),
                    String.format(ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION_NOT_FOUND.getMessage(), ratingGroups));

        return ratingGroupDescriptionList;
    }

}
