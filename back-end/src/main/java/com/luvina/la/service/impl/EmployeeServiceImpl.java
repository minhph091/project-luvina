package com.luvina.la.service.impl;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeServiceImpl.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeListDTO;
import com.luvina.la.exception.CustomValidationException;
import com.luvina.la.payload.response.MessageResponse;
import com.luvina.la.repository.EmployeeNativeRepository;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.validator.EmployeeValidator;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Lớp triển khai các dịch vụ liên quan đến nhân viên.
 * Trả về EmployeeListDTO cho tầng Controller.
 *
 * @author Phạm Văn Minh
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeNativeRepository employeeNativeRepository;
    private final EmployeeValidator employeeValidator;

    /**
     * Khởi tạo EmployeeServiceImpl.
     *
     * @param employeeNativeRepository Repository native query cho nhân viên.
     * @param employeeValidator        Validator kiểm tra tính hợp lệ của tham số nhân viên.
     */
    public EmployeeServiceImpl(
            EmployeeNativeRepository employeeNativeRepository,
            EmployeeValidator employeeValidator) {
        this.employeeNativeRepository = employeeNativeRepository;
        this.employeeValidator = employeeValidator;
    }

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
    @Override
    public EmployeeListDTO getEmployees(
            String employeeName,
            String departmentId,
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String offsetStr,
            String limitStr) {
        return getEmployees(employeeName, departmentId, ordEmployeeName, ordCertificationName, ordEndDate, offsetStr, limitStr, null);
    }

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
    @Override
    public EmployeeListDTO getEmployees(
            String employeeName,
            String departmentId,
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String offsetStr,
            String limitStr,
            String sortBy) {

        // 1. Validate parameter
        MessageResponse validationError = employeeValidator.validateGetEmployeesParams(
                ordEmployeeName, ordCertificationName, ordEndDate, offsetStr, limitStr);
        if (validationError != null) {
            throw new CustomValidationException(validationError);
        }

        // Parse offset
        int offsetVal = Constants.DEFAULT_OFFSET;
        if (offsetStr != null && !offsetStr.trim().isEmpty()) {
            offsetVal = Integer.parseInt(offsetStr.trim());
        }

        // Parse limit
        int limitVal = Constants.DEFAULT_LIMIT;
        if (limitStr != null && !limitStr.trim().isEmpty()) {
            limitVal = Integer.parseInt(limitStr.trim());
        }

        // Parse departmentId
        Long deptIdVal = null;
        if (departmentId != null && !departmentId.trim().isEmpty()) {
            try {
                deptIdVal = Long.parseLong(departmentId.trim());
            } catch (NumberFormatException ex) {
                log.warn("Invalid departmentId format: {}", departmentId);
            }
        }

        // Chuẩn hóa tên nhân viên
        String nameFilter = (employeeName == null || employeeName.trim().isEmpty())
                ? null : employeeName.trim();

        // 2.1 Thực hiện lấy tổng số nhân viên từ database
        Long totalRecords = employeeNativeRepository.countEmployees(nameFilter, deptIdVal);

        // Nếu tổng số bản ghi là 0 thì trả về kết quả rỗng
        if (totalRecords == null || totalRecords == 0L) {
            return new EmployeeListDTO(0L, new ArrayList<>());
        }

        // 2.2 Thực hiện get danh sách DTO nhân viên từ database
        List<EmployeeDTO> employeeDTOs = employeeNativeRepository.findEmployees(
                nameFilter,
                deptIdVal,
                limitVal,
                offsetVal,
                ordEmployeeName,
                ordCertificationName,
                ordEndDate,
                sortBy);

        return new EmployeeListDTO(totalRecords, employeeDTOs);
    }
}