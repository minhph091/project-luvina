package com.luvina.la.repository;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeCertificationRepository.java, 07/09/2026 Phạm Văn Minh
 */

import com.luvina.la.entity.EmployeeCertificationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository xử lý thao tác với bảng employees_certifications qua JPA.
 *
 * @author Phạm Văn Minh
 */
@Repository
public interface EmployeeCertificationRepository extends JpaRepository<EmployeeCertificationEntity, Long> {

    /**
     * Tìm danh sách chứng chỉ theo ID của nhân viên.
     *
     * @param employeeId ID của nhân viên.
     * @return Danh sách các chứng chỉ của nhân viên.
     */
    List<EmployeeCertificationEntity> findByEmployeeId(Long employeeId);
}
