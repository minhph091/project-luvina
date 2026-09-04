/**
 * Copyright(C) 2026 Luvina
 * certification.ts - Type Definitions for Japanese Certification Domain
 * 04/09/2026 Pham Van Minh
 */

/**
 * Thông tin một chứng chỉ tiếng Nhật trả về từ API master data.
 */
export interface CertificationItem {
  certificationId: number;
  certificationName: string;
  certificationLevel?: string;
}

/**
 * Phản hồi từ API GET /certification.
 */
export interface GetCertificationsApiResponse {
  code: number;
  certifications: CertificationItem[];
}
