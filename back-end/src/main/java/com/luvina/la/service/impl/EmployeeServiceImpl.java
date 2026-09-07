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

import com.luvina.la.entity.EmployeeCertificationEntity;
import com.luvina.la.entity.EmployeeEntity;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.CertificationItemRequest;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.EmployeeEntityRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lớp triển khai các dịch vụ liên quan đến nhân viên.
 * Trả về EmployeeListDTO và EmployeeDTO cho tầng Controller.
 *
 * @author Phạm Văn Minh
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeNativeRepository employeeNativeRepository;
    private final EmployeeValidator employeeValidator;
    private final EmployeeEntityRepository employeeEntityRepository;
    private final DepartmentRepository departmentRepository;
    private final CertificationRepository certificationRepository;
    private final EmployeeCertificationRepository employeeCertificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;

    /**
     * Khởi tạo EmployeeServiceImpl với tham số tối thiểu cho backward compatibility trong unit tests.
     */
    public EmployeeServiceImpl(
            EmployeeNativeRepository employeeNativeRepository,
            EmployeeValidator employeeValidator) {
        this(employeeNativeRepository, employeeValidator, null, null, null, null, null, null);
    }

    /**
     * Khởi tạo EmployeeServiceImpl với 7 tham số hỗ trợ backward compatibility trong unit tests.
     */
    public EmployeeServiceImpl(
            EmployeeNativeRepository employeeNativeRepository,
            EmployeeValidator employeeValidator,
            EmployeeEntityRepository employeeEntityRepository,
            DepartmentRepository departmentRepository,
            CertificationRepository certificationRepository,
            EmployeeCertificationRepository employeeCertificationRepository,
            PasswordEncoder passwordEncoder) {
        this(employeeNativeRepository, employeeValidator, employeeEntityRepository, departmentRepository, certificationRepository, employeeCertificationRepository, passwordEncoder, null);
    }

    /**
     * Khởi tạo EmployeeServiceImpl với đầy đủ các dependencies cần thiết.
     */
    @Autowired
    public EmployeeServiceImpl(
            EmployeeNativeRepository employeeNativeRepository,
            EmployeeValidator employeeValidator,
            EmployeeEntityRepository employeeEntityRepository,
            DepartmentRepository departmentRepository,
            CertificationRepository certificationRepository,
            EmployeeCertificationRepository employeeCertificationRepository,
            PasswordEncoder passwordEncoder,
            EmployeeMapper employeeMapper) {
        this.employeeNativeRepository = employeeNativeRepository;
        this.employeeValidator = employeeValidator;
        this.employeeEntityRepository = employeeEntityRepository;
        this.departmentRepository = departmentRepository;
        this.certificationRepository = certificationRepository;
        this.employeeCertificationRepository = employeeCertificationRepository;
        this.passwordEncoder = passwordEncoder;
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

    /**
     * Thêm mới nhân viên và danh sách chứng chỉ tiếng Nhật (nếu có) theo tài liệu thiết kế API.
     * Toàn bộ thao tác thực thi trong một transaction, tự động rollback nếu có ngoại lệ.
     *
     * @param request Thông tin nhân viên và chứng chỉ gửi lên từ client.
     * @return EmployeeDTO chứa thông tin nhân viên vừa được tạo (bao gồm employeeId).
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDTO addEmployee(AddEmployeeRequest request) {
        // 1. Validate parameter
        MessageResponse validationError = employeeValidator.validateAddEmployee(
                request, employeeEntityRepository, departmentRepository, certificationRepository);
        if (validationError != null) {
            throw new CustomValidationException(validationError);
        }

        // 2. Insert nhân viên vào database (bảng employees)
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setDepartmentId(Long.parseLong(request.getDepartmentId().trim()));
        employeeEntity.setEmployeeName(request.getEmployeeName().trim());
        employeeEntity.setEmployeeNameKana(request.getEmployeeNameKana().trim());
        employeeEntity.setEmployeeBirthDate(employeeValidator.parseStrictDate(request.getEmployeeBirthDate().trim()));
        employeeEntity.setEmployeeEmail(request.getEmployeeEmail().trim());
        employeeEntity.setEmployeeTelephone(request.getEmployeeTelephone().trim());
        employeeEntity.setEmployeeLoginId(request.getEmployeeLoginId().trim());
        if (passwordEncoder != null && request.getEmployeeLoginPassword() != null) {
            employeeEntity.setEmployeeLoginPassword(passwordEncoder.encode(request.getEmployeeLoginPassword().trim()));
        } else {
            employeeEntity.setEmployeeLoginPassword(request.getEmployeeLoginPassword());
        }
        employeeEntity.setEmployeeRole("USER");

        EmployeeEntity savedEmployee = employeeEntityRepository.save(employeeEntity);
        Long newEmployeeId = savedEmployee.getEmployeeId();

        // 3. Nếu tồn tại certifications thì thực hiện insert vào bảng employees_certifications
        if (request.getCertifications() != null && !request.getCertifications().isEmpty()) {
            for (CertificationItemRequest certReq : request.getCertifications()) {
                if (certReq == null) {
                    continue;
                }
                EmployeeCertificationEntity certEntity = EmployeeCertificationEntity.builder()
                        .employeeId(newEmployeeId)
                        .certificationId(Long.parseLong(certReq.getCertificationId().trim()))
                        .startDate(employeeValidator.parseStrictDate(certReq.getStartDate().trim()))
                        .endDate(employeeValidator.parseStrictDate(certReq.getEndDate().trim()))
                        .score(new BigDecimal(certReq.getScore().trim()))
                        .build();

                employeeCertificationRepository.save(certEntity);
            }
        }

        // 4. Chuyển đổi sang EmployeeDTO và trả về
        if (employeeMapper != null) {
            return employeeMapper.toDto(savedEmployee);
        }
        return EmployeeDTO.builder()
                .employeeId(newEmployeeId)
                .departmentId(savedEmployee.getDepartmentId())
                .employeeName(savedEmployee.getEmployeeName())
                .employeeNameKana(savedEmployee.getEmployeeNameKana())
                .employeeBirthDate(savedEmployee.getEmployeeBirthDate())
                .employeeEmail(savedEmployee.getEmployeeEmail())
                .employeeTelephone(savedEmployee.getEmployeeTelephone())
                .employeeLoginId(savedEmployee.getEmployeeLoginId())
                .employeeRole(savedEmployee.getEmployeeRole())
                .build();
    }
}