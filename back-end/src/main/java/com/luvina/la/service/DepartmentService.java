package com.luvina.la.service;

import com.luvina.la.dto.DepartmentDTO;
import java.util.List;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentService.java, 18/08/2026 Phạm Văn Minh
 */

/**
 * Interface xử lý nghiệp vụ liên quan đến phòng ban.
 *
 * @author Phạm Văn Minh
 */
public interface DepartmentService {

    /**
     * Lấy danh sách tất cả phòng ban dạng DTO.
     *
     * @return Danh sách DepartmentDTO.
     */
    List<DepartmentDTO> getDepartments();
}