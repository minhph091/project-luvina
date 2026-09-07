package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * AddEmployeeResponse.java, 07/09/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Chứa kết quả trả về của API thêm mới nhân viên (POST /employee).
 * Khớp chuẩn thiết kế API: mã 200 kèm employeeId và MSG001 khi thành công, hoặc mã 500 kèm message khi có lỗi.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddEmployeeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Mã kết quả của API (200 thành công, 500 lỗi).
     */
    private Integer code;

    /**
     * ID của nhân viên vừa được tạo mới (chỉ có khi thành công).
     */
    private Long employeeId;

    /**
     * Thông báo kết quả (MSG001 khi thành công, hoặc mã lỗi ERxxx khi có lỗi).
     */
    private MessageResponse message;
}
