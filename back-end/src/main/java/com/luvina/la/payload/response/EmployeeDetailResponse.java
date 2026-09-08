package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeDetailResponse.java, 08/09/2026 Pham Van Minh
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response trả về cho API Get Employee (GET /employee/{employeeId}).
 * Khớp chuẩn tài liệu đặc tả API mục 2.3 và 4.2.
 *
 * @author Pham Van Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetailResponse {

    /**
     * Mã kết quả phản hồi (200).
     */
    private int code;

    /**
     * ID của nhân viên.
     */
    private Long employeeId;

    /**
     * Họ và tên nhân viên.
     */
    private String employeeName;

    /**
     * Ngày sinh nhân viên (định dạng yyyy/MM/dd).
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate employeeBirthDate;

    /**
     * ID phòng ban của nhân viên.
     */
    private Long departmentId;

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
     * Tên Katakana của nhân viên.
     */
    private String employeeNameKana;

    /**
     * Tên đăng nhập của nhân viên.
     */
    private String employeeLoginId;

    /**
     * Danh sách chứng chỉ tiếng Nhật của nhân viên (nếu có).
     */
    @Builder.Default
    private List<EmployeeCertificationResponse> certifications = new ArrayList<>();
}
