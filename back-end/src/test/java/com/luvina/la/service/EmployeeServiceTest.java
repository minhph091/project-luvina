package com.luvina.la.service;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeServiceTest.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.payload.response.ListEmployeesResponse;
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
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test cho EmployeeService sử dụng EmployeeDTO và EmployeeMapper.
 *
 * @author Phạm Văn Minh
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeNativeRepository employeeNativeRepository;

    private EmployeeValidator employeeValidator;
    private EmployeeMapper employeeMapper;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeValidator = new EmployeeValidator();
        employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        employeeService = new EmployeeServiceImpl(employeeNativeRepository, employeeValidator, employeeMapper);
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

        ListEmployeesResponse response = employeeService.getEmployees(
                null, null, null, null, null, null, null
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(1L, response.getTotalRecords());
        assertEquals(1, response.getEmployees().size());
        assertEquals("Nguyen Van A", response.getEmployees().get(0).getEmployeeName());
        assertEquals("Phòng IT", response.getEmployees().get(0).getDepartmentName());
        assertEquals("N1", response.getEmployees().get(0).getCertificationName());

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

        ListEmployeesResponse response = employeeService.getEmployees(
                "  Van A  ", "2", "DESC", "ASC", "DESC", "20", "10"
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(1L, response.getTotalRecords());
        assertEquals(1, response.getEmployees().size());
        assertEquals("Van A", response.getEmployees().get(0).getEmployeeName());
        assertEquals("Phòng Dev", response.getEmployees().get(0).getDepartmentName());

        verify(employeeNativeRepository).countEmployees(eq("Van A"), eq(2L));
        verify(employeeNativeRepository).findEmployees(eq("Van A"), eq(2L), eq(10), eq(20), eq("DESC"), eq("ASC"), eq("DESC"), isNull());
    }

    @Test
    @DisplayName("Test getEmployees trả về danh sách rỗng khi không có bản ghi nào")
    void testGetEmployeesNoRecords() {
        when(employeeNativeRepository.countEmployees(eq("Nonexistent"), eq(1L)))
                .thenReturn(0L);

        ListEmployeesResponse response = employeeService.getEmployees(
                "Nonexistent", "1", null, null, null, "0", "5"
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(0L, response.getTotalRecords());
        assertTrue(response.getEmployees().isEmpty());

        verify(employeeNativeRepository).countEmployees(eq("Nonexistent"), eq(1L));
    }

    @Test
    @DisplayName("Test getEmployees lỗi ER021 khi tham số ord không phải ASC hoặc DESC")
    void testGetEmployeesInvalidOrder() {
        ListEmployeesResponse response = employeeService.getEmployees(
                null, null, "INVALID", null, null, "0", "5"
        );

        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertNotNull(response.getMessage());
        assertEquals("ER021", response.getMessage().getCode());
        assertTrue(response.getMessage().getParams().isEmpty());
    }

    @Test
    @DisplayName("Test getEmployees lỗi ER018 khi offset không phải số nguyên không âm")
    void testGetEmployeesInvalidOffset() {
        ListEmployeesResponse response = employeeService.getEmployees(
                null, null, "ASC", null, null, "-1", "5"
        );

        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertNotNull(response.getMessage());
        assertEquals("ER018", response.getMessage().getCode());
        assertEquals(List.of("オフセット"), response.getMessage().getParams());
    }

    @Test
    @DisplayName("Test getEmployees lỗi ER018 khi limit không phải số nguyên dương")
    void testGetEmployeesInvalidLimit() {
        ListEmployeesResponse response = employeeService.getEmployees(
                null, null, "ASC", null, null, "0", "0"
        );

        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertNotNull(response.getMessage());
        assertEquals("ER018", response.getMessage().getCode());
        assertEquals(List.of("リミット"), response.getMessage().getParams());
    }

    @Test
    @DisplayName("Test getEmployees với sortBy certificationNameOrder")
    void testGetEmployeesWithSortByCertification() {
        when(employeeNativeRepository.countEmployees(isNull(), isNull()))
                .thenReturn(1L);
        when(employeeNativeRepository.findEmployees(isNull(), isNull(), eq(5), eq(0), eq("ASC"), eq("ASC"), eq("DESC"), eq("certificationNameOrder")))
                .thenReturn(Collections.emptyList());

        ListEmployeesResponse response = employeeService.getEmployees(
                null, null, "ASC", "ASC", "DESC", "0", "5", "certificationNameOrder"
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        verify(employeeNativeRepository).findEmployees(isNull(), isNull(), eq(5), eq(0), eq("ASC"), eq("ASC"), eq("DESC"), eq("certificationNameOrder"));
    }

    @Test
    @DisplayName("Test getEmployees với sortBy endDateOrder")
    void testGetEmployeesWithSortByEndDate() {
        when(employeeNativeRepository.countEmployees(isNull(), isNull()))
                .thenReturn(1L);
        when(employeeNativeRepository.findEmployees(isNull(), isNull(), eq(5), eq(0), eq("ASC"), eq("ASC"), eq("DESC"), eq("endDateOrder")))
                .thenReturn(Collections.emptyList());

        ListEmployeesResponse response = employeeService.getEmployees(
                null, null, "ASC", "ASC", "DESC", "0", "5", "endDateOrder"
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        verify(employeeNativeRepository).findEmployees(isNull(), isNull(), eq(5), eq(0), eq("ASC"), eq("ASC"), eq("DESC"), eq("endDateOrder"));
    }
}
