package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * CertificationResponse.java, 04/09/2026 Phạm Văn Minh
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Chứa thông tin chứng chỉ tiếng Nhật được trả về trong response của API.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationResponse {

    /**
     * ID của chứng chỉ tiếng Nhật.
     */
    private Long certificationId;

    /**
     * Tên của chứng chỉ tiếng Nhật.
     */
    private String certificationName;
}
