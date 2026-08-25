package com.luvina.la.payload.response;

import java.util.List;

/**
 * Copyright(C) 2026 Luvina
 * GetDepartmentsResponse.java, 18/08/2026 Phạm Văn Minh
 */

/**
 * Chứa kết quả trả về của API lấy danh sách phòng ban.
 *
 * @author Phạm Văn Minh
 */
public class GetDepartmentsResponse {

    /**
     * Mã kết quả của API.
     */
    private Integer code;

    /**
     * Danh sách thông tin phòng ban.
     */
    private List<DepartmentResponse> departments;

    /**
     * Lấy mã kết quả của API.
     *
     * @return Mã kết quả của API.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Thiết lập mã kết quả của API.
     *
     * @param code Mã kết quả cần thiết lập cho API.
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Lấy danh sách phòng ban.
     *
     * @return Danh sách phòng ban.
     */
    public List<DepartmentResponse> getDepartments() {
        return departments;
    }

    /**
     * Thiết lập danh sách phòng ban.
     *
     * @param departments Danh sách phòng ban cần thiết lập cho response.
     */
    public void setDepartments(
            List<DepartmentResponse> departments) {
        this.departments = departments;
    }
}