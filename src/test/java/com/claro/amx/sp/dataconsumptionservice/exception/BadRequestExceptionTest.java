package com.claro.amx.sp.dataconsumptionservice.exception;


import com.claro.amx.sp.dataconsumptionservice.exception.impl.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.exception.ExceptionType.BUSINESS_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BadRequestExceptionTest {
	private BadRequestException badRequestException;

	@BeforeEach
	void setUp() {
		badRequestException = new BadRequestException("1122", "Business Exception Test");
	}

	@Test
	void getDescriptionTest_Succes() {
		String message = "Bad Request Exception Test";
		String code = "0000";
		try {
			throw new BadRequestException(code, message, null);
		} catch (BadRequestException e) {
			assertEquals(message, e.getMessage());
			assertEquals(code, e.getCode());
		}
	}

	@Test
	void getDescriptionTestSucces() {
		String message = "Business Exception Test";
		String code = "0000";
		try {
			throw new BadRequestException(code, message);
		} catch (BadRequestException e) {
			assertEquals(message, e.getMessage());
			assertEquals(code, e.getCode());
		}
	}

	@Test
	void getExceptionType() {
		final ExceptionType exceptionType = badRequestException.getExceptionType();
		assertEquals(BUSINESS_EXCEPTION, exceptionType);
	}

	@Test
	void getExtraInfo() {
		final List<Object> list = badRequestException.getExtraInfo();
		assertNotNull(list);
	}

}