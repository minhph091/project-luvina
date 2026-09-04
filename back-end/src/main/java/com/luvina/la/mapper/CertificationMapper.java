package com.luvina.la.mapper;

/**
 * Copyright(C) 2026 Luvina
 * CertificationMapper.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.CertificationEntity;
import com.luvina.la.payload.response.CertificationResponse;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Mapper chuyển đổi dữ liệu giữa CertificationEntity, CertificationDTO và CertificationResponse.
 *
 * @author Phạm Văn Minh
 */
@Mapper(componentModel = "spring")
public interface CertificationMapper {

    /**
     * Chuyển đổi từ CertificationEntity sang CertificationDTO.
     *
     * @param entity Đối tượng entity chứng chỉ.
     * @return Đối tượng DTO chứng chỉ.
     */
    CertificationDTO toDto(CertificationEntity entity);

    /**
     * Chuyển đổi danh sách CertificationEntity sang danh sách CertificationDTO.
     *
     * @param entities Danh sách entity chứng chỉ.
     * @return Danh sách DTO chứng chỉ.
     */
    List<CertificationDTO> toDtoList(List<CertificationEntity> entities);

    /**
     * Chuyển đổi từ CertificationDTO sang CertificationEntity.
     *
     * @param dto Đối tượng DTO chứng chỉ.
     * @return Đối tượng entity chứng chỉ.
     */
    CertificationEntity toEntity(CertificationDTO dto);

    /**
     * Chuyển đổi từ CertificationDTO sang CertificationResponse.
     *
     * @param dto Đối tượng DTO chứng chỉ.
     * @return Đối tượng response chứng chỉ.
     */
    CertificationResponse toResponse(CertificationDTO dto);

    /**
     * Chuyển đổi danh sách CertificationDTO sang danh sách CertificationResponse.
     *
     * @param dtos Danh sách DTO chứng chỉ.
     * @return Danh sách response chứng chỉ.
     */
    List<CertificationResponse> toResponseList(List<CertificationDTO> dtos);

    /**
     * Chuyển đổi trực tiếp từ CertificationEntity sang CertificationResponse.
     *
     * @param entity Đối tượng entity chứng chỉ.
     * @return Đối tượng response chứng chỉ.
     */
    CertificationResponse toResponse(CertificationEntity entity);
}
