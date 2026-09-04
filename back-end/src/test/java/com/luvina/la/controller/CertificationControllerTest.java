package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * CertificationControllerTest.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.mapper.CertificationMapper;
import com.luvina.la.payload.response.ListCertificationsResponse;
import com.luvina.la.service.CertificationService;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test cho CertificationController.
 *
 * @author Phạm Văn Minh
 */
@ExtendWith(MockitoExtension.class)
public class CertificationControllerTest {

    @Mock
    private CertificationService certificationService;

    private CertificationMapper certificationMapper;
    private CertificationController certificationController;

    @BeforeEach
    void setUp() {
        certificationMapper = Mappers.getMapper(CertificationMapper.class);
        certificationController = new CertificationController(certificationService, certificationMapper);
    }

    @Test
    @DisplayName("Test getCertifications thành công với danh sách chứng chỉ")
    void testGetCertificationsSuccess() {
        CertificationDTO c1 = CertificationDTO.builder().certificationId(1L).certificationName("JLPT N1").certificationLevel(1).build();
        CertificationDTO c2 = CertificationDTO.builder().certificationId(2L).certificationName("JLPT N2").certificationLevel(2).build();
        when(certificationService.getCertifications()).thenReturn(Arrays.asList(c1, c2));

        ListCertificationsResponse response = certificationController.getCertifications();

        assertNotNull(response);
        assertEquals(Constants.RESPONSE_CODE_SUCCESS, response.getCode());
        assertNotNull(response.getCertifications());
        assertEquals(2, response.getCertifications().size());
        assertEquals(1L, response.getCertifications().get(0).getCertificationId());
        assertEquals("JLPT N1", response.getCertifications().get(0).getCertificationName());
        assertNull(response.getMessage());

        verify(certificationService).getCertifications();
    }

    @Test
    @DisplayName("Test getCertifications thành công khi danh sách rỗng")
    void testGetCertificationsEmpty() {
        when(certificationService.getCertifications()).thenReturn(Collections.emptyList());

        ListCertificationsResponse response = certificationController.getCertifications();

        assertNotNull(response);
        assertEquals(Constants.RESPONSE_CODE_SUCCESS, response.getCode());
        assertNotNull(response.getCertifications());
        assertTrue(response.getCertifications().isEmpty());
        assertNull(response.getMessage());

        verify(certificationService).getCertifications();
    }

    @Test
    @DisplayName("Test getCertifications trả về lỗi 500 với ER023 khi có ngoại lệ")
    void testGetCertificationsError() {
        when(certificationService.getCertifications()).thenThrow(new RuntimeException("Database error"));

        ListCertificationsResponse response = certificationController.getCertifications();

        assertNotNull(response);
        assertEquals(Constants.RESPONSE_CODE_ERROR, response.getCode());
        assertNull(response.getCertifications());
        assertNotNull(response.getMessage());
        assertEquals(Constants.ERROR_CODE_ER023, response.getMessage().getCode());
        assertTrue(response.getMessage().getParams().isEmpty());

        verify(certificationService).getCertifications();
    }
}
