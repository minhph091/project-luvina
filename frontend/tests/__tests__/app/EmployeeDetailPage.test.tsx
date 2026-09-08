/**
 * Copyright(C) 2026 Luvina
 * EmployeeDetailPage.test.tsx - Unit Tests for ADM003 Employee Detail Page
 * 08/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import EmployeeDetailPage from '@/app/(protected)/employees/adm003/page';
import { useAdm003 } from '@/hooks/useAdm003';
import { BUTTON_LABELS, COMMON_LABELS, FIELD_LABELS, PAGE_TITLES } from '@/constants';

jest.mock('@/hooks/useAuth', () => ({
  useAuth: jest.fn(),
}));

jest.mock('@/hooks/useAdm003', () => ({
  useAdm003: jest.fn(),
}));

const mockHandleNavigateToEdit = jest.fn();
const mockHandleNavigateToList = jest.fn();
const mockHandleDelete = jest.fn();

const MOCK_EMPLOYEE = {
  employeeId: 1,
  employeeLoginId: 'ntmhuong',
  departmentId: 1,
  departmentName: 'Nhóm 1',
  employeeName: 'Nguyễn Thị Mai Hương',
  employeeNameKana: '名カナ',
  employeeBirthDate: '1983/07/08',
  employeeEmail: 'ntmhuong@luvina.net',
  employeeTelephone: '0914326386',
  certificationId: 1,
  certificationName: 'Trình độ tiếng nhật cấp 1',
  certificationStartDate: '2010/07/08',
  certificationEndDate: '2010/07/08',
  score: 290,
};

describe('EmployeeDetailPage (ADM003)', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('displays loading indicator when data is loading and no employee exists', () => {
    (useAdm003 as jest.Mock).mockReturnValue({
      employee: null,
      loading: true,
      deleting: false,
      apiError: null,
      hasCertification: false,
      handleNavigateToEdit: mockHandleNavigateToEdit,
      handleNavigateToList: mockHandleNavigateToList,
      handleDelete: mockHandleDelete,
    });

    render(<EmployeeDetailPage />);
    expect(screen.getByText(COMMON_LABELS.LOADING)).toBeInTheDocument();
  });

  test('renders employee information and Japanese certification details correctly', () => {
    (useAdm003 as jest.Mock).mockReturnValue({
      employee: MOCK_EMPLOYEE,
      loading: false,
      deleting: false,
      apiError: null,
      hasCertification: true,
      handleNavigateToEdit: mockHandleNavigateToEdit,
      handleNavigateToList: mockHandleNavigateToList,
      handleDelete: mockHandleDelete,
    });

    render(<EmployeeDetailPage />);

    // Title
    expect(screen.getByText(PAGE_TITLES.INFO_CONFIRM)).toBeInTheDocument();

    // Fields
    expect(screen.getByText('ntmhuong')).toBeInTheDocument();
    expect(screen.getByText('Nhóm 1')).toBeInTheDocument();
    expect(screen.getByText('Nguyễn Thị Mai Hương')).toBeInTheDocument();
    expect(screen.getByText('名カナ')).toBeInTheDocument();
    expect(screen.getByText('1983/07/08')).toBeInTheDocument();
    expect(screen.getByText('ntmhuong@luvina.net')).toBeInTheDocument();
    expect(screen.getByText('0914326386')).toBeInTheDocument();

    // Certification
    expect(screen.getByText('Trình độ tiếng nhật cấp 1')).toBeInTheDocument();
    expect(screen.getByText('290')).toBeInTheDocument();

    // Buttons
    expect(screen.getByRole('button', { name: BUTTON_LABELS.EDIT })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: BUTTON_LABELS.DELETE })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: BUTTON_LABELS.BACK })).toBeInTheDocument();
  });

  test('hides certification block when employee has no certification', () => {
    (useAdm003 as jest.Mock).mockReturnValue({
      employee: {
        ...MOCK_EMPLOYEE,
        certificationName: null,
      },
      loading: false,
      deleting: false,
      apiError: null,
      hasCertification: false,
      handleNavigateToEdit: mockHandleNavigateToEdit,
      handleNavigateToList: mockHandleNavigateToList,
      handleDelete: mockHandleDelete,
    });

    render(<EmployeeDetailPage />);

    expect(screen.queryByText(FIELD_LABELS.CERTIFICATION)).not.toBeInTheDocument();
    expect(screen.queryByText(FIELD_LABELS.START_DATE)).not.toBeInTheDocument();
  });

  test('displays API error box when apiError exists', () => {
    (useAdm003 as jest.Mock).mockReturnValue({
      employee: null,
      loading: false,
      deleting: false,
      apiError: '該当するユーザが存在しません。',
      hasCertification: false,
      handleNavigateToEdit: mockHandleNavigateToEdit,
      handleNavigateToList: mockHandleNavigateToList,
      handleDelete: mockHandleDelete,
    });

    render(<EmployeeDetailPage />);

    const errorBox = screen.getByText('該当するユーザが存在しません。');
    expect(errorBox).toBeInTheDocument();
  });

  test('calls handleNavigateToEdit when clicking Edit button', () => {
    (useAdm003 as jest.Mock).mockReturnValue({
      employee: MOCK_EMPLOYEE,
      loading: false,
      deleting: false,
      apiError: null,
      hasCertification: true,
      handleNavigateToEdit: mockHandleNavigateToEdit,
      handleNavigateToList: mockHandleNavigateToList,
      handleDelete: mockHandleDelete,
    });

    render(<EmployeeDetailPage />);

    const editBtn = screen.getByRole('button', { name: BUTTON_LABELS.EDIT });
    fireEvent.click(editBtn);

    expect(mockHandleNavigateToEdit).toHaveBeenCalledTimes(1);
  });

  test('calls handleDelete when clicking Delete button', () => {
    (useAdm003 as jest.Mock).mockReturnValue({
      employee: MOCK_EMPLOYEE,
      loading: false,
      deleting: false,
      apiError: null,
      hasCertification: true,
      handleNavigateToEdit: mockHandleNavigateToEdit,
      handleNavigateToList: mockHandleNavigateToList,
      handleDelete: mockHandleDelete,
    });

    render(<EmployeeDetailPage />);

    const deleteBtn = screen.getByRole('button', { name: BUTTON_LABELS.DELETE });
    fireEvent.click(deleteBtn);

    expect(mockHandleDelete).toHaveBeenCalledTimes(1);
  });

  test('calls handleNavigateToList when clicking Back button', () => {
    (useAdm003 as jest.Mock).mockReturnValue({
      employee: MOCK_EMPLOYEE,
      loading: false,
      deleting: false,
      apiError: null,
      hasCertification: true,
      handleNavigateToEdit: mockHandleNavigateToEdit,
      handleNavigateToList: mockHandleNavigateToList,
      handleDelete: mockHandleDelete,
    });

    render(<EmployeeDetailPage />);

    const backBtn = screen.getByRole('button', { name: BUTTON_LABELS.BACK });
    fireEvent.click(backBtn);

    expect(mockHandleNavigateToList).toHaveBeenCalledTimes(1);
  });
});
