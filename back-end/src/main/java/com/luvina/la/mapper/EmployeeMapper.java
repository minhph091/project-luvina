package com.luvina.la.mapper;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * use:
 *  EmployeeMapper.MAPPER.toEntity(dto);
 *  EmployeeMapper.MAPPER.toList(list);
 */
@Mapper
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper( EmployeeMapper.class );

    EmployeeEntity toEntity(EmployeeDTO entity);
    EmployeeEntity toDto(EmployeeDTO entity);
    Iterable<EmployeeDTO> toList(Iterable<EmployeeEntity> list);

}
