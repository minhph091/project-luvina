package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentControllerTest.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.payload.response.DepartmentResponse;
import com.luvina.la.payload.response.ListDepartmentsResponse;
import com.luvina.la.service.DepartmentService;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test cho DepartmentController.
 *
 * @author Phạm Văn Minh
 */
@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        departmentController = new DepartmentController(departmentService);
    }

    @Test
    @DisplayName("Test getDepartments thành công với danh sách phòng ban")
    void testGetDepartmentsSuccess() {
        DepartmentResponse d1 = DepartmentResponse.builder().departmentId(1L).departmentName("Phòng Dev").build();
        DepartmentResponse d2 = DepartmentResponse.builder().departmentId(2L).departmentName("Phòng Sales").build();
        ListDepartmentsResponse mockResponse = ListDepartmentsResponse.builder()
                .code(200)
                .departments(Arrays.asList(d1, d2))
                .build();
        when(departmentService.getDepartments()).thenReturn(mockResponse);

        ListDepartmentsResponse response = departmentController.getDepartments();

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(2, response.getDepartments().size());
        assertEquals(1L, response.getDepartments().get(0).getDepartmentId());
        assertEquals("Phòng Dev", response.getDepartments().get(0).getDepartmentName());

        verify(departmentService).getDepartments();
    }

    @Test
    @DisplayName("Test getDepartments thành công khi danh sách rỗng")
    void testGetDepartmentsEmpty() {
        ListDepartmentsResponse mockResponse = ListDepartmentsResponse.builder()
                .code(200)
                .departments(Collections.emptyList())
                .build();
        when(departmentService.getDepartments()).thenReturn(mockResponse);

        ListDepartmentsResponse response = departmentController.getDepartments();

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertTrue(response.getDepartments().isEmpty());

        verify(departmentService).getDepartments();
    }
}
