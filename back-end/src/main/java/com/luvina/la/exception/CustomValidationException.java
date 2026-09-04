package com.luvina.la.exception;

/**
 * Copyright(C) 2026 Luvina
 * CustomValidationException.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.payload.response.MessageResponse;
import lombok.Getter;

/**
 * Ngoại lệ tùy chỉnh cho các lỗi xác thực tham số nghiệp vụ.
 *
 * @author Phạm Văn Minh
 */
@Getter
public class CustomValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final MessageResponse messageResponse;

    public CustomValidationException(MessageResponse messageResponse) {
        super(messageResponse != null ? messageResponse.getCode() : "Validation Error");
        this.messageResponse = messageResponse;
    }
}
