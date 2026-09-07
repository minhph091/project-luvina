package com.luvina.la.payload.request;

/**
 * Copyright(C) 2026 Luvina
 * AddEmployeeRequest.java, 07/09/2026 Phạm Văn Minh
 */

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO đại diện cho dữ liệu gửi lên khi tạo mới nhân viên (POST /employee).
 * Khớp theo tài liệu thiết kế API Add Employee (ADM004/ADM005).
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEmployeeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Tên đăng nhập (tài khoản).
     */
    private String employeeLoginId;

    /**
     * Mật khẩu đăng nhập.
     */
    private String employeeLoginPassword;

    /**
     * Họ và tên nhân viên.
     */
    private String employeeName;

    /**
     * Tên katakana của nhân viên.
     */
    private String employeeNameKana;

    /**
     * Ngày sinh của nhân viên (định dạng yyyy/MM/dd).
     */
    private String employeeBirthDate;

    /**
     * Địa chỉ email của nhân viên.
     */
    private String employeeEmail;

    /**
     * Số điện thoại của nhân viên.
     */
    private String employeeTelephone;

    /**
     * ID phòng ban mà nhân viên trực thuộc.
     */
    private String departmentId;

    /**
     * Danh sách chứng chỉ tiếng Nhật (nếu có).
     */
    private List<CertificationItemRequest> certifications;
}
