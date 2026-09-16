package com.luvina.la.validator;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeValidator.java, 24/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.CertificationItemRequest;
import com.luvina.la.payload.request.UpdateEmployeeRequest;
import com.luvina.la.payload.response.MessageResponse;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeEntityRepository;
import com.luvina.la.entity.EmployeeEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validator kiểm tra tính hợp lệ của các tham số liên quan đến nhân viên.
 *
 * @author Phạm Văn Minh
 */
@Component
public class EmployeeValidator {

    private final EmployeeEntityRepository employeeEntityRepository;
    private final DepartmentRepository departmentRepository;
    private final CertificationRepository certificationRepository;

    @Autowired
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

    /**
     * Formatter cho ngày tháng theo định dạng "uuuu/MM/dd" với chế độ phân tích nghiêm ngặt (STRICT).
     * Sử dụng ký tự 'u' (year) thay vì 'y' (year-of-era) nhằm hỗ trợ xác thực chính xác ngày thực tế trên lịch,
     * ngăn chặn tự động làm tròn các ngày không tồn tại (ví dụ: ngày 29/02 ở năm không nhuận, ngày 31 các tháng có 30 ngày).
     */
    private static final DateTimeFormatter STRICT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu/MM/dd")
                    .withResolverStyle(ResolverStyle.STRICT);

    /** Regex kiểm tra định dạng tên đăng nhập (ER019: gồm a-z, A-Z, 0-9, _, ký tự đầu không được là số) */
    private static final Pattern LOGIN_ID_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");

    /** Regex kiểm tra ký tự Katakana nửa chữ (ER009: Half-width Katakana Unicode range \uFF66 - \uFF9F) */
    private static final Pattern KANA_HALFSIZE_PATTERN = Pattern.compile("^[\\uFF66-\\uFF9F]+$");

    /** Regex kiểm tra định dạng chuỗi ngày yyyy/MM/dd (ER005) */
    private static final Pattern DATE_FORMAT_PATTERN = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");

    /**
     * Validate toàn bộ thông tin trong request thêm mới nhân viên theo tài liệu thiết kế (POST /employee).
     *
     * @param request Request DTO chứa thông tin nhân viên và danh sách chứng chỉ cần thêm mới.
     * @return MessageResponse chứa mã lỗi và danh sách tham số lỗi nếu vi phạm, hoặc null nếu toàn bộ dữ liệu hợp lệ.
     */
    public MessageResponse validateAddEmployee(AddEmployeeRequest request) {
        // 1.1 Validate tài khoản đăng nhập (employeeLoginId)
        MessageResponse loginIdError = validateLoginId(request.getEmployeeLoginId());
        if (loginIdError != null) {
            return loginIdError;
        }

        // 1.2 - 1.6 Validate các thông tin cá nhân cơ bản (tên, tên kana, ngày sinh, email, số điện thoại)
        MessageResponse personalInfoError = validatePersonalInfo(
                request.getEmployeeName(),
                request.getEmployeeNameKana(),
                request.getEmployeeBirthDate(),
                request.getEmployeeEmail(),
                request.getEmployeeTelephone());
        if (personalInfoError != null) {
            return personalInfoError;
        }

        // 1.7 Validate mật khẩu đăng nhập (employeeLoginPassword: bắt buộc nhập khi thêm mới)
        MessageResponse passwordError = validatePassword(request.getEmployeeLoginPassword());
        if (passwordError != null) {
            return passwordError;
        }

        // 1.8 - 1.9 Validate thông tin phòng ban và danh sách chứng chỉ
        return validateDepartmentAndCertifications(request.getDepartmentId(), request.getCertifications());
    }

    /**
     * Validate toàn bộ thông tin trong request cập nhật nhân viên theo tài liệu thiết kế (PUT /employee).
     *
     * @param request Request DTO chứa thông tin cần cập nhật của nhân viên.
     * @return MessageResponse chứa mã lỗi và danh sách tham số lỗi nếu vi phạm, hoặc null nếu toàn bộ dữ liệu hợp lệ.
     */
    public MessageResponse validateUpdateEmployee(UpdateEmployeeRequest request) {
        // 1.1 Validate mã ID nhân viên (employeeId: bắt buộc, tồn tại trong database)
        MessageResponse idError = validateEmployeeId(request.getEmployeeId());
        if (idError != null) {
            return idError;
        }

        // 1.2 Validate tài khoản đăng nhập (employeeLoginId: cho phép giữ nguyên của chính nhân viên này)
        MessageResponse loginIdError = validateLoginId(request.getEmployeeLoginId(), request.getEmployeeId());
        if (loginIdError != null) {
            return loginIdError;
        }

        // 1.3 - 1.7 Validate các thông tin cá nhân cơ bản (tên, tên kana, ngày sinh, email, số điện thoại)
        MessageResponse personalInfoError = validatePersonalInfo(
                request.getEmployeeName(),
                request.getEmployeeNameKana(),
                request.getEmployeeBirthDate(),
                request.getEmployeeEmail(),
                request.getEmployeeTelephone());
        if (personalInfoError != null) {
            return personalInfoError;
        }

        // 1.8 Validate mật khẩu đăng nhập (employeeLoginPassword: chỉ kiểm tra độ dài khi người dùng nhập mật khẩu mới)
        if (request.getEmployeeLoginPassword() != null && !request.getEmployeeLoginPassword().trim().isEmpty()) {
            MessageResponse passwordError = validatePasswordLength(request.getEmployeeLoginPassword().trim());
            if (passwordError != null) {
                return passwordError;
            }
        }

        // 1.9 - 1.10 Validate thông tin phòng ban và danh sách chứng chỉ
        return validateDepartmentAndCertifications(request.getDepartmentId(), request.getCertifications());
    }

    /**
     * Validate các trường thông tin cá nhân cơ bản dùng chung cho cả màn hình Thêm mới và Cập nhật
     * theo thứ tự ưu tiên hiển thị lỗi: Tên -> Tên Katakana -> Ngày sinh -> Email -> Số điện thoại.
     *
     * @param name       Họ và tên nhân viên.
     * @param nameKana   Họ và tên phiên âm Katakana.
     * @param birthDate  Ngày sinh (chuỗi định dạng yyyy/MM/dd).
     * @param email      Địa chỉ email.
     * @param telephone  Số điện thoại liên lạc.
     * @return MessageResponse chứa mã lỗi nếu có trường không hợp lệ, hoặc null nếu tất cả đều hợp lệ.
     */
    public MessageResponse validatePersonalInfo(
            String name,
            String nameKana,
            String birthDate,
            String email,
            String telephone) {
        MessageResponse nameError = validateName(name);
        if (nameError != null) {
            return nameError;
        }

        MessageResponse kanaError = validateNameKana(nameKana);
        if (kanaError != null) {
            return kanaError;
        }

        MessageResponse birthDateError = validateBirthDate(birthDate);
        if (birthDateError != null) {
            return birthDateError;
        }

        MessageResponse emailError = validateEmail(email);
        if (emailError != null) {
            return emailError;
        }

        return validateTelephone(telephone);
    }

    /**
     * Validate thông tin phòng ban và danh sách chứng chỉ của nhân viên.
     * Dùng chung cho cả chức năng Thêm mới và Cập nhật.
     *
     * @param departmentId ID phòng ban dưới dạng chuỗi.
     * @param certs        Danh sách chứng chỉ của nhân viên.
     * @return MessageResponse chứa mã lỗi nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateDepartmentAndCertifications(
            String departmentId,
            List<CertificationItemRequest> certs) {
        MessageResponse departmentError = validateDepartmentId(departmentId);
        if (departmentError != null) {
            return departmentError;
        }

        return validateCertifications(certs);
    }

    /**
     * Kiểm tra định dạng và độ dài của tên đăng nhập (employeeLoginId).
     * - ER001: Bắt buộc nhập.
     * - ER006: Độ dài tối đa 50 ký tự.
     * - ER019: Chỉ chứa ký tự chữ cái (a-z, A-Z), số (0-9), dấu gạch dưới (_), và ký tự đầu tiên không phải là số.
     *
     * @param loginId Tên đăng nhập cần kiểm tra.
     * @return MessageResponse chứa mã lỗi tương ứng nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateLoginIdFormat(String loginId) {
        if (loginId == null || loginId.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        String trimmed = loginId.trim();
        if (trimmed.length() > 50) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        // Chỉ chứa ký tự a-z, A-Z, 0-9, _ và ký tự đầu tiên không phải là số
        if (!LOGIN_ID_PATTERN.matcher(trimmed).matches()) {
            return new MessageResponse(Constants.ERROR_CODE_ER019, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        return null;
    }

    /**
     * Validate tên đăng nhập (employeeLoginId) khi thêm mới nhân viên:
     * - Kiểm tra định dạng và độ dài hợp lệ.
     * - ER003: Kiểm tra tên đăng nhập đã tồn tại trong hệ thống hay chưa.
     *
     * @param loginId Tên đăng nhập cần validate.
     * @return MessageResponse lỗi nếu không hợp lệ hoặc đã tồn tại, ngược lại trả về null.
     */
    public MessageResponse validateLoginId(String loginId) {
        MessageResponse formatError = validateLoginIdFormat(loginId);
        if (formatError != null) {
            return formatError;
        }
        if (employeeEntityRepository.existsByEmployeeLoginId(loginId.trim())) {
            return new MessageResponse(Constants.ERROR_CODE_ER003, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        return null;
    }

    /**
     * Validate tên đăng nhập (employeeLoginId) khi cập nhật nhân viên:
     * - Kiểm tra định dạng và độ dài hợp lệ.
     * - ER003: Kiểm tra tên đăng nhập có bị trùng với nhân viên khác trong hệ thống hay không
     *   (cho phép giữ nguyên tên đăng nhập của chính nhân viên đang được cập nhật).
     *
     * @param loginId           Tên đăng nhập mới cần cập nhật.
     * @param currentEmployeeId ID của nhân viên đang được cập nhật để loại trừ kiểm tra trùng lặp.
     * @return MessageResponse lỗi nếu không hợp lệ hoặc trùng với nhân viên khác, ngược lại null.
     */
    public MessageResponse validateLoginId(String loginId, Long currentEmployeeId) {
        if (currentEmployeeId == null) {
            return validateLoginId(loginId);
        }
        MessageResponse formatError = validateLoginIdFormat(loginId);
        if (formatError != null) {
            return formatError;
        }
        Optional<EmployeeEntity> existingOpt = employeeEntityRepository.findByEmployeeLoginId(loginId.trim());
        if (existingOpt.isPresent() && !existingOpt.get().getEmployeeId().equals(currentEmployeeId)) {
            return new MessageResponse(Constants.ERROR_CODE_ER003, Collections.singletonList(Constants.PARAM_ACCOUNT_NAME));
        }
        return null;
    }

    /**
     * Validate họ tên nhân viên (employeeName):
     * - ER001: Bắt buộc nhập (không được null hoặc chỉ chứa khoảng trắng).
     * - ER006: Độ dài tối đa 125 ký tự.
     *
     * @param name Họ và tên nhân viên.
     * @return MessageResponse nếu có lỗi, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_NAME));
        }
        if (name.trim().length() > 125) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_NAME));
        }
        return null;
    }

    /**
     * Validate tên phiên âm Katakana (employeeNameKana):
     * - ER001: Bắt buộc nhập (không được null hoặc chỉ chứa khoảng trắng).
     * - ER006: Độ dài tối đa 125 ký tự.
     * - ER009: Chỉ chấp nhận ký tự Katakana nửa chữ (Half-width Katakana).
     *
     * @param nameKana Tên Katakana cần kiểm tra.
     * @return MessageResponse nếu có lỗi, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateNameKana(String nameKana) {
        if (nameKana == null || nameKana.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_KATAKANA_NAME));
        }
        String trimmed = nameKana.trim();
        if (trimmed.length() > 125) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_KATAKANA_NAME));
        }
        if (!KANA_HALFSIZE_PATTERN.matcher(trimmed).matches()) {
            return new MessageResponse(Constants.ERROR_CODE_ER009, Collections.singletonList(Constants.PARAM_KATAKANA_NAME));
        }
        return null;
    }

    /**
     * Validate một trường ngày tháng bắt buộc theo định dạng yyyy/MM/dd và tính hợp lệ trên lịch (theo chuẩn strict):
     * - ER001: Bắt buộc nhập.
     * - ER005: Đúng định dạng yyyy/MM/dd.
     * - ER011: Ngày có thực trên lịch (ví dụ: loại bỏ ngày 2023/02/29 hoặc 2023/04/31).
     *
     * @param dateStr   Giá trị chuỗi ngày cần kiểm tra.
     * @param paramName Tên tham số dùng trong message lỗi (Constants.PARAM_BIRTHDAY, Constants.PARAM_CERTIFICATION_START_DATE, v.v.).
     * @return MessageResponse nếu có lỗi, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateDateField(String dateStr, String paramName) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(paramName));
        }
        String trimmed = dateStr.trim();
        if (!isValidDateFormat(trimmed)) {
            return new MessageResponse(Constants.ERROR_CODE_ER005, Arrays.asList(paramName, Constants.DATE_FORMAT_YYYY_MM_DD));
        }
        if (parseStrictDate(trimmed) == null) {
            return new MessageResponse(Constants.ERROR_CODE_ER011, Collections.singletonList(paramName));
        }
        return null;
    }

    /**
     * Validate ngày sinh của nhân viên (employeeBirthDate):
     * - Định dạng yyyy/MM/dd và ngày có thực trên lịch (validateDateField).
     * - ER011: Ngày sinh phải nhỏ hơn ngày hiện tại (không được bằng hoặc lớn hơn hôm nay).
     *
     * @param birthDate Chuỗi ngày sinh cần kiểm tra.
     * @return MessageResponse nếu có lỗi, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateBirthDate(String birthDate) {
        MessageResponse dateError = validateDateField(birthDate, Constants.PARAM_BIRTHDAY);
        if (dateError != null) {
            return dateError;
        }
        LocalDate date = parseStrictDate(birthDate.trim());
        if (date != null && !date.isBefore(LocalDate.now())) {
            return new MessageResponse(Constants.ERROR_CODE_ER011, Collections.singletonList(Constants.PARAM_BIRTHDAY));
        }
        return null;
    }

    /**
     * Validate địa chỉ email của nhân viên (employeeEmail):
     * - ER001: Bắt buộc nhập.
     * - ER006: Độ dài tối đa 125 ký tự.
     * - ER008: Chỉ chứa ký tự 1 byte nửa chữ (ký tự ASCII in được từ mã 33 đến 126).
     *
     * @param email Địa chỉ email cần validate.
     * @return MessageResponse nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_EMAIL));
        }
        String trimmed = email.trim();
        if (trimmed.length() > 125) {
            return new MessageResponse(Constants.ERROR_CODE_ER006, Collections.singletonList(Constants.PARAM_EMAIL));
        }
        if (!trimmed.chars().allMatch(c -> c >= 33 && c <= 126)) {
            return new MessageResponse(Constants.ERROR_CODE_ER008, Collections.singletonList(Constants.PARAM_EMAIL));
        }
        return null;
    }

    /**
     * Validate số điện thoại liên lạc của nhân viên (employeeTelephone):
     * - ER001: Bắt buộc nhập.
     * - ER006: Độ dài tối đa 50 ký tự.
     * - ER008: Chỉ chứa ký tự 1 byte nửa chữ (ASCII từ 0 đến 127).
     *
     * @param telephone Số điện thoại cần validate.
     * @return MessageResponse nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateTelephone(String telephone) {
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
     * Validate độ dài mật khẩu đăng nhập (employeeLoginPassword):
     * - ER007: Độ dài phải nằm trong khoảng từ 8 đến 50 ký tự.
     *
     * @param trimmedPassword Chuỗi mật khẩu đã loại bỏ khoảng trắng thừa ở hai đầu.
     * @return MessageResponse lỗi ER007 nếu không nằm trong khoảng 8-50 ký tự, hoặc null nếu hợp lệ.
     */
    public MessageResponse validatePasswordLength(String trimmedPassword) {
        if (trimmedPassword.length() < 8 || trimmedPassword.length() > 50) {
            return new MessageResponse(Constants.ERROR_CODE_ER007, Arrays.asList(Constants.PARAM_PASSWORD, "8", "50"));
        }
        return null;
    }

    /**
     * Validate mật khẩu đăng nhập khi thêm mới nhân viên:
     * - ER001: Bắt buộc nhập (không được null hoặc chỉ chứa khoảng trắng).
     * - ER007: Độ dài từ 8 đến 50 ký tự (validatePasswordLength).
     *
     * @param password Mật khẩu cần validate.
     * @return MessageResponse nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_PASSWORD));
        }
        return validatePasswordLength(password.trim());
    }

    /**
     * Helper kiểm tra chuỗi số nguyên và giới hạn giá trị.
     *
     * @param valueStr   Giá trị số dạng chuỗi.
     * @param paramName  Tên param cho message lỗi.
     * @param allowZero  true nếu cho phép giá trị >= 0, false nếu yêu cầu giá trị > 0.
     * @return MessageResponse lỗi ER018 nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validatePositiveNumber(String valueStr, String paramName, boolean allowZero) {
        if (valueStr == null || valueStr.trim().isEmpty()) {
            return null;
        }
        String trimmed = valueStr.trim();
        try {
            long val = Long.parseLong(trimmed);
            if (allowZero ? val < 0 : val <= 0) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(paramName));
            }
        } catch (NumberFormatException ex) {
            return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(paramName));
        }
        return null;
    }

    /**
     * Validate mã phòng ban (departmentId):
     * - ER002: Bắt buộc chọn (không được null hoặc rỗng).
     * - ER018: Phải là số nguyên dương (> 0).
     * - ER004: Phòng ban phải tồn tại trong cơ sở dữ liệu.
     *
     * @param departmentId ID phòng ban dưới dạng chuỗi.
     * @return MessageResponse nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateDepartmentId(String departmentId) {
        if (departmentId == null || departmentId.trim().isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER002, Collections.singletonList(Constants.PARAM_GROUP));
        }
        MessageResponse numberError = validatePositiveNumber(departmentId, Constants.PARAM_GROUP, false);
        if (numberError != null) {
            return numberError;
        }

        long parsedId = Long.parseLong(departmentId.trim());
        if (!departmentRepository.existsById(parsedId)) {
            return new MessageResponse(Constants.ERROR_CODE_ER004, Collections.singletonList(Constants.PARAM_GROUP));
        }
        return null;
    }

    /**
     * Validate danh sách chứng chỉ của nhân viên:
     * - startDate (Ngày cấp chứng chỉ): Bắt buộc (ER001), định dạng yyyy/MM/dd (ER005),
     *   ngày hợp lệ trên lịch (ER011), không được lớn hơn ngày hiện tại (ER011).
     * - endDate (Ngày hết hạn chứng chỉ): Bắt buộc (ER001), định dạng yyyy/MM/dd (ER005),
     *   ngày hợp lệ trên lịch (ER011).
     * - Quan hệ giữa 2 ngày: endDate phải sau startDate (ER012).
     * - score (Điểm chứng chỉ): Bắt buộc (ER001), phải là số nguyên không âm >= 0 (ER018).
     * - certificationId (Mã chứng chỉ): Bắt buộc (ER001), phải là số nguyên dương > 0 (ER018),
     *   và phải tồn tại trong cơ sở dữ liệu (ER004).
     *
     * @param certs Danh sách chứng chỉ cần kiểm tra.
     * @return MessageResponse nếu có chứng chỉ không hợp lệ, hoặc null nếu tất cả đều hợp lệ.
     */
    public MessageResponse validateCertifications(List<CertificationItemRequest> certs) {
        if (certs == null || certs.isEmpty()) {
            return null;
        }

        for (CertificationItemRequest cert : certs) {
            // 1. Kiểm tra ngày cấp chứng chỉ (startDate)
            MessageResponse startDateError = validateDateField(cert.getStartDate(), Constants.PARAM_CERTIFICATION_START_DATE);
            if (startDateError != null) {
                return startDateError;
            }
            LocalDate parsedStart = parseStrictDate(cert.getStartDate().trim());
            if (parsedStart != null && parsedStart.isAfter(LocalDate.now())) {
                return new MessageResponse(Constants.ERROR_CODE_ER011, Collections.singletonList(Constants.PARAM_CERTIFICATION_START_DATE));
            }

            // 2. Kiểm tra ngày hết hạn chứng chỉ (endDate)
            MessageResponse endDateError = validateDateField(cert.getEndDate(), Constants.PARAM_CERTIFICATION_END_DATE);
            if (endDateError != null) {
                return endDateError;
            }

            // 3. Kiểm tra logic ngày: ngày hết hạn phải sau ngày cấp (endDate > startDate - ER012)
            LocalDate parsedEnd = parseStrictDate(cert.getEndDate().trim());
            if (!parsedEnd.isAfter(parsedStart)) {
                return new MessageResponse(Constants.ERROR_CODE_ER012, Arrays.asList(Constants.PARAM_CERTIFICATION_END_DATE, Constants.PARAM_CERTIFICATION_START_DATE));
            }

            // 4. Kiểm tra điểm chứng chỉ (score: bắt buộc, số nguyên >= 0 - ER001, ER018)
            String score = cert.getScore();
            if (score == null || score.trim().isEmpty()) {
                return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_SCORE));
            }
            MessageResponse scoreError = validatePositiveNumber(score, Constants.PARAM_SCORE, true);
            if (scoreError != null) {
                return scoreError;
            }

            // 5. Kiểm tra mã loại chứng chỉ (certificationId: bắt buộc, số nguyên > 0, tồn tại trong DB - ER001, ER018, ER004)
            String certId = cert.getCertificationId();
            if (certId == null || certId.trim().isEmpty()) {
                return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_CERTIFICATION));
            }
            MessageResponse certIdError = validatePositiveNumber(certId, Constants.PARAM_CERTIFICATION, false);
            if (certIdError != null) {
                return certIdError;
            }

            long parsedCertId = Long.parseLong(certId.trim());
            if (!certificationRepository.existsById(parsedCertId)) {
                return new MessageResponse(Constants.ERROR_CODE_ER004, Collections.singletonList(Constants.PARAM_CERTIFICATION));
            }
        }

        return null;
    }

    /**
     * Kiểm tra chuỗi ngày có đúng định dạng yyyy/MM/dd hay không (sử dụng regex 4 số/2 số/2 số).
     *
     * @param dateStr Chuỗi ngày cần kiểm tra.
     * @return true nếu khớp định dạng yyyy/MM/dd, false nếu null hoặc sai định dạng.
     */
    public boolean isValidDateFormat(String dateStr) {
        if (dateStr == null) {
            return false;
        }
        return DATE_FORMAT_PATTERN.matcher(dateStr).matches();
    }

    /**
     * Parse chuỗi ngày theo định dạng uuuu/MM/dd với chế độ nghiêm ngặt (ResolverStyle.STRICT).
     * Giúp phát hiện và loại bỏ các ngày không có thực trên lịch (như 2023/02/29, 2023/04/31,...).
     *
     * @param dateStr Chuỗi ngày cần parse.
     * @return Đối tượng {@link LocalDate} nếu ngày hợp lệ, hoặc null nếu không thể parse.
     */
    public LocalDate parseStrictDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr, STRICT_DATE_FORMATTER);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Validate tham số employeeId theo thiết kế API Xem chi tiết nhân viên (GET /employee/{id}):
     * - ER001: Bắt buộc truyền ID hợp lệ (> 0).
     * - ER013: Nhân viên phải tồn tại trong cơ sở dữ liệu.
     *
     * @param employeeId ID của nhân viên cần lấy chi tiết.
     * @return MessageResponse nếu có lỗi (ER001 hoặc ER013), hoặc null nếu hợp lệ.
     */
    public MessageResponse validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_ID));
        }

        if (!employeeEntityRepository.existsById(employeeId)) {
            return new MessageResponse(Constants.ERROR_CODE_ER013, Collections.singletonList(Constants.PARAM_ID));
        }

        return null;
    }

    /**
     * Validate tham số employeeId theo thiết kế API Xóa nhân viên (DELETE /employee/{id}):
     * - ER001: Bắt buộc truyền ID hợp lệ (> 0).
     * - ER014: Nhân viên phải tồn tại trong bảng employees.
     * - ER020: Không được phép xóa tài khoản nhân viên có vai trò quản trị viên (role ADMIN).
     *
     * @param employeeId ID của nhân viên cần xóa.
     * @return MessageResponse nếu có lỗi (ER001, ER014 hoặc ER020), hoặc null nếu hợp lệ.
     */
    public MessageResponse validateEmployeeIdForDelete(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            return new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_ID));
        }

        Optional<EmployeeEntity> employeeOpt = employeeEntityRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return new MessageResponse(Constants.ERROR_CODE_ER014, Collections.singletonList(Constants.PARAM_ID));
        }

        if (Constants.ROLE_ADMIN.equalsIgnoreCase(employeeOpt.get().getEmployeeRole())) {
            return new MessageResponse(Constants.ERROR_CODE_ER020, new ArrayList<>());
        }

        return null;
    }
}
