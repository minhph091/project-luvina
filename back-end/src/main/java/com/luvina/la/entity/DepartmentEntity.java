package com.luvina.la.entity;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentEntity.java, 18/08/2026 Phạm Văn Minh
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity đại diện cho bảng departments.
 * Dùng để lưu trữ thông tin phòng ban của hệ thống.
 *
 * @author Phạm Văn Minh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class DepartmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
}