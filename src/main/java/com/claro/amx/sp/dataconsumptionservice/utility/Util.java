package com.claro.amx.sp.dataconsumptionservice.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.*;
import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.ERROR_GENERAL;

/**
 * The type Util.
 */

public class Util {
    private static final ObjectMapper mapperJson = getMapperJson();

    private Util() {
    }

    /**
     * Generate mapper without WRITE_DATES_AS_TIMESTAMPS.
     *
     * @return the static ObjectMapper
     */
    public static ObjectMapper getMapperJson() {
        ObjectMapper mapperJson = new ObjectMapper();
        mapperJson.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapperJson.registerModule(new JavaTimeModule());
        mapperJson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapperJson;
    }

    /**
     * Generate unique id string.
     *
     * @param pKeyConcactToId the p key concact to id
     * @return the string
     */
    public static String generateUniqueId(String pKeyConcactToId) {
        if (isNullOrEmpty(pKeyConcactToId))
            return UUID.randomUUID().toString().replace("-", "");
        return UUID.randomUUID().toString().replace("-", "") + "-" + pKeyConcactToId;
    }

    /**
     * Is null or empty.
     *
     * @param pText the p text
     * @return the boolean
     */
    public static boolean isNullOrEmpty(String pText) {
        return pText == null || pText.isEmpty();
    }


    /**
     * Converts an Object to JSONString.
     *
     * @param input the input
     * @return the string
     */
    public static String toJSONString(Object input) {
        if (input == null) return null;
        try {
            return mapperJson.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            return String.format(ERROR_GENERAL.getMessage(), e.getMessage(), e);
        }
    }

    /**
     * ZonedDateTime converted to string by config pattern
     *
     * @param date    the String
     * @param pattern the String pattern
     * @return the string
     */
    public static LocalDateTime getZonedDateTime(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormatter
                .ofPattern(pattern));
    }

    /**
     * String converted to List separated by field
     *
     * @param value     the string value to convert in list
     * @param separator the string separator
     * @return the list
     */
    public static List<String> getValueList(String value, String separator) {
        if (separator == null) return List.of(value);
        if (separator.contains(HASH))
            separator = HASH;
        String stringToList = value.startsWith(separator) ? value.replaceFirst(separator, "") : value;
        return Arrays.asList(stringToList.split(separator));
    }

    /**
     * Convert message with throwable line code error to String.
     *
     * @param message   the message error
     * @param throwable the throwable to get line code error
     * @return the string
     */
    public static String getMsgWithLineCodeError(String message, Throwable throwable) {
        try {
            String lineCodeError = Arrays.stream(throwable.getStackTrace()).findFirst().map(StackTraceElement::toString).orElse(null);
            if (message == null) return lineCodeError;
            return String.format(PARSE_EXCEPTION, message, lineCodeError);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getYesOrNoRoaming(String roaming) {
        if (roaming!=null) {
            return (roaming.equalsIgnoreCase("true"))? "Y" : "N";
        }
        return null;
    }

    public static boolean getRoaming(String roaming) {
        return roaming.equalsIgnoreCase("Y");
    }

    public static boolean validateBillNumber(String billNumber) {
        for (int x = 0; x < billNumber.length(); x++) {
            char c = billNumber.charAt(x);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return true;
            }
        }
        return false;
    }

}
