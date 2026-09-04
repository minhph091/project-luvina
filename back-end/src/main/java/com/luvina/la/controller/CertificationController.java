package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * CertificationController.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.mapper.CertificationMapper;
import com.luvina.la.payload.response.CertificationResponse;
import com.luvina.la.payload.response.ListCertificationsResponse;
import com.luvina.la.payload.response.MessageResponse;
import com.luvina.la.service.CertificationService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xử lý các request liên quan đến chứng chỉ tiếng Nhật.
 * Nhận DTO từ Service và chuyển đổi sang Response Payload trả về client.
 *
 * @author Phạm Văn Minh
 */
@RestController
public class CertificationController {

    private static final Logger log = LoggerFactory.getLogger(CertificationController.class);

    private final CertificationService certificationService;
    private final CertificationMapper certificationMapper;

    /**
     * Khởi tạo CertificationController.
     *
     * @param certificationService Service xử lý nghiệp vụ chứng chỉ.
     * @param certificationMapper  Mapper chuyển đổi giữa DTO và Response.
     */
    public CertificationController(
            CertificationService certificationService,
            CertificationMapper certificationMapper) {
        this.certificationService = certificationService;
        this.certificationMapper = certificationMapper;
    }

    /**
     * Lấy danh sách tất cả chứng chỉ tiếng Nhật theo tài liệu thiết kế API (GET /certifications).
     * Hỗ trợ alias /certification cho frontend.
     *
     * @return Response chứa mã kết quả và danh sách chứng chỉ hoặc lỗi ER023.
     */
    @GetMapping({"/certifications", "/certification"})
    public ListCertificationsResponse getCertifications() {
        try {
            List<CertificationDTO> certificationDTOs = certificationService.getCertifications();
            List<CertificationResponse> certifications = certificationMapper.toResponseList(certificationDTOs);

            return ListCertificationsResponse.builder()
                    .code(Constants.RESPONSE_CODE_SUCCESS)
                    .certifications(certifications)
                    .build();
        } catch (Exception ex) {
            log.error("Error occurred while getting certification list: ", ex);
            return ListCertificationsResponse.builder()
                    .code(Constants.RESPONSE_CODE_ERROR)
                    .message(new MessageResponse(Constants.ERROR_CODE_ER023, new ArrayList<>()))
                    .build();
        }
    }
}
