package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * ListCertificationsResponse.java, 04/09/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Chứa kết quả trả về của API lấy danh sách chứng chỉ tiếng Nhật (Get List certifications).
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListCertificationsResponse {

    /**
     * Mã kết quả của API (200: thành công, 500: lỗi).
     */
    private Integer code;

    /**
     * Danh sách thông tin chứng chỉ tiếng Nhật.
     */
    private List<CertificationResponse> certifications;

    /**
     * Thông báo hoặc mã lỗi trả về khi có sự cố.
     */
    private MessageResponse message;
}
