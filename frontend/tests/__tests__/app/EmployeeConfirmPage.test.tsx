/**
 * Copyright(C) 2026 Luvina
 * EmployeeConfirmPage.test.tsx - Component Tests for ADM005 EmployeeConfirmPage
 * 05/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import EmployeeConfirmPage from '@/app/(protected)/employees/confirm/page';
import { saveEmployeeFormData, clearEmployeeFormData } from '@/lib/storage/employeeFormState';
import { useRouter } from 'next/navigation';
import { APP_ROUTES, BUTTON_LABELS, FIELD_LABELS, PAGE_TITLES } from '@/constants';
import { EmployeeFormData } from '@/types/employee';

const mockPush = jest.fn();
jest.mock('next/navigation', () => ({
  useRouter: jest.fn(),
}));

jest.mock('@/hooks/useAuth', () => ({
  useAuth: jest.fn(),
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

describe('EmployeeConfirmPage Component (ADM005)', () => {
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

  test('renders full employee information including departmentName and certification', () => {
    saveEmployeeFormData(MOCK_FORM_DATA);

    render(<EmployeeConfirmPage />);

    // Kiểm tra tiêu đề trang
    expect(screen.getByText(PAGE_TITLES.INFO_CONFIRM)).toBeInTheDocument();
    expect(screen.getByText(PAGE_TITLES.CONFIRM_DESCRIPTION)).toBeInTheDocument();

    // Kiểm tra thông tin cơ bản
    expect(screen.getByText(MOCK_FORM_DATA.employeeLoginId)).toBeInTheDocument();
    expect(screen.getByText('Phát triển số 1')).toBeInTheDocument();
    expect(screen.getByText(MOCK_FORM_DATA.employeeName)).toBeInTheDocument();
    expect(screen.getByText(MOCK_FORM_DATA.employeeNameKana)).toBeInTheDocument();
    expect(screen.getByText(MOCK_FORM_DATA.employeeBirthDate)).toBeInTheDocument();
    expect(screen.getByText(MOCK_FORM_DATA.employeeEmail)).toBeInTheDocument();
    expect(screen.getByText(MOCK_FORM_DATA.employeeTelephone)).toBeInTheDocument();

    // Kiểm tra khối chứng chỉ tiếng Nhật
    expect(screen.getByText(FIELD_LABELS.JAPANESE_LEVEL)).toBeInTheDocument();
    expect(screen.getByText('Trình độ tiếng Nhật N2')).toBeInTheDocument();
    expect(screen.getByText('2023/07/01')).toBeInTheDocument();
    expect(screen.getByText('2028/07/01')).toBeInTheDocument();
    expect(screen.getByText('150')).toBeInTheDocument();

    // Kiểm tra các nút bấm
    expect(screen.getByRole('button', { name: BUTTON_LABELS.OK })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: BUTTON_LABELS.BACK })).toBeInTheDocument();
  });

  test('does not render Japanese certification section when employee has no certification', () => {
    const dataWithoutCert: EmployeeFormData = {
      ...MOCK_FORM_DATA,
      certificationId: '',
      certificationName: '',
      certificationStartDate: '',
      certificationEndDate: '',
      score: '',
    };
    saveEmployeeFormData(dataWithoutCert);

    render(<EmployeeConfirmPage />);

    expect(screen.getByText(MOCK_FORM_DATA.employeeLoginId)).toBeInTheDocument();
    expect(screen.getByText('Phát triển số 1')).toBeInTheDocument();

    // Khối tiếng Nhật không được render
    expect(screen.queryByText(FIELD_LABELS.JAPANESE_LEVEL)).not.toBeInTheDocument();
    expect(screen.queryByText('Trình độ tiếng Nhật N2')).not.toBeInTheDocument();
  });

  test('clicking OK button navigates to EMPLOYEE_COMPLETE', () => {
    saveEmployeeFormData(MOCK_FORM_DATA);

    render(<EmployeeConfirmPage />);

    const okButton = screen.getByRole('button', { name: BUTTON_LABELS.OK });
    fireEvent.click(okButton);

    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_COMPLETE);
  });

  test('clicking BACK button navigates to EMPLOYEE_EDIT', () => {
    saveEmployeeFormData(MOCK_FORM_DATA);

    render(<EmployeeConfirmPage />);

    const backButton = screen.getByRole('button', { name: BUTTON_LABELS.BACK });
    fireEvent.click(backButton);

    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_EDIT);
  });

  test('returns null and redirects to EMPLOYEE_EDIT when no formData in storage', () => {
    clearEmployeeFormData();

    const { container } = render(<EmployeeConfirmPage />);

    expect(container).toBeEmptyDOMElement();
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_EDIT);
  });
});
