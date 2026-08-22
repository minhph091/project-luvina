package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * MessageResponse.java, 21/08/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

/**
 * Đối tượng chứa thông tin mã thông báo hoặc mã lỗi và danh sách tham số của API response.
 *
 * @author Phạm Văn Minh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {

    /**
     * Mã thông báo hoặc mã lỗi.
     */
    private String code;

    /**
     * Danh sách các tham số truyền vào thông báo lỗi.
     */
    private List<String> params;

    /**
     * Khởi tạo MessageResponse mặc định.
     */
    public MessageResponse() {
        this.params = new ArrayList<>();
    }

    /**
     * Khởi tạo MessageResponse với mã lỗi và danh sách tham số.
     *
     * @param code   Mã lỗi hoặc mã thông báo.
     * @param params Danh sách tham số lỗi.
     */
    public MessageResponse(String code, List<String> params) {
        this.code = code;
        this.params = params != null ? params : new ArrayList<>();
    }

    /**
     * Lấy mã thông báo hoặc mã lỗi.
     *
     * @return Mã thông báo hoặc mã lỗi.
     */
    public String getCode() {
        return code;
    }

    /**
     * Thiết lập mã thông báo hoặc mã lỗi.
     *
     * @param code Mã cần thiết lập.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Lấy danh sách tham số thông báo.
     *
     * @return Danh sách tham số.
     */
    public List<String> getParams() {
        return params;
    }

    /**
     * Thiết lập danh sách tham số thông báo.
     *
     * @param params Danh sách tham số cần thiết lập.
     */
    public void setParams(List<String> params) {
        this.params = params != null ? params : new ArrayList<>();
    }
}
