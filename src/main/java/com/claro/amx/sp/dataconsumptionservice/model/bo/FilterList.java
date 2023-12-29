package com.claro.amx.sp.dataconsumptionservice.model.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
public class FilterList {
    @Pattern(regexp = "^(true|false)$", message = "El campo roaming debería ser true, false o null")
    private String roaming;
    private List<String> ratingGroupList;

}
