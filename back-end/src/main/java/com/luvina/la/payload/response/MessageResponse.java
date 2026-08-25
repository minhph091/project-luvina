package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * MessageResponse.java, 21/08/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Đối tượng chứa thông tin mã thông báo hoặc mã lỗi và danh sách tham số của API response.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {

    /**
     * Mã thông báo hoặc mã lỗi.
     */
    private String code;

    /**
     * Danh sách các tham số truyền vào thông báo lỗi.
     */
    @Builder.Default
    private List<String> params = new ArrayList<>();
}
