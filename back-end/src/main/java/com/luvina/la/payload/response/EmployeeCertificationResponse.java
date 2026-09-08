package com.luvina.la.payload.response;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeCertificationResponse.java, 08/09/2026 Pham Van Minh
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dữ liệu chứng chỉ tiếng Nhật trả về trong chi tiết nhân viên (GET /employee/{id}).
 *
 * @author Pham Van Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCertificationResponse {

    /**
     * ID của chứng chỉ.
     */
    private Long certificationId;

    /**
     * Tên của chứng chỉ tiếng Nhật.
     */
    private String certificationName;

    /**
     * Ngày bắt đầu hiệu lực (định dạng yyyy/MM/dd).
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    /**
     * Ngày kết thúc hiệu lực (định dạng yyyy/MM/dd).
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    /**
     * Điểm số chứng chỉ.
     */
    private BigDecimal score;
}
