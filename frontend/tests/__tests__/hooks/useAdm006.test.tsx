/**
 * Copyright(C) 2026 Luvina
 * useAdm006.test.tsx - Unit Tests for useAdm006 Hook
 * 09/09/2026 Pham Van Minh
 */

import { renderHook, act } from '@testing-library/react';
import { useAdm006 } from '@/hooks/useAdm006';
import { APP_ROUTES, COMPLETE_ACTION_TYPES, STORAGE_KEYS, SYSTEM_MESSAGES } from '@/constants';
import {
  setEmployeeCompleteAction,
  getEmployeeCompleteAction,
} from '@/lib/storage/employeeCompleteState';
import { saveEmployeeSearchState } from '@/lib/storage/employeeSearchState';

const mockPush = jest.fn();
jest.mock('next/navigation', () => ({
  useRouter: () => ({
    push: mockPush,
  }),
}));

describe('useAdm006 Hook', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    sessionStorage.clear();
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  test('returns MSG001 by default when no action is in sessionStorage', () => {
    const { result } = renderHook(() => useAdm006());
    expect(result.current.completeMessage).toBe(SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE);
  });

  test('returns MSG001 when complete action is ADD', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.ADD);
    const { result } = renderHook(() => useAdm006());
    expect(result.current.completeMessage).toBe(SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE);
  });

  test('returns MSG002 when complete action is EDIT', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.EDIT);
    const { result } = renderHook(() => useAdm006());
    expect(result.current.completeMessage).toBe(SYSTEM_MESSAGES.MSG002_USER_UPDATE_COMPLETE);
  });

  test('returns MSG003 when complete action is DELETE', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.DELETE);
    const { result } = renderHook(() => useAdm006());
    expect(result.current.completeMessage).toBe(SYSTEM_MESSAGES.MSG003_USER_DELETE_COMPLETE);
  });

  test('handleNavigateToList clears complete action, search state, and navigates to EMPLOYEE_LIST', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.ADD);
    saveEmployeeSearchState({
      currentPage: 2,
      searchName: 'Minh',
      searchDepartmentId: 1,
      appliedName: 'Minh',
      appliedDepartmentId: 1,
      sort: { employeeNameOrder: 'ASC', certificationNameOrder: 'ASC', endDateOrder: 'ASC' },
      activeSortColumn: 'employeeNameOrder',
    });

    const { result } = renderHook(() => useAdm006());

    act(() => {
      result.current.handleNavigateToList();
    });

    expect(getEmployeeCompleteAction()).toBeNull();
    expect(sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_SEARCH_STATE)).toBeNull();
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_LIST);
  });
});
