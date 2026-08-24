package com.luvina.la.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luvina.la.payload.response.GetDepartmentsResponse;
import com.luvina.la.service.DepartmentService;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentController.java, 18/08/2026 Phạm Văn Minh
 */

/**
 * Controller xử lý các request liên quan đến phòng ban.
 *
 * @author Phạm Văn Minh
 */
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * Khởi tạo DepartmentController.
     *
     * @param departmentService Service xử lý nghiệp vụ phòng ban.
     */
    public DepartmentController(
            DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Lấy danh sách tất cả phòng ban theo tài liệu thiết kế API (GET /department).
     *
     * @return Response chứa mã kết quả và danh sách phòng ban.
     */
    @GetMapping("/department")
    public GetDepartmentsResponse getDepartments() {
        return departmentService.getDepartments();
    }
}