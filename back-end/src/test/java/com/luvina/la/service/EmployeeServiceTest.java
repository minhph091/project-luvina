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

/**
 * Unit test cho EmployeeService sử dụng EmployeeDTO và EmployeeListDTO.
 *
 * @author Phạm Văn Minh
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeNativeRepository employeeNativeRepository;

    private EmployeeValidator employeeValidator;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeValidator = new EmployeeValidator();
        employeeService = new EmployeeServiceImpl(employeeNativeRepository, employeeValidator);
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
}
