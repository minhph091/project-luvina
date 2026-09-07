package com.luvina.la.payload.request;

/**
 * Copyright(C) 2026 Luvina
 * CertificationItemRequest.java, 07/09/2026 Phạm Văn Minh
 */

import com.fasterxml.jackson.annotation.JsonAlias;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO đại diện cho một chứng chỉ tiếng Nhật trong request thêm nhân viên.
 * Hỗ trợ alias linh hoạt giữa tài liệu API design doc và frontend form fields.
 *
 * @author Phạm Văn Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationItemRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID chứng chỉ tiếng Nhật.
     */
    @JsonAlias({"certificationId"})
    private String certificationId;

    /**
     * Ngày cấp chứng chỉ (định dạng yyyy/MM/dd).
     */
    @JsonAlias({"startDate", "certificationStartDate"})
    private String startDate;

    /**
     * Ngày hết hạn chứng chỉ (định dạng yyyy/MM/dd).
     */
    @JsonAlias({"endDate", "certificationEndDate"})
    private String endDate;

    /**
     * Điểm số chứng chỉ.
     */
    @JsonAlias({"score", "employeeCertificationScore"})
    private String score;
}
