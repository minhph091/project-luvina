package com.luvina.la.service.impl;

/**
 * Copyright(C) 2026 Luvina
 * CertificationServiceImpl.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.CertificationEntity;
import com.luvina.la.mapper.CertificationMapper;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.CertificationService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implementation xử lý nghiệp vụ liên quan đến chứng chỉ tiếng Nhật.
 * Trả về danh sách CertificationDTO cho tầng Controller.
 *
 * @author Phạm Văn Minh
 */
@Service
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;

    /**
     * Khởi tạo CertificationServiceImpl.
     *
     * @param certificationRepository Repository dùng để truy vấn dữ liệu chứng chỉ.
     * @param certificationMapper     Mapper chuyển đổi giữa Entity và DTO.
     */
    public CertificationServiceImpl(
            CertificationRepository certificationRepository,
            CertificationMapper certificationMapper) {
        this.certificationRepository = certificationRepository;
        this.certificationMapper = certificationMapper;
    }

    /**
     * Lấy danh sách tất cả chứng chỉ tiếng Nhật dạng DTO.
     *
     * @return Danh sách CertificationDTO.
     */
    @Override
    public List<CertificationDTO> getCertifications() {
        // 1. Lấy danh sách entity từ Repository (sắp xếp tăng dần theo cấp độ)
        List<CertificationEntity> certificationEntities = certificationRepository.findAllByOrderByCertificationLevelAsc();

        // 2. Chuyển đổi Entity sang DTO cho tầng nghiệp vụ và trả về
        return certificationMapper.toDtoList(certificationEntities);
    }
}
