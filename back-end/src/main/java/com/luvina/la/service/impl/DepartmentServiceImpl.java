package com.luvina.la.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luvina.la.entity.DepartmentEntity;
import com.luvina.la.payload.response.DepartmentResponse;
import com.luvina.la.payload.response.GetDepartmentsResponse;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.DepartmentService;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentServiceImpl.java, 18/08/2026 Phạm Văn Minh
 */

/**
 * Implementation xử lý nghiệp vụ liên quan đến phòng ban.
 *
 * @author Phạm Văn Minh
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    /**
     * Khởi tạo DepartmentServiceImpl.
     *
     * @param departmentRepository Repository dùng để truy vấn dữ liệu phòng ban.
     */
    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Lấy danh sách tất cả phòng ban.
     *
     * @return Response chứa mã kết quả và danh sách phòng ban.
     */
    @Override
    public GetDepartmentsResponse getDepartments() {

        List<DepartmentEntity> departmentEntities =
                departmentRepository.findAll();

        List<DepartmentResponse> departments =
                departmentEntities.stream()
                        .map(this::convertToResponse)
                        .toList();

        GetDepartmentsResponse response =
                new GetDepartmentsResponse();

        response.setCode(200);
        response.setDepartments(departments);

        return response;
    }

    /**
     * Chuyển đổi dữ liệu từ Entity sang Response.
     *
     * @param departmentEntity Entity chứa thông tin phòng ban cần chuyển đổi.
     * @return Đối tượng Response chứa thông tin phòng ban.
     */
    private DepartmentResponse convertToResponse(
            DepartmentEntity departmentEntity) {

        return new DepartmentResponse(
                departmentEntity.getDepartmentId(),
                departmentEntity.getDepartmentName());
    }
}