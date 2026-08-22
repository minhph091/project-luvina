package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeController.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.payload.response.GetEmployeesResponse;
import com.luvina.la.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xử lý các request liên quan đến nhân viên.
 *
 * @author Phạm Văn Minh
 */
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Khởi tạo EmployeeController.
     *
     * @param employeeService Service xử lý nghiệp vụ nhân viên.
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Lấy danh sách nhân viên có phân trang, tìm kiếm và sắp xếp theo tài liệu thiết kế API (GET /employee).
     *
     * @param employeeName           Tên nhân viên cần lọc (camelCase).
     * @param employeeNameSnake      Tên nhân viên cần lọc (snake_case theo thiết kế).
     * @param departmentId           ID phòng ban cần lọc (camelCase).
     * @param departmentIdSnake      ID phòng ban cần lọc (snake_case theo thiết kế).
     * @param limit                  Số bản ghi trên mỗi trang.
     * @param offset                 Vị trí bắt đầu lấy bản ghi.
     * @param pageNo                 Số trang hiện tại (tùy chọn hỗ trợ frontend).
     * @param pageSize               Số bản ghi trên mỗi trang (tùy chọn hỗ trợ frontend).
     * @param employeeNameOrder      Chiều sắp xếp theo tên (camelCase).
     * @param ordEmployeeName        Chiều sắp xếp theo tên (snake_case theo thiết kế).
     * @param certificationNameOrder Chiều sắp xếp theo tên chứng chỉ (camelCase).
     * @param ordCertificationName   Chiều sắp xếp theo tên chứng chỉ (snake_case theo thiết kế).
     * @param endDateOrder           Chiều sắp xếp theo ngày hết hạn (camelCase).
     * @param ordEndDate             Chiều sắp xếp theo ngày hết hạn (snake_case theo thiết kế).
     * @return Response chứa mã kết quả, tổng số bản ghi và danh sách nhân viên.
     */
    @GetMapping({"/employee", "/employees"})
    public GetEmployeesResponse getEmployees(
            @RequestParam(required = false, name = "employeeName") String employeeName,
            @RequestParam(required = false, name = "employee_name") String employeeNameSnake,
            @RequestParam(required = false, name = "departmentId") String departmentId,
            @RequestParam(required = false, name = "department_id") String departmentIdSnake,
            @RequestParam(required = false, name = "limit") String limit,
            @RequestParam(required = false, name = "offset") String offset,
            @RequestParam(required = false, name = "pageNo") Integer pageNo,
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            @RequestParam(required = false, name = "employeeNameOrder") String employeeNameOrder,
            @RequestParam(required = false, name = "ord_employee_name") String ordEmployeeName,
            @RequestParam(required = false, name = "certificationNameOrder") String certificationNameOrder,
            @RequestParam(required = false, name = "ord_certification_name") String ordCertificationName,
            @RequestParam(required = false, name = "endDateOrder") String endDateOrder,
            @RequestParam(required = false, name = "ord_end_date") String ordEndDate,
            @RequestParam(required = false, name = "sortBy") String sortBy,
            @RequestParam(required = false, name = "sort_by") String sortBySnake) {

        // Ưu tiên tham số snake_case theo thiết kế API, fallback sang camelCase
        String finalName = employeeNameSnake != null ? employeeNameSnake : employeeName;
        String finalDeptId = departmentIdSnake != null ? departmentIdSnake : departmentId;
        String finalOrdName = ordEmployeeName != null ? ordEmployeeName : employeeNameOrder;
        String finalOrdCert = ordCertificationName != null ? ordCertificationName : certificationNameOrder;
        String finalOrdEnd = ordEndDate != null ? ordEndDate : endDateOrder;
        String finalSortBy = sortBySnake != null ? sortBySnake : sortBy;

        // Xử lý limit / pageSize
        String finalLimit = limit;
        if ((finalLimit == null || finalLimit.trim().isEmpty()) && pageSize != null && pageSize > 0) {
            finalLimit = String.valueOf(pageSize);
        }

        // Xử lý offset / pageNo
        String finalOffset = offset;
        if ((finalOffset == null || finalOffset.trim().isEmpty()) && pageNo != null && pageNo > 0) {
            int calcLimit = 5;
            if (finalLimit != null && !finalLimit.trim().isEmpty()) {
                try {
                    calcLimit = Integer.parseInt(finalLimit.trim());
                } catch (NumberFormatException ignored) {
                    calcLimit = 5;
                }
            }
            finalOffset = String.valueOf((pageNo - 1) * calcLimit);
        }

        return employeeService.getEmployees(
                finalName,
                finalDeptId,
                finalOrdName,
                finalOrdCert,
                finalOrdEnd,
                finalOffset,
                finalLimit,
                finalSortBy);
    }
}
