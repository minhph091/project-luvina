package com.luvina.la.payload.request;

/**
 * Copyright(C) 2026 Luvina
 * UpdateEmployeeRequest.java, 10/09/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO đại diện cho dữ liệu gửi lên khi cập nhật thông tin nhân viên (PUT /employee).
 * Khớp theo tài liệu thiết kế API Update Employee.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của nhân viên cần cập nhật.
     */
    private Long employeeId;

    /**
     * Tên đăng nhập (tài khoản).
     */
    private String employeeLoginId;

    /**
     * Mật khẩu đăng nhập (chỉ cập nhật khi khác rỗng).
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
     * Thông tin chứng chỉ tiếng Nhật (nếu có).
     * Hỗ trợ linh hoạt cả dạng Object đơn lẻ theo mẫu JSON spec hoặc Array.
     */
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<CertificationItemRequest> certifications;
}
