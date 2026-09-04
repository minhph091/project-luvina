/**
 * Copyright(C) 2026 Luvina
 * certifications.ts - API Service for Japanese Certification Domain
 * 04/09/2026 Pham Van Minh
 */

import { apiClient } from './client';
import { CertificationItem, GetCertificationsApiResponse } from '@/types/certification';

/**
 * Gọi API GET /certification để lấy toàn bộ danh sách chứng chỉ tiếng Nhật.
 *
 * @returns Promise chứa danh sách chứng chỉ từ backend.
 */
export async function getCertifications(): Promise<GetCertificationsApiResponse> {
  const response = await apiClient.get<GetCertificationsApiResponse>('/certification');
  return response.data;
}

export type { CertificationItem, GetCertificationsApiResponse };
