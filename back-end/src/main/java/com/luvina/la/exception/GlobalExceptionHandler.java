package com.luvina.la.exception;

/**
 * Copyright(C) 2026 Luvina
 * GlobalExceptionHandler.java, 07/09/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.payload.response.ErrorResponse;
import com.luvina.la.payload.response.MessageResponse;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Bộ xử lý ngoại lệ tập trung toàn ứng dụng (Centralized Exception Handler).
 * Bắt các ngoại lệ xác thực nghiệp vụ (CustomValidationException) và ngoại lệ chung,
 * chuẩn hóa về định dạng JSON theo đúng quy chuẩn tài liệu thiết kế API.
 *
 * @author Phạm Văn Minh
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Xử lý ngoại lệ xác thực nghiệp vụ CustomValidationException.
     * Trả về mã lỗi 500 cùng nội dung lỗi nghiệp vụ (mã ERxxx và tham số tương ứng).
     *
     * @param ex CustomValidationException ném ra từ Service hoặc Validator.
     * @return ResponseEntity chứa ErrorResponse với mã code 500 và MessageResponse.
     */
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomValidationException(CustomValidationException ex) {
        log.warn("Validation error handled by GlobalExceptionHandler: {}", ex.getMessageResponse());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(Constants.RESPONSE_CODE_ERROR)
                .message(ex.getMessageResponse())
                .build();

        return ResponseEntity.ok(errorResponse);
    }

    /**
     * Xử lý các ngoại lệ hệ thống không mong muốn hoặc chưa được định nghĩa trước.
     * Trả về mã lỗi chung ER015 và mã code 500.
     *
     * @param ex Ngoại lệ Exception chung.
     * @return ResponseEntity chứa ErrorResponse với mã code 500 và mã lỗi ER015.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Unhandled exception caught by GlobalExceptionHandler: ", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(Constants.RESPONSE_CODE_ERROR)
                .message(new MessageResponse(Constants.ERROR_CODE_ER015, new ArrayList<>()))
                .build();

        return ResponseEntity.ok(errorResponse);
    }
}
