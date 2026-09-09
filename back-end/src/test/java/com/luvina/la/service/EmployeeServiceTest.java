package com.luvina.la.service;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeServiceTest.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeListDTO;
import com.luvina.la.exception.CustomValidationException;
import com.luvina.la.repository.EmployeeNativeRepository;
import com.luvina.la.service.impl.EmployeeServiceImpl;
import com.luvina.la.validator.EmployeeValidator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.luvina.la.entity.EmployeeCertificationEntity;
import com.luvina.la.entity.EmployeeEntity;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.CertificationItemRequest;
import com.luvina.la.payload.response.AddEmployeeResponse;
import com.luvina.la.payload.response.MessageResponse;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.EmployeeEntityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Unit test cho EmployeeService sử dụng EmployeeDTO và EmployeeListDTO.
 *
 * @author Phạm Văn Minh
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeNativeRepository employeeNativeRepository;

    @Mock
    private EmployeeEntityRepository employeeEntityRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private CertificationRepository certificationRepository;

    @Mock
    private EmployeeCertificationRepository employeeCertificationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @org.mockito.Spy
    private EmployeeValidator employeeValidator = new EmployeeValidator();

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl(
                employeeNativeRepository,
                employeeValidator,
                employeeEntityRepository,
                departmentRepository,
                certificationRepository,
                employeeCertificationRepository,
                passwordEncoder
        );
    }

    @Test
    @DisplayName("Test getEmployees thành công với tham số mặc định")
    void testGetEmployeesDefault() {
        EmployeeDTO empDto = EmployeeDTO.builder()
                .employeeId(1L)
                .employeeName("Nguyen Van A")
                .employeeBirthDate(LocalDate.of(1990, 1, 1))
                .departmentName("Phòng IT")
                .employeeEmail("a@luvina.net")
                .employeeTelephone("0123456789")
                .certificationName("N1")
                .endDate(LocalDate.of(2025, 12, 31))
                .score(new BigDecimal("150"))
                .build();

        when(employeeNativeRepository.findEmployees(isNull(), isNull(), eq(5), eq(0), isNull(), isNull(), isNull(), isNull()))
                .thenReturn(List.of(empDto));
        when(employeeNativeRepository.countEmployees(isNull(), isNull()))
                .thenReturn(1L);

        EmployeeListDTO result = employeeService.getEmployees(
                null, null, null, null, null, null, null
        );

        assertNotNull(result);
        assertEquals(1L, result.getTotalRecords());
        assertEquals(1, result.getEmployees().size());
        assertEquals("Nguyen Van A", result.getEmployees().get(0).getEmployeeName());
        assertEquals("Phòng IT", result.getEmployees().get(0).getDepartmentName());
        assertEquals("N1", result.getEmployees().get(0).getCertificationName());

        verify(employeeNativeRepository).findEmployees(isNull(), isNull(), eq(5), eq(0), isNull(), isNull(), isNull(), isNull());
        verify(employeeNativeRepository).countEmployees(isNull(), isNull());
    }

    @Test
    @DisplayName("Test getEmployees thành công với bộ lọc và phân trang tùy chỉnh")
    void testGetEmployeesWithFilter() {
        EmployeeDTO empDto = EmployeeDTO.builder()
                .employeeId(2L)
                .employeeName("Van A")
                .employeeBirthDate(LocalDate.of(1995, 5, 20))
                .departmentName("Phòng Dev")
                .employeeEmail("vana@luvina.net")
                .employeeTelephone("0987654321")
                .build();

        when(employeeNativeRepository.countEmployees(eq("Van A"), eq(2L)))
                .thenReturn(1L);
        when(employeeNativeRepository.findEmployees(eq("Van A"), eq(2L), eq(10), eq(20), eq("DESC"), eq("ASC"), eq("DESC"), isNull()))
                .thenReturn(List.of(empDto));

        EmployeeListDTO result = employeeService.getEmployees(
                "  Van A  ", "2", "DESC", "ASC", "DESC", "20", "10"
        );

        assertNotNull(result);
        assertEquals(1L, result.getTotalRecords());
        assertEquals(1, result.getEmployees().size());
        assertEquals("Van A", result.getEmployees().get(0).getEmployeeName());
        assertEquals("Phòng Dev", result.getEmployees().get(0).getDepartmentName());

        verify(employeeNativeRepository).countEmployees(eq("Van A"), eq(2L));
        verify(employeeNativeRepository).findEmployees(eq("Van A"), eq(2L), eq(10), eq(20), eq("DESC"), eq("ASC"), eq("DESC"), isNull());
    }

    @Test
    @DisplayName("Test getEmployees trả về danh sách rỗng khi không có bản ghi nào")
    void testGetEmployeesNoRecords() {
        when(employeeNativeRepository.countEmployees(eq("Nonexistent"), eq(1L)))
                .thenReturn(0L);

        EmployeeListDTO result = employeeService.getEmployees(
                "Nonexistent", "1", null, null, null, "0", "5"
        );

        assertNotNull(result);
        assertEquals(0L, result.getTotalRecords());
        assertTrue(result.getEmployees().isEmpty());

        verify(employeeNativeRepository).countEmployees(eq("Nonexistent"), eq(1L));
    }

    @Test
    @DisplayName("Test getEmployees ném CustomValidationException khi tham số ord không phải ASC hoặc DESC")
    void testGetEmployeesInvalidOrder() {
        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> {
            employeeService.getEmployees(null, null, "INVALID", null, null, "0", "5");
        });

        assertNotNull(ex.getMessageResponse());
        assertEquals("ER021", ex.getMessageResponse().getCode());
        assertTrue(ex.getMessageResponse().getParams().isEmpty());
    }

    @Test
    @DisplayName("Test getEmployees ném CustomValidationException khi offset không phải số nguyên không âm")
    void testGetEmployeesInvalidOffset() {
        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> {
            employeeService.getEmployees(null, null, "ASC", null, null, "-1", "5");
        });

        assertNotNull(ex.getMessageResponse());
        assertEquals("ER018", ex.getMessageResponse().getCode());
        assertEquals(List.of("オフセット"), ex.getMessageResponse().getParams());
    }

    @Test
    @DisplayName("Test getEmployees ném CustomValidationException khi limit không phải số nguyên dương")
    void testGetEmployeesInvalidLimit() {
        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> {
            employeeService.getEmployees(null, null, "ASC", null, null, "0", "0");
        });

        assertNotNull(ex.getMessageResponse());
        assertEquals("ER018", ex.getMessageResponse().getCode());
        assertEquals(List.of("リミット"), ex.getMessageResponse().getParams());
    }

    @Test
    @DisplayName("Test getEmployees với sortBy certificationNameOrder")
    void testGetEmployeesWithSortByCertification() {
        when(employeeNativeRepository.countEmployees(isNull(), isNull()))
                .thenReturn(1L);
        when(employeeNativeRepository.findEmployees(isNull(), isNull(), eq(5), eq(0), eq("ASC"), eq("ASC"), eq("DESC"), eq("certificationNameOrder")))
                .thenReturn(Collections.emptyList());

        EmployeeListDTO result = employeeService.getEmployees(
                null, null, "ASC", "ASC", "DESC", "0", "5", "certificationNameOrder"
        );

        assertNotNull(result);
        assertEquals(1L, result.getTotalRecords());
        verify(employeeNativeRepository).findEmployees(isNull(), isNull(), eq(5), eq(0), eq("ASC"), eq("ASC"), eq("DESC"), eq("certificationNameOrder"));
    }

    @Test
    @DisplayName("Test getEmployees với sortBy endDateOrder")
    void testGetEmployeesWithSortByEndDate() {
        when(employeeNativeRepository.countEmployees(isNull(), isNull()))
                .thenReturn(1L);
        when(employeeNativeRepository.findEmployees(isNull(), isNull(), eq(5), eq(0), eq("ASC"), eq("ASC"), eq("DESC"), eq("endDateOrder")))
                .thenReturn(Collections.emptyList());

        EmployeeListDTO result = employeeService.getEmployees(
                null, null, "ASC", "ASC", "DESC", "0", "5", "endDateOrder"
        );

        assertNotNull(result);
        assertEquals(1L, result.getTotalRecords());
        verify(employeeNativeRepository).findEmployees(isNull(), isNull(), eq(5), eq(0), eq("ASC"), eq("ASC"), eq("DESC"), eq("endDateOrder"));
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
    @DisplayName("Test addEmployee thành công khi không có chứng chỉ")
    void testAddEmployeeSuccessWithoutCertifications() {
        AddEmployeeRequest request = createValidAddRequest();

        when(employeeEntityRepository.existsByEmployeeLoginId("nguyenvana")).thenReturn(false);
        when(departmentRepository.existsById(1L)).thenReturn(true);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        EmployeeEntity savedEntity = new EmployeeEntity();
        savedEntity.setEmployeeId(10L);
        savedEntity.setDepartmentId(1L);
        savedEntity.setEmployeeName("Nguyễn Văn A");
        when(employeeEntityRepository.save(any(EmployeeEntity.class))).thenReturn(savedEntity);

        EmployeeDTO response = employeeService.addEmployee(request);

        assertNotNull(response);
        assertEquals(10L, response.getEmployeeId());
        assertEquals("Nguyễn Văn A", response.getEmployeeName());

        verify(employeeEntityRepository).save(argThat(entity ->
                entity.getDepartmentId().equals(1L)
                        && "Nguyễn Văn A".equals(entity.getEmployeeName())
                        && "encodedPassword".equals(entity.getEmployeeLoginPassword())
                        && "USER".equals(entity.getEmployeeRole())
        ));
    }

    @Test
    @DisplayName("Test addEmployee thành công kèm chứng chỉ tiếng Nhật")
    void testAddEmployeeSuccessWithCertifications() {
        AddEmployeeRequest request = createValidAddRequest();
        request.setCertifications(List.of(
                CertificationItemRequest.builder()
                        .certificationId("1")
                        .startDate("2023/01/01")
                        .endDate("2024/01/01")
                        .score("150")
                        .build()
        ));

        when(employeeEntityRepository.existsByEmployeeLoginId("nguyenvana")).thenReturn(false);
        when(departmentRepository.existsById(1L)).thenReturn(true);
        when(certificationRepository.existsById(1L)).thenReturn(true);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        EmployeeEntity savedEntity = new EmployeeEntity();
        savedEntity.setEmployeeId(15L);
        when(employeeEntityRepository.save(any(EmployeeEntity.class))).thenReturn(savedEntity);

        EmployeeDTO response = employeeService.addEmployee(request);

        assertNotNull(response);
        assertEquals(15L, response.getEmployeeId());

        verify(employeeCertificationRepository).save(argThat(certEntity ->
                certEntity.getEmployeeId().equals(15L)
                        && certEntity.getCertificationId().equals(1L)
                        && certEntity.getStartDate().equals(LocalDate.of(2023, 1, 1))
                        && certEntity.getEndDate().equals(LocalDate.of(2024, 1, 1))
                        && certEntity.getScore().compareTo(new BigDecimal("150")) == 0
        ));
    }

    @Test
    @DisplayName("Test addEmployee ném CustomValidationException khi dữ liệu không hợp lệ")
    void testAddEmployeeValidationFailureThrowsCustomValidationException() {
        AddEmployeeRequest request = createValidAddRequest();
        request.setEmployeeLoginId(""); // Không hợp lệ

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> {
            employeeService.addEmployee(request);
        });

        assertNotNull(ex.getMessageResponse());
        assertEquals("ER001", ex.getMessageResponse().getCode());
        assertEquals(List.of("アカウント名"), ex.getMessageResponse().getParams());
    }

    @Test
    @DisplayName("Test getEmployeeById thành công trả về đầy đủ EmployeeDetailDTO và certifications")
    void testGetEmployeeByIdSuccess() {
        Long empId = 1L;

        when(employeeEntityRepository.existsById(empId)).thenReturn(true);

        List<Object[]> rows = new java.util.ArrayList<>();
        rows.add(new Object[] {
                empId,
                2L,
                "Phòng Phát Triển 1",
                "Nguyễn Văn A",
                "名カナ",
                LocalDate.of(1990, 1, 1),
                "vana@luvina.net",
                "0123456789",
                "vana",
                "USER",
                1L,
                "Trình độ tiếng Nhật cấp 1",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2024, 1, 1),
                new BigDecimal("180")
        });
        when(employeeEntityRepository.findEmployeeDetailWithCertifications(empId)).thenReturn(rows);

        com.luvina.la.dto.EmployeeDetailDTO result = employeeService.getEmployeeById(empId);

        assertNotNull(result);
        assertEquals(empId, result.getEmployeeId());
        assertEquals("Nguyễn Văn A", result.getEmployeeName());
        assertEquals("Phòng Phát Triển 1", result.getDepartmentName());
        assertEquals(1, result.getCertifications().size());
        assertEquals("Trình độ tiếng Nhật cấp 1", result.getCertifications().get(0).getCertificationName());
    }

    @Test
    @DisplayName("Test getEmployeeById thất bại khi ID không tồn tại ném CustomValidationException ER013")
    void testGetEmployeeByIdNotFoundThrowsCustomValidationException() {
        Long empId = 999L;
        when(employeeEntityRepository.existsById(empId)).thenReturn(false);

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> {
            employeeService.getEmployeeById(empId);
        });

        assertNotNull(ex.getMessageResponse());
        assertEquals("ER013", ex.getMessageResponse().getCode());
        assertEquals(List.of("ＩＤ"), ex.getMessageResponse().getParams());
    }

    @Test
    @DisplayName("Test deleteEmployee thành công xóa trong cả employees và employees_certifications")
    void testDeleteEmployeeSuccess() {
        Long empId = 1L;
        when(employeeEntityRepository.existsById(empId)).thenReturn(true);

        employeeService.deleteEmployee(empId);

        verify(employeeCertificationRepository).deleteByEmployeeId(empId);
        verify(employeeEntityRepository).deleteById(empId);
    }

    @Test
    @DisplayName("Test deleteEmployee thất bại khi ID không tồn tại ném CustomValidationException ER014")
    void testDeleteEmployeeNotFoundThrowsCustomValidationException() {
        Long empId = 999L;
        when(employeeEntityRepository.existsById(empId)).thenReturn(false);

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> {
            employeeService.deleteEmployee(empId);
        });

        assertNotNull(ex.getMessageResponse());
        assertEquals("ER014", ex.getMessageResponse().getCode());
        assertEquals(List.of("ＩＤ"), ex.getMessageResponse().getParams());
        assertEquals(empId, ex.getEmployeeId());
    }

    @Test
    @DisplayName("Test deleteEmployee gặp lỗi khi xóa trong CSDL ném CustomValidationException ER015")
    void testDeleteEmployeeDatabaseErrorThrowsCustomValidationExceptionER015() {
        Long empId = 1L;
        when(employeeEntityRepository.existsById(empId)).thenReturn(true);
        org.mockito.Mockito.doThrow(new RuntimeException("DB Connection Error"))
                .when(employeeEntityRepository).deleteById(empId);

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> {
            employeeService.deleteEmployee(empId);
        });

        assertNotNull(ex.getMessageResponse());
        assertEquals("ER015", ex.getMessageResponse().getCode());
        assertEquals(empId, ex.getEmployeeId());
    }
}
