package com.luvina.la.dto;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeDTO.java, 21/08/2026 Phạm Văn Minh
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object đại diện cho dữ liệu nhân viên trong tầng Service & nghiệp vụ.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của nhân viên.
     */
    private Long employeeId;

    /**
     * ID của phòng ban.
     */
    private Long departmentId;

    /**
     * Tên phòng ban của nhân viên.
     */
    private String departmentName;

    /**
     * Tên của nhân viên.
     */
    private String employeeName;

    /**
     * Tên katakana của nhân viên.
     */
    private String employeeNameKana;

    /**
     * Ngày sinh của nhân viên.
     */
    private LocalDate employeeBirthDate;

    /**
     * Địa chỉ email của nhân viên.
     */
    private String employeeEmail;

    /**
     * Số điện thoại của nhân viên.
     */
    private String employeeTelephone;

    /**
     * Tên tài khoản đăng nhập của nhân viên.
     */
    private String employeeLoginId;

    /**
     * Quyền hạn/vai trò của nhân viên (USER, ADMIN,...).
     */
    private String employeeRole;

    /**
     * Tên chứng chỉ tiếng Nhật (nếu có).
     */
    private String certificationName;

    /**
     * Ngày hết hạn chứng chỉ tiếng Nhật (nếu có).
     */
    private LocalDate endDate;

    /**
     * Điểm chứng chỉ tiếng Nhật (nếu có).
     */
    private BigDecimal score;
}
