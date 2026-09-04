package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * ListEmployeesResponse.java, 21/08/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Chứa kết quả trả về của API lấy danh sách nhân viên (List employees).
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListEmployeesResponse {

    /**
     * Mã kết quả của API (200 thành công, 500 lỗi).
     */
    private Integer code;

    /**
     * Tổng số bản ghi nhân viên thỏa điều kiện tìm kiếm.
     */
    private Long totalRecords;

    /**
     * Danh sách thông tin nhân viên theo trang hiện tại.
     */
    private List<EmployeeResponse> employees;

    /**
     * Thông báo hoặc mã lỗi trả về khi có sự cố.
     */
    private MessageResponse message;
}
