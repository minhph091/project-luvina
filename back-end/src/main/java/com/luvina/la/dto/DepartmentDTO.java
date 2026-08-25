package com.luvina.la.dto;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentDTO.java, 18/08/2026 Phạm Văn Minh
 */

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO chứa thông tin phòng ban.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID của phòng ban.
     */
    private Long departmentId;

    /**
     * Tên của phòng ban.
     */
    private String departmentName;
}