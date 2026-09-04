/**
 * Copyright(C) 2026 Luvina
 * certifications.test.ts - Unit Tests for Certifications API Service
 * 04/09/2026 Pham Van Minh
 */

import { getCertifications } from '@/lib/api/certifications';
import { apiClient } from '@/lib/api/client';

jest.mock('@/lib/api/client');
const mockedApiClient = apiClient as jest.Mocked<typeof apiClient>;

describe('Certifications API Service', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('calls GET /certification and returns response data', async () => {
    const mockResponseData = {
      code: 200,
      certifications: [
        { certificationId: 1, certificationName: 'Trình độ tiếng Nhật N1', certificationLevel: 'N1' },
        { certificationId: 2, certificationName: 'Trình độ tiếng Nhật N2', certificationLevel: 'N2' },
      ],
    };
    mockedApiClient.get.mockResolvedValue({ data: mockResponseData });

    const result = await getCertifications();

    expect(mockedApiClient.get).toHaveBeenCalledWith('/certification');
    expect(result).toEqual(mockResponseData);
  });
});
