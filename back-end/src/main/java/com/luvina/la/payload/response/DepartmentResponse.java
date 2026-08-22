package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentResponse.java, 18/08/2026 Hoàng Ngọc Lâm
 */

/**
 * Chứa thông tin phòng ban được trả về trong response của API.
 *
 * @author Hoàng Ngọc Lâm
 */
public class DepartmentResponse {

    /**
     * ID của phòng ban.
     */
    private Long departmentId;

    /**
     * Tên của phòng ban.
     */
    private String departmentName;

    /**
     * Khởi tạo đối tượng DepartmentResponse.
     */
    public DepartmentResponse() {
    }

    /**
     * Khởi tạo đối tượng DepartmentResponse với thông tin phòng ban.
     *
     * @param departmentId ID của phòng ban.
     * @param departmentName Tên của phòng ban.
     */
    public DepartmentResponse(
            Long departmentId,
            String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    /**
     * Lấy ID của phòng ban.
     *
     * @return ID của phòng ban.
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Thiết lập ID của phòng ban.
     *
     * @param departmentId ID của phòng ban cần thiết lập.
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Lấy tên của phòng ban.
     *
     * @return Tên của phòng ban.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Thiết lập tên của phòng ban.
     *
     * @param departmentName Tên của phòng ban cần thiết lập.
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}