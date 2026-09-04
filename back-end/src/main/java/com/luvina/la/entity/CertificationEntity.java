package com.luvina.la.entity;

/**
 * Copyright(C) 2026 Luvina
 * CertificationEntity.java, 04/09/2026 Phạm Văn Minh
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity đại diện cho bảng certifications.
 * Dùng để lưu trữ thông tin chứng chỉ tiếng Nhật của hệ thống.
 *
 * @author Phạm Văn Minh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "certifications")
public class CertificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của chứng chỉ tiếng Nhật.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id")
    private Long certificationId;

    /**
     * Tên của chứng chỉ tiếng Nhật.
     */
    @Column(name = "certification_name", nullable = false)
    private String certificationName;

    /**
     * Cấp độ của chứng chỉ tiếng Nhật.
     */
    @Column(name = "certification_level", nullable = false)
    private Integer certificationLevel;
}
