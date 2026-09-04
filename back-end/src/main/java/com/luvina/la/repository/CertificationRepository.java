package com.luvina.la.repository;

/**
 * Copyright(C) 2026 Luvina
 * CertificationRepository.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.entity.CertificationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository xử lý truy vấn dữ liệu chứng chỉ tiếng Nhật.
 *
 * @author Phạm Văn Minh
 */
public interface CertificationRepository extends JpaRepository<CertificationEntity, Long> {

    /**
     * Lấy danh sách chứng chỉ tiếng Nhật sắp xếp tăng dần theo cấp độ.
     *
     * @return Danh sách CertificationEntity đã sắp xếp theo certificationLevel tăng dần.
     */
    List<CertificationEntity> findAllByOrderByCertificationLevelAsc();
}
