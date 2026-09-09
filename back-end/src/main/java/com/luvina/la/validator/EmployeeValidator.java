package com.luvina.la.validator;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeValidator.java, 24/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.CertificationItemRequest;
import com.luvina.la.payload.response.MessageResponse;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeEntityRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validator kiểm tra tính hợp lệ của các tham số liên quan đến nhân viên.
 *
 * @author Phạm Văn Minh
 */
@Component
public class EmployeeValidator {

    @Autowired(required = false)
    private EmployeeEntityRepository employeeEntityRepository;

    @Autowired(required = false)
    private DepartmentRepository departmentRepository;

    @Autowired(required = false)
    private CertificationRepository certificationRepository;

    public EmployeeValidator() {
    }

    public EmployeeValidator(
            EmployeeEntityRepository employeeEntityRepository,
            DepartmentRepository departmentRepository,
            CertificationRepository certificationRepository) {
        this.employeeEntityRepository = employeeEntityRepository;
        this.departmentRepository = departmentRepository;
        this.certificationRepository = certificationRepository;
    }

    /**
     * Validate toàn bộ các tham số đầu vào khi lấy danh sách nhân viên.
     *
     * @param ordEmployeeName      Chiều sắp xếp theo tên nhân viên.
     * @param ordCertificationName Chiều sắp xếp theo tên chứng chỉ.
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn chứng chỉ.
     * @param offsetStr            Vị trí bắt đầu lấy bản ghi dưới dạng chuỗi.
     * @param limitStr             Số bản ghi tối đa dưới dạng chuỗi.
     * @return MessageResponse chứa thông tin lỗi nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateGetEmployeesParams(
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String offsetStr,
            String limitStr) {

        // 1.1 Validate ord_employee_name, ord_certification_name, ord_end_date
        MessageResponse orderError = validateOrderParams(ordEmployeeName, ordCertificationName, ordEndDate);
        if (orderError != null) {
            return orderError;
        }

        // 1.2 Validate offset
        MessageResponse offsetError = validateOffset(offsetStr);
        if (offsetError != null) {
            return offsetError;
        }

        // 1.3 Validate limit
        MessageResponse limitError = validateLimit(limitStr);
        if (limitError != null) {
            return limitError;
        }

        return null;
    }

    /**
     * Kiểm tra tính hợp lệ của các tham số sắp xếp.
     *
     * @param ordEmployeeName      Chiều sắp xếp theo tên nhân viên.
     * @param ordCertificationName Chiều sắp xếp theo tên chứng chỉ.
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn.
     * @return MessageResponse lỗi ER021 nếu có tham số không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateOrderParams(
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate) {
        if (!isValidOrderParam(ordEmployeeName)
                || !isValidOrderParam(ordCertificationName)
                || !isValidOrderParam(ordEndDate)) {
            return new MessageResponse(Constants.ERROR_CODE_ER021, new ArrayList<>());
        }
        return null;
    }

    /**
     * Kiểm tra tính hợp lệ của một tham số sắp xếp (chấp nhận rỗng, "ASC" hoặc "DESC").
     *
     * @param orderParam Tham số order cần kiểm tra.
     * @return true nếu hợp lệ, false nếu không hợp lệ.
     */
    public boolean isValidOrderParam(String orderParam) {
        if (orderParam == null || orderParam.trim().isEmpty()) {
            return true;
        }
        String trimmed = orderParam.trim();
        return Constants.ORDER_ASC.equalsIgnoreCase(trimmed) || Constants.ORDER_DESC.equalsIgnoreCase(trimmed);
    }

    /**
     * Validate tham số offset (phải là số nguyên không âm).
     *
     * @param offsetStr Giá trị offset dạng chuỗi.
     * @return MessageResponse lỗi ER018 nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateOffset(String offsetStr) {
        if (offsetStr != null && !offsetStr.trim().isEmpty()) {
            try {
                int parsedOffset = Integer.parseInt(offsetStr.trim());
                if (parsedOffset < 0) {
                    return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_OFFSET));
                }
            } catch (NumberFormatException ex) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_OFFSET));
            }
        }
        return null;
    }

    /**
     * Validate tham số limit (phải là số nguyên dương > 0).
     *
     * @param limitStr Giá trị limit dạng chuỗi.
     * @return MessageResponse lỗi ER018 nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateLimit(String limitStr) {
        if (limitStr != null && !limitStr.trim().isEmpty()) {
            try {
                int parsedLimit = Integer.parseInt(limitStr.trim());
                if (parsedLimit <= 0) {
                    return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_LIMIT));
                }
            } catch (NumberFormatException ex) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_LIMIT));
            }
        }
        return null;
    }

    private static final java.time.format.DateTimeFormatter STRICT_DATE_FORMATTER =
            java.time.format.DateTimeFormatter.ofPattern("uuuu/MM/dd")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);

    /**
     * Validate toàn bộ thông tin trong request thêm mới nhân viên sử dụng các repository được inject tự động.
     *
     * @param request Request DTO chứa thông tin nhân viên và chứng chỉ.
     * @return MessageResponse chứa mã lỗi và params nếu có lỗi, ngược lại null nếu hợp lệ.
     */
    public MessageResponse validateAddEmployee(AddEmployeeRequest request) {
        return validateAddEmployee(request, this.employeeEntityRepository, this.departmentRepository, this.certificationRepository);
    }

    /**
     * Validate toàn bộ thông tin trong request thêm mới nhân viên theo tài liệu thiết kế (POST /employee).
     *
     * @param request          Request DTO chứa thông tin nhân viên và chứng chỉ.
     * @param employeeRepo     Repository nhân viên để kiểm tra trùng login ID.
     * @param departmentRepo   Repository phòng ban để kiểm tra tồn tại phòng ban.
     * @param certificationRepo Repository chứng chỉ để kiểm tra tồn tại chứng chỉ.
     * @return MessageResponse chứa mã lỗi và params nếu có lỗi, ngược lại null nếu hợp lệ.
     */
    public MessageResponse validateAddEmployee(
            AddEmployeeRequest request,
            EmployeeEntityRepository employeeRepo,
            DepartmentRepository departmentRepo,
            CertificationRepository certificationRepo) {

        if (request == null) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }

        // 1.1 Validate employeeLoginId
        MessageResponse loginIdError = validateEmployeeLoginId(request.getEmployeeLoginId(), employeeRepo);
        if (loginIdError != null) {
            return loginIdError;
        }

        // 1.2 Validate employeeName
        MessageResponse nameError = validateEmployeeName(request.getEmployeeName());
        if (nameError != null) {
            return nameError;
        }

        // 1.3 Validate employeeNameKana
        MessageResponse kanaError = validateEmployeeNameKana(request.getEmployeeNameKana());
        if (kanaError != null) {
            return kanaError;
        }

        // 1.4 Validate employeeBirthDate
        MessageResponse birthDateError = validateEmployeeBirthDate(request.getEmployeeBirthDate());
        if (birthDateError != null) {
            return birthDateError;
        }

        // 1.5 Validate employeeEmail
        MessageResponse emailError = validateEmployeeEmail(request.getEmployeeEmail());
        if (emailError != null) {
            return emailError;
        }

        // 1.6 Validate employeeTelephone
        MessageResponse telephoneError = validateEmployeeTelephone(request.getEmployeeTelephone());
        if (telephoneError != null) {
            return telephoneError;
        }

        // 1.7 Validate employeeLoginPassword
        MessageResponse passwordError = validateEmployeePassword(request.getEmployeeLoginPassword());
        if (passwordError != null) {
            return passwordError;
        }

        // 1.8 Validate departmentId
        MessageResponse departmentError = validateDepartmentId(request.getDepartmentId(), departmentRepo);
        if (departmentError != null) {
            return departmentError;
        }

        // 1.9 Validate certifications
        MessageResponse certsError = validateCertifications(request.getCertifications(), certificationRepo);
        if (certsError != null) {
            return certsError;
        }

        return null;
    }

    /**
     * Validate employeeLoginId.
     */
    public MessageResponse validateEmployeeLoginId(
            String loginId,
            EmployeeEntityRepository employeeRepo) {
        if (loginId == null || loginId.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        String trimmed = loginId.trim();
        if (trimmed.length() > 50) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        // Chỉ chứa ký tự a-z, A-Z, 0-9, _ và ký tự đầu tiên không phải là số
        if (!trimmed.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            return new MessageResponse(Constants.ERROR_CODE_ER019, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        if (employeeRepo != null && employeeRepo.existsByEmployeeLoginId(trimmed)) {
            return new MessageResponse(Constants.ERROR_CODE_ER003, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        return null;
    }

    /**
     * Validate employeeName.
     */
    public MessageResponse validateEmployeeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_NAME));
        }
        if (name.trim().length() > 125) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_NAME));
        }
        return null;
    }

    /**
     * Validate employeeNameKana (yêu cầu Halfsize Katakana).
     */
    public MessageResponse validateEmployeeNameKana(String nameKana) {
        if (nameKana == null || nameKana.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_KATAKANA_NAME));
        }
        String trimmed = nameKana.trim();
        if (trimmed.length() > 125) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_KATAKANA_NAME));
        }
        if (!trimmed.matches("^[\\uFF66-\\uFF9F]+$")) {
            return new MessageResponse(Constants.ERROR_CODE_ER009, Collections.singletonList(Constants.PARAM_KATAKANA_NAME));
        }
        return null;
    }

    /**
     * Validate một trường ngày tháng bắt buộc theo định dạng yyyy/MM/dd và tính hợp lệ của ngày.
     *
     * @param dateStr   Giá trị chuỗi ngày cần kiểm tra.
     * @param paramName Tên tham số dùng trong message lỗi (Constants.PARAM_BIRTHDAY, Constants.PARAM_CERTIFICATION_START_DATE, v.v.).
     * @return MessageResponse nếu có lỗi (ER001 nếu rỗng, ER005 nếu sai format, ER011 nếu ngày không hợp lệ), null nếu hợp lệ.
     */
    public MessageResponse validateDateField(String dateStr, String paramName) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(paramName));
        }
        String trimmed = dateStr.trim();
        if (!isValidDateFormat(trimmed)) {
            return new MessageResponse(Constants.ERROR_CODE_ER005, java.util.Arrays.asList(paramName, Constants.DATE_FORMAT_YYYY_MM_DD));
        }
        if (parseStrictDate(trimmed) == null) {
            return new MessageResponse(Constants.ERROR_CODE_ER011, Collections.singletonList(paramName));
        }
        return null;
    }

    /**
     * Validate employeeBirthDate (định dạng yyyy/MM/dd và ngày hợp lệ).
     */
    public MessageResponse validateEmployeeBirthDate(String birthDate) {
        return validateDateField(birthDate, Constants.PARAM_BIRTHDAY);
    }

    /**
     * Validate employeeEmail.
     */
    public MessageResponse validateEmployeeEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_EMAIL));
        }
        if (email.trim().length() > 125) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_EMAIL));
        }
        return null;
    }

    /**
     * Validate employeeTelephone (chỉ ký tự 1 byte, độ dài tối đa 50).
     */
    public MessageResponse validateEmployeeTelephone(String telephone) {
        if (telephone == null || telephone.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_TEL));
        }
        String trimmed = telephone.trim();
        if (trimmed.length() > 50) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_TEL));
        }
        if (!trimmed.chars().allMatch(c -> c >= 0 && c <= 127)) {
            return new MessageResponse(Constants.ERROR_CODE_ER008, Collections.singletonList(Constants.PARAM_TEL));
        }
        return null;
    }

    /**
     * Validate employeeLoginPassword (độ dài 8 đến 50 ký tự).
     */
    public MessageResponse validateEmployeePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_PASSWORD));
        }
        String trimmed = password.trim();
        if (trimmed.length() < 8 || trimmed.length() > 50) {
            return new MessageResponse(Constants.ERROR_CODE_ER007, java.util.Arrays.asList(Constants.PARAM_PASSWORD, "8", "50"));
        }
        return null;
    }

    /**
     * Validate departmentId (bắt buộc, số nguyên dương, tồn tại trong database).
     */
    public MessageResponse validateDepartmentId(
            String departmentId,
            DepartmentRepository departmentRepo) {
        if (departmentId == null || departmentId.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER002, Collections.singletonList(Constants.PARAM_GROUP));
        }
        String trimmed = departmentId.trim();
        if (!trimmed.matches("^[0-9]+$")) {
            return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_GROUP));
        }
        long parsedId;
        try {
            parsedId = Long.parseLong(trimmed);
            if (parsedId <= 0) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_GROUP));
            }
        } catch (NumberFormatException ex) {
            return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_GROUP));
        }

        if (departmentRepo != null && !departmentRepo.existsById(parsedId)) {
            return new MessageResponse(Constants.ERROR_CODE_ER004, Collections.singletonList(Constants.PARAM_GROUP));
        }
        return null;
    }

    /**
     * Validate danh sách certifications.
     */
    public MessageResponse validateCertifications(
            List<CertificationItemRequest> certs,
            CertificationRepository certificationRepo) {
        if (certs == null || certs.isEmpty()) {
            return null;
        }

        for (CertificationItemRequest cert : certs) {
            if (cert == null) {
                continue;
            }

            // 1. startDate
            MessageResponse startDateError = validateDateField(cert.getStartDate(), Constants.PARAM_CERTIFICATION_START_DATE);
            if (startDateError != null) {
                return startDateError;
            }

            // 2. endDate
            MessageResponse endDateError = validateDateField(cert.getEndDate(), Constants.PARAM_CERTIFICATION_END_DATE);
            if (endDateError != null) {
                return endDateError;
            }

            // Check endDate > startDate (ER012)
            java.time.LocalDate parsedStart = parseStrictDate(cert.getStartDate().trim());
            java.time.LocalDate parsedEnd = parseStrictDate(cert.getEndDate().trim());
            if (!parsedEnd.isAfter(parsedStart)) {
                return new MessageResponse(Constants.ERROR_CODE_ER012, java.util.Arrays.asList(Constants.PARAM_CERTIFICATION_END_DATE, Constants.PARAM_CERTIFICATION_START_DATE));
            }

            // 3. score
            String score = cert.getScore();
            if (score == null || score.trim().isEmpty()) {
                return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_SCORE));
            }
            String trimmedScore = score.trim();
            if (!trimmedScore.matches("^[0-9]+$")) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_SCORE));
            }
            try {
                int parsedScore = Integer.parseInt(trimmedScore);
                if (parsedScore < 0) {
                    return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_SCORE));
                }
            } catch (NumberFormatException ex) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_SCORE));
            }

            // 4. certificationId
            String certId = cert.getCertificationId();
            if (certId == null || certId.trim().isEmpty()) {
                return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_CERTIFICATION));
            }
            String trimmedCertId = certId.trim();
            if (!trimmedCertId.matches("^[0-9]+$")) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_CERTIFICATION));
            }
            long parsedCertId;
            try {
                parsedCertId = Long.parseLong(trimmedCertId);
                if (parsedCertId <= 0) {
                    return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_CERTIFICATION));
                }
            } catch (NumberFormatException ex) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_CERTIFICATION));
            }

            if (certificationRepo != null && !certificationRepo.existsById(parsedCertId)) {
                return new MessageResponse(Constants.ERROR_CODE_ER004, Collections.singletonList(Constants.PARAM_CERTIFICATION));
            }
        }

        return null;
    }

    /**
     * Kiểm tra định dạng chuỗi ngày yyyy/MM/dd.
     */
    public boolean isValidDateFormat(String dateStr) {
        if (dateStr == null) {
            return false;
        }
        return dateStr.matches("^\\d{4}/\\d{2}/\\d{2}$");
    }

    /**
     * Parse chuỗi ngày nghiêm ngặt (strict) theo định dạng yyyy/MM/dd.
     */
    public java.time.LocalDate parseStrictDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        try {
            return java.time.LocalDate.parse(dateStr, STRICT_DATE_FORMATTER);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Validate tham số employeeId theo thiết kế API Get employee sử dụng repository được inject.
     */
    public MessageResponse validateEmployeeId(Long employeeId) {
        return validateEmployeeId(employeeId, this.employeeEntityRepository);
    }

    /**
     * Validate tham số employeeId theo thiết kế API Get/Delete employee.
     *
     * @param employeeId   ID của nhân viên.
     * @param employeeRepo Repository để kiểm tra tồn tại trong CSDL.
     * @return MessageResponse nếu có lỗi (ER001 hoặc ER013), null nếu hợp lệ.
     */
    public MessageResponse validateEmployeeId(
            Long employeeId,
            EmployeeEntityRepository employeeRepo) {
        if (employeeId == null || employeeId <= 0) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_ID));
        }

        if (employeeRepo != null && !employeeRepo.existsById(employeeId)) {
            return new MessageResponse(Constants.ERROR_CODE_ER013, Collections.singletonList(Constants.PARAM_ID));
        }

        return null;
    }

    /**
     * Validate tham số employeeId theo thiết kế API Delete employee sử dụng repository được inject.
     */
    public MessageResponse validateEmployeeIdForDelete(Long employeeId) {
        return validateEmployeeIdForDelete(employeeId, this.employeeEntityRepository);
    }

    /**
     * Validate tham số employeeId theo thiết kế API Delete employee.
     * Trả về ER001 nếu không tồn tại tham số, trả về ER014 nếu không tồn tại trong bảng employees.
     *
     * @param employeeId   ID của nhân viên cần xóa.
     * @param employeeRepo Repository để kiểm tra tồn tại trong CSDL.
     * @return MessageResponse nếu có lỗi (ER001 hoặc ER014), null nếu hợp lệ.
     */
    public MessageResponse validateEmployeeIdForDelete(
            Long employeeId,
            EmployeeEntityRepository employeeRepo) {
        if (employeeId == null || employeeId <= 0) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_ID));
        }

        if (employeeRepo != null && !employeeRepo.existsById(employeeId)) {
            return new MessageResponse(Constants.ERROR_CODE_ER014, Collections.singletonList(Constants.PARAM_ID));
        }

        return null;
    }
}
