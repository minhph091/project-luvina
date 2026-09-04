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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
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
    @DisplayName("Test getEmployees with standard limit and offset according to API design")
    void testGetEmployeesWithLimitAndOffset() {
        ListEmployeesResponse mockResponse = new ListEmployeesResponse();
        mockResponse.setCode(200);
        mockResponse.setTotalRecords(10L);
        mockResponse.setEmployees(Collections.emptyList());

        when(employeeService.getEmployees(
                eq("John"), eq("1"), eq("ASC"), eq("ASC"), eq("ASC"), eq("5"), eq("10"), isNull()
        )).thenReturn(mockResponse);

        ListEmployeesResponse response = employeeController.getEmployees(
                "John", null, "1", null, "10", "5", null, null,
                "ASC", null, "ASC", null, "ASC", null, null, null
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(10L, response.getTotalRecords());

        verify(employeeService).getEmployees(
                eq("John"), eq("1"), eq("ASC"), eq("ASC"), eq("ASC"), eq("5"), eq("10"), isNull()
        );
    }

    @Test
    @DisplayName("Test getEmployees with snake_case parameters according to API design")
    void testGetEmployeesWithSnakeCaseAndPage() {
        ListEmployeesResponse mockResponse = new ListEmployeesResponse();
        mockResponse.setCode(200);
        mockResponse.setTotalRecords(25L);
        mockResponse.setEmployees(Collections.emptyList());

        // pageNo = 3, pageSize = 5 -> offset = (3-1)*5 = 10, limit = 5
        when(employeeService.getEmployees(
                eq("Doe"), eq("2"), eq("DESC"), eq("ASC"), eq("DESC"), eq("10"), eq("5"), isNull()
        )).thenReturn(mockResponse);

        ListEmployeesResponse response = employeeController.getEmployees(
                null, "Doe", null, "2", null, null, 3, 5,
                null, "DESC", null, "ASC", null, "DESC", null, null
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(25L, response.getTotalRecords());

        verify(employeeService).getEmployees(
                eq("Doe"), eq("2"), eq("DESC"), eq("ASC"), eq("DESC"), eq("10"), eq("5"), isNull()
        );
    }
}
