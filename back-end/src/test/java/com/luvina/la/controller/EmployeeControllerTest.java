package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeControllerTest.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeListDTO;
import com.luvina.la.exception.CustomValidationException;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.payload.response.ListEmployeesResponse;
import com.luvina.la.payload.response.MessageResponse;
import com.luvina.la.exception.GlobalExceptionHandler;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        employeeController = new EmployeeController(employeeService, employeeMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
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
    @DisplayName("Test getEmployees ném CustomValidationException khi Service ném lỗi")
    void testGetEmployeesValidationException() {
        when(employeeService.getEmployees(
                isNull(), isNull(), eq("INVALID"), isNull(), isNull(), isNull(), isNull(), isNull()
        )).thenThrow(new CustomValidationException(new MessageResponse("ER021", new ArrayList<>())));

        assertThrows(CustomValidationException.class, () -> employeeController.getEmployees(
                null, null, "INVALID", null, null, null, null
        ));
    }

    @Test
    @DisplayName("Test getEmployees với MockMvc và GlobalExceptionHandler trả về JSON lỗi code 500")
    void testGetEmployeesValidationWithMockMvc() throws Exception {
        when(employeeService.getEmployees(
                isNull(), isNull(), eq("INVALID"), isNull(), isNull(), isNull(), isNull(), any()
        )).thenThrow(new CustomValidationException(new MessageResponse("ER021", new ArrayList<>())));

        mockMvc.perform(get("/employee").param("ord_employee_name", "INVALID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message.code").value("ER021"));
    }

    @Test
    @DisplayName("Test addEmployee thành công trả về code 200 và employeeId")
    void testAddEmployeeSuccess() {
        com.luvina.la.payload.request.AddEmployeeRequest request = com.luvina.la.payload.request.AddEmployeeRequest.builder()
                .employeeLoginId("nguyenvana")
                .employeeName("Nguyễn Văn A")
                .build();

        com.luvina.la.dto.EmployeeDTO mockDto = com.luvina.la.dto.EmployeeDTO.builder()
                .employeeId(1L)
                .employeeName("Nguyễn Văn A")
                .build();

        when(employeeService.addEmployee(request)).thenReturn(mockDto);

        com.luvina.la.payload.response.AddEmployeeResponse response = employeeController.addEmployee(request);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(1L, response.getEmployeeId());
        assertEquals("MSG001", response.getMessage().getCode());
        verify(employeeService).addEmployee(request);
    }

    @Test
    @DisplayName("Test addEmployee ném CustomValidationException khi Service ném lỗi")
    void testAddEmployeeValidationException() {
        com.luvina.la.payload.request.AddEmployeeRequest request = com.luvina.la.payload.request.AddEmployeeRequest.builder().build();

        when(employeeService.addEmployee(request))
                .thenThrow(new CustomValidationException(new MessageResponse(Constants.ERROR_CODE_ER001, List.of(Constants.PARAM_ACCOUNT_NAME))));

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> employeeController.addEmployee(request));
        assertEquals(Constants.ERROR_CODE_ER001, ex.getMessageResponse().getCode());
        assertEquals(List.of(Constants.PARAM_ACCOUNT_NAME), ex.getMessageResponse().getParams());
    }

    @Test
    @DisplayName("Test addEmployee với MockMvc và GlobalExceptionHandler trả về JSON lỗi code 500")
    void testAddEmployeeValidationWithMockMvc() throws Exception {
        when(employeeService.addEmployee(any()))
                .thenThrow(new CustomValidationException(new MessageResponse(Constants.ERROR_CODE_ER001, List.of(Constants.PARAM_ACCOUNT_NAME))));

        mockMvc.perform(post("/employee")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message.code").value("ER001"))
                .andExpect(jsonPath("$.message.params[0]").value(Constants.PARAM_ACCOUNT_NAME));
    }

    @Test
    @DisplayName("Test addEmployee ném RuntimeException khi có lỗi hệ thống")
    void testAddEmployeeGeneralException() {
        com.luvina.la.payload.request.AddEmployeeRequest request = com.luvina.la.payload.request.AddEmployeeRequest.builder().build();

        when(employeeService.addEmployee(request))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> employeeController.addEmployee(request));
    }

    @Test
    @DisplayName("Test addEmployee với MockMvc và GlobalExceptionHandler trả về JSON mã lỗi ER015 khi có lỗi hệ thống")
    void testAddEmployeeGeneralExceptionWithMockMvc() throws Exception {
        when(employeeService.addEmployee(any()))
                .thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(post("/employee")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message.code").value(Constants.ERROR_CODE_ER015));
    }
}
