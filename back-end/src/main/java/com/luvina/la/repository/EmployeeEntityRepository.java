package com.luvina.la.repository;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeEntityRepository.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.entity.EmployeeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository xử lý thao tác CRUD với bảng employees qua JPA.
 *
 * @author Phạm Văn Minh
 */
@Repository
public interface EmployeeEntityRepository extends JpaRepository<EmployeeEntity, Long> {

    /**
     * Tìm nhân viên theo tên tài khoản đăng nhập.
     *
     * @param employeeLoginId Tên tài khoản đăng nhập.
     * @return Optional chứa EmployeeEntity nếu tìm thấy.
     */
    Optional<EmployeeEntity> findByEmployeeLoginId(String employeeLoginId);

    /**
     * Kiểm tra tên tài khoản đăng nhập đã tồn tại hay chưa.
     *
     * @param employeeLoginId Tên tài khoản cần kiểm tra.
     * @return true nếu đã tồn tại, ngược lại false.
     */
    boolean existsByEmployeeLoginId(String employeeLoginId);

    /**
     * Lấy chi tiết nhân viên, phòng ban và danh sách chứng chỉ bằng 1 câu JOIN 4 bảng.
     *
     * @param employeeId ID của nhân viên cần lấy chi tiết.
     * @return Danh sách mảng Object chứa thông tin nhân viên, phòng ban và chứng chỉ.
     */
    @org.springframework.data.jpa.repository.Query(
            "SELECT e.employeeId, e.departmentId, d.departmentName, e.employeeName, e.employeeNameKana, "
                    + "e.employeeBirthDate, e.employeeEmail, e.employeeTelephone, e.employeeLoginId, e.employeeRole, "
                    + "ec.certificationId, c.certificationName, ec.startDate, ec.endDate, ec.score "
                    + "FROM EmployeeEntity e "
                    + "LEFT JOIN DepartmentEntity d ON e.departmentId = d.departmentId "
                    + "LEFT JOIN EmployeeCertificationEntity ec ON e.employeeId = ec.employeeId "
                    + "LEFT JOIN CertificationEntity c ON ec.certificationId = c.certificationId "
                    + "WHERE e.employeeId = :employeeId "
                    + "ORDER BY c.certificationLevel ASC")
    java.util.List<Object[]> findEmployeeDetailWithCertifications(
            @org.springframework.data.repository.query.Param("employeeId") Long employeeId);
}
