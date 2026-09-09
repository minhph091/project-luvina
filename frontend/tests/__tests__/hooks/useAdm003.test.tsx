/**
 * Copyright(C) 2026 Luvina
 * useAdm003.test.tsx - Unit Tests for useAdm003 Hook
 * 08/09/2026 Pham Van Minh
 */

import { renderHook, act, waitFor } from '@testing-library/react';
import { useAdm003 } from '@/hooks/useAdm003';
import { useRouter, useSearchParams } from 'next/navigation';
import { APP_ROUTES, COMPLETE_ACTION_TYPES, SYSTEM_MESSAGES, VALIDATION_MESSAGES } from '@/constants';
import { getEmployeeById, deleteEmployee } from '@/lib/api/employees';
import { getEditEmployeeId, getEmployeeFormData } from '@/lib/storage/employeeFormState';
import { getEmployeeCompleteAction } from '@/lib/storage/employeeCompleteState';

const mockPush = jest.fn();
const mockGetSearchParams = jest.fn();

jest.mock('next/navigation', () => ({
  useRouter: jest.fn(),
  useSearchParams: jest.fn(),
}));

jest.mock('@/lib/api/employees', () => ({
  getEmployeeById: jest.fn(),
  deleteEmployee: jest.fn(),
}));

const MOCK_API_EMPLOYEE_RESPONSE = {
  code: 200,
  employeeId: 1,
  employeeLoginId: 'ntmhuong',
  departmentId: 1,
  departmentName: 'Nhóm 1',
  employeeName: 'Nguyễn Thị Mai Hương',
  employeeNameKana: '名カナ',
  employeeBirthDate: '1983-07-08',
  employeeEmail: 'ntmhuong@luvina.net',
  employeeTelephone: '0914326386',
  certifications: [
    {
      certificationId: 1,
      certificationName: 'Trình độ tiếng nhật cấp 1',
      startDate: '2010-07-08',
      endDate: '2010-07-08',
      score: 290,
    },
  ],
  employee: {
    employeeId: 1,
    employeeLoginId: 'ntmhuong',
    departmentId: 1,
    departmentName: 'Nhóm 1',
    employeeName: 'Nguyễn Thị Mai Hương',
    employeeNameKana: '名カナ',
    employeeBirthDate: '1983-07-08',
    employeeEmail: 'ntmhuong@luvina.net',
    employeeTelephone: '0914326386',
    certificationId: 1,
    certificationName: 'Trình độ tiếng nhật cấp 1',
    certificationStartDate: '2010-07-08',
    certificationEndDate: '2010-07-08',
    score: 290,
  },
};

describe('useAdm003 Hook', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    sessionStorage.clear();
    (useRouter as jest.Mock).mockReturnValue({ push: mockPush });
    (useSearchParams as jest.Mock).mockReturnValue({
      get: mockGetSearchParams.mockReturnValue('1'),
    });
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  test('redirects to SYSTEM_ERROR when ID parameter is missing or invalid', async () => {
    mockGetSearchParams.mockReturnValue(null);

    renderHook(() => useAdm003());

    await waitFor(() => {
      expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.SYSTEM_ERROR);
    });
  });

  test('fetches employee detail successfully and formats dates', async () => {
    (getEmployeeById as jest.Mock).mockResolvedValue(MOCK_API_EMPLOYEE_RESPONSE);

    const { result } = renderHook(() => useAdm003());

    expect(result.current.loading).toBe(true);

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    expect(getEmployeeById).toHaveBeenCalledWith(1);
    expect(result.current.employee).not.toBeNull();
    expect(result.current.employee?.employeeLoginId).toBe('ntmhuong');
    expect(result.current.employee?.employeeBirthDate).toBe('1983/07/08');
    expect(result.current.employee?.certificationStartDate).toBe('2010/07/08');
    expect(result.current.hasCertification).toBe(true);
    expect(result.current.apiError).toBeNull();
  });

  test('handles API error when fetching employee details fails by redirecting to SYSTEM_ERROR', async () => {
    (getEmployeeById as jest.Mock).mockRejectedValue(new Error('Network error'));

    renderHook(() => useAdm003());

    await waitFor(() => {
      expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.SYSTEM_ERROR);
    });
  });

  test('navigates to EDIT page with saved editId and cleared form data', async () => {
    (getEmployeeById as jest.Mock).mockResolvedValue(MOCK_API_EMPLOYEE_RESPONSE);

    const { result } = renderHook(() => useAdm003());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    act(() => {
      result.current.handleNavigateToEdit();
    });

    expect(getEditEmployeeId()).toBe(1);
    expect(getEmployeeFormData()).toBeNull();
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_EDIT);
  });

  test('navigates back to EMPLOYEE_LIST on handleNavigateToList', async () => {
    (getEmployeeById as jest.Mock).mockResolvedValue(MOCK_API_EMPLOYEE_RESPONSE);

    const { result } = renderHook(() => useAdm003());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    act(() => {
      result.current.handleNavigateToList();
    });

    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_LIST);
  });

  test('handles delete action when user confirms and deletion succeeds', async () => {
    (getEmployeeById as jest.Mock).mockResolvedValue(MOCK_API_EMPLOYEE_RESPONSE);
    (deleteEmployee as jest.Mock).mockResolvedValue({ code: 200, employeeId: 1 });

    const confirmSpy = jest.spyOn(window, 'confirm').mockReturnValue(true);

    const { result } = renderHook(() => useAdm003());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    await act(async () => {
      await result.current.handleDelete();
    });

    expect(confirmSpy).toHaveBeenCalledWith(SYSTEM_MESSAGES.MSG004_DELETE_CONFIRM);
    expect(deleteEmployee).toHaveBeenCalledWith(1);
    expect(getEmployeeCompleteAction()).toBe(COMPLETE_ACTION_TYPES.DELETE);
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_COMPLETE);

    confirmSpy.mockRestore();
  });

  test('cancels delete action when user clicks cancel on confirm dialog', async () => {
    (getEmployeeById as jest.Mock).mockResolvedValue(MOCK_API_EMPLOYEE_RESPONSE);

    const confirmSpy = jest.spyOn(window, 'confirm').mockReturnValue(false);

    const { result } = renderHook(() => useAdm003());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    await act(async () => {
      await result.current.handleDelete();
    });

    expect(confirmSpy).toHaveBeenCalledWith(SYSTEM_MESSAGES.MSG004_DELETE_CONFIRM);
    expect(deleteEmployee).not.toHaveBeenCalled();

    confirmSpy.mockRestore();
  });

  test('displays error message when delete API returns error', async () => {
    (getEmployeeById as jest.Mock).mockResolvedValue(MOCK_API_EMPLOYEE_RESPONSE);
    (deleteEmployee as jest.Mock).mockResolvedValue({
      code: 500,
      message: { code: 'ER013', params: ['ＩＤ'] },
    });

    const confirmSpy = jest.spyOn(window, 'confirm').mockReturnValue(true);

    const { result } = renderHook(() => useAdm003());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    await act(async () => {
      await result.current.handleDelete();
    });

    expect(result.current.apiError).toBe(VALIDATION_MESSAGES.ER013_USER_NOT_FOUND);
    expect(mockPush).not.toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_COMPLETE);

    confirmSpy.mockRestore();
  });

  test('displays error message when delete API returns ER014 (user not found)', async () => {
    (getEmployeeById as jest.Mock).mockResolvedValue(MOCK_API_EMPLOYEE_RESPONSE);
    (deleteEmployee as jest.Mock).mockResolvedValue({
      code: 500,
      employeeId: 1,
      message: { code: 'ER014', params: ['ＩＤ'] },
    });

    const confirmSpy = jest.spyOn(window, 'confirm').mockReturnValue(true);

    const { result } = renderHook(() => useAdm003());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    await act(async () => {
      await result.current.handleDelete();
    });

    expect(result.current.apiError).toBe(VALIDATION_MESSAGES.ER014_USER_NOT_FOUND);
    expect(mockPush).not.toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_COMPLETE);

    confirmSpy.mockRestore();
  });
});
