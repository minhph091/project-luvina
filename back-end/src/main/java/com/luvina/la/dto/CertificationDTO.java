package com.luvina.la.dto;

/**
 * Copyright(C) 2026 Luvina
 * CertificationDTO.java, 04/09/2026 Phạm Văn Minh
 */

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO chứa thông tin chứng chỉ tiếng Nhật.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của chứng chỉ tiếng Nhật.
     */
    private Long certificationId;

    /**
     * Tên của chứng chỉ tiếng Nhật.
     */
    private String certificationName;

    /**
     * Cấp độ của chứng chỉ tiếng Nhật.
     */
    private Integer certificationLevel;
}
