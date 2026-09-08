package com.luvina.la.dto;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeDetailDTO.java, 08/09/2026 Pham Van Minh
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object đại diện cho thông tin chi tiết đầy đủ của một nhân viên,
 * bao gồm cả phòng ban và danh sách chứng chỉ tiếng Nhật.
 *
 * @author Pham Van Minh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long employeeId;
    private Long departmentId;
    private String departmentName;
    private String employeeName;
    private String employeeNameKana;
    private LocalDate employeeBirthDate;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeLoginId;
    private String employeeRole;

    @Builder.Default
    private List<CertificationInfo> certifications = new ArrayList<>();

    /**
     * Thông tin chi tiết một chứng chỉ tiếng Nhật của nhân viên.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CertificationInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        private Long certificationId;
        private String certificationName;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal score;
    }
}
