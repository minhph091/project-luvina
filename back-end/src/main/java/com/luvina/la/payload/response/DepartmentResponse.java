package com.luvina.la.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentResponse.java, 18/08/2026 Phạm Văn Minh
 */

/**
 * Chứa thông tin phòng ban được trả về trong response của API.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {

    /**
     * ID của phòng ban.
     */
    private Long departmentId;

    /**
     * Tên của phòng ban.
     */
    private String departmentName;
}