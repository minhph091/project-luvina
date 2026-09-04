package com.luvina.la.service;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentServiceTest.java, 25/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.DepartmentEntity;
import com.luvina.la.mapper.DepartmentMapper;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.impl.DepartmentServiceImpl;
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
import static org.mockito.Mockito.when;

/**
 * Unit test cho DepartmentService sử dụng DepartmentMapper.
 *
 * @author Phạm Văn Minh
 */
@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    private DepartmentMapper departmentMapper;
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        departmentMapper = Mappers.getMapper(DepartmentMapper.class);
        departmentService = new DepartmentServiceImpl(departmentRepository, departmentMapper);
    }

    @Test
    @DisplayName("Test getDepartments thành công với danh sách phòng ban")
    void testGetDepartmentsSuccess() {
        DepartmentEntity d1 = new DepartmentEntity(1L, "Phát triển 1");
        DepartmentEntity d2 = new DepartmentEntity(2L, "Phát triển 2");
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        List<DepartmentDTO> result = departmentService.getDepartments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getDepartmentId());
        assertEquals("Phát triển 1", result.get(0).getDepartmentName());
        assertEquals(2L, result.get(1).getDepartmentId());
        assertEquals("Phát triển 2", result.get(1).getDepartmentName());
    }

    @Test
    @DisplayName("Test getDepartments thành công khi danh sách rỗng")
    void testGetDepartmentsEmpty() {
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());

        List<DepartmentDTO> result = departmentService.getDepartments();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
