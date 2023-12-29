package com.claro.amx.sp.dataconsumptionservice.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void constantTest() {
        assertAll("Constants",
                () -> assertEquals("Session-Id", Constants.SESSION_NAME),
                () -> assertEquals("Session-Id sent by client. It is the field that is used to keep track of the request made in the application.", Constants.SESSION_DESCR),
                () -> assertEquals("Channel-Id", Constants.CHANNEL_NAME),
                () -> assertEquals("Channel-Id sent by client. It is the field that is used to indicate the channel that the application consumes.", Constants.CHANNEL_DESCR),
                () -> assertEquals("User-Id", Constants.USER_NAME),
                () -> assertEquals("User-Id sent by client. It is the field that is used to indicate the user that the application consumes.", Constants.USER_DESCR),
                () -> assertEquals("BillNumber", Constants.BILL_NUMBER),
                () -> assertEquals("Success", Constants.SUCCESS_MSG),
                () -> assertEquals("DataBase Error", Constants.DATABASE_MSG),
                () -> assertEquals("Business Error", Constants.BUSINESS_MSG),
                () -> assertEquals("Technical Error", Constants.TECHNICAL_MSG),
                () -> assertEquals("External Error", Constants.EXTERNAL_MSG),
                () -> assertEquals("Internal Error", Constants.INTERNAL_MSG),
                () -> assertEquals("OK", Constants.OK_MSG),
                () -> assertEquals("0", Constants.ZERO_MSG),
                () -> assertEquals(" ", Constants.SPACE),
                () -> assertEquals("Y", Constants.Y),
                () -> assertEquals("N", Constants.N),
                () -> assertEquals("%s.%s", Constants.OPNAME),
                () -> assertEquals("%s - Line Code Error: %s", Constants.PARSE_EXCEPTION),
                () -> assertEquals("#", Constants.HASH),
                () -> assertEquals(",", Constants.COMMA),
                () -> assertEquals("_", Constants.UNDERSCORE),
                () -> assertEquals("RATING_GROUPS_FILTERS", Constants.RATING_GROUPS_FILTERS),
                () -> assertEquals("RATING_GROUPS_SOCIAL_NETWORKS", Constants.RATING_GROUPS_SOCIAL_NETWORKS),
                () -> assertEquals("dd-MM-yyyy", Constants.DD_MM_YYY),
                () -> assertEquals("yyyy-MM-dd HH:mm:ss.SSS Z", Constants.FORMATTER),
                () -> assertEquals("pre:v_rating_group_description:1", Constants.DESCRIPTION_CACHE),
                () -> assertEquals("pre:prepay_parameters:1", Constants.PARAMETERS_CACHE),
                () -> assertEquals(3600, Constants.TIME_TO_LIVE));
    }

}