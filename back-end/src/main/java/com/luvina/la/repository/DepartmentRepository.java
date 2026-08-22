package com.luvina.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luvina.la.entity.DepartmentEntity;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentRepository.java, 18/08/2026 Hoàng Ngọc Lâm
 */

/**
 * Repository xử lý truy vấn dữ liệu phòng ban.
 *
 * @author Hoàng Ngọc Lâm
 */
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

}