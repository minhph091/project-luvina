/**
 * Copyright(C) 2026 Luvina
 * Adm005.test.tsx - Unit Tests for Adm005 Component
 * 09/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Adm005 from '@/components/employees/Adm005';
import { BUTTON_LABELS, PAGE_TITLES } from '@/constants';
import { EmployeeFormData } from '@/types/employee';

const mockHandleConfirmSubmit = jest.fn();
const mockHandleNavigateToEdit = jest.fn();

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

describe('Adm005 Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('returns null when loading is true or formData is null', () => {
    const { container: loadingContainer } = render(
      <Adm005
        formData={MOCK_FORM_DATA}
        loading={true}
        submitting={false}
        apiError={null}
        hasCertification={true}
        handleConfirmSubmit={mockHandleConfirmSubmit}
        handleNavigateToEdit={mockHandleNavigateToEdit}
      />
    );
    expect(loadingContainer.firstChild).toBeNull();

    const { container: nullDataContainer } = render(
      <Adm005
        formData={null}
        loading={false}
        submitting={false}
        apiError={null}
        hasCertification={false}
        handleConfirmSubmit={mockHandleConfirmSubmit}
        handleNavigateToEdit={mockHandleNavigateToEdit}
      />
    );
    expect(nullDataContainer.firstChild).toBeNull();
  });

  test('renders form information and responds to button clicks', () => {
    render(
      <Adm005
        formData={MOCK_FORM_DATA}
        loading={false}
        submitting={false}
        apiError={null}
        hasCertification={true}
        handleConfirmSubmit={mockHandleConfirmSubmit}
        handleNavigateToEdit={mockHandleNavigateToEdit}
      />
    );

    expect(screen.getByText(PAGE_TITLES.INFO_CONFIRM)).toBeInTheDocument();
    expect(screen.getByText('minhpv')).toBeInTheDocument();
    expect(screen.getByText('Phát triển số 1')).toBeInTheDocument();
    expect(screen.getByText('Phạm Văn Minh')).toBeInTheDocument();
    expect(screen.getByText('Trình độ tiếng Nhật N2')).toBeInTheDocument();

    const okBtn = screen.getByRole('button', { name: BUTTON_LABELS.OK });
    fireEvent.click(okBtn);
    expect(mockHandleConfirmSubmit).toHaveBeenCalledTimes(1);

    const backBtn = screen.getByRole('button', { name: BUTTON_LABELS.BACK });
    fireEvent.click(backBtn);
    expect(mockHandleNavigateToEdit).toHaveBeenCalledTimes(1);
  });

  test('displays api error when present', () => {
    render(
      <Adm005
        formData={MOCK_FORM_DATA}
        loading={false}
        submitting={false}
        apiError="Lỗi hệ thống khi xác nhận"
        hasCertification={false}
        handleConfirmSubmit={mockHandleConfirmSubmit}
        handleNavigateToEdit={mockHandleNavigateToEdit}
      />
    );

    expect(screen.getByText('Lỗi hệ thống khi xác nhận')).toBeInTheDocument();
  });
});
