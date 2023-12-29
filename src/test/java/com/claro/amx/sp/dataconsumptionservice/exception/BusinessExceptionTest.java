package com.claro.amx.sp.dataconsumptionservice.exception;


import com.claro.amx.sp.dataconsumptionservice.exception.impl.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.exception.ExceptionType.BUSINESS_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BusinessExceptionTest {
	private BusinessException businessException;

	@BeforeEach
	void setUp() {
		businessException = new BusinessException("1122", "Business Exception Test");
	}

	@Test
	void getDescriptionTest_Succes() {
		String message = "Business Exception Test";
		String code = "0000";
		try {
			throw new BusinessException(code, message, null);
		} catch (BusinessException e) {
			assertEquals(message, e.getMessage());
			assertEquals(code, e.getCode());
		}
	}

	@Test
	void getDescriptionTestSucces() {
		String message = "Business Exception Test";
		String code = "0000";
		try {
			throw new BusinessException(code, message);
		} catch (BusinessException e) {
			assertEquals(message, e.getMessage());
			assertEquals(code, e.getCode());
		}
	}

	@Test
	void getExceptionType() {
		final ExceptionType exceptionType = businessException.getExceptionType();
		assertEquals(BUSINESS_EXCEPTION, exceptionType);
	}

	@Test
	void getExtraInfo() {
		final List<Object> list = businessException.getExtraInfo();
		assertNotNull(list);
	}

}