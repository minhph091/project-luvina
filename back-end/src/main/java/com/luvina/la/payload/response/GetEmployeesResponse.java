package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * GetEmployeesResponse.java, 21/08/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Chứa kết quả trả về của API lấy danh sách nhân viên.
 *
 * @author Phạm Văn Minh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetEmployeesResponse {

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
     * @param code Mã kết quả cần thiết lập.
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Lấy tổng số bản ghi.
     *
     * @return Tổng số nhân viên thỏa điều kiện.
     */
    public Long getTotalRecords() {
        return totalRecords;
    }

    /**
     * Thiết lập tổng số bản ghi.
     *
     * @param totalRecords Tổng số bản ghi cần thiết lập.
     */
    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * Getter tương thích ngược cho totalRecord.
     *
     * @return Tổng số nhân viên thỏa điều kiện.
     */
    public Long getTotalRecord() {
        return totalRecords;
    }

    /**
     * Setter tương thích ngược cho totalRecord.
     *
     * @param totalRecord Tổng số bản ghi cần thiết lập.
     */
    public void setTotalRecord(Long totalRecord) {
        this.totalRecords = totalRecord;
    }

    /**
     * Lấy danh sách nhân viên.
     *
     * @return Danh sách nhân viên theo trang.
     */
    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    /**
     * Thiết lập danh sách nhân viên.
     *
     * @param employees Danh sách nhân viên cần thiết lập cho response.
     */
    public void setEmployees(List<EmployeeResponse> employees) {
        this.employees = employees;
    }

    /**
     * Lấy thông tin lỗi / thông báo.
     *
     * @return Đối tượng MessageResponse chứa mã lỗi và params.
     */
    public MessageResponse getMessage() {
        return message;
    }

    /**
     * Thiết lập thông tin lỗi / thông báo.
     *
     * @param message Đối tượng MessageResponse cần thiết lập.
     */
    public void setMessage(MessageResponse message) {
        this.message = message;
    }
}
