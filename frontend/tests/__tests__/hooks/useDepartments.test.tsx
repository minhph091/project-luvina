import { renderHook, waitFor } from '@testing-library/react';
import { useDepartments } from '@/hooks/useDepartments';
import { getDepartments } from '@/lib/api/departments';

jest.mock('@/lib/api/departments', () => ({
  getDepartments: jest.fn(),
}));

describe('useDepartments Hook', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('loads department list successfully', async () => {
    (getDepartments as jest.Mock).mockResolvedValue({
      code: 200,
      departments: [
        { departmentId: 1, departmentName: 'Phòng Phát triển 1' },
      ],
    });

    const { result } = renderHook(() => useDepartments());

    await waitFor(() => {
      expect(result.current.loadingDepartments).toBe(false);
      expect(result.current.departments).toHaveLength(1);
      expect(result.current.departments[0].departmentName).toBe('Phòng Phát triển 1');
      expect(result.current.departmentErrorMessage).toBeNull();
    });
  });

  it('sets error message when department fetch fails', async () => {
    (getDepartments as jest.Mock).mockRejectedValue(new Error('Failed to load'));

    const { result } = renderHook(() => useDepartments());

    await waitFor(() => {
      expect(result.current.loadingDepartments).toBe(false);
      expect(result.current.departments).toEqual([]);
      expect(result.current.departmentErrorMessage).toBe('部門を取得できません');
    });
  });
});
