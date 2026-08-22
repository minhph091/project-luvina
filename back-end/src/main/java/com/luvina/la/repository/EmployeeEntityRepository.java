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
}
