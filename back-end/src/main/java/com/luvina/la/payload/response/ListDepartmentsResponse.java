package com.luvina.la.payload.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright(C) 2026 Luvina
 * GetDepartmentsResponse.java, 18/08/2026 Phạm Văn Minh
 */

/**
 * Chứa kết quả trả về của API lấy danh sách phòng ban.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListDepartmentsResponse {

    /**
     * Mã kết quả của API.
     */
    private Integer code;

    /**
     * Danh sách thông tin phòng ban.
     */
    private List<DepartmentResponse> departments;
}