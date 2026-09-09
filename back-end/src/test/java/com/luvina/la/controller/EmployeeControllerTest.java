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
import com.luvina.la.validator.EmployeeValidator;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Mock
    private EmployeeValidator employeeValidator;

    private EmployeeMapper employeeMapper;
    private EmployeeController employeeController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        employeeController = new EmployeeController(employeeService, employeeMapper, employeeValidator);
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
    @DisplayName("Test getEmployees ném CustomValidationException khi tham số ord không hợp lệ (ER021)")
    void testGetEmployeesValidationExceptionInvalidOrder() {
        when(employeeValidator.validateGetEmployeesParams(eq("INVALID"), any(), any(), any(), any()))
                .thenReturn(new MessageResponse("ER021", new ArrayList<>()));

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> employeeController.getEmployees(
                null, null, "INVALID", null, null, null, null
        ));
        assertEquals("ER021", ex.getMessageResponse().getCode());
    }

    @Test
    @DisplayName("Test getEmployees ném CustomValidationException khi offset không hợp lệ (ER018)")
    void testGetEmployeesValidationExceptionInvalidOffset() {
        when(employeeValidator.validateGetEmployeesParams(any(), any(), any(), eq("-1"), any()))
                .thenReturn(new MessageResponse("ER018", List.of("オフセット")));

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> employeeController.getEmployees(
                null, null, "ASC", null, null, "-1", "5"
        ));
        assertEquals("ER018", ex.getMessageResponse().getCode());
        assertEquals(List.of("オフセット"), ex.getMessageResponse().getParams());
    }

    @Test
    @DisplayName("Test getEmployees ném CustomValidationException khi limit không hợp lệ (ER018)")
    void testGetEmployeesValidationExceptionInvalidLimit() {
        when(employeeValidator.validateGetEmployeesParams(any(), any(), any(), any(), eq("0")))
                .thenReturn(new MessageResponse("ER018", List.of("リミット")));

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> employeeController.getEmployees(
                null, null, "ASC", null, null, "0", "0"
        ));
        assertEquals("ER018", ex.getMessageResponse().getCode());
        assertEquals(List.of("リミット"), ex.getMessageResponse().getParams());
    }

    @Test
    @DisplayName("Test getEmployees với MockMvc và GlobalExceptionHandler trả về JSON lỗi code 500 khi Controller validate lỗi")
    void testGetEmployeesValidationWithMockMvc() throws Exception {
        when(employeeValidator.validateGetEmployeesParams(eq("INVALID"), any(), any(), any(), any()))
                .thenReturn(new MessageResponse("ER021", new ArrayList<>()));

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
    @DisplayName("Test addEmployee ném CustomValidationException khi Validator báo lỗi")
    void testAddEmployeeValidationException() {
        com.luvina.la.payload.request.AddEmployeeRequest request = com.luvina.la.payload.request.AddEmployeeRequest.builder().build();

        when(employeeValidator.validateAddEmployee(request))
                .thenReturn(new MessageResponse(Constants.ERROR_CODE_ER001, List.of(Constants.PARAM_ACCOUNT_NAME)));

        CustomValidationException ex = assertThrows(CustomValidationException.class, () -> employeeController.addEmployee(request));
        assertEquals(Constants.ERROR_CODE_ER001, ex.getMessageResponse().getCode());
        assertEquals(List.of(Constants.PARAM_ACCOUNT_NAME), ex.getMessageResponse().getParams());
    }

    @Test
    @DisplayName("Test addEmployee với MockMvc và GlobalExceptionHandler trả về JSON lỗi code 500 khi Validator báo lỗi")
    void testAddEmployeeValidationWithMockMvc() throws Exception {
        when(employeeValidator.validateAddEmployee(any()))
                .thenReturn(new MessageResponse(Constants.ERROR_CODE_ER001, List.of(Constants.PARAM_ACCOUNT_NAME)));

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

    @Test
    @DisplayName("Test getEmployeeById trả về HTTP 200 và chi tiết nhân viên khi ID tồn tại")
    void testGetEmployeeByIdSuccess() throws Exception {
        com.luvina.la.dto.EmployeeDetailDTO detailDTO = com.luvina.la.dto.EmployeeDetailDTO.builder()
                .employeeId(1L)
                .employeeName("Nguyễn Văn A")
                .employeeBirthDate(java.time.LocalDate.of(1990, 5, 20))
                .departmentId(3L)
                .departmentName("Phòng Phát Triển 1")
                .employeeEmail("vana@luvina.net")
                .employeeTelephone("0123456789")
                .employeeNameKana("名カナ")
                .employeeLoginId("vana")
                .certifications(List.of(
                        com.luvina.la.dto.EmployeeDetailDTO.CertificationInfo.builder()
                                .certificationId(1L)
                                .certificationName("Trình độ tiếng Nhật cấp 1")
                                .startDate(java.time.LocalDate.of(2023, 1, 1))
                                .endDate(java.time.LocalDate.of(2024, 1, 1))
                                .score(new java.math.BigDecimal("180"))
                                .build()
                ))
                .build();

        when(employeeService.getEmployeeById(1L)).thenReturn(detailDTO);

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.employeeName").value("Nguyễn Văn A"))
                .andExpect(jsonPath("$.departmentName").value("Phòng Phát Triển 1"))
                .andExpect(jsonPath("$.certifications[0].certificationName").value("Trình độ tiếng Nhật cấp 1"))
                .andExpect(jsonPath("$.certifications[0].score").value(180));
    }

    @Test
    @DisplayName("Test getEmployeeById trả về lỗi ER013 khi Validator báo ID không tồn tại")
    void testGetEmployeeByIdNotFound() throws Exception {
        when(employeeValidator.validateEmployeeId(999L))
                .thenReturn(new MessageResponse(Constants.ERROR_CODE_ER013, List.of(Constants.PARAM_ID)));

        mockMvc.perform(get("/employee/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message.code").value("ER013"))
                .andExpect(jsonPath("$.message.params[0]").value(Constants.PARAM_ID));
    }

    @Test
    @DisplayName("Test deleteEmployee trả về HTTP 200 và MSG003 khi xóa thành công")
    void testDeleteEmployeeSuccess() throws Exception {
        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.message.code").value("MSG003"));

        verify(employeeService).deleteEmployee(1L);
    }

    @Test
    @DisplayName("Test deleteEmployee trả về lỗi ER001 khi Validator báo ID rỗng")
    void testDeleteEmployeeMissingId() throws Exception {
        when(employeeValidator.validateEmployeeIdForDelete(isNull()))
                .thenReturn(new MessageResponse(Constants.ERROR_CODE_ER001, Collections.singletonList(Constants.PARAM_ID)));

        mockMvc.perform(delete("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message.code").value("ER001"))
                .andExpect(jsonPath("$.message.params[0]").value(Constants.PARAM_ID));
    }

    @Test
    @DisplayName("Test deleteEmployee trả về lỗi ER014 và employeeId khi ID không tồn tại")
    void testDeleteEmployeeNotFound() throws Exception {
        when(employeeValidator.validateEmployeeIdForDelete(999L))
                .thenReturn(new MessageResponse(Constants.ERROR_CODE_ER014, List.of(Constants.PARAM_ID)));

        mockMvc.perform(delete("/employee/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.employeeId").value(999))
                .andExpect(jsonPath("$.message.code").value("ER014"))
                .andExpect(jsonPath("$.message.params[0]").value(Constants.PARAM_ID));
    }

    @Test
    @DisplayName("Test deleteEmployee trả về lỗi ER015 và employeeId khi gặp lỗi hệ thống trong CSDL")
    void testDeleteEmployeeDatabaseError() throws Exception {
        org.mockito.Mockito.doThrow(new CustomValidationException(new MessageResponse(Constants.ERROR_CODE_ER015, List.of()), 1L))
                .when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.message.code").value("ER015"));
    }
}
