/**
 * Copyright(C) 2026 Luvina
 * useCertifications.ts - Custom Hook for Japanese Certification Data
 * 04/09/2026 Pham Van Minh
 */

import { useEffect, useState } from 'react';
import { getCertifications } from '@/lib/api/certifications';
import { CertificationItem } from '@/types/certification';

interface UseCertificationsReturn {
  certifications: CertificationItem[];
  loadingCertifications: boolean;
  certificationErrorMessage: string | null;
}

/**
 * Custom Hook quản lý việc tải danh sách chứng chỉ tiếng Nhật từ API GET /certification.
 *
 * @returns Object chứa danh sách certifications, trạng thái loading và error message.
 */
export function useCertifications(): UseCertificationsReturn {
  const [certifications, setCertifications] = useState<CertificationItem[]>([]);
  const [loadingCertifications, setLoadingCertifications] = useState(false);
  const [certificationErrorMessage, setCertificationErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    let isMounted = true;

    async function fetchCertificationList() {
      setLoadingCertifications(true);
      setCertificationErrorMessage(null);
      try {
        const response = await getCertifications();
        if (isMounted) {
          setCertifications(response.certifications || []);
        }
      } catch {
        if (isMounted) {
          setCertificationErrorMessage('資格を取得できません');
        }
      } finally {
        if (isMounted) {
          setLoadingCertifications(false);
        }
      }
    }

    fetchCertificationList();

    return () => {
      isMounted = false;
    };
  }, []);

  return {
    certifications,
    loadingCertifications,
    certificationErrorMessage,
  };
}
