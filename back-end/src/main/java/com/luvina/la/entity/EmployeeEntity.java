package com.luvina.la.entity;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeEntity.java, 21/08/2026 Phạm Văn Minh
 */

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity đại diện cho bảng employees.
 * Dùng để lưu trữ thông tin nhân viên của hệ thống.
 *
 * @author Phạm Văn Minh
 */
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

    public EmployeeEntity() {
    }

    /**
     * Lấy ID của nhân viên.
     *
     * @return ID của nhân viên.
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * Thiết lập ID cho nhân viên.
     *
     * @param employeeId ID của nhân viên cần thiết lập.
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Lấy ID phòng ban.
     *
     * @return ID phòng ban của nhân viên.
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Thiết lập ID phòng ban.
     *
     * @param departmentId ID phòng ban cần thiết lập.
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Lấy tên của nhân viên.
     *
     * @return Tên của nhân viên.
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Thiết lập tên cho nhân viên.
     *
     * @param employeeName Tên của nhân viên cần thiết lập.
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * Lấy tên katakana của nhân viên.
     *
     * @return Tên katakana của nhân viên.
     */
    public String getEmployeeNameKana() {
        return employeeNameKana;
    }

    /**
     * Thiết lập tên katakana cho nhân viên.
     *
     * @param employeeNameKana Tên katakana cần thiết lập.
     */
    public void setEmployeeNameKana(String employeeNameKana) {
        this.employeeNameKana = employeeNameKana;
    }

    /**
     * Lấy ngày sinh của nhân viên.
     *
     * @return Ngày sinh của nhân viên.
     */
    public LocalDate getEmployeeBirthDate() {
        return employeeBirthDate;
    }

    /**
     * Thiết lập ngày sinh cho nhân viên.
     *
     * @param employeeBirthDate Ngày sinh cần thiết lập.
     */
    public void setEmployeeBirthDate(LocalDate employeeBirthDate) {
        this.employeeBirthDate = employeeBirthDate;
    }

    /**
     * Lấy địa chỉ email của nhân viên.
     *
     * @return Địa chỉ email của nhân viên.
     */
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    /**
     * Thiết lập địa chỉ email cho nhân viên.
     *
     * @param employeeEmail Địa chỉ email cần thiết lập.
     */
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    /**
     * Lấy số điện thoại của nhân viên.
     *
     * @return Số điện thoại của nhân viên.
     */
    public String getEmployeeTelephone() {
        return employeeTelephone;
    }

    /**
     * Thiết lập số điện thoại cho nhân viên.
     *
     * @param employeeTelephone Số điện thoại cần thiết lập.
     */
    public void setEmployeeTelephone(String employeeTelephone) {
        this.employeeTelephone = employeeTelephone;
    }

    /**
     * Lấy tên tài khoản đăng nhập của nhân viên.
     *
     * @return Tên tài khoản đăng nhập của nhân viên.
     */
    public String getEmployeeLoginId() {
        return employeeLoginId;
    }

    /**
     * Thiết lập tên tài khoản đăng nhập cho nhân viên.
     *
     * @param employeeLoginId Tên tài khoản cần thiết lập.
     */
    public void setEmployeeLoginId(String employeeLoginId) {
        this.employeeLoginId = employeeLoginId;
    }

    /**
     * Lấy mật khẩu đăng nhập của nhân viên.
     *
     * @return Mật khẩu đăng nhập của nhân viên.
     */
    public String getEmployeeLoginPassword() {
        return employeeLoginPassword;
    }

    /**
     * Thiết lập mật khẩu đăng nhập cho nhân viên.
     *
     * @param employeeLoginPassword Mật khẩu cần thiết lập.
     */
    public void setEmployeeLoginPassword(String employeeLoginPassword) {
        this.employeeLoginPassword = employeeLoginPassword;
    }

    /**
     * Lấy vai trò của nhân viên.
     *
     * @return Vai trò của nhân viên.
     */
    public String getEmployeeRole() {
        return employeeRole;
    }

    /**
     * Thiết lập vai trò cho nhân viên.
     *
     * @param employeeRole Vai trò cần thiết lập.
     */
    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }
}
