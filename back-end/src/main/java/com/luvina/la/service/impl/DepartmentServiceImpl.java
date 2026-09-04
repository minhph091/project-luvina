package com.luvina.la.service.impl;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentServiceImpl.java, 18/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.DepartmentEntity;
import com.luvina.la.mapper.DepartmentMapper;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.DepartmentService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implementation xử lý nghiệp vụ liên quan đến phòng ban.
 * Trả về danh sách DepartmentDTO cho tầng Controller.
 *
 * @author Phạm Văn Minh
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    /**
     * Khởi tạo DepartmentServiceImpl.
     *
     * @param departmentRepository Repository dùng để truy vấn dữ liệu phòng ban.
     * @param departmentMapper     Mapper chuyển đổi giữa Entity và DTO.
     */
    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Lấy danh sách tất cả phòng ban dạng DTO.
     *
     * @return Danh sách DepartmentDTO.
     */
    @Override
    public List<DepartmentDTO> getDepartments() {
        // 1. Lấy danh sách entity từ Repository
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();

        // 2. Chuyển đổi Entity sang DTO cho tầng nghiệp vụ và trả về
        return departmentMapper.toDtoList(departmentEntities);
    }
}