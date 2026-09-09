/**
 * Copyright(C) 2026 Luvina
 * EmployeeCompletePage.test.tsx - Unit Tests for ADM006 Employee Complete Page
 * 07/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import EmployeeCompletePage from '@/app/(protected)/employees/adm006/page';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/hooks/useAuth';
import { APP_ROUTES, BUTTON_LABELS, COMPLETE_ACTION_TYPES, STORAGE_KEYS, SYSTEM_MESSAGES } from '@/constants';
import { setEmployeeCompleteAction, getEmployeeCompleteAction } from '@/lib/storage/employeeCompleteState';
import { saveEmployeeSearchState } from '@/lib/storage/employeeSearchState';

jest.mock('next/navigation', () => ({
  useRouter: jest.fn(),
}));

jest.mock('@/hooks/useAuth', () => ({
  useAuth: jest.fn(),
}));

describe('EmployeeCompletePage (ADM006)', () => {
  const mockPush = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    sessionStorage.clear();
    (useRouter as jest.Mock).mockReturnValue({ push: mockPush });
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  test('displays MSG001 (Add complete) by default when no action is in sessionStorage', () => {
    render(<EmployeeCompletePage />);
    expect(useAuth).toHaveBeenCalled();
    expect(screen.getByText(SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE)).toBeInTheDocument();
  });

  test('displays MSG001 when complete action is ADD', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.ADD);
    render(<EmployeeCompletePage />);
    expect(screen.getByText(SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE)).toBeInTheDocument();
  });

  test('displays MSG002 when complete action is EDIT', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.EDIT);
    render(<EmployeeCompletePage />);
    expect(screen.getByText(SYSTEM_MESSAGES.MSG002_USER_UPDATE_COMPLETE)).toBeInTheDocument();
  });

  test('displays MSG003 when complete action is DELETE', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.DELETE);
    render(<EmployeeCompletePage />);
    expect(screen.getByText(SYSTEM_MESSAGES.MSG003_USER_DELETE_COMPLETE)).toBeInTheDocument();
  });

  test('clicking OK button clears complete action, clears search state, and navigates to employee list', () => {
    setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.ADD);
    saveEmployeeSearchState({
      currentPage: 2,
      searchName: 'Minh',
      searchDepartmentId: 1,
      appliedName: 'Minh',
      appliedDepartmentId: 1,
      sort: { employee_id: 'ASC', employee_name: 'ASC', end_date: 'ASC', score: 'ASC' },
      activeSortColumn: 'employee_id',
    });

    render(<EmployeeCompletePage />);

    const okButton = screen.getByRole('button', { name: BUTTON_LABELS.OK });
    fireEvent.click(okButton);

    expect(getEmployeeCompleteAction()).toBeNull();
    expect(sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_SEARCH_STATE)).toBeNull();
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_LIST);
  });
});
