/**
 * Copyright(C) 2026 Luvina
 * employeeFormState.test.ts - Unit Tests for Employee Form SessionStorage Helper
 * 04/09/2026 Pham Van Minh
 */

import {
  saveEmployeeFormData,
  getEmployeeFormData,
  clearEmployeeFormData,
  setEditEmployeeId,
  getEditEmployeeId,
  clearEditEmployeeId,
} from '@/lib/storage/employeeFormState';
import { STORAGE_KEYS } from '@/constants';
import { EmployeeFormData } from '@/types/employee';

describe('employeeFormState storage helper', () => {
  const sampleFormData: EmployeeFormData = {
    employeeLoginId: 'testuser',
    departmentId: 1,
    employeeName: 'Nguyễn Văn A',
    employeeNameKana: 'ﾅｶﾞﾔﾏ',
    employeeBirthDate: '1990/01/01',
    employeeEmail: 'test@luvina.net',
    employeeTelephone: '0123456789',
    employeeLoginPassword: 'Password123',
    employeeLoginPasswordConfirm: 'Password123',
    certificationId: 2,
    certificationStartDate: '2020/01/01',
    certificationEndDate: '2025/01/01',
    score: 150,
  };

  beforeEach(() => {
    sessionStorage.clear();
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  test('saves and retrieves employee form data correctly', () => {
    expect(getEmployeeFormData()).toBeNull();
    saveEmployeeFormData(sampleFormData);
    const retrieved = getEmployeeFormData();
    expect(retrieved).toEqual(sampleFormData);
  });

  test('clears employee form data', () => {
    saveEmployeeFormData(sampleFormData);
    expect(getEmployeeFormData()).not.toBeNull();
    clearEmployeeFormData();
    expect(getEmployeeFormData()).toBeNull();
  });

  test('handles corrupted JSON in employee form data gracefully', () => {
    sessionStorage.setItem(STORAGE_KEYS.EMPLOYEE_FORM_DATA, 'invalid json');
    expect(getEmployeeFormData()).toBeNull();
  });

  test('sets, gets and clears edit_employee_id', () => {
    expect(getEditEmployeeId()).toBeNull();
    setEditEmployeeId(42);
    expect(getEditEmployeeId()).toBe(42);
    clearEditEmployeeId();
    expect(getEditEmployeeId()).toBeNull();
  });

  test('returns null for non-numeric edit_employee_id', () => {
    sessionStorage.setItem(STORAGE_KEYS.EDIT_EMPLOYEE_ID, 'not-a-number');
    expect(getEditEmployeeId()).toBeNull();
  });
});
