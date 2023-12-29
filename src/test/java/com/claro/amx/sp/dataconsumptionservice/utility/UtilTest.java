package com.claro.amx.sp.dataconsumptionservice.utility;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {
    @Test
    void testClassCreation() {
        assertNotNull(new UtilTest());
    }

    @Test
    void getMapperJson() {
        assertNotNull("getMapperJson_not_null", Util.getMapperJson().toString());
    }

    @Test
    void generateUniqueId() {
        assertNotNull("generateUniqueId_key_null ", Util.generateUniqueId(null));
        assertNotNull("generateUniqueId_key_not_null ", Util.generateUniqueId("Test"));
    }

    @Test
    void isNullOrEmpty() {
        assertTrue(Util.isNullOrEmpty(null), "isNullOrEmpty_true");
        assertFalse(Util.isNullOrEmpty("test"), "isNullOrEmpty_false");
    }

    @Test
    void toJSONString() {
        Map<String, String> test = new HashMap<>();
        test.put("1", "test1");
        assertNull(Util.toJSONString(null));
        assertEquals("{\"1\":\"test1\"}", Util.toJSONString(test));
        assertNotNull(Util.toJSONString(test));
    }

}