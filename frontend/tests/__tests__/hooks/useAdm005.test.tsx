/**
 * Copyright(C) 2026 Luvina
 * useAdm005.test.tsx - Unit Tests for useAdm005 Hook
 * 05/09/2026 Pham Van Minh
 */

import { renderHook, act } from '@testing-library/react';
import { useAdm005 } from '@/hooks/useAdm005';
import {
  setEditEmployeeId,
  clearEditEmployeeId,
  saveEmployeeFormData,
  clearEmployeeFormData,
} from '@/lib/storage/employeeFormState';
import { useRouter } from 'next/navigation';
import { APP_ROUTES } from '@/constants';
import { EmployeeFormData } from '@/types/employee';

const mockPush = jest.fn();
jest.mock('next/navigation', () => ({
  useRouter: jest.fn(),
}));

const MOCK_FORM_DATA: EmployeeFormData = {
  employeeLoginId: 'minhpv',
  departmentId: 1,
  departmentName: 'Phát triển số 1',
  employeeName: 'Phạm Văn Minh',
  employeeNameKana: 'ファムヴァンミン',
  employeeBirthDate: '1998/05/20',
  employeeEmail: 'minhpv@luvina.net',
  employeeTelephone: '0987654321',
  certificationId: 2,
  certificationName: 'Trình độ tiếng Nhật N2',
  certificationStartDate: '2023/07/01',
  certificationEndDate: '2028/07/01',
  score: '150',
};

describe('useAdm005 Hook', () => {
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

  test('redirects to EMPLOYEE_EDIT if no formData exists in sessionStorage', () => {
    clearEmployeeFormData();

    const { result } = renderHook(() => useAdm005());

    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_EDIT);
    expect(result.current.formData).toBeNull();
  });

  test('initializes successfully with saved form data in ADD mode', () => {
    clearEditEmployeeId();
    saveEmployeeFormData(MOCK_FORM_DATA);

    const { result } = renderHook(() => useAdm005());

    expect(result.current.loading).toBe(false);
    expect(result.current.mode).toBe('ADD');
    expect(result.current.formData).toEqual(MOCK_FORM_DATA);
    expect(result.current.hasCertification).toBe(true);
  });

  test('initializes in EDIT mode when edit_employee_id is present', () => {
    setEditEmployeeId(10);
    saveEmployeeFormData(MOCK_FORM_DATA);

    const { result } = renderHook(() => useAdm005());

    expect(result.current.mode).toBe('EDIT');
    expect(result.current.formData?.employeeLoginId).toBe('minhpv');
  });

  test('correctly identifies hasCertification = false when no certificationId is selected', () => {
    const dataWithoutCert: EmployeeFormData = {
      ...MOCK_FORM_DATA,
      certificationId: '',
      certificationName: '',
      certificationStartDate: '',
      certificationEndDate: '',
      score: '',
    };
    saveEmployeeFormData(dataWithoutCert);

    const { result } = renderHook(() => useAdm005());

    expect(result.current.hasCertification).toBe(false);
  });

  test('handleConfirmSubmit navigates to EMPLOYEE_COMPLETE', () => {
    saveEmployeeFormData(MOCK_FORM_DATA);

    const { result } = renderHook(() => useAdm005());

    act(() => {
      result.current.handleConfirmSubmit();
    });

    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_COMPLETE);
  });

  test('handleNavigateToEdit navigates back to EMPLOYEE_EDIT', () => {
    saveEmployeeFormData(MOCK_FORM_DATA);

    const { result } = renderHook(() => useAdm005());

    act(() => {
      result.current.handleNavigateToEdit();
    });

    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_EDIT);
  });
});
