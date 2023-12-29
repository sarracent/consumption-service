package com.claro.amx.sp.dataconsumptionservice.constants;

public final class Constants {

    public static final String SESSION_NAME = "Session-Id";
    public static final String SESSION_DESCR = "Session-Id sent by client. It is the field that is used to keep track of the request made in the application.";

    public static final String CHANNEL_NAME = "Channel-Id";
    public static final String CHANNEL_DESCR = "Channel-Id sent by client. It is the field that is used to indicate the channel that the application consumes.";

    public static final String USER_NAME = "User-Id";
    public static final String USER_DESCR = "User-Id sent by client. It is the field that is used to indicate the user that the application consumes.";

    public static final String BILL_NUMBER = "BillNumber";

    public static final String SUCCESS_MSG = "Success";
    public static final String DATABASE_MSG = "DataBase Error";
    public static final String BUSINESS_MSG = "Business Error";
    public static final String TECHNICAL_MSG = "Technical Error";
    public static final String EXTERNAL_MSG = "External Error";
    public static final String INTERNAL_MSG = "Internal Error";

    public static final String OK_MSG = "OK";
    public static final String ZERO_MSG = "0";
    public static final String SPACE = " ";
    public static final String Y = "Y";
    public static final String N = "N";
    public static final String OPNAME = "%s.%s";
    public static final String PARSE_EXCEPTION = "%s - Line Code Error: %s";

    public static final String HASH = "#";
    public static final String COMMA = ",";
    public static final String UNDERSCORE = "_";

    public static final String RATING_GROUPS_FILTERS = "RATING_GROUPS_FILTERS";
    public static final String RATING_GROUPS_SOCIAL_NETWORKS = "RATING_GROUPS_SOCIAL_NETWORKS";

    public static final String DD_MM_YYY = "dd-MM-yyyy";

    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss.SSS Z";

    public static final String DESCRIPTION_CACHE="pre:v_rating_group_description:1";
    public static final String PARAMETERS_CACHE="pre:prepay_parameters:1";
    public static final long TIME_TO_LIVE = 3600;

    private Constants() {
    }

}
