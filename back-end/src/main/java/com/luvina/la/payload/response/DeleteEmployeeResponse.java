package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * DeleteEmployeeResponse.java, 08/09/2026 Pham Van Minh
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response trả về cho API Delete Employee (DELETE /employee/{employeeId}).
 *
 * @author Pham Van Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteEmployeeResponse {

    /**
     * Mã kết quả phản hồi (200).
     */
    private int code;

    /**
     * ID của nhân viên vừa bị xóa.
     */
    private Long employeeId;

    /**
     * Thông điệp trả về (MSG003).
     */
    private MessageResponse message;
}
