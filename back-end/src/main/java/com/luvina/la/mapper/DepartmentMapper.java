package com.luvina.la.mapper;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentMapper.java, 25/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.DepartmentEntity;
import com.luvina.la.payload.response.DepartmentResponse;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Mapper chuyển đổi dữ liệu giữa DepartmentEntity, DepartmentDTO và DepartmentResponse.
 *
 * @author Phạm Văn Minh
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    /**
     * Chuyển đổi từ DepartmentEntity sang DepartmentDTO.
     *
     * @param entity Đối tượng entity phòng ban.
     * @return Đối tượng DTO phòng ban.
     */
    DepartmentDTO toDto(DepartmentEntity entity);

    /**
     * Chuyển đổi danh sách DepartmentEntity sang danh sách DepartmentDTO.
     *
     * @param entities Danh sách entity phòng ban.
     * @return Danh sách DTO phòng ban.
     */
    List<DepartmentDTO> toDtoList(List<DepartmentEntity> entities);

    /**
     * Chuyển đổi từ DepartmentDTO sang DepartmentEntity.
     *
     * @param dto Đối tượng DTO phòng ban.
     * @return Đối tượng entity phòng ban.
     */
    DepartmentEntity toEntity(DepartmentDTO dto);

    /**
     * Chuyển đổi từ DepartmentDTO sang DepartmentResponse.
     *
     * @param dto Đối tượng DTO phòng ban.
     * @return Đối tượng response phòng ban.
     */
    DepartmentResponse toResponse(DepartmentDTO dto);

    /**
     * Chuyển đổi danh sách DepartmentDTO sang danh sách DepartmentResponse.
     *
     * @param dtos Danh sách DTO phòng ban.
     * @return Danh sách response phòng ban.
     */
    List<DepartmentResponse> toResponseList(List<DepartmentDTO> dtos);

    /**
     * Chuyển đổi trực tiếp từ DepartmentEntity sang DepartmentResponse (hỗ trợ tiện ích).
     *
     * @param entity Đối tượng entity phòng ban.
     * @return Đối tượng response phòng ban.
     */
    DepartmentResponse toResponse(DepartmentEntity entity);
}
