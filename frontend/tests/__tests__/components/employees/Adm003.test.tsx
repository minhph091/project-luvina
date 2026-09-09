/**
 * Copyright(C) 2026 Luvina
 * Adm003.test.tsx - Unit Tests for Adm003 Component
 * 09/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Adm003 from '@/components/employees/Adm003';
import { BUTTON_LABELS, COMMON_LABELS, PAGE_TITLES } from '@/constants';

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

describe('Adm003 Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders loading indicator when loading is true and no employee data exists', () => {
    render(
      <Adm003
        employee={null}
        loading={true}
        deleting={false}
        apiError={null}
        hasCertification={false}
        handleNavigateToEdit={mockHandleNavigateToEdit}
        handleNavigateToList={mockHandleNavigateToList}
        handleDelete={mockHandleDelete}
      />
    );

    expect(screen.getByText(COMMON_LABELS.LOADING)).toBeInTheDocument();
  });

  test('renders employee details and responds to button clicks', () => {
    render(
      <Adm003
        employee={MOCK_EMPLOYEE}
        loading={false}
        deleting={false}
        apiError={null}
        hasCertification={true}
        handleNavigateToEdit={mockHandleNavigateToEdit}
        handleNavigateToList={mockHandleNavigateToList}
        handleDelete={mockHandleDelete}
      />
    );

    expect(screen.getByText(PAGE_TITLES.INFO_CONFIRM)).toBeInTheDocument();
    expect(screen.getByText('ntmhuong')).toBeInTheDocument();
    expect(screen.getByText('Nguyễn Thị Mai Hương')).toBeInTheDocument();
    expect(screen.getByText('Trình độ tiếng nhật cấp 1')).toBeInTheDocument();

    const editBtn = screen.getByRole('button', { name: BUTTON_LABELS.EDIT });
    fireEvent.click(editBtn);
    expect(mockHandleNavigateToEdit).toHaveBeenCalledTimes(1);

    const deleteBtn = screen.getByRole('button', { name: BUTTON_LABELS.DELETE });
    fireEvent.click(deleteBtn);
    expect(mockHandleDelete).toHaveBeenCalledTimes(1);

    const backBtn = screen.getByRole('button', { name: BUTTON_LABELS.BACK });
    fireEvent.click(backBtn);
    expect(mockHandleNavigateToList).toHaveBeenCalledTimes(1);
  });

  test('renders server error message if apiError is provided', () => {
    render(
      <Adm003
        employee={null}
        loading={false}
        deleting={false}
        apiError="Nhân viên không tồn tại"
        hasCertification={false}
        handleNavigateToEdit={mockHandleNavigateToEdit}
        handleNavigateToList={mockHandleNavigateToList}
        handleDelete={mockHandleDelete}
      />
    );

    expect(screen.getByText('Nhân viên không tồn tại')).toBeInTheDocument();
  });
});
