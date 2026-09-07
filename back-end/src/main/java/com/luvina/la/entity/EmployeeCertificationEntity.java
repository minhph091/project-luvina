package com.luvina.la.entity;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeCertificationEntity.java, 07/09/2026 Phạm Văn Minh
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity đại diện cho bảng employees_certifications.
 * Dùng để lưu trữ thông tin chứng chỉ tiếng Nhật của nhân viên.
 *
 * @author Phạm Văn Minh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees_certifications")
public class EmployeeCertificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID liên kết chứng chỉ nhân viên.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_certification_id")
    private Long employeeCertificationId;

    /**
     * ID của nhân viên.
     */
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    /**
     * ID của chứng chỉ tiếng Nhật.
     */
    @Column(name = "certification_id", nullable = false)
    private Long certificationId;

    /**
     * Ngày cấp chứng chỉ tiếng Nhật.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * Ngày hết hạn của chứng chỉ tiếng Nhật.
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * Điểm số đạt được của chứng chỉ tiếng Nhật.
     */
    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;
}
