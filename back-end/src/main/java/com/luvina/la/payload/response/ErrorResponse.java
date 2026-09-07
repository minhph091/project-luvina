package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * ErrorResponse.java, 07/09/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Phản hồi chuẩn khi có lỗi phát sinh trong hệ thống theo tài liệu thiết kế API.
 * Khớp định dạng: code (mã kết quả 500) và message (chứa mã lỗi ERxxx và tham số nếu có).
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Mã kết quả của API (mặc định 500 khi có lỗi).
     */
    private Integer code;

    /**
     * Thông tin chi tiết lỗi gồm mã lỗi và danh sách tham số.
     */
    private MessageResponse message;
}
