package com.luvina.la.exception;

/**
 * Copyright(C) 2026 Luvina
 * GlobalExceptionHandlerTest.java, 07/09/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.payload.response.ErrorResponse;
import com.luvina.la.payload.response.MessageResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test cho GlobalExceptionHandler.
 *
 * @author Phạm Văn Minh
 */
public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Test handleCustomValidationException trả về code 500 và đúng MessageResponse")
    void testHandleCustomValidationException() {
        MessageResponse msgResponse = new MessageResponse(Constants.ERROR_CODE_ER001, List.of(Constants.PARAM_ACCOUNT_NAME));
        CustomValidationException ex = new CustomValidationException(msgResponse);

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleCustomValidationException(ex);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ErrorResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals(500, body.getCode());
        assertNotNull(body.getMessage());
        assertEquals(Constants.ERROR_CODE_ER001, body.getMessage().getCode());
        assertEquals(List.of(Constants.PARAM_ACCOUNT_NAME), body.getMessage().getParams());
    }

    @Test
    @DisplayName("Test handleGeneralException trả về code 500 và mã lỗi ER015")
    void testHandleGeneralException() {
        Exception ex = new RuntimeException("Unexpected database failure");

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleGeneralException(ex);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ErrorResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals(500, body.getCode());
        assertNotNull(body.getMessage());
        assertEquals(Constants.ERROR_CODE_ER015, body.getMessage().getCode());
        assertTrue(body.getMessage().getParams().isEmpty());
    }
}
