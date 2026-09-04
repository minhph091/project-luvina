package com.luvina.la.service;

/**
 * Copyright(C) 2026 Luvina
 * CertificationService.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.CertificationDTO;
import java.util.List;

/**
 * Interface xử lý nghiệp vụ liên quan đến chứng chỉ tiếng Nhật.
 *
 * @author Phạm Văn Minh
 */
public interface CertificationService {

    /**
     * Lấy danh sách tất cả chứng chỉ tiếng Nhật dạng DTO.
     *
     * @return Danh sách CertificationDTO.
     */
    List<CertificationDTO> getCertifications();
}
