package com.luvina.la.entity;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeEntity.java, 21/08/2026 Phạm Văn Minh
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity đại diện cho bảng employees.
 * Dùng để lưu trữ thông tin nhân viên của hệ thống.
 *
 * @author Phạm Văn Minh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của nhân viên.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * ID của phòng ban mà nhân viên thuộc về.
     */
    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    /**
     * Tên của nhân viên.
     */
    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    /**
     * Tên katakana của nhân viên.
     */
    @Column(name = "employee_name_kana")
    private String employeeNameKana;

    /**
     * Ngày sinh của nhân viên.
     */
    @Column(name = "employee_birth_date")
    private LocalDate employeeBirthDate;

    /**
     * Địa chỉ email của nhân viên.
     */
    @Column(name = "employee_email", nullable = false)
    private String employeeEmail;

    /**
     * Số điện thoại của nhân viên.
     */
    @Column(name = "employee_telephone")
    private String employeeTelephone;

    /**
     * Tên tài khoản đăng nhập của nhân viên.
     */
    @Column(name = "employee_login_id", nullable = false, unique = true)
    private String employeeLoginId;

    /**
     * Mật khẩu đăng nhập của nhân viên (đã mã hóa BCrypt).
     */
    @Column(name = "employee_login_password")
    private String employeeLoginPassword;

    /**
     * Quyền hạn/vai trò của nhân viên (USER, ADMIN,...).
     */
    @Column(name = "employee_role", nullable = false)
    private String employeeRole = "USER";
}

