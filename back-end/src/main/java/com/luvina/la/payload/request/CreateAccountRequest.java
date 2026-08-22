package com.luvina.la.payload.request;

/**
 * Copyright(C) 2026 Luvina
 * CreateAccountRequest.java, 21/08/2026 Phạm Văn Minh
 */

import java.time.LocalDate;
import lombok.Data;

/**
 * Request payload cho API tạo tài khoản test (Public).
 *
 * @author Phạm Văn Minh
 */
@Data
public class CreateAccountRequest {

    /**
     * Tên tài khoản đăng nhập (bắt buộc).
     */
    private String username;

    /**
     * Mật khẩu đăng nhập (bắt buộc).
     */
    private String password;

    /**
     * Tên nhân viên (mặc định lấy theo username nếu để trống).
     */
    private String employeeName;

    /**
     * Tên katakana của nhân viên.
     */
    private String employeeNameKana;

    /**
     * Ngày sinh của nhân viên (định dạng yyyy-MM-dd).
     */
    private LocalDate employeeBirthDate;

    /**
     * Địa chỉ email (mặc định username + "@luvina.net" nếu để trống).
     */
    private String employeeEmail;

    /**
     * Số điện thoại liên hệ.
     */
    private String employeeTelephone;

    /**
     * ID phòng ban (mặc định là 1 nếu để trống).
     */
    private Long departmentId;

    /**
     * Vai trò (mặc định là "USER" nếu để trống).
     */
    private String employeeRole;
}
