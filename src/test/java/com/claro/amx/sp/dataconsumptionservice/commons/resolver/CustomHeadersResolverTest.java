package com.claro.amx.sp.dataconsumptionservice.commons.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ExtendWith(MockitoExtension.class)
class CustomHeadersResolverTest {

    @Test
    void getHttpHeaders() {
        HttpHeaders httpHeaders = CustomHeadersResolver.getHttpHeaders(Map.of(CONTENT_TYPE, "Content-Type"));
        assertEquals(CONTENT_TYPE, httpHeaders.getFirst(CONTENT_TYPE));
    }

}