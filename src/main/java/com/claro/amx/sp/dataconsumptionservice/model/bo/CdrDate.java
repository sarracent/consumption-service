package com.claro.amx.sp.dataconsumptionservice.model.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(hidden = true)
public class CdrDate {
    @NotEmpty(message = "Error el campo dateTime es requerido.")
    private String dateTime;
    @NotEmpty(message = "Error el campo format es requerido.")
    private String format;
}
