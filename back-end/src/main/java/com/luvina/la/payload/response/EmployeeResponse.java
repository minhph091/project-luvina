package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeResponse.java, 21/08/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Chứa thông tin một nhân viên được trả về trong response của API List Employees.
 *
 * @author Phạm Văn Minh
 */
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

    /**
     * Khởi tạo EmployeeResponse mặc định.
     */
    public EmployeeResponse() {
    }

    /**
     * Khởi tạo EmployeeResponse với đầy đủ thông tin.
     *
     * @param employeeId       ID nhân viên.
     * @param employeeName     Tên nhân viên.
     * @param employeeBirthDate Ngày sinh.
     * @param departmentName   Tên phòng ban.
     * @param employeeEmail    Email.
     * @param employeeTelephone Số điện thoại.
     * @param certificationName Tên chứng chỉ.
     * @param endDate          Ngày hết hạn chứng chỉ.
     * @param score            Điểm chứng chỉ.
     */
    public EmployeeResponse(
            Long employeeId,
            String employeeName,
            LocalDate employeeBirthDate,
            String departmentName,
            String employeeEmail,
            String employeeTelephone,
            String certificationName,
            LocalDate endDate,
            BigDecimal score) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeBirthDate = employeeBirthDate;
        this.departmentName = departmentName;
        this.employeeEmail = employeeEmail;
        this.employeeTelephone = employeeTelephone;
        this.certificationName = certificationName;
        this.endDate = endDate;
        this.score = score;
    }

    /**
     * Lấy ID nhân viên.
     *
     * @return ID của nhân viên.
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * Thiết lập ID nhân viên.
     *
     * @param employeeId ID cần thiết lập.
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Lấy tên nhân viên.
     *
     * @return Tên của nhân viên.
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Thiết lập tên nhân viên.
     *
     * @param employeeName Tên cần thiết lập.
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * Lấy ngày sinh nhân viên.
     *
     * @return Ngày sinh của nhân viên.
     */
    public LocalDate getEmployeeBirthDate() {
        return employeeBirthDate;
    }

    /**
     * Thiết lập ngày sinh nhân viên.
     *
     * @param employeeBirthDate Ngày sinh cần thiết lập.
     */
    public void setEmployeeBirthDate(LocalDate employeeBirthDate) {
        this.employeeBirthDate = employeeBirthDate;
    }

    /**
     * Lấy tên phòng ban.
     *
     * @return Tên phòng ban của nhân viên.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Thiết lập tên phòng ban.
     *
     * @param departmentName Tên phòng ban cần thiết lập.
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Lấy địa chỉ email.
     *
     * @return Email của nhân viên.
     */
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    /**
     * Thiết lập địa chỉ email.
     *
     * @param employeeEmail Email cần thiết lập.
     */
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    /**
     * Lấy số điện thoại.
     *
     * @return Số điện thoại của nhân viên.
     */
    public String getEmployeeTelephone() {
        return employeeTelephone;
    }

    /**
     * Thiết lập số điện thoại.
     *
     * @param employeeTelephone Số điện thoại cần thiết lập.
     */
    public void setEmployeeTelephone(String employeeTelephone) {
        this.employeeTelephone = employeeTelephone;
    }

    /**
     * Lấy tên chứng chỉ tiếng Nhật.
     *
     * @return Tên chứng chỉ (null nếu không có).
     */
    public String getCertificationName() {
        return certificationName;
    }

    /**
     * Thiết lập tên chứng chỉ tiếng Nhật.
     *
     * @param certificationName Tên chứng chỉ cần thiết lập.
     */
    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    /**
     * Lấy ngày hết hạn chứng chỉ.
     *
     * @return Ngày hết hạn (null nếu không có).
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Thiết lập ngày hết hạn chứng chỉ.
     *
     * @param endDate Ngày hết hạn cần thiết lập.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Lấy điểm chứng chỉ.
     *
     * @return Điểm (null nếu không có).
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * Thiết lập điểm chứng chỉ.
     *
     * @param score Điểm cần thiết lập.
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
