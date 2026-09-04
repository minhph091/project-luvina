package com.luvina.la.service;

/**
 * Copyright(C) 2026 Luvina
 * CertificationServiceTest.java, 04/09/2026 Phạm Văn Minh
 */

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.CertificationEntity;
import com.luvina.la.mapper.CertificationMapper;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.impl.CertificationServiceImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
 * Unit test cho CertificationService sử dụng CertificationMapper.
 *
 * @author Phạm Văn Minh
 */
@ExtendWith(MockitoExtension.class)
public class CertificationServiceTest {

    @Mock
    private CertificationRepository certificationRepository;

    private CertificationMapper certificationMapper;
    private CertificationService certificationService;

    @BeforeEach
    void setUp() {
        certificationMapper = Mappers.getMapper(CertificationMapper.class);
        certificationService = new CertificationServiceImpl(certificationRepository, certificationMapper);
    }

    @Test
    @DisplayName("Test getCertifications thành công với danh sách chứng chỉ")
    void testGetCertificationsSuccess() {
        CertificationEntity c1 = new CertificationEntity(1L, "JLPT N1", 1);
        CertificationEntity c2 = new CertificationEntity(2L, "JLPT N2", 2);
        when(certificationRepository.findAllByOrderByCertificationLevelAsc()).thenReturn(Arrays.asList(c1, c2));

        List<CertificationDTO> result = certificationService.getCertifications();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getCertificationId());
        assertEquals("JLPT N1", result.get(0).getCertificationName());
        assertEquals(1, result.get(0).getCertificationLevel());
        assertEquals(2L, result.get(1).getCertificationId());
        assertEquals("JLPT N2", result.get(1).getCertificationName());
        assertEquals(2, result.get(1).getCertificationLevel());

        verify(certificationRepository).findAllByOrderByCertificationLevelAsc();
    }

    @Test
    @DisplayName("Test getCertifications thành công khi danh sách rỗng")
    void testGetCertificationsEmpty() {
        when(certificationRepository.findAllByOrderByCertificationLevelAsc()).thenReturn(Collections.emptyList());

        List<CertificationDTO> result = certificationService.getCertifications();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(certificationRepository).findAllByOrderByCertificationLevelAsc();
    }
}
