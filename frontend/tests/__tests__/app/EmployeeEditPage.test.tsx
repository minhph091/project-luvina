/**
 * Copyright(C) 2026 Luvina
 * EmployeeEditPage.test.tsx - Integration Tests for ADM004 Page
 * 04/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import EmployeeEditPage from '@/app/(protected)/employees/edit/page';
import { getDepartments } from '@/lib/api/departments';
import { getCertifications } from '@/lib/api/certifications';
import { PAGE_TITLES } from '@/constants';

jest.mock('@/lib/api/departments', () => ({
  getDepartments: jest.fn(),
}));

jest.mock('@/lib/api/certifications', () => ({
  getCertifications: jest.fn(),
}));

jest.mock('@/lib/api/employees', () => ({
  getEmployeeById: jest.fn(),
}));

jest.mock('@/hooks/useAuth', () => ({
  useAuth: () => {},
}));

jest.mock('next/navigation', () => ({
  useRouter: () => ({
    push: jest.fn(),
  }),
}));

describe('EmployeeEditPage (ADM004)', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    sessionStorage.clear();
    (getDepartments as jest.Mock).mockResolvedValue({
      code: 200,
      departments: [
        { departmentId: 1, departmentName: 'Phòng Phát triển 1' },
      ],
    });
    (getCertifications as jest.Mock).mockResolvedValue({
      code: 200,
      certifications: [
        { certificationId: 1, certificationName: 'Trình độ tiếng Nhật N1', certificationLevel: 'N1' },
      ],
    });
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  it('renders ADM004 page and loads departments and certifications master data', async () => {
    render(<EmployeeEditPage />);

    expect(screen.getByText(PAGE_TITLES.ADD_EMPLOYEE)).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText('Phòng Phát triển 1')).toBeInTheDocument();
      expect(screen.getByText('Trình độ tiếng Nhật N1')).toBeInTheDocument();
    });
  });
});
