/**
 * Copyright(C) 2026 Luvina
 * employeeCompleteState.test.ts - Unit Tests for Employee Complete State Helper
 * 07/09/2026 Pham Van Minh
 */

import {
  setEmployeeCompleteAction,
  getEmployeeCompleteAction,
  clearEmployeeCompleteAction,
} from '@/lib/storage/employeeCompleteState';
import { STORAGE_KEYS, COMPLETE_ACTION_TYPES } from '@/constants';

describe('employeeCompleteState storage helper', () => {
  beforeEach(() => {
    sessionStorage.clear();
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  test('returns null when no action is stored in sessionStorage', () => {
    expect(getEmployeeCompleteAction()).toBeNull();
  });

  test('saves and retrieves ADD action correctly', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.ADD);
    expect(sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_COMPLETE_ACTION)).toBe('ADD');
    expect(getEmployeeCompleteAction()).toBe('ADD');
  });

  test('saves and retrieves EDIT action correctly', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.EDIT);
    expect(sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_COMPLETE_ACTION)).toBe('EDIT');
    expect(getEmployeeCompleteAction()).toBe('EDIT');
  });

  test('saves and retrieves DELETE action correctly', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.DELETE);
    expect(sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_COMPLETE_ACTION)).toBe('DELETE');
    expect(getEmployeeCompleteAction()).toBe('DELETE');
  });

  test('returns null if stored value is invalid', () => {
    sessionStorage.setItem(STORAGE_KEYS.EMPLOYEE_COMPLETE_ACTION, 'UNKNOWN_ACTION');
    expect(getEmployeeCompleteAction()).toBeNull();
  });

  test('clears stored complete action from sessionStorage', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.ADD);
    expect(getEmployeeCompleteAction()).toBe('ADD');

    clearEmployeeCompleteAction();
    expect(sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_COMPLETE_ACTION)).toBeNull();
    expect(getEmployeeCompleteAction()).toBeNull();
  });
});
