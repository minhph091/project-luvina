package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeResponse.java, 21/08/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Chứa thông tin một nhân viên được trả về trong response của API List Employees.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    /**
     * ID của nhân viên.
     */
    private Long employeeId;

    /**
     * Tên của nhân viên.
     */
    private String employeeName;

    /**
     * Ngày sinh của nhân viên.
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate employeeBirthDate;

    /**
     * Tên phòng ban của nhân viên.
     */
    private String departmentName;

    /**
     * Địa chỉ email của nhân viên.
     */
    private String employeeEmail;

    /**
     * Số điện thoại của nhân viên.
     */
    private String employeeTelephone;

    /**
     * Tên chứng chỉ tiếng Nhật (nếu có).
     */
    private String certificationName;

    /**
     * Ngày hết hạn chứng chỉ tiếng Nhật (nếu có).
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    /**
     * Điểm chứng chỉ tiếng Nhật (nếu có).
     */
    private BigDecimal score;
}
