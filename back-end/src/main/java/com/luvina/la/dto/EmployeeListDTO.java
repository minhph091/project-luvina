package com.luvina.la.dto;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeListDTO.java, 04/09/2026 Phạm Văn Minh
 */

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO chứa kết quả danh sách nhân viên và tổng số bản ghi từ tầng Service.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Tổng số bản ghi tìm kiếm được.
     */
    private Long totalRecords;

    /**
     * Danh sách thông tin nhân viên dạng DTO.
     */
    private List<EmployeeDTO> employees;
}
