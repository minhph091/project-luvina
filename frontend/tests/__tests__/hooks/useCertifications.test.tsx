/**
 * Copyright(C) 2026 Luvina
 * useCertifications.test.tsx - Unit Tests for useCertifications Hook
 * 04/09/2026 Pham Van Minh
 */

import { renderHook, waitFor } from '@testing-library/react';
import { useCertifications } from '@/hooks/useCertifications';
import { getCertifications } from '@/lib/api/certifications';

jest.mock('@/lib/api/certifications', () => ({
  getCertifications: jest.fn(),
}));

describe('useCertifications Hook', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('loads certification list successfully', async () => {
    (getCertifications as jest.Mock).mockResolvedValue({
      code: 200,
      certifications: [
        { certificationId: 1, certificationName: 'Trình độ tiếng Nhật N1', certificationLevel: 'N1' },
      ],
    });

    const { result } = renderHook(() => useCertifications());

    await waitFor(() => {
      expect(result.current.loadingCertifications).toBe(false);
      expect(result.current.certifications).toHaveLength(1);
      expect(result.current.certifications[0].certificationName).toBe('Trình độ tiếng Nhật N1');
      expect(result.current.certificationErrorMessage).toBeNull();
    });
  });

  it('sets error message when certification fetch fails', async () => {
    (getCertifications as jest.Mock).mockRejectedValue(new Error('Failed to load'));

    const { result } = renderHook(() => useCertifications());

    await waitFor(() => {
      expect(result.current.loadingCertifications).toBe(false);
      expect(result.current.certifications).toEqual([]);
      expect(result.current.certificationErrorMessage).toBe('資格を取得できません');
    });
  });
});
