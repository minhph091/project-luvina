package com.luvina.la.dto;

/**
 * Copyright(C) 2026 Luvina
 * DepartmentDto.java, 18/08/2026 Phạm Văn Minh
 */

/**
 * DTO chứa thông tin phòng ban.
 *
 * @author Phạm Văn Minh
 */
public class DepartmentDTO {

    /**
     * ID của phòng ban.
     */
    private Long departmentId;

    /**
     * Tên của phòng ban.
     */
    private String departmentName;

    /**
     * Khởi tạo DepartmentDto.
     */
    public DepartmentDTO() {
    }

    /**
     * Khởi tạo DepartmentDto với thông tin phòng ban.
     *
     * @param departmentId ID của phòng ban.
     * @param departmentName Tên của phòng ban.
     */
    public DepartmentDTO(
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