package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeControllerTest.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.payload.response.ListEmployeesResponse;
import com.luvina.la.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

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

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    @DisplayName("Test getEmployees with standard parameters according to API design")
    void testGetEmployeesWithLimitAndOffset() {
        ListEmployeesResponse mockResponse = new ListEmployeesResponse();
        mockResponse.setCode(200);
        mockResponse.setTotalRecords(10L);
        mockResponse.setEmployees(Collections.emptyList());

        when(employeeService.getEmployees(
                eq("John"), eq("1"), eq("ASC"), eq("ASC"), eq("ASC"), eq("5"), eq("10"), isNull()
        )).thenReturn(mockResponse);

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
        ListEmployeesResponse mockResponse = new ListEmployeesResponse();
        mockResponse.setCode(200);
        mockResponse.setTotalRecords(25L);
        mockResponse.setEmployees(Collections.emptyList());

        when(employeeService.getEmployees(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull()
        )).thenReturn(mockResponse);

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
        ListEmployeesResponse mockResponse = new ListEmployeesResponse();
        mockResponse.setCode(200);
        mockResponse.setTotalRecords(5L);
        mockResponse.setEmployees(Collections.emptyList());

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("ord_certification_name=DESC&ord_employee_name=ASC&ord_end_date=ASC");

        when(employeeService.getEmployees(
                isNull(), isNull(), eq("ASC"), eq("DESC"), eq("ASC"), isNull(), isNull(), eq("certificationNameOrder")
        )).thenReturn(mockResponse);

        ListEmployeesResponse response = employeeController.getEmployees(
                null, null, "ASC", "DESC", "ASC", null, null, request
        );

        assertNotNull(response);
        verify(employeeService).getEmployees(
                isNull(), isNull(), eq("ASC"), eq("DESC"), eq("ASC"), isNull(), isNull(), eq("certificationNameOrder")
        );
    }
}
