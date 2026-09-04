/**
 * Copyright(C) 2026 Luvina
 * EmployeeForm.test.tsx - Unit Tests for EmployeeForm Component
 * 04/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import EmployeeForm from '@/components/employees/EmployeeForm';
import { EmployeeFormData } from '@/types/employee';
import { BUTTON_LABELS, PAGE_TITLES } from '@/constants';

describe('EmployeeForm Component', () => {
  const mockDepartments = [
    { departmentId: 1, departmentName: 'Phòng Phát triển 1' },
    { departmentId: 2, departmentName: 'Phòng Phát triển 2' },
  ];

  const mockCertifications = [
    { certificationId: 1, certificationName: 'Trình độ tiếng Nhật N1', certificationLevel: 'N1' },
    { certificationId: 2, certificationName: 'Trình độ tiếng Nhật N2', certificationLevel: 'N2' },
  ];

  const initialFormData: EmployeeFormData = {
    employeeLoginId: 'testuser',
    departmentId: 1,
    employeeName: 'Nguyễn Văn A',
    employeeNameKana: 'ﾅｶﾞﾔﾏ',
    employeeBirthDate: '1990/01/01',
    employeeEmail: 'test@luvina.net',
    employeeTelephone: '0123456789',
    employeeLoginPassword: 'Password123',
    employeeLoginPasswordConfirm: 'Password123',
    certificationId: '',
    certificationStartDate: '',
    certificationEndDate: '',
    score: '',
  };

  test('renders in ADD mode with title 会員情報追加', () => {
    const handleFieldChange = jest.fn();
    const handleDateChange = jest.fn();
    const handleBlur = jest.fn();
    const handleSubmit = jest.fn();
    const handleBack = jest.fn();

    render(
      <EmployeeForm
        mode="ADD"
        formData={initialFormData}
        errors={{}}
        departments={mockDepartments}
        certifications={mockCertifications}
        isCertSelected={false}
        birthDateObj={new Date(1990, 0, 1)}
        startDateObj={null}
        endDateObj={null}
        apiError={null}
        loading={false}
        onFieldChange={handleFieldChange}
        onDateChange={handleDateChange}
        onBlur={handleBlur}
        onSubmit={handleSubmit}
        onBack={handleBack}
      />
    );

    expect(screen.getByText(PAGE_TITLES.ADD_EMPLOYEE)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: BUTTON_LABELS.CONFIRM })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: BUTTON_LABELS.BACK })).toBeInTheDocument();
  });

  test('renders in EDIT mode with title 会員情報編集', () => {
    render(
      <EmployeeForm
        mode="EDIT"
        formData={initialFormData}
        errors={{}}
        departments={mockDepartments}
        certifications={mockCertifications}
        isCertSelected={false}
        birthDateObj={new Date(1990, 0, 1)}
        startDateObj={null}
        endDateObj={null}
        apiError={null}
        loading={false}
        onFieldChange={jest.fn()}
        onDateChange={jest.fn()}
        onBlur={jest.fn()}
        onSubmit={jest.fn()}
        onBack={jest.fn()}
      />
    );

    expect(screen.getByText(PAGE_TITLES.EDIT_EMPLOYEE)).toBeInTheDocument();
  });

  test('displays API / system error box when apiError is provided', () => {
    render(
      <EmployeeForm
        mode="ADD"
        formData={initialFormData}
        errors={{}}
        departments={mockDepartments}
        certifications={mockCertifications}
        isCertSelected={false}
        birthDateObj={null}
        startDateObj={null}
        endDateObj={null}
        apiError="システムエラーが発生しました"
        loading={false}
        onFieldChange={jest.fn()}
        onDateChange={jest.fn()}
        onBlur={jest.fn()}
        onSubmit={jest.fn()}
        onBack={jest.fn()}
      />
    );

    expect(screen.getByText('システムエラーが発生しました')).toBeInTheDocument();
  });

  test('displays validation errors under fields', () => {
    render(
      <EmployeeForm
        mode="ADD"
        formData={initialFormData}
        errors={{
          employeeLoginId: 'アカウント名を入力してください',
          employeeEmail: 'メールアドレスを入力してください',
        }}
        departments={mockDepartments}
        certifications={mockCertifications}
        isCertSelected={false}
        birthDateObj={null}
        startDateObj={null}
        endDateObj={null}
        apiError={null}
        loading={false}
        onFieldChange={jest.fn()}
        onDateChange={jest.fn()}
        onBlur={jest.fn()}
        onSubmit={jest.fn()}
        onBack={jest.fn()}
      />
    );

    expect(screen.getByText('アカウント名を入力してください')).toBeInTheDocument();
    expect(screen.getByText('メールアドレスを入力してください')).toBeInTheDocument();
  });

  test('triggers onSubmit and onBack handlers on button click', () => {
    const handleSubmit = jest.fn();
    const handleBack = jest.fn();

    render(
      <EmployeeForm
        mode="ADD"
        formData={initialFormData}
        errors={{}}
        departments={mockDepartments}
        certifications={mockCertifications}
        isCertSelected={false}
        birthDateObj={null}
        startDateObj={null}
        endDateObj={null}
        apiError={null}
        loading={false}
        onFieldChange={jest.fn()}
        onDateChange={jest.fn()}
        onBlur={jest.fn()}
        onSubmit={handleSubmit}
        onBack={handleBack}
      />
    );

    const confirmBtn = screen.getByRole('button', { name: BUTTON_LABELS.CONFIRM });
    fireEvent.click(confirmBtn);
    expect(handleSubmit).toHaveBeenCalled();

    const backBtn = screen.getByRole('button', { name: BUTTON_LABELS.BACK });
    fireEvent.click(backBtn);
    expect(handleBack).toHaveBeenCalled();
  });
});
