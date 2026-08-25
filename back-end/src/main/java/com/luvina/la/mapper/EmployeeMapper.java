package com.luvina.la.mapper;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeMapper.java, 25/08/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.EmployeeEntity;
import com.luvina.la.payload.request.CreateAccountRequest;
import com.luvina.la.payload.response.EmployeeResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper chuyển đổi dữ liệu giữa EmployeeEntity, EmployeeDTO và các Payload (Request/Response).
 *
 * @author Phạm Văn Minh
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * Chuyển đổi từ EmployeeDTO sang EmployeeEntity.
     *
     * @param dto Đối tượng EmployeeDTO.
     * @return Đối tượng EmployeeEntity.
     */
    EmployeeEntity toEntity(EmployeeDTO dto);

    /**
     * Chuyển đổi từ EmployeeEntity sang EmployeeDTO.
     *
     * @param entity Đối tượng EmployeeEntity.
     * @return Đối tượng EmployeeDTO.
     */
    EmployeeDTO toDto(EmployeeEntity entity);

    /**
     * Chuyển đổi danh sách EmployeeEntity sang danh sách EmployeeDTO.
     *
     * @param list Danh sách EmployeeEntity.
     * @return Danh sách EmployeeDTO.
     */
    List<EmployeeDTO> toDtoList(List<EmployeeEntity> list);

    /**
     * Chuyển đổi từ EmployeeDTO sang EmployeeResponse.
     *
     * @param dto Đối tượng EmployeeDTO.
     * @return Đối tượng EmployeeResponse.
     */
    EmployeeResponse toResponse(EmployeeDTO dto);

    /**
     * Chuyển đổi danh sách EmployeeDTO sang danh sách EmployeeResponse.
     *
     * @param dtos Danh sách EmployeeDTO.
     * @return Danh sách EmployeeResponse.
     */
    List<EmployeeResponse> toResponseList(List<EmployeeDTO> dtos);

    /**
     * Chuyển đổi từ EmployeeResponse sang EmployeeDTO.
     *
     * @param response Đối tượng EmployeeResponse.
     * @return Đối tượng EmployeeDTO.
     */
    EmployeeDTO toDto(EmployeeResponse response);

    /**
     * Chuyển đổi từ CreateAccountRequest sang EmployeeEntity.
     * Lưu ý: Mật khẩu chưa mã hóa sẽ được xử lý riêng bằng PasswordEncoder.
     *
     * @param request Payload tạo tài khoản.
     * @return Đối tượng EmployeeEntity đã được map các trường cơ bản.
     */
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "employeeLoginId", source = "username")
    @Mapping(target = "employeeLoginPassword", ignore = true)
    EmployeeEntity toEntity(CreateAccountRequest request);
}
