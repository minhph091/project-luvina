/**
 * Copyright(C) 2026 Luvina
 * useAdm004.test.tsx - Unit Tests for useAdm004 Hook
 * 04/09/2026 Pham Van Minh
 */

import { renderHook, act, waitFor } from '@testing-library/react';
import { useAdm004 } from '@/hooks/useAdm004';
import { getEmployeeById } from '@/lib/api/employees';
import {
  setEditEmployeeId,
  clearEditEmployeeId,
  saveEmployeeFormData,
  clearEmployeeFormData,
  getEmployeeFormData,
} from '@/lib/storage/employeeFormState';
import { useRouter } from 'next/navigation';
import { APP_ROUTES } from '@/constants';

jest.mock('@/lib/api/employees', () => ({
  getEmployeeById: jest.fn(),
}));

const mockPush = jest.fn();
jest.mock('next/navigation', () => ({
  useRouter: jest.fn(),
}));

describe('useAdm004 Hook', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    sessionStorage.clear();
    (useRouter as jest.Mock).mockReturnValue({
      push: mockPush,
    });
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  test('initializes in ADD mode when edit_employee_id is not present', async () => {
    clearEditEmployeeId();
    clearEmployeeFormData();

    const { result } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
      expect(result.current.mode).toBe('ADD');
      expect(result.current.formData.employeeLoginId).toBe('');
      expect(result.current.apiError).toBeNull();
    });
  });

  test('initializes in EDIT mode and loads employee data via API', async () => {
    setEditEmployeeId(5);
    (getEmployeeById as jest.Mock).mockResolvedValue({
      code: 200,
      employee: {
        employeeId: 5,
        employeeLoginId: 'emp005',
        departmentId: 2,
        employeeName: 'Trần Văn B',
        employeeNameKana: 'ﾀﾅｶ',
        employeeBirthDate: '1992-08-15',
        employeeEmail: 'tranb@luvina.net',
        employeeTelephone: '0912345678',
        certificationId: 1,
        certificationStartDate: '2021-06-01',
        certificationEndDate: '2026-06-01',
        score: 160,
      },
    });

    const { result } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
      expect(result.current.mode).toBe('EDIT');
      expect(result.current.formData.employeeLoginId).toBe('emp005');
      expect(result.current.formData.employeeBirthDate).toBe('1992/08/15');
      expect(result.current.formData.certificationStartDate).toBe('2021/06/01');
      expect(result.current.formData.score).toBe('160');
      expect(result.current.isCertSelected).toBe(true);
    });
  });

  test('restores form data from sessionStorage when returning from ADM005', async () => {
    const savedData = {
      employeeLoginId: 'restoredUser',
      departmentId: 3,
      employeeName: 'Lê Văn C',
      employeeNameKana: 'ｶﾅ',
      employeeBirthDate: '1994/03/20',
      employeeEmail: 'levanc@luvina.net',
      employeeTelephone: '0988776655',
      employeeLoginPassword: 'Password123',
      employeeLoginPasswordConfirm: 'Password123',
      certificationId: '',
      certificationStartDate: '',
      certificationEndDate: '',
      score: '',
    };
    saveEmployeeFormData(savedData);

    const { result } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
      expect(result.current.formData.employeeLoginId).toBe('restoredUser');
      expect(result.current.formData.employeeName).toBe('Lê Văn C');
      expect(getEmployeeById).not.toHaveBeenCalled();
    });
  });

  test('handles field changes and onBlur realtime validation', async () => {
    clearEditEmployeeId();
    clearEmployeeFormData();

    const { result } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    // Blur empty account name -> triggers ER001 error
    act(() => {
      result.current.handleBlur('employeeLoginId');
    });

    expect(result.current.errors.employeeLoginId).toBeTruthy();

    // Type valid account name -> error clears
    act(() => {
      result.current.handleFieldChange('employeeLoginId', 'valid_user');
    });

    expect(result.current.formData.employeeLoginId).toBe('valid_user');
    expect(result.current.errors.employeeLoginId).toBeUndefined();
  });

  test('handles date changes correctly', async () => {
    const { result } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    act(() => {
      result.current.handleDateChange('employeeBirthDate', new Date(1996, 7, 25)); // 1996/08/25
    });

    expect(result.current.formData.employeeBirthDate).toBe('1996/08/25');
  });

  test('clears Japanese certificate fields and errors when certificationId is reset to empty', async () => {
    const { result } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    act(() => {
      result.current.handleFieldChange('certificationId', 1);
      result.current.handleFieldChange('certificationStartDate', '2025/01/01');
      result.current.handleFieldChange('certificationEndDate', '2026/01/01');
      result.current.handleFieldChange('score', '150');
    });

    expect(result.current.isCertSelected).toBe(true);

    // Reset cert to empty
    act(() => {
      result.current.handleFieldChange('certificationId', '');
    });

    expect(result.current.isCertSelected).toBe(false);
    expect(result.current.formData.certificationStartDate).toBe('');
    expect(result.current.formData.certificationEndDate).toBe('');
    expect(result.current.formData.score).toBe('');
  });

  test('prevents submission and marks fields when form is invalid', async () => {
    const { result } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    let success = false;
    act(() => {
      success = result.current.handleSubmit();
    });

    expect(success).toBe(false);
    expect(Object.keys(result.current.errors).length).toBeGreaterThan(0);
    expect(mockPush).not.toHaveBeenCalled();
  });

  test('submits successfully, saves to sessionStorage, and navigates to confirm page when form is valid', async () => {
    const { result } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(result.current.loading).toBe(false);
    });

    act(() => {
      result.current.handleFieldChange('employeeLoginId', 'user_valid');
      result.current.handleFieldChange('departmentId', 1);
      result.current.handleFieldChange('employeeName', 'Nguyễn Văn Test');
      result.current.handleFieldChange('employeeNameKana', 'ﾃｽﾄ');
      result.current.handleFieldChange('employeeBirthDate', '1990/01/01');
      result.current.handleFieldChange('employeeEmail', 'test@luvina.net');
      result.current.handleFieldChange('employeeTelephone', '0912345678');
      result.current.handleFieldChange('employeeLoginPassword', 'Password123');
      result.current.handleFieldChange('employeeLoginPasswordConfirm', 'Password123');
      result.current.handleFieldChange('certificationId', '');
    });

    let success = false;
    act(() => {
      success = result.current.handleSubmit();
    });

    expect(success).toBe(true);
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_CONFIRM);

    const saved = getEmployeeFormData();
    expect(saved?.employeeLoginId).toBe('user_valid');
  });

  test('navigates back to ADM002 in ADD mode and to ADM003 in EDIT mode', async () => {
    clearEditEmployeeId();
    const { result: addHook } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(addHook.current.loading).toBe(false);
    });

    act(() => {
      addHook.current.handleBack();
    });
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_LIST);

    // Test in EDIT mode
    mockPush.mockClear();
    setEditEmployeeId(10);
    (getEmployeeById as jest.Mock).mockResolvedValue({
      code: 200,
      employee: {
        employeeId: 10,
        employeeLoginId: 'emp10',
        departmentId: 1,
        employeeName: 'Name 10',
        employeeNameKana: 'ｶﾅ',
        employeeBirthDate: '1990-01-01',
        employeeEmail: 'emp10@luvina.net',
        employeeTelephone: '0987654321',
      },
    });

    const { result: editHook } = renderHook(() => useAdm004());

    await waitFor(() => {
      expect(editHook.current.loading).toBe(false);
    });

    act(() => {
      editHook.current.handleBack();
    });
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_DETAIL);
  });
});
