package com.luvina.la.entity;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentEntity.java, 18/08/2026 Hoàng Ngọc Lâm
 */

import javax.persistence.*;

/**
 * Entity đại diện cho bảng departments.
 * Dùng để lưu trữ thông tin phòng ban của hệ thống.
 *
 * @author Hoàng Ngọc Lâm
 */
@Entity
@Table(name = "departments")
public class DepartmentEntity {

    /**
     * ID của phòng ban.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    /**
     * Tên của phòng ban.
     */
    @Column(name = "department_name")
    private String departmentName;

    /**
     * Lấy ID của phòng ban.
     *
     * @return ID của phòng ban.
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Thiết lập ID cho phòng ban.
     *
     * @param departmentId ID của phòng ban cần thiết lập.
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Lấy tên của phòng ban.
     *
     * @return Tên của phòng ban.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Thiết lập tên cho phòng ban.
     *
     * @param departmentName Tên của phòng ban cần thiết lập.
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}