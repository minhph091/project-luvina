package com.luvina.la.mapper;

/**
 * Copyright(C) 2026 Luvina
 * MapperTest.java, 25/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.DepartmentEntity;
import com.luvina.la.entity.EmployeeEntity;
import com.luvina.la.payload.request.CreateAccountRequest;
import com.luvina.la.payload.response.DepartmentResponse;
import com.luvina.la.payload.response.EmployeeResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test cho DepartmentMapper và EmployeeMapper với đầy đủ các tầng Entity, DTO và Payload.
 *
 * @author Phạm Văn Minh
 */
public class MapperTest {

    private final DepartmentMapper departmentMapper = Mappers.getMapper(DepartmentMapper.class);
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    @DisplayName("Test DepartmentMapper: Entity <-> DTO và DTO <-> Response")
    void testDepartmentMapperAll() {
        DepartmentEntity entity = new DepartmentEntity(1L, "Phòng Đào Tạo");

        // 1. Entity -> DTO
        DepartmentDTO dto = departmentMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(1L, dto.getDepartmentId());
        assertEquals("Phòng Đào Tạo", dto.getDepartmentName());

        // 2. DTO -> Entity
        DepartmentEntity mappedEntity = departmentMapper.toEntity(dto);
        assertNotNull(mappedEntity);
        assertEquals(1L, mappedEntity.getDepartmentId());
        assertEquals("Phòng Đào Tạo", mappedEntity.getDepartmentName());

        // 3. DTO -> Response
        DepartmentResponse response = departmentMapper.toResponse(dto);
        assertNotNull(response);
        assertEquals(1L, response.getDepartmentId());
        assertEquals("Phòng Đào Tạo", response.getDepartmentName());

        // 4. List<DTO> -> List<Response>
        List<DepartmentResponse> responseList = departmentMapper.toResponseList(Arrays.asList(dto));
        assertEquals(1, responseList.size());
        assertEquals("Phòng Đào Tạo", responseList.get(0).getDepartmentName());
    }

    @Test
    @DisplayName("Test EmployeeMapper: Entity <-> DTO và DTO <-> Response")
    void testEmployeeMapperDtoAndResponse() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployeeId(10L);
        entity.setEmployeeName("Dev A");
        entity.setEmployeeNameKana("デブ エー");
        entity.setDepartmentId(2L);
        entity.setEmployeeEmail("deva@luvina.net");
        entity.setEmployeeTelephone("0912345678");
        entity.setEmployeeLoginId("deva");
        entity.setEmployeeRole("USER");
        entity.setEmployeeBirthDate(LocalDate.of(1996, 6, 15));

        // 1. Entity -> DTO
        EmployeeDTO dto = employeeMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(10L, dto.getEmployeeId());
        assertEquals("Dev A", dto.getEmployeeName());
        assertEquals("deva@luvina.net", dto.getEmployeeEmail());
        assertEquals(2L, dto.getDepartmentId());

        // 2. DTO -> Entity
        EmployeeEntity mappedBack = employeeMapper.toEntity(dto);
        assertNotNull(mappedBack);
        assertEquals(10L, mappedBack.getEmployeeId());
        assertEquals("Dev A", mappedBack.getEmployeeName());

        // 3. DTO -> Response
        dto.setDepartmentName("Phòng IT");
        dto.setCertificationName("N2");
        dto.setEndDate(LocalDate.of(2027, 12, 31));
        dto.setScore(new BigDecimal("150"));

        EmployeeResponse response = employeeMapper.toResponse(dto);
        assertNotNull(response);
        assertEquals(10L, response.getEmployeeId());
        assertEquals("Dev A", response.getEmployeeName());
        assertEquals("Phòng IT", response.getDepartmentName());
        assertEquals("N2", response.getCertificationName());
        assertEquals(new BigDecimal("150"), response.getScore());

        // 4. List<DTO> -> List<Response>
        List<EmployeeResponse> responses = employeeMapper.toResponseList(Arrays.asList(dto));
        assertEquals(1, responses.size());
        assertEquals("Dev A", responses.get(0).getEmployeeName());
    }

    @Test
    @DisplayName("Test EmployeeMapper: toEntity từ CreateAccountRequest")
    void testEmployeeMapperCreateAccountRequest() {
        CreateAccountRequest req = CreateAccountRequest.builder()
                .username("testuser")
                .password("secret123")
                .employeeName("Test User")
                .employeeEmail("testuser@luvina.net")
                .departmentId(2L)
                .employeeBirthDate(LocalDate.of(1995, 5, 10))
                .employeeTelephone("0987654321")
                .build();

        EmployeeEntity entity = employeeMapper.toEntity(req);

        assertNotNull(entity);
        assertEquals("testuser", entity.getEmployeeLoginId());
        assertEquals("Test User", entity.getEmployeeName());
        assertEquals("testuser@luvina.net", entity.getEmployeeEmail());
        assertEquals(2L, entity.getDepartmentId());
        assertEquals(LocalDate.of(1995, 5, 10), entity.getEmployeeBirthDate());
        assertEquals("0987654321", entity.getEmployeeTelephone());
        assertNull(entity.getEmployeeLoginPassword()); // Password được mã hóa riêng
    }
}
