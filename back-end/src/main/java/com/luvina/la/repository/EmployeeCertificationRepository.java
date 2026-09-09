package com.luvina.la.repository;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeCertificationRepository.java, 07/09/2026 Phạm Văn Minh
 */

import com.luvina.la.entity.EmployeeCertificationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * Tìm danh sách chi tiết chứng chỉ của nhân viên sắp xếp theo level chứng chỉ tăng dần.
     *
     * @param employeeId ID của nhân viên.
     * @return Danh sách mảng Object chứa certificationId, certificationName, startDate, endDate, score.
     */
    @Query(
            "SELECT ec.certificationId, c.certificationName, ec.startDate, ec.endDate, ec.score "
                    + "FROM EmployeeCertificationEntity ec, CertificationEntity c "
                    + "WHERE ec.certificationId = c.certificationId AND ec.employeeId = :employeeId "
                    + "ORDER BY c.certificationLevel ASC")
    List<Object[]> findCertificationsWithDetailsByEmployeeId(
            @Param("employeeId") Long employeeId);

    /**
     * Xóa toàn bộ chứng chỉ của nhân viên theo employeeId.
     *
     * @param employeeId ID của nhân viên cần xóa chứng chỉ.
     */
    void deleteByEmployeeId(Long employeeId);
}
