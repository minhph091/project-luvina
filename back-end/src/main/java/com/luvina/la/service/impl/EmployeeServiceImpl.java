package com.luvina.la.service.impl;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeServiceImpl.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.payload.response.EmployeeResponse;
import com.luvina.la.payload.response.GetEmployeesResponse;
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
 * Sử dụng EmployeeDTO làm tầng dữ liệu trung gian cho nghiệp vụ.
 *
 * @author Phạm Văn Minh
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeNativeRepository employeeNativeRepository;
    private final EmployeeValidator employeeValidator;
    private final EmployeeMapper employeeMapper;

    /**
     * Khởi tạo EmployeeServiceImpl.
     *
     * @param employeeNativeRepository Repository native query cho nhân viên.
     * @param employeeValidator        Validator kiểm tra tính hợp lệ của tham số nhân viên.
     * @param employeeMapper           Mapper chuyển đổi giữa DTO và Response Payload.
     */
    public EmployeeServiceImpl(
            EmployeeNativeRepository employeeNativeRepository,
            EmployeeValidator employeeValidator,
            EmployeeMapper employeeMapper) {
        this.employeeNativeRepository = employeeNativeRepository;
        this.employeeValidator = employeeValidator;
        this.employeeMapper = employeeMapper;
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
     * @return GetEmployeesResponse chứa kết quả tìm kiếm hoặc thông tin lỗi theo thiết kế.
     */
    @Override
    public GetEmployeesResponse getEmployees(
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
     * @return GetEmployeesResponse chứa kết quả tìm kiếm hoặc thông tin lỗi theo thiết kế.
     */
    @Override
    public GetEmployeesResponse getEmployees(
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
            GetEmployeesResponse errorResponse = new GetEmployeesResponse();
            errorResponse.setCode(Constants.RESPONSE_CODE_ERROR);
            errorResponse.setMessage(validationError);
            return errorResponse;
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

        try {
            // 2.1 Thực hiện lấy tổng số nhân viên từ database
            Long totalRecords = employeeNativeRepository.countEmployees(nameFilter, deptIdVal);

            // Nếu tổng số bản ghi là 0 thì trả về kết quả rỗng
            if (totalRecords == null || totalRecords == 0L) {
                GetEmployeesResponse emptyResponse = new GetEmployeesResponse();
                emptyResponse.setCode(Constants.RESPONSE_CODE_SUCCESS);
                emptyResponse.setTotalRecords(0L);
                emptyResponse.setEmployees(new ArrayList<>());
                return emptyResponse;
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

            // 2.3 Chuyển đổi DTO sang Response Payload thông qua Mapper
            List<EmployeeResponse> employeeResponses = employeeMapper.toResponseList(employeeDTOs);

            // 3. Tạo dữ liệu response thành công
            GetEmployeesResponse response = new GetEmployeesResponse();
            response.setCode(Constants.RESPONSE_CODE_SUCCESS);
            response.setTotalRecords(totalRecords);
            response.setEmployees(employeeResponses);
            return response;

        } catch (Exception ex) {
            log.error("Error occurred while getting employee list: ", ex);
            GetEmployeesResponse errorResponse = new GetEmployeesResponse();
            errorResponse.setCode(Constants.RESPONSE_CODE_ERROR);
            errorResponse.setMessage(new MessageResponse(Constants.ERROR_CODE_ER015, new ArrayList<>()));
            return errorResponse;
        }
    }
}