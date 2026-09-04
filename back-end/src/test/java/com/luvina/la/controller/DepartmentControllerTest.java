package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentControllerTest.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.mapper.DepartmentMapper;
import com.luvina.la.payload.response.ListDepartmentsResponse;
import com.luvina.la.service.DepartmentService;
import java.util.Arrays;
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

    private DepartmentMapper departmentMapper;
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        departmentMapper = Mappers.getMapper(DepartmentMapper.class);
        departmentController = new DepartmentController(departmentService, departmentMapper);
    }

    @Test
    @DisplayName("Test getDepartments thành công với danh sách phòng ban")
    void testGetDepartmentsSuccess() {
        DepartmentDTO d1 = DepartmentDTO.builder().departmentId(1L).departmentName("Phòng Dev").build();
        DepartmentDTO d2 = DepartmentDTO.builder().departmentId(2L).departmentName("Phòng Sales").build();
        when(departmentService.getDepartments()).thenReturn(Arrays.asList(d1, d2));

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
        when(departmentService.getDepartments()).thenReturn(Collections.emptyList());

        ListDepartmentsResponse response = departmentController.getDepartments();

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertTrue(response.getDepartments().isEmpty());

        verify(departmentService).getDepartments();
    }
}
