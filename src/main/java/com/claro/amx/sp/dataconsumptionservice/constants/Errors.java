package com.claro.amx.sp.dataconsumptionservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errors {
    ERROR_BUSINESS_VALIDATION_NULL_OR_EMPTY("100000", "Error el campo %s es nulo o vacio"),
    ERROR_BADREQUEST_GENERAL("100001", "No se puede procesar la informacion entrante - Field: %s"),
    ERROR_BADREQUEST_FAIL_DATE("100002", "Error la fecha %s no puede ser posterior a %s"),
    ERROR_BADREQUEST_FAIL_ROAMING("100003", "El campo Roaming debería ser true, false o null"),
    ERROR_BADREQUEST_FAIL_RATINGGROUPLIST("100004", "Error el campo ratingGroupList debería ser un array"),
    ERROR_BADREQUEST_FAIL_BILL_NUMBER("100005", "Error el Bill Number %s no puede contener letras"),


    ERROR_DATABASE_GENERAL("200000", "Error al consultar la base de datos -> [%s] %s"),
    ERROR_DATABASE_PARAMETERS_DUPLICATE("200001", "Error se encontraron 2 o mas registros en PREPAY_PARAMETERS con el valor %s"),
    ERROR_DATABASE_PARAMETERS_NOT_FOUND("200002", "Error no se encontraron registros en PREPAY_PARAMETERS con el valor %s"),
    ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION_NOT_FOUND("200003", "Error no se encontraron registros en V_RATING_GROUP_DESCRIPTION con el valor %s"),
    ERROR_DATABASE_DATA_CONSUMPTION_NOT_FOUND("200004", "Error no se encontraron registros en DATA_DAILY_CONSUMPTION con el valor %s"),

    ERROR_DATABASE_PARAMETERS("200005", "Error al consultar la base de datos CCARD PrepayParametersDAO -> [%s] %s"),
    ERROR_DATABASE_DATA_CONSUMPTION("200006", "Error al consultar la base de datos CCARD DataConsumptionDAO -> [%s] %s"),
    ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION("200007", "Error al consultar la base de datos PROD RatingGroupDescriptionDAO -> [%s] %s"),
    ERROR_DATABASE_PARAMETERS_NOT_VALUE("200008", "Error no se encontraron datos cargados en el registro de PREPAY_PARAMETERS con el valor %s"),
    ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION_NOT_VALUE("200009", "Error no se encontraron datos cargados en el registro de V_RATING_GROUP_DESCRIPTION con el valor %s"),
    ERROR_DATABASE_TIMEOUT("200010","Timeout - %s segundos - <query>"),

    ERROR_REDIS_GENERAL("200100","Error al consultar cache en redis - <Error Message>"),
    ERROR_REDIS_SERVICE_DOWN("200101","Error Service REDIS Down"),
    ERROR_REDIS_TIMEOUT("200102","Timeout - %s segundos - <query>"),
    ERROR_REDIS_PREPAY_PARAMETERS_MANAGER("200103","Error Redis PrepayParametersManager -> [%s] %s"),
    ERROR_REDIS_V_RATING_GROUP_DESCRIPTION_MANAGER("200104","Error Redis RatingGroupDescriptionManager -> [%s] %s"),

    ERROR_GENERAL("900000", "Error General -> [%s] %s");

    private final String code;
    private final String message;

}