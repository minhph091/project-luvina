package com.luvina.la.validator;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeValidatorTest.java, 24/08/2026 Phạm Văn Minh
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test cho EmployeeValidator.
 *
 * @author Phạm Văn Minh
 */
public class EmployeeValidatorTest {

    private EmployeeValidator employeeValidator;

    @BeforeEach
    void setUp() {
        employeeValidator = new EmployeeValidator();
    }

    @Test
    @DisplayName("Test validateGetEmployeesParams thành công khi tất cả tham số hợp lệ")
    void testValidateGetEmployeesParamsValid() {
        MessageResponse error = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "0", "10");
        assertNull(error);

        // Với tham số null hoặc rỗng (mặc định)
        MessageResponse errorNull = employeeValidator.validateGetEmployeesParams(null, null, null, null, null);
        assertNull(errorNull);
    }

    @Test
    @DisplayName("Test validateGetEmployeesParams lỗi khi tham số order không hợp lệ (ER021)")
    void testValidateGetEmployeesParamsInvalidOrder() {
        MessageResponse error = employeeValidator.validateGetEmployeesParams("INVALID", "ASC", "DESC", "0", "10");
        assertNotNull(error);
        assertEquals(Constants.ERROR_CODE_ER021, error.getCode());
        assertTrue(error.getParams().isEmpty());

        MessageResponse errorCert = employeeValidator.validateGetEmployeesParams("ASC", "WRONG", "DESC", "0", "10");
        assertNotNull(errorCert);
        assertEquals(Constants.ERROR_CODE_ER021, errorCert.getCode());

        MessageResponse errorEnd = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "WRONG", "0", "10");
        assertNotNull(errorEnd);
        assertEquals(Constants.ERROR_CODE_ER021, errorEnd.getCode());
    }

    @Test
    @DisplayName("Test validateGetEmployeesParams lỗi khi offset âm hoặc sai định dạng (ER018)")
    void testValidateGetEmployeesParamsInvalidOffset() {
        MessageResponse errorNegative = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "-1", "10");
        assertNotNull(errorNegative);
        assertEquals(Constants.ERROR_CODE_ER018, errorNegative.getCode());
        assertEquals(List.of(Constants.PARAM_OFFSET), errorNegative.getParams());

        MessageResponse errorFormat = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "abc", "10");
        assertNotNull(errorFormat);
        assertEquals(Constants.ERROR_CODE_ER018, errorFormat.getCode());
        assertEquals(List.of(Constants.PARAM_OFFSET), errorFormat.getParams());
    }

    @Test
    @DisplayName("Test validateGetEmployeesParams lỗi khi limit <= 0 hoặc sai định dạng (ER018)")
    void testValidateGetEmployeesParamsInvalidLimit() {
        MessageResponse errorZero = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "0", "0");
        assertNotNull(errorZero);
        assertEquals(Constants.ERROR_CODE_ER018, errorZero.getCode());
        assertEquals(List.of(Constants.PARAM_LIMIT), errorZero.getParams());

        MessageResponse errorNegative = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "0", "-5");
        assertNotNull(errorNegative);
        assertEquals(Constants.ERROR_CODE_ER018, errorNegative.getCode());
        assertEquals(List.of(Constants.PARAM_LIMIT), errorNegative.getParams());

        MessageResponse errorFormat = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "0", "xyz");
        assertNotNull(errorFormat);
        assertEquals(Constants.ERROR_CODE_ER018, errorFormat.getCode());
        assertEquals(List.of(Constants.PARAM_LIMIT), errorFormat.getParams());
    }

    @Test
    @DisplayName("Test isValidOrderParam chấp nhận null, chuỗi rỗng, ASC, DESC bất kể hoa thường")
    void testIsValidOrderParam() {
        assertTrue(employeeValidator.isValidOrderParam(null));
        assertTrue(employeeValidator.isValidOrderParam(""));
        assertTrue(employeeValidator.isValidOrderParam("   "));
        assertTrue(employeeValidator.isValidOrderParam("ASC"));
        assertTrue(employeeValidator.isValidOrderParam("asc"));
        assertTrue(employeeValidator.isValidOrderParam("DESC"));
        assertTrue(employeeValidator.isValidOrderParam("desc"));
        assertFalse(employeeValidator.isValidOrderParam("ascending"));
        assertFalse(employeeValidator.isValidOrderParam("123"));
    }

    private AddEmployeeRequest createValidAddRequest() {
        return AddEmployeeRequest.builder()
                .employeeLoginId("nguyenvana")
                .employeeLoginPassword("password123")
                .employeeName("Nguyễn Văn A")
                .employeeNameKana("ｱｲｳｴｵ")
                .employeeBirthDate("1995/05/20")
                .employeeEmail("a@luvina.net")
                .employeeTelephone("0987654321")
                .departmentId("1")
                .build();
    }

    @Test
    @DisplayName("Test validateAddEmployee thành công với request hợp lệ không có chứng chỉ")
    void testValidateAddEmployeeSuccessWithoutCertifications() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        DepartmentRepository mockDeptRepo = mock(DepartmentRepository.class);
        CertificationRepository mockCertRepo = mock(CertificationRepository.class);

        when(mockEmpRepo.existsByEmployeeLoginId("nguyenvana")).thenReturn(false);
        when(mockDeptRepo.existsById(1L)).thenReturn(true);

        AddEmployeeRequest request = createValidAddRequest();
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, mockCertRepo);

        assertNull(error);
    }

    @Test
    @DisplayName("Test validateAddEmployee thành công với request hợp lệ kèm chứng chỉ")
    void testValidateAddEmployeeSuccessWithCertifications() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        DepartmentRepository mockDeptRepo = mock(DepartmentRepository.class);
        CertificationRepository mockCertRepo = mock(CertificationRepository.class);

        when(mockEmpRepo.existsByEmployeeLoginId("nguyenvana")).thenReturn(false);
        when(mockDeptRepo.existsById(1L)).thenReturn(true);
        when(mockCertRepo.existsById(2L)).thenReturn(true);

        AddEmployeeRequest request = createValidAddRequest();
        request.setCertifications(List.of(
                CertificationItemRequest.builder()
                        .certificationId("2")
                        .startDate("2023/01/01")
                        .endDate("2024/01/01")
                        .score("150")
                        .build()
        ));

        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, mockCertRepo);
        assertNull(error);
    }

    @Test
    @DisplayName("Test validateAddEmployee lỗi loginId: rỗng (ER001), quá dài (ER006), ký tự sai/bắt đầu bằng số (ER019), trùng DB (ER003)")
    void testValidateAddEmployeeLoginIdErrors() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        AddEmployeeRequest request = createValidAddRequest();

        // ER001 khi rỗng
        request.setEmployeeLoginId("");
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER001, error.getCode());
        assertEquals(List.of(Constants.PARAM_ACCOUNT_NAME), error.getParams());

        // ER006 khi > 50 ký tự
        request.setEmployeeLoginId("a".repeat(51));
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER006, error.getCode());
        assertEquals(List.of(Constants.PARAM_ACCOUNT_NAME), error.getParams());

        // ER019 khi bắt đầu bằng số
        request.setEmployeeLoginId("1admin");
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER019, error.getCode());

        // ER019 khi chứa ký tự đặc biệt không hợp lệ
        request.setEmployeeLoginId("admin@xyz");
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER019, error.getCode());

        // ER003 khi đã tồn tại trong DB
        request.setEmployeeLoginId("admin");
        when(mockEmpRepo.existsByEmployeeLoginId("admin")).thenReturn(true);
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER003, error.getCode());
        assertEquals(List.of(Constants.PARAM_ACCOUNT_NAME), error.getParams());
    }

    @Test
    @DisplayName("Test validateAddEmployee lỗi name: rỗng (ER001), vượt quá 125 ký tự (ER006)")
    void testValidateAddEmployeeNameErrors() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        AddEmployeeRequest request = createValidAddRequest();

        request.setEmployeeName("");
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER001, error.getCode());
        assertEquals(List.of(Constants.PARAM_NAME), error.getParams());

        request.setEmployeeName("A".repeat(126));
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER006, error.getCode());
        assertEquals(List.of(Constants.PARAM_NAME), error.getParams());
    }

    @Test
    @DisplayName("Test validateAddEmployee lỗi nameKana: rỗng (ER001), vượt 125 (ER006), không phải Katakana (ER009)")
    void testValidateAddEmployeeNameKanaErrors() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        AddEmployeeRequest request = createValidAddRequest();

        request.setEmployeeNameKana("");
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER001, error.getCode());
        assertEquals(List.of(Constants.PARAM_KATAKANA_NAME), error.getParams());

        request.setEmployeeNameKana("ｱ".repeat(126));
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER006, error.getCode());

        // Ký tự kanji / latin -> ER009
        request.setEmployeeNameKana("Yamada");
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER009, error.getCode());
        assertEquals(List.of(Constants.PARAM_KATAKANA_NAME), error.getParams());
    }

    @Test
    @DisplayName("Test validateAddEmployee lỗi birthDate: rỗng (ER001), sai format (ER005), ngày không hợp lệ (ER011)")
    void testValidateAddEmployeeBirthDateErrors() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        AddEmployeeRequest request = createValidAddRequest();

        request.setEmployeeBirthDate("");
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER001, error.getCode());
        assertEquals(List.of(Constants.PARAM_BIRTHDAY), error.getParams());

        request.setEmployeeBirthDate("1995-05-20");
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER005, error.getCode());

        request.setEmployeeBirthDate("2023/02/29"); // Năm không nhuận
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER011, error.getCode());
    }

    @Test
    @DisplayName("Test validateDateField kiểm tra đầy đủ các trường hợp rỗng (ER001), sai format (ER005), ngày không hợp lệ (ER011), và thành công")
    void testValidateDateField() {
        // 1. null hoặc rỗng -> ER001
        MessageResponse errorNull = employeeValidator.validateDateField(null, Constants.PARAM_BIRTHDAY);
        assertNotNull(errorNull);
        assertEquals(Constants.ERROR_CODE_ER001, errorNull.getCode());
        assertEquals(List.of(Constants.PARAM_BIRTHDAY), errorNull.getParams());

        MessageResponse errorEmpty = employeeValidator.validateDateField("   ", Constants.PARAM_CERTIFICATION_START_DATE);
        assertNotNull(errorEmpty);
        assertEquals(Constants.ERROR_CODE_ER001, errorEmpty.getCode());
        assertEquals(List.of(Constants.PARAM_CERTIFICATION_START_DATE), errorEmpty.getParams());

        // 2. Sai format -> ER005
        MessageResponse errorFormat = employeeValidator.validateDateField("2024-01-01", Constants.PARAM_BIRTHDAY);
        assertNotNull(errorFormat);
        assertEquals(Constants.ERROR_CODE_ER005, errorFormat.getCode());
        assertEquals(List.of(Constants.PARAM_BIRTHDAY, Constants.DATE_FORMAT_YYYY_MM_DD), errorFormat.getParams());

        // 3. Ngày không hợp lệ trong lịch (ví dụ 30/02) -> ER011
        MessageResponse errorInvalidDate = employeeValidator.validateDateField("2024/02/30", Constants.PARAM_CERTIFICATION_END_DATE);
        assertNotNull(errorInvalidDate);
        assertEquals(Constants.ERROR_CODE_ER011, errorInvalidDate.getCode());
        assertEquals(List.of(Constants.PARAM_CERTIFICATION_END_DATE), errorInvalidDate.getParams());

        // 4. Hợp lệ -> null
        MessageResponse success = employeeValidator.validateDateField("2024/02/29", Constants.PARAM_BIRTHDAY); // 2024 là năm nhuận
        assertNull(success);
    }

    @Test
    @DisplayName("Test validateAddEmployee lỗi email và telephone (ER001, ER006, ER008)")
    void testValidateAddEmployeeEmailAndTelephoneErrors() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        AddEmployeeRequest request = createValidAddRequest();

        // Email rỗng
        request.setEmployeeEmail("");
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER001, error.getCode());
        assertEquals(List.of(Constants.PARAM_EMAIL), error.getParams());

        // Email > 125 ký tự
        request.setEmployeeEmail("a".repeat(126) + "@luvina.net");
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER006, error.getCode());

        // Telephone rỗng
        request = createValidAddRequest();
        request.setEmployeeTelephone("");
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER001, error.getCode());
        assertEquals(List.of(Constants.PARAM_TEL), error.getParams());

        // Telephone chứa ký tự non-1-byte
        request.setEmployeeTelephone("０９８７６５４３２１"); // fullwidth
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER008, error.getCode());
        assertEquals(List.of(Constants.PARAM_TEL), error.getParams());
    }

    @Test
    @DisplayName("Test validateAddEmployee lỗi password: rỗng (ER001), < 8 hoặc > 50 (ER007)")
    void testValidateAddEmployeePasswordErrors() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        AddEmployeeRequest request = createValidAddRequest();

        request.setEmployeeLoginPassword("");
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER001, error.getCode());
        assertEquals(List.of(Constants.PARAM_PASSWORD), error.getParams());

        request.setEmployeeLoginPassword("1234567"); // 7 ký tự
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER007, error.getCode());
        assertEquals(List.of(Constants.PARAM_PASSWORD, "8", "50"), error.getParams());

        request.setEmployeeLoginPassword("a".repeat(51)); // 51 ký tự
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, null, null);
        assertEquals(Constants.ERROR_CODE_ER007, error.getCode());
    }

    @Test
    @DisplayName("Test validateAddEmployee lỗi departmentId: không tồn tại (ER002), không phải số nguyên dương (ER018), không tồn tại trong DB (ER004)")
    void testValidateAddEmployeeDepartmentErrors() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        DepartmentRepository mockDeptRepo = mock(DepartmentRepository.class);
        AddEmployeeRequest request = createValidAddRequest();

        request.setDepartmentId(null);
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, null);
        assertEquals(Constants.ERROR_CODE_ER002, error.getCode());
        assertEquals(List.of(Constants.PARAM_GROUP), error.getParams());

        request.setDepartmentId("-1");
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, null);
        assertEquals(Constants.ERROR_CODE_ER018, error.getCode());

        request.setDepartmentId("abc");
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, null);
        assertEquals(Constants.ERROR_CODE_ER018, error.getCode());

        request.setDepartmentId("999");
        when(mockDeptRepo.existsById(999L)).thenReturn(false);
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, null);
        assertEquals(Constants.ERROR_CODE_ER004, error.getCode());
        assertEquals(List.of(Constants.PARAM_GROUP), error.getParams());
    }

    @Test
    @DisplayName("Test validateAddEmployee lỗi certifications: endDate <= startDate (ER012), điểm sai (ER018), chứng chỉ không tồn tại (ER004)")
    void testValidateAddEmployeeCertificationErrors() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        DepartmentRepository mockDeptRepo = mock(DepartmentRepository.class);
        CertificationRepository mockCertRepo = mock(CertificationRepository.class);

        when(mockEmpRepo.existsByEmployeeLoginId("nguyenvana")).thenReturn(false);
        when(mockDeptRepo.existsById(1L)).thenReturn(true);

        AddEmployeeRequest request = createValidAddRequest();

        // 1. startDate rỗng
        request.setCertifications(List.of(
                CertificationItemRequest.builder()
                        .certificationId("1")
                        .startDate("")
                        .endDate("2024/01/01")
                        .score("100")
                        .build()
        ));
        MessageResponse error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, mockCertRepo);
        assertEquals(Constants.ERROR_CODE_ER001, error.getCode());
        assertEquals(List.of(Constants.PARAM_CERTIFICATION_START_DATE), error.getParams());

        // 2. endDate <= startDate (ER012)
        request.setCertifications(List.of(
                CertificationItemRequest.builder()
                        .certificationId("1")
                        .startDate("2024/01/01")
                        .endDate("2023/01/01")
                        .score("100")
                        .build()
        ));
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, mockCertRepo);
        assertEquals(Constants.ERROR_CODE_ER012, error.getCode());

        // 3. Score không phải số (ER018)
        request.setCertifications(List.of(
                CertificationItemRequest.builder()
                        .certificationId("1")
                        .startDate("2023/01/01")
                        .endDate("2024/01/01")
                        .score("abc")
                        .build()
        ));
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, mockCertRepo);
        assertEquals(Constants.ERROR_CODE_ER018, error.getCode());
        assertEquals(List.of(Constants.PARAM_SCORE), error.getParams());

        // 4. certificationId không tồn tại trong DB (ER004)
        request.setCertifications(List.of(
                CertificationItemRequest.builder()
                        .certificationId("999")
                        .startDate("2023/01/01")
                        .endDate("2024/01/01")
                        .score("100")
                        .build()
        ));
        when(mockCertRepo.existsById(999L)).thenReturn(false);
        error = employeeValidator.validateAddEmployee(request, mockEmpRepo, mockDeptRepo, mockCertRepo);
        assertEquals(Constants.ERROR_CODE_ER004, error.getCode());
        assertEquals(List.of(Constants.PARAM_CERTIFICATION), error.getParams());
    }

    @Test
    @DisplayName("Test validateEmployeeId khi ID null hoặc <= 0 (ER001) và không tồn tại trong DB (ER013)")
    void testValidateEmployeeId() {
        EmployeeEntityRepository mockEmpRepo =
                mock(EmployeeEntityRepository.class);

        // 1. employeeId null -> ER001
        MessageResponse errorNull = employeeValidator.validateEmployeeId(null, mockEmpRepo);
        assertNotNull(errorNull);
        assertEquals(Constants.ERROR_CODE_ER001, errorNull.getCode());
        assertEquals(List.of(Constants.PARAM_ID), errorNull.getParams());

        // 2. employeeId <= 0 -> ER001
        MessageResponse errorNegative = employeeValidator.validateEmployeeId(0L, mockEmpRepo);
        assertNotNull(errorNegative);
        assertEquals(Constants.ERROR_CODE_ER001, errorNegative.getCode());
        assertEquals(List.of(Constants.PARAM_ID), errorNegative.getParams());

        // 3. employeeId không tồn tại trong DB -> ER013
        when(mockEmpRepo.existsById(999L)).thenReturn(false);
        MessageResponse errorNotFound = employeeValidator.validateEmployeeId(999L, mockEmpRepo);
        assertNotNull(errorNotFound);
        assertEquals(Constants.ERROR_CODE_ER013, errorNotFound.getCode());
        assertEquals(List.of(Constants.PARAM_ID), errorNotFound.getParams());

        // 4. employeeId hợp lệ và tồn tại -> null
        when(mockEmpRepo.existsById(1L)).thenReturn(true);
        MessageResponse valid = employeeValidator.validateEmployeeId(1L, mockEmpRepo);
        assertNull(valid);
    }

    @Test
    @DisplayName("Test validateEmployeeIdForDelete khi ID null/<=0 (ER001), không tồn tại hoặc role ADMIN (ER014), và hợp lệ role USER")
    void testValidateEmployeeIdForDelete() {
        EmployeeEntityRepository mockEmpRepo =
                mock(EmployeeEntityRepository.class);

        // 1. employeeId null -> ER001
        MessageResponse errorNull = employeeValidator.validateEmployeeIdForDelete(null, mockEmpRepo);
        assertNotNull(errorNull);
        assertEquals(Constants.ERROR_CODE_ER001, errorNull.getCode());
        assertEquals(List.of(Constants.PARAM_ID), errorNull.getParams());

        // 2. employeeId <= 0 -> ER001
        MessageResponse errorNegative = employeeValidator.validateEmployeeIdForDelete(0L, mockEmpRepo);
        assertNotNull(errorNegative);
        assertEquals(Constants.ERROR_CODE_ER001, errorNegative.getCode());
        assertEquals(List.of(Constants.PARAM_ID), errorNegative.getParams());

        // 3. employeeId không tồn tại trong DB -> ER014
        when(mockEmpRepo.findById(999L)).thenReturn(Optional.empty());
        MessageResponse errorNotFound = employeeValidator.validateEmployeeIdForDelete(999L, mockEmpRepo);
        assertNotNull(errorNotFound);
        assertEquals(Constants.ERROR_CODE_ER014, errorNotFound.getCode());
        assertEquals(List.of(Constants.PARAM_ID), errorNotFound.getParams());

        // 4. employeeId tồn tại nhưng có role là ADMIN -> ER014 (không được xóa role Admin)
        EmployeeEntity adminEntity = new EmployeeEntity();
        adminEntity.setEmployeeId(2L);
        adminEntity.setEmployeeRole(Constants.ROLE_ADMIN);
        when(mockEmpRepo.findById(2L)).thenReturn(Optional.of(adminEntity));

        MessageResponse errorAdmin = employeeValidator.validateEmployeeIdForDelete(2L, mockEmpRepo);
        assertNotNull(errorAdmin);
        assertEquals(Constants.ERROR_CODE_ER014, errorAdmin.getCode());
        assertEquals(List.of(Constants.PARAM_ID), errorAdmin.getParams());

        // 5. employeeId hợp lệ và tồn tại với role USER -> null
        EmployeeEntity userEntity = new EmployeeEntity();
        userEntity.setEmployeeId(1L);
        userEntity.setEmployeeRole("USER");
        when(mockEmpRepo.findById(1L)).thenReturn(Optional.of(userEntity));

        MessageResponse valid = employeeValidator.validateEmployeeIdForDelete(1L, mockEmpRepo);
        assertNull(valid);
    }

    @Test
    @DisplayName("Test validateUpdateEmployee thành công khi dữ liệu hợp lệ (có đổi password, có cert)")
    void testValidateUpdateEmployeeSuccess() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        DepartmentRepository mockDeptRepo = mock(DepartmentRepository.class);
        CertificationRepository mockCertRepo = mock(CertificationRepository.class);

        when(mockEmpRepo.existsById(1L)).thenReturn(true);
        when(mockEmpRepo.findByEmployeeLoginId("valid_user")).thenReturn(Optional.empty());
        when(mockDeptRepo.existsById(1L)).thenReturn(true);
        when(mockCertRepo.existsById(2L)).thenReturn(true);

        CertificationItemRequest cert = CertificationItemRequest.builder()
                .certificationId("2")
                .startDate("2023/01/01")
                .endDate("2024/01/01")
                .score("950")
                .build();

        UpdateEmployeeRequest request = UpdateEmployeeRequest.builder()
                .employeeId(1L)
                .employeeLoginId("valid_user")
                .employeeLoginPassword("newpassword123")
                .employeeName("Nguyễn Văn B")
                .employeeNameKana("ｱｲｳｴｵ")
                .employeeBirthDate("1990/05/15")
                .employeeEmail("vanb@luvina.net")
                .employeeTelephone("0987654321")
                .departmentId("1")
                .certifications(List.of(cert))
                .build();

        MessageResponse error = employeeValidator.validateUpdateEmployee(request, mockEmpRepo, mockDeptRepo, mockCertRepo);
        assertNull(error);
    }

    @Test
    @DisplayName("Test validateUpdateEmployee thành công khi không đổi password và không có cert")
    void testValidateUpdateEmployeeSuccessWithoutPasswordAndCert() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        DepartmentRepository mockDeptRepo = mock(DepartmentRepository.class);
        CertificationRepository mockCertRepo = mock(CertificationRepository.class);

        when(mockEmpRepo.existsById(1L)).thenReturn(true);
        // LoginId giữ nguyên của chính mình
        EmployeeEntity currentEmp = new EmployeeEntity();
        currentEmp.setEmployeeId(1L);
        currentEmp.setEmployeeLoginId("my_login_id");
        when(mockEmpRepo.findByEmployeeLoginId("my_login_id")).thenReturn(Optional.of(currentEmp));
        when(mockDeptRepo.existsById(1L)).thenReturn(true);

        UpdateEmployeeRequest request = UpdateEmployeeRequest.builder()
                .employeeId(1L)
                .employeeLoginId("my_login_id")
                .employeeLoginPassword("") // password rỗng
                .employeeName("Nguyễn Văn B")
                .employeeNameKana("ｱｲｳｴｵ")
                .employeeBirthDate("1990/05/15")
                .employeeEmail("vanb@luvina.net")
                .employeeTelephone("0987654321")
                .departmentId("1")
                .certifications(null) // không có cert
                .build();

        MessageResponse error = employeeValidator.validateUpdateEmployee(request, mockEmpRepo, mockDeptRepo, mockCertRepo);
        assertNull(error);
    }

    @Test
    @DisplayName("Test validateUpdateEmployee lỗi employeeId không tồn tại (ER013)")
    void testValidateUpdateEmployeeNotFoundId() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        when(mockEmpRepo.existsById(999L)).thenReturn(false);

        UpdateEmployeeRequest request = UpdateEmployeeRequest.builder()
                .employeeId(999L)
                .employeeLoginId("user1")
                .build();

        MessageResponse error = employeeValidator.validateUpdateEmployee(request, mockEmpRepo, null, null);
        assertNotNull(error);
        assertEquals(Constants.ERROR_CODE_ER013, error.getCode());
        assertEquals(List.of(Constants.PARAM_ID), error.getParams());
    }

    @Test
    @DisplayName("Test validateUpdateEmployee lỗi trùng loginId với nhân viên khác (ER003)")
    void testValidateUpdateEmployeeDuplicateLoginIdWithOther() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        when(mockEmpRepo.existsById(1L)).thenReturn(true);

        EmployeeEntity otherEmp = new EmployeeEntity();
        otherEmp.setEmployeeId(2L); // ID khác
        otherEmp.setEmployeeLoginId("existing_user");
        when(mockEmpRepo.findByEmployeeLoginId("existing_user")).thenReturn(Optional.of(otherEmp));

        UpdateEmployeeRequest request = UpdateEmployeeRequest.builder()
                .employeeId(1L)
                .employeeLoginId("existing_user")
                .build();

        MessageResponse error = employeeValidator.validateUpdateEmployee(request, mockEmpRepo, null, null);
        assertNotNull(error);
        assertEquals(Constants.ERROR_CODE_ER003, error.getCode());
        assertEquals(List.of(Constants.PARAM_ACCOUNT_NAME), error.getParams());
    }

    @Test
    @DisplayName("Test validateUpdateEmployee lỗi password ngắn hơn 8 ký tự khi update (ER007)")
    void testValidateUpdateEmployeePasswordTooShort() {
        EmployeeEntityRepository mockEmpRepo = mock(EmployeeEntityRepository.class);
        DepartmentRepository mockDeptRepo = mock(DepartmentRepository.class);

        when(mockEmpRepo.existsById(1L)).thenReturn(true);
        when(mockEmpRepo.findByEmployeeLoginId("valid_user")).thenReturn(Optional.empty());
        when(mockDeptRepo.existsById(1L)).thenReturn(true);

        UpdateEmployeeRequest request = UpdateEmployeeRequest.builder()
                .employeeId(1L)
                .employeeLoginId("valid_user")
                .employeeLoginPassword("short") // ngắn hơn 8 ký tự
                .employeeName("Nguyễn Văn B")
                .employeeNameKana("ｱｲｳｴｵ")
                .employeeBirthDate("1990/05/15")
                .employeeEmail("vanb@luvina.net")
                .employeeTelephone("0987654321")
                .departmentId("1")
                .build();

        MessageResponse error = employeeValidator.validateUpdateEmployee(request, mockEmpRepo, mockDeptRepo, null);
        assertNotNull(error);
        assertEquals(Constants.ERROR_CODE_ER007, error.getCode());
        assertEquals(List.of(Constants.PARAM_PASSWORD, "8", "50"), error.getParams());
    }
}

