package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeControllerTest.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeListDTO;
import com.luvina.la.exception.CustomValidationException;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.payload.response.ListEmployeesResponse;
import com.luvina.la.payload.response.MessageResponse;
import com.luvina.la.service.EmployeeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test cho EmployeeController.
 *
 * @author Phạm Văn Minh
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    private EmployeeMapper employeeMapper;
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        employeeController = new EmployeeController(employeeService, employeeMapper);
    }

    @Test
    @DisplayName("Test getEmployees with standard parameters according to API design")
    void testGetEmployeesWithLimitAndOffset() {
        EmployeeListDTO mockResult = EmployeeListDTO.builder()
                .totalRecords(10L)
                .employees(Collections.emptyList())
                .build();

        when(employeeService.getEmployees(
                eq("John"), eq("1"), eq("ASC"), eq("ASC"), eq("ASC"), eq("5"), eq("10"), isNull()
        )).thenReturn(mockResult);

        ListEmployeesResponse response = employeeController.getEmployees(
                "John", "1", "ASC", "ASC", "ASC", "5", "10"
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(10L, response.getTotalRecords());

        verify(employeeService).getEmployees(
                eq("John"), eq("1"), eq("ASC"), eq("ASC"), eq("ASC"), eq("5"), eq("10"), isNull()
        );
    }

    @Test
    @DisplayName("Test getEmployees with optional null parameters according to API design")
    void testGetEmployeesWithNullParameters() {
        EmployeeListDTO mockResult = EmployeeListDTO.builder()
                .totalRecords(25L)
                .employees(Collections.emptyList())
                .build();

        when(employeeService.getEmployees(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull()
        )).thenReturn(mockResult);

        ListEmployeesResponse response = employeeController.getEmployees(
                null, null, null, null, null, null, null
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(25L, response.getTotalRecords());

        verify(employeeService).getEmployees(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull()
        );
    }

    @Test
    @DisplayName("Test getEmployees with HttpServletRequest prioritizing ord_certification_name")
    void testGetEmployeesWithQueryStringPriorityCert() {
        EmployeeListDTO mockResult = EmployeeListDTO.builder()
                .totalRecords(5L)
                .employees(Collections.emptyList())
                .build();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("ord_certification_name=DESC&ord_employee_name=ASC&ord_end_date=ASC");

        when(employeeService.getEmployees(
                isNull(), isNull(), eq("ASC"), eq("DESC"), eq("ASC"), isNull(), isNull(), eq("certificationNameOrder")
        )).thenReturn(mockResult);

        ListEmployeesResponse response = employeeController.getEmployees(
                null, null, "ASC", "DESC", "ASC", null, null, request
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(5L, response.getTotalRecords());
        verify(employeeService).getEmployees(
                isNull(), isNull(), eq("ASC"), eq("DESC"), eq("ASC"), isNull(), isNull(), eq("certificationNameOrder")
        );
    }

    @Test
    @DisplayName("Test getEmployees handles CustomValidationException returning error response")
    void testGetEmployeesValidationException() {
        when(employeeService.getEmployees(
                isNull(), isNull(), eq("INVALID"), isNull(), isNull(), isNull(), isNull(), isNull()
        )).thenThrow(new CustomValidationException(new MessageResponse("ER021", new ArrayList<>())));

        ListEmployeesResponse response = employeeController.getEmployees(
                null, null, "INVALID", null, null, null, null
        );

        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertNotNull(response.getMessage());
        assertEquals("ER021", response.getMessage().getCode());
    }
}
