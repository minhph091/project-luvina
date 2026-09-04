package com.luvina.la.controller;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.mapper.DepartmentMapper;
import com.luvina.la.payload.response.DepartmentResponse;
import com.luvina.la.payload.response.ListDepartmentsResponse;
import com.luvina.la.service.DepartmentService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentController.java, 18/08/2026 Phạm Văn Minh
 */

/**
 * Controller xử lý các request liên quan đến phòng ban.
 * Nhận DTO từ Service và chuyển đổi sang Response Payload trả về client.
 *
 * @author Phạm Văn Minh
 */
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    /**
     * Khởi tạo DepartmentController.
     *
     * @param departmentService Service xử lý nghiệp vụ phòng ban.
     * @param departmentMapper  Mapper chuyển đổi giữa DTO và Response.
     */
    public DepartmentController(
            DepartmentService departmentService,
            DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Lấy danh sách tất cả phòng ban theo tài liệu thiết kế API (GET /department).
     *
     * @return Response chứa mã kết quả và danh sách phòng ban.
     */
    @GetMapping("/department")
    public ListDepartmentsResponse getDepartments() {
        List<DepartmentDTO> departmentDTOs = departmentService.getDepartments();
        List<DepartmentResponse> departments = departmentMapper.toResponseList(departmentDTOs);

        return ListDepartmentsResponse.builder()
                .code(200)
                .departments(departments)
                .build();
    }
}