package com.luvina.la.service;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeService.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.EmployeeListDTO;

/**
 * Interface xử lý nghiệp vụ liên quan đến nhân viên.
 *
 * @author Phạm Văn Minh
 */
public interface EmployeeService {

    /**
     * Lấy danh sách nhân viên theo các điều kiện lọc, sắp xếp và phân trang theo tài liệu thiết kế API.
     *
     * @param employeeName         Tên nhân viên để lọc (tùy chọn).
     * @param departmentId         ID phòng ban để lọc (tùy chọn).
     * @param ordEmployeeName      Chiều sắp xếp theo tên nhân viên (ASC/DESC).
     * @param ordCertificationName Chiều sắp xếp theo tên chứng chỉ (ASC/DESC).
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn (ASC/DESC).
     * @param offsetStr            Vị trí bắt đầu lấy bản ghi (mặc định 0).
     * @param limitStr             Số bản ghi tối đa trên một trang (mặc định 5).
     * @return EmployeeListDTO chứa tổng số bản ghi và danh sách EmployeeDTO.
     */
    EmployeeListDTO getEmployees(
            String employeeName,
            String departmentId,
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String offsetStr,
            String limitStr);

    /**
     * Lấy danh sách nhân viên theo các điều kiện lọc, sắp xếp, phân trang và cột ưu tiên.
     *
     * @param employeeName         Tên nhân viên để lọc (tùy chọn).
     * @param departmentId         ID phòng ban để lọc (tùy chọn).
     * @param ordEmployeeName      Chiều sắp xếp theo tên nhân viên (ASC/DESC).
     * @param ordCertificationName Chiều sắp xếp theo tên chứng chỉ (ASC/DESC).
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn (ASC/DESC).
     * @param offsetStr            Vị trí bắt đầu lấy bản ghi (mặc định 0).
     * @param limitStr             Số bản ghi tối đa trên một trang (mặc định 5).
     * @param sortBy               Cột đang được người dùng ưu tiên sắp xếp hàng đầu.
     * @return EmployeeListDTO chứa tổng số bản ghi và danh sách EmployeeDTO.
     */
    EmployeeListDTO getEmployees(
            String employeeName,
            String departmentId,
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String offsetStr,
            String limitStr,
            String sortBy);
}
